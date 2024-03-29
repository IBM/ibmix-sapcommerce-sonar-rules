/*
 * Copyright (C) 2012-2023 SonarSource SA - mailto:info AT sonarsource DOT com
 * This code is released under [MIT No Attribution](https://opensource.org/licenses/MIT-0) license.
 */

package at.ibmix.sonarqube.sapcx;

import java.util.List;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.api.sonarlint.SonarLintSide;

/**
 * Provide the "checks" (implementations of rules) classes that are going be executed during
 * source code analysis.
 *
 * This class is a batch extension by implementing the {@link CheckRegistrar} interface.
 */
@SonarLintSide
public class SAPCXFileCheckRegistrar implements CheckRegistrar
{

    /**
     * Register the classes that will be used to instantiate checks during analysis.
     */
    @Override
    public void register(RegistrarContext registrarContext)
    {
        // Call to registerClassesForRepository to associate the classes with the correct repository key
        registrarContext.registerClassesForRepository(SAPCXRulesDefinition.REPOSITORY_KEY, checkClasses(), testCheckClasses());
    }

    /**
     * Lists all the main checks provided by the plugin
     */
    public static List<Class<? extends JavaCheck>> checkClasses()
    {
        return RulesList.getJavaChecks();
    }

    /**
     * Lists all the test checks provided by the plugin
     */
    public static List<Class<? extends JavaCheck>> testCheckClasses()
    {
        return RulesList.getJavaTestChecks();
    }
}
