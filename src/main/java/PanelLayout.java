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
        add(new StartUp(), "com.tests.StartUp");
        //TODO: This is where you would add the panels.
        //TODO: Just call add then the construction of the Panel along with a name descriptor as seen in the rest of the method.
        //TODO: The name descriptor will be used in the update below.
        add(new StudentListView(), "com.tests.StudentListView");
        add(new TeacherListView(), "com.tests.TeacherListView");
//        add(new com.tests.WorkSpace("teacher"), "TeacherDrawArea");
        add(new StudentDrawArea(), "StudentDrawArea");
//        add(new com.tests.WorkSpace("teacher"), "TeacherList");
//        add(new com.tests.WorkSpace("student"), "StudentList");
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO: This is where the panel will be updated.
        //TODO: The repository will update the panel with the name of the panel to switch to.
        //TODO: The name should correlate to the name declared in the constructor when you add a new panel.
        String panel = (String) arg;
        CardLayout cl = (CardLayout)(this.getLayout());
        switch(panel) {
            case "com.tests.StudentListView" -> cl.show(this, "com.tests.StudentListView");
            case "com.tests.TeacherListView" -> cl.show(this, "com.tests.TeacherListView");
            case "StudentDrawArea" -> cl.show(this, "StudentDrawArea");
            case "com.tests.StartUp" -> cl.show(this, "com.tests.StartUp");
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
