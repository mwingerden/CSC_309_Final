import java.awt.event.*;
import java.util.Objects;

public class MainController implements MouseMotionListener, ActionListener, MouseListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "If" -> Repository.getInstance().setBlockToDraw("If");
            case "Instr" -> Repository.getInstance().setBlockToDraw("Instr");
            case "Start" -> Repository.getInstance().setBlockToDraw("Start");
            case "End" -> Repository.getInstance().setBlockToDraw("End");
            case "I/O" -> Repository.getInstance().setBlockToDraw("I/O");
            case "Var" -> Repository.getInstance().setBlockToDraw("Var");
            case "Arrow" -> Repository.getInstance().setBlockToDraw("Arrow");
            case "New" -> {
                Repository.getInstance().setStatus("New diagram");
                Repository.getInstance().clearBlocks();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Repository.getInstance().getBlockToDraw().equals("If")){
            Repository.getInstance().setStatus("Condition block was drawn");
        }
        if (Repository.getInstance().getBlockToDraw().equals("Instr")){
            Repository.getInstance().setStatus("Command block was drawn");
        }
        if (Repository.getInstance().getBlockToDraw().equals("Start")){
            Repository.getInstance().setStatus("Starting block was drawn");
        }
        if (Repository.getInstance().getBlockToDraw().equals("End")){
            Repository.getInstance().setStatus("Ending block was drawn");
        }
        if (Repository.getInstance().getBlockToDraw().equals("I/O")){
            Repository.getInstance().setStatus("Input/Output block was drawn");
        }
        if (Repository.getInstance().getBlockToDraw().equals("Var")){
            Repository.getInstance().setStatus("Variable declaration block was drawn");
        }
        if (Repository.getInstance().getBlockToDraw().equals("Arrow")){
            Repository.getInstance().setStatus("Arrow was drawn");
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
