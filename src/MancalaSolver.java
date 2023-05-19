

public class MancalaSolver {
    public static void main(String[] args) {
        //tells you the most optimal move in a mancala avalanche game
        MancalaBoard mb = inputBoard();
    }

    public static MancalaBoard inputBoard() {
        MancalaBoard ret = new MancalaBoard(true, false);
        ret.inputBoard();
        return ret;
    }
}
