package org.linter.checks.structure;

import com.github.javaparser.ast.CompilationUnit;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.List;
import java.util.Optional;

public class MemberOrderingCheck implements Check {
    @Override
    public Optional<List<Violation>> apply(CompilationUnit compilationUnit) {
        return Optional.empty();
    }
}
