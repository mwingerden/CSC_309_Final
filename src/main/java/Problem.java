import java.util.List;

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
        this.hints = hints;
    }
}
