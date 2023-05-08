package com.tests;

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
        assertTrue(true, String.valueOf(arrow.getInBlock().equals(Block.class)));
        assertTrue(true, String.valueOf(arrow.getOutBlock().equals(Block.class)));

        //checks to see that the values given to blocks carry into arrow properly
        assertEquals(30, arrow.inBlock.x );
        assertEquals(20, arrow.inBlock.y);
        assertEquals(20, arrow.outBlock.x);
        assertEquals(10, arrow.outBlock.y);
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
}