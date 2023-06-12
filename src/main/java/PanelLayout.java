
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PanelLayout extends JPanel implements Observer {
    Repository repository;

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

//        if (panel.equalsIgnoreCase("new") || panel.equalsIgnoreCase("edit")) {
//            cl.show(this, "TeacherDrawArea");
//        }
//        else if (panel.equalsIgnoreCase("solve")) {
//            cl.show(this, "StudentDrawArea");
//        }
//        else {
//            cl.show(this, panel);
//        }
    }
}
