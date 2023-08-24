public class SolverTester {
    public static void main(String[] args) {
        MancalaBoard mb = new MancalaBoard(true, false);
        mb.testInput2();
        System.out.println(mb);
        int[] moves = {2, 6, 1, 6, 2, 6, 4, 6, 5};
        //testMoves(mb, moves);

        moves = new int[] {6, 3, 2, 5, 1, 1, 5, 5, 6, 1, 3, 4, 6, 2, 6, 6, 5, 2, 6, 2, 6, 4, 6,
                5, 6, 1};
        testMoves(new MancalaBoard(true, false), moves);
    }

    private static void testMoves(MancalaBoard mb, int[] moves) {
        for (int move : moves) {
            mb.playTurn(move);
            System.out.println("Move: " + move);
            System.out.println(mb);
        }
    }
}
