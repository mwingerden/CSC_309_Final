
import javax.swing.*;

/**
 * The WorkspaceMenuBar class where the user will use menu items to interact with the application.
 */
public class WorkspaceMenuBar extends JMenuBar {
    /**
     * WorkspaceMenuBar constructor sets up all the needed menus for the user.
     */
    public WorkspaceMenuBar() {
        MainController mainController = new MainController();
        JMenuBar menuBar = new JMenuBar();
        JButton undo = new JButton("Undo");
        JButton redo = new JButton("Redo");
        JMenu file = new JMenu("Settings");
        JMenu shape = new JMenu("Shape");
        JMenuItem newItem = new JMenuItem("Clear");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem ifElse = new JMenuItem("If/Else");
        JMenuItem command = new JMenuItem("Instruct");
        JMenuItem start = new JMenuItem("Start");
        JMenuItem end = new JMenuItem("End");
        JMenuItem method = new JMenuItem("Method");
        JMenuItem io = new JMenuItem("I/O");
        JMenuItem variable = new JMenuItem("Variable");
        JMenuItem arrow = new JMenuItem("Arrow");

        newItem.addActionListener(mainController);
        save.addActionListener(mainController);
        ifElse.addActionListener(mainController);
        command.addActionListener(mainController);
        start.addActionListener(mainController);
        end.addActionListener(mainController);
        method.addActionListener(mainController);
        io.addActionListener(mainController);
        variable.addActionListener(mainController);
        arrow.addActionListener(mainController);
        undo.addActionListener(mainController);
        redo.addActionListener(mainController);


        file.add(newItem);
        file.add(save);
        shape.add(start);
        shape.add(end);
        shape.add(ifElse);
        shape.add(command);
        shape.add(method);
        shape.add(io);
        shape.add(variable);
        shape.add(arrow);
        menuBar.add(file);
        menuBar.add(shape);
        menuBar.add(undo);
        menuBar.add(redo);
        add(menuBar);
    }
}
