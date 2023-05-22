import java.util.Scanner;

public class MancalaRunner {
    public static void main(String[] args) {
        //tester();
        MancalaBoard mb = new MancalaBoard(true, false);
        mb.testInput2();
        /*String out = preamble();
        MancalaBoard mb = new MancalaBoard(out.charAt(0) == 'y',
                out.charAt(1) == 'y');
        System.out.println("Your Board (Avalanche: " + (out.charAt(0) == 'y') + ")");*/
        System.out.println(mb);
        play(mb);
        //game over, nothing to be handled in main
    }

    private static String preamble() {
        System.out.println("Welcome to Mancala");
        System.out.println("Players can pick between pockets 1-6");
        System.out.println("Player 1's pockets go 1-6 top to bottom, while player 2's pockets go " +
                "1-6 bottom to top");
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to play avalanche mode? y/n");
        String aval = input.nextLine().toLowerCase();
        while(!aval.equals("y") && !aval.equals("n"))
        {
            System.out.println("Please enter y or n");
            aval = input.nextLine().toLowerCase();
        }
        System.out.println("Would you like to randomize starting values? y/n");
        String rand = input.nextLine().toLowerCase();
        while(!rand.equals("y") && !rand.equals("n"))
        {
            System.out.println("Please enter y or n");
            rand = input.nextLine().toLowerCase();
        }
        return aval + rand;
    }

    private static void tester() {
        int[] moves = {1, -1, 2, -1, 3, -1, 4, -1, 5, -1, 6};
        MancalaBoard mb = new MancalaBoard(false, false);
        for (int move : moves) {
            mb.playTurn(move);
        }
    }

    private static void play(MancalaBoard mb) {
        while(!mb.isGameOver()) {
            Scanner gameInput = new Scanner(System.in);
            System.out.println("Player " + mb.currPlayer() + ", please input a number 1-6");
            int index = gameInput.nextInt();

            mb.playTurn(index);
            System.out.println(mb);
            mb.gameOver();
        }
    }
}
