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
            backgroundTexture = new Texture("background2.png");
            backgroundSprite = new Sprite(backgroundTexture);

            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Ruby.mp3"));
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(0.2f);
            backgroundMusic.play();

            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);

            skin = new Skin(Gdx.files.internal("uiskin.json"));

            TextButton triangleMapButton = new TextButton("Triangle Map", skin);
            triangleMapButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game, new TriangleMap(new World(new Vector2(0, -9.8f), true))));
                }
            });

            TextButton randomObstaclesMapButton = new TextButton("Random Obstacles Map", skin);
            randomObstaclesMapButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game, new RandomObstaclesMap(new World(new Vector2(0, -9.8f), true))));
                }
            });

            TextButton rotatingPlatformsMapButton = new TextButton("Rotating Platforms Map", skin);
            rotatingPlatformsMapButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game, new RotatingPlatformsMap(new World(new Vector2(0, -9.8f), true))));
                }
            });

            TextButton structuredObstaclesMapButton = new TextButton("Structured Obstacles Map", skin);
            structuredObstaclesMapButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game, new StructuredObstaclesMap(new World(new Vector2(0, -9.8f), true))));
                }
            });

            TextButton backButton = new TextButton("Back", skin);
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new StartMenuScreen(game));
                }
            });

            Table table = new Table();
            table.setFillParent(true);
            table.center();
            table.add(triangleMapButton).pad(10).width(250).height(50);
            table.row();
            table.add(randomObstaclesMapButton).pad(10).width(250).height(50);
            table.row();
            table.add(rotatingPlatformsMapButton).pad(10).width(250).height(50);
            table.row();
            table.add(structuredObstaclesMapButton).pad(10).width(250).height(50);
            table.row();
            table.add(backButton).pad(10).width(200).height(50);

            stage.addActor(table);

        } catch (Exception e) {
            System.err.println("Error in ChooseMapScreen show(): " + e.getMessage());
            Gdx.app.error("ChooseMapScreen", "Error in ChooseMapScreen show()", e);
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
            Gdx.app.error("ChooseMapScreen", "Error in ChooseMapScreen render()", e);
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
            Gdx.app.error("ChooseMapScreen", "Error in ChooseMapScreen dispose()", e);
        }
    }

    @Override
    public void resize(int width, int height) {
        try {
            stage.getViewport().update(width, height, true);
            backgroundSprite.setSize(width, height);
        } catch (Exception e) {
            System.err.println("Error in ChooseMapScreen resize(): " + e.getMessage());
            Gdx.app.error("ChooseMapScreen", "Error in ChooseMapScreen resize()", e);
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
            Gdx.app.error("ChooseMapScreen", "Error in ChooseMapScreen hide()", e);
        }
    }
}