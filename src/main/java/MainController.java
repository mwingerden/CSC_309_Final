import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
            case "Teacher" -> Repository.getInstance().updatePanel("TeacherListView");
            case "Student" -> Repository.getInstance().updatePanel("StudentListView");
            case "Home" -> Repository.getInstance().updatePanel("StartUp");
            case "Go to Problem List" -> Repository.getInstance().updatePanel("StudentListView");
            case "Undo" -> Repository.getInstance().undoList();
            case "Redo" -> Repository.getInstance().redoList();
            case "Login" -> Repository.getInstance().authenticateLogin();
            case "Cancel" -> Repository.getInstance().closeLogin();
            default -> menuItemClicked(e.getActionCommand());
        }
    }

    /**
     * Processes left and right mouse clicks. Right click results in the block text of the clicked block to be changed.
     * Right click and shift down results in the hints of the clicked block to be changed. Left click creates the
     * currently selected block.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e) || (SwingUtilities.isRightMouseButton(e) && e.isShiftDown())) {
            Repository.getInstance().blockText(e, e.getX(), e.getY());
        } else {
            if (e.getButton() == MouseEvent.BUTTON1) {
                changeStatus(e);
            }
        }
    }

    /**
     * Processes when the mouse is pressed. If the user has selected to draw arrows and holds shift down it will set the
     * starting point for the arrow and set the program status to an arrow being drawn.
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (Repository.getInstance().getBlockToDraw().equals("Arrow") && e.isShiftDown()) {
            Repository.getInstance().setStatus("Arrow is being drawn");
        }
        startDragx = e.getX();
        startDragy = e.getY();
    }

    /**
     * Processes when the mouse is released. If the user has selected to draw arrows and holds shift down it will draw
     * an arrow between the two blocks that correspond to the location where the moused was pressed and where it was
     * released.
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        endDragx = e.getX();
        endDragy = e.getY();
        if (Repository.getInstance().getBlockToDraw().equals("Arrow") && e.isShiftDown()) {
            Repository.getInstance().addArrow(startDragx,startDragy,endDragx,endDragy);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // no actions associated
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // no actions associated
    }

    /**
     * Sets the status to dragging and attempts to drag a block as long as shift is not down and arrow is not selected.
     * Prints the stack trace if there is an error when dragging.
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Repository.getInstance().setStatus("Dragging");
        endDragx = e.getX();
        endDragy = e.getY();
        if(!(Repository.getInstance().getBlockToDraw().equals("Arrow") && e.isShiftDown())){
            try {
                Repository.getInstance().drag(startDragx, startDragy, endDragx, endDragy);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
            startDragx = endDragx;
            startDragy = endDragy;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // no actions associated
    }

    private void menuItemClicked(String e) {
        switch (e) {
            case "Clear" -> {
                Repository.getInstance().clearBlocks();
                Repository.getInstance().setStatus("New diagram");
                Repository.getInstance().setBlockToDraw("None");
            }
            case "Save" -> {
                Repository.getInstance().setStatus("Saving diagram");
                Repository.getInstance().setBlockToDraw("None");
                try {
                    Repository.getInstance().saveList(Repository.getInstance().getLoadedProblem(), false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private void changeStatus(MouseEvent e){
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
                Repository.getInstance().addBlock(new StartBlock(e.getX() - 40, e.getY() - 40));
            }
            case "End" -> {
                Repository.getInstance().setStatus("Ending block was drawn");
                Repository.getInstance().addBlock(new EndBlock(e.getX() - 40, e.getY() - 40));
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
