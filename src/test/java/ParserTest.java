import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testRuleMethod(){
        Parser p = new Parser("void method(){}");
        assertDoesNotThrow(()->p.rule_method());


        //check that spaces are ignored
        Parser p2 = new Parser("void    method    (   )   {    }");
        assertDoesNotThrow(()->p2.rule_method());

    }

    @Test
    public void testIOMethod1(){
        //printing a string
        Parser p = new Parser("void method(){System.out.println(\"abcd\");}");
        p.rule_method();

        assertTrue(p.get_blocks().get(0) instanceof StartBlock);
        assertTrue(p.get_blocks().get(1) instanceof InputOutputBlock);
        assertTrue(((InputOutputBlock) p.get_blocks().get(1)).getText().equals("abcd"));
        assertTrue(p.get_blocks().get(2) instanceof EndBlock);

    }

    @Test
    public void testIOMethod2(){
        //printing a number
        Parser p = new Parser("void method(){System.out.println(4);}");
        p.rule_method();
        assertTrue(p.get_blocks().get(0) instanceof StartBlock);
        assertTrue(p.get_blocks().get(1) instanceof InputOutputBlock);
        assertTrue(((InputOutputBlock) p.get_blocks().get(1)).getText().equals("4"));
        assertTrue(p.get_blocks().get(2) instanceof EndBlock);
    }

    @Test
    public void testIfBlock1(){
        Parser p = new Parser(("void method(){if(x<10){System.out.println(x);}}"));
        p.rule_method();
        assertTrue(p.get_blocks().get(0) instanceof StartBlock);
        assertTrue(p.get_blocks().get(1) instanceof InputOutputBlock);
        assertTrue(p.get_blocks().get(2) instanceof ConditionBlock);
        assertTrue(p.get_blocks().get(3) instanceof EndBlock);
        assertTrue(p.get_blocks().size() == 4);

        Arrow arrow1 = (Arrow) p.get_arrows().get(0);
        Arrow arrow2 = (Arrow) p.get_arrows().get(1);
        Arrow arrow3 = (Arrow) p.get_arrows().get(2);
        Arrow arrow4 = (Arrow) p.get_arrows().get(3);

        System.out.println(arrow3.block2.getClass());
        assertTrue(arrow1.block1 instanceof StartBlock && arrow1.block2 instanceof ConditionBlock);
        assertTrue(arrow2.block1 instanceof ConditionBlock && arrow2.block2 instanceof InputOutputBlock);
        //assertTrue(arrow3.block1 instanceof ConditionBlock && arrow3.block2 instanceof EndBlock);
       // assertTrue(arrow4.block1 instanceof InputOutputBlock && arrow4.block2 instanceof EndBlock);
        assertEquals(p.get_arrows().size(),4);




    }


}
