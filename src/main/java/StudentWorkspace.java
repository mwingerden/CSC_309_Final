
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * TeacherWorkspace class where all the blocks will be displayed on by the user's inputs.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
 * @author  Mary Lemmer
 */
public class StudentWorkspace extends JPanel implements Observer {
    Repository repository;

    /**
     * The TeacherWorkspace method sets up the layout of the panel.
     */
    public StudentWorkspace() {
        repository = Repository.getInstance();
        repository.addObserver(this);
        MainController controller = new MainController();
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(300, 300));
        addMouseListener(controller);
        addMouseMotionListener(controller);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        JPanel workspacePanel = new JPanel();
        workspacePanel.setLayout(new BoxLayout(workspacePanel, BoxLayout.Y_AXIS));
        JButton submit = new JButton("Submit");
        submit.addActionListener(new MainController());
        add(submit, BorderLayout.SOUTH);
        workspacePanel.setBackground(Color.PINK);

        WorkspaceMenuBar workspaceMenuBar = new WorkspaceMenuBar();

        add(workspaceMenuBar, BorderLayout.NORTH);
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