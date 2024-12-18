package org.linter.checks.naming;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConstantNamingCheck extends Check {
    private static final String SCREAMING_CASE = "^[A-Z][A-Z0-9_]*$";
    private static final String ERROR_MESSAGE = "Naming: Class constants must follow SCREAMING_CASE";
    @Override
    public Optional<List<Violation>> apply(CompilationUnit cu) {
        List<Violation> violations = new ArrayList<>();
        cu.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
            if (fieldDeclaration.isStatic() && fieldDeclaration.isFinal()) {
                fieldDeclaration.getVariables().forEach(variable -> {
                    String constantName = variable.getNameAsString();
                    if (!constantName.matches(SCREAMING_CASE)) {
                        variable.getName().getRange().ifPresent(range ->
                                violations.add(new Violation(ERROR_MESSAGE, range.begin.line)));
                    }
                });
            }
        });
        return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
    }
}
