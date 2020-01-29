/**
 * The Player class inherits from the abstract Character class. Every Player object has a maximum number of Hexaforces
 * he can have, a number representing the number of pieces of Hexaforces he has at this moment, a boolean played value
 * which becomes true when the player's turn is done an x and a y coordinate, an alive boolean value, a maximum number
 * of health, a number of health points, a damage points variable as well as an appearance.
 */

import java.util.Scanner;

public class Player extends Character {

    private int maxNbOfHexaforces;
    private int nbOfHexaforces;
    private static boolean played = false;

    /**
     * Constructor for the class Player.
     * @param x: Holds the x coordinate of the player.
     * @param y: Holds the y coordinate of the player.
     */
    public Player(int x, int y) {

        super('&', x, y, 5, 5, 1); // Starts with 5hp and remains 1dmg throughout the game
        this.maxNbOfHexaforces = 6;
        this.nbOfHexaforces = 0;
    }

    /**
     * Enables the player movement and performs the render priority.
     * @param x: Holds the x coordinate of the player.
     * @param y: Holds the y coordinate of the player.
     * @param lvl: Holds the level in which the player is about to move in.
     */
    public void move(int x, int y, Level lvl) {

        GameObject[][] gameBoard = lvl.getGameBoard();
        GameObject[] overlappingObjs = lvl.getOverlappingObjs();
        boolean changed = false;

        // Checks coordinates only in game board that are not walls, treasurechests or exit and player has to have
        // Hexaforce for this specific level
        if (x < LevelGenerator.LARGEUR && x > -1 && y < LevelGenerator.HAUTEUR && y > -1
                && !(gameBoard[y][x] instanceof Walls) && !(gameBoard[y][x] instanceof TreasureChest)
                && (getNbOfHexaforces() == lvl.getLevel()? true: !(gameBoard[y][x] instanceof Exit))) {
            for (int i = 0; i < overlappingObjs.length; i++) {
                GameObject object = overlappingObjs[i]; // For every object
                // If it's an instance of enemy, the player is standing on then render player
                if (object instanceof Enemy && ((Enemy) object).getX() == getX() &&
                        ((Enemy) object).getY() == getY()) {
                    gameBoard[getY()][getX()] = object;
                    setX(x);
                    setY(y);
                    gameBoard[y][x] = this;
                    changed = true;
                    // If it's an instance of exit, the player is standing on then render player
                } else if (object instanceof Exit && ((Exit) object).getX() == getX() &&
                        ((Exit) object).getY() == getY()) {
                    gameBoard[getY()][getX()] = object;
                    setX(x);
                    setY(y);
                    gameBoard[y][x] = this;
                    changed = true;
                }
            }
            // Nothing got changed then change old coordinates to null and render player on new coordinates
            if (!changed) {
                gameBoard[getY()][getX()] = null;
                setX(x);
                setY(y);
                gameBoard[y][x] = this;
            }
        }
    }

    /**
     *  Enables the player to attack every instance of enemy in his surroundings.
     */
    public void attack() {

        String[] surroundings = surroundings();

        for (int i = 0; i < surroundings.length; i++) {
            if (surroundings[i] != null) { // Check surrounding coordinates
                int x = Integer.parseInt(surroundings[i].split(":")[0]);
                int y = Integer.parseInt(surroundings[i].split(":")[1]);

                // For every coordinate in player's surroundings check for all instances of enemy at this coordinate
                for (int j = 0; j < Level.getOverlappingObjs().length; j++) {
                    GameObject obj = Level.getOverlappingObjs()[j];

                    if (obj instanceof Enemy && ((Enemy) obj).getX() == x && ((Enemy) obj).getY() == y) {
                        Enemy enemy  = (Enemy) obj;

                        enemy.setHp(enemy.getHp() - this.getDmg()); // Player attacks enemy
                        if (enemy.isAlive()) {
                            System.out.println("Zoe attaque " + enemy.getName() + "!");
                        }
                        enemyDeath(enemy); // Checks if enemy is dead
                    }
                }
            }
        }
    }

    /**
     * Enables the player to loot the enemy and recieve the item he holds.
     * @param enemy: Holds the enemy object which is about to get looted.
     */
    public void lootItem(Enemy enemy) {

        // Depending on item enemy possess, perform the corresponding actions
        switch (enemy.getItem()) {
            case ("coeur"): if (getHp() < 5) {
                setHp(getHp() + 1);
            }
                enemy.setLooted(true);
                System.out.println("Vous trouvez un coeur!");
                break;
            case ("potionvie"): setHp(getMaxHp());
                enemy.setLooted(true);
                System.out.println("Vous trouvez une potion de vie!");
                break;
            case ("hexaforce"): setNbOfHexaforces(getNbOfHexaforces() + 1);
                enemy.setLooted(true);
                System.out.println("Vous trouvez un morceau d'Hexaforce!");
                break;
        }
    }

    /**
     * Enables the player to loot the chest and recieve the item it holds.
     * @param chest: Holds the treasure chest object which is about to get looted.
     */
    public void lootItem(TreasureChest chest) {

        // Depending on item treasure chest possess, perform the corresponding actions
        switch (chest.getItem()) {
            case ("coeur"): if (getHp() < 5) {
                setHp(getHp() + 1);
            }
                chest.setLooted(true);
                System.out.println("Vous trouvez un coeur!");
                break;
            case ("potionvie"): setHp(getMaxHp());
                chest.setLooted(true);
                System.out.println("Vous trouvez une potion de vie!");
                break;
            case ("hexaforce"): setNbOfHexaforces(getNbOfHexaforces() + 1);
                chest.setLooted(true);
                System.out.println("Vous trouvez un morceau d'Hexaforce!");
                break;
        }
    }

    /**
     * Performs the action which follows an enemy's death (alive = false and change of appearance).
     * @param enemy: Holds the enemy object that died.
     */
    public void enemyDeath(Enemy enemy) {

        // If enemy dies change it's appearance and print an attack message
        if (enemy.getHp() == 0) {
            enemy.setAlive(false);
            enemy.setAppearance('x');
            System.out.println(enemy.getName() + " meurt!");

            lootItem(enemy); // Loots enemy corpse
        }
    }

    /**
     * Takes in the player's input and performs the corresponding action. The player loses a turn if he enters a non
     * valid command.
     * @param lvl: Holds the level in which the player is about to perform an action in.
     */
    public void playerInput(Level lvl) {

        Scanner scanner = new Scanner(System.in);
        String playerInput = scanner.nextLine();

        // Goes through the string of commands inputed by player and peforms the corresponding action depending on every
        // character inputed
        for (int i = 0; i < playerInput.length(); i++) {
            switch (playerInput.charAt(i)) {
                case ('w'): move(getX(), getY() - 1, lvl);
                    takePortal(lvl);
                    setPlayed(true);
                    Level.enemyMovement(lvl);
                    break;
                case ('a'): move(getX() - 1, getY(), lvl);
                    takePortal(lvl);
                    setPlayed(true);
                    Level.enemyMovement(lvl);
                    break;
                case ('s'): move(getX(), getY() + 1, lvl);
                    takePortal(lvl);
                    setPlayed(true);
                    Level.enemyMovement(lvl);
                    break;
                case ('d'): move(getX() + 1, getY(), lvl);
                    takePortal(lvl);
                    setPlayed(true);
                    Level.enemyMovement(lvl);
                    break;
                case ('c'): digWall(lvl);
                    setPlayed(true);
                    Level.enemyMovement(lvl);
                    break;
                case ('x'): attack();
                    takePortal(lvl);
                    setPlayed(true);
                    Level.enemyMovement(lvl);
                    break;
                case ('o'): openChest(lvl);
                    takePortal(lvl);
                    setPlayed(true);
                    Level.enemyMovement(lvl);
                    break;
                case ('q'): System.exit(0);
                    break;
                default:    setPlayed(true);
                    Level.enemyMovement(lvl);
                    break;
            }
            setPlayed(false);
        }
    }

    /**
     * Performs the digging of all instances of walls in the player's surroundings.
     * @param lvl: Holds the level in which the player is about to dig walls in.
     */
    public void digWall(Level lvl) {

        GameObject[][] gameBoard = lvl.getGameBoard();
        String[] surroundings = surroundings();

        for (int i = 0; i < surroundings.length; i++) {
            if (surroundings[i] != null) {  // Check surrounding coordinates
                int x = Integer.parseInt(surroundings[i].split(":")[0]);
                int y = Integer.parseInt(surroundings[i].split(":")[1]);

                if (gameBoard[y][x] instanceof Walls) { // For every wall in surroundings set it to broken
                    Walls wall = (Walls) gameBoard[y][x];

                    wall.setBroken(true);
                    gameBoard[y][x] = null;
                }
            }
        }
    }

    /**
     * Performs the looting of all instances of treasure chests in the player's surroundings.
     * @param lvl: Holds the level in which the player is about to open a treasure chest in.
     */
    public void openChest(Level lvl) {

        GameObject[][] level = lvl.getGameBoard();
        String[] surroundings = surroundings();

        for (int i = 0; i < surroundings.length; i++) {
            if (surroundings[i] != null) { // Check surrounding coordinates
                int x = Integer.parseInt(surroundings[i].split(":")[0]);
                int y = Integer.parseInt(surroundings[i].split(":")[1]);

                if (level[y][x] instanceof TreasureChest) { // For every treasure chest in surroundings loot it
                    TreasureChest chest = (TreasureChest) level[y][x];

                    lootItem(chest);
                }
            }
        }
    }

    /**
     * Enables a player to take an exit when he acquired the Hexaforce in this respective level.
     * @param lvl: Holds the level in which the player is about take an exit in.
     */
    public void takePortal(Level lvl) {

        Player player = lvl.getPlayer();
        int playerX = player.getX();
        int playerY = player.getY();
        Exit exit = lvl.getExit();
        int exitX = exit.getX();
        int exitY = exit.getY();

        // Player is standing on the same coordinate as an exit and has Hexaforce for this level
        if (getNbOfHexaforces() == lvl.getLevel() && surrondingPortal(player, lvl)) {
            Level nextLevel = new Level();

            Level.setGameBoard(nextLevel.getGameBoard()); // Create a new level
            Level.setOverlappingObjs(nextLevel.getOverlappingObjs()); // Add new objs to overlappingObjs
            System.out.println("Vous entrez dans le niveau " + Level.getLevel());
            // Set the new exit in the new level at it's corresponding coordinate
            for (int i = 0; i < nextLevel.getOverlappingObjs().length; i++) {
                if (nextLevel.getOverlappingObjs()[i] instanceof Exit) {
                    Level.getExit().setX(((Exit) nextLevel.getOverlappingObjs()[i]).getX());
                    Level.getExit().setY(((Exit) nextLevel.getOverlappingObjs()[i]).getY());
                }
            }
            // Set the new player in the new level at it's corresponding coordinate
            for (int i = 0; i < nextLevel.getOverlappingObjs().length; i++) {
                if (nextLevel.getOverlappingObjs()[i] instanceof Player) {
                    player.setX(((Player) nextLevel.getOverlappingObjs()[i]).getX());
                    player.setY(((Player) nextLevel.getOverlappingObjs()[i]).getY());
                }
            }
            nextLevel.setPlayer(player); // Reinitialise player
        }
    }

    public boolean surrondingPortal(Player player, Level lvl) {

        String[] surroundings = surroundings();
        Exit exit = lvl.getExit();

        for (int i = 0; i < surroundings.length; i++) {
            if (surroundings[i] != null) { // Check surrounding coordinates
                int x = Integer.parseInt(surroundings[i].split(":")[0]);
                int y = Integer.parseInt(surroundings[i].split(":")[1]);

                if(x == exit.getX() && y == exit.getY()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns the string representation of the object.
     */
    @Override
    public String toString() {
        return "" + this.getAppearance();
    }

    // GETTERS & SETTERS
    public int getMaxNbOfHexaforces() {
        return maxNbOfHexaforces;
    }

    public void setMaxNbOfHexaforces(int maxNbOfHexaforces) {
        this.maxNbOfHexaforces = maxNbOfHexaforces;
    }

    public int getNbOfHexaforces() {
        return nbOfHexaforces;
    }

    public void setNbOfHexaforces(int nbOfHexaforces) {
        this.nbOfHexaforces = nbOfHexaforces;
    }

    public static boolean isPlayed() {
        return played;
    }

    public static void setPlayed(boolean played) {
        Player.played = played;
    }

}