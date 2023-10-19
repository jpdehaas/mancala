package nl.qsd.mancala.game;

/**
 * Value class for the result of a move by a player. The state of which player
 * had last moved is required to be stored in the client side.
 */
public final class MoveResult {
    private final MoveResultEnum result;
    private final int[] currentBoard;

    private MoveResult(final MoveResultEnum result, final int[] currentBoard) {
        this.result = result;
        this.currentBoard = currentBoard;
    }

    public static MoveResult valueOfMoveResult(final MoveResultEnum result, final int[] currentBoard) {
        return new MoveResult(result, currentBoard);
    }

    /**
     * Get Result
     *
     * @return MoveResultEnum, contains the result of the move needed for the player
     *         moves
     */
    public final MoveResultEnum getResult() {
        return result;
    }

    /**
     * Get the result of the board after the move
     *
     * @return int[] board after the move was done
     */
    public final int[] getCurrentBoard() {
        return currentBoard;
    }
}
