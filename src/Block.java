import java.awt.*;

public abstract class Block
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

}
