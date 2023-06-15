
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Block b1 = new StartBlock(10,10);
        Block b2 = new EndBlock(20,30);


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
     *  methods that checks arrows for the remaining block classes.
     */
    @Test
    public void testCheckArrows2()
    {
        Block b1 = new VariableDeclarationBlock(22,22);
        Block b2 = new InstructionBlock(50,10);

        //check arrows for block 1
        assertEquals(2, b1.arrowInLimit);
        assertEquals(1, b1.arrowOutLimit);
        // check for block 2
        assertEquals(2, b2.arrowInLimit);
        assertEquals(1, b2.arrowOutLimit);
    }

    @Test
    public void testCheckArrows3()
    {
        Block b1 = new ConditionBlock(74,35);
        Block b2 = new CallMethodBlock(45,88);

        //check arrows for block 1
        assertEquals(2, b1.arrowInLimit);
        assertEquals(2, b1.arrowOutLimit);
        // check for block 2
        assertEquals(2, b2.arrowInLimit);
        assertEquals(1, b2.arrowOutLimit);

        assertTrue(b1.checkOutGoing());
        assertTrue(b1.checkInGoing());
        assertTrue(b2.checkInGoing());
        assertTrue(b2.checkOutGoing());
    }
}
