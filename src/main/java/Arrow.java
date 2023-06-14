
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * This class is responsible for representing and drawing the arrow between two blocks.
 */
public class Arrow implements Draw {
    Block sourceShape;
    Block destinationShape;
    double phi;
    int barb;
    /**
     * This constructor method takes both block 1 and block 2 as parameters.
     *
     * @param sourceShape, the first incoming block
     * @param destinationShape, the second incoming block
     */
    public Arrow(Block sourceShape, Block destinationShape) {
        this.sourceShape = sourceShape;
        this.destinationShape = destinationShape;
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
        Point p1 = new Point(sourceShape.getCenterX(), sourceShape.getCenterY());
        Point p2 = new Point(destinationShape.getCenterX(), destinationShape.getCenterY());
        g2.draw(new Line2D.Double(p1, p2));
        drawArrowHead(g2, p1, p2);
    }

    private void drawArrowHead(Graphics2D g2, Point p1, Point p2) {
        g2.setColor(Color.BLACK);
        double dy = ((double) p2.y) - p1.y;
        double dx = ((double) p2.x) - p1.x;
        double centerX = (dx / 2.0) + p1.x;
        double centerY = (dy / 2.0) + p1.y;
        double theta = Math.atan2(dy, dx);
        double x;
        double y;
        double rho = theta + phi;
        for(int j = 0; j < 2; j++) {
            x = centerX - barb * Math.cos(rho);
            y = centerY - barb * Math.sin(rho);
            g2.draw(new Line2D.Double(centerX, centerY, x, y));
            rho = theta - phi;
        }
    }

    /**
     * getter method to return destination block.
     * @return inBlock
     */
    Block getDestinationShape(){
        return this.destinationShape;
    }

    /**
     * getter method to return source block.
     * @return outBlock
     */
    Block getSourceShape(){
        return this.sourceShape;
    }

    /**
     * getter method to return the blocks associated with this arrow.
     * @return codeBlocks
     */
    ArrayList<Block> getCodeBlocks(){
        ArrayList<Block> codeBlocks =  new ArrayList<>();
        codeBlocks.add(sourceShape);
        codeBlocks.add(destinationShape);
        return codeBlocks;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arrow arrow = (Arrow) o;
        return  this.sourceShape.equals(arrow.getSourceShape()) && destinationShape.equals(arrow.getDestinationShape());
    }

}
