import java.awt.*;

public class InputOutputBlock extends Block{

    public InputOutputBlock(int x, int y) {
        super(x, y, x + 100, y + 30, String.valueOf(Color.WHITE));
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(color);

        int[] xCoords = {x, x2, x + 50, x - 50};
        int[] yCoords = {y - 30, y - 30, y2, y2};

        g2.fillPolygon(xCoords, yCoords, 4);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xCoords, yCoords, 4);
        if(this.text != null) {
            g.drawString(this.text, x, y);
        }
    }

    @Override
    public boolean contains(int xcoord, int ycoord){
        if (xcoord>=x && xcoord<=(x+50 )&& ycoord >= (y-30) && ycoord <=(y+30) ){
            return true;
        }


        return false;

    }
    @Override
    public int getCenterX(){
        return x + 25;

    }
    @Override
    public int getCenterY() {
        return y;
    }
}
