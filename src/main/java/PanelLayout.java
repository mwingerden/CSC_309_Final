import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Creates all the main panels used by the program.
 */
public class PanelLayout extends JPanel implements Observer {
    Repository repository;

    /**
     * Creates all the main panels used by the program.
     */
    public PanelLayout() {
        repository = Repository.getInstance();
        repository.addObserver(this);
        MainController controller = new MainController();
        addMouseListener(controller);
        addMouseMotionListener(controller);
        setLayout(new CardLayout());
        StartUp startUp = new StartUp();
        add(startUp, "StartUp");
        repository.setUpLogin(startUp);
        add(new StudentListView(), "StudentListView");
        add(new TeacherListView(), "TeacherListView");
        add(new StudentSolutionPanel(), "StudentSolutionPanel");
    }

    /**
     * Swaps between the StudentListView, TeacherListView, StudentSolutionPanel, and Startup panel.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            String panel = (String) arg;
            CardLayout cl = (CardLayout) (this.getLayout());
            switch (panel) {
                case "StudentListView" -> cl.show(this, "StudentListView");
                case "TeacherListView" -> cl.show(this, "TeacherListView");
                case "StudentSolutionPanel" -> cl.show(this, "StudentSolutionPanel");
                case "StartUp" -> cl.show(this, "StartUp");
            }
        }
    }
}
