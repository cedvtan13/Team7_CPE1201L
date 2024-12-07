package io.github.projectile_game;

import com.badlogic.gdx.physics.box2d.*;

public class TunnelMap extends PhysicsWorld {
    public TunnelMap(World world) {
        super(world);
        createMovingPlatforms();
    }

    @Override
    public void createMiddleWall() {
        // Implementation for middle wall (if needed)
    }

    private void createMovingPlatforms() {
        createMovingPlatform(2.5f, 1.5f, 0.5f, 0.2f, 3f);
        createMovingPlatform(5.5f, 3f, 0.5f, 0.2f, -2f);
    }

    private void createMovingPlatform(float x, float y, float width, float height, float speed) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x, y);
        bodyDef.linearVelocity.set(speed, 0);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        // Add a sensor fixture to detect edges
        shape.setAsBox(width / 2 + 0.1f, height / 2 + 0.1f); // Slightly larger than the platform
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("edgeSensor");

        shape.dispose();
    }
}
