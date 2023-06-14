import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {
    @Test
    public void TokenTest(){
        Token t1 = new Token("delimiter", "{");
        Token t2 = new Token("delimiter", "{");
        Token t3 = new Token("integer", "{");
        Token t4 = new Token("delimiter", "}");

        assertEquals(t1,t2);
        assertNotEquals(t1, t3);
        assertNotEquals(t1,t4);
    }


    @Test
    public void LexerTest1(){
        //testing lexer, testing spaces and newlines are ignored
        Lexer l = new Lexer("public main(   )  \n {int i    = 10;}");
        ArrayList<Token> answer = new ArrayList<>();
        answer.add(new Token("keyword", "public"));
        answer.add(new Token("identifier", "main"));
        answer.add(new Token("delimiter", "("));
        answer.add(new Token("delimiter", ")"));
        answer.add(new Token("delimiter", "{"));
        answer.add(new Token("keyword", "int"));
        answer.add(new Token("identifier", "i"));
        answer.add(new Token("operator", "="));
        answer.add(new Token("integer", "10"));
        answer.add(new Token("delimiter", ";"));
        answer.add(new Token("delimiter", "}"));
        ArrayList<Token> output = l.constructLexer();

        for (int i = 0; i<output.size();i++){
            output.get(i).printToken();
            assertTrue(output.get(i).equals(answer.get(i)));
        }
        assertEquals(output.size(), answer.size());
    }



}
