package nl.qsd.mancala;

import java.util.Arrays;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.qsd.mancala.game.IBoardControl;
import nl.qsd.mancala.game.IBoardModel;
import nl.qsd.mancala.game.MoveResult;
import nl.qsd.mancala.game.MoveResultEnum;
import nl.qsd.mancala.game.PlayerEnum;
import nl.qsd.mancala.game.impl.BoardGame;
import nl.qsd.mancala.game.impl.BoardModel;

/**
 * This is just a temporary main to test the control and model.
 */
public class MancalaMain {

    private final Random generator;
    private final IBoardControl game;
    private final IBoardModel board;
    private static final String PLAYERA_NAME = "Ivo";
    private static final String PLAYERB_NAME = "Raymond";
    protected static final Logger LOG = LogManager.getLogger();

    public MancalaMain(final IBoardControl game, final IBoardModel board) {
        generator = new Random();
        this.game = game;
        this.board = board;
    }

    public static void main(String[] args) {

        final IBoardModel model = new BoardModel();
        final IBoardControl game = new BoardGame(model);

        final MancalaMain main = new MancalaMain(game, model);

        // just a kind of integration test for now
        main.simulateGame();
    }

    // just to show one game to prove the working in this version
    public void simulateGame() {
        boolean ended = false;

        MoveResultEnum result = MoveResultEnum.SAME_PLAYER;

        while (!ended) {
            // Player A
            while (result == MoveResultEnum.SAME_PLAYER) {
                final MoveResult moveResult = game.performMove(selectMoveForPlayer(PlayerEnum.A), PlayerEnum.A);
                result = moveResult.getResult();
                if (result == MoveResultEnum.GAME_ENDED) {
                    ended = true;
                    break;
                }
                LOG.info("\n{}", viewBoard(moveResult.getCurrentBoard()));
            }

            if (ended) {
                break;
            }

            // PlayerB
            result = MoveResultEnum.SAME_PLAYER;
            while (result == MoveResultEnum.SAME_PLAYER) {
                final MoveResult moveResult = game.performMove(selectMoveForPlayer(PlayerEnum.B), PlayerEnum.B);
                result = moveResult.getResult();
                if (result == MoveResultEnum.GAME_ENDED) {
                    ended = true;
                    break;
                }
                LOG.info("\n{}", viewBoard(moveResult.getCurrentBoard()));
            }
            result = MoveResultEnum.SAME_PLAYER;
        }

        endGame();

    }

    private void endGame() {
        int[] finalBoard = game.endGame();
        LOG.info("\n{}", viewBoard(finalBoard));

        int a = game.getPlayerAScore();
        int b = game.getPlayerBScore();
        LOG.info("{} score = {}", PLAYERA_NAME, a);
        LOG.info("{} score = {}", PLAYERB_NAME, b);

        if (a > b) {
            LOG.info("{} wins", PLAYERA_NAME);
        } else if (b > a) {
            LOG.info("{} wins", PLAYERB_NAME);
        } else {
            LOG.info("Its a draw.");
        }
    }

    private int selectMoveForPlayer(final PlayerEnum player) {
        int[] possibleSelections;
        int pocket;
        possibleSelections = Arrays.stream(player.getPockets()).filter(x -> board.getGemsInPocket(x) != 0).toArray();

        if (possibleSelections.length == 0) {
            throw new ArrayIndexOutOfBoundsException("no more moves");
        }

        pocket = generator.nextInt(possibleSelections.length);

        int result = possibleSelections[pocket];
        LOG.info("choosed socket for {} is {}", player, result);
        return result;
    }

    private String viewBoard(int[] board) {
        // @formatter:off
        return "|  |" + twoSpacer(board[13]) + "|" + twoSpacer(board[12]) + "|" + twoSpacer(board[11]) + "|" + twoSpacer(board[10]) + "|" + twoSpacer(board[9]) + "|" + twoSpacer(board[8]) + "|  |\n" +
               "|" + twoSpacer(board[0]) + "|                 |" + twoSpacer(board[7]) + "|\n" +
               "|  |" + twoSpacer(board[1]) + "|" + twoSpacer(board[2]) + "|" + twoSpacer(board[3]) + "|" + twoSpacer(board[4]) + "|" + twoSpacer(board[5]) + "|" + twoSpacer(board[6]) + "|  |\n";
        // @formatter:on
    }

    private String twoSpacer(int gems) {
        if (gems < 10) {
            return " " + gems;
        }
        return "" + gems;
    }

}
