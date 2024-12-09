package io.github.projectile_game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball {
    private Body body;

    // Ensure the ball body is created only once
    public void createBall(World world, float x, float y, float radius) {
        // Only create the body if it doesn't already exist
        if (body == null) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(x, y);
            bodyDef.linearDamping = 1.5f; // Adding linear damping to slow down the ball over time

            CircleShape shape = new CircleShape();
            shape.setRadius(radius);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1.0f;
            fixtureDef.friction = 1.0f; // High friction to slow down balls after collision
            fixtureDef.restitution = .9f; // Bounciness

            // Create the body in the world and attach the fixture
            body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);

            shape.dispose(); // Dispose shape as it's no longer needed after creating the body
        }
    }

    // Return the body, but ensure it is never null
    public Body getBody() {
        return body;
    }
}
