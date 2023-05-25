
import java.awt.*;
/**
 * The EndBlock class representing the final block in the work space.
 */
public class EndBlock extends Block
{
    /**
     * EndBlock constructor receiving needed parameters.
     * @param x
     * @param y
     * @param c
     */
    public EndBlock(int x, int y, String c)
    {
        super(x, y, x+80, y+80, c,2,0);
    }
    /**
     * A draw method to represent a black colored EndBlock
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x,y,x2-x, y2-y);
    }
    /**
     * The getter methods that return the centered x and y coordinates.
     * @return x
     * @return y
     */
    @Override
    public int getCenterX(){
        return x + 40;
    }
    @Override
    public int getCenterY() {
        return y+ 40;
    }
}
