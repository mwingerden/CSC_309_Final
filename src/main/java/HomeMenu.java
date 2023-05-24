
import javax.swing.*;

public class HomeMenu extends JMenuBar {

    public HomeMenu() {
        MainController controller = new MainController();
        JMenuBar menuBar = new JMenuBar();
        JMenuItem home = new JMenuItem("Home");
        home.addActionListener(controller);
        menuBar.add(home);
        add(menuBar);
    }
}
