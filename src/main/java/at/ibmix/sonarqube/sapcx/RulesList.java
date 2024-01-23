package at.ibmix.sonarqube.sapcx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.sonar.plugins.java.api.JavaCheck;

import at.ibmix.sonarqube.sapcx.checks.AvoidChangingSessionRule;
import at.ibmix.sonarqube.sapcx.checks.AvoidDirectDBAccessRule;
import at.ibmix.sonarqube.sapcx.checks.AvoidModelConstructorRule;
import at.ibmix.sonarqube.sapcx.checks.AvoidSearchRestrictionDisableRule;
import at.ibmix.sonarqube.sapcx.checks.AvoidUnsafeQueryParametersRule;
import at.ibmix.sonarqube.sapcx.checks.DoNotUpdateDatabaseInPopulatorRule;
import at.ibmix.sonarqube.sapcx.checks.DoNotUseModelsInControllersRule;

public final class RulesList {

    private RulesList() {
    }

    public static List<Class<? extends JavaCheck>> getChecks() {
        List<Class<? extends JavaCheck>> checks = new ArrayList<>();
        checks.addAll(getJavaChecks());
        checks.addAll(getJavaTestChecks());
        return Collections.unmodifiableList(checks);
    }

    /**
     * These rules are going to target MAIN code only
     */
    public static List<Class<? extends JavaCheck>> getJavaChecks() {
        return Collections.unmodifiableList(Arrays.asList(
                AvoidChangingSessionRule.class,
                AvoidSearchRestrictionDisableRule.class,
                AvoidModelConstructorRule.class,
                AvoidDirectDBAccessRule.class,
                AvoidUnsafeQueryParametersRule.class,
                DoNotUseModelsInControllersRule.class,
                DoNotUpdateDatabaseInPopulatorRule.class));
    }

    /**
     * These rules are going to target TEST code only
     */
    public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
        return Collections.unmodifiableList(Arrays.asList());
    }
}