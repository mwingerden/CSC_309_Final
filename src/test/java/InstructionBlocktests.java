
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//for each class:
//test center coordinates, amount of in/out arrows validity, check contains


public class InstructionBlocktests {
    CallMethodBlock ib1;
    CallMethodBlock ib2;

    @BeforeEach
    void setup(){
        ib1 = new CallMethodBlock(40, 40);
        ib2 = new CallMethodBlock( 200,200);
    }

    @Test
    public void testCenterCoords(){
        assertEquals(ib1.getCenterX(), 115);
        assertEquals(ib1.getCenterY(), 77);

        assertEquals(ib2.getCenterX(), 275);
        assertEquals(ib2.getCenterY(), 237);
    }

    @Test
    public void testarrowsinouts(){
        assertTrue(ib1.checkInGoing());
        //ingoing ++ now = 1
        assertTrue(ib1.checkOutGoing());
        //outgoing ++ now = 1
        assertFalse(ib1.checkOutGoing());
        assertTrue(ib1.checkInGoing());
        assertFalse(ib1.checkInGoing());
    }

    @Test
    public void testContains(){
        assertTrue(ib1.contains(50, 50));
        assertFalse(ib1.contains(200,200));
    }

}
