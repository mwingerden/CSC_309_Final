
import java.awt.*;
import java.awt.geom.Area;

/**
 * InstructionBlock represented by a simple rectangle, user can pick this block from menu.
 */
public class InstructionBlock extends Block {
    /**
     * Constructor method for InstructionBlock giving the required parameters.
     * @param x, block's top left x coordinate
     * @param y, block's top left y coordinate
     */
    public InstructionBlock(int x, int y) {
        super(x, y, x+150, y+75, Color.WHITE,2,1);
    }

    /**
     * Draw method uses abstract class graphics 2D to draw the rectangle block.
     * @param g, Graphics
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2-x, y2-y);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2-x, y2-y);
        if(this.blockText != null) {
            g.drawString(this.blockText, getCenterX()-10, getCenterY()+10);
        }
    }

    /**
     * Getter method returning an x coordinate.
     * @return x, center x of block
     */
    @Override
    public int getCenterX(){
        int width = x2-x;
        return x + (width/2);
    }

    /**
     * Getter method returning a y coordinate.
     * @return y, center y of block
     */
    @Override
    public int getCenterY() {
        int length = y2 - y;
        return y+ (length/2);
    }

    @Override
    public Area getShapeArea() {
        return new Area(new Rectangle(x, y, 150, 75));
    }
}