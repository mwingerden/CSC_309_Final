import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Repository extends Observable {
    private static final Repository instance = new Repository();
    private List<Draw> drawings;

    String blockToDraw;
    String status;

    private Repository() {
        this.blockToDraw = "";
        this.drawings = new ArrayList<>();
    }

    public static Repository getInstance() {
        return instance;
    }

    public List<Draw> getDrawings() {
        return this.drawings;
        //TODO: Uncomment when arrow is created. This implementation draws the arrows underneath the blocks.
//        List<Draw> newDrawings = new ArrayList<>();
//        List<Block> codeBlocks = new ArrayList<>();
//        List<Arrow> arrows = new ArrayList<>();
//
//        for (Draw drawing : drawings) {
//            if (drawing instanceof Block) {
//                codeBlocks.add((Block) drawing);
//            } else if (drawing instanceof Arrow) {
//                arrows.add((Arrow) drawing);
//            }
//        }
//        newDrawings.addAll(arrows);
//        newDrawings.addAll(codeBlocks);
//        return newDrawings;
    }

    public void saveList() {
        String name = (String) JOptionPane.showInputDialog(
                new WorkSpace(),
                "Type Name for the Save File:",
                "Save File",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
        );
        if (name != null) {
            Save.save(drawings, name);
            setChanged();
            notifyObservers("save");
        }
    }

    public void loadList() {
        String name = (String) JOptionPane.showInputDialog(
                new WorkSpace(),
                "Enter Name to Load File:",
                "Enter Name",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
        );
        if (name != null) {
            drawings.clear();
            drawings = Load.load(name);
            setChanged();
            notifyObservers("load");
        }
    }

    public void drag(int x, int y) {
        //TODO: Uncomment when the rest of the blocks are created.
        Block blockToDrag = null;
        int dragX;
        int dragY;

        for (Draw drawing : drawings) {
            if (drawing instanceof Block && ((Block) drawing).contains(x, y)) {
                blockToDrag = (Block) drawing;
            }
        }

        if (blockToDrag != null) {
            dragX = ((blockToDrag.getX2() - blockToDrag.getX1()) / 2);
            dragY = ((blockToDrag.getY2() - blockToDrag.getY1()) / 2);
            if (blockToDrag instanceof InstructionBlock) {
                dragging(blockToDrag, new InstructionBlock(x - dragX, y - dragY));
            } else if (blockToDrag instanceof ConditionBlock) {
                dragging(blockToDrag, new ConditionBlock(x - dragX, y - dragY));
            } else if (blockToDrag instanceof VariableDeclarationBlock) {
                dragging(blockToDrag, new VariableDeclarationBlock(x - dragX, y - dragY));
            } else if (blockToDrag instanceof CallMethodBlock) {
                dragging(blockToDrag, new CallMethodBlock(x - dragX, y - dragY));
            } else if (blockToDrag instanceof InputOutputBlock) {
                dragging(blockToDrag, new InputOutputBlock(x - dragX, y - dragY));
            } else if (blockToDrag instanceof StartBlock) {
                dragging(blockToDrag, new StartBlock(x - dragX, y - dragY, "PINK"));
            } else if (blockToDrag instanceof EndBlock) {
                dragging(blockToDrag, new EndBlock(x - dragX, y - dragY, "BLUE"));
            }
        }
        setChanged();
        notifyObservers("Dragging");
    }

    private void dragging(Block block, Block newBlock) {
        //TODO: Uncomment when arrow is created
        List<Draw> tempList = new ArrayList<>(drawings);
        newBlock.setText(block.getText());
        for (Draw temp1 : tempList) {
            if (temp1 instanceof Arrow arrow) {
                if (arrow.getBlock1().equals(block)) {
                    drawings.add(new Arrow(newBlock, arrow.getBlock2()));
                    drawings.remove(arrow);
                } else if (arrow.getBlock2().equals(block)) {
                    drawings.add(new Arrow(arrow.getBlock1(), newBlock));
                    drawings.remove(arrow);
                }
            }
        }
        drawings.remove(block);
        drawings.add(newBlock);
    }

    public void setBlockToDraw(String blockToDraw) {
        this.blockToDraw = blockToDraw;
    }

    public void addBlock(Block block){
        drawings.add(block);
    }

    public void clearBlocks(){
        if (!drawings.isEmpty()){
            drawings.clear();
            setChanged();
            notifyObservers();
        }
    }

    public void setStatus(String status){
        this.status = status;
        setChanged();
        notifyObservers(status);
    }

    public void addText(int x, int y) {
        //TODO Uncomment when contains is implemented and the rest of the blocks are implemented.
//        for (Draw drawing : drawings) {
//            if (drawing instanceof Block && ((Block) drawing).contains(x, y)) {
//                if (!(drawing instanceof StartBlock || drawing instanceof EndBlock)) {
//                    String text = (String) JOptionPane.showInputDialog(
//                            new WorkSpace(),
//                            "Name:",
//                            "Enter Name",
//                            JOptionPane.PLAIN_MESSAGE,
//                            null,
//                            null,
//                            ""
//                    );
//                    if (text != null) {
//                        ((Block) drawing).setText(text);
//                        setChanged();
//                        notifyObservers("Created Text");
//                    }
//                    return;
//                }
//            }
//        }
    }

    public String getStatus(){
        return status;
    }

    public String getBlockToDraw() {
        return blockToDraw;
    }
}
