
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

/**
 * The Repository class holds all the needed information of other classes.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
 * @author  Mary Lemmer
 */
public class Repository extends Observable {
    private static final Repository instance = new Repository();

    private Problem loadedProblem = new Problem("",
            "",
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList());
    private List<Draw> drawnChart;
    private final List<Draw> undoDrawings;

    private boolean loadSolution = false;
    private String blockToDraw;
    private String status;
    private Repository() {
        this.blockToDraw = "";
        this.drawnChart = new ArrayList<>();
        this.undoDrawings = new ArrayList<>();
    }

    //TODO: Updates the observers with the new panel info.
    public void updatePanel(String panel) {
        setChanged();
        notifyObservers(panel);
    }

    public void UndoList() {
        if(!this.drawnChart.isEmpty()) {
            Draw temp = this.drawnChart.get(this.drawnChart.size() - 1);
            undoDrawings.add(temp);
            this.drawnChart.remove(temp);
            if (temp instanceof Arrow){
                ((Arrow) temp).block1.removeOutArrow((Arrow)temp);
                ((Arrow) temp).block2.removeInArrow((Arrow)temp);
            }
            setChanged();
            notifyObservers();
        }
    }

    public void RedoList() {
        if(!this.undoDrawings.isEmpty()) {
            Draw temp = this.undoDrawings.get(this.undoDrawings.size() - 1);
            drawnChart.add(temp);
            this.undoDrawings.remove(temp);
            setChanged();
            notifyObservers();
        }
    }

    public static Repository getInstance() {
        return instance;
    }
    /**
     * getDrawings method adds all blocks and arrows to a list of drawings and returns said new list.
     * @return newDrawings
     */
    public List<Draw> getDrawings() {
        List<Draw> newDrawings = new ArrayList<>();
        List<Block> codeBlocks = new ArrayList<>();
        List<Arrow> arrows = new ArrayList<>();
        for (Draw drawing : drawnChart) {
            if (drawing instanceof Block block) {
                codeBlocks.add(block);
            } else if (drawing instanceof Arrow arrow) {
                arrows.add(arrow);
            }
       }
        newDrawings.addAll(arrows);
        newDrawings.addAll(codeBlocks);
        return newDrawings;
    }

    public Problem getLoadedProblem() {
        return this.loadedProblem;
    }

    /**
     * A saveList method that allows the user to save the work space if needed.
     * @throws IOException if unable to save file
     */
    public String saveList(Problem problemToSave) throws IOException {
        if (Objects.isNull(problemToSave)) {
            String name = (String) JOptionPane.showInputDialog(
                    new WorkSpace(),
                    "Problem Title:",
                    "Problem Title",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""
            );
            if (name != null) {
                problemToSave = new Problem(name,
                        "",
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
            } else {
                return null;
            }
        } else {
            problemToSave.setDrawing(this.loadSolution, this.drawnChart);
        }
        this.loadedProblem = problemToSave;
        setChanged();
        notifyObservers("Saved");
        Save.save(problemToSave);
        return problemToSave.getProblemName();
//            setChanged();
//            notifyObservers("save");

    }

    public void saveStudentSubmission() {
        try {
            Save.save(loadedProblem);
            for (Draw d : loadedProblem.getStudentAttempt()){
                if (d instanceof Block && !(d instanceof EndBlock)){
                    ((Block) d).setColor(Color.WHITE);
                }
            }
            ProblemChecker pc = new ProblemChecker(loadedProblem);
            Draw diffDraw = pc.CheckProblem();

            if (diffDraw instanceof EndBlock){
                //case that the graphs are the same;

                loadedProblem.setProgress("complete");
                loadedProblem.setFeedback("Correct!");

            }

            if (diffDraw instanceof Block){
                if (loadedProblem.getTeacherSolution().contains((Block)diffDraw)){
                    //case, student graph is missing a block
                    loadedProblem.setProgress("in progress");
                    loadedProblem.setFeedback("You are Missing a Block or a Block has incorrect information");

                }
                else if (loadedProblem.getStudentAttempt().contains((Block)diffDraw)){
                    //case, student contains extra blocks
                    for (Draw d : loadedProblem.getStudentAttempt()){
                        if (d instanceof Block && !(d instanceof EndBlock)){
                            if (d.equals(diffDraw)){
                                ((Block) d).setColor(Color.RED);
                            }

                        }
                    }


                    loadedProblem.setProgress("in progress");
                    loadedProblem.setFeedback("You have an extra block or a block has incorrect information");
                }
            }
            else{
            }
            setChanged();
            notifyObservers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * A loadList method allowing the user to load a previously saved file.
     */
    public void loadList(boolean loadSolution, String problemName) {
        if (problemName != null) {
            drawnChart.clear();
            this.loadedProblem = Load.load(problemName);
            // Error loading file
            if (Objects.isNull(this.loadedProblem)) {
                return;
            }
            if (loadSolution) {
                this.drawnChart = this.loadedProblem.getTeacherSolution();
                this.loadSolution = true;
            } else {
                this.drawnChart = this.loadedProblem.getStudentAttempt();
                this.loadSolution = false;
            }
            setChanged();
            notifyObservers("Loaded");
        }
    }

    public void delete(String problemName) {
        try {
            File deleteProblem = new File("Drawings/" + problemName + ".json");
            Files.deleteIfExists(deleteProblem.toPath());
            setChanged();
            notifyObservers("Deleted");
            clearChanged();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(
                    null,
                    "Selected problem unable to be found in Drawings directory.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * draw method allowing user to draw from and to different coordinates for each kind of block.
     * @param x, previous x coordinate
     * @param y, previous y coordinate
     * @param newx, the new x coordinate
     * @param newy, the new y coordinate
     */
    public void drag(int x, int y, int newx, int newy) {
        Block blockToDrag = null;
        int dragX;
        int dragY;
        for (Draw drawing : drawnChart) {
            if (drawing instanceof Block block && block.contains(x, y)) {
                blockToDrag = (Block) drawing;
            }
        }
        if (blockToDrag != null) {
            dragX = newx;
            dragY = newy;
            applyDrag(blockToDrag, dragX, dragY);
        }
        setChanged();
        notifyObservers("Dragging");
    }

    private void applyDrag(Block blockToDrag, int dragX, int dragY) {
        if (blockToDrag instanceof InstructionBlock) {
            dragging(blockToDrag, new InstructionBlock(dragX-75, dragY-32));
        } else if (blockToDrag instanceof ConditionBlock) {
            dragging(blockToDrag, new ConditionBlock(dragX - 50,  dragY - 50));
        } else if (blockToDrag instanceof VariableDeclarationBlock) {
            dragging(blockToDrag, new VariableDeclarationBlock(dragX - 75, dragY - 32));
        } else if (blockToDrag instanceof CallMethodBlock) {
            dragging(blockToDrag, new CallMethodBlock(dragX - 75, dragY- 32));
        } else if (blockToDrag instanceof InputOutputBlock) {
            dragging(blockToDrag, new InputOutputBlock(dragX - 30, dragY));
        } else if (blockToDrag instanceof StartBlock) {
            dragging(blockToDrag, new StartBlock(dragX - 40, dragY - 40, "PINK"));
        } else if (blockToDrag instanceof EndBlock) {
            dragging(blockToDrag, new EndBlock( dragX - 40, dragY - 40, "BLUE"));
        }
    }

    /**
     * dragging method allows the user to drag the different blocks around with arrows.
     * @param block, block that is being dragged
     * @param newBlock, block after dragging
     */
    private void dragging(Block block, Block newBlock) {
        List<Draw> tempList = new ArrayList<>(drawnChart);
        newBlock.setBlockText(block.getBlockText());
        newBlock.setHintText((block.getHintText()));
        newBlock.setArrowInCount(block.getArrowInCount());
        newBlock.setArrowOutCount(block.getArrowOutCount());
        for (Draw temp1 : tempList) {
            if (temp1 instanceof Arrow arrow) {
                if (arrow.getBlock1().equals(block)) {
                    drawnChart.add(new Arrow(newBlock, arrow.getBlock2()));
                    drawnChart.remove(arrow);
                } else if (arrow.getBlock2().equals(block)) {
                    drawnChart.add(new Arrow(arrow.getBlock1(), newBlock));
                    drawnChart.remove(arrow);
                }
            }
        }
        drawnChart.remove(block);
        drawnChart.add(newBlock);
    }

    public void deleteBlock(int x, int y) {
        for (Draw drawing : drawnChart) {
            if (drawing instanceof Block block && block.contains(x, y)) {
                drawnChart.remove(drawing);
                setChanged();
                notifyObservers();
                break;
            }
        }
    }

    /**
     * setter that helps decide what block is drawn.
     * @param blockToDraw, what block is needed
     */
    public void setBlockToDraw(String blockToDraw) {
        this.blockToDraw = blockToDraw;
    }
    /**
     * addBlock method adds the block to the drawings list.
     * @param block, added block
     */
    public void addBlock(Block block){
        undoDrawings.clear();
        if (!(block instanceof StartBlock) && !(block instanceof EndBlock)){
            newBlockText(block);
        }
        drawnChart.add(block);
    }
    /**
     * A method that clears all the blocks on the work space. Clears hint index for each solution block if
     * called by student.
     */
    public void clearBlocks(){
        if (!drawnChart.isEmpty()){
            if (!this.loadSolution) {
                clearSolutionBlockHintIndex();
            }
            drawnChart.clear();
            setChanged();
            notifyObservers("Clear Description");
        }
    }

    private void clearSolutionBlockHintIndex() {
        for (Draw drawing : this.loadedProblem.getTeacherSolution()) {
            if (drawing instanceof Block block) {
                block.resetStudentHintIndex();
            }
        }
    }

    /**
     * Setter method used to change the status bar text status.
     * @param status, text to be displayed
     */
    public void setStatus(String status){
        this.status = status;
        setChanged();
        notifyObservers(status);
    }
    /**
     * addText method allowing text to be set in the blocks.
     * @param x, block's x coordinate
     * @param y, block's y coordinate
     */
    public void blockText(MouseEvent e, int x, int y) {
        for (Draw drawing : drawnChart) {
            if (drawing instanceof Block block && block.contains(x, y)) {
                if (e.isControlDown()) {
                    if (this.loadSolution) {
                        block.teacherSideHint();
                    } else {
                        findCorrespondingTeacherBlock(block);
                    }
                } else if (!(block instanceof StartBlock || block instanceof EndBlock)) {
                    String text = (String) JOptionPane.showInputDialog(
                            new WorkSpace(),
                            "Name:",
                            "Enter Name",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            null,
                            ""
                    );
                    if (text != null) {
                        block.setBlockText(text);
                        setChanged();
                        notifyObservers("Created Text");
                    }
                }
            }
        }
    }

    private void findCorrespondingTeacherBlock(Block studentBlock) {
        boolean foundTeacherBlock = false;
        for (Draw drawing: this.loadedProblem.getTeacherSolution()) {
            if (drawing instanceof Block teacherBlock) {
                if ((teacherBlock instanceof StartBlock && studentBlock instanceof StartBlock) ||
                (teacherBlock instanceof EndBlock && studentBlock instanceof EndBlock)) {
                    teacherBlock.studentSideHint();
                    foundTeacherBlock = true;
                    break;
                } else if (!(teacherBlock instanceof StartBlock || teacherBlock instanceof EndBlock) &&
                        (teacherBlock.getBlockText().equals(studentBlock.getBlockText()))) {
                    teacherBlock.studentSideHint();
                    foundTeacherBlock = true;
                    break;
                }
            }
        }
        if (!foundTeacherBlock) {
            JOptionPane.showMessageDialog(
                    null,
                    "ERROR: Could not locate corresponding teacher block. Check the text assigned to the " +
                            "block you are attempting to get a hint for matches the prompt exactly."
            );
        }
    }

    private void newBlockText(Block newBlock) {
        String text = (String) JOptionPane.showInputDialog(
                new WorkSpace(),
                "Name:",
                "Enter Name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
        );
        if (text != null) {
            newBlock.setBlockText(text);
            setChanged();
            notifyObservers("Created Text");
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "ERROR: Must input text block represents.",
                    "No Input Found",
                    JOptionPane.ERROR_MESSAGE
            );
            newBlockText(newBlock);
        }
    }

    /**
     * addArrow method used by the MainController to add arrows between blocks.
     * @param x1, arrow's first x coordinate
     * @param y1, arrow's first y coordinate
     * @param x2, arrow's second x coordinate
     * @param y2, arrow's second y coordinate
     */
    public void addArrow(int x1, int y1, int x2, int y2){
        Block b1 = null;
        Block b2 = null;
        for (Draw drawing : drawnChart) {
            if (drawing instanceof Block block && block.contains(x1, y1) && !(drawing instanceof EndBlock)){
                b1 = blockOneArrow(drawing, x1, y1);

            }
            if (drawing instanceof Block block && block.contains(x2, y2) && !(drawing instanceof StartBlock)) {
                b2 = (Block) drawing;
            }
        }
        if(b1 != null && b2 != null && b1 != b2){
            if(b1.checkOutGoing() && b2.checkInGoing()) {
                Arrow a = new Arrow(b1, b2);
                b1.addOutArrow(a);
                b2.addInArrow(a);
                drawnChart.add(a);
            }
        }
        setChanged();
        notifyObservers();
    }

    private Block blockOneArrow(Draw drawing, int x1, int y1) {
        if (drawing instanceof StartBlock startBlock){
            if (startBlock.maxNumsOut()){
                return null;
            }else{
                startBlock.increaseNumOut();
                return (Block) drawing;
            }
        }else {
            return (Block) drawing;
        }
    }

    /**
     * Getter method that returns what the status displays.
     * @return status
     */
    public String getStatus(){
        return status;
    }
    /**
     * A getter method that returns a block, used to decide which block is needed to draw.
     * @return blockToDraw
     */
    public String getBlockToDraw() {
        return blockToDraw;
    }
}