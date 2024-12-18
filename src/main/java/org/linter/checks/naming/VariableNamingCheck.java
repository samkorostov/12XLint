package org.linter.checks.naming;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VariableNamingCheck extends Check {
    private static final String CAMEL_CASE = "^[a-z][a-zA-Z0-9]*$";
    private static final String ERROR_MESSAGE = "Naming: Variables must follow camelCasing";

    @Override
    public Optional<List<Violation>> apply(CompilationUnit cu) {
        List<Violation> violations = new ArrayList<>();
        cu.findAll(VariableDeclarator.class).forEach(variable -> {
            if (isConstant(variable)) {
                return;
            }
            String variableName = variable.getNameAsString();
            if (!variableName.matches(CAMEL_CASE)) {
                variable.getName().getRange().ifPresent(range ->
                        violations.add(new Violation(ERROR_MESSAGE, range.begin.line)));
            }
        });

        return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
    }

    /**
     * Helper method to ensure that we aren't accidentally linting a class constant variable,
     * as those follow a different pattern
     * @param variable The given variable we want to ensure is not a constant
     * @return
     */
    private boolean isConstant(VariableDeclarator variable) {
        Node parent = variable.getParentNode().orElse(null);
        if (parent == null || !(parent instanceof FieldDeclaration)) {
            return false;
        }

        FieldDeclaration field = (FieldDeclaration) parent;
        return field.isStatic() && field.isFinal();
    }
}
