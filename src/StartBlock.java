import java.awt.*;

public class StartBlock extends Block
{
    public StartBlock(int x, int y, String c)
    {
        super(x, y, x+80, y+80, c);
    }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval(x,y,x2-x, y2-y);
        g.setColor(Color.black);
        g.drawOval(x, y, x2-x, y2-y);
        if(this.text != null) {
            g.drawString(this.text, x , getCenterY());
        }
    }
    @Override
    public int getCenterX(){

        return x + 40;

    }
    @Override
    public int getCenterY() {
        return y+ 40;
    }

}
