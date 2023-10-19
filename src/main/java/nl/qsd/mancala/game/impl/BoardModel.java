package nl.qsd.mancala.game.impl;

import java.util.Arrays;

import nl.qsd.mancala.game.IBoardModel;
import nl.qsd.mancala.game.PlayerEnum;

/**
 * The BoardModel of the game. The model is only aware of the Mancala board and
 * logic of moving. The player is needed to know to skip the others player
 * Mancala (note this could be determined also by the selected pocket, but i
 * felt it was more clear this way).
 */
public class BoardModel implements IBoardModel {

    private int[] initialBoard = { 0, GEMS_AT_START, GEMS_AT_START, GEMS_AT_START, GEMS_AT_START, GEMS_AT_START,
            GEMS_AT_START, 0, GEMS_AT_START, GEMS_AT_START, GEMS_AT_START, GEMS_AT_START, GEMS_AT_START,
            GEMS_AT_START };
    private int[] board = Arrays.copyOf(initialBoard, BOARD_SIZE);

    /**
     * Selected a pocket on the board to move the gems.
     * 
     * @param pocket  that is selected
     * @param player, the player making the move
     * @return last pocket reached
     */
    @Override
    public int move(int pocket, final PlayerEnum player) {
        if (pocket < 0 || pocket > 13) {
            throw new ArrayIndexOutOfBoundsException("Pocket is outside board limitation");
        }

        if (pocket == PlayerEnum.A.getMancalaPocket() || pocket == PlayerEnum.B.getMancalaPocket()) {
            throw new IllegalStateException("Can not move from the Mancala postions");
        }

        if (Arrays.asList(Integer.class, player.getOppositPlayerPockets()).contains(pocket)) {
            throw new IllegalStateException("Can not move from pockets of other player");
        }

        return moveStones(pocket, player);
    }

    @Override
    public int getGemsInPocket(int pocket) {
        return board[pocket];
    }

    @Override
    public int addToPocket(int pocket, int value) {
        this.board[pocket] = this.board[pocket] + value;
        return value;
    }

    @Override
    public int removeFromPocket(int pocket, int value) {
        this.board[pocket] = this.board[pocket] - value;
        return value;
    }

    @Override
    public int[] getBoard() {
        return Arrays.copyOf(board, BOARD_SIZE);
    }

    @Override
    public void resetBoard() {
        board = Arrays.copyOf(initialBoard, BOARD_SIZE);
    }

    private int moveStones(int pocket, PlayerEnum player) {
        int gemsInpocket = board[pocket];
        int currentPocket = pocket;

        int i = 1;
        while (gemsInpocket != 0) {

            currentPocket = realPocket(pocket + i);
            i++;

            // skip the others player Mancala
            if (currentPocket == player.getOppositPlayerMancalaPocket()) {
                continue;
            }

            // remove gem from starting pocket
            board[pocket] = board[pocket] - 1;

            // add gem to current pocket
            board[currentPocket] = board[currentPocket] + 1;

            gemsInpocket--;
        }
        return realPocket(currentPocket);
    }

    // recursive
    private int realPocket(int arrayPosition) {
        if (arrayPosition < BOARD_SIZE)
            return arrayPosition;
        return realPocket(arrayPosition - BOARD_SIZE);
    }

    // used for some specific testing
    protected void setUpBoard(int[] board) {
        this.board = board;
    }
}
