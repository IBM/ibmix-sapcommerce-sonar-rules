package at.ibmix.sonarqube.sapcx.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

@Rule(key = "AvoidChangingSessionRule")
public class AvoidChangingSessionRule extends AbstractMethodDetection
{
    private static final String PARAM_TYPE_1 = "de.hybris.platform.servicelayer.session.SessionExecutionBody";

    private static final String PARAM_TYPE_2 = "de.hybris.platform.core.model.user.UserModel";

    private static final String CLASS_NAME = "de.hybris.platform.servicelayer.session.SessionService";

    private static final String METHOD_NAME = "executeInLocalView";

    @Override
    protected MethodMatchers getMethodInvocationMatchers()
    {
        return MethodMatchers.create().ofSubTypes(CLASS_NAME) //
                .names(METHOD_NAME) //
                .addParametersMatcher(PARAM_TYPE_1, PARAM_TYPE_2).build();
    }

    @Override
    public void onMethodInvocationFound(final MethodInvocationTree tree)
    {
        this.reportIssue(tree, "Make sure that changing SAP Commerce user context is safe here");
    }
}
