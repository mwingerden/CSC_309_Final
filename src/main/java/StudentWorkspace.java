
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * TeacherWorkspace class where all the blocks will be displayed on by the user's inputs.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
 * @author  Mary Lemmer
 */
public class StudentWorkspace extends JPanel implements Observer {
    private final Repository repository;

    private final StudentSolutionPanel studentSolutionPanel;

    /**
     * The TeacherWorkspace method sets up the layout of the panel.
     */
    public StudentWorkspace(StudentSolutionPanel studentSolutionPanel) {
        repository = Repository.getInstance();
        repository.addObserver(this);
        this.studentSolutionPanel = studentSolutionPanel;
        MainController controller = new MainController();
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(300, 300));
        addMouseListener(controller);
        addMouseMotionListener(controller);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        JPanel workspacePanel = new JPanel();
        workspacePanel.setLayout(new BoxLayout(workspacePanel, BoxLayout.Y_AXIS));
        JButton submit = new JButton("Submit");
        submit.addActionListener(this::runSolutionCheck);
        add(submit, BorderLayout.SOUTH);
        workspacePanel.setBackground(Color.PINK);

        WorkspaceMenuBar workspaceMenuBar = new WorkspaceMenuBar();
        add(workspaceMenuBar, BorderLayout.NORTH);
    }

    private void runSolutionCheck(ActionEvent e) {
        CodeToFlowchartValidator cToFChecker = new CodeToFlowchartValidator(Repository.getInstance().getDrawings(),
                Repository.getInstance().getLoadedProblem());
        if (cToFChecker.checkAgainstSolution()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Congrats! You have solved this problem correctly!",
                    "Problem Solved",
                    JOptionPane.INFORMATION_MESSAGE
            );
            Repository.getInstance().getLoadedProblem().setFeedback("Solved Correctly.");
            Repository.getInstance().getLoadedProblem().setProgress("complete");
            Repository.getInstance().setStatus("Saving diagram");
            Repository.getInstance().setBlockToDraw("None");
            try {
                Repository.getInstance().saveList(Repository.getInstance().getLoadedProblem(), false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "There seems to be errors in your solution. Please see the feedback panel for the issues " +
                            "found.",
                    "Error With Solution",
                    JOptionPane.INFORMATION_MESSAGE
            );
            String errorsFound = String.join("\n",cToFChecker.getErrors());
            Repository.getInstance().getLoadedProblem().setFeedback(errorsFound);
            Repository.getInstance().setStatus("Incorrect Diagram");
        }
    }

    /**
     * paintComponent method that allows the different blocks to be drawn on screen by user.
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Draw drawing : repository.getDrawings()) {
            drawing.draw(g);
        }
    }
    /**
     * update method handles the repainting after each component is drawn.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        revalidate();
        repaint();
    }
}