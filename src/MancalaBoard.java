import java.util.Arrays;
import java.util.Scanner;

public class MancalaBoard {
    //TODO: implement "capture" feature in non-avalanche mode
    //TODO: consider making the game board-size-agnostic (remove magic numbers 7, 14, etc)

    //6 pockets + 1 bank, times two players
    private final int[] board = new int[14];
    private final boolean avalanche;

    private final boolean random;

    //alternates btw 0 (player 1) and 1 (player 2) - could use bool but makes math easier
    private int turn = 0;
    private boolean gameOver = false;

    private MancalaBoard lastMove;

    /* mancala board in actuality
      [     Bank 1            ]
      [pocket1]   [pocket6(12)]
      [pocket2]   [pocket5(11)]
      [pocket3]   [pocket4(10)]
      [pocket4]   [pocket3(9)]
      [pocket5]   [pocket2(8)]
      [pocket6]   [pocket1(7)]
      [     Bank 2            ]
     */

    /* mancala board int array representation
          0       1     2  3  4  5  6    7      8       9  10 11  12  13
        bnk2, pockets 1, 2, 3, 4, 5, 6, bnk 1, pockets 7, 8, 9, 10, 11, 12
       (p2 bank)           player 1's section           player 2's section
     */

    public MancalaBoard(boolean avalancheMode, boolean random) {
        avalanche = avalancheMode;
        this.random = random;
        if (random) {
            for (int i = 0; i < 12; i++) {
                int boardIndex = i >= 6 ? i + 2 : i + 1;
                board[boardIndex] = (int) (Math.random() * 7 + 1);
            }
        } else {
            //testInput1();
            //testInput2();
            //default to 4 stones in each pocket
            Arrays.fill(board, 4);
            board[0] = board[board.length / 2] = 0;
        }
    }

    //TODO: make private
    public void testInput1() {
        board[1] = board[8] = 1;
        board[2] = board[9] = 4;
        board[3] = board[10] = 5;
        board[4] = board[11] = 1;
        board[5] = board[12] = 3;
        board[6] = board[13] = 2;
    }

    public void testInput2() {
        for (int i = 0; i < 14; i++) {
            board[i] = 0;
        }
        board[2] = board[3] = board[5] = 1;
        board[1] = 6;
        board[13] = 3;
    }

    //used to input in-progress games for solving
    public void inputBoard() {
        System.out.println("Please input player 1's pockets, 1-6, then player 2's pockets, " +
                "1-6");
        Scanner input = new Scanner(System.in);
        int count = 0;
        //while less than 12 pockets have been filled
        while (count < 12) {
            int currPlayer = count > 5 ? 2 : 1;
            System.out.println("Please input player "
                    + currPlayer + "'s pocket " + (count % 6 + 1));
            int stones = input.nextInt();
            while (stones < 0) {
                System.out.println("Please enter a positive number");
                stones = input.nextInt();
            }
            int boardIndex = count >= 6 ? count + 2 : count + 1;
            board[boardIndex] = stones;
            count++;
        }
    }

    //player 1 will be asked index 1-6, player 2 index 8-13
    public void playTurn(int index) {
        /*if (index == -1) {
            turn = turn == 0 ? 1 : 0;
            return; //debugging purposes
        }*/
        while (!isIndexValid((index))) {
            System.out.println("Please enter a valid index, current: " + index);
            Scanner scanner = new Scanner(System.in);
            index = scanner.nextInt();
        }

        lastMove = this.copy();
        index += turn == 1 ? 7 : 0; //denormalize indices, needs to be 8-13 for player 2
        int endIndex = index + board[index] % board.length;
        if (!avalanche) {
            moveStones(index);
        } else {
            boolean go = true;
            while (go) {
                //endIndex predicts where the last stone will fall. However, it can't account for
                // skipping the other player's bank. Use moveStones's return to account for this
                endIndex = (index + board[index]) % board.length;
                endIndex += moveStones(index);
                //lazy way of fixing edge case
                endIndex = endIndex == 14 && turn == 0 ? 1 : endIndex;
                go = board[endIndex] > 1 && (endIndex != 0 && endIndex != 7);
                index = endIndex;
            }
        }
        //if a player lands in their own bank, they get another turn
        if ((turn == 0 && endIndex != 7) || (turn == 1 && endIndex != 0)) {
            turn = turn == 0 ? 1 : 0;
        }
        gameOver = isEitherRowEmpty();

    }

    private boolean isEitherRowEmpty() {
        //is left row or right row empty?
        return isRowEmpty(0) || isRowEmpty(1);
    }

    //whichRow = 0 for left row, 1 for right row
    private boolean isRowEmpty(int whichRow) {
        int i = 1 + 7 * whichRow;
        int limit = i + 6;
        while (i < limit) {
            if (board[i] > 0) {
                return false;
            }
            i++;
        }
        return true;
    }

    private int moveStones(int index) {
        int ret = 0;
        //whichever pocket is picked will have n stones, need to increment the subsequent n
        // pockets by 1 (including banks)
        int numStones = board[index];
        board[index] = 0;
        for (int i = 1; i <= numStones; i++) {
            int currIndex = (index + i) % board.length;
            //skip the other player's bank
            if ((turn == 0 && currIndex == 0) || (turn == 1 && currIndex == 7)) {
                numStones++;
                ret = 1;
            } else {
                board[currIndex]++;
            }
        }
        return ret;
    }

    public boolean isIndexValid(int index) {
        //check that index in bounds AND the pocket actually has stones in it
        int actualIndex = index + (turn == 1 ? 7 : 0);
        return (index >= 1 && index <= 6) && board[actualIndex] > 0;
    }

    //I abandoned the principle of writing code independent of the board array's size here, could
    // be changed in the future
    public String toString() {
        StringBuilder ret = new StringBuilder("[      " + board[0] + "      ]\n");

        for (int i = 0; i < 6; i++) {
            //iterates "down" the left side and "up" the right at the same time
            //in actuality iterating from index 1-->6 and 13-->8
            ret.append("[  ").append(board[i + 1]).append("  ] [  ")
                    .append(board[board.length - i - 1]).append("  ]\n");
        }

        return ret.append("[      ").append(board[7]).append("      ]").toString();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int currPlayer() {
        return turn + 1;
    }

    public int getWinner() {
        return board[0] > board[7] ? 2 : board[0] < board[7] ? 1 : 0;
    }

    public void gameOver() {
        if (gameOver) {
            System.out.println("Game Over!");
            int winner = getWinner();
            String endText = winner == 0 ? "Draw!" : "Player " + winner + " wins!";
            System.out.println(endText);
        }
    }

    public int getPlayerScore(int player) {
        return board[player == 1 ? 7 : 0];
    }

    //deep copy
    public MancalaBoard copy() {
        MancalaBoard ret = new MancalaBoard(avalanche, random);
        ret.gameOver = gameOver;
        ret.turn = turn;
        System.arraycopy(board, 0, ret.board, 0, board.length);
        return ret;
    }

    public MancalaBoard undoLastMove() {
        return lastMove;
    }
}
