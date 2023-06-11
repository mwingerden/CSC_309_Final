import java.util.List;

public class Parser {
    List<Token> input;
    int currentToken;
    Lexer lexer;

    public Parser(String inputString)
    {
        lexer = new Lexer(inputString);
        input = lexer.constructLexer();
        currentToken = 0;
    }

    private boolean check(String checkType, String checkValue)
    {
        if (currentToken < input.size()) {
            Token token = input.get(currentToken);
            if (checkType.equals(token.getType()) && token.getWord().equals(checkValue)) {
                currentToken++;
                return true;
            }
        }
        error();
        return false;
    }

    public void rule_method() {
        currentToken = 0;
        if (check("keyword", "void")) {
            currentToken++;
            if (check("identifier", "method")) {
                currentToken++;
                if (check("delimiter", "(")) {
                    currentToken++;
                    if (check("delimiter", ")")) {
                        currentToken++;
                        if (check("delimiter", "{")) {
                            currentToken++;
                            parseBody();
                            if (check("delimiter", "}")) {
                                System.out.println("Inputted a method!");
                            } else {
                                error();
                            }
                        } else {
                            error();
                        }
                    } else {
                        error();
                    }
                } else {
                    error();
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void parseBody() {
        if (!check("delimiter", "{")) {
            return;
        }
        while (currentToken < input.size() && !input.get(currentToken).getWord().equals("}")) {
            if (check("identifier", "System.out.println")){
                rule_IO();
            }

            currentToken++;
        }
        if (!check("delimiter", "}")) {
            error();
        }
    }
    private void rule_IO(){
        if (check("identifier", "System.out.println")){
            currentToken++;
            if (check("delimiter", "(")){
                currentToken++;
                if (check("identifier", input.get(currentToken).getWord()) ||
                        check("integer", input.get(currentToken).getWord())){
                    System.out.println("blank");
                }
            }
            else{
                error();
            }
        }
    }

    private void error() {
        throw new RuntimeException();
    }

}