package at.ibmix.sonarqube.sapcx.checks;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

import at.ibmix.sonarqube.sapcx.utils.FilesUtils;
import at.ibmix.sonarqube.sapcx.utils.TestConstants;

class DoNotUseModelsInControllersRuleTest
{
    @Test
    void testController()
    {
        List<File> classpath = FilesUtils.getClassPath("target/test-jars");
        classpath.addAll(FilesUtils.getSAPDependencyJARs(TestConstants.CORESERVER_JAR, TestConstants.MODELS_JAR));

        CheckVerifier.newVerifier() //
                .onFile("src/test/files/DoNotUseModelsInControllersRule.java") //
                .withCheck(new DoNotUseModelsInControllersRule()) //
                .withClassPath(classpath) //
                .verifyIssues();
    }

    @Test
    void testRestController()
    {
        List<File> classpath = FilesUtils.getClassPath("target/test-jars");
        classpath.addAll(FilesUtils.getSAPDependencyJARs(TestConstants.CORESERVER_JAR, TestConstants.MODELS_JAR));

        CheckVerifier.newVerifier() //
                .onFile("src/test/files/DoNotUseModelsInControllersRule2.java") //
                .withCheck(new DoNotUseModelsInControllersRule()) //
                .withClassPath(classpath) //
                .verifyIssues();
    }
}