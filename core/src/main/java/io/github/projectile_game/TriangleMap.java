package io.github.projectile_game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TriangleMap extends PhysicsWorld {
    public TriangleMap(World world) {
        super(world);

        createTriangleObstacle(new Vector2(4, 3), new Vector2(2, 2));
        createTriangleObstacle(new Vector2(6.15f, 0.1f), new Vector2(2, .5f));
        createTriangleObstacle(new Vector2(1.8f, 0.1f), new Vector2(2, .5f));
        createTriangleObstacle(new Vector2(5.7f, 2), new Vector2(2, -2));
        createTriangleObstacle(new Vector2(2.3f, 2), new Vector2(2, -2));
        createTriangleObstacle(new Vector2(4, 5.9f), new Vector2(1.5f, -2));
        createTriangleObstacle(new Vector2(4, 0.1f), new Vector2(0.5f,4));
        createTriangleObstacle(new Vector2(7.2f, 3.5f), new Vector2(2, -2));
        createTriangleObstacle(new Vector2(0.8f, 3.5f), new Vector2(2, -2));

    }

    @Override
    public void createMiddleWall() {
        // Left empty so there is no middle wall.
    }

    private void createTriangleObstacle(Vector2 position, Vector2 size) {
        // Define a body definition for the triangle
        BodyDef triangleBodyDef = new BodyDef();
        triangleBodyDef.type = BodyDef.BodyType.StaticBody; // Obstacles are usually static
        triangleBodyDef.position.set(position); // Set the position of the triangle

        // Define the triangle shape
        PolygonShape triangleShape = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-size.x / 4, 0); // Bottom-left corner
        vertices[1] = new Vector2(size.x / 4, 0);  // Bottom-right corner
        vertices[2] = new Vector2(0, size.y / 2);      // Top corner
        triangleShape.set(vertices);

        // Define the fixture for the triangle
        FixtureDef triangleFixtureDef = new FixtureDef();
        triangleFixtureDef.shape = triangleShape;
        triangleFixtureDef.density = DENSITY; // Density doesn't matter much for static bodies
        triangleFixtureDef.friction = FRICTION; // Friction for interaction with other objects
        triangleFixtureDef.restitution = RESTITUTION; // Bounciness

        // Create the body in the world and attach the fixture
        Body triangleBody = world.createBody(triangleBodyDef);
        triangleBody.createFixture(triangleFixtureDef);

        // Dispose the shape to avoid memory leaks
        triangleShape.dispose();
    }
}
