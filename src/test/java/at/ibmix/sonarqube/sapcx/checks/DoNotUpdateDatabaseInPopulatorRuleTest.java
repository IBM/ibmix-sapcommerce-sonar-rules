package at.ibmix.sonarqube.sapcx.checks;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

import at.ibmix.sonarqube.sapcx.utils.FilesUtils;
import at.ibmix.sonarqube.sapcx.utils.TestConstants;

class DoNotUpdateDatabaseInPopulatorRuleTest
{
    @Test
    void test()
    {
        List<File> classpath = FilesUtils.getClassPath("target/test-jars");
        classpath.addAll(FilesUtils.getSAPDependencyJARs(TestConstants.CORESERVER_JAR, TestConstants.MODELS_JAR));
        classpath.add(FilesUtils.getSAPDependencyClassFolder(TestConstants.PLATFORM_SERVICES_CLASSPATH));

        CheckVerifier.newVerifier() //
                .onFile("src/test/files/DoNotUpdateDatabaseInPopulatorRule.java") //
                .withCheck(new DoNotUpdateDatabaseInPopulatorRule()) //
                .withClassPath(classpath) //
                .verifyIssues();
    }
}