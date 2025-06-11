package io.github.some_example_name.niveles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.some_example_name.Main;
import io.github.some_example_name.entidades.BolaEnemiga;
import io.github.some_example_name.entidades.Jugador;
import io.github.some_example_name.entidades.Jugador2;

public class Nivel1Multijugador implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture fondo;
    private Array<BolaEnemiga> bolas;
    private Jugador jugador;
    private Jugador2 jugador2;
    private float anchoPantalla, altoPantalla;
    private boolean jugador1Muerto;
    private boolean jugador2Muerto;
    private boolean mostrandoMensaje;
    private boolean juegoPausado;

    public Nivel1Multijugador(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        juegoPausado = false;
        batch = new SpriteBatch();
        fondo = new Texture("cancha.png");
        anchoPantalla = Gdx.graphics.getWidth();
        altoPantalla = Gdx.graphics.getHeight();

        jugador = new Jugador(anchoPantalla, altoPantalla);    // Control con WASD
        jugador2 = new Jugador2(anchoPantalla, altoPantalla);  // Control con flechas
        jugador2.getHitbox().x = 100; // Para que no se superpongan

        jugador1Muerto = false;
        jugador2Muerto = false;
        mostrandoMensaje = false;

        bolas = new Array<>();
        for (int i = 0; i < 3; i++) {
            BolaEnemiga bola = new BolaEnemiga(anchoPantalla, altoPantalla);
            bola.setVelocidadExtra(1.25f);
            bolas.add(bola);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!juegoPausado) {
            jugador.actualizar(delta);
            jugador2.actualizar(delta);

            for (BolaEnemiga bola : bolas) {
                bola.actualizar(delta);

                if (jugador.getHitbox().overlaps(bola.getHitbox())) {
                    jugador1Muerto = true;
                    juegoPausado = true;
                }

                if (jugador2.getHitbox().overlaps(bola.getHitbox())) {
                    jugador2Muerto = true;
                    juegoPausado = true;
                }
            }

            boolean jugador1EnMeta = (jugador.getHitbox().x > anchoPantalla - 40) &&
                (jugador.getHitbox().y > (altoPantalla / 2) - 50 && jugador.getHitbox().y < (altoPantalla / 2) + 20);
            boolean jugador2EnMeta = (jugador2.getHitbox().x > anchoPantalla - 40) &&
                (jugador2.getHitbox().y > (altoPantalla / 2) - 50 && jugador2.getHitbox().y < (altoPantalla / 2) + 20);

            if (jugador1EnMeta && jugador2EnMeta) {
                mostrandoMensaje = true;
                juegoPausado = true;
            }
        }

        batch.begin();

        batch.draw(fondo, 0, 0, anchoPantalla, altoPantalla);

        for (BolaEnemiga bola : bolas) {
            bola.renderizar(batch);
        }

        jugador.renderizar(batch);
        jugador2.renderizar(batch);

        if (mostrandoMensaje) {
            game.getFont().setColor(Color.BLACK);
            game.getFont().draw(batch,
                "¡Pasaron al Nivel 2! Presionen ENTER",
                anchoPantalla / 2 - 100, altoPantalla / 2
            );

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                game.addScore(100); // +100 al pasar de nivel
                game.setScreen(new Nivel2(game)); // Nivel siguiente
            }
        }

        if (jugador1Muerto || jugador2Muerto) {
            game.getFont().setColor(Color.BLACK);
            game.getFont().draw(batch,
                "¡Perdieron! Presionen ENTER para reiniciar",
                anchoPantalla / 2 - 150, altoPantalla / 2 + 25
            );

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                game.addScore(-50); // −50 al morir
                game.setScreen(new Nivel1Multijugador(game));
            }
        }

        game.getFont().setColor(Color.WHITE);
        game.getFont().draw(batch,
            "Score: " + game.getScore(),
            10, altoPantalla - 10
        );

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        fondo.dispose();
        jugador.dispose();
        jugador2.dispose();
        for (BolaEnemiga bola : bolas) {
            bola.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}
