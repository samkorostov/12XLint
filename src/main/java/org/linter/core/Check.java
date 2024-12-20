package org.linter.core;
import com.github.javaparser.ast.CompilationUnit;
import java.util.List;
import java.util.Optional;

public interface Check {
    /**
     * Applies a given check to a file
     * @param cu The AST (Abstract Syntax Tree) created by parsing a file using
     *                        javaparser
     * @return a List of all the violations this check, or nothing
     */
    Optional<List<Violation>> apply(CompilationUnit cu);
}
