import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class StudentSolutionPanel extends JPanel {

    private List<String> hintList;

    int hintIndex = 0;

    private JTextArea problemHintArea;

    public StudentSolutionPanel() {
        BorderLayout majorityLayout = new BorderLayout();
        setLayout(majorityLayout);

        StudentMenuBar menuBar = new StudentMenuBar();
        add(menuBar, BorderLayout.PAGE_START);

        this.setupProblemInfoPanel();
        add(new StudentWorkspace(),BorderLayout.CENTER);
    }

    private void setupProblemInfoPanel() {
        JPanel problemInfoPanel = new JPanel();
        BoxLayout problemInfoLayout = new BoxLayout(problemInfoPanel, BoxLayout.PAGE_AXIS);
        problemInfoPanel.setLayout(problemInfoLayout);
        add(problemInfoPanel, BorderLayout.WEST);

        // Problem Title
        JLabel problemTitle = new JLabel(Repository.getInstance()
                .getLoadedProblem()
                .getProblemName(), JLabel.LEFT);
        problemTitle.setFont(new Font("Serif", Font.BOLD, 24));
        problemTitle.setVerticalAlignment(JLabel.TOP);

        // Problem Description
        JTextArea problemDescription = new JTextArea(Repository.getInstance()
                .getLoadedProblem()
                .getProblemDescription());
        problemDescription.setEditable(false);

        // Hints
        this.problemHintArea = new JTextArea("Click button below for hint.");
        this.problemHintArea.setEditable(false);

        // TODO: Replace with pulling hints from file.
        this.hintList = new ArrayList<>();
        this.hintList.add("Example Hint 1");
        this.hintList.add("Another Example Hint");
        this.hintList.add("And This Is Example Hint 3.");

        JButton hintButton = new JButton("Get Hint");
        hintButton.addActionListener(this::updateHints);


        problemInfoPanel.add(problemTitle);
        problemInfoPanel.add(problemDescription);
        problemInfoPanel.add(this.problemHintArea);
        problemInfoPanel.add(hintButton);

        add(problemInfoPanel, BorderLayout.WEST);
    }

    private void updateHints(ActionEvent e) {
        if (this.problemHintArea.getText().equals("Click button below for hint")) {
            this.problemHintArea.setText(this.hintList.get(0)+"\n");
            this.hintIndex++;
        } else {
            this.problemHintArea.append(this.hintList.get(this.hintIndex));
        }
    }
}
