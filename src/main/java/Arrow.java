
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
/**
 * This class is responsible for representing and drawing the arrow between two blocks.
 */
public class Arrow implements Draw {
    Block inBlock;
    Block outBlock;
    /**
     * This constructor method takes both block 1 and block 2 as parameters.
     *
     * @param shape1, the first incoming block
     * @param shape2, the second incoming block
     */
    public Arrow(Block shape1, Block shape2) {
        inBlock = shape1;
        outBlock = shape2;
    }
    /**
     * The draw method draws the arrow by grabbing the center coordinates of both blocks.
     *
     * @param g, Graphics class
     */
    @Override
    public void draw(Graphics g) {
        int x1 = inBlock.getCenterX();
        int x2 = outBlock.getCenterX();
        int y1 = inBlock.getCenterY();
        int y2 = outBlock.getCenterY();
        g.drawLine(x1, y1, x2, y2);
        //draw pointing part
        int centerx = (x1 + x2) / 2;
        int centery = (y1 + y2) / 2;
        AffineTransform tx = new AffineTransform();
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(0, 20);
        arrowHead.addPoint(-20, -20);
        arrowHead.addPoint(20, -20);
        tx.setToIdentity();
        double angle = Math.atan2(y2 - y1, x2 - x1);
        tx.translate(centerx * 2, centery * 2);
        tx.rotate((angle - Math.PI / 2d));
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setTransform(tx);
        g2.fill(arrowHead);
    }
    /**
     * getter method to return incoming block.
     * @return inBlock
     */
    Block getInBlock(){
        return this.inBlock;
    }
    /**
     * getter method to return outcoming block.
     * @return outBlock
     */
    Block getOutBlock(){
        return this.outBlock;
    }
    /**
     * getter method to return the codeBlocks.
     * @return codeBlocks
     */
    ArrayList<Block> getCodeBlocks(){
        ArrayList<Block> codeBlocks =  new ArrayList<>();
        codeBlocks.add(inBlock);
        codeBlocks.add(outBlock);
        return codeBlocks;
    }
}
