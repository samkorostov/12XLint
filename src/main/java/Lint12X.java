import org.linter.core.Linter;
import org.linter.core.Violation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/// Usage: ./12XLint 12XLint.<version>.jar <file>.
/// Example:
///      java -jar 12XLint.1.0-SNAPSHOT.jar /path/to/MyClass.java"
public class Lint12X {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java -jar 12XLint.<version>.jar <file>");
            System.out.println("Example:");
            System.out.println("    java -jar 12XLint.1.0-SNAPSHOT.jar /path/to/MyClass.java");
            return;
        }
        Linter linter = new Linter();
        for (String arg : args) {
            Path filePath = Paths.get(arg);
            Optional<List<Violation>> violationList = linter.lint(filePath);
            System.out.println(filePath.getFileName() + ": ");
            if (violationList.isPresent()) {
                violationList.get().sort(Comparator.comparingInt(Violation::getLineNumber));
                for (Violation violation : violationList.get()) {
                    System.out.println("\t" + violation);
                }
                System.out.println();
            } else {
                System.out.println("No violations found");
            }
        }
    }
}
