package at.ibmix.sonarqube.sapcx.checks;

import java.util.Optional;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "DoNotUpdateDatabaseInPopulatorRule")
public class DoNotUpdateDatabaseInPopulatorRule extends AbstractMethodDetection
{
    private static final String PARENT_CLASS_NAME = "de.hybris.platform.converters.Populator";

    private static final String CLASS_NAME = "de.hybris.platform.servicelayer.model.ModelService";

    private static final String[] METHOD_NAMES = { "save", "saveAll", "remove", "removeAll" };

    @Override
    protected MethodMatchers getMethodInvocationMatchers()
    {
        return MethodMatchers.create().ofSubTypes(CLASS_NAME).names(METHOD_NAMES).withAnyParameters().build();
    }

    @Override
    public void onMethodInvocationFound(final MethodInvocationTree tree)
    {
        if (isInPopulator(tree))
        {
            reportIssue(tree, "Do not save models in populator");
        }
    }

    private boolean isInPopulator(final MethodInvocationTree methodInvocationTree)
    {
        Optional<ClassTree> tree = findClassUsedIn(methodInvocationTree);
        if (tree.isPresent())
        {
            return tree.get().symbol().type().isSubtypeOf(PARENT_CLASS_NAME);
        }
        return false;
    }

    private Optional<ClassTree> findClassUsedIn(final Tree tree)
    {
        if (tree.parent() == null)
        {
            return Optional.empty();
        }
        return Tree.Kind.CLASS.equals(tree.parent().kind()) ? Optional.of((ClassTree) tree.parent()) : findClassUsedIn(tree.parent());
    }
}
