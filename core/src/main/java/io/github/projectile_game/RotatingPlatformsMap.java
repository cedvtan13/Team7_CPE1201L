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
        //Right side
        createRotatingPlatform(1f, 3.5f, 0.1f, 1.2f, 3f);
        createRotatingPlatform(2.5f, 2f, 0.1f, .7f, -1f);

        //Left side
        createRotatingPlatform(7f, 3.5f, 0.1f, 1.2f, -3f);
        createRotatingPlatform(5.5f, 2f, 0.1f, .7f, 1f);

        //To stop for easy points
        createRotatingPlatform(4f, 2f, 0.1f, 2f, 2f);
        createRotatingPlatform(4f, 2f, 0.1f, 2f, -2f);
        createRotatingPlatform(4.6f, 4.5f, .3f, 1.2f, 2f);
        createRotatingPlatform(3.4f, 4.5f, .3f, 1.2f, -2f);


    }

    private void createRotatingPlatform(float x, float y, float width, float height, float angularSpeed) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody; // Use KinematicBody for rotation
        bodyDef.position.set(x, y);
        bodyDef.angularVelocity = angularSpeed; // Set rotation speed

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = DENSITY;
        fixtureDef.friction = FRICTION;
        fixtureDef.restitution = RESTITUTION;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();
    }


}
