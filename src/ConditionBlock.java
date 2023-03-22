import java.awt.*;

public class ConditionBlock extends Block{
    public ConditionBlock(int x, int y) {
        super(x, y, x + 100, y + 50, String.valueOf(Color.RED));
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        int[] xCoords = {x, x + 50, x2, x + 50};
        int[] yCoords = {y2, y, y2, y + 100};

        g2.fillPolygon(xCoords, yCoords, 4);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xCoords, yCoords, 4);

        g2.drawString(this.text,x, y );
    }
}
