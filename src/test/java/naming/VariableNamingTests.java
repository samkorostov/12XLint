package naming;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;
import org.linter.checks.naming.VariableNamingCheck;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VariableNamingTests {

    @Test
    public void testBasicFunctionality() {
        String code = """
                public class Main {
                    public static void main(String[] args) {
                        int A = 5;
                    }
                }
                """;
        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(code).getResult();
        assertTrue(cu.isPresent());
        Check check = new VariableNamingCheck();
        Optional<List<Violation>> violationList = check.apply(cu.get());
        assertTrue(violationList.isPresent());

        List<Violation> violations = violationList.get();
        assertTrue(violations.size() == 1);
        assertEquals(3, violations.get(0).getLineNumber());
        assertEquals("Naming: Variables must follow camelCasing", violations.get(0).getMessage());
    }
}
