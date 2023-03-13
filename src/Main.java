import java.awt.*;
import javax.swing.*;

public class Main extends JFrame{

    public static void main(String[] args) {
        Main main = new Main();
        main.setSize(800, 800);
        main.setDefaultCloseOperation(EXIT_ON_CLOSE);
        main.setVisible(true);
    }

    public Main() {
        super("Final Project");
        MenuBar menuBar = new MenuBar();
        StatusBar statusBar = new StatusBar(70);
        WorkSpace workSpace = new WorkSpace();


        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        add(menuBar, BorderLayout.NORTH);
        add(workSpace, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
    }
}