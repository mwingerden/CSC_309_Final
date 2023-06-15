
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * TeacherWorkspace class where all the blocks will be displayed on by the teacher's inputs and where a teacher can
 * input important information like the code version of the flowchart and general hints.
 */
public class TeacherWorkspace extends JPanel implements Observer {
    private final Repository repository;

    private JPanel workspacePanel = new JPanel();
    private final JTextArea hintText = new JTextArea();

    private final JTextArea descriptionText = new JTextArea();

    private final DocumentListener hintEdit = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            List<String> updatedHintList = new ArrayList<>(List.of(hintText.getText().split("\n")));
            repository.getLoadedProblem().setHints(updatedHintList);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            List<String> updatedHintList = new ArrayList<>(List.of(hintText.getText().split("\n")));
            repository.getLoadedProblem().setHints(updatedHintList);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // not used for checking if text changed.
        }
    };

    private final DocumentListener descriptionEdit = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            repository.getLoadedProblem().setProblemDescription(descriptionText.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            repository.getLoadedProblem().setProblemDescription(descriptionText.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // not used for checking if text changed.
        }
    };


    /**
     * The TeacherWorkspace method sets up the layout of the panel.
     */
    public TeacherWorkspace() {
        repository = Repository.getInstance();
        repository.addObserver(this);
        MainController controller = new MainController();
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(300, 300));
        addMouseListener(controller);
        addMouseMotionListener(controller);
        addMouseMotionListener(controller);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        this.workspacePanel = new JPanel();
        workspacePanel.setPreferredSize(new Dimension(300, 150));
        workspacePanel.setLayout(new BorderLayout());
        workspacePanel.setBackground(Color.DARK_GRAY);

        WorkspaceMenuBar workspaceMenuBar = new WorkspaceMenuBar();

        add(workspaceMenuBar, BorderLayout.NORTH);

        currentProblemAndGenHintsButton();
        problemAndHintsPanel();
        add(workspacePanel, BorderLayout.SOUTH);
    }

    private void currentProblemAndGenHintsButton() {
        JPanel nameAndAutoHintButtonPanel = new JPanel(new BorderLayout());
        nameAndAutoHintButtonPanel.setBackground(Color.DARK_GRAY);
        String problemEditing = "Currently Editing: "+repository.getLoadedProblem().getProblemName();
        JLabel problemTitle = new JLabel(problemEditing);
        problemTitle.setForeground(Color.WHITE);
        nameAndAutoHintButtonPanel.add(problemTitle, BorderLayout.WEST);

        JButton autoGenHints = new JButton("Auto\nGenerate\nHints");
        autoGenHints.addActionListener(this::autoGenHints);
        nameAndAutoHintButtonPanel.add(autoGenHints, BorderLayout.EAST);

        workspacePanel.add(nameAndAutoHintButtonPanel, BorderLayout.NORTH);
    }

    private void problemAndHintsPanel() {
        JPanel problemAndHints = new JPanel(new GridLayout(1, 2));
        problemAndHints.add(descriptionArea());
        problemAndHints.add(hintTextArea());

        workspacePanel.add(problemAndHints, BorderLayout.CENTER);
    }

    private JScrollPane descriptionArea() {
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(Color.white);
        if (!Objects.isNull(repository.getLoadedProblem().getProblemDescription())) {
            descriptionText.setText(repository.getLoadedProblem().getProblemDescription());
        } else {
            descriptionText.setText("Enter problem to recreate here.");
        }
        descriptionText.getDocument().addDocumentListener(descriptionEdit);
        descriptionText.setEditable(true);
        descriptionPanel.add(descriptionText);
        return new JScrollPane(descriptionText,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private JScrollPane hintTextArea() {
        JPanel hintPanel = new JPanel();
        hintPanel.setBackground(Color.white);
        setHintText();
        hintText.getDocument().addDocumentListener(hintEdit);
        hintText.setEditable(true);
        hintPanel.add(hintText);
        return new JScrollPane(hintText,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void setHintText() {
        if (!Objects.isNull(repository.getLoadedProblem().getHints())) {
            String hintString = String.join("\n", repository.getLoadedProblem().getHints());
            hintText.setText(hintString);
        } else {
            hintText.setText("Enter hints separated by enter here.");
        }
    }

    private void autoGenHints(ActionEvent e) {
        Problem loadedProblem = Repository.getInstance().getLoadedProblem();
        loadedProblem.autoGenHints();
        this.workspacePanel.remove(1);
        problemAndHintsPanel();
        revalidate();

    }

    /**
     * paintComponent method that allows the different blocks to be drawn on screen by user.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
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
        repaint();
    }
}