package nl.qsd.mancala.game;

/**
 * PlayerEnum with methods for easy access of some needed data.
 */
public enum PlayerEnum {
    A(7, 0), B(0, 7);

    private final int[] pocketsPlayerB = { 8, 9, 10, 11, 12, 13 };
    private final int[] pocketsPlayerA = { 1, 2, 3, 4, 5, 6 };

    private final int mancalaPocket;
    private final int oppositPlayerMancalaPocket;

    PlayerEnum(int mancalaPocket, int oppositPlayerMancalaPocket) {
        this.mancalaPocket = mancalaPocket;
        this.oppositPlayerMancalaPocket = oppositPlayerMancalaPocket;
    }

    /**
     * Get the Mancala Pocket for the player
     *
     * @return int mancalaPocket
     */
    public int getMancalaPocket() {
        return mancalaPocket;
    }

    /**
     * Get the Mancala Pocket for the opposit player
     *
     * @return int mancalaPocket
     */
    public int getOppositPlayerMancalaPocket() {
        return oppositPlayerMancalaPocket;
    }

    /**
     * Get the playable pockets for the player
     *
     * @return int[], playable pockets
     */
    public int[] getPockets() {
        int[] result;
        if (this.name().equals("A")) {
            result = pocketsPlayerA;
        } else {
            result = pocketsPlayerB;
        }
        return result;
    }

    /**
     * Get the opposit player
     *
     * @return PlayerEnum, opposit player
     */
    public PlayerEnum getOppositPlayer() {
        PlayerEnum oppositePlayer;
        if (this.toString().equals("A")) {
            oppositePlayer = PlayerEnum.B;
        } else {
            oppositePlayer = PlayerEnum.A;
        }
        return oppositePlayer;
    }

    /**
     * Get the opposit player pockets
     *
     * @return int[], playable pockets of the opposit player
     */
    public int[] getOppositPlayerPockets() {
        int[] result;
        if (this.toString().equals("A")) {
            result = pocketsPlayerB;
        } else {
            result = pocketsPlayerA;
        }
        return result;
    }
}
