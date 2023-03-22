import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Arrow implements Draw{

    Block inBlock;
    Block outBlock;

    public Arrow(Block shape1, Block shape2){
        inBlock = shape1;
        outBlock = shape2;
    }
    @Override
    public void draw(Graphics g) {
        int x1 = inBlock.getCenterX();
        int x2 = outBlock.getCenterX();
        int y1 = inBlock.getCenterY();
        int y2 = outBlock.getCenterY();

        g.drawLine(x1,y1,x2,y2);

        //draw pointing part
        int centerx = (x1 + x2)/2;
        int centery = (y1 + y2)/2;
        AffineTransform tx = new AffineTransform();
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint( 0,20);
        arrowHead.addPoint( -20, -20);
        arrowHead.addPoint( 20,-20);
        tx.setToIdentity();
        double angle = Math.atan2(y2-y1, x2-x1);
        tx.translate(centerx*2, centery*2);
        tx.rotate((angle-Math.PI/2d));
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setTransform(tx);
        g2.fill(arrowHead);

    }
    Block getInBlock(){
        return this.inBlock;
    }
    Block getOutBlock(){
        return this.outBlock;
    }
    ArrayList<Block> getCodeBlocks(){
        ArrayList<Block> codeBlocks =  new ArrayList<>();
        codeBlocks.add(inBlock);
        codeBlocks.add(outBlock);
        return codeBlocks;
    }
}
