package e1.model;

import utils.Position;

import java.util.Set;

/**
 * An interface modeling a grid which contains static and movable entities.
 */
public interface Grid {

    void removePawnAt(final Position position);

    Set<StaticEntity> getPawns();

    MovableEntity getKnight();

    int getWidth();

    int getHeight();
}
