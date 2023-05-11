
import javax.swing.*;

public class StudentMenuBar extends JMenuBar {

    public StudentMenuBar() {
        MainController controller = new MainController();
        JMenuBar menuBar = new JMenuBar();
        JMenuItem home = new JMenuItem("Home");
        home.addActionListener(controller);
        menuBar.add(home);
        add(menuBar);
    }
}
