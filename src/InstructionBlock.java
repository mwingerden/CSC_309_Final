import java.awt.*;

public class InstructionBlock extends Block {
    public InstructionBlock(int x, int y) {
        super(x, y, 150, 75, String.valueOf(Color.RED));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2, y2);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2, y2);
    }
}
