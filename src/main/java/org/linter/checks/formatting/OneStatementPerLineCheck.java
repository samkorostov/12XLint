package org.linter.checks.formatting;

import com.github.javaparser.ast.CompilationUnit;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.List;
import java.util.Optional;

public class OneStatementPerLineCheck extends Check {

    /**
     * This method checks for multiple statements existing on one line. For example,
     * the line System.out.println(); i++; would be flagged.
     * @param compilationUnit
     * @return
     */
    @Override
    public Optional<List<Violation>> apply(CompilationUnit compilationUnit) {
        return Optional.empty();
        // TODO: Implement
    }
}
