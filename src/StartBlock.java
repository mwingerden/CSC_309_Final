import java.awt.*;

/**
 * The StartBlock class that is represented by a circle, user selects and sets this block on the work space.
 */
public class StartBlock extends Block
{
    public StartBlock(int x, int y, String c)
    {
        super(x, y, x+80, y+80, c,0,1);
    }

    /**
     * The draw method, represents the start block with a circle.
     * @param g, Graphics
     */
    @Override
    public void draw(Graphics g)
    {
        g.setColor(Color.white);
        g.fillOval(x,y,x2-x, y2-y);
        g.setColor(Color.black);
        g.drawOval(x, y, x2-x, y2-y);
    }

    /**
     * A getter method returns the center x coordinate.
     * @return x + 40
     */
    @Override
    public int getCenterX(){

        return x + 40;

    }

    /**
     * A getter method returns the center y coordinate.
     * @return y + 40
     */
    @Override
    public int getCenterY() {
        return y+ 40;
    }

}
