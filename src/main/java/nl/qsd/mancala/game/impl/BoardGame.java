package nl.qsd.mancala.game.impl;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.qsd.mancala.game.IBoardControl;
import nl.qsd.mancala.game.IBoardModel;
import nl.qsd.mancala.game.MoveResult;
import nl.qsd.mancala.game.MoveResultEnum;
import nl.qsd.mancala.game.PlayerEnum;

/**
 * Controller for the Mancala game, containing game logic.
 */
public class BoardGame implements IBoardControl {

    private final IBoardModel boardModel;
    protected static final Logger LOG = LogManager.getLogger();

    public BoardGame(final IBoardModel boardModel) {
        this.boardModel = boardModel;
    }

    @Override
    public MoveResult performMove(int pocket, PlayerEnum player) {
        LOG.info("performMove: pocket = {}, player={}", pocket, player);

        MoveResultEnum result;

        if (checkNoMovesLeft()) {
            result = MoveResultEnum.GAME_ENDED;
        } else {
            int lastPocket = boardModel.move(pocket, player);

            if (lastPocket == player.getMancalaPocket()) {
                result = MoveResultEnum.SAME_PLAYER;

                if (checkNoMovesLeft()) {
                    result = MoveResultEnum.GAME_ENDED;
                }
            } else {
                performPossibleCaptures(pocket, lastPocket, player);
                result = MoveResultEnum.NEXT_PLAYER;

                if (checkNoMovesLeft()) {
                    result = MoveResultEnum.GAME_ENDED;
                }
            }

        }
        return MoveResult.valueOfMoveResult(result, boardModel.getBoard());
    }

    @Override
    public void startGame() {
        boardModel.resetBoard();
    }

    /**
     * Ends the game, finish the gems.
     */
    @Override
    public int[] endGame() {
        finishBoard();
        return boardModel.getBoard();

    }

    private boolean checkNoMovesLeft() {
        int[] board = boardModel.getBoard();
        boolean result = false;
        if (board[1] + board[2] + board[3] + board[4] + board[5] + board[6] == 0
                || board[8] + board[9] + board[10] + board[11] + board[12] + board[13] == 0) {
            result = true;
        }
        return result;
    }

    private void finishBoard() {
        // @formatter:off
        Arrays.stream(PlayerEnum.A.getPockets()).filter(x -> boardModel.getGemsInPocket(x) != 0)
            .forEach(x -> boardModel.removeFromPocket(x, boardModel.addToPocket(PlayerEnum.A.getMancalaPocket(), boardModel.getGemsInPocket(x)))
        );

        Arrays.stream(PlayerEnum.B.getPockets()).filter(x -> boardModel.getGemsInPocket(x) != 0)
            .forEach(x -> boardModel.removeFromPocket(x, boardModel.addToPocket(PlayerEnum.B.getMancalaPocket(), boardModel.getGemsInPocket(x)))
        );
        // @formatter:on

    }

    @Override
    public int getPlayerAScore() {
        return boardModel.getGemsInPocket(PlayerEnum.A.getMancalaPocket());
    }

    @Override
    public int getPlayerBScore() {
        return boardModel.getGemsInPocket(PlayerEnum.B.getMancalaPocket());
    }

    private void performPossibleCaptures(final int originalPocket, final int lastPocket, final PlayerEnum player) {
        // no captures on other Player his pockets
        if (IntStream.of(player.getOppositPlayerPockets()).anyMatch(x -> x == lastPocket)) {
            return;
        }

        int gemsInLastPocket = boardModel.getGemsInPocket(lastPocket);

        // rule to make capture
        if (gemsInLastPocket == 1 && originalPocket != lastPocket) {
            int oppositPocket = IBoardModel.BOARD_SIZE - lastPocket;
            int gemsInOppositPocket = boardModel.getGemsInPocket(oppositPocket);

            if (gemsInOppositPocket != 0) {
                boardModel.addToPocket(player.getMancalaPocket(),
                        boardModel.removeFromPocket(oppositPocket, gemsInOppositPocket));
                LOG.info("player {} captured {} gems", player, gemsInOppositPocket);
            }
        }
    }

}
