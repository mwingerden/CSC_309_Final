import java.awt.*;
import javax.swing.*;

/**
 * The Main class sets the window up ,adds the work space and menu bar for the user to see.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
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
        MenuBar menuBar = new MenuBar();
        StatusBar statusBar = new StatusBar(77);
        WorkSpace workSpace = new WorkSpace();

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        add(menuBar, BorderLayout.NORTH);
        add(workSpace, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
    }
}