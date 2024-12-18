package org.linter.checks.naming;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassNamingCheck extends Check {
    private static String UPPER_CAMEL_CASE ="^[A-Z][a-zA-Z0-9]*$";
    private static String ERROR_MESSAGE = "Naming: Class declarations must follow UpperCamelCase";
    @Override
    public Optional<List<Violation>> apply(CompilationUnit cu) {
        List<Violation> violations = new ArrayList<>();
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {
            String className = classDeclaration.getNameAsString();
            if (!className.matches(UPPER_CAMEL_CASE)) {
                classDeclaration.getName().getRange().ifPresent(range ->
                        violations.add(new Violation(ERROR_MESSAGE, range.begin.line)));
            }
        });
        return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
    };
}
