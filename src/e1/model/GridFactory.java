package e1.model;

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
    Grid createWithSingleKnightAndPawn(final int width, final int height);

    /**
     * Creates a {@link Grid} populated with a single knight and multiple pawns.
     * @param width the width of the grid.
     * @param height the height of the grid.
     * @param numberOfPawns the number of pawns to generate.
     * @return the created grid.
     */
    Grid createWithKnightAndMultiplePawn(final int width, final int height, final int numberOfPawns);
}
