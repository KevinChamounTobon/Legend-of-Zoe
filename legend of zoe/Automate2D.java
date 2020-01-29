/**
 * Génération de cavernes basée sur
 * https://gamedevelopment.tutsplus.com/tutorials/generate-random-cave-levels-using-cellular-automata--gamedev-9664
 *
 * ATTENTION : VOUS NE DEVEZ PAS MODIFIER CETTE CLASSE
 */
public class Automate2D {

    private boolean[][] grid;
    private final int width;
    private final int height;

    private static final int BIRTH_RATE = 4;
    private static final int DEATH_RATE = 3;

    public static boolean[][] randomGrid(int width, int height) {

        Automate2D game = new Automate2D(width, height, 0.45);

        for (int i = 0; i < 3; i++) {
            game.iteration();
        }

        return game.grid;
    }

    private Automate2D(int width, int height, double density) {

        this.width = width;
        this.height = height;

        this.grid = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Math.random() < density) {
                    grid[i][j] = true;
                }
            }
        }
    }

    private void iteration() {

        boolean[][] newGrid = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int nbNeighbours = this.getNbNeighbours(j, i);
                boolean alive = grid[i][j];

                newGrid[i][j] = (alive && nbNeighbours > DEATH_RATE) || (!alive && nbNeighbours > BIRTH_RATE);
            }
        }

        this.grid = newGrid;
    }

    private int getNbNeighbours(int x, int y) {

        int total = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xPos = x + j;
                int yPos = y + i;

                if (xPos < 0 || xPos >= width || yPos < 0 || yPos >= height) {
                    total++;
                    continue;
                }

                if (!(i == 0 && j == 0) && grid[yPos][xPos]) {
                    total++;
                }
            }
        }

        return total;
    }

}