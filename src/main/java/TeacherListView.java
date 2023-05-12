
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creates View for the Teacher, should include list of problem sets that are creatable/editable
 */
public class TeacherListView extends JPanel {

    private List<String> teacherProblemNames;

    private final ButtonGroup problemButtons = new ButtonGroup();

    private TeacherWorkspace workspace;

    private final JPanel problemsPanel;

    private final JLabel selectProblem = new JLabel("Select problem from the left or create a new one to begin.");

    private boolean problemNotSelected = true;

    private String loadedWorkspace = "";

    public TeacherListView() {
        MainController mainController = new MainController();

        setBackground(Color.LIGHT_GRAY);
        getTeacherProblemNames();
        ArrayList<JRadioButton> problems = new ArrayList<>();
        for(String problemName: this.teacherProblemNames){
            problems.add(new JRadioButton(problemName));
        }

        this.problemsPanel = new JPanel();
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
        GridLayout grid = new GridLayout(problems.size() + 1,1);
        this.problemsPanel.setLayout(grid);
        this.problemsPanel.add(southPanel);

        JPanel QA_Hint_Field = new JPanel();

        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        add(this.problemsPanel, BorderLayout.WEST);

        this.selectProblem.setFont(new Font("Serif", Font.BOLD, 36));
        add(this.selectProblem, BorderLayout.CENTER);
    }

    private void createNewProblem(ActionEvent e) {
        try {
            String name = Repository.getInstance().saveList(null);
            JRadioButton newProblem = new JRadioButton(name);
            problemButtons.add(newProblem);
            this.problemsPanel.add(newProblem);
            openProblem(e);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void openProblem(ActionEvent e) {
        if (e.getActionCommand().equals("New")) {
            this.loadedWorkspace = Repository.getInstance().getLoadedProblem().getProblemName();
            this.workspace = new TeacherWorkspace(this.loadedWorkspace);
            if (this.problemNotSelected) {
                remove(this.selectProblem);
                add(this.workspace,BorderLayout.CENTER);
                this.problemNotSelected = false;
            }
            this.problemsPanel.revalidate();
            this.problemsPanel.repaint();
            this.revalidate();
            this.repaint();
        } else {
            for (Enumeration<AbstractButton> buttons = this.problemButtons.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    this.loadedWorkspace = button.getText();
                    this.workspace = new TeacherWorkspace(this.loadedWorkspace);
                    if (this.problemNotSelected) {
                        remove(this.selectProblem);
                        add(this.workspace, BorderLayout.CENTER);
                        this.problemNotSelected = false;
                    }
                    this.problemsPanel.revalidate();
                    this.problemsPanel.repaint();
                    this.revalidate();
                    this.repaint();
                }
            }
        }
    }

    private void deleteProblem(ActionEvent e) {
        try {
            for (Enumeration<AbstractButton> buttons = this.problemButtons.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    if (this.loadedWorkspace.equals(button.getText())) {
                        remove(this.workspace);
                        this.problemNotSelected = true;
                        add(this.selectProblem, BorderLayout.CENTER);
                    }
                    File deleteProblem = new File("Drawings/" + button.getText() + ".json");
                    Files.deleteIfExists(deleteProblem.toPath());
                    this.problemButtons.remove(button);
                    this.teacherProblemNames.remove(button.getText());
                    remove(button);
                    this.problemsPanel.revalidate();
                    this.problemsPanel.repaint();
                    this.revalidate();
                    this.repaint();
                }
            }
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(
                    null,
                    "Selected problem unable to be found in Drawings directory.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
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
