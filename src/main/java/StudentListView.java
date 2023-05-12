
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class StudentListView extends JPanel implements Observer {
    Repository repository = Repository.getInstance();
    JPanel problems;
    JPanel panelCenter;
    public StudentListView() {
        repository.addObserver(this);
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
        add(attempt, BorderLayout.SOUTH);

        panelCenter = new JPanel();
        BorderLayout problemCenter = new BorderLayout();
        panelCenter.setLayout(problemCenter);

        panelCenter.add(listProblems(), BorderLayout.WEST);
        add(panelCenter, BorderLayout.CENTER);
    }

    private JPanel listProblems()
    {

        problems = new JPanel();
        BoxLayout problemBoxes = new BoxLayout(problems,BoxLayout.PAGE_AXIS);
        problems.setLayout(problemBoxes);

        File file = new File("./Drawings/");
        File[] files = file.listFiles();

        ArrayList<JRadioButton> buttons = new ArrayList<>();


        for(File f: files )
        {

            if(f.getName().endsWith(".json"))
            {
                String fileName = f.getName();
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                JRadioButton temp = new JRadioButton(fileName);
                temp.addActionListener(new MainController());
                temp.setFont(new Font("Serif", Font.PLAIN, 28));
                temp.setSize(6, 6);
                buttons.add(temp);
            }
        }

        ButtonGroup bg = new ButtonGroup();

        for(JRadioButton button: buttons)
        {
            bg.add(button);
        }

        for(JRadioButton button: buttons)
        {
            problems.add(button);
        }
        return problems;

    }
    @Override
    public void update(Observable o, Object arg)
    {
        panelCenter.remove(problems);
        panelCenter.add(listProblems());
    }
}
