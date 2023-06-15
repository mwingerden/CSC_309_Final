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


        //check that extra spaces are ignored
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
        assertTrue(((InputOutputBlock) p.get_blocks().get(1)).getBlockText().equals("abcd"));
        assertTrue(p.get_blocks().get(2) instanceof EndBlock);

    }

    @Test
    public void testIOMethod2(){
        //printing a number
        Parser p = new Parser("void method(){System.out.println(4);}");
        p.rule_method();
        assertTrue(p.get_blocks().get(0) instanceof StartBlock);
        assertTrue(p.get_blocks().get(1) instanceof InputOutputBlock);
        assertTrue(((InputOutputBlock) p.get_blocks().get(1)).getBlockText().equals("4"));
        assertTrue(p.get_blocks().get(2) instanceof EndBlock);
    }

    @Test
    public void testVariables1(){
        Parser p = new Parser("void method (){int i = 0;}");
        p.rule_method();
        assertTrue(p.get_blocks().get(0) instanceof StartBlock);
        assertTrue(p.get_blocks().get(1) instanceof VariableDeclarationBlock);
        assertEquals(((VariableDeclarationBlock) p.get_blocks().get(1)).getBlockText(), "int i");
        assertTrue(p.get_blocks().get(2) instanceof InstructionBlock);
        assertEquals(((InstructionBlock) p.get_blocks().get(2)).getBlockText(), "i = 0");
        assertTrue(p.get_blocks().get(3) instanceof EndBlock);

        Arrow arrow1 = (Arrow) p.get_arrows().get(0);
        Arrow arrow2 = (Arrow) p.get_arrows().get(1);
        Arrow arrow3 = (Arrow) p.get_arrows().get(2);
        assertEquals(p.get_arrows().size(), 3);

        assertTrue(arrow1.sourceShape instanceof StartBlock && arrow1.destinationShape instanceof VariableDeclarationBlock);
        assertTrue(arrow2.sourceShape instanceof VariableDeclarationBlock && arrow2.destinationShape instanceof InstructionBlock);
        assertTrue(arrow3.sourceShape instanceof InstructionBlock && arrow3.destinationShape instanceof EndBlock);
    }

    @Test
    public void testForBlock1(){
        Parser p = new Parser(("void method(){for(int i = 0; i < 4; i++){System.out.println(i);}}"));
        p.rule_method();
        assertTrue(p.get_blocks().get(0) instanceof StartBlock);
        assertTrue(p.get_blocks().get(1) instanceof VariableDeclarationBlock);
        assertEquals(((VariableDeclarationBlock) p.get_blocks().get(1)).getBlockText(), "int i");
        assertTrue(p.get_blocks().get(2) instanceof InstructionBlock);
        assertEquals(((InstructionBlock) p.get_blocks().get(2)).getBlockText(), "i = 0");
        assertTrue(p.get_blocks().get(3) instanceof InputOutputBlock);
        assertTrue(p.get_blocks().get(4) instanceof InstructionBlock);
        assertEquals(((InstructionBlock) p.get_blocks().get(4)).getBlockText(), "i++");
        assertTrue(p.get_blocks().get(5) instanceof ConditionBlock);
        assertTrue(p.get_blocks().get(6) instanceof EndBlock);

        assertEquals(p.get_blocks().size(), 7);

        Arrow arrow1 = (Arrow) p.get_arrows().get(0);
        Arrow arrow2 = (Arrow) p.get_arrows().get(1);
        Arrow arrow3 = (Arrow) p.get_arrows().get(2);
        Arrow arrow4 = (Arrow) p.get_arrows().get(3);
        Arrow arrow5 = (Arrow) p.get_arrows().get(4);
        Arrow arrow6 = (Arrow) p.get_arrows().get(5);
        Arrow arrow7 = (Arrow) p.get_arrows().get(6);
        assertEquals(p.get_arrows().size(), 7);

        assertTrue(arrow1.sourceShape instanceof StartBlock && arrow1.destinationShape instanceof VariableDeclarationBlock);
        assertTrue(arrow2.sourceShape instanceof VariableDeclarationBlock && arrow2.destinationShape instanceof InstructionBlock);
        assertTrue(arrow3.sourceShape instanceof InstructionBlock && arrow3.destinationShape instanceof ConditionBlock);
        assertTrue(arrow4.sourceShape instanceof ConditionBlock && arrow4.destinationShape instanceof InputOutputBlock);
        assertTrue(arrow5.sourceShape instanceof InputOutputBlock && arrow5.destinationShape instanceof InstructionBlock);
        assertTrue(arrow6.sourceShape instanceof InstructionBlock && arrow6.destinationShape instanceof ConditionBlock);
        assertTrue(arrow7.sourceShape instanceof ConditionBlock && arrow7.destinationShape instanceof EndBlock);
    }


    @Test
    public void testIfBlock1(){
        Parser p = new Parser(("void method(){if(x<10){System.out.println(x);}}"));
        p.rule_method();
        assertTrue(p.get_blocks().get(0) instanceof StartBlock);
        assertTrue(p.get_blocks().get(1) instanceof ConditionBlock);
        assertTrue(p.get_blocks().get(2) instanceof InputOutputBlock);
        assertTrue(p.get_blocks().get(3) instanceof EndBlock);
        assertTrue(p.get_blocks().size() == 4);

        Arrow arrow1 = (Arrow) p.get_arrows().get(0);
        Arrow arrow2 = (Arrow) p.get_arrows().get(1);
        Arrow arrow3 = (Arrow) p.get_arrows().get(2);
        Arrow arrow4 = (Arrow) p.get_arrows().get(3);

        assertTrue(arrow1.sourceShape instanceof StartBlock && arrow1.destinationShape instanceof ConditionBlock);
        assertTrue(arrow2.sourceShape instanceof ConditionBlock && arrow2.destinationShape instanceof InputOutputBlock);
        assertTrue(arrow3.sourceShape instanceof InputOutputBlock && arrow3.destinationShape instanceof EndBlock);
        assertTrue(arrow4.sourceShape instanceof ConditionBlock && arrow4.destinationShape instanceof EndBlock);
        assertEquals(p.get_arrows().size(),4);
    }
    @Test
    public void testIfBlock2(){
        Parser p = new Parser(("void method(){if(x<10){System.out.println(x);} System.out.println(4);}"));
        p.rule_method();
        assertTrue(p.get_blocks().get(0) instanceof StartBlock);
        assertTrue(p.get_blocks().get(1) instanceof ConditionBlock);
        assertTrue(p.get_blocks().get(2) instanceof InputOutputBlock);
        assertTrue(p.get_blocks().get(3) instanceof InputOutputBlock);
        assertTrue(p.get_blocks().get(4) instanceof EndBlock);
        assertTrue(p.get_blocks().size() == 5);

        Arrow arrow1 = (Arrow) p.get_arrows().get(0);
        Arrow arrow2 = (Arrow) p.get_arrows().get(1);
        Arrow arrow3 = (Arrow) p.get_arrows().get(2);
        Arrow arrow4 = (Arrow) p.get_arrows().get(3);
        Arrow arrow5 = (Arrow) p.get_arrows().get(4);
        assertEquals(p.get_arrows().size(), 5);

        assertTrue(arrow1.sourceShape instanceof StartBlock && arrow1.destinationShape instanceof ConditionBlock);
        assertTrue(arrow2.sourceShape instanceof ConditionBlock && arrow2.destinationShape instanceof InputOutputBlock);
        assertTrue(arrow3.sourceShape instanceof InputOutputBlock && arrow3.destinationShape instanceof InputOutputBlock);
        assertTrue(arrow4.sourceShape instanceof ConditionBlock && arrow4.destinationShape instanceof InputOutputBlock);
        assertTrue(arrow5.sourceShape instanceof  InputOutputBlock && arrow5.destinationShape instanceof EndBlock);
    }





}
