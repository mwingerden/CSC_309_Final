import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Save {
    @SuppressWarnings("unchecked")
    public static void save(List<Draw> drawings, String name) {
        JSONArray drawingsList = new JSONArray();
        JSONObject jsonObject = null;
        for (Draw drawing : drawings) {
            if (drawing instanceof Block) {
                jsonObject = storeCodeBlock(drawing);
            }
            //TODO: Uncomment when arrow class is created.
//            } else if (drawing instanceof Arrow) {
//                jsonObject = storeArrow(drawing);
//            }
            drawingsList.add(jsonObject);
        }

        try (FileWriter file = new FileWriter("./DrawingJSONFiles/" + name + ".json")) {
            file.write(drawingsList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static JSONObject storeCodeBlock(Draw codeBlock) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectDetails = new JSONObject();
        //TODO: Uncomment when the block classes are created.
//        jsonObjectDetails.put("X1", Integer.toString(codeBlock.getX1()));
//        jsonObjectDetails.put("Y1", Integer.toString(codeBlock.getY1()));
//        jsonObjectDetails.put("Text", codeBlock.getText());
//        if (codeBlock instanceof CallMethodBlock) {
//            jsonObjectDetails.put("Name", "CallMethodBlock");
//            jsonObject.put("CodeBlock", jsonObjectDetails);
//        } else if (codeBlock instanceof ConditionBlock) {
//            jsonObjectDetails.put("Name", "ConditionBlock");
//            jsonObject.put("CodeBlock", jsonObjectDetails);
//        } else if (codeBlock instanceof EndBlock) {
//            jsonObjectDetails.put("Name", "EndBlock");
//            jsonObject.put("CodeBlock", jsonObjectDetails);
//        } else if (codeBlock instanceof InputOutputBlock) {
//            jsonObjectDetails.put("Name", "InputOutputBlock");
//            jsonObject.put("CodeBlock", jsonObjectDetails);
//        } else if (codeBlock instanceof InstructionBlock) {
//            jsonObjectDetails.put("Name", "InstructionBlock");
//            jsonObject.put("CodeBlock", jsonObjectDetails);
//        } else if (codeBlock instanceof StartBlock) {
//            jsonObjectDetails.put("Name", "StartBlock");
//            jsonObject.put("CodeBlock", jsonObjectDetails);
//        } else if (codeBlock instanceof VariableDeclarationBlock) {
//            jsonObjectDetails.put("Name", "VariableDeclarationBlock");
//            jsonObject.put("CodeBlock", jsonObjectDetails);
//        }
        return jsonObject;
    }

    @SuppressWarnings("unchecked")
    private static JSONObject storeArrow(Draw arrowDraw) {
        //TODO: Uncomment when arrow class is created.
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObjectDetails;
//        Arrow arrow = (Arrow) arrowDraw;
//
//        List<CodeBlock> codeBlocks = arrow.getCodeBlocks();
//        for (CodeBlock codeBlock : codeBlocks) {
//            jsonObjectDetails = storeCodeBlock(codeBlock);
//            jsonArray.add(jsonObjectDetails);
//        }
//        jsonObject.put("Arrow", jsonArray);
//        return jsonObject;
        return null;
    }
}
