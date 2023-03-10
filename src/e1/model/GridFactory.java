package e1.model;

import java.util.Set;

/**
 * A factory which contains a set of methods to create and populate a {@link Grid}.
 */
public interface GridFactory {

    /**
     * Creates a {@link Grid} populated with a single knight and pawn.
     * @param width the width of the grid.
     * @param height the height of the grid.
     * @return the created grid.
     */
    Grid createWithSingleKnightAndPawn(int width, int height);

    /**
     * Creates a {@link Grid} populated with a single knight and multiple pawns.
     * @param width the width of the grid.
     * @param height the height of the grid.
     * @param numberOfPawns the number of pawns to generate.
     * @return the created grid.
     */
    Grid createWithKnightAndMultiplePawn(int width, int height, int numberOfPawns);

    /**
     * Creates a {@link Grid} populated with the given knight and pawns.
     * @param width the width of the grid.
     * @param height the height of the grid.
     * @param knight the knight to place in the grid.
     * @param pawns a {@link Set} of pawns to place in the grid.
     * @return the created grid.
     */
    Grid create(int width, int height, MovableEntity knight, Set<StaticEntity> pawns);
}
