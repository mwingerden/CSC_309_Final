import java.awt.event.*;
import java.util.Objects;

public class MainController implements MouseMotionListener, ActionListener, MouseListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "If/Else" -> Repository.getInstance().setBlockToDraw("If/Else");
            case "Instruct" -> Repository.getInstance().setBlockToDraw("Instruct");
            case "Start" -> Repository.getInstance().setBlockToDraw("Start");
            case "End" -> Repository.getInstance().setBlockToDraw("End");
            case "I/O" -> Repository.getInstance().setBlockToDraw("I/O");
            case "Method" -> Repository.getInstance().setBlockToDraw("Method");
            case "Variable" -> Repository.getInstance().setBlockToDraw("Variable");
            case "Arrow" -> Repository.getInstance().setBlockToDraw("Arrow");
            case "New" -> {
                Repository.getInstance().clearBlocks();
                Repository.getInstance().setStatus("New diagram");
                Repository.getInstance().setBlockToDraw("None");
            }
            case "Save" -> {
                Repository.getInstance().setStatus("Saving diagram");
            }
            case "Load" -> {
                Repository.getInstance().setStatus("Loading diagram");
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Repository.getInstance().getBlockToDraw()) {
            case "If/Else" -> {
                Repository.getInstance().setStatus("Condition block was drawn");
                Repository.getInstance().addBlock(new ConditionBlock(e.getX() - 50, e.getY() - 50));
            }
            case "Instruct" -> {
                Repository.getInstance().setStatus("Command block was drawn");
                Repository.getInstance().addBlock(new InstructionBlock(e.getX() - 75, e.getY() - 32));
            }
            case "Start" -> {
                Repository.getInstance().setStatus("Starting block was drawn");
                Repository.getInstance().addBlock(new StartBlock(e.getX() - 40, e.getY() - 40, 80, 80, "PINK"));
            }
            case "End" -> {
                Repository.getInstance().setStatus("Ending block was drawn");
                Repository.getInstance().addBlock(new EndBlock(e.getX() - 40, e.getY() - 40, 80, 80, "BLUE"));
            }
            case "I/O" -> {
                Repository.getInstance().setStatus("Input/Output block was drawn");
                Repository.getInstance().addBlock(new InputOutputBlock(e.getX() - 30, e.getY()));
            }
            case "Method" -> {
                Repository.getInstance().setStatus("Method call block was drawn");

            }
            case "Variable" -> Repository.getInstance().setStatus("Variable declaration block was drawn");
            case "Arrow" -> Repository.getInstance().setStatus("Arrow was drawn");
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
