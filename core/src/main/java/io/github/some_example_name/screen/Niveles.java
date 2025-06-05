package io.github.some_example_name.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.some_example_name.Main;
import io.github.some_example_name.niveles.Nivel1;
import io.github.some_example_name.niveles.Nivel2;

public class Niveles implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private Texture fondo;
    private SpriteBatch batch;

    public Niveles(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        fondo = new Texture("estadio.png");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton nivel1Button = new TextButton("Nivel 1", skin);
        TextButton nivel2Button = new TextButton("Nivel 2", skin);
        TextButton volverButton = new TextButton("Volver", skin);

        nivel1Button.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Nivel1(game));
            }
        });

        nivel2Button.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Nivel2(game));
            }
        });

        volverButton.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.center();

        table.add(nivel1Button).width(200).height(60).pad(10).row();
        table.add(nivel2Button).width(200).height(60).pad(10).row();
        table.add(volverButton).width(200).height(60).pad(10).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
        fondo.dispose();
        batch.dispose();
    }
}

