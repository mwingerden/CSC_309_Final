import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
            "",
            "",
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList());

    private List<Draw> drawnChart;
    private final List<Draw> undoDrawings;

    private boolean loadSolution = false;
    private String blockToDraw;
    private String status;
    private Login login = null;
    private Repository() {
        this.blockToDraw = "";
        this.drawnChart = new ArrayList<>();
        this.undoDrawings = new ArrayList<>();
    }

    /**
     * Update which panel is shown
     * @param panel panel name
     */
    public void updatePanel(String panel) {
        if(panel.equals("TeacherListView")) {
            login.setVisible(true);
        }
        if(panel.equals("StudentSolutionPanel")) {
            this.loadSolution = false;
        }
        setChanged();
        notifyObservers(panel);
    }

    /**
     * Make login dialog
     * @param parent panel that the login should be relative to
     */
    public void setUpLogin(JPanel parent) {
        this.login = new Login(parent);
    }

    /**
     * Confirm that the entered login credentials are equal to the correct credentials.
     */
    public void authenticateLogin() {
        if(!login.isSucceeded()) {
            updatePanel("StartUp");
            login.dispose();
        } else {
            login.dispose();
        }
    }

    /**
     * Close the login dialog and return to the startup panel.
     */
    public void closeLogin() {
        login.dispose();
        updatePanel("StartUp");
    }

    /**
     * Set the currently drawn objects to the passed in list of draw objects.
     * @param drawnChart list of draw objects to update the currently drawn objects list to.
     */
    public void setDrawnChart(List<Draw> drawnChart) {
        this.drawnChart = drawnChart;
    }

    /**
     * Undo last drawing.
     */
    public void undoList() {
        if(!this.drawnChart.isEmpty()) {
            Draw temp = this.drawnChart.get(this.drawnChart.size() - 1);
            undoDrawings.add(temp);
            this.drawnChart.remove(temp);
            if (temp instanceof Arrow tempArrow){
                (tempArrow).sourceShape.removeOutArrow(tempArrow);
                (tempArrow).destinationShape.removeInArrow(tempArrow);
            }
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Redo last drawing.
     */
    public void redoList() {
        if(!this.undoDrawings.isEmpty()) {
            Draw temp = this.undoDrawings.get(this.undoDrawings.size() - 1);
            drawnChart.add(temp);
            this.undoDrawings.remove(temp);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Get the repository being used.
     * @return the active Repository object.
     */
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

    /**
     * Get the currently loaded problem.
     * @return Problem object of the currently loaded problem.
     */
    public Problem getLoadedProblem() {
        return this.loadedProblem;
    }

    /**
     * A saveList method that allows the user to save the work space if needed.
     * @throws IOException if unable to save file
     */
    public String saveList(Problem problemToSave, boolean rename) throws IOException {
        if (Objects.isNull(problemToSave) || rename) {
            String name = (String) JOptionPane.showInputDialog(
                    null,
                    "Problem Title:",
                    "Problem Title",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""
            );
            if (name != null) {
                if (Objects.isNull(problemToSave)) {
                    problemToSave = new Problem(name,
                            "",
                            "",
                            "",
                            Collections.emptyList(),
                            Collections.emptyList(),
                            Collections.emptyList());
                } else {
                    problemToSave.setNewProblemName(name);
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a non-null name for the problem.",
                        "No Name Entered",
                        JOptionPane.ERROR_MESSAGE
                );
                return saveList(problemToSave, rename);
            }
        } else {
            problemToSave.setDrawing(this.loadSolution, this.drawnChart);
        }
        if (!rename) {
            this.loadedProblem = problemToSave;
            setChanged();
            notifyObservers("Saved");
        }
        Save.save(problemToSave);
        return problemToSave.getProblemName();
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

    /**
     * Delete a problem file in the Drawings folder.
     * @param problemName Name of the problem file to delete.
     */
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
    public void drag(int x, int y, int newx, int newy)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Block blockToDrag = null;
        int dragX;
        int dragY;
        for (Draw drawing : drawnChart) {
            if (drawing instanceof Block block && block.contains(x, y)) {
                blockToDrag = (Block) drawing;
            }
        }
        if (blockToDrag != null && !testDragCollision(blockToDrag, newx, newy)) {
            dragX = newx;
            dragY = newy;
            applyDrag(blockToDrag, dragX, dragY);
        }
        setChanged();
        notifyObservers("Dragging");
    }

    private boolean testDragCollision(Block blockToDrag, int newX, int newY)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> blockArguments = new ArrayList<>();
        if (blockToDrag instanceof InstructionBlock ||
                blockToDrag instanceof VariableDeclarationBlock ||
                blockToDrag instanceof CallMethodBlock) {
            blockArguments.add(newX - 75);
            blockArguments.add((newY - 32));
        } else if (blockToDrag instanceof ConditionBlock) {
            blockArguments.add(newX - 50);
            blockArguments.add(newY - 50);
        } else if (blockToDrag instanceof InputOutputBlock) {
            blockArguments.add(newX);
            blockArguments.add(newY);
        } else if (blockToDrag instanceof StartBlock || blockToDrag instanceof EndBlock) {
            blockArguments.add(newX - 40);
            blockArguments.add(newY - 40);
        }
        Block testNewLocationBlock = (Block) blockToDrag.getClass()
                .getConstructors()[0].newInstance(blockArguments.toArray());
        return collides(testNewLocationBlock, blockToDrag);
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
            dragging(blockToDrag, new StartBlock(dragX - 40, dragY - 40));
        } else if (blockToDrag instanceof EndBlock) {
            dragging(blockToDrag, new EndBlock( dragX - 40, dragY - 40));
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
        newBlock.setColor(block.getColor());
        for (Draw temp1 : tempList) {
            if (temp1 instanceof Arrow arrow) {
                if (arrow.getSourceShape().equals(block)) {
                    drawnChart.add(new Arrow(newBlock, arrow.getDestinationShape()));
                    drawnChart.remove(arrow);
                } else if (arrow.getDestinationShape().equals(block)) {
                    drawnChart.add(new Arrow(arrow.getSourceShape(), newBlock));
                    drawnChart.remove(arrow);
                }
            }
        }
        drawnChart.remove(block);
        drawnChart.add(newBlock);
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
        if (!collides(block)) {
            undoDrawings.clear();
            if (!(block instanceof StartBlock) && !(block instanceof EndBlock)){
                if (newBlockText(block)) {
                    drawnChart.add(block);
                }
            } else {
                drawnChart.add(block);
            }
        }
    }

    private boolean collides(Block newBlock) {
        for (Draw drawing : this.drawnChart) {
            if (drawing instanceof Block drawnBlock && !drawnBlock.equals(newBlock) && drawnBlock.collides(newBlock)) {
                return true;
            }
        }
        return false;
    }

    private boolean collides(Block dragBlock, Block originalBlock) {
        for (Draw drawing : this.drawnChart) {
            if (drawing instanceof Block drawnBlock && !drawnBlock.equals(originalBlock) &&
                    drawnBlock.collides(dragBlock)) {
                return true;
            }
        }
        return false;
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
                if (e.isShiftDown()) {
                    if (this.loadSolution) {
                        block.teacherSideHint();
                    } else {
                        findCorrespondingTeacherBlock(block);
                    }
                } else if (!(block instanceof StartBlock || block instanceof EndBlock)) {
                    if (newBlockText(block)) {
                        block.setColor(Color.WHITE);
                    }
                    setChanged();
                    notifyObservers("Changed block text");
                }
            }
        }
    }

    private void findCorrespondingTeacherBlock(Block studentBlock) {
        boolean foundTeacherBlock = false;
        for (Draw drawing: this.loadedProblem.getTeacherSolution()) {
            if (drawing instanceof Block teacherBlock
                    && (((teacherBlock instanceof StartBlock && studentBlock instanceof StartBlock) ||
                        (teacherBlock instanceof EndBlock && studentBlock instanceof EndBlock))
                    || (!(teacherBlock instanceof StartBlock || teacherBlock instanceof EndBlock) &&
                        teacherBlock.getBlockText().equals(studentBlock.getBlockText())))) {
                teacherBlock.studentSideHint();
                foundTeacherBlock = true;
                break;
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

    private boolean newBlockText(Block newBlock) {
        String text = (String) JOptionPane.showInputDialog(
                null,
                "Name:",
                "Enter Name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
        );
        if (text != null && !text.isBlank()) {
            if (blockAlreadyNamedInput(newBlock, text)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Another block already exists with that same name.",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE
                );
                newBlockText(newBlock);
            } else {
                newBlock.setBlockText(text);
                setChanged();
                notifyObservers("Created Text");
                return true;
            }
        }
        return false;
    }

    private boolean blockAlreadyNamedInput(Block newNamedBlock, String proposedName) {
        for (Draw drawing : drawnChart) {
            if (drawing instanceof Block block
                    && block.getBlockText().equals(proposedName)
                    && block.getClass().equals(newNamedBlock.getClass())) {
                return true;
            }
        }
        return false;
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
                b1 = blockOneArrow(drawing);

            }
            if (drawing instanceof Block block && block.contains(x2, y2) && !(drawing instanceof StartBlock)) {
                b2 = (Block) drawing;
            }
        }
        if(!(Objects.isNull(b1)) && !(Objects.isNull(b2)) && !b1.equals(b2)
                && (b1.checkOutGoing() && b2.checkInGoing())) {
            Arrow a = new Arrow(b1, b2);
            b1.addOutArrow(a);
            b2.addInArrow(a);
            drawnChart.add(a);

        }
        setChanged();
        notifyObservers();
    }

    private Block blockOneArrow(Draw drawing) {
        if (drawing instanceof StartBlock startBlock && startBlock.maxNumsOut()){
            return null;
        } else {
            return (Block) drawing;
        }
    }

    /**
     * A getter method that returns a block, used to decide which block is needed to draw.
     * @return blockToDraw
     */
    public String getBlockToDraw() {
        return blockToDraw;
    }
}