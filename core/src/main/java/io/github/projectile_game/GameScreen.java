package io.github.projectile_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

class GameScreen implements Screen, ContactListener {
    private Game game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Ball ball1, ball2;
    private PhysicsWorld physicsWorld;
    private GameUI gameUI;
    private ScoreManager scoreManager;
    private TurnManager turnManager;
    private BallController ballController1, ballController2;
    private Stage stage; // Stage is a member variable
    private Stage pauseStage;
    private boolean paused;
    private Skin skin;

    private PlayerTurn currentPlayer = PlayerTurn.PLAYER1;
    private final List<Runnable> tasksQueue = new ArrayList<>();
    private Texture backgroundTexture;
    private Sprite backgroundSprite;

    public GameScreen(Game game, PhysicsWorld physicsWorld) {
        this.game = game;
        this.physicsWorld = physicsWorld;
        this.stage = new Stage(); // Initialize stage in the constructor
        this.pauseStage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.paused = false;
        createPauseMenu();
    }

    private void createPauseMenu() {
        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                paused = false;
                Gdx.input.setInputProcessor(stage);
            }
        });

        TextButton menuButton = new TextButton("Return to Menu", skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StartMenuScreen(game));
            }
        });

        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(resumeButton).pad(10).width(200).height(50);
        table.row();
        table.add(menuButton).pad(10).width(200).height(50);
        table.row();
        table.add(quitButton).pad(10).width(200).height(50);

        pauseStage.addActor(table);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background3.png");
        backgroundSprite = new Sprite(backgroundTexture);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 8, 6);
        camera.position.set(4, 3, 0);
        camera.update();

        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(this);
        debugRenderer = new Box2DDebugRenderer();

        if (physicsWorld instanceof TriangleMap) {
            this.physicsWorld = new TriangleMap(world);
        } else if (physicsWorld instanceof RandomObstaclesMap) {
            this.physicsWorld = new RandomObstaclesMap(world);
        } else if (physicsWorld instanceof RotatingPlatformsMap) {
            this.physicsWorld = new RotatingPlatformsMap(world);
        } else if (physicsWorld instanceof StructuredObstaclesMap) {
            this.physicsWorld = new StructuredObstaclesMap(world);
        } else {
            this.physicsWorld = new PhysicsWorld(world);
        }

        turnManager = new TurnManager();
        world.setContactListener(this);

        ball1 = new Ball();
        ballController1 = new BallController(ball1);
        ball2 = new Ball();
        ballController2 = new BallController(ball2);

        ball1.createBall(world, 1, 1, 0.075f);
        ball2.createBall(world, 7, 1, 0.075f);

        Gdx.input.setInputProcessor(stage);
        gameUI = new GameUI(stage, skin); // Initialize gameUI with the stage
        scoreManager = new ScoreManager(skin);

        gameUI.createUI();
        gameUI.addListenerToButton(gameUI.getLaunchButton1(),
            () -> turnManager.launchBall(ballController1, gameUI.getSpeedField1(), gameUI.getAngleField1(), this));
        gameUI.addListenerToButton(gameUI.getLaunchButton2(),
            () -> turnManager.launchBall(ballController2, gameUI.getSpeedField2(), gameUI.getAngleField2(), this));

        scoreManager.setUpScoreLabels(stage);
        turnManager.updateButtonStates(gameUI);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
            if (paused) {
                Gdx.input.setInputProcessor(pauseStage);
            } else {
                Gdx.input.setInputProcessor(stage);
            }
        }

        if (paused) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            pauseStage.act(delta);
            pauseStage.draw();
            return;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 6, 2);

        if (!tasksQueue.isEmpty()) {
            for (Runnable task : tasksQueue) {
                task.run();
            }
            tasksQueue.clear();
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        backgroundSprite.draw(batch);
        batch.draw(ballController1.getBallTexture(), ball1.getBody().getPosition().x - 0.1f,
            ball1.getBody().getPosition().y - 0.1f, 0.2f, 0.2f);
        batch.draw(ballController2.getBallTexture(), ball2.getBody().getPosition().x - 0.1f,
            ball2.getBody().getPosition().y - 0.1f, 0.2f, 0.2f);
        batch.end();

        debugRenderer.render(world, camera.combined);
        gameUI.getStage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        gameUI.getStage().draw();
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        Gdx.app.log("Collision", "Body A: " + bodyA + ", Body B: " + bodyB);
        Gdx.app.log("Current Player", "Current Player: " + turnManager.getCurrentPlayer());  // Log the current player

        // Detect collision between the two balls and update the score only if it's the current player's turn
        if ((bodyA == ball1.getBody() && bodyB == ball2.getBody()) || (bodyA == ball2.getBody() && bodyB == ball1.getBody())) {
            // Update the score only for the current player
            scoreManager.updateScore(turnManager.getCurrentPlayer(), bodyA, bodyB, ball1, ball2);

            tasksQueue.add(() -> {
                ballController1.resetBallPosition(1, 1);
                ballController2.resetBallPosition(7, 1);
                physicsWorld.recreateMiddleWall();
            });
        }
    }

    public World getWorld() {
        return world;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    @Override
    public void endContact(Contact contact) {}
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    @Override
    public void resize(int width, int height) {
        gameUI.getStage().getViewport().update(width, height, true); // Access stage via gameUI
        backgroundSprite.setSize(width, height);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        gameUI.dispose();
        scoreManager.dispose();
        backgroundTexture.dispose();
        pauseStage.dispose();
        skin.dispose();
    }
}