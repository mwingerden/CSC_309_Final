import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
    }

    public void dragBlock(int x, int y) {
        Block blockToDrag = null;
        Block newBlock = null;
        int dragX;
        int dragY;

        //TODO: Uncomment when the method contains is implemented.
        //TODO: Uncomment when the rest of the block classes are created.
        for(Draw drawing : drawings) {
//            if(drawing instanceof Block && drawing.contains(x, y)) {
//                blockToDrag = (CodeBlock) drawing;
//                dragX = ((drawing.getX2() - drawing.getX1()) / 2);
//                dragY = ((drawing.getY2() - drawing.getY1()) / 2);
//                if (blockToDrag instanceof InstructionBlock) {
//                    newBlock = new InstructionBlock(x - dragX, y - dragY);
//                } else if (blockToDrag instanceof ConditionBlock) {
//                    newBlock = new ConditionBlock(x - dragX, y - dragY);
//                } else if (blockToDrag instanceof VariableDeclarationBlock) {
//                    newBlock = new VariableDeclarationBlock(x - dragX, y - dragY);
//                } else if (blockToDrag instanceof CallMethodBlock) {
//                    newBlock = new CallMethodBlock(x - dragX, y - dragY);
//                } else if (blockToDrag instanceof InputOutputBlock) {
//                    newBlock = new InputOutputBlock(x - dragX, y - dragY);
//                } else if (blockToDrag instanceof StartBlock) {
//                    newBlock = new Start(x - dragX, y - dragY);
//                } else if (blockToDrag instanceof EndBlock) {
//                    newBlock = new End(x - dragX, y - dragY);
//                }
//            }
        }
        drawings.remove(blockToDrag);
        drawings.add(newBlock);
        setChanged();
        notifyObservers("Dragging");
    }

    public void setBlockToDraw(String blockToDraw) {
        this.blockToDraw = blockToDraw;
    }

    public void setStatus(String status){
        this.status = status;
        setChanged();
        notifyObservers(status);
    }

    public String getStatus(){
        return status;
    }

}
