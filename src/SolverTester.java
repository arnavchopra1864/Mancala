public class SolverTester {
    public static void main(String[] args) {
        MancalaBoard mb = new MancalaBoard(true, false);
        mb.testInput2();
        System.out.println(mb);
        int[] moves = {2, 6, 1, 6, 2, 6, 4, 6, 5};
        for (int move : moves) {
            mb.playTurn(move);
            System.out.println("Move: " + move);
            System.out.println(mb);
        }
    }
}
