package io.github.some_example_name.niveles;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import io.github.some_example_name.entidades.BolaEnemiga;
import io.github.some_example_name.entidades.Jugador;
import io.github.some_example_name.Main;
import io.github.some_example_name.screen.MenuScreen;

public class Nivel1 implements Screen {
    private Stage stage;
    private Skin skin;
    private TextButton menuButton;
    private Main game;
    private SpriteBatch batch;
    private Texture fondo;
    private Array<BolaEnemiga> bolas;
    private Jugador jugador;
    private float anchoPantalla, altoPantalla;
    private boolean jugadorMuerto;

    public Nivel1(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        fondo = new Texture("cancha.png");
        anchoPantalla = Gdx.graphics.getWidth();
        altoPantalla = Gdx.graphics.getHeight();
        jugador = new Jugador(anchoPantalla, altoPantalla);
        jugadorMuerto = false;
        bolas = new Array<>();
        for (int i = 0; i < 3; i++) {
            bolas.add(new BolaEnemiga(anchoPantalla, altoPantalla));
        }

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        menuButton = new TextButton("Menu", skin);
        menuButton.setSize(80, 40);
        menuButton.setPosition(20, altoPantalla - 90,10);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        stage.addActor(menuButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!jugadorMuerto) {
            jugador.actualizar(delta);
            for (BolaEnemiga bola : bolas) {
                bola.actualizar(delta);
                if (jugador.getHitbox().overlaps(bola.getHitbox())) {
                    jugadorMuerto = true;
                }
            }
            if (jugador.getHitbox().y > anchoPantalla - 50) {
                game.setScreen(new Nivel2(game));
            }
        }

        batch.begin();
        batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);
        for (BolaEnemiga bola : bolas) {
            bola.renderizar(batch);
        }
        jugador.renderizar(batch);
        batch.end();

        stage.act(delta);
        stage.draw();

        if (jugadorMuerto) {
            game.setScreen(new Nivel1(game));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        fondo.dispose();
        for (BolaEnemiga bola : bolas) bola.dispose();
        jugador.dispose();
        stage.dispose();
        skin.dispose();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}

