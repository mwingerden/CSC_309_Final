
import java.awt.*;

/**
 * InputOutpuBlock class that represents a parallelogram block that can be selected by the user.
 */
public class InputOutputBlock extends Block{

    public InputOutputBlock(int x, int y) {
        super(x, y, x + 100, y + 30, String.valueOf(Color.WHITE),2,1);
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
     * @param xcoord, x to check
     * @param ycoord, y to check
     * @return false, if coordinates not present
     */
    @Override
    public boolean contains(int xcoord, int ycoord){
        if (xcoord>=x && xcoord<=(x+50 )&& ycoord >= (y-30) && ycoord <=(y+30) ){
            return true;
        }
        return false;
    }
    /**
     * Getter methods that return the centered x and y coordinates.
     * @return x , y
     */
    @Override
    public int getCenterX(){
        return x + 25;
    }
    @Override
    public int getCenterY() {
        return y;
    }
}