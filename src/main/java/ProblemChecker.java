import java.util.ArrayList;
import java.util.List;

public class ProblemChecker {
    Problem problem;
    List<Draw> studentAttempt;
    List<Draw> teacherSolution;

    public ProblemChecker(Problem p){
        problem = p;
        studentAttempt = problem.getStudentAttempt();
        teacherSolution = problem.getTeacherSolution();
    }


    //steps to checking problem
    //check if
    public Draw CheckProblem(){
        List<Draw> differences = CheckDifferentBlocks();
        List<Block> blockdiffs = new ArrayList<>();
        List<Arrow> Arrowdiffs = new ArrayList<>();
        for (Draw d : differences){
            if(d instanceof Block){
                blockdiffs.add((Block)d);
            }
            else{
                Arrowdiffs.add((Arrow)d);
            }
        }
        if (blockdiffs.size() != 0){
            return blockdiffs.get(0);
        }
        else if (Arrowdiffs.size() != 0){
            return Arrowdiffs.get(0);
        }
        else{
            return new EndBlock(0,0, "no diffs");
        }
    }



    public List<Draw> CheckDifferentBlocks(){

        if (studentAttempt.size() < teacherSolution.size()){
            List<Draw> diffs = new ArrayList<>(teacherSolution);
            diffs.removeAll(studentAttempt);
            return diffs;


        }else if (studentAttempt.size() > teacherSolution.size()){
            List<Draw> diffs = new ArrayList<>(studentAttempt);
            diffs.removeAll(teacherSolution);
            return diffs;
        }
        else{
            List<Draw> teacherdiffs = new ArrayList<>(teacherSolution);
            teacherdiffs.removeAll(studentAttempt);


            List<Draw> studentdiffs = new ArrayList<>(studentAttempt);
            studentdiffs.removeAll(teacherSolution);

            if (studentdiffs.size() == 0){
                return teacherdiffs;
            }
            return studentdiffs;

        }
    }
}
