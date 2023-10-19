package nl.qsd.mancala.game.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.qsd.mancala.game.PlayerEnum;

/**
 * The test for the BoardModel.
 */
class BoardModelTest {

    private BoardModel model = null;

    @BeforeEach
    void createBoard() {
        model = new BoardModel();
    }

    // validate the initial values
    @Test
    void setup() {
        int[] expectedStartBoard = { 0, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4 };
        assertArrayEquals(expectedStartBoard, model.getBoard());
    }

    @Test
    void validateSimpleMove() {
        int moveGemsFrom = 1;
        int expectedPostion = moveGemsFrom + model.getBoard()[1];
        int[] expectedResult = { 0, 0, 5, 5, 5, 5, 4, 0, 4, 4, 4, 4, 4, 4 };
        int finalPocket = model.move(moveGemsFrom, PlayerEnum.A);
        
        assertArrayEquals(expectedResult, model.getBoard());
        assertEquals(expectedPostion, finalPocket);
    }

    @Test
    void validateMoveFromZeroPocket() {
        model.move(1, PlayerEnum.A);
        int moveGemsFrom = 1;
        int expectedPocket = moveGemsFrom + model.getBoard()[1];
        int[] expectedResult = { 0, 0, 5, 5, 5, 5, 4, 0, 4, 4, 4, 4, 4, 4 };
        int finalPocket = model.move(moveGemsFrom, PlayerEnum.A);

        assertArrayEquals(expectedResult, model.getBoard());
        assertEquals(expectedPocket, finalPocket);
    }

    @Test
    void validateMoveOverOwnMacala() {
        model.move(1, PlayerEnum.A);
        int moveGemsFrom = 6;
        int expectedPocket = moveGemsFrom + model.getBoard()[6];
        int[] expectedResult = { 0, 0, 5, 5, 5, 5, 0, 1, 5, 5, 5, 4, 4, 4 };
        int finalPocket = model.move(moveGemsFrom, PlayerEnum.A);
        
        assertArrayEquals(expectedResult, model.getBoard());
        assertEquals(expectedPocket, finalPocket);
    }

    @Test
    void validateMoveOverOtherPlayersMacala() {
        int[] initialPostion = { 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0 };
        model.setUpBoard(initialPostion);

        int moveGemsFrom = 6;
        int expectedPocket = 1;
        int[] expectedResult = { 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 };
        int finalPocket = model.move(moveGemsFrom, PlayerEnum.A);
        
        assertArrayEquals(expectedResult, model.getBoard());
        assertEquals(expectedPocket, finalPocket);
    }

    @Test
    void validateMoveAllGemsFromOnePocket() {
        int[] crazyPostion = { 0, 48, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        model.setUpBoard(crazyPostion);
        
        int finalPocket = model.move(1, PlayerEnum.A);       

        int[] expectedResult = { 0, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3 };
        int expectedPocket = 10;
        assertArrayEquals(expectedResult, model.getBoard());
        assertEquals(expectedPocket, finalPocket);
    }

    @Test()
    void validatePocketOutsideBoardThrowsException() {
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            model.move(15, PlayerEnum.A);
        });
        
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            model.move(-1, PlayerEnum.A);
        });
    }

    @Test()
    void validateMoveFromMancalaPocketThrowsException() {
        final int mancalaPocketPlayerA = PlayerEnum.A.getMancalaPocket();
        Assertions.assertThrows(IllegalStateException.class, () -> model.move(mancalaPocketPlayerA, PlayerEnum.A));

        final int mancalaPocketPlayerB = PlayerEnum.B.getMancalaPocket();
        Assertions.assertThrows(IllegalStateException.class, () -> model.move(mancalaPocketPlayerB, PlayerEnum.B));
    }
    
    @Test()
    void validateResetBoard() {
        int[] ongoingPosition = { 0, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 3, 3 };
        model.setUpBoard(ongoingPosition);
        int[] expectedStartBoard = { 0, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4 };
        model.resetBoard();
        assertArrayEquals(expectedStartBoard, model.getBoard());    
    }
}
