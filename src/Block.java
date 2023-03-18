import java.awt.*;

public abstract class Block implements Draw
{
    protected int x, y, x2, y2;
    protected Color color;

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
    //TODO: Create setter and getters for the coordinates.

}
