package org.linter.core;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

/**
 * This class functions as a linter for CSE 12X Teaching assistants at University of Washington
 * The goal for this project is to create an easy way to detect code quality issues in student
 * submissions, improving grading accuracy and cutting down on teaching assistant workload.
 * A teaching assistant will be able to select which aspects of the code quality guideline they
 * want to check for in a given assignment, and this program will output a list of each violation
 * and the line where it occured at.
 */
public class Linter {

    private final List<Check> checks;

    /**
     * This will be a linter that lints according to CSE12X code quality guidelines
     * TODO: Implement selective check adding so quality checks can be adjusted
     */
    public Linter() {
        checks = new ArrayList<>();
        // TODO: Implement a way to select which checks to be performed? Maybe a config file?
    }

    /**
     * This method runs a determined number of checks against a certain file.
     * @param filepath The path to the file to be linted
     * @return A list of all the quality violations in a file if there are
     *         any, or an empty optional if not.
     */
    public Optional<List<Violation>> lint(Path filepath) {
        try {
            JavaParser parser = new JavaParser();
            Optional<CompilationUnit> compilationUnit = parser.parse(filepath).getResult();
            if (compilationUnit.isEmpty()) {
                return Optional.empty();
            }
            List<Violation> errors = new ArrayList<>();
            for (Check check : checks) {
                Optional<List<Violation>> checkResults = check.apply(compilationUnit.get());
                if (checkResults.isPresent()) {
                    errors.addAll(checkResults.get());
                }
            }
            return errors.isEmpty() ? Optional.empty() : Optional.of(errors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
