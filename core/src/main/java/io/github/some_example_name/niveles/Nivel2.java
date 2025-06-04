package io.github.some_example_name.niveles;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.entidades.BolaEnemiga;
import io.github.some_example_name.entidades.Jugador;
import io.github.some_example_name.Main;

public class Nivel2 implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture fondo;
    private Array<BolaEnemiga> bolas;
    private Jugador jugador;
    private float anchoPantalla, altoPantalla;
    private boolean jugadorMuerto;

    public Nivel2(Main game) {
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
        for (int i = 0; i < 10; i++) {
            BolaEnemiga bola = new BolaEnemiga(anchoPantalla, altoPantalla);
            bola.setVelocidadExtra(1.5f); // Aumenta velocidad
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
            if (jugador.getHitbox().y > altoPantalla - 50) {
                // Aquí podrías mostrar una pantalla de victoria
                Gdx.app.exit();
            }
        }

        batch.begin();
        batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);
        for (BolaEnemiga bola : bolas) {
            bola.renderizar(batch);
        }
        jugador.renderizar(batch);
        batch.end();

        if (jugadorMuerto) {
            game.setScreen(new Nivel2(game));
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

