package at.ibmix.sonarqube.sapcx.checks;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

import at.ibmix.sonarqube.sapcx.utils.FilesUtils;
import at.ibmix.sonarqube.sapcx.utils.TestConstants;

class AvoidDirectDBAccessRuleTest
{
    @Test
    void test()
    {
        List<File> classpath = FilesUtils.getClassPath("target/test-jars");
        classpath.addAll(FilesUtils.getSAPDependencyJARs(TestConstants.CORESERVER_JAR, TestConstants.MODELS_JAR));

        CheckVerifier.newVerifier() //
                .onFile("src/test/files/AvoidDirectDBAccessRule.java") //
                .withCheck(new AvoidDirectDBAccessRule()) //
                .withClassPath(classpath) //
                .verifyIssues();
    }
}