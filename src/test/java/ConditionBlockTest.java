
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class ConditionBlockTest {

    ConditionBlock cb1;
    ConditionBlock cb2;

    @BeforeEach
    void setup(){
        cb1 = new ConditionBlock(40, 40);
        cb2 = new ConditionBlock( 200,200);
    }

    @Test
    public void testCenterCoords(){
        assertEquals(cb1.getCenterX(), 90);
        assertEquals(cb1.getCenterY(), 90);

        assertEquals(cb2.getCenterX(), 250);
        assertEquals(cb2.getCenterY(), 250);
    }

    @Test
    public void testarrowsinouts(){
        assertTrue(cb1.checkInGoing());
        //ingoing ++ now = 1
        assertTrue(cb1.checkOutGoing());
        //outgoing ++ now = 1
        assertTrue(cb1.checkOutGoing());
        assertTrue(cb1.checkInGoing());

        assertFalse(cb1.checkInGoing());
        assertFalse(cb1.checkOutGoing());
    }

    @Test
    public void testContains(){
        assertFalse(cb1.contains(50, 50));
        assertTrue(cb1.contains(100,100));
    }

}
