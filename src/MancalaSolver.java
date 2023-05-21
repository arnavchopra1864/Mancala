import java.util.Arrays;

public class MancalaSolver {
    public static void main(String[] args) {
        //tells you the most optimal move in a mancala avalanche game
        MancalaBoard mb = new MancalaBoard(true, false);
        //mb.inputBoard();
        mb.testInput1();
        //current implementation only works for player 1
        int[] possibleOutcomes = new int[6]; //stores the highest
        solveMancala(mb.currPlayer(), possibleOutcomes, mb);
        int high = 0;
        int highIndex = 0;
        for (int i = 0; i < possibleOutcomes.length; i++) {
            if(high < possibleOutcomes[i]) {
                high = possibleOutcomes[i];
                highIndex = i;
            }
        }
        System.out.println("Best possible move is " + highIndex + ", with a score of " + high);
    }

    public static void solveMancala(int player, int[] outs, MancalaBoard b)
    {
        //loop through six possible moves
        for (int i = 1; i < 6; i++) {
            MancalaBoard temp = b;
            if (temp.isIndexValid(i)) {
                temp.playTurn(i);
            }
            //if the chosen move allows for another turn
            if (temp.currPlayer() == player) {
                solveMancala(player, outs, temp);
            } else {
                outs[i] = player == 1 ? temp.getPlayerOneScore() : temp.getPlayerTwoScore();
            }
        }
    }
}
