
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Creates View for the Teacher, should include list of problem sets that are creatable/editable
 */
public class TeacherListView extends JPanel implements Observer {
    MainController mainController = new MainController();

    JPanel WestPanel;

    JPanel southPanel;

    public TeacherListView() {
        Repository.getInstance().addObserver(this);

        setBackground(Color.LIGHT_GRAY);
        ArrayList<String> problems = Load.get_names();
        ButtonGroup problemGroup = new ButtonGroup();
        WestPanel = new JPanel();
        for(String s : problems){
            JRadioButton j = new JRadioButton(s);
            problemGroup.add(j);
            WestPanel.add(j);
            j.addActionListener(mainController);
        }

        southPanel = new JPanel();
        JButton newButton = new JButton("New");
        newButton.addActionListener(mainController);
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(mainController);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(mainController);
        southPanel.add(newButton);
        southPanel.add(editButton);
        southPanel.add(deleteButton);

        WestPanel.setBackground(Color.GRAY);
        GridLayout grid = new GridLayout(problems.size() + 1,1);
        WestPanel.setLayout(grid);

        JPanel QA_Hint_Field = new JPanel();

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        add(WestPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }


    @Override
    public void update(Observable o, Object arg) {
        this.remove(WestPanel);
        WestPanel = new JPanel();
        ArrayList<String> problems = Load.get_names();
        ButtonGroup problemGroup = new ButtonGroup();
        WestPanel.setLayout(new GridLayout(problems.size() + 1,1));
        for(String s : problems){
            JRadioButton j = new JRadioButton(s);
            problemGroup.add(j);
            WestPanel.add(j);
            j.addActionListener(mainController);
        }


        add(southPanel,BorderLayout.SOUTH);
        add(WestPanel, BorderLayout.CENTER);

    }
}
