import javax.swing.*;
import java.awt.*;

public class TeacherListView extends JPanel {
    public TeacherListView() {
        setBackground(Color.LIGHT_GRAY);

        JComboBox colorList = new JComboBox();

        JRadioButton problem1 = new JRadioButton("Problem 1");
        JRadioButton problem2 = new JRadioButton("Problem 2");
        JRadioButton problem3 = new JRadioButton("Problem 3");

        ButtonGroup problemGroup = new ButtonGroup();
        JPanel WestPanel = new JPanel();
        WestPanel.setBackground(Color.GRAY);
        GridLayout grid = new GridLayout(7,1);
        WestPanel.setLayout(grid);
        WestPanel.add(problem1);
        WestPanel.add(problem2);
        WestPanel.add(problem3);

        WorkSpace workSpace = new WorkSpace();


        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        add(WestPanel, BorderLayout.WEST);
        add(workSpace, BorderLayout.CENTER);
    }
}
