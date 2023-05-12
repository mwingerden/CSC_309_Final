
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 * The Main.Save class stores the user's saved files
 */
public class Save {

    /**
     * Saves all elements of a problem to a file in the Drawings directory.
     *
     * @param problemToSave Problem object containing needed elements for a problem file.
     * @throws IOException if an issue occurs creating or opening file
     */
    @SuppressWarnings("unchecked")
    public static void save(Problem problemToSave) throws IOException {

        JSONArray fileElements = new JSONArray();

        JSONObject problemDescription = new JSONObject();
        problemDescription.put("Problem Description", problemToSave.getProblemDescription());
        fileElements.add(problemDescription);

        JSONObject teacherDrawing = new JSONObject();
        teacherDrawing.put("Teacher Solution", saveDrawingList(problemToSave.getTeacherSolution()));
        fileElements.add(teacherDrawing);

        JSONObject studentDrawing = new JSONObject();
        studentDrawing.put("Student Attempt", saveDrawingList(problemToSave.getStudentAttempt()));
        fileElements.add(studentDrawing);

        JSONObject problemHints = new JSONObject();
        problemHints.put("Hints", problemToSave.getHints());
        fileElements.add(problemHints);

        try (FileWriter file = new FileWriter("Drawings/" + problemToSave.getProblemName() + ".json")) {
            file.write(fileElements.toJSONString());
            file.flush();
        } catch (FileNotFoundException e) {
            File newFile = new File("Drawings/" + problemToSave.getProblemName() + ".json");
            FileWriter file = new FileWriter(newFile);
            file.write(fileElements.toJSONString());
            file.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static JSONArray saveDrawingList(List<Draw> drawings) {
        JSONArray drawingArray = new JSONArray();
        JSONObject shape = new JSONObject();
        for (Draw drawing : drawings) {
            if (drawing instanceof Block block) {
                shape = storeCodeBlock(block);
            } else if (drawing instanceof Arrow) {
                shape = storeArrow(drawing);
            }
            drawingArray.add(shape);
        }
        return drawingArray;
    }

    /**
     * storeCodeBlock method will hold the blocks saved for the future
     * @param codeBlock, type of block to store
     * @return
     */
    @SuppressWarnings("unchecked")
    private static JSONObject storeCodeBlock(Block codeBlock) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectDetails = new JSONObject();
        jsonObjectDetails.put("X1", Integer.toString(codeBlock.getX1()));
        jsonObjectDetails.put("Y1", Integer.toString(codeBlock.getY1()));
        jsonObjectDetails.put("Text", codeBlock.getText());
        if (codeBlock instanceof CallMethodBlock) {
            jsonObjectDetails.put("Name", "Main.CallMethodBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof ConditionBlock) {
            jsonObjectDetails.put("Name", "Main.ConditionBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof EndBlock) {
            jsonObjectDetails.put("Name", "Main.EndBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof InputOutputBlock) {
            jsonObjectDetails.put("Name", "Main.InputOutputBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof InstructionBlock) {
            jsonObjectDetails.put("Name", "Main.InstructionBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof StartBlock) {
            jsonObjectDetails.put("Name", "Main.StartBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof VariableDeclarationBlock) {
            jsonObjectDetails.put("Name", "Main.VariableDeclarationBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        }
        return jsonObject;
    }
    /**
     * storeArrow method will hold the arrows
     * @param arrowDraw, arrows from blocks
     * @return jsonObject, arrows
     */
    @SuppressWarnings("unchecked")
    private static JSONObject storeArrow(Draw arrowDraw) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObjectDetails;
        Arrow arrow = (Arrow) arrowDraw;

        List<Block> codeBlocks = arrow.getCodeBlocks();
        for (Block codeBlock : codeBlocks) {
            jsonObjectDetails = storeCodeBlock(codeBlock);
            jsonArray.add(jsonObjectDetails);
        }
        jsonObject.put("Main.Arrow", jsonArray);
        return jsonObject;
    }
}
