import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.*;

public class Lexer {

    String input;

    String output;

    String[] kw = { "abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "extends", "false",
            "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native",
            "new", "null", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true",
            "try", "void", "volatile", "while" };

    List<String> keywords = Arrays.asList(kw);

    String[] ops = {"+", "-", "%", "*", "%", "=" ,"-=", "+=", "*=", "/=", "%=", "==", "!=",
            ">", "<", ">=", "<=", "&&", "||", "!", "++" , "--", "~", "^", "&", "<<", ">>", ">>>"};

    List<String> operators = Arrays.asList(ops);

    String[] dl = {"{", "}", "(", ")", " ", "\n", ";" ,"\""};

    List<String> delimiters = Arrays.asList(dl);

    public Lexer(String input){
        this.input = input;
    }

    public ArrayList<Token> constructLexer(){
        ArrayList<Token> lexed = new ArrayList<>();
        String current_word = "";
        for(int i = 0; i< input.length(); i++){
            if (operators.contains("" +input.charAt(i))){
                if (!current_word.equals( "")) {
                    if (keywords.contains(current_word)){
                        lexed.add(new Token("keyword", current_word));
                    }else {
                        lexed.add(new Token("identifier", current_word));
                    }
                }
                lexed.add(new Token("operator", "" + input.charAt(i)));
                current_word = "";

            }
            else if (delimiters.contains(""+ input.charAt(i))){
                if (!current_word.equals("")) {
                    if (keywords.contains(current_word)){
                        lexed.add(new Token("keyword", current_word));
                    }else {
                        lexed.add(new Token("identifier", current_word));
                    }
                }
                lexed.add(new Token("delimiter", "" + input.charAt(i)));
                current_word = "";
            }
            else if (keywords.contains(current_word)) {
                lexed.add(new Token ("keyword", current_word));
                current_word = "";
            }

            else {
                current_word  = current_word + input.charAt(i);
            }
            try {
                int in = Integer.parseInt(current_word);
                lexed.add(new Token("integer", current_word));
                current_word = "";
            }
            catch(NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return lexed;
    }


}