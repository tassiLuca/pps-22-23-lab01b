package e1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GridTest {

    private final static int GRID_HEIGHT = 10;
    private final static int GRID_WIDTH = 15;
    private Grid grid;

    @BeforeEach
    void setup() {
        grid = new GridFactoryImpl().createWithSingleKnightAndPawn(GRID_WIDTH, GRID_HEIGHT);
    }

    @Test
    void attemptMovingOutsideBoundaries() {
        final var newPosition = new Position(15, 2);
        assertThrows(IllegalArgumentException.class, () -> grid.moveKnight(newPosition));
    }
}