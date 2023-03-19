import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(){
        MainController mainController = new MainController();
        JMenuBar menubar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenu shape = new JMenu("Shape");

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem about = new JMenuItem("About");
        JMenuItem ifElse = new JMenuItem("If");
        JMenuItem command = new JMenuItem("Instr");
        JMenuItem start = new JMenuItem("Start");
        JMenuItem end = new JMenuItem("End");
        JMenuItem io = new JMenuItem("I/O");
        JMenuItem var = new JMenuItem("Var");
        JMenuItem arrow = new JMenuItem("Arrow");

        newItem.addActionListener(mainController);
        ifElse.addActionListener(mainController);
        command.addActionListener(mainController);
        start.addActionListener(mainController);
        end.addActionListener(mainController);
        io.addActionListener(mainController);
        var.addActionListener(mainController);
        arrow.addActionListener(mainController);

        file.add(newItem);
        file.add(save);
        file.add(load);

        help.add(about);

        shape.add(ifElse);
        shape.add(command);
        shape.add(start);
        shape.add(end);
        shape.add(io);
        shape.add(var);
        shape.add(arrow);

        menubar.add(file);
        menubar.add(help);
        menubar.add(shape);

        add(menubar);
    }
}
