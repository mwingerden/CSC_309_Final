import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<Token> input;
    int currentToken;
    Lexer lexer;
    ArrayList<Draw> outputBlocks;
    ArrayList<Draw> outputArrows;

    public Parser(String inputString)
    {
        lexer = new Lexer(inputString);
        input = lexer.constructLexer();
        currentToken = 0;
        outputBlocks = new ArrayList<>();
        outputArrows = new ArrayList<>();
    }

    private boolean check(String checkType, String checkValue)
    {
        if (currentToken < input.size()) {
            Token token = input.get(currentToken);
            if (checkType.equals(token.getType()) && token.getWord().equals(checkValue)) {
                return true;
            }
            if (token.getType().equals("delimiter") && token.getWord().equals(" ")){
                currentToken++;
                return check(checkType, checkValue);
            }
        }
        return false;
    }

    public ArrayList<Draw> get_blocks(){
        return this.outputBlocks;
    }

    public ArrayList<Draw> get_arrows(){
        return this.outputArrows;
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
                            outputBlocks.add(new StartBlock(0 ,0 , "irrelevant_color"));
                            parseBody();
                            if (check("delimiter", "}")) {
                                System.out.println("Inputted a method!");
                                EndBlock eb = new EndBlock(0, 0, "Why string in this parameter?");
                                outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), eb));
                                outputBlocks.add(eb);

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

    private Draw parseBody() {
        List<Draw> current_drawings = new ArrayList<>();
        while (currentToken < input.size() && !input.get(currentToken).getWord().equals("}")) {
            if (check("identifier", "System.out.println")){
                InputOutputBlock ioblock = rule_IO();
                current_drawings.add(ioblock);
            }
            else if (check("identifier", input.get(currentToken).getWord())){
                CallMethodBlock cmb = rule_call_method();
                current_drawings.add(cmb);
            }
            else if(check("keyword", "if")){
                  rule_if();
            }
            currentToken++;
        }
        if (!check("delimiter", "}")) {
            error();
        }
        return current_drawings.get(0);
    }
    private InputOutputBlock rule_IO(){
        if (check("identifier", "System.out.println")){
            currentToken++;
            if (check("delimiter", "(")){
                currentToken++;
                if (check("identifier", input.get(currentToken).getWord()) ||
                        check("integer", input.get(currentToken).getWord())){
                    InputOutputBlock ioblock = new InputOutputBlock(0 , 0);
                    ioblock.setText(input.get(currentToken).getWord());
                    outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), ioblock));
                    outputBlocks.add(ioblock);
                    currentToken++;
                    if(check("delimiter", ")")){
                        currentToken++;
                        if(check("delimiter", ";")){

                            return ioblock;
                        }
                        else{error();}
                    }
                    else{error();}
                }
                else if (check("delimiter", "\"")){
                    currentToken++;
                    if(check("identifier", input.get(currentToken).getWord())){
                        InputOutputBlock ioblock = new InputOutputBlock(0 , 0);
                        ioblock.setText(input.get(currentToken).getWord());
                        outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), ioblock));
                        outputBlocks.add(ioblock);
                        currentToken++;
                        if(check("delimiter", "\"")){
                            currentToken++;
                            if(check("delimiter", ")")){
                                currentToken++;
                                if(check("delimiter", ";")){

                                    return ioblock;
                                }
                                else{error();}
                            }
                            else{error();}
                        }
                    }
                    else{
                        error();
                    }
                }else{
                    error();
                }
            }
            else{
                error();
            }
        }
        return new InputOutputBlock(0,0);
    }

    private void rule_for(){
        if (check("keyword", "for")){
            currentToken ++;
            if(check("delimiter", "(")){
                currentToken++;
                if(check("identifier", input.get(currentToken).getWord())){
                    VariableDeclarationBlock vblock = new VariableDeclarationBlock(0 , 0);
                    vblock.setText(input.get(currentToken).getWord());
                    outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), vblock));
                    outputBlocks.add(vblock);
                    currentToken++;
                }

            }
        }

    }
    private void rule_if(){
        if (check("keyword", "if")){
            currentToken++;
            if(check("delimiter", "(")){
                String insideParen = "";
                while (!check("delimiter", ")")){
                    insideParen += input.get(currentToken).getWord();
                    currentToken++;
                }
                ConditionBlock cb = new ConditionBlock(0,0);
                cb.setText(insideParen.strip());
                outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), cb));
                outputBlocks.add(cb);
                currentToken++;
                if (check("delimiter", "{")){

                    parseBody();
                }
                else{
                    error();
                }
            }
            else{
                error();
            }
        }
        else{
            error();
        }
    }


    private void rule_variable(){
        if (check("keyword", "int")|| check("keyword", "String")){
            currentToken++;
            if (check("identifier", input.get(currentToken).getWord())){
                VariableDeclarationBlock vdb = new VariableDeclarationBlock(0, 0);
                vdb.setText(input.get(currentToken-1).getWord() + " " + input.get(currentToken).getWord());
                outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), vdb));
                outputBlocks.add(vdb);
                currentToken++;
                if(check("delimiter", ";")){
                    return;
                }
                else if (check("operator", "=")){
                    currentToken--;
                    rule_assignment();
                    return;
                }

            }
        }
    }

    private void rule_assignment(){
        if (check("identifier", input.get(currentToken).getWord())){
            String varname = input.get(currentToken).getWord();
            currentToken++;
            if (check("operator", "=")){
                currentToken++;
                if(check("integer", input.get(currentToken).getWord())){
                   InstructionBlock ib = new InstructionBlock(0,0);
                   ib.setText(varname + " = " + input.get(currentToken).getWord());
                    outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), ib));
                    outputBlocks.add(ib);
                    currentToken++;
                    if (check("delimiter", ";")){
                        return;
                    }
                    else{
                        error();
                    }
                }
                else if (check("delimiter", "\"")){
                    currentToken++;
                    if(check("identifier", input.get(currentToken).getWord())){
                        InstructionBlock ib = new InstructionBlock(0,0);
                        ib.setText(varname + " = \"" + input.get(currentToken).getWord() + "\"");
                        outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), ib));
                        outputBlocks.add(ib);
                        currentToken++;
                        if (check("delimiter", "\"")){
                            currentToken++;
                            if (check("delimiter", ";")){
                                return;
                            }
                            else{
                                error();
                            }

                        }
                        else{
                            error();
                        }
                    }
                    else{
                        error();
                    }

                }
                else{
                    error();
                }
            }
            else{
                error();
            }
        }
        else{
            error();
        }
    }

    private CallMethodBlock rule_call_method(){
        if(check("identifier", input.get(currentToken).getWord())){
            currentToken++;
            CallMethodBlock cmb = new CallMethodBlock(0,0);
            cmb.setText(input.get(currentToken).getWord());
            outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), cmb));
            outputBlocks.add(cmb);
            if (check("delimiter", "(")){
                currentToken++;
                if(check("delimiter", ")")){
                    currentToken++;
                    if(check("delimiter", ";")){
                        return cmb;
                    }
                    else{
                        error();
                    }
                }
                else{
                    error();
                }
            }
            else{
                error();
            }
        }
        else{
            error();
        }
        return  new CallMethodBlock(0,0);
    }

    private void error() {
        throw new RuntimeException();
    }

}