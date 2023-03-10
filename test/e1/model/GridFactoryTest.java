package e1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import utils.Position;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GridFactoryTest {

    private final static int GRID_HEIGHT = 10;
    private final static int GRID_WIDTH = 15;
    private final static int REPETITIONS = 20;
    private final GridFactory gridFactory = new GridFactoryImpl();

    abstract static class CommonTests {

        protected int pawnsNumber;
        protected Grid grid;

        @Test
        void knightShouldBePresent() {
            assertNotNull(grid.getKnightPosition());
        }

        @Test
        void pawnsShouldBePresent() {
            assertEquals(pawnsNumber, grid.getPawnsPosition().size());
        }

        @RepeatedTest(REPETITIONS)
        void knightAndPawnShouldNotOverlap() {
            final var entities = new HashSet<>(grid.getPawnsPosition());
            entities.add(grid.getKnightPosition());
            assertEquals(pawnsNumber + 1, entities.stream().distinct().count());
        }
    }

    @Nested
    class CreationWithSinglePawnTests extends CommonTests {

        @BeforeEach
        void setup() {
            pawnsNumber = 1;
            grid = gridFactory.createWithSingleKnightAndPawn(GRID_WIDTH, GRID_HEIGHT);
        }
    }

    @Nested
    class CreationWithMultiplePawnsTests extends CommonTests {

        private static final int PAWNS_NUMBER = 10;

        @BeforeEach
        void setup() {
            pawnsNumber = PAWNS_NUMBER;
            grid = initWithKnightAndMultiplePawns(pawnsNumber);
        }

        @Test
        public void creationWithTooManyShouldThrowException() {
            assertThrows(IllegalArgumentException.class, () ->
                initWithKnightAndMultiplePawns(GRID_HEIGHT * GRID_WIDTH));
        }

        private Grid initWithKnightAndMultiplePawns(final int pawnsNumber) {
            return gridFactory.createWithKnightAndMultiplePawn(GRID_WIDTH, GRID_HEIGHT, pawnsNumber);
        }
    }

    @Nested
    class CreationWithEntities extends CommonTests {

        private final EntityFactory entityFactory = new EntityFactoryImpl();
        private final MovableEntity knight = entityFactory.createKnight(new Position(0, 0));
        private final Set<StaticEntity> pawns = Set.of(entityFactory.createPawn(new Position(1, 1)));

        @BeforeEach
        void setup() {
            pawnsNumber = pawns.size();
            grid = initWith(knight, pawns);
        }

        @Test
        void knightShouldBeInGivenPosition() {
            assertEquals(knight.getPosition(), grid.getKnightPosition());
        }

        @Test
        void pawnsShouldBeInGivenPositions() {
            assertEquals(getPositionsFromEntities(pawns), grid.getPawnsPosition());
        }

        @Test
        public void attemptToCreateOverlappingEntities() {
            final var position = new Position(0, 0);
            final var knight = entityFactory.createKnight(position);
            final var pawns = Set.of(entityFactory.createPawn(position));
            assertThrows(IllegalArgumentException.class, () -> initWith(knight, pawns));
        }

        @Test
        public void attemptUpdatingKnightDirectlyOnIt() {
            final var initialPosition = new Position(9, 14);
            final var knight = entityFactory.createKnight(initialPosition);
            final var grid = initWith(knight, pawns);
            final var newPosition = new Position(8, 16);
            knight.moveTo(newPosition);
            assertEquals(newPosition, knight.getPosition());
            assertEquals(initialPosition, grid.getKnightPosition());
        }

        @Test
        public void creationWithTooManyShouldThrowException() {
            final var knight = entityFactory.createKnight(new Position(GRID_HEIGHT, GRID_WIDTH));
            final var pawns = IntStream.range(0, GRID_HEIGHT + 1)
                .mapToObj(row -> IntStream.range(0, GRID_WIDTH).mapToObj(col -> new Position(row, col)))
                .flatMap(Function.identity())
                .map(entityFactory::createPawn)
                .collect(Collectors.toSet());
            assertThrows(IllegalArgumentException.class, () -> initWith(knight, pawns));
        }

        private Grid initWith(MovableEntity knight, Set<StaticEntity> pawns) {
            return new GridFactoryImpl().create(GRID_WIDTH, GRID_HEIGHT, knight, pawns);
        }
    }

    private <E extends StaticEntity> Set<Position> getPositionsFromEntities(Set<E> entities) {
        return entities.stream()
            .map(E::getPosition)
            .collect(Collectors.toSet());
    }
}