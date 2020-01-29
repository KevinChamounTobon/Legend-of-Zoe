/**
 * The TreasureChest class inherits from the abstract GameObject class. Every TreasureChest object holds an x and a y
 * coordinate as well as an appearance.
 */
public class TreasureChest extends GameObject {

    private boolean looted;
    private String item;

    /**
     * Constructor for the class TreasureChest.
     * @param item: Holds a string value representing the item the treasure chest bears.
     */
    public TreasureChest(String item) {

        super('$');
        this.item = item;
        this.looted = false;
    }

    /**
     * Returns the string representation of the object.
     */
    @Override
    public String toString() {
        return "" + this.getAppearance();
    }

    // GETTERS & SETTERS
    public boolean isLooted() {
        return looted;
    }

    public void setLooted(boolean looted) {

        // Change treasure chest's appearance when it get's looted and set it to empty
        if (looted) {
            setAppearance('_');
            setItem("empty");
        }

        this.looted = looted;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {

        this.item = item;
    }

}