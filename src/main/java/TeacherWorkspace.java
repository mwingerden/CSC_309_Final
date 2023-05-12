
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
/**
 * Main.TeacherWorkspace class where all the blocks will be displayed on by the user's inputs.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
 * @author  Mary Lemmer
 */
public class TeacherWorkspace extends JPanel implements Observer {
    Repository repository;

    private String description;

    /**
     * The Main.TeacherWorkspace method sets up the layout of the panel.
     */
    public TeacherWorkspace(String problemName) {
        repository = Repository.getInstance();
        repository.addObserver(this);
        MainController controller = new MainController();
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(300, 300));
        addMouseListener(controller);
        addMouseMotionListener(controller);

        Repository.getInstance().loadList(true, problemName);

        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.setBackground(Color.PINK);

        TeacherWorkspaceMenuBar teacherWorkspaceMenuBar = new TeacherWorkspaceMenuBar(problemName);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        north.add(teacherWorkspaceMenuBar, BorderLayout.NORTH);
        north.add(new ProblemDescription(), BorderLayout.NORTH);

        add(north, BorderLayout.NORTH);
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