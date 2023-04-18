import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 * The Save class stores the user's saved files
 */
public class Save {
    /**
     * save method saves the file and its components
     * @param drawings, blocks and arrows to save
     * @param name, file name
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static void save(List<Draw> drawings, String name) throws IOException {
        JSONArray drawingsList = new JSONArray();
        JSONObject jsonObject = null;
        for (Draw drawing : drawings) {
            if (drawing instanceof Block) {
                jsonObject = storeCodeBlock((Block)drawing);
            }
             else if (drawing instanceof Arrow) {
               jsonObject = storeArrow(drawing);
          }
            drawingsList.add(jsonObject);
        }
        try (FileWriter file = new FileWriter("DrawingJSONFiles/" + name + ".json")) {
            file.write(drawingsList.toJSONString());
            file.flush();
        } catch (FileNotFoundException e) {
            File newFile = new File("DrawingJSONFiles/" + name + ".json");
            FileWriter file = new FileWriter(newFile);
            file.write(drawingsList.toJSONString());
            file.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
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
            jsonObjectDetails.put("Name", "CallMethodBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof ConditionBlock) {
            jsonObjectDetails.put("Name", "ConditionBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof EndBlock) {
            jsonObjectDetails.put("Name", "EndBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof InputOutputBlock) {
            jsonObjectDetails.put("Name", "InputOutputBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof InstructionBlock) {
            jsonObjectDetails.put("Name", "InstructionBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof StartBlock) {
            jsonObjectDetails.put("Name", "StartBlock");
            jsonObject.put("CodeBlock", jsonObjectDetails);
        } else if (codeBlock instanceof VariableDeclarationBlock) {
            jsonObjectDetails.put("Name", "VariableDeclarationBlock");
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
        jsonObject.put("Arrow", jsonArray);
        return jsonObject;
    }
}
