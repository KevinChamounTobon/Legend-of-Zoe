/**
 * The abstract GameObject class is the mother class of all objects in the game. It holds the appearance of every object
 * in the game.
 */

public abstract class GameObject {

    private char appearance;

    /**
     * Constructor for the class GameObject.
     * @param apperance: Holds a char value of the appearance of a game object.
     */
    public GameObject(char appearance) {
        this.appearance = appearance;
    }

    // GETTERS & SETTERS
    public char getAppearance() {
        return appearance;
    }

    public void setAppearance(char appearance) {
        this.appearance = appearance;
    }

}