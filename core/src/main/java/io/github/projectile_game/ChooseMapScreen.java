package io.github.projectile_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class ChooseMapScreen implements Screen {
    private final Game game;
    private Stage stage;
    private Skin skin;
    private SpriteBatch spriteBatch;
    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Music backgroundMusic;

    public ChooseMapScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        try {
            spriteBatch = new SpriteBatch();
            backgroundTexture = new Texture("background2.png"); // Ensure the background image is correctly loaded
            backgroundSprite = new Sprite(backgroundTexture);

            // Initialize and start music
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Ruby.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
            backgroundMusic.play();

            // Initialize the stage and input processor
            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);  // Set the input processor to capture button clicks

            // Load the skin (UI elements)
            skin = new Skin(Gdx.files.internal("uiskin.json"));

            // Create buttons and add listeners
            TextButton triangleMapButton = new TextButton("Triangle Map", skin);
            triangleMapButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        System.out.println("Triangle Map button clicked");
                        game.setScreen(new GameScreen(game, new TriangleMap(new World(new Vector2(0, -9.8f), true))));
                    } catch (Exception e) {
                        System.err.println("Error navigating to TriangleMap: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

            TextButton randomObstaclesMapButton = new TextButton("Random Obstacles Map", skin);
            randomObstaclesMapButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        System.out.println("Random Obstacles Map button clicked");
                        game.setScreen(new GameScreen(game, new RandomObstaclesMap(new World(new Vector2(0, -9.8f), true))));
                    } catch (Exception e) {
                        System.err.println("Error navigating to RandomObstaclesMap: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

            TextButton rotatingPlatformsMapButton = new TextButton("Rotating Platforms Map", skin);
            rotatingPlatformsMapButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        System.out.println("Rotating Platforms Map button clicked");
                        game.setScreen(new GameScreen(game, new RotatingPlatformsMap(new World(new Vector2(0, -9.8f), true))));
                    } catch (Exception e) {
                        System.err.println("Error navigating to RotatingPlatformsMap: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

            TextButton backButton = new TextButton("Back", skin);
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        System.out.println("Back button clicked");
                        game.setScreen(new StartMenuScreen(game));
                    } catch (Exception e) {
                        System.err.println("Error navigating to StartMenuScreen: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

            // Arrange buttons in a table
            Table table = new Table();
            table.setFillParent(true);
            table.center();
            table.add(triangleMapButton).pad(10).width(250).height(50);
            table.row();
            table.add(randomObstaclesMapButton).pad(10).width(250).height(50);
            table.row();
            table.add(rotatingPlatformsMapButton).pad(10).width(250).height(50);
            table.row();
            table.add(backButton).pad(10).width(200).height(50);

            stage.addActor(table);  // Add the table to the stage

        } catch (Exception e) {
            System.err.println("Error in ChooseMapScreen show(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        try {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            spriteBatch.begin();
            backgroundSprite.draw(spriteBatch);
            spriteBatch.end();

            stage.act(delta);
            stage.draw();
        } catch (Exception e) {
            System.err.println("Error in ChooseMapScreen render(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        try {
            stage.dispose();
            skin.dispose();
            spriteBatch.dispose();
            backgroundTexture.dispose();
            backgroundMusic.dispose();
        } catch (Exception e) {
            System.err.println("Error in ChooseMapScreen dispose(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void resize(int width, int height) {
        try {
            stage.getViewport().update(width, height, true);  // Make sure the viewport scales properly
            backgroundSprite.setSize(width, height);  // Resize the background to fill the screen
        } catch (Exception e) {
            System.err.println("Error in ChooseMapScreen resize(): " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        try {
            if (backgroundMusic.isPlaying()) {
                backgroundMusic.stop();
            }
        } catch (Exception e) {
            System.err.println("Error in ChooseMapScreen hide(): " + e.getMessage());
            e.printStackTrace();
        }
    }
}