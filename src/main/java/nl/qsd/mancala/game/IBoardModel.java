package nl.qsd.mancala.game;

/**
 * BoardModel interface.
 */
public interface IBoardModel {

    static final int BOARD_SIZE = 14;
    static final int GEMS_AT_START = 4;

    /**
     * Selected a pocket on the board to move the gems.
     *
     * @param pocket  that is selected
     * @param player, the player making the move
     * @return last pocket reached
     */
    int move(int pocket, PlayerEnum player);

    /**
     * Retrieve the amount of gems in a pocket.
     *
     * @param pocket, the pocket to get the gems of
     * @return int, amount of gems
     */
    int getGemsInPocket(int pocket);

    /**
     * Adds gems to a pocket
     *
     * @param pocket, the pocket to add gems too
     * @param value,  the amount of gems to add
     * @return int, the amount removed
     */
    int addToPocket(int pocket, int value);

    /**
     * Remove gems from a pocket
     *
     * @param pocket, the pocket to remove gems from
     * @param value,  the amount of gems to remove
     * @return int, the amount added
     */
    int removeFromPocket(int pocket, int value);

    /**
     * Retrieve a copy of the board
     *
     * @return int[], the copy of the board.
     */
    int[] getBoard();

    /**
     * Resets the board to the initial position
     */
    void resetBoard();

}
