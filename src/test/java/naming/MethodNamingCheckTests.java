package naming;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;
import org.linter.checks.naming.MethodNamingCheck;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MethodNamingCheckTests {
    @Test
    public void testBasicFunctioanlity() {
        String code = """
                public class Main {
                    public static void main(String[] args) {
                        int A = 5;
                    }
                    
                    public static int ASDASDASD() {
                        return 5;
                    }
                }
                """;
        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(code).getResult();
        assertTrue(cu.isPresent());
        Check check = new MethodNamingCheck();
        Optional<List<Violation>> violationList = check.apply(cu.get());
        assertTrue(violationList.isPresent());

        List<Violation> violations = violationList.get();
        assertTrue(violations.size() == 1);
        assertEquals(6, violations.get(0).getLineNumber());
        assertEquals("Naming: Method declarations must follow camelCasing", violations.get(0).getMessage());
    }
}
