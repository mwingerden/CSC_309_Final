
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Creates View for the Teacher, should include list of problem sets that are creatable/editable
 */
public class TeacherListView extends JPanel {
    public TeacherListView() {
        MainController mainController = new MainController();

        setBackground(Color.LIGHT_GRAY);
        ArrayList<JRadioButton> problems = new ArrayList<>();
        for(int i = 0; i < 3; i++){

        }

        ButtonGroup problemGroup = new ButtonGroup();
        JPanel WestPanel = new JPanel();
        for(JRadioButton j : problems){
            problemGroup.add(j);
            WestPanel.add(j);
            // j.addActionListener(mainController);
        }

        JPanel southPanel = new JPanel();
        JButton newButton = new JButton("New");
        newButton.addActionListener(mainController);
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        southPanel.add(newButton);
        southPanel.add(editButton);
        southPanel.add(deleteButton);



        WestPanel.setBackground(Color.GRAY);
        GridLayout grid = new GridLayout(problems.size() + 1,1);
        WestPanel.setLayout(grid);
        WestPanel.add(southPanel);

        JPanel QA_Hint_Field = new JPanel();

        WorkSpace workSpace = new WorkSpace();
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        add(WestPanel, BorderLayout.WEST);
        add(workSpace, BorderLayout.CENTER);
    }


}
