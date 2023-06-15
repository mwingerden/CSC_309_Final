
import java.awt.*;
import java.awt.geom.Area;

/**
 * The VariableDeclarationBlock class represented by a rectangle with two lines.
 */
public class VariableDeclarationBlock extends Block{

    /**
     * Constructor method for VariableDeclarationBlock giving the required parameters.
     * @param x, block's top left x coordinate
     * @param y, block's top left y coordinate
     */
    public VariableDeclarationBlock(int x, int y) {
        super(x, y, x+150, y+75, Color.WHITE,2,1);
    }
    /**
     * draw method used to create the VariableDeclarationBlock rectangle.
     * @param g, Graphics class
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2-x, y2-y);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2-x, y2-y);
        g.drawLine(x+10, y, x+10, y2);
        g.drawLine(x, y+10, x2, y+10);
        if(this.blockText != null) {
            g.drawString(this.blockText, x+20, getCenterY()+10);
        }
    }
    /**
     * Get the center x coordinate.
     * @return x, center x of block
     */
    @Override
    public int getCenterX(){
        int width = x2-x;
        return x + (width/2);
    }
    /**
     * Get the center y coordinate.
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