package e1.model;

import utils.Position;

public class MovableEntityImpl extends StaticEntityImpl implements MovableEntity  {

    private final MovementStrategy movementStrategy;

    public MovableEntityImpl(final Position initialPosition, final MovementStrategy movementStrategy) {
        super(initialPosition);
        this.movementStrategy = movementStrategy;
    }

    @Override
    public boolean moveTo(Position newPosition) {
        if (movementStrategy.isMovementFeasible(actualPosition, newPosition)) {
            actualPosition = newPosition;
            return true;
        }
        return false;
    }
}
