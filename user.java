import java.util.Random;
import java.util.Scanner;

class Player {
    String name;
    int id, score = 0, level = 0;

    Player(String name, int id) {
        this.name = name;
        this.id = id;
    }

    void updateScore() { score += 10; }
    void nextLevel() { level++; }
}
// To take user inputs and generate random numbers for the game
public class user {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        int id = getInt(sc, "Enter your ID: ");
        Player player = new Player(name, id);
        int highScore = 0;

    //    Various stages of the game 
        if (!play(sc, rand, 10, 3, player)) end(player, highScore);
        else {
            player.nextLevel(); highScore = player.score; showStatus(player);
            if (!play(sc, rand, 20, 4, player)) end(player, highScore);
            else {
                player.nextLevel(); highScore = player.score; showStatus(player);
                if (play(sc, rand, 100, 6, player)) {
                    System.out.println("Congratulations! You won the game!");
                    player.updateScore(); player.nextLevel(); highScore = player.score;
                } else System.out.println("You lost in the final round.");
                showStatus(player); end(player, highScore);
            }
        }
    }
     
    // The play method handles a single level of the game where the player
    //  tries to guess a random number within a specified range.
    static boolean play(Scanner sc, Random rand, int range, int attempts, Player p) {
        int target = rand.nextInt(range + 1);
        System.out.println("\nLevel " + p.level + ": Guess between 0 and " + range);

        int trial = 1;
        while (trial <= attempts) {
            int guess = getInt(sc, "Attempt " + trial + ": ");
            if (guess < 0 || guess > range) {
                System.out.println("Out of range! Please enter a number between 0 and " + range + ".");
                continue; // do not count this as a trial
            }

            if (guess == target) {
                System.out.println("Correct! +10 points");
                p.updateScore();
                return true;
            }

            System.out.println(guess < target ? "Too low." : "Too high.");
            trial++;
        }

        System.out.println("The correct number was: " + target);
        return false;
    }

    // This method  makes sure that the user enters a valid input
    static int getInt(Scanner sc, String prompt) {
        String input;
        int num = 0;
        boolean valid;
        do {
            System.out.print(prompt);
            input = sc.nextLine();
            valid = input.matches("\\d+");
            if (valid) {
                num = 0;
                for (char c : input.toCharArray()) {
                    num = num * 10 + (c - '0');
                }
            } else {
                System.out.println("Invalid input. Enter digits only.");
            }
        } while (!valid);
        return num;
    }
    // Displayes the current status of player(name,ID,level)
    static void showStatus(Player p) {
        System.out.println("Player: " + p.name + "  ID: " + p.id);
        System.out.println("Score: " + p.score + "  Level: " + p.level);
        
    }

    static void end(Player p, int highScore) {
        System.out.println("\nGame Over!");
        System.out.println("Final Score: " + p.score);
        System.out.println("Highest Score: " + highScore);
        System.out.println("Level Reached: " + p.level);
    }
}