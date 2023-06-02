
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is responsible for representing and drawing the arrow between two blocks.
 */
public class Arrow implements Draw {
    Block block1;
    Block block2;
    double phi;
    int barb;
    /**
     * This constructor method takes both block 1 and block 2 as parameters.
     *
     * @param shape1, the first incoming block
     * @param shape2, the second incoming block
     */
    public Arrow(Block shape1, Block shape2) {
        this.block1 = shape1;
        this.block2 = shape2;
        this.phi = Math.toRadians(40);
        this.barb = 20;
    }
    /**
     * The draw method draws the arrow by grabbing the center coordinates of both blocks.
     *
     * @param g, Graphics class
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Point p1 = new Point(block1.getCenterX(), block1.getCenterY());
        Point p2 = new Point(block2.getCenterX(), block2.getCenterY());
        g2.draw(new Line2D.Double(p1, p2));
        drawArrowHead(g2, p1, p2);
    }

    private void drawArrowHead(Graphics2D g2, Point p1, Point p2)
    {
        g2.setColor(Color.BLACK);
        double dy = p2.y - p1.y;
        double dx = p2.x - p1.x;
        double centerX = (double) ((p2.x - p1.x) / 2) + p1.x;
        double centerY = (double) ((p2.y - p1.y) / 2) + p1.y;
        double theta = Math.atan2(dy, dx);
        //System.out.println("theta = " + Math.toDegrees(theta));
        double x, y, rho = theta + phi;
        for(int j = 0; j < 2; j++)
        {
            x = centerX - barb * Math.cos(rho);
            y = centerY - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(centerX, centerY, x, y));
            rho = theta - phi;
        }
    }

    /**
     * getter method to return incoming block.
     * @return inBlock
     */
    Block getBlock2(){
        return this.block2;
    }
    /**
     * getter method to return outcoming block.
     * @return outBlock
     */
    Block getBlock1(){
        return this.block1;
    }
    /**
     * getter method to return the codeBlocks.
     * @return codeBlocks
     */
    ArrayList<Block> getCodeBlocks(){
        ArrayList<Block> codeBlocks =  new ArrayList<>();
        codeBlocks.add(block1);
        codeBlocks.add(block2);
        return codeBlocks;
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrow arrow = (Arrow) o;
        return  this.block1.equals(arrow.getBlock1()) && block2.equals(arrow.getBlock2());
    }

}
