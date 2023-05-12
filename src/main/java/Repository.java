
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
/**
 * The Main.Repository class holds all the needed information of other classes.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
 * @author  Mary Lemmer
 */
public class Repository extends Observable {
    private static final Repository instance = new Repository();
    private List<Draw> drawings;
    private String blockToDraw;
    private String status;
    private String problemDesc;
    private String problemName;
    private Boolean editing;

    private Repository() {
        this.blockToDraw = "";
        this.drawings = new ArrayList<>();
        this.problemName = null;
        this.editing = false;
    }

    //TODO: Updates the observers with the new panel info.
    public void updatePanel(String panel) {
        setChanged();
        notifyObservers(panel);
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
        for (Draw drawing : drawings) {
            if (drawing instanceof Block) {
                codeBlocks.add((Block) drawing);
            } else if (drawing instanceof Arrow) {
                arrows.add((Arrow) drawing);
            }
       }
        newDrawings.addAll(arrows);
        newDrawings.addAll(codeBlocks);
        return newDrawings;
    }

    public void deleteProblem(){
        File f1 = new File("Drawings/"+ problemName + ".json");
        if(f1.delete()){
            System.out.println("deleted");
        } else{
            System.out.println("not deleted");
        }
        setChanged();
        notifyObservers();
    }

    public void setProblemLoad(String name)
    {
        this.problemName = name;
    }

    /**
     * A saveList method that allows the user to save the work space if needed.
     * @throws IOException
     */

    public void setEditing(){
        editing = true;
    }

    public void unsetEditing(){
        editing = false;
    }

    public void saveList() throws IOException {
        String name = problemName;
        if (!editing) {
            name = (String) JOptionPane.showInputDialog(
                    new WorkSpace(),
                    "Problem Title:",
                    "Problem Title",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    ""
            );
        }

        if (name != null) {
            setChanged();
            notifyObservers("Save Description");
            Save.save(drawings, name, problemDesc);
            setChanged();
            notifyObservers("save");
        }
    }

    public void saveProblemDescription(String desc) {
        this.problemDesc = desc;
    }

    public String loadProblemDescription() {
        return this.problemDesc;
    }

    /**
     * A loadList method allowing the user to load a previously saved file.
     */
    public void loadList() {
        /*String name = (String) JOptionPane.showInputDialog(
                new WorkSpace(),
                "Enter Name to Main.Load File:",
                "Enter Name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
        );*/
        if (problemName != null) {
            drawings.clear();
            drawings = Load.load(problemName);
            setChanged();
            notifyObservers("Load Description");
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
        for (Draw drawing : drawings) {
            if (drawing instanceof Block && ((Block) drawing).contains(x, y)) {
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
        List<Draw> tempList = new ArrayList<>(drawings);
        newBlock.setText(block.getText());
        for (Draw temp1 : tempList) {
            if (temp1 instanceof Arrow arrow) {
                if (arrow.getInBlock().equals(block)) {
                    drawings.add(new Arrow(newBlock, arrow.getOutBlock()));
                    drawings.remove(arrow);
                } else if (arrow.getOutBlock().equals(block)) {
                    drawings.add(new Arrow(arrow.getInBlock(), newBlock));
                    drawings.remove(arrow);
                }
            }
        }
        drawings.remove(block);
        drawings.add(newBlock);
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
        drawings.add(block);
    }
    /**
     * A method that clears all the blocks on the work space.
     */
    public void clearBlocks(){
        if (!drawings.isEmpty()){
            drawings.clear();
            setChanged();
            notifyObservers("Clear Description");
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
    public void addText(int x, int y) {
        for (Draw drawing : drawings) {
            if (drawing instanceof Block && ((Block) drawing).contains(x, y)) {
                if (!(drawing instanceof StartBlock || drawing instanceof EndBlock)) {
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
                        ((Block) drawing).setText(text);
                        setChanged();
                        notifyObservers("Created Text");
                    }
                    return;
                }
            }
        }
    }
    /**
     * addArrow method used by the Main.MainController to add arrows between blocks.
     * @param x1, arrow's first x coordinate
     * @param y1, arrow's first y coordinate
     * @param x2, arrow's second x coordinate
     * @param y2, arrow's second y coordinate
     */
    public void addArrow(int x1, int y1, int x2, int y2){
        Block b1 = null;
        Block b2 = null;
        for (Draw drawing : drawings) {
            if (drawing instanceof Block && ((Block) drawing).contains(x1, y1) && !(drawing instanceof EndBlock)){
                b1 = blockOneArrow(drawing, x1, y1);
            }
            if (drawing instanceof Block && ((Block) drawing).contains(x2, y2) && !(drawing instanceof StartBlock)) {
                b2 = (Block) drawing;
            }
        }
        if(b1 != null && b2 != null && b1 != b2){
            if(b1.checkOutGoing() && b2.checkInGoing())
            {
                drawings.add(new Arrow(b1,b2));
            }
        }
        setChanged();
        notifyObservers();
    }

    private Block blockOneArrow(Draw drawing, int x1, int y1) {
        if (drawing instanceof StartBlock){
            if (((StartBlock)drawing).maxNumsOut()){
                return null;
            }else{
                ((StartBlock) drawing).increaseNumOut();
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