package nl.qsd.mancala.game;

/**
 * BoardControl interface.
 */
public interface IBoardControl {

    /**
     * Performs a move with a player and applies gamelogic.
     *
     * @param pocket, the player selection of his possible pocket
     * @param player, the player making the move
     * @return MoveResult, result of move with enum (next players turn, same players
     *         turn, game is ended and the current board.
     */
    MoveResult performMove(int pocket, PlayerEnum player);

    /**
     * Starts a new game;
     */
    void startGame();

    /**
     * Ends the game;
     *
     * @return final board
     */
    int[] endGame();

    /**
     * Gets the score for PlayerA, use it when game is ended
     *
     * @return int, the score
     */
    int getPlayerAScore();

    /**
     * Gets the score for PlayerB, use it when game is ended
     *
     * @return int, the score
     */
    int getPlayerBScore();

}
