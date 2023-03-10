package e1.model;

import utils.Position;

public class EntityFactoryImpl implements EntityFactory {

    @Override
    public StaticEntity createPawn(final Position position) {
        return new StaticEntityImpl(position);
    }

    @Override
    public MovableEntity createKnight(final Position initialPosition) {
        return new MovableEntityImpl(initialPosition, (actualPosition, newPosition) -> {
            int x = newPosition.getRow() - actualPosition.getRow();
            int y = newPosition.getColumn() - actualPosition.getColumn();
            return x != 0 && y != 0 && (Math.abs(x) + Math.abs(y) == 3);
        });
    }
}
