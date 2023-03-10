package e1.controller;

import e1.model.Grid;
import e1.model.GridFactoryImpl;
import utils.Position;

import java.util.Set;

public class LogicsImpl implements Logics {

    private final Grid grid;

    public LogicsImpl(final int gridSize) {
        grid = new GridFactoryImpl().createWithSingleKnightAndPawn(gridSize, gridSize);
    }

    public LogicsImpl(final Grid grid) {
        this.grid = grid;
    }

    @Override
    public void hit(final Position newPosition) {
        grid.moveKnight(newPosition);
    }

    @Override
    public boolean isOver() {
        return grid.getPawnsPosition().isEmpty();
    }

    @Override
    public Position getKnightPosition() {
        return grid.getKnightPosition();
    }

    @Override
    public Set<Position> getPawnsPosition() {
        return grid.getPawnsPosition();
    }
}
