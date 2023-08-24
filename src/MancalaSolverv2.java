import java.util.ArrayList;
import java.util.HashMap;

//TODO: remove tester cases, implement board inputting
public class MancalaSolverv2 {
    public static void main(String[] args) {
        MancalaBoard mb = new MancalaBoard(true, false);
        mb.inputBoard();
        System.out.println(mb);

        //maps the path of moves (stored in an array list containing indices) to the score
        HashMap<ArrayList<Integer>, Integer> movesAndScores = new HashMap<>();
        ArrayList<Integer> paths = new ArrayList<>();
        MancalaBoard temp = mb.copy();
        solveMancala(temp, movesAndScores, paths);
        outputSolution(movesAndScores);
    }

    /*Possible Optimizations
     *  - Only add move order to the hashmap if the score is higher than the current highest
     *      - Still have to go through the operations of finding the path, just saves space
     *  - If multiple moves with same highest score, calculate best move for player 2 then
     *      calculate best follow-up for player 1
     */
    public static void solveMancala(MancalaBoard mb, HashMap<ArrayList<Integer>, Integer> ms,
                                    ArrayList<Integer> currPath) {
        //meaning the turn has switched
        if (mb.currPlayer() != 1) {
            ms.put(new ArrayList<>(currPath), mb.getPlayerScore(1));
        }
        //otherwise, it is still player one's turn. Recurse to find best path
        else {
            MancalaBoard temp = mb.copy();
            for (int move = 1; move <= 6; move++) {
                //make one move, assess outcome, undo move and continue considering options
                if(temp.isIndexValid(move)) {
                    temp.playTurn(move);
                    currPath.add(move);
                    solveMancala(temp, ms, currPath);
                    currPath.remove(currPath.size() - 1);
                    temp = temp.undoLastMove();
                }
            }
        }
    }

    //TODO: account for player 2's moves, maybe?
    /*Run best move finder algorithm for both players arbitrarily deep, then return the move
     * order that maximizes the difference btw player 1's and player 2's scores
     */
    public static void outputSolution(HashMap<ArrayList<Integer>, Integer> ms) {
        int highScore = 0;
        ArrayList<Integer> bestPath = new ArrayList<>();
        for (ArrayList<Integer> path : ms.keySet()) {
            int score = ms.get(path);
            if(score > highScore) {
                bestPath = path;
                highScore = score;
            }
        }

        System.out.println("Best possible move order is " + bestPath
                + ", with a score of " + highScore);
    }
}
