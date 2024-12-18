package naming;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;
import org.linter.checks.naming.ClassNamingCheck;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ClassNamingCheckTests {

    @Test
    public void testBasicFunctionality() {
        String code = """
               public class i_like_to_code {
                   public static void main(String[] args) {
                       System.out.println("ASDASDAD");
                   }
               }
               """;
        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(code).getResult();
        assertTrue(cu.isPresent());
        Check check = new ClassNamingCheck();
        Optional<List<Violation>> violationList = check.apply(cu.get());
        assertTrue(violationList.isPresent());
        List<Violation> violations = violationList.get();
        assertTrue(violations.size() > 0);
        assertEquals(1, violations.get(0).getLineNumber());
        assertEquals("Naming: Class declarations must follow UpperCamelCase", violations.get(0).getMessage());
    }
}
