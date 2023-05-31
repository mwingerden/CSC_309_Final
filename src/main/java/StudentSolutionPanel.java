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
        JLabel problemTitle = new JLabel(Repository.getInstance()
                .getLoadedProblem()
                .getProblemName(), SwingConstants.LEFT);
        problemTitle.setFont(new Font("Serif", Font.BOLD, 24));
        problemTitle.setVerticalAlignment(SwingConstants.TOP);

        // Problem Description
        JTextArea problemDescription = new JTextArea(Repository.getInstance()
                .getLoadedProblem()
                .getProblemDescription());
        problemDescription.setEditable(false);

        // Hints
        this.problemHintArea = new JTextArea("Click button below for hint.");
        this.problemHintArea.setEditable(false);

        this.hintList = Repository.getInstance().getLoadedProblem().getHints();

        this.hintButton = new JButton("Get Hint");
        hintButton.addActionListener(this::updateHints);


        problemInfoPanel.add(problemTitle);
        problemInfoPanel.add(problemDescription);
        problemInfoPanel.add(this.problemHintArea);
        problemInfoPanel.add(hintButton);

        add(problemInfoPanel, BorderLayout.WEST);
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

    @Override
    public void update(Observable o, Object arg) {
        if (!Objects.isNull(arg) && ((String) arg).equals("StudentSolutionPanel")) {
            this.remove(problemInfoPanel);
            this.hintIndex = 0;
            this.setupProblemInfoPanel();
        }
    }
}
