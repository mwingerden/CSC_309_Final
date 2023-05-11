
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Main.WorkSpace class where all the blocks will be displayed on by the user's inputs.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
 * @author  Mary Lemmer
 */
public class StudentDrawArea extends JPanel implements Observer {
    Repository repository;
    /**
     * The Main.WorkSpace method sets up the layout of the panel.
     */
    public StudentDrawArea() {
        repository = Repository.getInstance();
        repository.addObserver(this);
        MainController controller = new MainController();
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(300, 300));
        addMouseListener(controller);
        addMouseMotionListener(controller);
        add(new MenuBar(), BorderLayout.NORTH);
        add(new StatusBar(77), BorderLayout.SOUTH);
    }
    /**
     * paintComponent method that allows the different blocks to be drawn on screen by user.
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Draw drawing : repository.getDrawings()) {
            drawing.draw(g);
        }
    }
    /**
     * update method handles the repainting after each component is drawn.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}