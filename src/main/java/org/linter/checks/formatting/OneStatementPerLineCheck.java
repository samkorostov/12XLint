package org.linter.checks.formatting;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.*;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.*;

public class OneStatementPerLineCheck extends Check {
    private final static String ERROR_MESSAGE = "Multiple statements per line";

    /**
     * Checks for multiple statements on a single line, excluding conditional/loops.
     * @param cu The AST (Abstract Syntax Tree) created by parsing a file using
     *                        javaparser
     * @return A List of all violations of this check, or nothing
     */
    @Override
    public Optional<List<Violation>> apply(CompilationUnit cu) {
       Map<Integer, Integer> statementsPerLine = new HashMap<>();
       cu.findAll(Statement.class).forEach(statement -> {
           if (isLoopOrConditionalStatement(statement)) {
               return;
           }
           statement.getRange().ifPresent(range -> {
               int line = range.begin.line;
               statementsPerLine.put(line, statementsPerLine.getOrDefault(line, 0) + 1);
           });
       });

       List<Violation> violations = new ArrayList<>();
       for (Integer line : statementsPerLine.keySet()) {
           if (statementsPerLine.get(line) > 1) {
               violations.add(new Violation(ERROR_MESSAGE, line));
           }
       }
       return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
    }

    /**
     * Helper method to ensure that loop and/or conditional statements do not get flagged
     * as multiple statements. For example, for (int i = 0; i < 3; i++) should only count
     * as a single statement, whereas for (int i = 0; i < 3; i++) x++; should count as two.
     * @param statement The statement to check.
     * @return true if this statement is within a loop/conditional, false if not.
     */
    private boolean isLoopOrConditionalStatement(Statement statement) {
        return statement.getParentNode()
                .filter(parent -> parent instanceof IfStmt || parent instanceof ForStmt ||
                                  parent instanceof SwitchStmt ||parent instanceof WhileStmt)
                .isPresent();
    }
}
