
import javax.swing.*;

/**
 * The MenuBar class where the user will use menu items to interact with the application.
 */
public class WorkspaceMenuBar extends JMenuBar {
    /**
     * MenuBar constructor sets up all the needed menu for the user.
     */
    public WorkspaceMenuBar() {
        MainController mainController = new MainController();
        JMenuBar menuBar = new JMenuBar();
        JButton undo = new JButton("Undo");
        JButton redo = new JButton("Redo");
        JMenuItem home = new JMenuItem("Home");
        JMenu file = new JMenu("Settings");
//        JMenu help = new JMenu("Help");
        JMenu shape = new JMenu("Shape");
        JMenuItem newItem = new JMenuItem("Clear");
        JMenuItem save = new JMenuItem("Save");
//        JMenuItem load = new JMenuItem("Load");
//        JMenuItem about = new JMenuItem("About");
        JMenuItem ifElse = new JMenuItem("If/Else");
        JMenuItem command = new JMenuItem("Instruct");
        JMenuItem start = new JMenuItem("Start");
        JMenuItem end = new JMenuItem("End");
        JMenuItem method = new JMenuItem("Method");
        JMenuItem io = new JMenuItem("I/O");
        JMenuItem var = new JMenuItem("Variable");
        JMenuItem arrow = new JMenuItem("Arrow");


        home.addActionListener(mainController);
        newItem.addActionListener(mainController);
        save.addActionListener(mainController);
//        load.addActionListener(mainController);
        ifElse.addActionListener(mainController);
        command.addActionListener(mainController);
        start.addActionListener(mainController);
        end.addActionListener(mainController);
        method.addActionListener(mainController);
        io.addActionListener(mainController);
        var.addActionListener(mainController);
        arrow.addActionListener(mainController);
        undo.addActionListener(mainController);
        redo.addActionListener(mainController);


        file.add(newItem);
        file.add(save);
//        file.add(load);
//        help.add(about);
        shape.add(start);
        shape.add(end);
        shape.add(ifElse);
        shape.add(command);
        shape.add(method);
        shape.add(io);
        shape.add(var);
        shape.add(arrow);
        menuBar.add(file);
//        menuBar.add(help);
        menuBar.add(shape);
        menuBar.add(undo);
        menuBar.add(redo);
        menuBar.add(home);
        add(menuBar);
    }
}
