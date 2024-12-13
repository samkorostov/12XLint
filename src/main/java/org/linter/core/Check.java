package org.linter.core;
import com.github.javaparser.ast.CompilationUnit;

import java.util.List;
import java.util.Optional;
import com.github.javaparser.ast.CompilationUnit;
public abstract class Check {

    /**
     * Applies a given check to a file
     * @return a List of all the instances of a given check, or nothing
     */
    public abstract Optional<List<Violation>> apply(CompilationUnit compilationUnit);
}
