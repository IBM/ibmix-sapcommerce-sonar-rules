package at.ibmix.sonarqube.sapcx.checks;

import java.util.Optional;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "DoNotUseModelsInControllersRule")
public class DoNotUseModelsInControllersRule extends AbstractMethodDetection
{
    private static final String CONTROLLER_ANNOTATION = "org.springframework.stereotype.Controller";

    private static final String REST_CONTROLLER_ANNOTATION = "org.springframework.web.bind.annotation.RestController";

    private static final String CLASS_NAME = "de.hybris.platform.core.model.ItemModel";

    private static final String ITERABLE_CLASS_NAME = "java.lang.Iterable";

    @Override
    protected MethodMatchers getMethodInvocationMatchers()
    {
        return MethodMatchers.create().ofAnyType().anyName().withAnyParameters().build();
    }

    @Override
    public void onMethodInvocationFound(final MethodInvocationTree tree)
    {
        if (isInController(tree) && returnsEntityModel(tree))
        {
            reportIssue(tree, "Do not use entity models in controllers");
        }
    }

    private boolean isInController(final MethodInvocationTree methodInvocationTree)
    {
        Optional<ClassTree> tree = findClassUsedIn(methodInvocationTree);
        if (tree.isPresent())
        {
            return tree.get().modifiers().stream() //
                    .filter(mod -> mod.is(Tree.Kind.ANNOTATION)) //
                    .anyMatch(mod -> isController((AnnotationTree) mod));
        }
        return false;
    }

    private boolean isController(final AnnotationTree annotationTree)
    {
        return annotationTree.symbolType().isSubtypeOf(CONTROLLER_ANNOTATION)
                || annotationTree.symbolType().isSubtypeOf(REST_CONTROLLER_ANNOTATION);
    }

    private Optional<ClassTree> findClassUsedIn(final Tree tree)
    {
        if (tree.parent() == null)
        {
            return Optional.empty();
        }
        return Tree.Kind.CLASS.equals(tree.parent().kind()) ? Optional.of((ClassTree) tree.parent()) : findClassUsedIn(tree.parent());
    }

    private boolean returnsEntityModel(final MethodInvocationTree methodInvocationTree)
    {
        Type type = methodInvocationTree.methodSymbol().returnType().type();
        return type.isSubtypeOf(CLASS_NAME) || (type.isSubtypeOf(ITERABLE_CLASS_NAME) && type.typeArguments().stream().anyMatch(arg -> arg.isSubtypeOf(CLASS_NAME)));
    }
}
