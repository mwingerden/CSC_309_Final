import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<Token> input;
    int currentToken;
    Lexer lexer;
    ArrayList<Draw> outputBlocks;
    ArrayList<Draw> outputArrows;
    Block last_condition;
    boolean merge_condition;

    public Parser(String inputString)
    {
        lexer = new Lexer(inputString);
        input = lexer.constructLexer();
        currentToken = 0;
        outputBlocks = new ArrayList<>();
        outputArrows = new ArrayList<>();
        merge_condition = false;
        last_condition = null;
    }

    private boolean check(String checkType, String checkValue)
    {
        if (currentToken < input.size()) {
            Token token = input.get(currentToken);
            if (checkType.equals(token.getType()) && token.getWord().equals(checkValue)) {
                return true;
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
                                currentToken++;
                                System.out.println("Inputted a method!");
                                EndBlock eb = new EndBlock(0, 0, "Why string in this parameter?");
                                outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), eb));
                                if (merge_condition == true ){
                                    outputArrows.add( new Arrow(last_condition, eb));
                                    merge_condition = false;
                                }

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

    private void parseBody() {
        List<Draw> current_drawings = new ArrayList<>();
        while (currentToken < input.size() && !input.get(currentToken).getWord().equals("}")) {
            if (check("identifier", "System.out.println")){
                InputOutputBlock ioblock = rule_IO();
                current_drawings.add(ioblock);
            }
            else if (check("identifier", input.get(currentToken).getWord())){
                currentToken++;
                if (check("delimiter", "(")){
                    currentToken--;
                    CallMethodBlock cmb = rule_call_method();
                    current_drawings.add(cmb);
                }
                else if (check("operator", "=")){
                    currentToken--;
                    current_drawings.add(rule_assignment());
                }
            }
            else if(check("keyword", "if")){
                  current_drawings.add(rule_if());
            }
            else if(check("keyword", "for")){
                current_drawings.add(rule_for());
            }

            else if (check("keyword", input.get(currentToken).getWord())){
                VariableDeclarationBlock vdb = rule_variable();
                current_drawings.add(vdb);

            }
            currentToken++;
        }
        if (!check("delimiter", "}")) {
            error();
        }
        if (merge_condition == true ){
            if(!current_drawings.get(0).equals(last_condition)) {
                outputArrows.add(new Arrow(last_condition, (Block) current_drawings.get(0)));
                merge_condition = false;
            }
            else if(current_drawings.size()>1){
                outputArrows.add(new Arrow(last_condition, (Block) current_drawings.get(1)));
                merge_condition = false;
            }
        }

        return;
    }
    private InputOutputBlock rule_IO(){
        if (check("identifier", "System.out.println")){
            currentToken++;
            if (check("delimiter", "(")){
                currentToken++;
                if (check("identifier", input.get(currentToken).getWord()) ||
                        check("integer", input.get(currentToken).getWord())){
                    InputOutputBlock ioblock = new InputOutputBlock(0 , 0);
                    ioblock.setBlockText(input.get(currentToken).getWord());
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
                        ioblock.setBlockText(input.get(currentToken).getWord());
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

    private ConditionBlock rule_for(){
        if (check("keyword", "for")){
            currentToken ++;
            if(check("delimiter", "(")){
                currentToken++;
                if(check("keyword", "int")){
                    rule_variable();
                    currentToken++;
                    if (check("identifier", input.get(currentToken).getWord())){
                        String conditionString = "";
                        conditionString+= input.get(currentToken).getWord() + " ";
                        currentToken++;
                        if (check("operator", "<") || check("operator", "<=")){
                            conditionString+= input.get(currentToken).getWord() + " ";
                            currentToken++;
                            if(check("integer", input.get(currentToken).getWord())){
                                conditionString+= input.get(currentToken).getWord();

                                ConditionBlock cb = new ConditionBlock(0,0);
                                cb.setBlockText(conditionString);
                                outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), cb));
                                outputBlocks.add(cb);
                                currentToken++;
                                if (check("delimiter", ";")){
                                    currentToken++;
                                    if (check("identifier", input.get(currentToken).getWord())){
                                        String instructionString = "";
                                        while(!check("delimiter", ")")){
                                            instructionString+= input.get(currentToken).getWord();
                                            currentToken++;
                                        }
                                        currentToken++;
                                        if (check("delimiter", "{")){
                                            parseBody();
                                            InstructionBlock ib = new InstructionBlock(0,0);
                                            ib.setBlockText(instructionString);
                                            outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), ib));
                                            outputBlocks.add(ib);

                                            outputArrows.add(new Arrow(ib, cb));

                                            outputBlocks.remove(cb);
                                            outputBlocks.add(cb);
                                            return cb;
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
        return  new ConditionBlock(0,0);
    }
    private ConditionBlock rule_if(){
        if (check("keyword", "if")){
            currentToken++;
            if(check("delimiter", "(")){
                String insideParen = "";
                while (!check("delimiter", ")")){
                    insideParen += input.get(currentToken).getWord();
                    currentToken++;
                }
                ConditionBlock cb = new ConditionBlock(0,0);
                cb.setBlockText(insideParen.strip());
                last_condition = cb;
                outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), cb));
                outputBlocks.add(cb);
                currentToken++;
                if (check("delimiter", "{")){
                    parseBody();
                    last_condition = cb;
                    merge_condition = true;
                    if(check("delimiter", "}")){
                            return cb;
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
        return new ConditionBlock(0,0);
    }


    private VariableDeclarationBlock rule_variable(){
        if (check("keyword", "int")|| check("keyword", "String")){
            currentToken++;
            if (check("identifier", input.get(currentToken).getWord())){
                VariableDeclarationBlock vdb = new VariableDeclarationBlock(0, 0);
                vdb.setBlockText(input.get(currentToken-1).getWord() + " " + input.get(currentToken).getWord());
                outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), vdb));
                outputBlocks.add(vdb);
                currentToken++;
                if(check("delimiter", ";")){
                    return vdb;
                }
                else if (check("operator", "=")){
                    currentToken--;
                    rule_assignment();
                    return vdb;
                }
                else{
                    error();
                }
            }
            else{
                error();
            }
        }
        else {
            error();
        }

        return new VariableDeclarationBlock(0,0);
    }

    private InstructionBlock rule_assignment(){
        if (check("identifier", input.get(currentToken).getWord())){
            String varname = input.get(currentToken).getWord();
            currentToken++;
            if (check("operator", "=")){
                currentToken++;
                if(check("integer", input.get(currentToken).getWord())){
                   InstructionBlock ib = new InstructionBlock(0,0);
                   ib.setBlockText(varname + " = " + input.get(currentToken).getWord());
                    outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), ib));
                    outputBlocks.add(ib);
                    currentToken++;
                    if (check("delimiter", ";")){
                        return ib;
                    }
                    else{
                        error();
                    }
                }
                else if (check("delimiter", "\"")){
                    currentToken++;
                    if(check("identifier", input.get(currentToken).getWord())){
                        InstructionBlock ib = new InstructionBlock(0,0);
                        ib.setBlockText(varname + " = \"" + input.get(currentToken).getWord() + "\"");
                        outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), ib));
                        outputBlocks.add(ib);
                        currentToken++;
                        if (check("delimiter", "\"")){
                            currentToken++;
                            if (check("delimiter", ";")){

                                return ib;
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
        return new InstructionBlock(0,0);
    }

    private CallMethodBlock rule_call_method(){
        if(check("identifier", input.get(currentToken).getWord())){
            currentToken++;
            CallMethodBlock cmb = new CallMethodBlock(0,0);
            cmb.setBlockText(input.get(currentToken).getWord());
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

    private InstructionBlock rule_instruction(){
        String instruction = "";
        while (!check("delimiter", ";")){

            instruction += input.get(currentToken).getWord();
            currentToken++;
        }
        InstructionBlock ib = new InstructionBlock(0,0);
        ib.setBlockText(instruction.strip());
        outputArrows.add(new Arrow((Block)outputBlocks.get(outputBlocks.size()-1), ib));
        outputBlocks.add(ib);
        currentToken++;
        return ib;
    }

    private void error() {
        throw new RuntimeException();
    }
}