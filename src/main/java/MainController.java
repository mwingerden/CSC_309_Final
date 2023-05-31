
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The MainController class handles the actions made by the user.
 */
public class MainController implements MouseMotionListener, ActionListener, MouseListener {
    int startDragx;
    int startDragy;
    int endDragx;
    int endDragy;

    private final List<String> drawingOptions = Arrays.asList("If/Else","Instruct","Start","End","I/O",
            "Method","Variable","Arrow");
    /**
     * actionPerformed class takes the user inputs and uses the repository to set/get blocks.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (drawingOptions.contains(e.getActionCommand())) {
            Repository.getInstance().setBlockToDraw(e.getActionCommand());
        }
        switch (e.getActionCommand()) {
            //TODO: The button press or other such actions are most likely be placed here.
            case "Teacher" -> Repository.getInstance().updatePanel("TeacherListView");
            case "Student" -> Repository.getInstance().updatePanel("StudentListView");
            case "Home" -> Repository.getInstance().updatePanel("StartUp");
            case "Undo" -> Repository.getInstance().UndoList();
            case "Redo" -> Repository.getInstance().RedoList();
            case "Submit" -> Repository.getInstance().saveStudentSubmission();
            default -> menuItemClicked(e.getActionCommand());
        }
    }

    /**
     * The mouse methods receive the actions by the user and creates blocks and arrows needed.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e) || (SwingUtilities.isRightMouseButton(e) && e.isControlDown())) {
            Repository.getInstance().blockText(e, e.getX(), e.getY());
        } else if (e.isShiftDown()) {
            //Repository.getInstance().deleteBlock(e.getX(), e.getY());
        } else {
            if (e.getButton() == MouseEvent.BUTTON1) {
                change_status(e);
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (Repository.getInstance().getBlockToDraw().equals("Arrow")) {
            Repository.getInstance().setStatus("Arrow is being drawn");
        }
        startDragx = e.getX();
        startDragy = e.getY();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        endDragx = e.getX();
        endDragy = e.getY();
        if (Repository.getInstance().getBlockToDraw().equals("Arrow")) {
            Repository.getInstance().addArrow(startDragx,startDragy,endDragx,endDragy);
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        Repository.getInstance().setStatus("Dragging");
        endDragx = e.getX();
        endDragy = e.getY();
        if(!Repository.getInstance().getBlockToDraw().equals("Arrow")){
            Repository.getInstance().drag(startDragx, startDragy, endDragx, endDragy);
            startDragx = endDragx;
            startDragy = endDragy;
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) {
    }
    private void menuItemClicked(String e) {
        switch (e) {
            case "New" -> {
                Repository.getInstance().clearBlocks();
                Repository.getInstance().setStatus("New diagram");
                Repository.getInstance().setBlockToDraw("None");
            }
            case "Save" -> {
                Repository.getInstance().setStatus("Saving diagram");
                Repository.getInstance().setBlockToDraw("None");
                try {
                    Repository.getInstance().saveList(Repository.getInstance().getLoadedProblem());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private void change_status(MouseEvent e){
        switch (Repository.getInstance().getBlockToDraw()) {
            case "If/Else" -> {
                Repository.getInstance().setStatus("Condition block was drawn");
                Repository.getInstance().addBlock(new ConditionBlock(e.getX() - 50, e.getY() - 50));
            }
            case "Instruct" -> {
                Repository.getInstance().setStatus("Command block was drawn");
                Repository.getInstance().addBlock(new InstructionBlock(e.getX() - 75, e.getY() - 32));
            }
            case "Start" -> {
                Repository.getInstance().setStatus("Starting block was drawn");
                Repository.getInstance().addBlock(new StartBlock(e.getX() - 40, e.getY() - 40, "PINK"));
            }
            case "End" -> {
                Repository.getInstance().setStatus("Ending block was drawn");
                Repository.getInstance().addBlock(new EndBlock(e.getX() - 40, e.getY() - 40, "BLUE"));
            }
            case "I/O" -> {
                Repository.getInstance().setStatus("Input/Output block was drawn");
                Repository.getInstance().addBlock(new InputOutputBlock(e.getX() - 30, e.getY()));
            }
            case "Method" -> {
                Repository.getInstance().setStatus("Method call block was drawn");
                Repository.getInstance().addBlock(new CallMethodBlock(e.getX() - 75, e.getY() - 32));
            }
            case "Variable" -> {
                Repository.getInstance().setStatus("Variable declaration block was drawn");
                Repository.getInstance().addBlock(new VariableDeclarationBlock(e.getX() - 75, e.getY() - 32));
            }
            case "Arrow" -> Repository.getInstance().setStatus("Arrow is being drawn");
        }
    }
}
