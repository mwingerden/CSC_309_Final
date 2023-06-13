
import java.awt.*;
import java.util.Objects;

/**
 * The abstract Block class represents the different blocks that the user can pick from the menu.
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

    public int getArrowInLimit() {
        return arrowInLimit;
    }

    public void setArrowInLimit(int arrowInLimit) {
        this.arrowInLimit = arrowInLimit;
    }

    public int getArrowOutLimit() {
        return arrowOutLimit;
    }

    public void setArrowOutLimit(int arrowOutLimit) {
        this.arrowOutLimit = arrowOutLimit;
    }

    public int getArrowInCount() {
        return arrowInCount;
    }

    public void setArrowInCount(int arrowInCount) {
        this.arrowInCount = arrowInCount;
    }

    public int getArrowOutCount() {
        return arrowOutCount;
    }

    public void setArrowOutCount(int arrowOutCount) {
        this.arrowOutCount = arrowOutCount;
    }

    public boolean checkInArrowMax(){
        return arrowInCount < arrowInLimit;
    }

    public boolean checkOutArrowMax(){
        return arrowOutCount < arrowOutLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return x == block.x && y == block.y && x2 == block.x2 && y2 == block.y2 && arrowInLimit == block.arrowInLimit
                && arrowOutLimit == block.arrowOutLimit && arrowInCount == block.arrowInCount &&
                arrowOutCount == block.arrowOutCount && Objects.equals(color, block.color) &&
                Objects.equals(text, block.text);
    }
}