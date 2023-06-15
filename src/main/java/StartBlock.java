
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * The StartBlock class that is represented by a circle, user selects and sets this block on the work space.
 */
public class StartBlock extends Block {

    /**
     * Constructor for a startBlock object at the x and y coordinates passed.
     * @param x, block's top left x coordinate
     * @param y, block's top left y coordinate
     */
    public StartBlock(int x, int y) {
        super(x, y, x+80, y+80, Color.white, 0, 1);
        this.setBlockText("Start");
    }
    /**
     * The draw method, represents the start block with a circle.
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(x,y,x2-x, y2-y);
        g.setColor(Color.black);
        g.drawOval(x, y, x2-x, y2-y);
    }
    /**
     * A getter method returns the center x coordinate.
     * @return x + 40
     */
    @Override
    public int getCenterX() {
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

    @Override
    public Area getShapeArea() {
        return new Area(new Ellipse2D.Double(x, y, 80, 80));
    }

    /**
     * Checks if there is an arrow leaving this block or not.
     * @return true if the number of outgoing arrows is 1 and false otherwise.
     */
    public boolean maxNumsOut(){
        return this.arrowOutCount == this.arrowOutLimit;
    }
}
