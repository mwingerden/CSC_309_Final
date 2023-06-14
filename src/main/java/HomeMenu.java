
import javax.swing.*;

/**
 * Class that creates a JMenuBar that contains only one item labeled home that is connected to an ActionListener in the
 * MainController class.
 */
public class HomeMenu extends JMenuBar {

    /**
     * Creates and adds a JMenuBar to the panel this object is created on. This JMenuBar only has one item labeled
     * "Home" that has an ActionListener connected to the MainController.
     */
    public HomeMenu() {
        MainController controller = new MainController();
        JMenuBar menuBar = new JMenuBar();
        JMenuItem home = new JMenuItem("Home");
        home.addActionListener(controller);
        menuBar.add(home);
        add(menuBar);
    }
}
