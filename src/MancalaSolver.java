import java.util.ArrayList;
import java.util.Arrays;

//Doesn't work, instead of debugging I restarted from scratch. Use v2
public class MancalaSolver {
    /*public static void main(String[] args) {
        //tells you the most optimal move in a mancala avalanche game
        MancalaBoard mb = new MancalaBoard(true, false);
        //mb.inputBoard();
        //mb.testInput1();
        System.out.println(mb);

        //current implementation only works for player 1
        int[] possibleOutcomes = new int[7]; //stores the highest score on each move
        ArrayList<Integer> mov = new ArrayList<>();
        solveMancala(mb.currPlayer(), possibleOutcomes, mov, mb);

        int[] out = new int[6];
        int high = getOutput(out, possibleOutcomes);

        System.out.println("Best possible move order is " + mov + ", with a score of " + high);
        System.out.println(Arrays.toString(out));
    }

    public static void solveMancala(int player, int[] outs,
                                    ArrayList<Integer> moves, MancalaBoard b) {
        //loop through six possible moves
        for (int i = 1; i <= 6; i++) {
            MancalaBoard temp = b.copy();
            if (temp.isIndexValid(i)) {
                temp.playTurn(i);
                //if the chosen move allows for another turn, recurse
                moves.add(i);
                if (temp.currPlayer() == player) {
                    solveMancala(player, outs, moves, temp);
                } else {
                    outs[i] = player == 1 ? temp.getPlayerScore(1) : temp.getPlayerScore(2);
                }
                moves.remove(moves.size() - 1);
            }
        }
    }

    private static int getOutput(int[] out, int[] possibleOutcomes) {
        int high = 0;
        for (int i = 0; i < possibleOutcomes.length; i++) {
            if (high < possibleOutcomes[i]) {
                high = possibleOutcomes[i];
            }
            if (i != 0) {
                out[i - 1] = possibleOutcomes[i];
            }
        }
        return high;
    }*/
}
