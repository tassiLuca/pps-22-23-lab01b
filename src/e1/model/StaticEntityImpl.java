package e1.model;

import utils.Position;

public class StaticEntityImpl implements StaticEntity {

    protected Position actualPosition;

    public StaticEntityImpl(final Position initialPosition) {
        actualPosition = initialPosition;
    }

    @Override
    public Position getPosition() {
        return actualPosition;
    }
}
