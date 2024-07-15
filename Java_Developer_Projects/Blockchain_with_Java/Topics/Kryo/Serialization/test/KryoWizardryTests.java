import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;

public class KryoWizardryTests extends StageTest<Object> {

    CheckResult test(String name, String isEvil, String filename) {
        TestedProgram program = new TestedProgram();
        program.start();
        program.execute(name);
        program.execute(isEvil);
        var output = program.execute(filename);

        var lines = expect(output).toContain().lines();
        var solved = lines.stream().anyMatch("Two wizards look the same"::equals);

        try {
            var path = Path.of(filename);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException ignored) {
        }

        if (!solved) {
            return CheckResult.wrong("Deserialization failed to produce the same Wizard object");
        }
        return CheckResult.correct();
    }

    @DynamicTest
    DynamicTesting[] dt = new DynamicTesting[]{
            () -> test("Lord Voldemort", "true", "wizard1.bin"),
            () -> test("Harry Potter", "false", "wizard2.bin"),
            () -> test("Kuramor", "true", "wizard3.bin"),
            () -> test("Gululun", "false", "wizard4.bin")
    };
}
