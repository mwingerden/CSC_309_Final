import java.awt.*;
import java.util.ArrayList;

public class Arrow implements Draw{

    Block b1;
    Block b2;

    public Arrow(Block shape1, Block shape2){
        b1 = shape1;
        b2 = shape2;
    }
    @Override
    public void draw(Graphics g) {
        int x1 = b1.getCenterX();
        int x2 = b2.getCenterX();
        int y1 = b1.getCenterY();
        int y2 = b2.getCenterY();

        g.drawLine(x1,y1,x2,y2);
    }
    Block getBlock1(){
        return this.b1;
    }
    Block getBlock2(){
        return this.b2;
    }
    ArrayList<Block> getCodeBlocks(){
        ArrayList<Block> codeBlocks =  new ArrayList<>();
        codeBlocks.add(b1);
        codeBlocks.add(b2);
        return codeBlocks;
    }
}
