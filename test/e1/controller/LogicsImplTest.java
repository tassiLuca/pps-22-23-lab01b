package e1.controller;

import e1.model.EntityFactory;
import e1.model.EntityFactoryImpl;
import e1.model.GridFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Position;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class LogicsImplTest {

    private final int GRID_HIGH = 5;
    private final int GRID_WIDTH = 6;
    private final EntityFactory entityFactory = new EntityFactoryImpl();
    private final Position pawnPosition = new Position(1, 3);
    private final Position knightPosition = new Position(3, 4);
    private final Set<Position> legalPositions = Set.of(
        new Position(1, 3),
        new Position(2, 2),
        new Position(4, 2),
        new Position(1, 5)
    );
    private Logics logic;

    @BeforeEach
    void setup() {
        final var pawns = Set.of(entityFactory.createPawn(pawnPosition));
        final var knights = entityFactory.createKnight(knightPosition);
        final var grid = new GridFactoryImpl().create(GRID_WIDTH, GRID_HIGH, knights, pawns);
        logic = new LogicsImpl(grid);
    }

    @Test
    void isNotInitiallyOver() {
        assertFalse(logic.isOver());
    }

    @Test
    void entitiesAreCorrectlyPlaced() {
        assertEquals(knightPosition, logic.getKnightPosition());
        assertEquals(Set.of(pawnPosition), logic.getPawnsPosition());
    }

    @Test
    void knightShouldMoveAccordingToRules() {
        doOnEachGridElement(position -> {
            setup();
            logic.hit(position);
            assertEquals(legalPositions.contains(position) ? position : knightPosition, logic.getKnightPosition());
        });
    }

    @Test
    void checkIsOver() {
        logic.hit(pawnPosition);
        assertTrue(logic.isOver());
    }

    private void doOnEachGridElement(final Consumer<Position> action) {
        IntStream.range(0, GRID_WIDTH).forEach(column ->
            IntStream.range(0, GRID_HIGH).forEach(row ->
                action.accept(new Position(row, column))
            )
        );
    }
}