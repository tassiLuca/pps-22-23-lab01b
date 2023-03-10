package e1.model;

import utils.Position;

/**
 * A factory of entities.
 */
public interface EntityFactory {

    /**
     * Creates a <b>static</b> pawn.
     * @param position the pawn position.
     * @return the created pawn.
     */
    StaticEntity createPawn(final Position position);

    /**
     * Creates a <b>movable</b> knight.
     * @param initialPosition the knight initial position.
     * @return the created knight.
     */
    MovableEntity createKnight(final Position initialPosition);
}
