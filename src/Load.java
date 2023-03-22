import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Load class that handles the loading of files and blocks.
 */
public class Load {
    private static final List<Draw> drawingsList = new ArrayList<>();

    /**
     * load method will try to load a file of a certain name.
     * @param name
     * @return drawingsList
     */
    @SuppressWarnings("unchecked")
    public static List<Draw> load(String name) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("DrawingJSONFiles/" + name + ".json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray drawings = (JSONArray) obj;
            drawings.forEach(drawing -> parseDrawingObject((JSONObject) drawing));
        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(
                    new WorkSpace(),
                    "There is no file by the name of " + name + ".",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
        return drawingsList;
    }

    /**
     * parseDrawingObject method adds arrows or blocks to list if needed.
     * @param drawing
     */
    private static void parseDrawingObject(JSONObject drawing) {
        JSONObject drawingObject = (JSONObject) drawing.get("CodeBlock");
        if (drawingObject != null) {
            drawingsList.add(loadCodeBlock(drawingObject));
            return;
        }
        JSONArray drawingObjects = (JSONArray) drawing.get("Arrow");
        if (drawingObjects != null) {
            drawingsList.add(loadArrow(drawingObjects));
        }
    }

    /**
     * loadCodeBlock method returns the different blocks and loads them into a list.
     * @param codeBlock
     * @return drawing
     */
    private static Draw loadCodeBlock(JSONObject codeBlock) {
        Block drawing = null;

        if (codeBlock.get("Name").equals("CallMethodBlock")) {
            drawing = new CallMethodBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("ConditionBlock")) {
            drawing = new ConditionBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("EndBlock")) {
            drawing = new EndBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")), "PINK");
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("InputOutputBlock")) {
            drawing = new InputOutputBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("InstructionBlock")) {
            drawing = new InstructionBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("StartBlock")) {
            drawing = new StartBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")),"BLUE");
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("VariableDeclarationBlock")) {
            drawing = new VariableDeclarationBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        }
        return drawing;
    }

    /**
     * loadArrow method adds the arrow between blocks and returns needed arrow for each.
     * @param arrow
     * @return arrowFinal
     */
    private static Draw loadArrow(JSONArray arrow) {
        Arrow arrowFinal;
        ArrayList<Block> CodeBlocks = new ArrayList<>();
        for (Object o : arrow) {
            JSONObject temp = (JSONObject) o;
            CodeBlocks.add((Block)loadCodeBlock((JSONObject) temp.get("CodeBlock")));
        }
        arrowFinal = new Arrow(CodeBlocks.get(0), CodeBlocks.get(1));
        return arrowFinal;
    }
}
