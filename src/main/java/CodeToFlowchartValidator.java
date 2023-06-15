import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class used to validate that a student's drawing is the same as the teacher's solution.
 */
public class CodeToFlowchartValidator {

    private final Problem loadedProblem;
    private final List<Draw> studentDrawing;
    private final List<Draw> teacherSolution;

    private List<String> errors = new ArrayList<>();

    /**
     * Constructor method that takes in the student's current drawing list and the problem that it is to be compared to.
     * @param studentDrawing List of Draw objects that the student has made
     * @param loadedProblem The problem that the student drawing is supposed to be compared to.
     */
    public CodeToFlowchartValidator(List<Draw> studentDrawing, Problem loadedProblem) {
        this.loadedProblem = loadedProblem;
        this.studentDrawing = studentDrawing;
        this.teacherSolution = loadedProblem.getTeacherSolution();
    }

    /**
     * Get the list of errors found with the student's solution.
     * @return list of strings containing the errors found in the student solution.
     */
    public List<String> getErrors() {
        return this.errors;
    }

    /**
     * Method that checks the student solution against the teacher's solution. If there are the correct amount of blocks
     * and arrows present but there is an issue with the arrow count or name of a block it will turn that block orange.
     * @return true, if and only if, every draw object present in the student drawing is equal to another draw object in
     * the teacher solution.
     */
    public boolean checkAgainstSolution() {
        if (studentDrawing.stream().filter(Block.class::isInstance).toList().size() ==
                teacherSolution.stream().filter(Block.class::isInstance).toList().size() && solved()) {
            Repository.getInstance().setDrawnChart(studentDrawing);
            return true;
        } else {
            getDifferingCounts();
            if (this.errors.isEmpty()) {
                getDifferingFactors();
            }
            Repository.getInstance().setDrawnChart(studentDrawing);
            return false;
        }
    }

    private boolean solved() {
        for (Draw drawing : studentDrawing) {
            if (!teacherSolution.contains(drawing)) {
                return false;
            }
            if (drawing instanceof Block block && !(block instanceof EndBlock)) {
                block.setColor(Color.white);
            }
        }
        return true;
    }

    private void getDifferingCounts() {
        List<Integer> studentBlockCount = loadedProblem.getBlockTypeCount(studentDrawing);
        List<Integer> teacherBlockCount = loadedProblem.getBlockTypeCount(teacherSolution);

        this.errors = new ArrayList<>();
        List<String> types = new ArrayList<>(List.of("Call Method",
                "Condition",
                "Input/Output",
                "Instruction",
                "Variable Declaration"));

        for (int index = 0; index < 5; index++) {
            if (studentBlockCount.get(index) > teacherBlockCount.get(index)) {
                this.errors.add(String.format("There are too many %s blocks in your solution.",
                        types.get(index)));
            } else if (studentBlockCount.get(index) < teacherBlockCount.get(index)) {
                this.errors.add(String.format("There are not enough %s blocks in your solution.",
                        types.get(index)));
            }
        }
    }

    private void getDifferingFactors() {
        this.errors = new ArrayList<>();

        for (Draw drawing : studentDrawing) {
            if (drawing instanceof Block block && !teacherSolution.contains(block)) {
                Optional<Block> matchedName = teacherSolution.stream()
                        .filter(Block.class::isInstance)
                        .map(Block.class::cast)
                        .filter(block.getClass()::isInstance)
                        .filter(tBlock -> tBlock.getBlockText().equals(block.getBlockText()))
                        .findFirst();
                if (matchedName.isEmpty()) {
                    this.errors.add(String.format("Check that the %s block named \"%s\" is named correctly.",
                            block.getClass(), block.getBlockText()));
                    block.setColor(Color.ORANGE);
                } else if (matchedName.get().arrowInCount != block.arrowInCount ||
                            matchedName.get().arrowOutCount != block.arrowOutCount) {
                    this.errors.add(String.format("Check that the %s block named \"%s\" has the correct " +
                                "amount of arrows.", block.getClass(), block.getBlockText()));
                    block.setColor(Color.ORANGE);
                }
            } else if (drawing instanceof Arrow arrow && !teacherSolution.contains(arrow)) {
                Block arrowBlock1 = arrow.getSourceShape();
                Block arrowBlock2 = arrow.getDestinationShape();
                this.errors.add(String.format("Check arrow going between %s block named %s and %s block named %s.",
                        arrowBlock1.getClass(), arrowBlock1.getBlockText(),
                        arrowBlock2.getClass(), arrowBlock2.getBlockText()));
            }
        }
    }

}
