import javax.swing.*;
import java.awt.*;
/**
 * The Main class sets the window up ,adds the work space and menu bar for the user to see.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
 * @author  Mary Lemmer
 */
public class Main extends JFrame{
    public static void main(String[] args) {
        Main main = new Main();
        main.setSize(1200, 800);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setLocationRelativeTo(null);
        main.setVisible(true);
    }
    /**
     * Main constructor method adds the menu, work space, and status bar to the window.
     */
    public Main() {
        super("Final Project");
        setLayout(new CardLayout());

        add(new PanelLayout(), BorderLayout.CENTER);
//        com.tests.MenuBar menuBar = new com.tests.MenuBar();
//        com.tests.StatusBar statusBar = new com.tests.StatusBar(77);
//        com.tests.WorkSpace workSpace = new com.tests.WorkSpace();
//        BorderLayout layout = new BorderLayout();
//        setLayout(layout);
//        add(menuBar, BorderLayout.NORTH);
//        add(workSpace, BorderLayout.CENTER);
//        add(statusBar, BorderLayout.SOUTH);
    }
}