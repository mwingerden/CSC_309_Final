
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

/**
 * Main.Load class that handles the loading of files and blocks.
 */
public class Load {
    private static final List<Draw> drawingsList = new ArrayList<>();
    /**
     * load method will try to load a file of a certain name.
     * @param name, file name
     * @return drawingsList
     */
    @SuppressWarnings("unchecked")
    public static List<Draw> load(String name) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("Drawings/" + name + ".json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray drawings = (JSONArray) obj;
            drawings.forEach(drawing -> parseDrawingObject((JSONObject) drawing));
        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(
                    new WorkSpace(),
                    "There is no problem by the name of " + name + ".",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
        return drawingsList;
    }

    public static ArrayList<String> get_names(){
        ArrayList<String> names = new ArrayList<>();

        File drawings = new File("Drawings");

        for (File f : drawings.listFiles()){
            names.add(f.getName().replaceAll("\\.\\w+",""));
        }

        return names;
    }

    /**
     * parseDrawingObject method adds arrows or blocks to list if needed.
     * @param drawing, can be either block or arrow
     */
    private static void parseDrawingObject(JSONObject drawing) {
        String description = (String) drawing.get("Problem Description");
        if (description != null) {
            Repository.getInstance().saveProblemDescription(description);
            return;
        }

        JSONObject drawingObject = (JSONObject) drawing.get("CodeBlock");
        if (drawingObject != null) {
            drawingsList.add(loadCodeBlock(drawingObject));
            return;
        }
        JSONArray drawingObjects = (JSONArray) drawing.get("Main.Arrow");
        if (drawingObjects != null) {
            drawingsList.add(loadArrow(drawingObjects));
        }
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
        ArrayList<Block> CodeBlocks = new ArrayList<>();
        for (Object o : arrow) {
            JSONObject temp = (JSONObject) o;
            CodeBlocks.add((Block)loadCodeBlock((JSONObject) temp.get("CodeBlock")));
        }
        arrowFinal = new Arrow(CodeBlocks.get(0), CodeBlocks.get(1));
        return arrowFinal;
    }
}
