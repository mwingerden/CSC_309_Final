import java.awt.event.*;

public class MainController implements MouseMotionListener, ActionListener, MouseListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("If")){
            Repository.getInstance().setBlockToDraw("If");
        }
        if(e.getActionCommand().equals("Inst")){
            Repository.getInstance().setBlockToDraw("Inst");
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
