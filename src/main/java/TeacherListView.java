
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creates View for the Teacher, should include list of problem sets that are creatable/editable
 */
public class TeacherListView extends JPanel {

    private List<String> teacherProblemNames;

    private ButtonGroup problemButtons;

    private TeacherWorkspace workspace;

    private JPanel problemsPanel;

    private final JLabel selectProblem = new JLabel("Select problem from the left or create a new one to begin.");

    private boolean problemNotSelected = true;

    private String loadedWorkspace = "";

    public TeacherListView() {

        setBackground(Color.LIGHT_GRAY);

        getTeacherProblemNames();

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        HomeMenu menuBar = new HomeMenu();
        add(menuBar, BorderLayout.PAGE_START);
        setupProblemPanel();

        this.selectProblem.setFont(new Font("Serif", Font.BOLD, 36));
        add(this.problemsPanel, BorderLayout.WEST);
        add(this.selectProblem, BorderLayout.CENTER);
    }

    private void setupProblemPanel() {
        ArrayList<JRadioButton> problems = new ArrayList<>();
        for(String problemName: this.teacherProblemNames){
            problems.add(new JRadioButton(problemName));
        }

        this.problemsPanel = new JPanel();
        this.problemButtons = new ButtonGroup();
        for(JRadioButton button : problems){
            problemButtons.add(button);
            this.problemsPanel.add(button);
        }

        JPanel southPanel = new JPanel();
        JButton newButton = new JButton("New");
        newButton.addActionListener(this::createNewProblem);
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this::openProblem);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteProblem);
        southPanel.add(newButton);
        southPanel.add(editButton);
        southPanel.add(deleteButton);

        this.problemsPanel.setBackground(Color.GRAY);
        GridLayout grid = new GridLayout(this.problemButtons.getButtonCount() + 1,1);
        this.problemsPanel.setLayout(grid);
        this.problemsPanel.add(southPanel);
    }

    private void createNewProblem(ActionEvent e) {
        try {
            String name = Repository.getInstance().saveList(null);
            if (Objects.isNull(name)) {
                return;
            }
            this.teacherProblemNames.add(name);
            this.remove(this.problemsPanel);
            this.setupProblemPanel();
            this.add(this.problemsPanel, BorderLayout.WEST);
            openProblem(e);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void openProblem(ActionEvent e) {
        if (e.getActionCommand().equals("New")) {
            this.loadedWorkspace = Repository.getInstance().getLoadedProblem().getProblemName();
            Repository.getInstance().loadList(true, this.loadedWorkspace);
            if (this.problemNotSelected) {
                remove(this.selectProblem);
                this.problemNotSelected = false;
            } else {
                this.remove(this.workspace);
            }
            this.workspace = new TeacherWorkspace();
        } else {
            for (Enumeration<AbstractButton> buttons = this.problemButtons.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    this.loadedWorkspace = button.getText();
                    Repository.getInstance().loadList(true, this.loadedWorkspace);
                    if (this.problemNotSelected) {
                        remove(this.selectProblem);
                        this.problemNotSelected = false;
                    } else {
                        this.remove(this.workspace);
                    }
                    this.workspace = new TeacherWorkspace();
                    break;
                }
            }
        }
        this.add(this.workspace,BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void deleteProblem(ActionEvent e) {
        for (Enumeration<AbstractButton> buttons = this.problemButtons.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                if (this.loadedWorkspace.equals(button.getText())) {
                    remove(this.workspace);
                    this.problemNotSelected = true;
                    add(this.selectProblem, BorderLayout.CENTER);
                }
                this.teacherProblemNames.remove(button.getText());
                Repository.getInstance().delete(button.getText());
                this.remove(this.problemsPanel);
                this.setupProblemPanel();
                this.add(this.problemsPanel, BorderLayout.WEST);
                this.revalidate();
                this.repaint();
                break;
                }
            }
    }

    private void getTeacherProblemNames() {
        try (Stream<Path> stream = Files.list(Paths.get("Drawings/"))) {
            this.teacherProblemNames = stream
                    .filter(file -> !Files.isDirectory(file) && file.getFileName().toString().endsWith(".json"))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException npe) {
            JOptionPane.showMessageDialog(
                    null,
                    "There is no Drawings directory.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
        if (!this.teacherProblemNames.isEmpty()) {
            for (int index = 0; index < this.teacherProblemNames.size(); index++ ){
                StringBuilder fileName = new StringBuilder(this.teacherProblemNames.get(index));
                fileName.delete(fileName.length() - 5, fileName.length());
                this.teacherProblemNames.set(index, fileName.toString());
            }
        }
    }
}
