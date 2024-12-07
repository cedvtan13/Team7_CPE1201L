package io.github.projectile_game;

import com.badlogic.gdx.physics.box2d.*;

public class RotatingPlatformsMap extends PhysicsWorld {
    public RotatingPlatformsMap(World world) {
        super(world);
        createRotatingPlatforms();
    }

    @Override
    public void createMiddleWall() {
        // Implementation for middle wall (if needed)
    }

    private void createRotatingPlatforms() {
        createRotatingPlatform(2.5f, 2f, 0.5f, 3f);
        createRotatingPlatform(5.5f, 2f, 0.5f, -2f);
    }

    private void createRotatingPlatform(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody; // Use KinematicBody for rotation
        bodyDef.position.set(x, y);
        bodyDef.angularVelocity = 2f; // Set rotation speed

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
}
