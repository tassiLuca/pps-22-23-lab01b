package e1.controller;

import utils.Position;

import java.util.Set;

public interface Logics {

    void hit(Position newPosition);

    boolean isOver();

    Position getKnightPosition();

    Set<Position> getPawnPosition();
}
