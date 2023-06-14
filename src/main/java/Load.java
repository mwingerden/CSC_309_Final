
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Load class that handles the loading of files and blocks.
 */
public class Load {
    private static final List<Draw> blockList = new ArrayList<>();

    private Load() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * load method will try to load a file of a certain name in the Drawings folder.
     * @param name, file name of a file in the Drawings folder.
     * @return Problem object containing all the information read from the file.
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
            List<String> hints = (List<String>) ((JSONObject) fileElements.get(3)).get("Hints");

            return new Problem(name, description, teacherDrawings, studentAttempt, hints);

        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "There is no problem by the name of " + name + ".",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    /**
     * load method for drawings that are used for testing. It will try to load a file of a certain name in the
     * TestDrawings folder
     * @param name, file name of a file in the TestDrawings folder.
     * @return Problem object containing all the information read from the file.
     */
    @SuppressWarnings("unchecked")
    public static Problem loadTest(String name) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("TestDrawings/" + name + ".json")) {
            Object obj = jsonParser.parse(reader);
            JSONArray fileElements = (JSONArray) obj;

            String description = ((JSONObject) fileElements.get(0)).get("Problem Description").toString();
            List<Draw> teacherDrawings = parseDrawingArray((JSONArray)
                    ((JSONObject) fileElements.get(1))
                            .get("Teacher Solution"));
            List<Draw> studentAttempt = parseDrawingArray((JSONArray)
                    ((JSONObject) fileElements.get(2))
                            .get("Student Attempt"));
            List<String> hints = (List<String>) ((JSONObject) fileElements.get(3)).get("Hints");

            return new Problem(name, description, teacherDrawings, studentAttempt, hints);

        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "There is no problem by the name of " + name + ".",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Draw> parseDrawingArray(JSONArray drawingElements) {
        if (Objects.isNull(drawingElements)) {
            return Collections.emptyList();
        }
        List<Draw> blocks = new ArrayList<>();
        drawingElements.stream()
                .filter(block -> !(Objects.isNull(((JSONObject)block).get("CodeBlock"))))
                .forEach(block ->
                        blocks.add(loadCodeBlock((JSONObject) ((JSONObject)block).get("CodeBlock"))));
        blockList.clear();
        blockList.addAll(blocks);
        drawingElements.stream()
                .filter(block -> (Objects.isNull(((JSONObject)block).get("CodeBlock"))
                        && !Objects.isNull(((JSONObject)block).get("Arrow"))))
                .forEach(arrow ->
                        blocks.add(loadArrow((JSONArray) ((JSONObject)arrow).get("Arrow"))));
        return blocks;
    }

    /**
     * loadCodeBlock method returns the different blocks and loads them into a list.
     * @param codeBlock, type of block
     * @return drawing
     */
    private static Draw loadCodeBlock(JSONObject codeBlock) {
        Block drawing = null;
        if (codeBlock.get("Name").equals("CallMethodBlock")) {
            drawing = new CallMethodBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
        } else if (codeBlock.get("Name").equals("ConditionBlock")) {
            drawing = new ConditionBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
        } else if (codeBlock.get("Name").equals("EndBlock")) {
            drawing = new EndBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
        } else if (codeBlock.get("Name").equals("InputOutputBlock")) {
            drawing = new InputOutputBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
        } else if (codeBlock.get("Name").equals("InstructionBlock")) {
            drawing = new InstructionBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
        } else if (codeBlock.get("Name").equals("StartBlock")) {
            drawing = new StartBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
        } else if (codeBlock.get("Name").equals("VariableDeclarationBlock")) {
            drawing = new VariableDeclarationBlock(Integer.parseInt((String) codeBlock.get("X1")),
                    Integer.parseInt((String) codeBlock.get("Y1")));
        }
        assert drawing != null;
        drawing.setBlockText((String) codeBlock.get("Text"));
        getHints(codeBlock.get("Hint"), drawing);
        drawing.setArrowInLimit(Integer.parseInt((String) codeBlock.get("arrowInLimit")));
        drawing.setArrowOutLimit(Integer.parseInt((String) codeBlock.get("arrowOutLimit")));
        drawing.setArrowInCount(Integer.parseInt((String) codeBlock.get("arrowInCount")));
        drawing.setArrowOutCount(Integer.parseInt((String) codeBlock.get("arrowOutCount")));
        return drawing;
    }

    private static void getHints(Object hintsObject, Block drawing) {
        if (hintsObject.getClass().isArray() || hintsObject instanceof Collection) {
            List<String> hintList = new ArrayList<>();
            for (Object object : (List<?>) hintsObject) {
                if (object instanceof String hint) {
                    hintList.add(hint);
                }
            }
            drawing.setHintText(hintList);
        }
    }

    /**
     * loadArrow method adds the arrow between blocks and returns needed arrow for each.
     * @param arrow, loaded arrow
     * @return arrowFinal
     */
    private static Draw loadArrow(JSONArray arrow) {
        Arrow arrowFinal;
        ArrayList<Block> blocks = new ArrayList<>();
        for (Object o : arrow) {
            Block temp = (Block)loadCodeBlock((JSONObject) ((JSONObject) o).get("CodeBlock"));
            for(Draw drawing : blockList) {
                if(drawing instanceof Block block && temp.equals(block)) {
                    blocks.add((Block) drawing);
                    break;
                }
            }
        }
        arrowFinal = new Arrow(blocks.get(0), blocks.get(1));
        return arrowFinal;
    }
}
