public class Token {
    private String type;
    private String word;

    public Token(String t, String w){
        type =t;
        word = w;
    }

    public String getType(){
        return type;
    }

    public String getWord(){
        return word;
    }

    public void printToken(){System.out.println( "|" + type + " " + word);}

    public boolean equals(Object o){
        if (o == this){
            return true;
        }

        if (!(o instanceof Token)) {
            return false;
        }
        Token e = (Token) o;
        return (this.type.equals(((Token) o).getType()) && this.word.equals(((Token) o).getWord()));
    }

}
