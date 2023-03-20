import java.awt.*;

public class EndBlock extends Block
{
    public EndBlock(int x, int y, int x2, int y2, String c)
    {
        super(x, y, x2, y2, c);
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x,y,x2, y2);
    }
}
