package e1.model;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
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
        assertNotNull(grid.getKnight());
        assertEquals(numberOfPawns, grid.getPawns().size());
    }

    @RepeatedTest(REPETITIONS)
    void testEntitiesDoNotOverlap() {
        final int numberOfPawns = 10;
        final var grid = initWithMultiplePawnsAndKnight(numberOfPawns);
        final var entities = new HashSet<>(grid.getPawns());
        entities.add(grid.getKnight());
        assertEquals(numberOfPawns + 1, entities.stream().map(StaticEntity::getPosition).distinct().count());
    }

    @Test
    public void attemptToCreateTooMuchEntities() {
        assertThrows(IllegalArgumentException.class, () ->
            initWithMultiplePawnsAndKnight(GRID_HEIGHT * GRID_WIDTH));
    }

    @Test
    public void testRemovingEntities() {
        final var grid = initWithSinglePawnAndKnight();
        final var pawnPosition = grid.getPawns().stream().map(StaticEntity::getPosition).findFirst();
        grid.removePawnAt(pawnPosition.get());
        assertEquals(Collections.emptySet(), grid.getPawns());
    }

    private Grid initWithSinglePawnAndKnight() {
        return new GridFactoryImpl().createSinglePawnAndKnight(GRID_WIDTH, GRID_HEIGHT);
    }

    private Grid initWithMultiplePawnsAndKnight(final int numberOfPawns) {
        return new GridFactoryImpl().createMultiplePawnsAndKnight(GRID_WIDTH, GRID_HEIGHT, numberOfPawns);
    }
}