package e1.model;

import utils.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovableEntityTest extends EntityTest {

    private MovableEntity movableEntity;

    @BeforeEach
    void setup() {
        movableEntity = entityFactory.createKnight(initialPosition);
        super.staticEntity = movableEntity;
    }

    @Test()
    void checkAllowedMovement() {
        final var newPosition = new Position(9, 3);
        movableEntity.moveTo(newPosition);
        assertEquals(newPosition, movableEntity.getPosition());
    }

    @Test
    void checkNotAllowedMovement() {
        final var newPosition = new Position(9, 2);
        movableEntity.moveTo(newPosition);
        assertEquals(initialPosition, movableEntity.getPosition());
    }
}