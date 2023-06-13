
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//for each class:
//test center coordinates, amount of in/out arrows validity, check contains


public class InputOutputBlockTest {
    InputOutputBlock iob1;
    InputOutputBlock iob2;

    @BeforeEach
    void setup(){
        iob1 = new InputOutputBlock(40, 40);
        iob2 = new InputOutputBlock( 200,200);
    }

    @Test
    public void testCenterCoords(){
        assertEquals(iob1.getCenterX(), 65);
        assertEquals(iob1.getCenterY(), 40);

        assertEquals(iob2.getCenterX(), 225);
        assertEquals(iob2.getCenterY(), 200);
    }


    @Test
    public void testContains(){
        assertTrue(iob1.contains(50, 40));
        assertFalse(iob1.contains(30,40));
        assertFalse(iob1.contains(50, 80));
    }

}
