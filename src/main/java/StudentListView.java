
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class StudentListView extends JPanel{
    Repository repository;

    public StudentListView() {
        BorderLayout majorityLayout = new BorderLayout();
        setLayout(majorityLayout);

        StudentMenuBar menuBar = new StudentMenuBar();
        add(menuBar, BorderLayout.PAGE_START);

        JLabel selectProblem = new JLabel("Select Problem To Attempt:", JLabel.LEFT);
        selectProblem.setFont(new Font("Serif", Font.BOLD, 36));
        selectProblem.setVerticalAlignment(JLabel.TOP);
        add(selectProblem,BorderLayout.LINE_START);

        JButton attempt = new JButton("Attempt");
        attempt.addActionListener(e -> Repository.getInstance().updatePanel("StudentDrawArea"));
        //attempt.addActionListener(new MainController());
        add(attempt, BorderLayout.SOUTH);

        JPanel panelCenter = new JPanel();
        BorderLayout problemCenter = new BorderLayout();
        panelCenter.setLayout(problemCenter);

        JPanel problems = new JPanel();
        BoxLayout problemBoxes = new BoxLayout(problems,BoxLayout.PAGE_AXIS);
        problems.setLayout(problemBoxes);

        panelCenter.add(problems, BorderLayout.WEST);

        listProblems(problems);
        add(panelCenter, BorderLayout.CENTER);
    }

    private void listProblems(JPanel problemPanel) {
        File file = new File("./Drawings/");
        File[] files = file.listFiles();

        ArrayList<JRadioButton> buttons = new ArrayList<>();

        for(File f: files )
        {
            JRadioButton temp = new JRadioButton(f.getName());
            temp.addActionListener(new MainController());
            temp.setFont(new Font("Serif", Font.PLAIN, 28));
            temp.setSize(6,6);
            buttons.add(temp);
        }
        ButtonGroup bg = new ButtonGroup();

        for(JRadioButton button: buttons)
        {
            bg.add(button);
        }

        for(JRadioButton button: buttons)
        {
            problemPanel.add(button);
        }
//        JRadioButton p1 = new JRadioButton("Problem 1: Basic");
//        JRadioButton p2 = new JRadioButton("Problem 2: Intermediate");
//        JRadioButton p3 = new JRadioButton("Problem 3: Advanced");
//
//        ButtonGroup bg = new ButtonGroup();
//        bg.add(p1);
//        bg.add(p2);
//        bg.add(p3);
//
//        p1.setFont(new Font("Serif", Font.PLAIN, 28));
//        p2.setFont(new Font("Serif", Font.PLAIN, 28));
//        p3.setFont(new Font("Serif", Font.PLAIN, 28));
//
//        p1.setSize(6,6);
//        p2.setSize(6,6);
//        p3.setSize(6,6);
//
//        problemPanel.add(p1);
//        problemPanel.add(p2);
//        problemPanel.add(p3);

    }
}
