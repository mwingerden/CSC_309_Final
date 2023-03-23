import java.awt.*;

/**
 * InstructionBlock represented by a simple rectangle, user can pick this block from menu.
 */
public class InstructionBlock extends Block {
    public InstructionBlock(int x, int y) {
        super(x, y, x+150, y+75, String.valueOf(Color.RED),1,1);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2-x, y2-y);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2-x, y2-y);
    }

    /**
     * Two getter methods returning the center coordinate of x and y of the InstructionBlock.
     * @return x
     * @return y
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
