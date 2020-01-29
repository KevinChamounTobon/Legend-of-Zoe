/**
 * The Enemy class inherits from the abstract Character class. Every Enemy object holds an item he drops after his
 * death, has a name, a looted boolean value, a static enemy tag value which helps name the enemies at their creation,
 * an x and a y coordinate, an alive boolean value, a maximum number of health, a number of health points, a damage
 * points variable as well as an appearance.
 */

public class Enemy extends Character {

    private String item;
    private String name;
    private boolean looted;
    private static int enemyTag = 1;

    /**
     * Constructor for the class Enemy.
     * @param item: Holds a string value representing the item the enemy bears.
     * @param x: Holds the x coordinate of the enemy.
     * @param y: Holds the y coordinate of the enemy.
     */
    public Enemy(String item, int x, int y) {

        // Hp and dmg are set and scaled depending on level
        super('@', x, y, 0, (int) Math.max(0.6 * Level.getLevel(), 1), (int) Math.max(0.4 * Level.getLevel(), 1));
        this.item = item;
        this.setLooted(false); // Sets looted to false
        this.name = "Monstre " + enemyTag++; // Names every new enemy
    }

    /**
     * Performs the enemy movement based on the player's coordinates.
     * @param lvl: Holds the level in which the enemy is about to move in.
     */
    public void move(Level lvl) {

        Player player = lvl.getPlayer();
        GameObject[] overlappingObjs = Level.getOverlappingObjs();
        GameObject[][] gameBoard = lvl.getGameBoard();

        if (isAlive()) {
            boolean attacked = attackSurroundings(lvl);

            if (Player.isPlayed() && !attacked) { // Enemy moves towards player if he hasn't attacked
                int oldX = getX();
                int oldY = getY();
                int x = getX();
                int y = getY();

                if (player.getX() == getX()) { // On same X coordinate as player
                    y = (player.getY() <= getY()? getY() - 1: getY() + 1);
                }else if (player.getY() == getY()) { // On same Y coordinate as player
                    x = (player.getX() <= getX()? getX() - 1: getX() + 1);
                }else { // Moves in diagonal to get closer to player
                    x = (player.getX() <= getX()? getX() - 1: getX() + 1);
                    y = (player.getY() <= getY()? getY() - 1: getY() + 1);
                }
                renderPriority(lvl, x, y, oldX, oldY); // Performs render priorities
            }
        } else {
            for (int i = 0; i < overlappingObjs.length; i++) {
                // If enemy dies on a exit render exit not dead enemy
                if (overlappingObjs[i] instanceof Exit && ((Exit) overlappingObjs[i]).getX() == getX() &&
                        ((Exit) overlappingObjs[i]).getY() == getY()) {
                    gameBoard[getY()][getX()] = overlappingObjs[i];
                }
            }
        }
    }

    /**
     * Enables the enemy to attack every instance of player.
     * @param lvl: Holds the level in which the enemy is about to attack in.
     */
    public void attack(Level lvl) {

        String[] surroundings = surroundings();
        GameObject[][] gameBoard = lvl.getGameBoard();
        Player player = lvl.getPlayer();

        for (int i = 0; i < surroundings.length; i++) {
            if (surroundings[i] != null) { // Check surrounding coordinates
                int x = Integer.parseInt(surroundings[i].split(":")[0]);
                int y = Integer.parseInt(surroundings[i].split(":")[1]);

                if (gameBoard[y][x] instanceof Player) { // Attack if it's a player
                    player.setHp(player.getHp() - this.getDmg());
                    System.out.println(getName() + " attaque Zoe!");

                    if (player.getHp() <= 0) { // Player dies
                        player.setAlive(false);
                        System.out.println("Zoe meurt!");
                    }
                }
            }
        }
    }

    /**
     * For every player in the enemy's surroundings, the enemy will attack.
     * @param lvl: Holds the level in which the enemy is about to attack in.
     * @return: A boolean value true if the enemy atttacked.
     */
    private boolean attackSurroundings(Level lvl) {

        String[] surroundings = surroundings();
        GameObject[][] gameBoard = lvl.getGameBoard();
        boolean attacked = false;

        for (int i = 0; i < surroundings.length; i++) {
            if (surroundings[i] != null) { // Check surrounding coordinates
                int x = Integer.parseInt(surroundings[i].split(":")[0]);
                int y = Integer.parseInt(surroundings[i].split(":")[1]);

                if (gameBoard[y][x] instanceof Player) { // If it's a player then attack it
                    attack(lvl);
                    attacked = true;
                }
            }
        }

        return attacked; // Remains false if enemy didn't attack
    }

    /**
     * Performs many instances checks in order to render the right object.
     * @param lvl: Holds the level in which the enemy is about to be rendered in.
     * @param x: Holds the new x coordinate of the enemy.
     * @param y: Holds the new y coordinate of the enemy.
     * @param oldX: Holds the old x coordinate of the enemy.
     * @param oldY: Holds the old y coordinate of the enemy.
     */
    public void renderPriority(Level lvl, int x, int y, int oldX, int oldY) {

        GameObject[][] gameBoard = lvl.getGameBoard();
        GameObject[] overlappingObjs = Level.getOverlappingObjs();
        boolean changed = false;

        // Checks coordinates only in game board that are not walls or treasurechests
        if (x < LevelGenerator.LARGEUR && x > -1 && y < LevelGenerator.HAUTEUR && y > -1
                && !(gameBoard[y][x] instanceof Walls) && !(gameBoard[y][x] instanceof TreasureChest)) {
            for (int i = 0; i < overlappingObjs.length; i++) {
                GameObject object = overlappingObjs[i]; // For every object
                
                // If it's an instance of enemy, the enemy is standing on then render enemy
                if (object instanceof Enemy && ((Enemy) object).getX() == oldX && ((Enemy) object).getY() == oldY) {
                    // If it's standing on a dead enemy render the one that's alive
                    if (object.getAppearance() == 'x') {
                        gameBoard[oldY][oldX] = object;
                    } else {
                        gameBoard[getY()][getX()] = null;
                    }
                    setX(x);
                    setY(y);
                    gameBoard[getY()][getX()] = this;
                    changed = true;
                // If it's an instance of exit the enemy is standing on then render enemy
                } else if (object instanceof Exit && ((Exit) object).getX() == oldX && ((Exit) object).getY() == oldY) {
                    gameBoard[oldY][oldX] = object;
                    setX(x);
                    setY(y);
                    gameBoard[getY()][getX()] = this;
                    changed = true;
                }
            }
            // Nothing got changed then change old coordinates to null and render enemy on new coordinates
            if (!changed) {
                gameBoard[oldY][oldX] = null;
                setX(x);
                setY(y);
                gameBoard[getY()][getX()] = this;
            }
        }
    }

    /**
     * Returns the string representation of the object.
     */
    @Override
    public String toString() {
        return "" + this.getAppearance();
    }

    // GETTERS & SETTERS
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getEnemyTag() {
        return enemyTag;
    }

    public static void setEnemyTag(int enemyTag) {
        Enemy.enemyTag = enemyTag;
    }

    public boolean isLooted() {
        return looted;
    }

    public void setLooted(boolean looted) {

        if (looted)
            setItem("empty");

        this.looted = looted;
    }

}