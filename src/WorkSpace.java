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
        this.currentDrawing = "If Block";
        this.drawing = false;
//        Controller controller = new Controller(this);
//        setBackground(Color.PINK);
//        codeBlocks = new ArrayList<>();
//        codeBlocks.add(new StartBlock());
//        codeBlocks.add(new EndBlock());
//        addMouseListener(controller);
//        addMouseMotionListener(controller);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        for (CodeBlock codeBlock : codeBlocks) {
//            codeBlock.draw(g);
//        }
        if (drawing) {
            g.setColor(Color.BLACK);
            if (currentDrawing.equalsIgnoreCase("if block")) {
                g.fillRect(Math.min(x2, x1), Math.min(y2, y1), (x2 > x1) ? x2 - x1 : x1 - x2, (y2 > y1) ? y2 - y1 : y1 - y2);
            }
        }
    }

    public void setX1Y1(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
        currentDrawing = repository.getBlockToDraw();
    }

    public void setX2Y2(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
        drawing = false;
//        if (currentDrawing.equalsIgnoreCase("if block")) {
//            codeBlocks.add(new IfBlock(this.x1, this.y1, this.x2, this.y2));
//        }
        repaint();
    }

    public void dragging(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
        drawing = true;
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
