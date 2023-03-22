import java.awt.*;

public class CallMethodBlock extends Block{
    public CallMethodBlock(int x, int y) {
        super(x, y, x+150, y+75, String.valueOf(Color.RED));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2-x, y2-y);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2-x, y2-y);
        g.drawLine(x+10, y, x+10, y2);
        g.drawLine(x2-10, y, x2-10, y2);
        if(this.text != null) {
            g.drawString(this.text, x+20, getCenterY()+10);
        }
    }
    @Override
    public int getCenterX(){
        int width = x2-x;
        return x + (width/2);

    }
    @Override
    public int getCenterY() {
        int length = y2 - y;
        return y+ (length/2);
    }
}
