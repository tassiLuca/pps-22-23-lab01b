package e1.model;

import utils.Position;

/**
 * A strategy encapsulating the movement logic of an {@link StaticEntity}.
 */
interface MovementStrategy {

    /**
     * @param actualPosition the current position of the entity
     * @param newPosition the desired next position of the entity
     * @return true if the move is feasible, false otherwise.
     */
    boolean isMovementFeasible(Position actualPosition, Position newPosition);
}
