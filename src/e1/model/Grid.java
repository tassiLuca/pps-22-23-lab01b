package e1.model;


import utils.Position;

import java.util.Set;

/**
 * An interface modeling a grid which contains static and movable entities.
 */
public interface Grid {

    /**
     * @returm a {@link Set} containing the pawns {@link Position}s.
     */
    Set<Position> getPawnsPosition();

    /**
     * @return the knight {@link Position}.
     */
    Position getKnightPosition();

    /**
     * Attempts to move the knight in the given position.
     * @param newPosition the position where to move the knight.
     */
    void moveKnight(final Position newPosition);
}
