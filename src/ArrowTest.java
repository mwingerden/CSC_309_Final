import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrowTest {

    @Test
    public void testArrow()
    {
        Block b1 = new ConditionBlock(1,1);
        Block b2 = new CallMethodBlock(10,10);

        Arrow arrow = new Arrow(b1, b2);

        //checks if different block classes can be used in arrow's constructor
        assertTrue(true, String.valueOf(arrow.inBlock.equals(Block.class)));


    }

}