package e1.model;

import utils.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class EntityTest {

    protected static final int POSITION_ROW = 10;
    protected static final int POSITION_COLUMN = 5;
    protected EntityFactory entityFactory = new EntityFactoryImpl();
    protected Position initialPosition = new Position(POSITION_ROW, POSITION_COLUMN);
    protected StaticEntity staticEntity;

    @Test
    void checkPosition() {
        assertEquals(initialPosition, staticEntity.getPosition());
    }
}
