package e1.model;

import utils.Position;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GridImpl implements Grid {

    private final Set<StaticEntity> pawns;
    private final MovableEntity knight;
    private final int width;
    private final int height;

    public GridImpl(final int width, final int height, final Set<StaticEntity> pawns, final MovableEntity knight) {
        this.width = width;
        this.height = height;
        this.pawns = new HashSet<>(pawns);
        this.knight = knight;
    }

    @Override
    public Set<Position> getPawnsPosition() {
        return pawns.stream().map(StaticEntity::getPosition).collect(Collectors.toSet());
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
