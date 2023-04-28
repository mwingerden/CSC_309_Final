package Main;

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
        add(new StartUp(), "Main.StartUp");
        //TODO: This is where you would add the panels.
        //TODO: Just call add then the construction of the Panel along with a name descriptor as seen in the rest of the method.
        //TODO: The name descriptor will be used in the update below.
        add(new StudentListView(), "Main.StudentListView");
        add(new TeacherListView(), "Main.TeacherListView");
//        add(new Main.WorkSpace("teacher"), "TeacherDrawArea");
        add(new StudentDrawArea(), "StudentDrawArea");
//        add(new Main.WorkSpace("teacher"), "TeacherList");
//        add(new Main.WorkSpace("student"), "StudentList");
    }

    @Override
    public void update(Observable o, Object arg) {
        //TODO: This is where the panel will be updated.
        //TODO: The repository will update the panel with the name of the panel to switch to.
        //TODO: The name should correlate to the name declared in the constructor when you add a new panel.
        String panel = (String) arg;
        CardLayout cl = (CardLayout)(this.getLayout());
        switch(panel) {
            case "Main.StudentListView" -> cl.show(this, "Main.StudentListView");
            case "Main.TeacherListView" -> cl.show(this, "Main.TeacherListView");
            case "StudentDrawArea" -> cl.show(this, "StudentDrawArea");
            case "Main.StartUp" -> cl.show(this, "Main.StartUp");
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
