/**
 * The abstract Character class inherits from the abstract GameObject class.
 * Every Character object holds a x and y coordinate, an alive boolean value, a maximum number of health, a number of
 * health points, a damage points variable as well as an appearance.
 */
public abstract class Character extends GameObject {

    private int x;
    private int y;
    private boolean alive;
    private double maxHp;
    private double hp;
    private double dmg;

    /**
     * Constructor for the class Character.
     * @param appearance: Holds the appearance of the character in a char value.
     * @param x: Holds the x coordinate of the character.
     * @param y: Holds the y coordinate of the character.
     * @param maxHp: Holds the maximum health a character can have.
     * @param hp: Holds the health the character has at a moment in game.
     * @param dmg: Holds the damage points the character has at a moment in game.
     */
    public Character(char appearance, int x, int y, double maxHp, double hp, double dmg) {

        super(appearance);
        this.x = x;
        this.y = y;
        this.maxHp = maxHp;
        this.hp = hp;
        this.dmg= dmg;
        this.alive = true;
    }

    /**
     * Gives all the valid coodinates the character may interact with.
     * @return: A string array representing all the valid coordinates in a (x:y) format.
     */
    public String[] surroundings() {

        String[] validSurroundings = new String[9]; // Character's surroundings never exceed 9 coodinates
        int index = 0;

        for (int i = getX() - 1; i <= getX() + 1; i++) {
            for (int j = getY() - 1; j <= getY() + 1; j++) {
                // Checks coordinates only in game board
                if (i < LevelGenerator.LARGEUR && i > -1 && j < LevelGenerator.HAUTEUR && j > -1)
                    validSurroundings[index++] = i + ":" + j;
            }
        }

        return validSurroundings;
    }

    // GETTERS & SETTERS
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getDmg() {
        return dmg;
    }

    public void setDmg(double dmg) {
        this.dmg = dmg;
    }

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