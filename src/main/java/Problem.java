import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class Problem {

    private final String problemName;

    private String problemDescription;

    private List<Draw> teacherSolution;

    private List<Draw> studentAttempt;

    private List<String> hints;

    private String progress;

    private String feedback;

    /**
     * Creates Problem object based off of loaded file.
     * @param problemName Name of the problem
     * @param problemDescription String containing what the student is aiming to create a flowchart of.
     * @param teacherSolution Draw object list of the solution
     * @param studentAttempt Draw object list of student attempt (maybe null if not yet attempted)
     * @param hints List of Strings containing hints for student to use
     */
    public Problem(String problemName,
            String problemDescription,
            List<Draw> teacherSolution,
            List<Draw> studentAttempt,
            List<String> hints) {
        this.problemName = problemName;
        this.problemDescription = problemDescription;
        this.teacherSolution = teacherSolution;
        this.studentAttempt = studentAttempt;
        this.hints = hints;
        progress = "";
        feedback = "";
    }

    public void setProgress(String s){
        progress = s;
    }
    public String getProgress(){
        return progress;
    }

    public  void setFeedback(String s){
        feedback = s;
    }
    public String getFeedback(){
        return feedback;
    }

    public String getProblemName() {
        return this.problemName;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public List<Draw> getStudentAttempt() {
        return studentAttempt;
    }

    public List<Draw> getTeacherSolution() {
        return teacherSolution;
    }

    public List<String> getHints() {
        return hints;
    }

    public void autoGenHints() {
        this.hints.removeIf(String::isBlank);
        int[] countOfBlocks = {0, 0, 0, 0, 0};
        for (Draw drawing : Repository.getInstance().getDrawings()) {
            if (drawing instanceof Block block) {
                block.autoGenHints();
            }
            if (drawing instanceof CallMethodBlock) {
                countOfBlocks[0]++;
            } else if (drawing instanceof ConditionBlock) {
                countOfBlocks[1]++;
            } else if (drawing instanceof InputOutputBlock) {
                countOfBlocks[2]++;
            } else if (drawing instanceof InstructionBlock) {
                countOfBlocks[3]++;
            } else if (drawing instanceof VariableDeclarationBlock) {
                countOfBlocks[4]++;
            }
        }
        List<String> types = new ArrayList<>(List.of("Call Method",
                "Condition",
                "Input/Output",
                "Instruction",
                "Variable Declaration"));
        for (int typeIndex = 0; typeIndex < 5; typeIndex++) {
            int countForSelectedType = countOfBlocks[typeIndex];
            String presentHint = String.format("There is 1 or more %s blocks in this flowchart.",
                    types.get(typeIndex));
            this.hints.removeIf(hintString -> hintString.matches(presentHint) && countForSelectedType == 0);
            if (countForSelectedType > 0 && this.hints.stream().noneMatch(hint -> hint.matches(presentHint))) {
                this.hints.add(presentHint);
            }

            String regexMatch = String.format("There (are \\d+|is 1) %s block(s)? in this flowchart.",
                    types.get(typeIndex));
            Optional<String> oldHint = this.hints.stream()
                    .filter(hintString ->
                            hintString.matches(regexMatch))
                    .findFirst();
            String newHint = String.format("There are %d %s blocks in this flowchart.",
                    countForSelectedType, types.get(typeIndex));
            if (countForSelectedType == 1) {
                newHint = String.format("There is 1 %s block in this flowchart.", types.get(typeIndex));
            }
            if (oldHint.isEmpty()) {
                this.hints.add(newHint);
            } else {
                this.hints.set(this.hints.indexOf(oldHint.get()), newHint);
            }
        }
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public void setDrawing(boolean solution, List<Draw> drawing) {
        if (solution) {
            this.teacherSolution = drawing;
        } else {
            this.studentAttempt = drawing;
        }
    }

    public void setHints(List<String> hints) {
        hints.removeIf(String::isBlank);
        this.hints = hints;
    }
}
