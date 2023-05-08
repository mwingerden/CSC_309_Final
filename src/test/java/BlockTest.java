import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * com.tests.BlockTest class tests the methods from the block class and other block types.
 */

public class BlockTest
{
    /**
     * testCheckArrows method tests the outgoing and incoming arrow count methods with a given block test case with start and end block.
     */
    @Test
    public void testCheckArrows()
    {
        Block b1 = new StartBlock(10,10, "PINK");
        Block b2 = new EndBlock(20,30,"BLUE");


        //test arrows limits for start and end blocks
        assertEquals(0, b1.arrowInLimit);
        assertEquals(1, b1.arrowOutLimit);
        assertEquals(2, b2.arrowInLimit);
        assertEquals(0, b2.arrowOutLimit);

        //since start block has 1 outgoing, should return true
        assertEquals(true, b1.checkOutGoing());
        //start block has no incoming arrows so should return false
        assertEquals(false, b1.checkInGoing());
        //end block has one incoming arrow, so expect 1
        assertEquals(true, b2.checkInGoing());
        //end block has no outgoing arrows, expect false
        assertEquals(false, b2.checkOutGoing());
    }

    /**
     * A method that checks arrows for the remaining block classes.
     */
    @Test
    public void testCheckArrows2()
    {
        Block b1 = new VariableDeclarationBlock(22,22);
    }
}