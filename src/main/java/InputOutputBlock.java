
import java.awt.*;
import java.awt.geom.Area;

/**
 * InputOutpuBlock class that represents a parallelogram block that can be selected by the user.
 */
public class InputOutputBlock extends Block{

    /**
     * Constructor for the InputOutputBlock.
     * @param x, block's x coordinate
     * @param y, block's y coordinate
     */
    public InputOutputBlock(int x, int y) {
        super(x, y, x + 100, y + 30, Color.WHITE,2,1);
    }
    /**
     * The draw method utilizes Graphics 2D abstract class to draw a parallelogram block.
     * @param g, Graphics abstract class
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(color);
        int[] xCoords = {x, x2, x + 50, x - 50};
        int[] yCoords = {y - 30, y - 30, y2, y2};
        g2.fillPolygon(xCoords, yCoords, 4);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xCoords, yCoords, 4);
        if(this.blockText != null) {
            g.drawString(this.blockText, x, y);
        }
    }
    /**
     * A contains method that checks if a given x and y is inside coordinates(x1,x2,y1,y2)
     * @param xCoord, x to check
     * @param yCoord, y to check
     * @return false, if coordinates not present
     */
    @Override
    public boolean contains(int xCoord, int yCoord){
        if (xCoord >=x && xCoord <=(x+50 )&& yCoord >= (y-30) && yCoord <=(y+30) ){
            return true;
        }
        return false;
    }

    @Override
    public Area getShapeArea() {
        return new Area(
                new Polygon(new int[]{x, x + 100, x + 50, x - 50},
                        new int[]{y - 30, y - 30, y + 30, y + 30}, 4));
    }

    /**
     * Get the center x coordinate.
     * @return x, center x of block
     */
    @Override
    public int getCenterX(){
        return x + 25;
    }

    /**
     * Get the center y coordinate.
     * @return y, center y of block
     */
    @Override
    public int getCenterY() {
        return y;
    }
}