package io.github.projectile_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Align;

public class ScoreManager {
    private int scorePlayer1 = 0;
    private int scorePlayer2 = 0;
    private Label scoreLabel1, scoreLabel2, winLabel;

    private Skin skin;

    public ScoreManager(Skin skin) {
        this.skin = skin;
    }

    public void updateScore(PlayerTurn currentPlayer, Body bodyA, Body bodyB, Ball ball1, Ball ball2) {

        // Only award score if it's the current player's turn
        if (currentPlayer == PlayerTurn.PLAYER1) {
            if (bodyA == ball2.getBody() || bodyB == ball2.getBody()) {
                scorePlayer2++;  // Player 1 scores
                scoreLabel2.setText("Player 2 Score: " + scorePlayer2);
            }
        } else if (currentPlayer == PlayerTurn.PLAYER2) {
            if (bodyA == ball1.getBody() || bodyB == ball1.getBody()) {
                scorePlayer1++;  // Player 2 scores
                scoreLabel1.setText("Player 1 Score: " + scorePlayer1);
            }
        }
    }

    // Set up score labels
    public void setUpScoreLabels(Stage stage) {
        scoreLabel1 = new Label("Player 1 Score: 0", skin);
        scoreLabel1.setPosition(50, Gdx.graphics.getHeight() - 100);
        scoreLabel1.setAlignment(Align.left);
        stage.addActor(scoreLabel1);

        scoreLabel2 = new Label("Player 2 Score: 0", skin);
        scoreLabel2.setPosition(450, Gdx.graphics.getHeight() - 100);
        scoreLabel2.setAlignment(Align.left);
        stage.addActor(scoreLabel2);
       // winCondition(scorePlayer1, scorePlayer2, stage);
    }

    // Update score based on collision and current player
    public void winCondition(int scorePlayer1, int scorePlayer2, Stage stage){
        if(scorePlayer1 == 5){
            winLabel = new Label("Player 1 Wins!", skin);
            winLabel.setPosition(100, Gdx.graphics.getHeight() - 100);
            winLabel.setAlignment(Align.left);
            stage.addActor(winLabel);
        }
        else {
            winLabel = new Label("Player 2 Wins!", skin);
            winLabel.setPosition(100, Gdx.graphics.getHeight() - 100);
            winLabel.setAlignment(Align.left);
            stage.addActor(winLabel);
        }
    }

    public void dispose() {
        skin.dispose();
    }
}
