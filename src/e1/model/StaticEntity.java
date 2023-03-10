package e1.model;

import utils.Position;

/**
 * An interface modeling a static entity, i.e. an entity that can not move.
 */
public interface StaticEntity {

    /**
     * @return the current entity's position.
     */
    Position getPosition();
}
