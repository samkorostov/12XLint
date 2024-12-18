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
     * This method checks for multiple statements existing on one line. For example,
     * the line System.out.println(); i++; would be flagged.
     * @param compilationUnit
     * @return
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

    private boolean isLoopOrConditionalStatement(Statement statement) {
        return statement.getParentNode()
                .filter(parent -> parent instanceof IfStmt || parent instanceof ForStmt ||
                                  parent instanceof SwitchStmt ||parent instanceof WhileStmt)
                .isPresent();
    }
}
