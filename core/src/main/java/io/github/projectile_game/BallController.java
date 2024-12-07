package io.github.projectile_game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BallController {
    private Ball ball;
    private Texture ballTexture;

    public BallController(Ball ball) {
        this.ball = ball;
    }

    public void applyThrow(World world, float speed, float angleDegrees) {
        // Ensure the ball's body is created first
        if (ball.getBody() == null) {
            ball.createBall(world, 1, 1, 0.075f); // Creating the ball's body
        }

        float angleRadians = (float) Math.toRadians(angleDegrees);
        float velocityX = speed * (float) Math.cos(angleRadians);
        float velocityY = speed * (float) Math.sin(angleRadians);

        // Apply the throw
        ball.getBody().setLinearVelocity(velocityX, velocityY);
    }

    public void resetBallPosition(float x, float y) {
        if (ball.getBody() != null) {
            // Set the ball's position to the specified (x, y) and set rotation (angle)
            ball.getBody().setTransform(x, y, 0);

            // Reset linear velocity
            ball.getBody().setLinearVelocity(0, 0);
        }
    }


    public Texture getBallTexture() {
        if (ballTexture == null) {
            ballTexture = new Texture("ball.png");
        }
        return ballTexture;
    }

    public void dispose(){
        ballTexture.dispose();
    }
}

