package org.linter.checks.naming;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassNamingCheck implements Check {
    private static final String UPPER_CAMEL_CASE_REGEX = "^[A-Z][a-zA-Z0-9]*$";
    private static final String ERROR_MESSAGE = "Naming: Class declarations must follow UpperCamelCase";

    /**
     * Checks all class declarations in a file to make sure they follow the correct
     * naming conventions.
     * @param cu The AST (Abstract Syntax Tree) created by parsing a file using
     *                        javaparser
     * @return A List of all violations of this check, or none
     */
    @Override
    public Optional<List<Violation>> apply(CompilationUnit cu) {
        List<Violation> violations = new ArrayList<>();
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {
            String className = classDeclaration.getNameAsString();
            if (!className.matches(UPPER_CAMEL_CASE_REGEX)) {
                classDeclaration.getName().getRange().ifPresent(range ->
                        violations.add(new Violation(ERROR_MESSAGE, range.begin.line)));
            }
        });
        return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
    }
}
