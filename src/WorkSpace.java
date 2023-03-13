import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class WorkSpace extends JPanel implements Observer {
//    private final List<CodeBlock> codeBlocks;
    private final Repository repository;
    private String currentDrawing;
    private boolean drawing;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public WorkSpace() {
        this.repository = Repository.getInstance();
        this.currentDrawing = "Command Block";
        this.drawing = false;
        MainController controller = new MainController();
        this.addMouseListener(controller);
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(300, 300));
        this.repository.addObserver(this);
//        codeBlocks = new ArrayList<>();
//        addMouseListener(controller);
//        addMouseMotionListener(controller);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        for (CodeBlock codeBlock : codeBlocks) {
//            codeBlock.draw(g);
//        }
//        if (drawing) {
//            g.setColor(Color.BLACK);
//            if (currentDrawing.equalsIgnoreCase("if block")) {
//                g.fillRect(Math.min(x2, x1), Math.min(y2, y1), (x2 > x1) ? x2 - x1 : x1 - x2, (y2 > y1) ? y2 - y1 : y1 - y2);
//            } else if (currentDrawing.equalsIgnoreCase("command block")) {
//                g.fillRect(Math.min(x2, x1), Math.min(y2, y1), (x2 > x1) ? x2 - x1 : x1 - x2, (y2 > y1) ? y2 - y1 : y1 - y2);
//            }
//        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

//    public void drawStartEndPoints() {
//        codeBlocks.add(new StartBlock());
//        codeBlocks.add(new EndBlock(this.getWidth() - 60, this.getHeight() - 60));
//    }

//    public void drawBlock(int x, int y) {
//        currentDrawing = repository.getBlockToDraw();
//        if (repository.getBlockToDraw().equalsIgnoreCase("condition block")) {
//            codeBlocks.add(new ConditionBlock(x, y));
//        } else if (repository.getBlockToDraw().equalsIgnoreCase("variable declaration block")) {
//            codeBlocks.add(new VariableDeclarationBlock(x, y));
//        } else if (repository.getBlockToDraw().equalsIgnoreCase("instruction block")) {
//            codeBlocks.add(new InstructionBlock(x, y));
//        } else if (repository.getBlockToDraw().equalsIgnoreCase("call method block")) {
//            codeBlocks.add(new CallMethodBlock(x, y));
//        } else if (repository.getBlockToDraw().equalsIgnoreCase("input/output block")) {
//            codeBlocks.add(new InputOutputBlock(x, y));
//        }
//        repaint();
//    }

//    public void setX2Y2(int x2, int y2) {
//        if(drawing) {
//            this.x2 = x2;
//            this.y2 = y2;
//            drawing = false;
//            if (currentDrawing.equalsIgnoreCase("if block")) {
//                codeBlocks.add(new IfBlock(this.x1, this.y1, this.x2, this.y2));
//            } else if (currentDrawing.equalsIgnoreCase("command block")) {
//                codeBlocks.add(new CommandBlock(this.x1, this.y1, this.x2, this.y2));
//            }
//            repaint();
//        }
//    }

//    public void dragging(int x2, int y2) {
//        this.x2 = x2;
//        this.y2 = y2;
//        drawing = true;
//        repaint();
//    }

//    public List<CodeBlock> getCodeBlocks() {
//        return codeBlocks;
//    }
}
