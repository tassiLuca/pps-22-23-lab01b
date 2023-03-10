package e1.model;

import utils.Position;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GridFactoryImpl implements GridFactory {

    private record GridImpl(int width, int height, Set<StaticEntity> pawns, MovableEntity knight) implements Grid {

        @Override
        public Set<Position> getPawnsPosition() {
            return positionsFromEntities(pawns);
        }

        @Override
        public Position getKnightPosition() {
            return knight.getPosition();
        }

        @Override
        public void moveKnight(final Position newPosition) {
            if (isOutOfBound(newPosition)) {
                throw new IllegalArgumentException(newPosition + " is not inside the grid!");
            } else if (knight.moveTo(newPosition)) {
                removePawnAt(newPosition);
            }
        }

        private boolean isOutOfBound(final Position position) {
            return position.getRow() < 0 || position.getColumn() < 0 ||
                position.getRow() > height || position.getColumn() > width;
        }

        private void removePawnAt(final Position position) {
            pawns.stream().filter(pawn -> pawn.getPosition().equals(position))
                .findAny()
                .ifPresent(pawns::remove);
        }
    }

    private final Random random = new Random();
    private final EntityFactory entityFactory = new EntityFactoryImpl();

    @Override
    public Grid createWithSingleKnightAndPawn(final int width, final int height) {
        return createWithKnightAndMultiplePawn(width, height, 1);
    }

    @Override
    public Grid createWithKnightAndMultiplePawn(final int width, final int height, final int numberOfPawns) {
        if (numberOfPawns >= width * height) {
            throw new IllegalArgumentException("The number of pawns exceeds the grid dimension");
        }
        final var pawns = new HashSet<StaticEntity>();
        for (int i = 0; i < numberOfPawns; i++) {
            pawns.add(entityFactory.createPawn(getRandomPosition(width, height, positionsFromEntities(pawns))));
        }
        final var knight = entityFactory.createKnight(getRandomPosition(width, height, positionsFromEntities(pawns)));
        return new GridImpl(width, height, pawns, knight);
    }

    private static Set<Position> positionsFromEntities(Set<StaticEntity> pawns) {
        return pawns.stream()
            .map(StaticEntity::getPosition)
            .collect(Collectors.toSet());
    }

    private Position getRandomPosition(final int widthBoundary, final int highBoundary, final Set<Position> occupied) {
        final var generated = new Position(random.nextInt(widthBoundary), random.nextInt(highBoundary));
        return occupied.contains(generated) ? getRandomPosition(widthBoundary, highBoundary, occupied) : generated;
    }

    @Override
    public Grid create(final int width, final int height, final MovableEntity knight, final Set<StaticEntity> pawns) {
        if (areOverlapping(knight, pawns)) {
            throw new IllegalArgumentException();
        }
        return new GridImpl(width, height, pawns, knight);
    }

    private boolean areOverlapping(final MovableEntity knight, final Set<StaticEntity> pawns) {
        final var entities = new HashSet<>(pawns);
        entities.add(knight);
        return positionsFromEntities(entities).stream().distinct().count() <= pawns.size();
    }
}
