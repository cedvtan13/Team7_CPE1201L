package io.github.projectile_game;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;

public class TurnManager {
    private PlayerTurn currentPlayer;

    public TurnManager() {
        this.currentPlayer = PlayerTurn.PLAYER1;  // Start with Player 1
        Gdx.app.log("Current Player", "Current Player: " + currentPlayer);  // Log the current player
    }

    // Switch between Player 1 and Player 2
    public void switchPlayerTurn() {
        if (currentPlayer == PlayerTurn.PLAYER1) {
            currentPlayer = PlayerTurn.PLAYER2;
        } else {
            currentPlayer = PlayerTurn.PLAYER1;
        }
    }

    // Update button states based on the current player
    public void updateButtonStates(GameUI gameUI) {
        if (currentPlayer == PlayerTurn.PLAYER1) {
            // Player 1's button is clickable, Player 2's is disabled
            gameUI.getLaunchButton1().setTouchable(Touchable.enabled);
            gameUI.getLaunchButton2().setTouchable(Touchable.disabled);
        } else {
            // Player 2's button is clickable, Player 1's is disabled
            gameUI.getLaunchButton1().setTouchable(Touchable.disabled);
            gameUI.getLaunchButton2().setTouchable(Touchable.enabled);
        }
    }

    public void launchBall(BallController ballController, TextField speedField, TextField angleField, GameScreen gameScreen) {
        try {
            // Get the speed and angle from the UI TextFields
            float speed = Float.parseFloat(speedField.getText());
            float angle = Float.parseFloat(angleField.getText());

            // Call applyThrow to apply physics to the ball
            ballController.applyThrow(gameScreen.getWorld(), speed, angle);

            // After launching, immediately switch the turn and update button states
            switchPlayerTurn();
            updateButtonStates(gameScreen.getGameUI());  // Ensure buttons are updated after turn switch
        } catch (NumberFormatException e) {
            Gdx.app.log("Launch Error", "Invalid input for ball launch");
        }
    }

    // Get current player
    public PlayerTurn getCurrentPlayer() {
        return currentPlayer;
    }
}
