
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EndBlockTest {

    EndBlock eb1;
    EndBlock eb2;

    @BeforeEach
    void setup(){
        eb1 = new EndBlock(40, 40);
        eb2 = new EndBlock( 200,200);
    }

    @Test
    public void testCenterCoords(){
        assertEquals(eb1.getCenterX(), 80);
        assertEquals(eb1.getCenterY(), 80);

        assertEquals(eb2.getCenterX(), 240);
        assertEquals(eb2.getCenterY(), 240);
    }

    @Test
    public void testarrowsinouts(){
        assertTrue(eb1.checkInGoing());
        //ingoing ++ now = 1
        assertFalse(eb1.checkOutGoing());

        assertTrue(eb1.checkInGoing());

        assertFalse(eb1.checkInGoing());

    }

    @Test
    public void testContains(){
        assertTrue(eb1.contains(50, 50));
        assertTrue(eb1.contains(100,100));
        assertFalse(eb1.contains(200, 200));
    }

}
