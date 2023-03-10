package e1.model;

import utils.Position;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        checkEntitiesNumber(width, height, numberOfPawns);
        final var pawns = randomPositions(width, height)
            .distinct()
            .limit(numberOfPawns)
            .map(entityFactory::createPawn)
            .collect(Collectors.toSet());
        final MovableEntity knight = randomPositions(width, height)
            .filter(pos -> !positionsFromEntities(pawns).contains(pos))
            .map(entityFactory::createKnight)
            .findFirst().orElseThrow();
        return create(width, height, knight, pawns);
    }

    private Stream<Position> randomPositions(final int widthBound, int heightBound) {
        return Stream.generate(() -> new Position(random.nextInt(heightBound), random.nextInt(widthBound)));
    }

    @Override
    public Grid create(final int width, final int height, final MovableEntity knight, final Set<StaticEntity> pawns) {
        checkOverlap(knight, pawns);
        checkEntitiesNumber(width, height, pawns.size());
        return new GridImpl(width, height, pawns, new EntityFactoryImpl().createKnight(knight.getPosition()));
    }

    private void checkOverlap(final MovableEntity knight, final Set<StaticEntity> pawns) {
        if (areOverlapping(knight, pawns)) {
            throw new IllegalArgumentException("Entities must not overlap.");
        }
    }

    private boolean areOverlapping(final MovableEntity knight, final Set<StaticEntity> pawns) {
        final var entities = new HashSet<>(pawns);
        entities.add(knight);
        return positionsFromEntities(entities).stream().distinct().count() <= pawns.size();
    }

    private void checkEntitiesNumber(final int width, final int height, final int numberOfPawns) {
        if (numberOfPawns >= width * height) {
            throw new IllegalArgumentException("The number of pawns exceeds the grid dimension");
        }
    }

    private static Set<Position> positionsFromEntities(final Set<StaticEntity> pawns) {
        return pawns.stream()
            .map(StaticEntity::getPosition)
            .collect(Collectors.toSet());
    }
}
