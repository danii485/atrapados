package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import com.badlogic.gdx.graphics.Color;
import io.github.some_example_name.niveles.Nivel1;

public class Main extends Game {
    private BitmapFont font;

    public BitmapFont getFont() {
        return font;
    }

    @Override
    public void create() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/Roboto-Bold.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40; // Aumenta o disminuye el tamaño según lo necesites
        parameter.color = Color.ORANGE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor = new Color(0, 0, 0, 0.5f); // sombra sutil

        font = generator.generateFont(parameter); // genera la fuente
        generator.dispose();

        setScreen(new Nivel1(this)); // o Nivel2 si quieres probar directo
    }

    @Override
    public void dispose() {
        font.dispose();
    }
}


