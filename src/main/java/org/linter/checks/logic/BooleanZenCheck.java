package org.linter.checks.logic;

import com.github.javaparser.ast.CompilationUnit;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.List;
import java.util.Optional;

public class BooleanZenCheck extends Check {
    @Override
    public Optional<List<Violation>> apply(CompilationUnit compilationUnit) {
        return Optional.empty();
    }
}