package org.linter.checks.naming;

import com.github.javaparser.ast.CompilationUnit;
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
            String variableName = variable.getNameAsString();
            if (!variableName.matches(CAMEL_CASE)) {
                variable.getName().getRange().ifPresent(range ->
                        violations.add(new Violation(ERROR_MESSAGE, range.begin.line)));
            }
        });
        return violations.isEmpty() ? Optional.empty() : Optional.of(violations);

    }
}
