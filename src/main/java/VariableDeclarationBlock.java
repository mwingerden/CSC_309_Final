
import java.awt.*;
/**
 * The VariableDeclarationBlock class represented by a rectangle with two lines.
 */
public class VariableDeclarationBlock extends Block{
    public VariableDeclarationBlock(int x, int y) {
        super(x, y, x+150, y+75, String.valueOf(Color.RED),2,1);
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
     * The getter methods returning the centered x and y coordinates of the VariableDeclarationBlock.
     * @ return, center x or y
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