package e1.model;

import utils.Position;

/**
 * A factory of entities.
 */
public interface EntityFactory {

    /**
     * Creates a <b>static</b> pawn.
     * @param grid the grid on which place the entity.
     * @param position the pawn position.
     * @return the created pawn.
     */
    StaticEntity createPawn(final Grid grid, final Position position);

    /**
     * Creates a <b>movable</b> knight.
     * @param grid the grid on which place the entity.
     * @param initialPosition the knight initial position.
     * @return the created knight.
     */
    MovableEntity createKnight(final Grid grid, final Position initialPosition);
}
