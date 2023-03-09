package e1.model;

/**
 * This is a factory which contains a set of methods to make agile
 * create a new {@link Grid}.
 */
public interface GridFactory {

    Grid createSinglePawnAndKnight(final int width, final int height);

    Grid createMultiplePawnsAndKnight(final int width, final int height, final int numberOfPawns);
}
