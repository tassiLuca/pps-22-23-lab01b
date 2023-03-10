package e1.model;


import utils.Position;

import java.util.Set;

/**
 * An interface modeling a grid which contains static and movable entities.
 */
public interface Grid {

    Set<Position> getPawnsPosition();

    Position getKnightPosition();

    void moveKnight(final Position newPosition);
}
