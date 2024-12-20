package checks.formatting;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.linter.checks.formatting.OneStatementPerLineCheck;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OneStatementPerLineCheckTests {
    @Test
    @DisplayName("Checks if this check functions correctly fora single line")
    public void testBasicFunctionality() {
        String code = """
                public class Main {
                    public static void main(String[] args) {
                        System.out.println(); i++;
                    }
                }""";

        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(code).getResult();
        assertTrue(cu.isPresent());

        Check check = new OneStatementPerLineCheck();
        Optional<List<Violation>> violationList = check.apply(cu.get());
        assertTrue(violationList.isPresent());

        List<Violation> violations = violationList.get();
        assertTrue(violations.size() == 1);
        assertEquals(3, violations.get(0).getLineNumber());
        assertEquals("Multiple statements per line", violations.get(0).getMessage());
    }

    @Test
    @DisplayName("Loop/conditional declarations and bodies shoudln't count as multiple")
    public void testLoopAndConditionalStatements() {
        String code = """
                public class Main {
                    public static void main(String[] args) {
                        System.out.println("AA"); int x = 3;
                        for (int i = 0; i < 10; i++) {
                            System.out.println(i);
                        }
                    }
                }""";
        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(code).getResult();
        assertTrue(cu.isPresent());
        Check check = new OneStatementPerLineCheck();
        Optional<List<Violation>> violationList = check.apply(cu.get());
        assertTrue(violationList.isPresent());
        List<Violation> violations = violationList.get();
        assertTrue(violations.size() == 1);
        assertEquals(3, violations.get(0).getLineNumber());
        assertEquals("Multiple statements per line", violations.get(0).getMessage());
    }
}
