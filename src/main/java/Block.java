
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The abstract Block class represents the different blocks that the user can pick from the menu.
 */
public abstract class Block implements Draw {
    protected int x;

    protected int y;
    protected int x2;
    protected int y2;

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
     * Get the x value of the center point of a block.
     * @return int representing the x value of the center point of a block.
     */
    public abstract int getCenterX();

    /**
     * Get the y value of the center point of a block.
     * @return int representing the y value of the center point of a block.
     */
    public abstract int getCenterY();

    /**
     * The block constructor taking all the needed parameters for each block that will be drawn.
     * @param x, shape's top left x coordinate
     * @param y, shape's top left y coordinate
     * @param x2, shape's top left x coordinate + desired width of the shape
     * @param y2, shape's top left y coordinate + desired width of the shape
     * @param color, color of block
     * @param arrowInLimit, limit of arrows going into block
     * @param arrowOutLimit, limit of arrow going out the block
     */
    protected Block(int x, int y, int x2, int y2, Color color, int arrowInLimit, int arrowOutLimit) {
        this.x=x;
        this.y=y;
        this.x2=x2;
        this.y2=y2;
        this.arrowInLimit = arrowInLimit;
        this.arrowOutLimit = arrowOutLimit;
        this.inArrows = new ArrayList<>();
        this.outArrows = new ArrayList<>();
        this.color = color;
        this.blockText = null;
    }

    /**
     * Set the block color.
     * @param color color to be set.
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Get the block color.
     * @return block color
     */
    public Color getColor(){
        return color;
    }

    /**
     * Checks if the outgoing arrow count is less than the limit and increments the outgoing arrow count if so.
     * @return true if the arrow count has not reached the limit and false otherwise.
     */
    public boolean checkOutGoing(){
        if(arrowOutCount < arrowOutLimit) {
            arrowOutCount++;
            return true;
        }
        return false;
    }

    /**
     * Checks if the incoming arrow count is less than the limit and increments the incoming arrow count if so.
     * @return true if the arrow count has not reached the limit and false otherwise.
     */
    public boolean checkInGoing() {
        if(arrowInCount < arrowInLimit) {
            arrowInCount++;
            return true;
        }
        return false;
    }

    /**
     * Checks if a given x and y is inside the block coordinates (x1,x2,y1,y2).
     * @param xCoord, x coordinate to check
     * @param yCoord, y coordinate to check
     * @return true if contained inside the block coordinates and false otherwise.
     */
    public boolean contains(int xCoord, int yCoord){
        return (xCoord >= x && xCoord <= x2 && yCoord >= y && yCoord <= y2);
    }

    /**
     * Checks if the passed in block will overlap with the block the method is called on.
     * @param otherBlock block that needs to be checked for overlap.
     * @return true if the blocks overlap and false if they do not.
     */
    public boolean collides(Block otherBlock) {
        Area thisShapeArea = this.getShapeArea();
        Area otherShapeArea = otherBlock.getShapeArea();
        Area collisionOne = new Area(thisShapeArea);
        Area collisionTwo = new Area(otherShapeArea);
        collisionOne.subtract(otherShapeArea);
        collisionTwo.subtract(thisShapeArea);
        return (!collisionOne.equals(thisShapeArea) || !collisionTwo.equals(otherShapeArea));
    }

    /**
     * Get the area of the block.
     * @return Area object of the block.
     */
    public abstract Area getShapeArea();

    /**
     * Get the shape's top left x coordinate
     * @return int representing the shape's top left x coordinate
     */
    public int getX1(){
        return this.x;
    }

    /**
     * Get the shape's top left y coordinate
     * @return int representing the shape's top left y coordinate
     */
    public int getY1(){
        return this.y;
    }

    /**
     * Get the shape's top left x coordinate + width of the shape
     * @return int representing the shape's top left x coordinate + width of the shape
     */
    public int getX2(){
        return this.x2;
    }

    /**
     * Get the shape's top left y coordinate + width of the shape
     * @return int representing the shape's top left y coordinate + width of the shape
     */
    public int getY2(){
        return this.y2;
    }

    /**
     * Set the shape's top left x coordinate
     */
    public void setX1(int x){
        this.x = x;
    }

    /**
     * Set the shape's top left y coordinate
     */
    public void setY1(int y){
        this.y = y;
    }

    /**
     * Set the shape's top left x coordinate + width of the shape
     */
    public void setX2(int x){
        this.x2 = x;
    }

    /**
     * Set the shape's top left y coordinate + width of the shape
     */
    public void setY2(int y){
        this.y2 = y;
    }

    /**
     * Set the display text of the block.
     * @param blockText, display text of the block
     */
    public void setBlockText(String blockText){
        this.blockText = blockText;
    }

    /**
     * Get the display text of the block.
     * @return display text of the block
     */
    public String getBlockText(){
        return this.blockText;
    }

    /**
     * Reset the hint index of the displayed hints so that when the student asks for a block specific hint it shows the
     * first hint again.
     */
    public void resetStudentHintIndex() {
        this.studentHintIndex = 0;
    }

    /**
     * Meant for the student side display. Shows a JOptionPane with the hints of the selected block up to the selected
     * index of the hint list associated with the block.
     */
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
                null,
                currentHint,
                "Hints for Clicked Block",
                JOptionPane.INFORMATION_MESSAGE
        );
        if (studentHintIndex < this.hintText.size() - 1) {
            studentHintIndex++;
        }
    }

    /**
     * Meant for the teacher side display. Shows a JOptionPane with all the current hints in the block and asks if the
     * teacher would like to change them. If yes, shows another JOptionPane where the teacher may edit the hints.
     * This method expects that all hints are separated with \n.
     */
    public void teacherSideHint() {
        String currentHint = "No hint for selected block.";
        if (!this.hintText.isEmpty()) {
            currentHint = String.join("\n", this.hintText);
        }
        int option = JOptionPane.showConfirmDialog(
                null,
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
            hintScroll.setPreferredSize(new Dimension(400, 200));
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
                this.hintText = new ArrayList<>(List.of(hintEdit.getText().split("\n")));
            }
        }
    }

    /**
     * Generate or update two preset hints relating to how many arrows are coming and going from the block.
     */
    public void autoGenHints() {
        Optional<String> oldIncoming = this.hintText.stream()
                .filter(hintString ->
                        hintString.matches("There (are \\d+|is 1) incoming arrow(s)? connected to this block."))
                .findFirst();
        String newIncoming = String.format("There are %d incoming arrows connected to this block.", this.arrowInCount);
        if (arrowInCount == 1) {
            newIncoming = "There is 1 incoming arrow connected to this block.";
        }
        if (oldIncoming.isEmpty()) {
            this.hintText.add(newIncoming);
        } else {
            this.hintText.set(this.hintText.indexOf(oldIncoming.get()), newIncoming);
        }

        Optional<String> oldOutgoing = this.hintText.stream()
                .filter(hintString ->
                        hintString.matches("There (are \\d+|is 1) outgoing arrow(s)? connected to this block."))
                .findFirst();
        String newOutgoing = String.format("There are %d outgoing arrows connected to this block.", this.arrowOutCount);
        if (arrowOutCount == 1) {
            newOutgoing = "There is 1 outgoing arrow connected to this block.";
        }
        if (oldOutgoing.isEmpty()) {
            this.hintText.add(newOutgoing);
        } else {
            this.hintText.set(this.hintText.indexOf(oldOutgoing.get()), newOutgoing);
        }
    }

    /**
     * Set the list of hints assigned to the block it is called on.
     * @param hintText list of strings that the current hint list is being replaced with.
     */
    public void setHintText(List<String> hintText){
        this.hintText = hintText;
    }

    /**
     * Get the list of hints assigned to the block it is called on.
     * @return list of strings containing the current hints.
     */
    public List<String> getHintText(){
        return this.hintText;
    }

    /**
     * Get the limit of incoming arrows
     * @return int representing the limit of incoming arrows.
     */
    public int getArrowInLimit() {
        return arrowInLimit;
    }

    /**
     * Set the limit of incoming arrows
     * @param arrowInLimit int representing how many arrows should be allowed pointing to this block
     */
    public void setArrowInLimit(int arrowInLimit) {
        this.arrowInLimit = arrowInLimit;
    }

    /**
     * Get the limit of outgoing arrows
     * @return int representing the limit of outgoing arrows.
     */
    public int getArrowOutLimit() {
        return arrowOutLimit;
    }

    /**
     * Set the limit of outgoing arrows
     * @param arrowOutLimit int representing how many arrows should be allowed pointing from this block
     */
    public void setArrowOutLimit(int arrowOutLimit) {
        this.arrowOutLimit = arrowOutLimit;
    }

    /**
     * Get the number of incoming arrows
     * @return int representing the number of arrows pointing to this block
     */
    public int getArrowInCount() {
        return arrowInCount;
    }

    /**
     * Set the number of arrows coming into this block.
     * @param arrowInCount int representing the number of arrows coming into this block
     */
    public void setArrowInCount(int arrowInCount) {
        this.arrowInCount = arrowInCount;
    }

    /**
     * Get the number of outgoing arrows
     * @return int representing the number of arrows pointing from this block
     */
    public int getArrowOutCount() {
        return arrowOutCount;
    }

    /**
     * Set the number of arrows coming from this block.
     * @param arrowOutCount int representing the number of arrows coming from this block
     */
    public void setArrowOutCount(int arrowOutCount) {
        this.arrowOutCount = arrowOutCount;
    }

    /**
     * Add an arrow to the list of arrows that point to this block.
     * @param a arrow object with the destination block being this block.
     */
    public void addInArrow(Arrow a){
        this.inArrows.add(a);
    }

    /**
     * Add an arrow to the list of arrows that point away from this block.
     * @param a arrow object with the source block being this block.
     */
    public void addOutArrow(Arrow a){
        this.outArrows.add(a);
    }

    /**
     * Remove an arrow from the list of arrows that point to this block.
     * @param a arrow object with the destination block being this block.
     */
    public void removeInArrow(Arrow a){
        this.inArrows.remove(a);
        this.arrowInCount-=1;
    }

    /**
     * Remove an arrow to the list of arrows that point away from this block.
     * @param a arrow object with the source block being this block.
     */
    public void removeOutArrow(Arrow a){
        this.outArrows.remove(a);
        this.arrowOutLimit -=1;
    }

    /**
     * Get the list of arrows that point from this block.
     * @return list of arrows that point from this block.
     */
    public List<Arrow> getOutArrows(){
        return this.outArrows;

    }

    /**
     * Get the list of arrows that point to this block.
     * @return list of arrows that point to this block.
     */
    public List<Arrow> getInArrows(){
        return this.inArrows;
    }

    /**
     * Checks that another object is equal to this block based on the class, arrowInLimit, arrowOutLimit, arrowInCount,
     * color, and blockText.
     * @param o object to be checked against the object this method is called on for equality.
     * @return true if the parameter object is not null, is the same class type, and has the same arrowInLimit,
     * arrowOutLimit, arrowInCount, color, and blockText.
     */
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