package e1.model;

import utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KnightTest extends EntityTest {

    private MovableEntity movableEntity;
    private final Set<Position> allowedPositions = Set.of(
        new Position(8, 4),
        new Position(8, 6),
        new Position(9, 3),
        new Position(9, 7),
        new Position(11, 3),
        new Position(11, 7),
        new Position(12, 4),
        new Position(12, 6)
    );

    @BeforeEach
    void setup() {
        movableEntity = entityFactory.createKnight(initialPosition);
        super.staticEntity = movableEntity;
    }

    @Test
    void entityShouldMoveCorrectly() {
        final int minRow = allowedPositionRows().min(Integer::compare).orElse(0);
        final int maxRow = allowedPositionRows().max(Integer::compare).orElse(POSITION_ROW);
        final int minColumn = allowedPositionColumns().min(Integer::compare).orElse(0);
        final int maxColumn = allowedPositionColumns().max(Integer::compare).orElse(POSITION_COLUMN);
        for (int row = minRow; row < maxRow + 1; row++) {
            for (int column = minColumn; column < maxColumn + 1; column++) {
                setup();
                final var newPosition = new Position(row, column);
                movableEntity.moveTo(newPosition);
                assertEquals(
                    allowedPositions.contains(newPosition) ? newPosition : initialPosition,
                    movableEntity.getPosition()
                );
            }
        }
    }

    private Stream<Integer> allowedPositionColumns() {
        return allowedPositions.stream().map(Position::getColumn);
    }

    private Stream<Integer> allowedPositionRows() {
        return allowedPositions.stream().map(Position::getRow);
    }
}