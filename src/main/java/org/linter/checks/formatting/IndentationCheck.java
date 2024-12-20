package org.linter.checks.formatting;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.BlockStmt;

public class IndentationCheck implements Check {
    private static final int EXPECTED_INDENT = 4;
    private static final String ERROR_MESSAGE = "Inconsistent Indentation";

    /**
     * This method checks for if lines in a given file match the 12X guidelines for indentation.
     * The guidelines are as follows: every time a curly brace is opened, indentation must increase
     * by a tab, and every time a curly brace is closed, indentation must decrease by a tab.
     * @param cu The compilation unit for the file to be linted produced by JavaParser
     * @return A List of all indentation violations that occur in this file, or an empty optional
     * if there are none.
     */
    @Override
    public Optional<List<Violation>> apply(CompilationUnit cu) {
        List<Violation> violations = new ArrayList<>();
        cu.findAll(BlockStmt.class).forEach(stmt -> {
            int parentStartColumn = stmt.getParentNode()
                    .flatMap(parent -> parent.getRange().map(range -> range.begin.column))
                    .orElse(0);
            stmt.getChildNodes().forEach(child -> {
                child.getRange().ifPresent(range -> {
                    int lineNumber = range.begin.line;
                    int columnNumber = range.begin.column;
                    if (parentStartColumn + EXPECTED_INDENT != columnNumber) {
                        violations.add(new Violation(ERROR_MESSAGE, lineNumber));
                    }
                });
            });
        });
        return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
    }
}
