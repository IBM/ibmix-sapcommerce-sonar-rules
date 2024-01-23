package at.ibmix.sonarqube.sapcx.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "AvoidUnsafeQueryParametersRule")
public class AvoidUnsafeQueryParametersRule extends AbstractMethodDetection
{
    private static final String QUERY_CLASS = "de.hybris.platform.servicelayer.search.FlexibleSearchQuery";

    private static final String SERVICE_CLASS = "de.hybris.platform.servicelayer.search.FlexibleSearchService";

    private static final String SERVICE_METHOD = "search";

    @Override
    protected MethodMatchers getMethodInvocationMatchers()
    {
        return MethodMatchers.or(MethodMatchers.create().ofSubTypes(QUERY_CLASS).constructor().withAnyParameters().build(), //
                MethodMatchers.create().ofSubTypes(SERVICE_CLASS).names(SERVICE_METHOD).withAnyParameters().build());
    }

    @Override
    public void onMethodInvocationFound(final MethodInvocationTree tree)
    {
        if (!tree.methodSymbol().parameterTypes().isEmpty() //
                && tree.methodSymbol().parameterTypes().get(0).isSubtypeOf(String.class.getCanonicalName()) //
                && !tree.arguments().get(0).is(Tree.Kind.IDENTIFIER) // Prevent false positive if whole query is passed
                && this.hasDynamicValueInArgument(tree.arguments().get(0)))
        {
            context.reportIssue(this, tree, "Review use of dynamic variables in your flexible search query string");
        }
    }

    @Override
    public void onConstructorFound(final NewClassTree newClassTree)
    {
        if (!newClassTree.arguments().isEmpty())
        {
            ExpressionTree query = newClassTree.arguments().get(0);
            if (!query.is(Tree.Kind.IDENTIFIER) && hasDynamicValueInArgument(query))
            {
                this.reportIssue(newClassTree, "Review use of dynamic variables in your flexible search query string");
            }
        }
    }

    private boolean hasDynamicValueInArgument(final ExpressionTree tree)
    {
        if (tree.is(Tree.Kind.PLUS))
        {
            return hasDynamicValueInExpression((BinaryExpressionTree) tree);
        }

        return isDynamicIdentifier(tree) || isDynamicMethodInvocation(tree);
    }

    private boolean hasDynamicValueInExpression(final BinaryExpressionTree tree)
    {
        return hasDynamicValueInArgument(tree.rightOperand()) || hasDynamicValueInArgument(tree.leftOperand());
    }

    private boolean isDynamicMethodInvocation(final ExpressionTree tree)
    {
        return tree.is(Tree.Kind.METHOD_INVOCATION) && hasDynamicArguments((MethodInvocationTree) tree);
    }

    private boolean hasDynamicArguments(final MethodInvocationTree tree)
    {
        return tree.arguments().stream() //
                .anyMatch(this::isDynamicIdentifier);
    }

    private boolean isDynamicIdentifier(final ExpressionTree tree)
    {
        return tree.is(Tree.Kind.IDENTIFIER) && !tree.symbolType().symbol().isStatic();
    }
}
