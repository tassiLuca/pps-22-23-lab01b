package e1.model;

import org.junit.jupiter.api.BeforeEach;

class PawnTest extends EntityTest {

    @BeforeEach
    void setup() {
        staticEntity = entityFactory.createPawn(initialPosition);
    }
}
