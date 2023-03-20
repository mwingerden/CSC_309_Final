import java.awt.*;

public class CommandBlock extends Block{
    public CommandBlock(int x, int y) {
        super(x, y, 150, 75, String.valueOf(Color.RED));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2, y2);
    }
}
