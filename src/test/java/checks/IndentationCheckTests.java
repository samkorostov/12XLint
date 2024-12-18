package checks;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.linter.checks.formatting.IndentationCheck;
import org.linter.core.Check;
import org.linter.core.Violation;
import java.util.List;
import java.util.Optional;


public class IndentationCheckTests {
    @Test
    public void basicFunctionalityTest() {
        String code = """
                      public class Code {
                          public static void main(String args[]) {
                              for (int i = 0; i < 5; i++ ) {
                              System.out.println("I'm an error");
                              }
                          }
                      }
                      """;
        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(code).getResult();
        assertTrue(cu.isPresent());
        Check indents = new IndentationCheck();
        Optional<List<Violation>> violationList = indents.apply(cu.get());
        assertTrue(violationList.isPresent());
        List<Violation> violations = violationList.get();
        assertEquals(1, violations.size());
        assertEquals(violations.get(0).getMessage(), "Inconsistent Indentation");
        assertEquals(violations.get(0).getLineNumber(), 4);

    }

    @Test
    public void diverseIndentationCasesTest() {
        String code = """
        public class DiverseCode {
            public static void main(String[] args) {
                if (true) {
                    System.out.println("Properly indented");
                   System.out.println("Misaligned"); // Inconsistent indentation
                }

                while (false) {
                    System.out.println("Properly indented again");
                }

                for (int i = 0; i < 5; i++) {
                      System.out.println("This is misaligned"); // Too far indented
                }

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        System.out.println("Nested and fine");
                     System.out.println("Nested but misaligned"); // Inconsistent indentation
                    }
                }
            }
        }
        """;

        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(code).getResult();
        assertTrue(cu.isPresent());
        Check indents = new IndentationCheck();
        Optional<List<Violation>> violationList = indents.apply(cu.get());
        assertTrue(violationList.isPresent());

        List<Violation> violations = violationList.get();

        assertEquals(3, violations.size());

        assertEquals("Inconsistent Indentation", violations.get(0).getMessage());
        assertEquals(5, violations.get(0).getLineNumber());

        assertEquals("Inconsistent Indentation", violations.get(1).getMessage());
        assertEquals(13, violations.get(1).getLineNumber());

        assertEquals("Inconsistent Indentation", violations.get(2).getMessage());
        assertEquals(19, violations.get(2).getLineNumber());
    }

    // TODO: Implement functionality so that each block of indentation only counts as 1 error message
    @Disabled
    @Test
    public void entireBlockIncorrectlyIndented() {
        String code = """
                public class Main {
                    public static void main(String[] args) {
                        for (int i = 0; i < 5; i++) {
                        System.out.println("This is misaligned"); // Too far indented
                        System.out.println(" I am incorrectly indented but consistent indentation");
                        System.out.println("We should only have 1 violation for this!");
                        }
                    }
                }""";
        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(code).getResult();
        assertTrue(cu.isPresent());
        Check indents = new IndentationCheck();
        Optional<List<Violation>> violationList = indents.apply(cu.get());
        assertTrue(violationList.isPresent());
        List<Violation> violations = violationList.get();
        assertEquals(3, violations.size());
        assertEquals( "Inconsistent Indentation", violations.get(0).getMessage());
        assertEquals(4, violations.get(0).getLineNumber());

    }


}
