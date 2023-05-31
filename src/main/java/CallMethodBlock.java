
import java.awt.*;
/**
 * The CallMethodBlock class that extends block abstract class.
 */
public class CallMethodBlock extends Block{
    /**
     * Constructor method for CallMethodBlock giving the required parameters.
     * @param x, block's x coordinate
     * @param y, block's y coordinate
     */
    public CallMethodBlock(int x, int y) {
        super(x, y, x+150, y+75, String.valueOf(Color.RED),2,1);
    }
    /**
     * The draw method handles drawing the block, uses two lines and rectangles to represent CallMethodBlock.
     * @param g, Graphics abstract class
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2-x, y2-y);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2-x, y2-y);
        g.drawLine(x+10, y, x+10, y2);
        g.drawLine(x2-10, y, x2-10, y2);
        if(this.blockText != null) {
            g.drawString(this.blockText, x+20, getCenterY()+10);
        }
    }
    /**
     * The getter methods used to return the center x or y.
     * @return x, center x of block
     * @return y, center y of block
     */
    @Override
    public int getCenterX(){
        int width = x2-x;
        return x + (width/2);
    }
    @Override
    public int getCenterY() {
        int length = y2 - y;
        return y+ (length/2);
    }
}