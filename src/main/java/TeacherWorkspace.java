
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * TeacherWorkspace class where all the blocks will be displayed on by the user's inputs.
 *
 * @author  Nathon Ho
 * @author  Matthew Wingerden
 * @author  Pablo Nguyen
 * @author  Juan Custodio
 * @author  Mary Lemmer
 */
public class TeacherWorkspace extends JPanel implements Observer {
    private Repository repository;

    private final JTextArea hintText = new JTextArea();

    private final JTextArea descriptionText = new JTextArea();

    private final DocumentListener hintEdit = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            List<String> updatedHintList = List.of(hintText.getText().split("\n"));
            repository.getLoadedProblem().setHints(updatedHintList);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            List<String> updatedHintList = List.of(hintText.getText().split("\n"));
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
        JPanel workspacePanel = new JPanel();
        workspacePanel.setPreferredSize(new Dimension(300, 150));
        workspacePanel.setLayout(new BorderLayout());
        workspacePanel.setBackground(Color.DARK_GRAY);

        WorkspaceMenuBar workspaceMenuBar = new WorkspaceMenuBar();

        add(workspaceMenuBar, BorderLayout.NORTH);

        String problemEditing = "Currently Editing: "+repository.getLoadedProblem().getProblemName();
        JLabel problemTitle = new JLabel(problemEditing);
        problemTitle.setForeground(Color.WHITE);
        workspacePanel.add(problemTitle, BorderLayout.NORTH);

        JPanel problemAndHints = new JPanel(new GridLayout(1, 2));
        problemAndHints.add(descriptionArea());
        problemAndHints.add(hintTextArea());

        workspacePanel.add(problemAndHints, BorderLayout.CENTER);
        add(workspacePanel, BorderLayout.SOUTH);
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
        if (!Objects.isNull(repository.getLoadedProblem().getHints())) {
            String hintString = String.join("\n", repository.getLoadedProblem().getHints());
            hintText.setText(hintString);
        } else {
            hintText.setText("Enter hints separated by enter here.");
        }
        hintText.getDocument().addDocumentListener(hintEdit);
        hintText.setEditable(true);
        hintPanel.add(hintText);
        return new JScrollPane(hintText,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
        repaint();
    }
}