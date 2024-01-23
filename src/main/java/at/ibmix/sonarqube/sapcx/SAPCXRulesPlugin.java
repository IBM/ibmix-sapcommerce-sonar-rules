/*
 * Copyright (C) 2012-2023 SonarSource SA - mailto:info AT sonarsource DOT com
 * This code is released under [MIT No Attribution](https://opensource.org/licenses/MIT-0) license.
 */

package at.ibmix.sonarqube.sapcx;

import org.sonar.api.Plugin;

public class SAPCXRulesPlugin implements Plugin
{

    @Override
    public void define(Context context)
    {
        // server extensions -> objects are instantiated during server startup
        context.addExtension(SAPCXRulesDefinition.class);

        // batch extensions -> objects are instantiated during code analysis
        context.addExtension(SAPCXFileCheckRegistrar.class);
    }

}
