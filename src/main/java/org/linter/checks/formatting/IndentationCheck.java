package org.linter.checks.formatting;

import com.github.javaparser.ast.CompilationUnit;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.List;
import java.util.Optional;

public class IndentationCheck extends Check {

    /**
     * This method checks for if lines in a given file match the 12X guidelines for indentation.
     * The guidelines are as follows: every time a curly brace is opened, indentation must increase
     * by a tab, and every time a curly brace is closed, indentation must decrease by a tab.
     * @param compilationUnit The compilation unit for the file to be linted produced by JavaParser
     * @return A List of all indentation violations that occur in this file, or an empty optional
     * if there are none.
     */
    @Override
    public Optional<List<Violation>> apply(CompilationUnit compilationUnit) {
        return Optional.empty();
        // TODO: Implement basic functionality, then add indentation block detection.
    }
}
