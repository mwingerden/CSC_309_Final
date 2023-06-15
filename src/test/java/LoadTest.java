import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadTest {
    @Test
    public void TestSave() {
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

        Problem solution = new Problem("TestSave1", "testing save", teacher, student, hints);
        Problem testProblem = Load.loadTest("TestSave1");

        assertEquals(solution, testProblem);
    }
}
