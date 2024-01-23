package at.ibmix.sonarqube.sapcx.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.Assert;

public class FilesUtils
{

    private FilesUtils()
    {
    }

    /**
     * Default location of the jars/zips to be taken into account when performing the analysis.
     */
    private static final String DEFAULT_TEST_JARS_DIRECTORY = "target/test-jars";

    public static List<File> getClassPath(String jarsDirectory)
    {
        List<File> classpath = new LinkedList<>();
        Path testJars = Paths.get(jarsDirectory);
        if (testJars.toFile().exists())
        {
            classpath = getFilesRecursively(testJars, "jar", "zip");
        }
        else if (!DEFAULT_TEST_JARS_DIRECTORY.equals(jarsDirectory))
        {
            throw new AssertionError("The directory to be used to extend class path does not exists (" + testJars.toAbsolutePath() + ").");
        }
        classpath.add(new File("target/test-classes"));
        return classpath;
    }

    public static List<File> getSAPDependencyJARs(String... jars)
    {
        List<File> classpath = new LinkedList<>();
        String hybrisHome = System.getProperty("hybrisHome");
        Assert.isNotNull("Please make sure 'hybrisHome' environment variable is set", hybrisHome);
        for (String path : jars)
        {
            File jar = new File(hybrisHome, path);
            if (jar.exists())
            {
                classpath.add(jar);
            }
            else
            {
                throw new AssertionError("File not found: " + hybrisHome + path);
            }
        }
        return classpath;
    }

    public static File getSAPDependencyClassFolder(final String folder)
    {
        String hybrisHome = System.getProperty("hybrisHome");
        Assert.isNotNull("Please make sure 'hybrisHome' environment variable is set", hybrisHome);
        Path classpath = Paths.get(hybrisHome, folder);

        if (!classpath.toFile().exists())
        {
            throw new AssertionError("Class folder not found: " + classpath);
        }

        return classpath.toFile();
    }

    private static List<File> getFilesRecursively(Path root, String... extensions)
    {
        final List<File> files = new ArrayList<>();

        FileVisitor<Path> visitor = new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs)
            {
                for (String extension : extensions)
                {
                    if (filePath.toString().endsWith("." + extension))
                    {
                        files.add(filePath.toFile());
                        break;
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
            {
                return FileVisitResult.CONTINUE;
            }
        };

        try
        {
            Files.walkFileTree(root, visitor);
        }
        catch (IOException e)
        {
            // we already ignore errors in the visitor
        }

        return files;
    }
}