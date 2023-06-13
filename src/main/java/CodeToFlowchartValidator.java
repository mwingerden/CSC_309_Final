import java.util.*;

public class CodeToFlowchartValidator {

    private final Problem loadedProblem;
    private final List<Draw> studentDrawing;
    private final List<Draw> teacherSolution;

    private List<String> errors = new ArrayList<>();

    public CodeToFlowchartValidator(List<Draw> studentDrawing, Problem loadedProblem) {
        this.loadedProblem = loadedProblem;
        this.studentDrawing = studentDrawing;
        this.teacherSolution = loadedProblem.getTeacherSolution();
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public boolean checkAgainstSolution() {
        if (studentDrawing.stream().filter(Block.class::isInstance).toList().size() ==
                teacherSolution.stream().filter(Block.class::isInstance).toList().size() && solved()) {
            return true;
        } else {
            getDifferingCounts();
            if (this.errors.isEmpty()) {
                getDifferingFactors();
            }
            return false;
        }
    }

    private boolean solved() {
        for (Draw drawing : studentDrawing) {
            if (!teacherSolution.contains(drawing)) {
                return false;
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
                } else if (matchedName.get().arrowInCount != block.arrowInCount ||
                            matchedName.get().arrowOutCount != block.arrowOutCount) {
                    this.errors.add(String.format("Check that the %s block named \"%s\" has the correct " +
                                "amount of arrows.", block.getClass(), block.getBlockText()));
                }
            } else if (drawing instanceof Arrow arrow && !teacherSolution.contains(arrow)) {
                Block arrowBlock1 = arrow.getBlock1();
                Block arrowBlock2 = arrow.getBlock2();
                this.errors.add(String.format("Check arrow going between %s block named %s and %s block named %s.",
                        arrowBlock1.getClass(), arrowBlock1.getBlockText(),
                        arrowBlock2.getClass(), arrowBlock2.getBlockText()));
            }
        }
    }

}
