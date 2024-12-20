package org.linter.checks.naming;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConstantNamingCheck implements Check {
    private static final String SCREAMING_CASE_REGEX = "^[A-Z][A-Z0-9_]*$";
    private static final String ERROR_MESSAGE = "Naming: Class constants must follow SCREAMING_CASE";

    /**
     * Checks all class constant declarations to make sure they follow the correct
     * naming conventions. Class constants are denoted with static and final.
     * @param cu The AST (Abstract Syntax Tree) created by parsing a file using
     *                        javaparser
     * @return A List of all violations of this check, or none.
     */
    @Override
    public Optional<List<Violation>> apply(CompilationUnit cu) {
        List<Violation> violations = new ArrayList<>();
        cu.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
            if (fieldDeclaration.isStatic() && fieldDeclaration.isFinal()) {
                fieldDeclaration.getVariables().forEach(variable -> {
                    String constantName = variable.getNameAsString();
                    if (!constantName.matches(SCREAMING_CASE_REGEX)) {
                        variable.getName().getRange().ifPresent(range ->
                                violations.add(new Violation(ERROR_MESSAGE, range.begin.line)));
                    }
                });
            }
        });
        return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
    }
}
