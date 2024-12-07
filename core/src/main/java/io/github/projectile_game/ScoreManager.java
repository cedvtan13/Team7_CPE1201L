package io.github.projectile_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class ScoreManager {
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private Label scoreLabel1, scoreLabel2, winLabel;
    private TextButton menuButton;

    private Skin skin;
    private Game game;
    private GameUI gameUI;

    public ScoreManager(Skin skin, Game game, GameUI gameUI) {
        this.skin = skin;
        this.game = game;
        this.gameUI = gameUI;
    }

    public void updateScore(PlayerTurn currentPlayer, Body bodyA, Body bodyB, Ball ball1, Ball ball2) {
        if (currentPlayer == PlayerTurn.PLAYER1) {
            if (bodyA == ball2.getBody() || bodyB == ball2.getBody()) {
                scorePlayer2++;
                scoreLabel2.setText("Player 2 Score: " + scorePlayer2);
            }
        } else if (currentPlayer == PlayerTurn.PLAYER2) {
            if (bodyA == ball1.getBody() || bodyB == ball1.getBody()) {
                scorePlayer1++;
                scoreLabel1.setText("Player 1 Score: " + scorePlayer1);
            }
        }
        checkWinCondition();
    }

    public void setUpScoreLabels(Stage stage) {
        scoreLabel1 = new Label("Player 1 Score: 0", skin);
        scoreLabel1.setPosition(50, Gdx.graphics.getHeight() - 100);
        scoreLabel1.setAlignment(Align.left);
        stage.addActor(scoreLabel1);

        scoreLabel2 = new Label("Player 2 Score: 0", skin);
        scoreLabel2.setPosition(450, Gdx.graphics.getHeight() - 100);
        scoreLabel2.setAlignment(Align.left);
        stage.addActor(scoreLabel2);
    }

    private void checkWinCondition() {
        if (scorePlayer1 == 5) {
            displayWinMessage("Player 1 Wins!");
        } else if (scorePlayer2 == 5) {
            displayWinMessage("Player 2 Wins!");
        }
    }

    private void displayWinMessage(String message) {
        if (winLabel == null) {
            winLabel = new Label(message, skin);
            winLabel.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
            winLabel.setAlignment(Align.center);
            Gdx.app.postRunnable(() -> {
                Stage stage = scoreLabel1.getStage();
                if (stage != null) {
                    stage.addActor(winLabel);
                    showMenuOption(stage);
                }
            });
        }
    }

    private void showMenuOption(Stage stage) {
        menuButton = new TextButton("Return to Menu", skin);
        menuButton.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f - 50);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameUI.stopMusic();
                ((GameScreen) game.getScreen()).disposeMapResources();
                game.setScreen(new StartMenuScreen(game));
            }
        });

        stage.addActor(menuButton);
    }

    public void dispose() {
        //hello
        skin.dispose();
    }
}