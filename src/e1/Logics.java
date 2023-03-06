package e1;

public interface Logics{

    /**
     * attempt to move Knight on position row,col, if possible
     * 
     * @param row
     * @param col
     * @return whether the pawn has been hit 
     */
    boolean hit(int row, int col);

    /**
     * @param row
     * @param col
     * @return whether position row,col has the knight
     */
    boolean hasKnight(int row, int col);

    /**
     * @param row
     * @param col
     * @return whether position row,col has the pawn
     */
    boolean hasPawn(int row, int col);

    /**
     * @return a {@link Pair} describing the position of the knight (column, row)
     */
    Pair<Integer, Integer> getKnight();

    /**
     * @return a {@link Pair} describing the position of the pawn (column, row)
     */
    Pair<Integer, Integer> getPawn();
}
