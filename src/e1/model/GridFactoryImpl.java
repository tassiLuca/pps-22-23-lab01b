package e1.model;

import utils.Position;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class GridFactoryImpl implements GridFactory {

    private final Random random = new Random();
    private final EntityFactory entityFactory = new EntityFactoryImpl();

    @Override
    public Grid createWithSingleKnightAndPawn(final int width, final int height) {
        return createWithKnightAndMultiplePawn(width, height, 1);
    }

    @Override
    public Grid createWithKnightAndMultiplePawn(final int width, final int height, final int numberOfPawns) {
        if (numberOfPawns >= width * height) {
            throw new IllegalArgumentException("The number of pawns is greater than the grid dimension");
        }
        final var pawns = new HashSet<StaticEntity>();
        for (int i = 0; i < numberOfPawns; i++) {
            pawns.add(entityFactory.createPawn(getRandomPosition(width, height, pawns.stream().map(StaticEntity::getPosition).collect(Collectors.toSet()))));
        }
        final var knight = entityFactory.createKnight(getRandomPosition(width, height, pawns.stream().map(StaticEntity::getPosition).collect(Collectors.toSet())));
        return new GridImpl(width, height, pawns, knight);
    }

    private Position getRandomPosition(final int widthBoundary, final int highBoundary, final Set<Position> occupied) {
        final var generated = new Position(random.nextInt(widthBoundary), random.nextInt(highBoundary));
        return occupied.contains(generated) ? getRandomPosition(widthBoundary, highBoundary, occupied) : generated;
    }
}
