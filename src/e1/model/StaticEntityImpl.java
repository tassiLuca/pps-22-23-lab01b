package e1.model;

import utils.Position;

/**
 * A simple entity implementation.
 */
public class StaticEntityImpl implements StaticEntity {

    protected Grid grid;
    protected Position actualPosition;

    public StaticEntityImpl(final Grid grid, final Position initialPosition) {
        this.grid = grid;
        actualPosition = initialPosition;
    }

    @Override
    public Position getPosition() {
        return actualPosition;
    }
}
