package nl.qsd.mancala.game.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.qsd.mancala.game.MoveResult;
import nl.qsd.mancala.game.MoveResultEnum;
import nl.qsd.mancala.game.PlayerEnum;

/**
 * The test for BoardGame. I did not use a mock framework as it is too simple.
 */
class BoardGameTest {

    private BoardModel model;
    private BoardGame game;

    @BeforeEach
    void setUp() throws Exception {
        model = new BoardModel();
        game = new BoardGame(model);
    }

    @Test
    void validateCapture() {
        int[] boardWithCapture = { 10, 4, 0, 6, 1, 0, 3, 7, 2, 7, 1, 1, 3, 3 };
        model.setUpBoard(boardWithCapture);
        int[] expectedAfterCaptureButOnOthersPlayerPocket = { 10, 4, 0, 6, 0, 1, 3, 14, 2, 0, 1, 1, 3, 3 };

        game.performMove(4, PlayerEnum.A);

        assertArrayEquals(boardWithCapture, expectedAfterCaptureButOnOthersPlayerPocket);

    }

    @Test
    void validateNoCaptureAtOthersPlayersPockets() {
        int[] boardWithCapture = { 10, 1, 6, 0, 0, 8, 1, 10, 0, 0, 1, 7, 3, 1 };
        model.setUpBoard(boardWithCapture);
        int[] expectedAfterCaptureButOnOthersPlayerPocket = { 11, 2, 7, 1, 1, 8, 1, 10, 0, 0, 1, 0, 4, 2 };

        game.performMove(11, PlayerEnum.B);

        assertArrayEquals(boardWithCapture, expectedAfterCaptureButOnOthersPlayerPocket);
    }

    @Test
    void validatePlayerScores() {
        int[] board = { 6, 1, 6, 0, 0, 8, 1, 8, 0, 0, 1, 7, 3, 1 };
        model.setUpBoard(board);

        assertEquals(game.getPlayerAScore(), 8);
        assertEquals(game.getPlayerBScore(), 6);
    }

    @Test
    void validateNoMovesLeftAndEndGame() {
        int[] board = { 10, 1, 6, 0, 0, 8, 1, 8, 0, 0, 0, 0, 0, 0 };
        model.setUpBoard(board);
        MoveResult moveResult = game.performMove(2, PlayerEnum.A);

        int[] expectedEndBoard = { 10, 0, 0, 0, 0, 0, 0, 24, 0, 0, 0, 0, 0, 0 };

        assertEquals(MoveResultEnum.GAME_ENDED, moveResult.getResult());
        assertArrayEquals(board, moveResult.getCurrentBoard());
        int[] finalBoard = game.endGame();
        assertArrayEquals(expectedEndBoard, finalBoard);
    }

}
