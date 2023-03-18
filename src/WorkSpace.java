import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class WorkSpace extends JPanel implements Observer {
    Repository repository;

    public WorkSpace() {
        repository = Repository.getInstance();
        repository.addObserver(this);
        MainController controller = new MainController();
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(300, 300));
        addMouseListener(controller);
        addMouseMotionListener(controller);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Draw drawing : repository.getDrawings()) {
            drawing.draw(g);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
