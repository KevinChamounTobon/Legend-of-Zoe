/**
 * The Exit class inherits from the abstract GameObject class. Every Walls object holds an x and a y coordinate as well
 * as an appearance.
 */

public class Walls extends GameObject {

    private boolean broken;

    /**
     * Constructor for the class Walls.
     */
    public Walls() {

        super('#');
        this.broken = false;
    }

    /**
     * Returns the string representation of the object.
     */
    @Override
    public String toString() {
        return "" + this.getAppearance();
    }

    // GETTERS & SETTERS
    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {

        if (broken) { // Change wall appearance when it's broken
            setAppearance(' ');
        }

        this.broken = broken;
    }

}