package checks.formatting;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;
import org.linter.checks.formatting.LongLineCheck;
import org.linter.core.Check;
import org.linter.core.Violation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class LongLineCheckTests {

    @Test
    public void basicFunctionalityTest() throws IOException {
        Path path = Paths.get("src/test/java/checks/TestSingleLongLine.java");
        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(path).getResult();
        assertTrue(cu.isPresent());
        Check longLineCheck = new LongLineCheck();
        Optional<List<Violation>> violations = longLineCheck.apply(cu.get());
        assertTrue(violations.isPresent());
        List<Violation> violationList = violations.get();
        Violation violation = violationList.get(0);
        assertEquals(violation.getMessage(), "Long Line");
        assertEquals(violation.getLineNumber(), 5);
    }

    @Test
    public void multipleLongLineTest() throws IOException {
        Path path = Paths.get("src/test/java/checks/TestMultipleLongLine.java");
        JavaParser parser = new JavaParser();
        Optional<CompilationUnit> cu = parser.parse(path).getResult();
        assertTrue(cu.isPresent());
        Check longLineCheck = new LongLineCheck();
        Optional<List<Violation>> violations = longLineCheck.apply(cu.get());
        assertTrue(violations.isPresent());
        List<Violation> violationList = violations.get();
        assertTrue(violationList.size() == 3);
        Violation violation = violationList.get(0);
        assertEquals(violation.getMessage(), "Long Line");
        assertEquals(violation.getLineNumber(), 5);
        violation = violationList.get(1);
        assertEquals(violation.getMessage(), "Long Line");
        assertEquals(violation.getLineNumber(), 7);
        violation = violationList.get(2);
        assertEquals(violation.getMessage(), "Long Line");
        assertEquals(violation.getLineNumber(), 9);

    }
}
