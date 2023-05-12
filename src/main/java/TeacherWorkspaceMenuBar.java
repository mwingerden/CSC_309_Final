
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * The Main.MenuBar class where the user will use menu items to interact with the application.
 */
public class TeacherWorkspaceMenuBar extends JMenuBar {
    /**
     * Main.MenuBar constructor sets up all the needed menu for the user.
     */
    public TeacherWorkspaceMenuBar(){
        MainController mainController = new MainController();
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenu shape = new JMenu("Shape");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Main.Save");
        JMenuItem load = new JMenuItem("Main.Load");
        JMenuItem about = new JMenuItem("About");
        JMenuItem ifElse = new JMenuItem("If/Else");
        JMenuItem command = new JMenuItem("Instruct");
        JMenuItem start = new JMenuItem("Start");
        JMenuItem end = new JMenuItem("End");
        JMenuItem method = new JMenuItem("Method");
        JMenuItem io = new JMenuItem("I/O");
        JMenuItem var = new JMenuItem("Variable");
        JMenuItem arrow = new JMenuItem("Main.Arrow");
        newItem.addActionListener(mainController);
        save.addActionListener(mainController);
        load.addActionListener(mainController);
        ifElse.addActionListener(mainController);
        command.addActionListener(mainController);
        start.addActionListener(mainController);
        end.addActionListener(mainController);
        method.addActionListener(mainController);
        io.addActionListener(mainController);
        var.addActionListener(mainController);
        arrow.addActionListener(mainController);
        file.add(newItem);
        file.add(save);
        file.add(load);
        help.add(about);
        shape.add(start);
        shape.add(end);
        shape.add(ifElse);
        shape.add(command);
        shape.add(method);
        shape.add(io);
        shape.add(var);
        shape.add(arrow);
        menubar.add(file);
        menubar.add(help);
        menubar.add(shape);
        add(menubar);
    }
}
