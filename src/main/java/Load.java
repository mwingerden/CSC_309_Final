
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
 * Main.Load class that handles the loading of files and blocks.
 */
public class Load {
    /**
     * load method will try to load a file of a certain name.
     * @param name, file name
     * @return drawingsList
     */
    @SuppressWarnings("unchecked")
    public static Problem load(String name) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("Drawings/" + name + ".json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray fileElements = (JSONArray) obj;

            String description = ((JSONObject) fileElements.get(0)).get("Problem Description").toString();
            List<Draw> teacherDrawings = parseDrawingArray((JSONArray)
                            ((JSONObject) fileElements.get(1))
                            .get("Teacher Solution"));
            List<Draw> studentAttempt = parseDrawingArray((JSONArray)
                    ((JSONObject) fileElements.get(2))
                            .get("Student Attempt"));
            List<String> hints = (List<String>) ((JSONObject) fileElements.get(0)).get("Hints");

            return new Problem(name, description, teacherDrawings, studentAttempt, hints);

        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(
                    new WorkSpace(),
                    "There is no problem by the name of " + name + ".",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Draw> parseDrawingArray(JSONArray drawingElements) {
        List<Draw> blocks = new ArrayList<>();
        drawingElements.forEach(drawObject -> blocks.add(getDrawObject((JSONObject) drawObject)));
        return blocks;
    }

    private static Draw getDrawObject(JSONObject block) {
        JSONObject drawingObject = (JSONObject) block.get("CodeBlock");
        if (drawingObject != null) {
            return loadCodeBlock(drawingObject);
        }
        JSONArray drawingObjects = (JSONArray) block.get("Main.Arrow");
        if (drawingObjects != null) {
            return loadArrow(drawingObjects);
        }
        return null;
    }

    /**
     * loadCodeBlock method returns the different blocks and loads them into a list.
     * @param codeBlock, type of block
     * @return drawing
     */
    private static Draw loadCodeBlock(JSONObject codeBlock) {
        Block drawing = null;
        if (codeBlock.get("Name").equals("Main.CallMethodBlock")) {
            drawing = new CallMethodBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("Main.ConditionBlock")) {
            drawing = new ConditionBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("Main.EndBlock")) {
            drawing = new EndBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")), "PINK");
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("Main.InputOutputBlock")) {
            drawing = new InputOutputBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("Main.InstructionBlock")) {
            drawing = new InstructionBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("Main.StartBlock")) {
            drawing = new StartBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")),"BLUE");
            drawing.setText((String) codeBlock.get("Text"));
        } else if (codeBlock.get("Name").equals("Main.VariableDeclarationBlock")) {
            drawing = new VariableDeclarationBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
            drawing.setText((String) codeBlock.get("Text"));
        }
        return drawing;
    }
    /**
     * loadArrow method adds the arrow between blocks and returns needed arrow for each.
     * @param arrow, loaded arrow
     * @return arrowFinal
     */
    private static Draw loadArrow(JSONArray arrow) {
        Arrow arrowFinal;
        ArrayList<Block> codeBlocks = new ArrayList<>();
        for (Object o : arrow) {
            JSONObject temp = (JSONObject) o;
            codeBlocks.add((Block)loadCodeBlock((JSONObject) temp.get("CodeBlock")));
        }
        arrowFinal = new Arrow(codeBlocks.get(0), codeBlocks.get(1));
        return arrowFinal;
    }
}
