import java.awt.*;

public class EndBlock extends Block
{
    public EndBlock(int x, int y, String c)
    {
        super(x, y, x+80, y+80, c);
        this.text = "Program End";
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x,y,x2-x, y2-y);
        if(this.text != null) {
            g.drawString(this.text, x, getCenterY());
        }
    }
    @Override
    public int getCenterX(){
        return x + 40;
    }
    @Override
    public int getCenterY() {
        return y+ 40;
    }
}
