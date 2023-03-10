package e1.model;

import org.junit.jupiter.api.BeforeEach;

class StaticEntityTest extends EntityTest {

    @BeforeEach
    void setup() {
        staticEntity = entityFactory.createPawn(initialPosition);
    }
}
