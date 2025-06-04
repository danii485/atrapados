package io.github.some_example_name.niveles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.Main;
import io.github.some_example_name.entidades.BolaEnemiga;
import io.github.some_example_name.entidades.Jugador;
import io.github.some_example_name.screen.Alerta;

import javax.swing.*;


public class Nivel3 implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture fondo;
    private Array<BolaEnemiga> bolas;
    private Jugador jugador;
    private float anchoPantalla, altoPantalla;
    private boolean jugadorMuerto;
    private boolean mostrandoMensaje;
    private boolean mensajeMostrado;


    public Nivel3(Main game) {
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
        for (int i = 0; i < 7; i++) {
            BolaEnemiga bola = new BolaEnemiga(anchoPantalla, altoPantalla);
            bola.setVelocidadExtra(1.75f);
            bolas.add(bola);
        }
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
            if (jugador.getHitbox().x > anchoPantalla-40) {
                mostrandoMensaje = true;
            }
        }

        batch.begin();

        batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);
        for (BolaEnemiga bola : bolas) {
            bola.renderizar(batch);
        }
        jugador.renderizar(batch);
        if (mostrandoMensaje) {
            game.getFont().draw(batch, "Completaste el juego !! Presiona ENTER", anchoPantalla / 2 - 100, altoPantalla / 2);
            if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
                game.setScreen(new Nivel3(game));
            }
        }

        batch.end();

        if (jugadorMuerto) {
            game.setScreen(new Nivel1(game));
        }
    }

    @Override public void dispose() {
        batch.dispose();
        fondo.dispose();
        for (BolaEnemiga bola : bolas) bola.dispose();
        jugador.dispose();
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
