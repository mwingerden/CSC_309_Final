import java.util.Observable;

public class Repository extends Observable {
    private static final Repository instance = new Repository();

    String blockToDraw;

    private Repository() {
        this.blockToDraw = "If Block";
    }

    public static Repository getInstance() {
        return instance;
    }

    public String getBlockToDraw() {
        return blockToDraw;
    }

    public void setBlockToDraw(String blockToDraw) {
        this.blockToDraw = blockToDraw;
    }
}
