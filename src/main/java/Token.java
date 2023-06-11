public class Token {
    String type;
    String word;

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

    public void PrintToken(){System.out.println( "|" + type + " " + word);}

}
