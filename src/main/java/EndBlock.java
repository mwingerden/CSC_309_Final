
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * The EndBlock class representing the final block in the work space.
 */
public class EndBlock extends Block {
    /**
     * EndBlock constructor receiving needed parameters.
     * @param x, block's top left x coordinate
     * @param y, block's top left y coordinate
     */
    public EndBlock(int x, int y) {
        super(x, y, x+80, y+80, Color.BLACK,2,0);
        this.setBlockText("End");
    }

    /**
     * A draw method to represent a black colored EndBlock
     * @param g graphics object to draw to
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(x,y,x2-x, y2-y);
    }

    /**
     * Getter method returning an x coordinate.
     * @return x, center x of block
     */
    @Override
    public int getCenterX(){
        return x + 40;
    }
    /**
     * Getter method returning a y coordinate.
     * @return y, center y of block
     */
    @Override
    public int getCenterY() {
        return y+ 40;
    }

    @Override
    public Area getShapeArea() {
        return new Area(new Ellipse2D.Double(x, y, 80, 80));
    }

    @Override
    public String getBlockText(){
        return "End";
    }
}
