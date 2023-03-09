package e1.controller;

import e1.model.Grid;
import e1.model.GridFactoryImpl;
import e1.model.StaticEntity;
import utils.Position;

import java.util.Set;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {

	private final Grid grid;

    public LogicsImpl(int gridSize) {
        grid = new GridFactoryImpl().createMultiplePawnsAndKnight(gridSize, gridSize, 10);
    }

    @Override
    public void hit(Position newPosition) {
        if (grid.getKnight().moveTo(newPosition)) {
            grid.removePawnAt(newPosition);
        }
    }

    @Override
    public boolean isOver() {
        return grid.getPawns().isEmpty();
    }

    @Override
    public Position getKnightPosition() {
        return grid.getKnight().getPosition();
    }

    @Override
    public Set<Position> getPawnPosition() {
        return grid.getPawns().stream()
            .map(StaticEntity::getPosition)
            .collect(Collectors.toSet());
    }
}
