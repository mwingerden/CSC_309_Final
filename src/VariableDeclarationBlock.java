import java.awt.*;

public class VariableDeclarationBlock extends Block{
    public VariableDeclarationBlock(int x, int y) {
        super(x, y, x+150, y+75, String.valueOf(Color.RED));
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, x2-x, y2-y);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, x2-x, y2-y);
        g.drawLine(x+10, y, x+10, y2);
        g.drawLine(x, y+10, x2, y+10);
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
