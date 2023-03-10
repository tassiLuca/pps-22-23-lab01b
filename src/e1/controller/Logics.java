package e1.controller;

import utils.Position;

import java.util.Set;

public interface Logics {

    /**
     * Attempts to move the knight in the given position.
     * if the movement is not valid the operation is not performed.
     * @param newPosition the position where move the knight.
     */
    void hit(final Position newPosition);

    /**
     * @return true if the knight ate all the pawns in the board, 
     * i.e. no pawns are left, false otherwise.
     */
    boolean isOver();

    /**
     * @return a {@link Position} describing the knight position inside the board.
     */
    Position getKnightPosition();

    /**
     * @return a {@link Set} containing all the pawns positions inside the board.
     */
    Set<Position> getPawnsPosition();
}
