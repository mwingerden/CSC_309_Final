import java.awt.event.*;
import java.util.Objects;

public class MainController implements MouseMotionListener, ActionListener, MouseListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("If")){
            Repository.getInstance().setBlockToDraw("If");
        }
        if(e.getActionCommand().equals("Instr")){
            Repository.getInstance().setBlockToDraw("Instr");
        }
        if(e.getActionCommand().equals("Start")){
            Repository.getInstance().setBlockToDraw("Start");
        }
        if(e.getActionCommand().equals("End")){
            Repository.getInstance().setBlockToDraw("End");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Repository.getInstance().getBlockToDraw().equals("If")){
            Repository.getInstance().setStatus("If block was drawn");
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
