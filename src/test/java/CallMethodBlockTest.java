
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//for each class:
//test center coordinates, amount of in/out arrows validity, check contains


public class CallMethodBlockTest {
    CallMethodBlock cmb1;
    CallMethodBlock cmb2;

    @BeforeEach
    void setup(){
        cmb1 = new CallMethodBlock(40, 40);
        cmb2 = new CallMethodBlock( 200,200);
    }

    @Test
    public void testCenterCoords(){
        assertEquals(cmb1.getCenterX(), 115);
        assertEquals(cmb1.getCenterY(), 77);

        assertEquals(cmb2.getCenterX(), 275);
        assertEquals(cmb2.getCenterY(), 237);
    }

    @Test
    public void testarrowsinouts(){
        assertTrue(cmb1.checkInGoing());
        //ingoing ++ now = 1
        assertTrue(cmb1.checkOutGoing());
        //outgoing ++ now = 1
        assertFalse(cmb1.checkOutGoing());
        assertTrue(cmb1.checkInGoing());
        assertFalse(cmb1.checkInGoing());
    }

    @Test
    public void testContains(){
        assertTrue(cmb1.contains(50, 50));
        assertFalse(cmb1.contains(200,200));
    }

}
