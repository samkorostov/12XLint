package org.linter.checks.formatting;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;


public class LongLineCheck extends Check{
    private static final int MAX_LINE_LENGTH = 100;

    /**
     * This method is unique among the ones that extend the check class,
     * as it doesn't need any data from JavaParser, it only needs the raw
     * data provided by java.nio
     * @param compilationUnit The compilation unit created from JavaParser
     * @return A list of all long line violations (lines > 100) that occur in a given file
     */
    @Override
    public Optional<List<Violation>> apply(CompilationUnit compilationUnit) {
        try {
            Optional<CompilationUnit.Storage> storage = compilationUnit.getStorage();
            List<String> lines = new ArrayList<>();
            if (storage.isPresent()) {
                Path path = storage.get().getPath();
                lines = Files.readAllLines(path);
            } else {
                return Optional.empty();
            }
            List<Violation> violations = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.length() > MAX_LINE_LENGTH) {
                    violations.add(new Violation("Long line: " + line.length() + " > "
                            + MAX_LINE_LENGTH + " characters", i + 1));

                }
            }
            return violations.isEmpty() ? Optional.empty() : Optional.of(violations);
        } catch (IOException  e) {
            return Optional.empty();
        }
    }
}
