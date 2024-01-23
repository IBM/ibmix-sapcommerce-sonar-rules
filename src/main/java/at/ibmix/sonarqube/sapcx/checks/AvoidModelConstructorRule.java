package at.ibmix.sonarqube.sapcx.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.NewClassTree;

@Rule(key = "AvoidModelConstructorRule")
public class AvoidModelConstructorRule extends AbstractMethodDetection
{
    private static final String CLASS_NAME = "de.hybris.platform.core.model.ItemModel";

    @Override
    protected MethodMatchers getMethodInvocationMatchers()
    {
        return MethodMatchers.create().ofSubTypes(CLASS_NAME).constructor().withAnyParameters().build();
    }

    @Override
    public void onConstructorFound(final NewClassTree newClassTree)
    {
        this.reportIssue(newClassTree, "Entity models have to be created using modelService.create()");
    }
}
