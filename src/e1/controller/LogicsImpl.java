package e1.controller;

import e1.model.Grid;
import e1.model.GridFactoryImpl;
import utils.Position;

import java.util.Set;

public class LogicsImpl implements Logics {

	private final Grid grid;

    public LogicsImpl(int gridSize) {
        grid = new GridFactoryImpl().createWithSingleKnightAndPawn(gridSize, gridSize);
    }

    @Override
    public void hit(Position newPosition) {
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
