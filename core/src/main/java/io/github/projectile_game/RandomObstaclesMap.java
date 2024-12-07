package io.github.projectile_game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.util.Random;

public class RandomObstaclesMap extends PhysicsWorld {
    private static final int MIN_OBSTACLES = 3;
    private static final int MAX_OBSTACLES = 7;
    private static final float MIN_SIZE = 0.2f;
    private static final float MAX_SIZE = 0.7f;
    private static final float WORLD_WIDTH = 8.0f;
    private static final float WORLD_HEIGHT = 6.0f;
    private static final float DENSITY = 1.0f;
    private static final float FRICTION = 0.5f;
    private static final float RESTITUTION = 0.3f;

    private final Random random = new Random();

    public RandomObstaclesMap(World world) {
        super(world);
        createRandomObstacles();
    }

    @Override
    public void createMiddleWall() {
        //add a middle wall please
        createRectangleObstacle(4, 0, 0.2f, 3);
    }

    private void createRandomObstacles() {
        int numObstacles = random.nextInt(MAX_OBSTACLES - MIN_OBSTACLES + 1) + MIN_OBSTACLES;

        for (int i = 0; i < numObstacles; i++) {
            float x = random.nextFloat() * (WORLD_WIDTH - 2) + 1; // x: 1 to 7
            float y = random.nextFloat() * (WORLD_HEIGHT - 2) + 1; // y: 1 to 5
            float size = random.nextFloat() * (MAX_SIZE - MIN_SIZE) + MIN_SIZE;

            switch (random.nextInt(3)) {
                case 0:
                    createCircleObstacle(x, y, size);
                    break;
                case 1:
                    createRectangleObstacle(x, y, size, size * (random.nextFloat() + 0.5f));
                    break;
                case 2:
                    createTriangleObstacle(x, y, size, size * (random.nextFloat() + 0.5f));
                    break;
            }
        }
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