import java.util.*;

/**
 * Contains all the information from the drawing files and what is currently on screen.
 */
public class Problem {

    private String problemName;

    private String changedName = "";

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
            String problemProgress,
            String problemFeedback,
            List<Draw> teacherSolution,
            List<Draw> studentAttempt,
            List<String> hints) {
        this.problemName = problemName;
        this.problemDescription = problemDescription;
        this.teacherSolution = teacherSolution;
        this.studentAttempt = studentAttempt;
        this.hints = hints;
        progress = problemProgress;
        feedback = problemFeedback;
    }

    /**
     * Set the progress level of the problem
     * @param s progress level name
     */
    public void setProgress(String s){
        progress = s;
    }

    /**
     * Get the progress level of the problem
     * @return progress level name
     */
    public String getProgress(){
        return progress;
    }

    /**
     * Set the feedback of the problem
     * @param s progress level name
     */
    public void setFeedback(String s){
        feedback = s;
    }

    /**
     * Get the feedback of the problem
     * @return string of the feedback relating to this problem
     */
    public String getFeedback(){
        return feedback;
    }

    /**
     * Get the problem name (the associated name of the file)
     * @return String of the problem name
     */
    public String getProblemName() {
        return this.problemName;
    }

    /**
     * Get the problem description, also known as the code that the student needs to translate
     * @return String of the problem name
     */
    public String getProblemDescription() {
        return problemDescription;
    }

    /**
     * Get the list of drawing objects that the student has created
     * @return list of drawing objects that the student has created
     */
    public List<Draw> getStudentAttempt() {
        return studentAttempt;
    }

    /**
     * Get the list of drawing objects that the teacher has created
     * @return list of drawing objects that the teacher has created
     */
    public List<Draw> getTeacherSolution() {
        return teacherSolution;
    }

    /**
     *
     * @return
     */
    public List<String> getHints() {
        return hints;
    }

    public List<Integer> getBlockTypeCount(List<Draw> drawingToCount) {
        int[] countOfBlocks = {0, 0, 0, 0, 0};
        for (Draw drawing : drawingToCount) {
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
        return Arrays.stream(countOfBlocks).boxed().toList();
    }

    public void autoGenHints() {
        this.hints.removeIf(String::isBlank);
        for (Draw drawing : Repository.getInstance().getDrawings()) {
            if (drawing instanceof Block block) {
                block.autoGenHints();
            }
        }
        List<Integer> blockCounts = getBlockTypeCount(Repository.getInstance().getDrawings());
        List<String> types = new ArrayList<>(List.of("Call Method",
                "Condition",
                "Input/Output",
                "Instruction",
                "Variable Declaration"));
        for (int typeIndex = 0; typeIndex < 5; typeIndex++) {
            int countForSelectedType = blockCounts.get(typeIndex);
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

    public boolean nameChanged() {
        return (!changedName.equals(""));
    }

    public void updateName() {
        if (!changedName.equals("")) {
            this.problemName = this.changedName;
            this.changedName = "";
        }
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public void setNewProblemName(String newProblemName) {
        this.changedName = newProblemName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Problem problem)) return false;
        return this.problemName.equals(problem.problemName) &&
                this.problemDescription.equals(problem.problemDescription) &&
                this.teacherSolution.equals(problem.teacherSolution) &&
                this.studentAttempt.equals(problem.studentAttempt) &&
                this.hints.equals(problem.hints);
    }
}
