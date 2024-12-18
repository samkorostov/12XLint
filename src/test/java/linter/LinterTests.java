package linter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.jupiter.api.Test;
import org.linter.checks.formatting.IndentationCheck;
import org.linter.checks.formatting.LongLineCheck;
import org.linter.checks.formatting.OneStatementPerLineCheck;
import org.linter.checks.naming.ClassNamingCheck;
import org.linter.checks.naming.ConstantNamingCheck;
import org.linter.checks.naming.MethodNamingCheck;
import org.linter.checks.naming.VariableNamingCheck;
import org.linter.core.Check;
import org.linter.core.Linter;
import org.linter.core.Violation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LinterTests {
    @Test
    public void multipleFormattingAndNamingErrorTest() throws IOException {
        Path path = Paths.get("linter/multipleformattingandnamingerrors.java");
        List<Check> checks  = new ArrayList<>();
        checks.add(new LongLineCheck());
        checks.add(new IndentationCheck());
        checks.add(new OneStatementPerLineCheck());
        checks.add(new VariableNamingCheck());
        checks.add(new MethodNamingCheck());
        checks.add(new ConstantNamingCheck());
        checks.add(new ClassNamingCheck());


        Linter linter = new Linter(checks);
        Optional<List<Violation>> violationList = linter.lint(path);
        assertTrue(violationList.isPresent());
        List<Violation> violations = violationList.get();

    }
}
