
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The abstract Block class represents the different blocks that the user can pick from the menu.
 */
public abstract class Block implements Draw
{
    protected int x, y, x2, y2;

    protected Color color;
    protected String blockText = "";
    private List<String> hintText = new ArrayList<>();

    private int studentHintIndex = 0;
    protected int arrowInLimit;
    protected int arrowOutLimit;
    protected int arrowInCount = 0;
    protected int arrowOutCount = 0;
    protected ArrayList<Arrow> inArrows;
    protected ArrayList<Arrow> outArrows;
    /**
     * These are the abstract methods that all the blocks will implement.
     * @param g
     */
    public abstract void draw(Graphics g);
    public abstract int getCenterX();
    public abstract int getCenterY();
    /**
     * The block constructor taking all the needed parameters for each block that will be drawn.
     * @param x, first x coordinate
     * @param y, first y coordinate
     * @param x2, second x coordinate
     * @param y2, second y coordinate
     * @param c, color of block
     * @param arrowInLimit, limit of arrows going into block
     * @param arrowOutLimit, limit of arrow going out the block
     */
    public Block(int x, int y, int x2, int y2, String c,int arrowInLimit, int arrowOutLimit)
    {
        this.x=x;
        this.y=y;
        this.x2=x2;
        this.y2=y2;
        this.arrowInLimit = arrowInLimit;
        this.arrowOutLimit = arrowOutLimit;
        this.inArrows = new ArrayList<>();
        this.outArrows = new ArrayList<>();
        switch (c) {
            case ("Black"):
                color = Color.BLACK;
                break;
            case("Red"):
                color=Color.RED; break;
            default: color=Color.WHITE;
        }
        this.blockText = null;
    }

    public void setColor(Color c){
        color = c;
    }
    public Color getColor(){
        return color;
    }

    /**
     * checkoutGoing method checks if arrow count out is less than the blocks out arrow limit.
     * @return false
     */
    public boolean checkOutGoing(){
        if(arrowOutCount < arrowOutLimit)
        {
            arrowOutCount++;
            return true;
        }
        return false;
    }
    /**
     * checkInGoing method checks if arrow count incoming is less than the blocks incoming arrow limit.
     * @return false
     */
    public boolean checkInGoing()
    {
        if(arrowInCount < arrowInLimit)
        {
            arrowInCount++;
            return true;
        }
        return false;
    }
    /**
     * A contains method that checks if a given x and y is inside coordinates(x1,x2,y1,y2).
     * @param xcoord, x coordinate to check
     * @param ycoord, y coordinate to check
     * @return
     */
    boolean contains(int xcoord, int ycoord){
        return (xcoord>x && xcoord<x2 && ycoord> y && ycoord<y2);
    }
    /**
     * * Getter methods for each of the different coordinates.
     * @return the desired coordinate
     */
    int getX1(){
        return this.x;
    }
    int getY1(){
        return this.y;
    }
    int getX2(){
        return this.x2;
    }
    int getY2(){
        return this.y2;
    }
    void setX1(int x){
        this.x = x;
    }
    void setY1(int y){
        this.y = y;
    }
    void setX2(int x){
        this.x2 = x;
    }
    void setY2(int y){
        this.y2 = y;
    }
    /**
     * Set and get methods for the strings text in the block.
     * @param blockText, of the block
     */
    public void setBlockText(String blockText){
        this.blockText = blockText;
    }
    public String getBlockText(){
        return this.blockText;
    }

    public void resetStudentHintIndex() {
        this.studentHintIndex = 0;
    }

    public void studentSideHint() {
        String currentHint = "No hint for selected block.";
        if (!this.hintText.isEmpty()) {
            if (studentHintIndex == this.hintText.size() - 1) {
                currentHint = String.join("\n", this.hintText);
            } else {
                currentHint = String.join("\n", this.hintText.subList(0, studentHintIndex + 1));
            }
        }
        JOptionPane.showMessageDialog(
                new WorkSpace(),
                currentHint,
                "Hints for Clicked Block",
                JOptionPane.INFORMATION_MESSAGE
        );
        if (studentHintIndex < this.hintText.size() - 1) {
            studentHintIndex++;
        }
    }

    public void teacherSideHint() {
        String currentHint = "No hint for selected block.";
        if (!this.hintText.isEmpty()) {
            currentHint = String.join("\n", this.hintText);
        }
        int option = JOptionPane.showConfirmDialog(
                new WorkSpace(),
                ("Current Hint(s): \n" + currentHint + "\n Change hint(s)?"),
                "Current Hint(s)",
                JOptionPane.YES_NO_OPTION
        );
        if (option == JOptionPane.YES_OPTION) {
            JTextArea hintEdit = new JTextArea();
            hintEdit.setEditable(true);
            if (!currentHint.equals("No hint for selected block.")) {
                hintEdit.setText(currentHint);
            }

            JScrollPane hintScroll = new JScrollPane(hintEdit,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
            );
            hintScroll.setMinimumSize(new Dimension(100, 100));
            Object[] editOptions = {"Save", "Cancel"};

            option = JOptionPane.showOptionDialog(
                    null,
                    hintScroll,
                    "Edit hints",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    editOptions,
                    editOptions[1]);
            if (option == JOptionPane.YES_OPTION) {
                this.hintText = List.of(hintEdit.getText().split("\n"));
            }
        }
    }

    public void setHintText(List<String> hintText){
        this.hintText = hintText;
    }
    public List<String> getHintText(){
        return this.hintText;
    }

    public int getArrowInLimit() {
        return arrowInLimit;
    }

    public void setArrowInLimit(int arrowInLimit) {
        this.arrowInLimit = arrowInLimit;
    }

    public int getArrowOutLimit() {
        return arrowOutLimit;
    }

    public void setArrowOutLimit(int arrowOutLimit) {
        this.arrowOutLimit = arrowOutLimit;
    }

    public int getArrowInCount() {
        return arrowInCount;
    }

    public void setArrowInCount(int arrowInCount) {
        this.arrowInCount = arrowInCount;
    }

    public int getArrowOutCount() {
        return arrowOutCount;
    }

    public void setArrowOutCount(int arrowOutCount) {
        this.arrowOutCount = arrowOutCount;
    }

    public void addInArrow(Arrow a){
        this.inArrows.add(a);
    }
    public void addOutArrow(Arrow a){
        this.outArrows.add(a);
    }

    public void removeInArrow(Arrow a){
        this.inArrows.remove(a);
        this.arrowInCount-=1;
    }
    public void removeOutArrow(Arrow a){
        this.outArrows.remove(a);
        this.arrowOutLimit -=1;
    }
    public ArrayList<Arrow> getOutArrows(){
        return this.outArrows;

    }
    public ArrayList<Arrow> getInArrows(){
        return this.inArrows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return arrowInLimit == block.arrowInLimit
                && arrowOutLimit == block.arrowOutLimit && arrowInCount == block.arrowInCount &&
                arrowOutCount == block.arrowOutCount && Objects.equals(color, block.color) &&
                Objects.equals(blockText, block.getBlockText());

    }
}