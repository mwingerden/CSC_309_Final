import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * The panel that allows the student to attempt a problem. The left side contains the progress, hints, feedback, and the
 * description of what a flowchart should look like.
 */
public class StudentSolutionPanel extends JPanel implements Observer {

    private List<String> hintList;

    int hintIndex = 0;

    private JTextArea problemHintArea;

    private JPanel problemInfoPanel;

    private JButton hintButton;

    private JComboBox progressMenu;

    private JLabel problemTitle;

    private JTextArea feedback;

    private JButton problemListView;

    private JProgressBar problemBar;

    /**
     * Constructs the panel that allows the student to attempt a problem. The left side contains the progress, hints,
     *  feedback, and the description of what a flowchart should look like.
     */
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
        BoxLayout problemInfoLayout = new BoxLayout(problemInfoPanel, BoxLayout.PAGE_AXIS);
        problemInfoPanel.setLayout(problemInfoLayout);
        problemInfoPanel.setSize(new Dimension(300, 800));

        // Problem Title
        this.problemTitle = new JLabel(Repository.getInstance()
                .getLoadedProblem()
                .getProblemName());
        problemTitle.setFont(new Font("Serif", Font.BOLD, 24));
        this.problemTitle.setPreferredSize(new Dimension(300, 20));

        //Problem Progress
        String[] progress = {"Complete","In Progress" ,"Incomplete" };
        this.progressMenu = new JComboBox<>(progress);
        this.progressMenu.setPreferredSize(new Dimension(300, 20));


        //going back to problem list
        this.problemListView = new JButton("Go to Problem List");
        this.problemListView.setPreferredSize(new Dimension(300, 20));

        //problem progress bar
        this.problemBar = new JProgressBar();
        this.problemBar.setValue(0);
        this.problemBar.setStringPainted(true);



        // Problem Description
        JTextArea problemDescription = new JTextArea(Repository.getInstance()
                .getLoadedProblem()
                .getProblemDescription());
        problemDescription.setEditable(false);
        JScrollPane problemDescScroll = new JScrollPane(problemDescription,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        problemDescScroll.setPreferredSize(new Dimension(300, 300));


        //status bar
        StatusBar status = new StatusBar();
        status.setPreferredSize(new Dimension(300, 50));
        status.setMaximumSize(new Dimension(300, 100));

        // Hints
        this.problemHintArea = new JTextArea("Click button below for hint.");
        this.problemHintArea.setEditable(false);
        JScrollPane problemHintScroll = new JScrollPane(this.problemHintArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        problemHintScroll.setPreferredSize(new Dimension(300, 300));

        //feedback
        this.feedback = new JTextArea("feedback");
        this.feedback.setEditable(false);
        JScrollPane feedbackScroll = new JScrollPane(this.feedback,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        feedbackScroll.setPreferredSize(new Dimension(300, 300));

        this.hintList = Repository.getInstance().getLoadedProblem().getHints();

        this.hintButton = new JButton("Get Hint");
        hintButton.addActionListener(this::updateHints);
        progressMenu.addActionListener(this::updateProgress);
        problemListView.addActionListener(new MainController());

        problemInfoPanel.add(this.problemTitle);
        problemInfoPanel.add(this.progressMenu);
        problemInfoPanel.add(this.problemBar);
        problemInfoPanel.add(this.problemListView);
        problemInfoPanel.add(problemDescScroll);
        problemInfoPanel.add(problemHintScroll);
        problemInfoPanel.add(hintButton);
        problemInfoPanel.add(feedbackScroll);
        problemInfoPanel.add(status);

        add(problemInfoPanel, BorderLayout.WEST);
    }

    private void updateHints(ActionEvent e) {
        if (Objects.isNull(this.hintList) || this.hintList.isEmpty()) {
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

    /**
     * Update the progress label shown in the panel.
     * @param s string containing "complete", "in progress", or "incomplete".
     */
    public void updateProgresstext(String s){
        if (s.equals("complete")) {
            this.progressMenu.setSelectedIndex(0);
        } else if (s.equals("in progress")){
            this.progressMenu.setSelectedIndex(1);
        } else {
            this.progressMenu.setSelectedIndex(2);
        }
    }


    private void updateProgress(ActionEvent e) {
        if(this.progressMenu.getSelectedItem().toString().equals("Incomplete")) {
            this.problemTitle.setForeground(Color.RED);
            this.problemBar.setValue(0);
            this.problemBar.setForeground(Color.RED);
        } else if(this.progressMenu.getSelectedItem().toString().equals("In Progress")) {
            this.problemTitle.setForeground(Color.YELLOW);
            this.problemBar.setValue(50);
        } else if(this.progressMenu.getSelectedItem().toString().equals("Complete")) {
            this.problemTitle.setForeground(Color.GREEN);
            this.problemBar.setValue(100);
            this.problemBar.setForeground(Color.GREEN);
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
        repaint();
    }
}
