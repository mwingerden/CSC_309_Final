import java.awt.*;

public class InstructionBlock extends Block {
    public InstructionBlock(int x, int y) {
        super(x, y, x+150, y+75, String.valueOf(Color.RED));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2-x, y2-y);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2-x, y2-y);
    }
}
