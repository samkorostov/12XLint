import org.linter.core.Linter;
import org.linter.core.Violation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Lint12X {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java Lint12X <file>");
            return;
        }
        Linter linter = new Linter();
        for (String arg : args) {
            Path filePath = Paths.get(arg);
            Optional<List<Violation>> violationList = linter.lint(filePath);
            System.out.println(arg + ": ");
            if (violationList.isPresent()) {
                Collections.sort(violationList.get(), (a, b) ->
                        Integer.compare(a.getLineNumber(), b.getLineNumber()));
                for (Violation violation : violationList.get()) {
                    System.out.println(violation);
                }
            } else {
                System.out.println("No violations found");
            }
        }
    }
}
