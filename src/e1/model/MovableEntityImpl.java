package e1.model;

import utils.Position;

public class MovableEntityImpl extends StaticEntityImpl implements MovableEntity  {

    private final MovementStrategy movementStrategy;

    public MovableEntityImpl(final Grid grid, final Position initialPosition, final MovementStrategy movementStrategy) {
        super(grid, initialPosition);
        this.movementStrategy = movementStrategy;
    }

    @Override
    public boolean moveTo(Position newPosition) {
        if (isOutOfBound(newPosition)) {
            throw new IllegalArgumentException(newPosition + " is not inside the grid!");
        } else if (movementStrategy.isMovementFeasible(actualPosition, newPosition)) {
            actualPosition = newPosition;
            return true;
        }
        return false;
    }

    private boolean isOutOfBound(Position position) {
        return position.getRow() < 0 || position.getColumn() < 0 ||
            position.getRow() > grid.getHeight() || position.getColumn() > grid.getWidth();
    }
}
