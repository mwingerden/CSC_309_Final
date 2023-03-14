import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorkSpace extends JPanel {
    private final List<Block> codeBlocks;
    private final Repository repository;

    public WorkSpace() {
        this.repository = Repository.getInstance();
        MainController controller = new MainController();
        this.addMouseListener(controller);
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(300, 300));
        codeBlocks = new ArrayList<>();
        addMouseListener(controller);
        addMouseMotionListener(controller);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Block codeBlock : codeBlocks) {
            codeBlock.draw(g);
        }
    }

    public void drawStartEndPoints() {
        codeBlocks.add(new Start(0, 0, 0, 0, String.valueOf(Color.WHITE)));
        codeBlocks.add(new End(getWidth() - 150, getHeight() - 150, 0, 0, String.valueOf(Color.BLACK)));
    }

    public void drawBlock(int x, int y) {
        if (repository.getBlockToDraw().equalsIgnoreCase("condition block")) {
            codeBlocks.add(new Condition(x, y));
        } else if (repository.getBlockToDraw().equalsIgnoreCase("variable declaration block")) {
//            codeBlocks.add(new VariableDeclarationBlock(x, y));
        } else if (repository.getBlockToDraw().equalsIgnoreCase("instruction block")) {
            codeBlocks.add(new Instruction(x, y));
        } else if (repository.getBlockToDraw().equalsIgnoreCase("call method block")) {
//            codeBlocks.add(new CallMethodBlock(x, y));
        } else if (repository.getBlockToDraw().equalsIgnoreCase("input/output block")) {
//            codeBlocks.add(new InputOutputBlock(x, y));
        }
        repaint();
    }

    public List<Block> getCodeBlocks() {
        return codeBlocks;
    }
}
