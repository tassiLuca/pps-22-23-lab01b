package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogicsTest {

    private static final int GRID_SIZE = 5;
    private static final int ATTEMPTS = 100;
    private final Random randomGenerator = new Random();
    private Logics logics;

    @Nested
    class InitializationTests {
        @BeforeEach
        void setUp() {
            init(GRID_SIZE);
        }

        @RepeatedTest(ATTEMPTS)
        void gridContainsOnlyOnePawn() {
            assertEquals(1, countSatisfiedPredicate((row, column) -> logics.hasPawn(row, column)));
        }

        @RepeatedTest(ATTEMPTS)
        void gridContainsOnlyOneKnight() {
            assertEquals(1, countSatisfiedPredicate((row, column) -> logics.hasKnight(row, column)));
        }

        @RepeatedTest(ATTEMPTS)
        void knightAndPawnPositionDoNotOverlap() {
            assertNotEquals(logics.getPawn(), logics.getKnight());
        }

        @Test
        void overlapsShouldThrowException() {
            final var position = new Pair<>(randomGenerator.nextInt(GRID_SIZE), randomGenerator.nextInt(GRID_SIZE));
            assertThrows(IllegalArgumentException.class, () -> new LogicsImpl(GRID_SIZE, position, position));
        }

        @Test
        void positionsNotInBoundsShouldThrowException() {
            final var knightPosition = new Pair<>(GRID_SIZE, GRID_SIZE);
            final var pawnPosition = new Pair<>(GRID_SIZE + 1, GRID_SIZE + 1);
            assertThrows(IllegalArgumentException.class, () -> new LogicsImpl(GRID_SIZE, pawnPosition, knightPosition));
        }
    }

    @Nested
    class RegressionTests {
        @Test
        void movingKnightOutsideGridShouldThrowException() {
            init(GRID_SIZE);
            assertThrows(IndexOutOfBoundsException.class, () -> logics.hit(GRID_SIZE, GRID_SIZE));
        }

        @Test
        void knightShouldMoveAccordingToRules() {
            final var knightPosition = new Pair<>(3, 4);
            final var pawnPosition = new Pair<>(1, 3);
            final var allowedMoves = Set.of(new Pair<>(1, 3), new Pair<>(2, 2), new Pair<>(4, 2));
            doOnEachGridElement((row, column) -> {
                final var position = new Pair<>(row, column);
                init(GRID_SIZE, pawnPosition, knightPosition);
                logics.hit(row, column);
                assertEquals(allowedMoves.contains(position) ? position : knightPosition, logics.getKnight());
            });
        }

        @Test
        void knightHitsPawn() {
            final var knightPosition = new Pair<>(3, 4);
            final var pawnPosition = new Pair<>(1, 3);
            init(GRID_SIZE, pawnPosition, knightPosition);
            assertTrue(logics.hit(pawnPosition.getX(), pawnPosition.getY()));
        }
    }

    private void init(final int size) {
        logics = new LogicsImpl(size);
    }

    private void init(final int size, final Pair<Integer, Integer> pawn, final Pair<Integer, Integer> knight) {
        logics = new LogicsImpl(size, pawn, knight);
    }

    private int countSatisfiedPredicate(final BiPredicate<Integer, Integer> predicate) {
        final var counter = new AtomicInteger();
        doOnEachGridElement((row, column) -> counter.set(counter.get() + (predicate.test(row, column) ? 1 : 0)));
        return counter.get();
    }

    private void doOnEachGridElement(final BiConsumer<Integer, Integer> action) {
        IntStream.range(0, GRID_SIZE).forEach(column ->
            IntStream.range(0, GRID_SIZE).forEach(row ->
                action.accept(row, column)
            )
        );
    }
}