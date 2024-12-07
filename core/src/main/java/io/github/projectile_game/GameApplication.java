package io.github.projectile_game;

import com.badlogic.gdx.Game;

public class GameApplication extends Game {

    @Override
    public void create() {
        // Start the game by showing the StartMenuScreen
        setScreen(new StartMenuScreen(this));
    }

    @Override
    public void render() {
        // Delegate the rendering to the current screen
        super.render();
    }

    @Override
    public void dispose() {
        // Dispose of the current screen when the game is closed
        getScreen().dispose();
    }
}
