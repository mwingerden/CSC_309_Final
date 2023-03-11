import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(){
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

        file.add(newItem);
        file.add(save);
        file.add(load);

        help.add(about);

        shape.add(ifElse);
        shape.add(command);
        shape.add(start);
        shape.add(end);

        menubar.add(file);
        menubar.add(help);
        menubar.add(shape);

        add(menubar);
    }
}
