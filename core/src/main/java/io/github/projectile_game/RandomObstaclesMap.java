package io.github.projectile_game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.util.Random;

public class RandomObstaclesMap extends PhysicsWorld {
    private Random random = new Random();

    public RandomObstaclesMap(World world) {
        super(world);
        createRandomObstacles();
    }

    @Override
    public void createMiddleWall() {
        // Implementation for middle wall (if needed)
    }

    private void createRandomObstacles() {
        int numObstacles = random.nextInt(5) + 3; // 3 to 7 obstacles

        for (int i = 0; i < numObstacles; i++) {
            float x = random.nextFloat() * 6 + 1; // x: 1 to 7
            float y = random.nextFloat() * 4 + 1; // y: 1 to 5
            float size = random.nextFloat() * 0.5f + 0.2f; // size: 0.2 to 0.7

            int shapeType = random.nextInt(3); // 0: Circle, 1: Rectangle, 2: Triangle

            switch (shapeType) {
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
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();
    }

    private void createRectangleObstacle(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();
    }

    private void createTriangleObstacle(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-width / 2, -height / 2);
        vertices[1] = new Vector2(width / 2, -height / 2);
        vertices[2] = new Vector2(0, height / 2);
        shape.set(vertices);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();
    }
}
