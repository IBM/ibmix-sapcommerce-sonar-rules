package at.ibmix.sonarqube.sapcx.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = "AvoidDirectDBAccessRule")
public class AvoidDirectDBAccessRule extends AbstractMethodDetection
{
    private static final String CLASS_NAME = "de.hybris.platform.jdbcwrapper.HybrisDataSource";

    private static final String METHOD_NAME = "getConnection";

    @Override
    protected MethodMatchers getMethodInvocationMatchers()
    {
        return MethodMatchers.create().ofSubTypes(CLASS_NAME).names(METHOD_NAME).withAnyParameters().build();
    }

    @Override
    public void onMethodInvocationFound(final MethodInvocationTree tree)
    {
        this.reportIssue(tree, "Do not access DB directly");
    }
}
