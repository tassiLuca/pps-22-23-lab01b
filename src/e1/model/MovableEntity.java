package e1.model;

import utils.Position;

/**
 * An interface modeling a movable entity, i.e. an entity that can move.
 */
public interface MovableEntity extends StaticEntity {

    /**
     * Try to attempt a movement, updating the position of the entity
     * only if the given new one is allowed.
     * @param newPosition the {@link Position} where to move the entity.
     */
    boolean moveTo(Position newPosition);
}
