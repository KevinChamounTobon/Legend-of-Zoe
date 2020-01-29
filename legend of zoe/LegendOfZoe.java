/**
 * @authors EL-HAMAOUI, Christian & TOBON, Kevin
 * March 18, 2019
 *
 * The LegendOfZoe class acts as the main method of the game and controls the flow of the game. While the player is
 * alive it will ask for input and print out the level to the console as well as checking for victory (player has 6
 * pieces of Hexaforces) of defeat (player dies).
 */

public class LegendOfZoe {

    public static void main(String[] args) {

        Level lvl = new Level(); // Creates a level for the player to play in
        Player zoe = lvl.getPlayer(); // Creates a player
        Messages.afficherIntro();

            // The game logic runs and asks for player input while player is alive
            while (zoe.isAlive()) {
                if (zoe.getNbOfHexaforces() == 6) { // Player wins
                    Messages.afficherVictoire();
                    System.exit(0);
                }
                System.out.println(lvl.renderPlayerInfo()); // Renders player's info
                System.out.println(lvl); // Renders current level
                zoe.playerInput(lvl); // Ask for player input
            }
            Messages.afficherDefaite(); // Player dies
    }

}