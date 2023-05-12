
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
        setLayout(new CardLayout());
        add(new StartUp(), "StartUp");
        //TODO: This is where you would add the panels.
        //TODO: Just call add then the construction of the Panel along with a name descriptor as seen in the rest of the method.
        //TODO: The name descriptor will be used in the update below.
        add(new StudentListView(), "StudentListView");
        add(new TeacherListView(), "TeacherListView");
//        add(new WorkSpace("teacher"), "TeacherDrawArea");
        add(new StudentDrawArea(), "StudentDrawArea");
        add(new WorkSpace(), "WorkSpace");
//        add(new WorkSpace("teacher"), "TeacherList");
//        add(new WorkSpace("student"), "StudentList");
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO: This is where the panel will be updated.
        //TODO: The repository will update the panel with the name of the panel to switch to.
        //TODO: The name should correlate to the name declared in the constructor when you add a new panel.
        if (arg != null) {
            String panel = (String) arg;
            CardLayout cl = (CardLayout) (this.getLayout());
            switch (panel) {
                case "StudentListView" -> cl.show(this, "StudentListView");
                case "TeacherListView" -> cl.show(this, "TeacherListView");
                case "StudentDrawArea" -> cl.show(this, "StudentDrawArea");
                case "StartUp" -> cl.show(this, "StartUp");
                case "WorkSpace" -> cl.show(this, "WorkSpace");
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
