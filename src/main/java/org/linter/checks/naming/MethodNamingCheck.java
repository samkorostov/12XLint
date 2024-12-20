package org.linter.checks.naming;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MethodNamingCheck implements Check {
    private static final String CAMEL_CASE_REGEX = "^[a-z][a-zA-Z0-9]*$";
    private static final String ERROR_MESSAGE = "Naming: Method declarations must follow camelCasing";

    /**
     * Checks all method declarations to ensure they follow the correct naming
     * conventions.
     * @param cu The AST (Abstract Syntax Tree) created by parsing a file using
     *                        javaparser
     * @return A List of all violations of this check, or none.
     */
    @Override
    public Optional<List<Violation>> apply(CompilationUnit cu) {
        List<Violation> violations = new ArrayList<>();
        cu.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
                String methodName = methodDeclaration.getNameAsString();
                if (!methodName.matches(CAMEL_CASE_REGEX)) {
                    methodDeclaration.getName().getRange().ifPresent(range ->
                            violations.add(new Violation(ERROR_MESSAGE, range.begin.line)));
                }
        });
        return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
    }
}
