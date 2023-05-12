
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
        if(e.getSource() instanceof JRadioButton){
            Repository.getInstance().setProblemLoad(e.getActionCommand());
        }
        switch (e.getActionCommand()) {
            //TODO: The button press or other such actions are most likely be placed here.
            case "Teacher" -> Repository.getInstance().updatePanel("TeacherListView");
            case "Student" -> Repository.getInstance().updatePanel("StudentListView");
            case "Home" -> Repository.getInstance().updatePanel("StartUp");
            case "Back" -> Repository.getInstance().updatePanel("TeacherListView");
            case "Edit" -> {
                Repository.getInstance().loadList();
                Repository.getInstance().setEditing();
                Repository.getInstance().updatePanel("WorkSpace");
                }

            case "New" -> {Repository.getInstance().updatePanel("WorkSpace");
                Repository.getInstance().clearBlocks();}
            case "Delete" -> {
                Repository.getInstance().deleteProblem();
            }
            default -> menuItemClicked(e.getActionCommand());
        }
    }

    /**
     * The mouse methods receive the actions by the user and creates blocks and arrows needed.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            Repository.getInstance().addText(e.getX(), e.getY());
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
        else{
            Repository.getInstance().setStatus("Dragging");
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
        else {
            Repository.getInstance().drag(startDragx, startDragy, endDragx, endDragy);
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
                Repository.getInstance().unsetEditing();
            }
            case "Save" -> {
                Repository.getInstance().setStatus("Saving diagram");
                Repository.getInstance().setBlockToDraw("None");
                try {
                    Repository.getInstance().saveList();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Repository.getInstance().updatePanel("TeacherListView");
                Repository.getInstance().unsetEditing();
            }
            case "Load" -> {
                Repository.getInstance().setStatus("Loading diagram");
                Repository.getInstance().setBlockToDraw("None");
                Repository.getInstance().loadList();
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
