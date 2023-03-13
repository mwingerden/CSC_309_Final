import java.awt.*;

public class Start extends Block
{
    public Start(int x, int y, int x2, int y2, String c)
    {
        super(x, y, 100, 50, c);
    }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval(x,y,x2, y2);
    }

}
