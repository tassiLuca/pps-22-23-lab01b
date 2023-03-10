package e1.model;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utils.Position;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GridTest {

    private final static int GRID_HEIGHT = 10;
    private final static int GRID_WIDTH = 15;
    private static final int REPETITIONS = 100;

    @Test
    void testDefaultCreation() {
        entitiesAreCorrectlyInitialized(initWithSinglePawnAndKnight(), 1);
    }

    @Test
    void testCreationMultiplePawns() {
        final int numberOfPawns = 5;
        entitiesAreCorrectlyInitialized(initWithMultiplePawnsAndKnight(numberOfPawns), numberOfPawns);
    }

    private void entitiesAreCorrectlyInitialized(final Grid grid, final int numberOfPawns) {
        assertNotNull(grid.getKnightPosition());
        assertEquals(numberOfPawns, grid.getPawnsPosition().size());
    }

    @RepeatedTest(REPETITIONS)
    void testEntitiesDoNotOverlap() {
        final int numberOfPawns = 10;
        final var grid = initWithMultiplePawnsAndKnight(numberOfPawns);
        final var entities = new HashSet<>(grid.getPawnsPosition());
        entities.add(grid.getKnightPosition());
        assertEquals(numberOfPawns + 1, entities.stream().distinct().count());
    }

    @Test
    public void attemptToCreateTooMuchEntities() {
        assertThrows(IllegalArgumentException.class, () ->
            initWithMultiplePawnsAndKnight(GRID_HEIGHT * GRID_WIDTH));
    }

    @Test
    public void attemptMovingOutsideBoundaries() {
        final var newPosition = new Position(15, 2);
        final var grid = initWithSinglePawnAndKnight();
        assertThrows(IllegalArgumentException.class, () -> grid.moveKnight(newPosition));
    }

    private Grid initWithSinglePawnAndKnight() {
        return new GridFactoryImpl().createWithSingleKnightAndPawn(GRID_WIDTH, GRID_HEIGHT);
    }

    private Grid initWithMultiplePawnsAndKnight(final int numberOfPawns) {
        return new GridFactoryImpl().createWithKnightAndMultiplePawn(GRID_WIDTH, GRID_HEIGHT, numberOfPawns);
    }
}