package org.linter.core;
/**
 * This class represents a code quality violation. It contains a message describing what
 * of the code quality guidelines have been violated, as well as the line number of the
 * violation.
 */
public class Violation {
    private final String message;
    private final int lineNumber;

    /**
     * Constructs a new violation
     * @param message   A message detailing what code quality guideline has been violated.
     * @param lineNumber    The line in which this violation occured.
     *
     */
    public Violation(String message, int lineNumber) {
        this.message = message;
        this.lineNumber = lineNumber;
    }

    public String getMessage() {
        return message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return message + " at line " + lineNumber;
    }
}
