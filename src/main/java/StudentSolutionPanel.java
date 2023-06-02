import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class StudentSolutionPanel extends JPanel implements Observer {

    private List<String> hintList;

    int hintIndex = 0;

    private JTextArea problemHintArea;

    private JPanel problemInfoPanel;

    private JButton hintButton;

    private JComboBox progressMenu;

    private JLabel problemTitle;

    private JTextArea feedback;

    public StudentSolutionPanel() {
        Repository.getInstance().addObserver(this);
        BorderLayout majorityLayout = new BorderLayout();
        setLayout(majorityLayout);

        HomeMenu menuBar = new HomeMenu();
        add(menuBar, BorderLayout.PAGE_START);

        this.setupProblemInfoPanel();
        add(new StudentWorkspace(),BorderLayout.CENTER);
    }

    private void setupProblemInfoPanel() {
        this.problemInfoPanel = new JPanel();
        this.problemInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        BoxLayout problemInfoLayout = new BoxLayout(problemInfoPanel, BoxLayout.PAGE_AXIS);
        problemInfoPanel.setLayout(problemInfoLayout);

        // Problem Title
         this.problemTitle = new JLabel(Repository.getInstance()
                .getLoadedProblem()
                .getProblemName(), SwingConstants.LEFT);
        problemTitle.setFont(new Font("Serif", Font.BOLD, 24));
        problemTitle.setVerticalAlignment(SwingConstants.TOP);

        //Problem Progress
        String[] progress = {"Complete","In Progress" ,"Incomplete" };
        this.progressMenu = new JComboBox(progress);

        // Problem Description
        JTextArea problemDescription = new JTextArea(Repository.getInstance()
                .getLoadedProblem()
                .getProblemDescription());
        problemDescription.setEditable(false);


        //feedback
        this.feedback = new JTextArea("feedback");


        // Hints
        this.problemHintArea = new JTextArea("Click button below for hint.");
        this.problemHintArea.setEditable(false);

        //feedback
        this.feedback = new JTextArea("feedback");

        this.hintList = Repository.getInstance().getLoadedProblem().getHints();

        this.hintButton = new JButton("Get Hint");
        hintButton.addActionListener(this::updateHints);
        progressMenu.addActionListener(this::updateProgress);

        problemInfoPanel.add(this.problemTitle);
        problemInfoPanel.add(this.progressMenu);
        problemInfoPanel.add(problemDescription);
        problemInfoPanel.add(this.problemHintArea);
        problemInfoPanel.add(hintButton);
        problemInfoPanel.add(feedback);

        add(problemInfoPanel, BorderLayout.WEST);
    }

    public void setFeedback(){

    }

    private void updateHints(ActionEvent e) {
        if (Objects.isNull(this.hintList)) {
            return;
        }
        if (this.problemHintArea.getText().equals("Click button below for hint.")) {
            this.problemHintArea.setText(this.hintList.get(0)+"\n");
            this.hintIndex++;
        } else {
            if (hintIndex == hintList.size()) {
                this.hintButton.setText("No More Hints Available.");
                this.hintButton.setEnabled(false);
            } else {
                this.problemHintArea.append(this.hintList.get(this.hintIndex) + "\n");
                this.hintIndex++;
            }
        }
        this.problemInfoPanel.repaint();
    }

    public void updateProgresstext(String s){
        if (s.equals("complete")) {
            this.progressMenu.setSelectedIndex(0);
        }
        else if (s.equals("in progress")){
            this.progressMenu.setSelectedIndex(1);
        }
        else{
            this.progressMenu.setSelectedIndex(2);
        }
    }


    private void updateProgress(ActionEvent e)
    {
       if(this.progressMenu.getSelectedItem().toString().equals("Incomplete"))
       {
           this.problemTitle.setForeground(Color.RED);
       }
       else if(this.progressMenu.getSelectedItem().toString().equals("In Progress"))
       {
           this.problemTitle.setForeground(Color.YELLOW);
       }
       else if(this.progressMenu.getSelectedItem().toString().equals("Complete"))
       {
           this.problemTitle.setForeground(Color.GREEN);
       }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!Objects.isNull(arg) && ((String) arg).equals("StudentSolutionPanel")) {

            this.remove(problemInfoPanel);
            this.hintIndex = 0;
            this.setupProblemInfoPanel();
        }
        Problem p = Repository.getInstance().getLoadedProblem();
        updateProgresstext(p.getProgress());
        this.feedback.setText(p.getFeedback());
    }
}
