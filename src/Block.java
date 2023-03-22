import java.awt.*;

public abstract class Block implements Draw
{
    protected int x, y, x2, y2;
    protected Color color;
    protected String text;

    public abstract void draw(Graphics g);

    public Block(int x, int y, int x2, int y2, String c)
    {
        this.x=x;
        this.y=y;
        this.x2=x2;
        this.y2=y2;
        switch (c) {
            case ("Black"):
                color = Color.BLACK;
                break;
            case("Red"):
                color=Color.RED; break;
            default: color=Color.WHITE;

        }
    }
    //TODO: Add a method call contains that checks a given x and y that is inside x1,x2,y1,y2
    boolean contains(int xcoord, int ycoord){
        return (xcoord>x && xcoord<x2 && ycoord> y && ycoord<y2);
    }
    //TODO: Create setter and getters for the coordinates.
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

    //TODO: Have method to set the text and draw text inside block.
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }

}
