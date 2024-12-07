package io.github.projectile_game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Random;

public class PhysicsWorld {
    public World world;
    private Body middleWall;
    private Random random = new Random();
    public static final float DENSITY = 1.0f;
    public static final float FRICTION = 0.3f;
    public static final float RESTITUTION = 0.5f;

    public PhysicsWorld(World world) {
        this.world = world;
        createWallsAndGround();
        createMiddleWall(); // Initialize the middle wall when the world is created
    }

    private void createWallsAndGround() {
        // Create ground, ceiling, and side walls as needed
        float wallThickness = 0.1f;

        // Create ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(4, 0);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(4, wallThickness); // Width 8 units, thickness for height

        FixtureDef groundFixture = new FixtureDef();
        groundFixture.shape = groundShape;

        Body groundBody = world.createBody(groundBodyDef);
        groundBody.createFixture(groundFixture);
        groundShape.dispose();

        // Create ceiling
        BodyDef ceilingBodyDef = new BodyDef();
        ceilingBodyDef.position.set(4, 6); // Center at x = 4 (middle of the screen), y = 6 (top of the screen)

        PolygonShape ceilingShape = new PolygonShape();
        ceilingShape.setAsBox(4, wallThickness); // Width 8 units, thickness for height

        FixtureDef ceilingFixture = new FixtureDef();
        ceilingFixture.shape = ceilingShape;

        Body ceilingBody = world.createBody(ceilingBodyDef);
        ceilingBody.createFixture(ceilingFixture);
        ceilingShape.dispose();

        // Create left wall
        BodyDef leftWallBodyDef = new BodyDef();
        leftWallBodyDef.position.set(0, 3); // Center at x = 0 (left of the screen), y = 3

        PolygonShape leftWallShape = new PolygonShape();
        leftWallShape.setAsBox(wallThickness, 3); // Thickness for width, height 6 units

        FixtureDef leftWallFixture = new FixtureDef();
        leftWallFixture.shape = leftWallShape;

        Body leftWallBody = world.createBody(leftWallBodyDef);
        leftWallBody.createFixture(leftWallFixture);
        leftWallShape.dispose();

        // Create right wall
        BodyDef rightWallBodyDef = new BodyDef();
        rightWallBodyDef.position.set(8, 3); // Center at x = 8 (right of the screen), y = 3

        PolygonShape rightWallShape = new PolygonShape();
        rightWallShape.setAsBox(wallThickness, 3); // Thickness for width, height 6 units

        FixtureDef rightWallFixture = new FixtureDef();
        rightWallFixture.shape = rightWallShape;

        Body rightWallBody = world.createBody(rightWallBodyDef);
        rightWallBody.createFixture(rightWallFixture);
        rightWallShape.dispose();
    }

    public void applyThrow(Body body, float speed, float angleDegrees) {
        float angleRadians = (float) Math.toRadians(angleDegrees);
        float velocityX = speed * (float) Math.cos(angleRadians);
        float velocityY = speed * (float) Math.sin(angleRadians);

        body.setLinearVelocity(velocityX, velocityY);
    }

    // Middle Wall Logic
    public void createMiddleWall() {
        float randomHeight = 2 + random.nextFloat() * 2; // Random height between 2 and 4 units

        // Define the middle wall body and shape
        BodyDef wallDef = new BodyDef();
        wallDef.position.set(4, randomHeight / 2); // Center the wall in the middle of the screen

        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(0.1f, randomHeight / 2); // Width 0.2 units, height based on random height

        FixtureDef wallFixture = new FixtureDef();
        wallFixture.shape = wallShape;
        wallFixture.density = 1.0f;

        middleWall = world.createBody(wallDef);
        middleWall.createFixture(wallFixture);
        wallShape.dispose();
    }

    public void recreateMiddleWall() {
        // Remove the existing wall
        if (middleWall != null) {
            world.destroyBody(middleWall);
        }
        // Create a new wall with a random position
        createMiddleWall();
    }
}
