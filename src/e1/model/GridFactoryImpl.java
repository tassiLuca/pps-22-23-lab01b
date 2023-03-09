package e1.model;

import utils.Position;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GridFactoryImpl implements GridFactory {

    private final Random random = new Random();
    private final EntityFactory entityFactory = new EntityFactoryImpl();

    @Override
    public Grid createSinglePawnAndKnight(final int width, final int height) {
        return createMultiplePawnsAndKnight(width, height, 1);
    }

    @Override
    public Grid createMultiplePawnsAndKnight(final int width, final int height, final int numberOfPawns) {
        if (numberOfPawns >= width * height) {
            throw new IllegalArgumentException("The number of pawns is greater than the grid dimension");
        }
        final var grid = new GridImpl(width, height);
        for (int i = 0; i < numberOfPawns; i++) {
            grid.addPawn(entityFactory.createPawn(grid, getRandomPosition(width, height, getPawnPositions(grid))));
        }
        grid.setKnight(entityFactory.createKnight(grid, getRandomPosition(width, height, getPawnPositions(grid))));
        return grid;
    }

    private Position getRandomPosition(final int widthBoundary, final int highBoundary, final Set<Position> occupied) {
        final var generated = new Position(random.nextInt(widthBoundary), random.nextInt(highBoundary));
        return occupied.contains(generated) ? getRandomPosition(widthBoundary, highBoundary, occupied) : generated;
    }

    private Set<Position> getPawnPositions(GridImpl grid) {
        return grid.getPawns().stream().map(StaticEntity::getPosition).collect(Collectors.toSet());
    }

    private static class GridImpl implements Grid {

        private final Set<StaticEntity> pawns = new HashSet<>();
        private MovableEntity knight;
        private final int width;
        private final int height;

        private GridImpl(final int width, final int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void removePawnAt(Position position) {
            pawns.stream().filter(pawn -> pawn.getPosition().equals(position))
                .findAny()
                .ifPresent(pawns::remove);
        }

        @Override
        public Set<StaticEntity> getPawns() {
            return Collections.unmodifiableSet(pawns);
        }

        @Override
        public MovableEntity getKnight() {
            return knight;
        }

        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        private void addPawn(final StaticEntity pawn) {
            this.pawns.add(pawn);
        }

        private void setKnight(final MovableEntity movableEntities) {
            this.knight = movableEntities;
        }
    }
}
