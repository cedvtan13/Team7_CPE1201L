package io.github.projectile_game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomObstaclesMap extends PhysicsWorld {
    private Random random = new Random();
    private List<Vector2> existingObstaclePositions = new ArrayList<>();
    private static final float MAP_WIDTH = 10f;  // Define map width
    private static final float MAP_HEIGHT = 6f; // Define map height

    public RandomObstaclesMap(World world) {
        super(world);
        createBalancedObstacles();
    }

    @Override
    public void createMiddleWall() {
        // Define the position, size, and other properties for the middle wall
        float x = 3.5f; // X-position in the middle of the map (assuming the map width is 10)
        float y = 1f; // Y-position (adjust based on where you want the wall placed)
        float width = 1f; // Width of the middle wall
        float height = 1f; // Height of the middle wall

        // Create the wall as a static obstacle
        createGroundObstacle(x, y, width, height);
    }


    private void createBalancedObstacles() {
        // Fixed ground obstacles
        createGroundObstacle(2f, 0.25f, 2f, 0.5f); // Left-side ground block
        createGroundObstacle(7f, 0.25f, 2f, 0.5f); // Right-side ground block
        createGroundObstacle(4.5f, 0.25f, 1.5f, 0.5f); // Center ground block

        // Scatter remaining obstacles across the map
        int numObstacles = 10; // Increase to create more variety
        float minDistance = 1.0f; // Minimum distance between obstacles

        for (int i = 0; i < numObstacles; i++) {
            boolean validPosition = false;
            float x = 0, y = 0, size = 0;

            while (!validPosition) {
                x = random.nextFloat() * (MAP_WIDTH - 2) + 1; // x: 1 to (MAP_WIDTH - 1)
                y = random.nextFloat() * (MAP_HEIGHT - 2) + 2; // y: 2 to (MAP_HEIGHT - 1)
                size = random.nextFloat() * 0.5f + 0.3f; // size: 0.3 to 0.8

                // Check if the new position is far enough from existing obstacles
                validPosition = checkObstaclePosition(x, y, size, minDistance);
            }

            createRandomShape(x, y, size);
        }

        // Place the circle in the first gap between the rectangles
        placeCircleInFirstGap();
    }

    private boolean checkObstaclePosition(float x, float y, float size, float minDistance) {
        // Logic to ensure no obstacles overlap or are too close
        for (Vector2 existing : existingObstaclePositions) {
            if (existing.dst(x, y) < minDistance + size) {
                return false; // Too close to another obstacle
            }
        }
        existingObstaclePositions.add(new Vector2(x, y));
        return true;
    }

    private void placeCircleInFirstGap() {
        // Coordinates for placing the circle in the first gap between rectangles
        // Find the first gap between obstacles on the ground
        float x = 4.0f; // Based on the fixed ground obstacles, adjust this value for desired gap position
        float y = 2.0f; // Place it above the ground block
        float size = 0.5f; // Size of the circle

        // Create the circle in the first gap
        createCircleObstacle(x, y, size);
    }

    private void createGroundObstacle(float x, float y, float width, float height) {
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

    private void createRandomShape(float x, float y, float size) {
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