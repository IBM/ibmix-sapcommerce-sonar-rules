package at.ibmix.sonarqube.sapcx.checks;

import java.util.Arrays;
import java.util.List;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.MethodMatchers;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodReferenceTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;

public abstract class AbstractMethodDetection extends IssuableSubscriptionVisitor
{
    private MethodMatchers matchers;

    public List<Tree.Kind> nodesToVisit()
    {
        return Arrays.asList(Tree.Kind.METHOD_INVOCATION, Tree.Kind.NEW_CLASS, Tree.Kind.METHOD_REFERENCE);
    }

    @Override
    public void visitNode(Tree tree)
    {
        if (tree.is(Tree.Kind.METHOD_INVOCATION))
        {
            MethodInvocationTree mit = (MethodInvocationTree) tree;
            if (this.matchers().matches(mit))
            {
                this.onMethodInvocationFound(mit);
            }
        }
        else if (tree.is(Tree.Kind.NEW_CLASS))
        {
            NewClassTree newClassTree = (NewClassTree) tree;
            if (this.matchers().matches(newClassTree))
            {
                this.onConstructorFound(newClassTree);
            }
        }
        else if (tree.is(Tree.Kind.METHOD_REFERENCE))
        {
            MethodReferenceTree methodReferenceTree = (MethodReferenceTree) tree;
            if (this.matchers().matches(methodReferenceTree))
            {
                this.onMethodReferenceFound(methodReferenceTree);
            }
        }

    }

    protected abstract MethodMatchers getMethodInvocationMatchers();

    protected void onMethodInvocationFound(MethodInvocationTree mit)
    {
    }

    protected void onConstructorFound(NewClassTree newClassTree)
    {
    }

    protected void onMethodReferenceFound(MethodReferenceTree methodReferenceTree)
    {
    }

    private MethodMatchers matchers()
    {
        if (this.matchers == null)
        {
            this.matchers = this.getMethodInvocationMatchers();
        }

        return this.matchers;
    }
}
