
import java.awt.*;
/**
 * The abstract Main.Block class represents the different blocks that the user can pick from the menu.
 */
public abstract class Block implements Draw
{
    protected int x, y, x2, y2;
    protected Color color;
    protected String text;
    protected int arrowInLimit;
    protected int arrowOutLimit;
    protected int arrowInCount = 0;
    protected int arrowOutCount = 0;
    /**
     * These are the abstract methods that all the blocks will implement.
     * @param g
     */
    public abstract void draw(Graphics g);
    public abstract int getCenterX();
    public abstract int getCenterY();
    /**
     * The block constructor taking all the needed parameters for each block that will be drawn.
     * @param x, first x coordinate
     * @param y, first y coordinate
     * @param x2, second x coordinate
     * @param y2, second y coordinate
     * @param c, color of block
     * @param arrowInLimit, limit of arrows going into block
     * @param arrowOutLimit, limit of arrow going out the block
     */
    public Block(int x, int y, int x2, int y2, String c,int arrowInLimit, int arrowOutLimit)
    {
        this.x=x;
        this.y=y;
        this.x2=x2;
        this.y2=y2;
        this.arrowInLimit = arrowInLimit;
        this.arrowOutLimit = arrowOutLimit;
        switch (c) {
            case ("Black"):
                color = Color.BLACK;
                break;
            case("Red"):
                color=Color.RED; break;
            default: color=Color.WHITE;
        }
        this.text = null;
    }
    /**
     * checkoutGoing method checks if arrow count out is less than the blocks out arrow limit.
     * @return false
     */
    public boolean checkOutGoing(){
        if(arrowOutCount < arrowOutLimit)
        {
            arrowOutCount++;
            return true;
        }
        return false;
    }
    /**
     * checkInGoing method checks if arrow count incoming is less than the blocks incoming arrow limit.
     * @return false
     */
    public boolean checkInGoing()
    {
        if(arrowInCount < arrowInLimit)
        {
            arrowInCount++;
            return true;
        }
        return false;
    }
    /**
     * A contains method that checks if a given x and y is inside coordinates(x1,x2,y1,y2).
     * @param xcoord, x coordinate to check
     * @param ycoord, y coordinate to check
     * @return
     */
    boolean contains(int xcoord, int ycoord){
        return (xcoord>x && xcoord<x2 && ycoord> y && ycoord<y2);
    }
    /**
     * * Getter methods for each of the different coordinates.
     * @return the desired coordinate
     */
    int getX1(){
        return this.x;
    }
    int getY1(){
        return this.y;
    }
    int getX2(){
        return this.x2;
    }
    int getY2(){
        return this.y2;
    }
    void setX1(int x){
        this.x = x;
    }
    void setY1(int y){
        this.y = y;
    }
    void setX2(int x){
        this.x2 = x;
    }
    void setY2(int y){
        this.y2 = y;
    }
    /**
     * Set and get methods for the strings text in the block.
     * @param text, of the block
     */
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
}