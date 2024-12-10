package io.github.projectile_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameUI {
    private Stage stage;
    private Skin skin;

    // Declare the buttons and text fields as instance variables
    public TextButton launchButton1;
    public TextButton launchButton2;
    public TextField speedField1, angleField1, speedField2, angleField2;

    private Music backgroundMusic; // Music variable for background music

    public GameUI(Stage stage, Skin skin) {
        this.stage = stage;
        this.skin = skin;
    }

    public void createUI() {
        // Initialize the UI elements and buttons
        speedField1 = new TextField("", skin);
        angleField1 = new TextField("", skin);
        speedField2 = new TextField("", skin);
        angleField2 = new TextField("", skin);

        // Set width, height, and position for TextField 1 (ball1)
        speedField1.setWidth(100);
        speedField1.setHeight(30);
        speedField1.setPosition(50, Gdx.graphics.getHeight() - 50);
        angleField1.setWidth(100);
        angleField1.setHeight(30);
        angleField1.setPosition(150, Gdx.graphics.getHeight() - 50);

        // Set width, height, and position for TextField 2 (ball2)
        speedField2.setWidth(100);
        speedField2.setHeight(30);
        speedField2.setPosition(450, Gdx.graphics.getHeight() - 50);
        angleField2.setWidth(100);
        angleField2.setHeight(30);
        angleField2.setPosition(550, Gdx.graphics.getHeight() - 50);

        // Set placeholder text to indicate input fields for Speed and Angle
        speedField1.setMessageText("Speed");
        angleField1.setMessageText("Angle");
        speedField2.setMessageText("Speed");
        angleField2.setMessageText("Angle");

        // Add TextFields to the stage
        stage.addActor(speedField1);
        stage.addActor(angleField1);
        stage.addActor(speedField2);
        stage.addActor(angleField2);

        // Initialize the launch buttons
        this.launchButton1 = new TextButton("Launch Ball 1", skin); // Set instance variable
        this.launchButton2 = new TextButton("Launch Ball 2", skin); // Set instance variable

        // Set the positions for buttons
        this.launchButton1.setPosition(250, Gdx.graphics.getHeight() - 50);
        this.launchButton2.setPosition(650, Gdx.graphics.getHeight() - 50);

        // Add buttons to the stage
        stage.addActor(this.launchButton1);
        stage.addActor(this.launchButton2);

        // Initialize and play background music
        initializeMusic();
    }


    private void initializeMusic() {
        try {
            // Load the background music file
            backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Pokemon.mp3"));
            backgroundMusic.setLooping(true); // Set music to loop
            backgroundMusic.setVolume(0.3f); // Adjust volume to a reasonable level
            backgroundMusic.play(); // Start playing the music
            System.out.println("Background music is playing.");
        } catch (Exception e) {
            System.err.println("Error loading or playing music: " + e.getMessage());
            e.printStackTrace(); // Log the error for debugging
        }
    }

    public void stopMusic() {
        backgroundMusic.stop();
    }

    public void addListenerToButton(TextButton button, Runnable action) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
    }

    // Getter methods to access buttons and text fields from other classes
    public TextButton getLaunchButton1() {
        return launchButton1;
    }

    public TextButton getLaunchButton2() {
        return launchButton2;
    }

    public TextField getSpeedField1() {
        return speedField1;
    }

    public TextField getAngleField1() {
        return angleField1;
    }

    public TextField getSpeedField2() {
        return speedField2;
    }

    public TextField getAngleField2() {
        return angleField2;
    }

    // Add this getter method for Stage to resolve the issue
    public Stage getStage() {
        return stage;
    }


    public void dispose() {
        // Stop and dispose of music when no longer needed
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose(); // Dispose the music resource when done
        }
        stage.dispose();
        skin.dispose();
    }
}