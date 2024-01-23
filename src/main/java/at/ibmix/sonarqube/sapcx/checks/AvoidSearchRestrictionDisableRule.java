package at.ibmix.sonarqube.sapcx.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "AvoidSearchRestrictionDisableRule")
public class AvoidSearchRestrictionDisableRule extends AbstractMethodDetection
{
    private static final String QUERY_CLASS = "de.hybris.platform.servicelayer.search.AbstractQuery";

    private static final String QUERY_METHOD = "setDisableSearchRestrictions";

    private static final String QUERY_PARAM_TYPE = "boolean";

    private static final String CLASS_NAME = "de.hybris.platform.search.restriction.SearchRestrictionService";

    private static final String METHOD_NAME = "disableSearchRestrictions";

    @Override
    protected MethodMatchers getMethodInvocationMatchers()
    {
        return MethodMatchers.or(MethodMatchers.create().ofSubTypes(CLASS_NAME).names(METHOD_NAME).withAnyParameters().build(), //
                MethodMatchers.create().ofSubTypes(QUERY_CLASS).names(QUERY_METHOD).addParametersMatcher(QUERY_PARAM_TYPE).build());
    }

    @Override
    public void onMethodInvocationFound(final MethodInvocationTree tree)
    {
        if (tree.methodSymbol().name().equals(METHOD_NAME) || this.isSetToTrue(tree))
        {
            this.reportIssue(tree, "Review disable of SAP Commerce search restrictions");
        }
    }

    private boolean isSetToTrue(final MethodInvocationTree tree)
    {
        return tree.arguments().get(0).is(Tree.Kind.BOOLEAN_LITERAL) //
                && tree.arguments().get(0).firstToken().text().equals("true");
    }
}
