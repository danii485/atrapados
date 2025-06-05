package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import io.github.some_example_name.screen.MenuScreen;

public class Main extends Game {

    @Override
    public void create() {
        // Usa el método setScreen() de Game directamente
        setScreen(new MenuScreen(this));
    }

    // No necesitas redefinir setScreen(). Game ya lo tiene.
}



