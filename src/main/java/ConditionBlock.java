
import java.awt.*;
import java.awt.geom.Area;

/**
 * The ConditionBlock class represented by a diamond shape when drawn by the user.
 */
public class ConditionBlock extends Block{
    /**
     * ConditionBlock constructor taking in the necessary parameters.
     * @param x, block's top left x coordinate
     * @param y, block's top left y coordinate
     */
    public ConditionBlock(int x, int y) {
        super(x, y, x + 100, y + 50, Color.WHITE,2,2);
    }

    /**
     * Draw method uses abstract class graphics 2D to draw the diamond block.
     * @param g, Graphics
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        int[] xCoords = {x, x + 50, x2, x + 50};
        int[] yCoords = {y2, y, y2, y + 100};
        g2.fillPolygon(xCoords, yCoords, 4);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xCoords, yCoords, 4);
        if(this.blockText != null) {
            g.drawString(this.blockText, x + 10, getCenterY());
        }
    }

    /**
     * Contains method compares if x and y coordinates are aligned.
     * @param xCoord, x coordinate to align
     * @param yCoord, y coordinate to align
     * @return false
     */
    @Override
    public boolean contains(int xCoord, int yCoord){
        int xcoord_aligned = xCoord - x;
        int ycoord_aligned = yCoord - y;
        if (xcoord_aligned>0 && xcoord_aligned<=50){
            return (ycoord_aligned >= (50 - xcoord_aligned) && ycoord_aligned <= (50+xcoord_aligned));
        }
        if(xcoord_aligned >50 && xcoord_aligned <=100){
            return (ycoord_aligned <= (150 - xcoord_aligned) && ycoord_aligned >=(50 - xcoord_aligned));
        }
        return false;
    }

    @Override
    public Area getShapeArea() {
        return new Area(
                new Polygon(new int[]{x, x + 50, x + 100, x + 50}, new int[]{y + 50, y, y + 50, y + 100}, 4));
    }

    /**
     * Getter method returning an x coordinate.
     * @return x, center x of block
     */
    @Override
    public int getCenterX(){
        return x + 50;
    }

    /**
     * Getter method returning a y coordinate.
     * @return y, center y of block
     */
    @Override
    public int getCenterY() {
        return y+ 50;
    }
}
