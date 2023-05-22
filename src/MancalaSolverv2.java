import java.util.ArrayList;
import java.util.HashMap;

public class MancalaSolverv2 {
    public static void main(String[] args) {
        MancalaBoard mb = new MancalaBoard(true, false);
        //mb.testInput2();
        System.out.println(mb);

        //maps the path of moves (stored in an array list containing indices) to the score
        HashMap<ArrayList<Integer>, Integer> movesAndScores = new HashMap<>();
        ArrayList<Integer> paths = new ArrayList<>();
        MancalaBoard temp = mb.copy();
        solveMancala(temp, movesAndScores, paths);
        //System.out.println(movesAndScores);
        outputSolution(movesAndScores);
    }

    //TODO: current implementation only works for player 1
    public static void solveMancala(MancalaBoard mb, HashMap<ArrayList<Integer>, Integer> ms,
                                    ArrayList<Integer> currPath) {
        //meaning the turn has switched
        if (mb.currPlayer() != 1) {
            ms.put(new ArrayList<>(currPath), mb.getPlayerScore(1));
        }
        //otherwise, it is still player one's turn. Recurse to find best path
        else {
            MancalaBoard temp = mb.copy();
            for (int i = 0; i < 6; i++) {
                int move = i + 1;
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

    //TODO: account for player 2's moves
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
