package org.linter.core;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.linter.checks.formatting.IndentationCheck;
import org.linter.checks.formatting.LongLineCheck;
import org.linter.checks.formatting.OneStatementPerLineCheck;
import org.linter.checks.naming.ClassNamingCheck;
import org.linter.checks.naming.ConstantNamingCheck;
import org.linter.checks.naming.MethodNamingCheck;
import org.linter.checks.naming.VariableNamingCheck;

/**
 * This class functions as a linter for CSE 12X Teaching assistants at University of Washington
 * The goal for this project is to create an easy way to detect code quality issues in student
 * submissions, improving grading accuracy and cutting down on teaching assistant workload.
 * A teaching assistant will be able to select which aspects of the code quality guideline they
 * want to check for in a given assignment, and this program will output a list of each violation
 * and the line where it occured at.
 */
public class Linter {

    private final Check[] checks = {new ConstantNamingCheck(),
                                    new ClassNamingCheck(),
                                    new MethodNamingCheck(),
                                    new VariableNamingCheck(),
                                    new OneStatementPerLineCheck(),
                                    new LongLineCheck(),
                                    new IndentationCheck()};
    private JavaParser parser;

    public Linter() {
        parser = new JavaParser();
    }

    /**
     * This method runs a determined number of checks against a certain file.
     * @param filepath The path to the file to be linted
     * @return A list of all the quality violations in a file if there are
     *         any, or an empty optional if not.
     */
    public Optional<List<Violation>> lint(Path filepath) {
        try {
            Optional<CompilationUnit> compilationUnit = parser.parse(filepath).getResult();
            if (compilationUnit.isEmpty()) {
                return Optional.empty();
            }
            List<Violation> errors = new ArrayList<>();
            for (Check check : checks) {
                Optional<List<Violation>> checkResults = check.apply(compilationUnit.get());
                checkResults.ifPresent(errors::addAll);
            }
            return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
