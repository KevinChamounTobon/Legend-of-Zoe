import java.util.Scanner;

/**
 * Affiche des messages pré-déterminés
 *
 * ATTENTION : VOUS NE DEVEZ PAS MODIFIER CETTE CLASSE
 */
public class Messages {

    public static void afficherIntro() {

        System.out.println("                             |||");
        System.out.println("                             |||");
        System.out.println("                             |||");
        System.out.println("                            =====");
        System.out.println("      _   _              //=======\\\\");
        System.out.println("     | |_| |__   ___    /// |   | \\\\\\");
        System.out.println("     | __| '_ \\ / _ \\  ///  |   |  \\\\\\");
        System.out.println("     | |_| | | |  ___ ///   |   |   \\\\\\            _");
        System.out.println("      \\__|_| |_|\\__| |    __|  _| _  ___ _ __   __| |");
        System.out.println("                   | |   / _ \\/ _` |/ _ \\ '_ \\ / _` |");
        System.out.println("                   | |__|  __/ (_| |  __/ | | | (_| |");
        System.out.println("                   |_____\\___|\\__, |\\___|_| |_|\\__,_|");
        System.out.println("                            | |___/");
        System.out.println("                           _|   |");
        System.out.println("                     ___  / _|  |   ________  _____");
        System.out.println("                    / _ \\| |_   |  |__  / _ \\| ____|");
        System.out.println("                   | (_) |  _|  |    / / | | |  _|");
        System.out.println("                    \\___/|_||   |   / /| |_| | |___");
        System.out.println("                            |   |  /____\\___/|_____|");
        System.out.println("                            \\   /");
        System.out.println("                        Ocarina of Time.sleep(1000);");
        System.out.println("                              V");
        System.out.println("Appuyez sur Enter pour commencer");
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

    public static void afficherVictoire() {

        System.out.println("          Félicitations! Vous avez trouvé les six pièces");
        System.out.println("          qui complètent l'Hexaforce, le monde est sauvé!");
        System.out.println("                           Δ");
        System.out.println("                          Δ Δ");
        System.out.println("                         Δ Δ Δ");
        System.out.println("                          \\o/");
        System.out.println("                           |");
        System.out.println("                          / \\");
    }

    public static void afficherDefaite() {

        System.out.println("          Nooooon! Zoé est morte avant d'avoir collecté les");
        System.out.println("          six pièces de l'Hexaforce...");
        System.out.println("              Meilleure chance la prochaine fois!");
        System.out.println("                           o ");
        System.out.println("                          /|\\");
        System.out.println("                          / \\");
    }
   
}