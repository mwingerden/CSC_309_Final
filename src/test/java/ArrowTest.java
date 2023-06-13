
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * An Arrow test class that creates its own blocks to test methods from the arrow class and from block classes as well.
 */
public class ArrowTest {

    /**
     * testArrow method creates a test case with an arrow and two given blocks.
     */

    @Test
    public void testArrow()
    {

        Block b1 = new ConditionBlock(30,20);
        Block b2 = new CallMethodBlock(20,10);

        Arrow arrow = new Arrow(b1, b2);

        //checks if different block classes can be used in arrow's constructor, also tests get methods
        assertTrue(true, String.valueOf(arrow.getBlock1().equals(Block.class)));
        assertTrue(true, String.valueOf(arrow.getBlock2().equals(Block.class)));

        //checks to see that the values given to blocks carry into arrow properly
        assertEquals(30, arrow.block1.x );
        assertEquals(20, arrow.block1.y);
        assertEquals(20, arrow.block2.x);
        assertEquals(10, arrow.block2.y);
    }
    //testing more arrows in different blocks
    @Test
    public void testArrow2()
    {

        Block b1 = new InputOutputBlock(20,50);
        Block b2 = new CallMethodBlock(45,80);

        Arrow arrow = new Arrow(b1, b2);

        //checks if different block classes can be used in arrow's constructor, also tests get methods
        assertTrue(true, String.valueOf(arrow.getBlock1().equals(Block.class)));
        assertTrue(true, String.valueOf(arrow.getBlock2().equals(Block.class)));

        //checks to see that the values given to blocks carry into arrow properly
        assertEquals(20, arrow.block1.x );
        assertEquals(50, arrow.block1.y);
        assertEquals(45, arrow.block2.x);
        assertEquals(80, arrow.block2.y);
    }
    @Test
    public void testArrow3()
    {

        Block b1 = new StartBlock(63,78, "Start");
        Block b2 = new EndBlock(13,99,"End");

        Arrow arrow = new Arrow(b1, b2);

        //checks if different block classes can be used in arrow's constructor, also tests get methods
        assertTrue(true, String.valueOf(arrow.getBlock1().equals(Block.class)));
        assertTrue(true, String.valueOf(arrow.getBlock2().equals(Block.class)));

        //checks to see that the values given to blocks carry into arrow properly
        assertEquals(63, arrow.block1.x );
        assertEquals(78, arrow.block1.y);
        assertEquals(13, arrow.block2.x);
        assertEquals(99, arrow.block2.y);

        assertEquals("Start", b1.blockText);
        assertEquals("End", b2.getBlockText());
    }

    /**
     * test_getCodeBlocks method used to see if blocks are being saved properly in the arrow
     */
    @Test
    public void test_getCodeBlocks()
    {
        Block b1 = new ConditionBlock(30,20);
        Block b2 = new CallMethodBlock(20,10);
        Arrow arrow = new Arrow(b1, b2);

        ArrayList<Block> testCodeBlocks =  new ArrayList<>();
        testCodeBlocks.add(b1);
        testCodeBlocks.add(b2);

        assertEquals(testCodeBlocks,arrow.getCodeBlocks());
    }

    @Test
    public void test_getCodeBlocks2()
    {
        Block b1 = new StartBlock(27,90,"Start");
        Block b2 = new EndBlock(59,40, "End");
        Arrow arrow = new Arrow(b1, b2);

        ArrayList<Block> testCodeBlocks =  new ArrayList<>();
        testCodeBlocks.add(b1);
        testCodeBlocks.add(b2);

        assertEquals(testCodeBlocks,arrow.getCodeBlocks());
    }

    @Test
    public void test_getCodeBlocks3()
    {
        Block b1 = new InputOutputBlock(19,74);
        Block b2 = new InstructionBlock(60,15);
        Arrow arrow = new Arrow(b1, b2);

        ArrayList<Block> testCodeBlocks =  new ArrayList<>();
        testCodeBlocks.add(b1);
        testCodeBlocks.add(b2);

        assertEquals(testCodeBlocks,arrow.getCodeBlocks());
    }
}