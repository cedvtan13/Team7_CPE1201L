package io.github.projectile_game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.util.Random;

public class StructuredObstaclesMap extends PhysicsWorld {
    private static final int GRID_ROWS = 2;
    private static final int GRID_COLUMNS = 2;
    private static final float MIN_SIZE = 0.2f;
    private static final float MAX_SIZE = 0.5f;
    private static final float WORLD_WIDTH = 8.0f;
    private static final float WORLD_HEIGHT = 6.0f;
    private static final float DENSITY = 1.0f;
    private static final float FRICTION = 0.5f;
    private static final float RESTITUTION = 0.3f;

    private final Random random = new Random();

    public StructuredObstaclesMap(World world) {
        super(world);
        createGridObstacles();
    }

    @Override
    public void createMiddleWall() {
        createRectangleObstacle(4, 0, 0.2f, 3);
    }

    private void createGridObstacles() {
        float cellWidth = WORLD_WIDTH / (GRID_COLUMNS + 1);
        float cellHeight = WORLD_HEIGHT / (GRID_ROWS + 1);

        for (int row = 1; row <= GRID_ROWS; row++) {
            for (int col = 1; col <= GRID_COLUMNS; col++) {
                float x = col * cellWidth;
                float y = row * cellHeight;
                float size = getRandomSize();

                switch (random.nextInt(4)) {
                    case 0:
                        createCircleObstacle(x, y, size);
                        break;
                    case 1:
                        createRectangleObstacle(x, y, size, size * (random.nextFloat() + 0.5f));
                        break;
                    case 2:
                        createTriangleObstacle(x, y, size, size * (random.nextFloat() + 0.5f));
                        break;
                    case 3:
                        createDiagonalObstacle(x, y, size, size * (random.nextFloat() + 0.5f));
                        break;
                }
            }
        }
    }

    private float getRandomSize() {
        return random.nextFloat() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE;
    }

    private void createCircleObstacle(float x, float y, float radius) {
        Body body = createStaticBody(x, y);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        createFixture(body, shape);
        shape.dispose();
    }

    private void createRectangleObstacle(float x, float y, float width, float height) {
        Body body = createStaticBody(x, y);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
        createFixture(body, shape);
        shape.dispose();
    }

    private void createTriangleObstacle(float x, float y, float width, float height) {
        Body body = createStaticBody(x, y);
        PolygonShape shape = new PolygonShape();
        shape.set(new Vector2[]{
            new Vector2(-width / 2, -height / 2),
            new Vector2(width / 2, -height / 2),
            new Vector2(0, height / 2)
        });
        createFixture(body, shape);
        shape.dispose();
    }

    private void createDiagonalObstacle(float x, float y, float width, float height) {
        Body body = createStaticBody(x, y);
        PolygonShape shape = new PolygonShape();
        shape.set(new Vector2[]{
            new Vector2(-width / 2, -height / 2),
            new Vector2(width / 2, height / 2),
            new Vector2(width / 2, -height / 2),
            new Vector2(-width / 2, height / 2)
        });
        createFixture(body, shape);
        shape.dispose();
    }

    private Body createStaticBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        return world.createBody(bodyDef);
    }

    private void createFixture(Body body, Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = DENSITY;
        fixtureDef.friction = FRICTION;
        fixtureDef.restitution = RESTITUTION;
        body.createFixture(fixtureDef);
    }
}