package e1.model;

import utils.Position;

public class EntityFactoryImpl implements EntityFactory {

    public static final int STEP_DISTANCE = 3;

    @Override
    public StaticEntity createPawn(final Position position) {
        return new StaticEntityImpl(position);
    }

    @Override
    public MovableEntity createKnight(final Position initialPosition) {
        return new MovableEntityImpl(initialPosition, (actualPosition, newPosition) -> {
            int verticalDistance = newPosition.getRow() - actualPosition.getRow();
            int horizontalDistance = newPosition.getColumn() - actualPosition.getColumn();
            return verticalDistance != 0 && horizontalDistance != 0 &&
                (Math.abs(verticalDistance) + Math.abs(horizontalDistance) == STEP_DISTANCE);
        });
    }
}
