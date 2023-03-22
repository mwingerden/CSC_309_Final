import java.awt.*;

public class Arrow implements Draw{

    Block b1;
    Block b2;

    public Arrow(Block shape1, Block shape2){
        b1 = shape1;
        b2 = shape2;
    }
    @Override
    public void draw(Graphics g) {
        int x1 = b1.getX1();
        int x2 = b2.getX2();
        int y1 = b1.getY1();
        int y2 = b2.getY2();

        g.drawLine(x1,y1,x2,y2);
    }
    Block getBlock1(){
        return this.b1;
    }
    Block getBlock2(){
        return this.b2;
    }
}
