import java.awt.*;

public class InputOutputBlock extends Block{

    public InputOutputBlock(int x, int y, String c) {
        super(x, y, x + 100, y + 30, c);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int[] xCoords = {x, x2, x + 50, x - 50};
        int[] yCoords = {y - 30, y - 30, y2, y2};

        g2.fillPolygon(xCoords, yCoords, 4);
    }
}
