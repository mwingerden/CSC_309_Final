import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SaveTest {
    @Test
    public void TestSave() throws IOException {
        List<Draw> teacher = new ArrayList<>();
        List<Draw> student = new ArrayList<>();
        List<String> hints = new ArrayList<>();

        Block block1 = new StartBlock(10, 10);
        Block block2 = new CallMethodBlock(10, 10);
        Block block3 = new EndBlock(10, 10);

        teacher.add(block1);
        teacher.add(block2);
        teacher.add(block3);
        teacher.add(new Arrow(block1, block2));
        teacher.add(new Arrow(block2, block3));
        student.add(block1);
        student.add(block2);
        student.add(block3);
        hints.add("etgwf");
        hints.add("ewfwefgw");
        hints.add("etthedthefhdhgwf");

        Save.saveTest(new Problem("Testing", "testing save", "incomplete", "no feedback", teacher, student, hints));

        Assertions.assertTrue(filesCompare());
    }

    public boolean filesCompare() throws IOException {
        try (BufferedReader bf1 = Files.newBufferedReader(Path.of("./TestDrawings/Testing.json"));
             BufferedReader bf2 = Files.newBufferedReader(Path.of("./TestDrawings/TestSave1.json"))) {

            String line1, line2;
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (!line1.equals(line2)) {
                    return false;
                }
            }
            return true;
        }
    }
}
