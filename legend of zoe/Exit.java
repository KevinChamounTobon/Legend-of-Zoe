/**
 * The Exit class inherits from the abstract GameObject class. Every Exit object holds an x and a y coordinate as well
 * as an appearance.
 */

public class Exit extends GameObject {

    private int x;
    private int y;

    /**
     * Constructor for the class Exit.
     * @param x: Holds the x coordinate of the exit.
     * @param y: Holds the y coordinate of the exit.
     */
    public Exit(int x, int y) {

        super('E');
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the string representation of the object.
     */
    @Override
    public String toString() {
        return "" + this.getAppearance();
    }

    // GETTERS & SETTERS
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}