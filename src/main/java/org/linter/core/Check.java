package org.linter.core;
import com.github.javaparser.ast.CompilationUnit;
import java.util.List;
import java.util.Optional;
// TODO: Maybe not really the greatest use of OOP here tbh, could be an interface?
public abstract class Check {
    /**
     * Applies a given check to a file
     * @param cu The AST (Abstract Syntax Tree) created by parsing a file using
     *                        javaparser
     * @return a List of all the instances of a given check, or nothing
     */
    public abstract Optional<List<Violation>> apply(CompilationUnit cu);
}
