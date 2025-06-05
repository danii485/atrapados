package io.github.some_example_name.entidades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Jugador {
    private Texture textura;
    private float x, y;
    private float velocidad;
    private static final float ANCHO = 64;
    private static final float ALTO = 64;
    private float pantallaAncho, pantallaAlto;
    private Rectangle hitbox;

    public Jugador(float pantallaAncho, float pantallaAlto) {
        this.textura = new Texture("personaje.png");
        this.pantallaAncho = pantallaAncho;
        this.pantallaAlto = pantallaAlto;
        this.x = (pantallaAncho - ANCHO) / 2f;
        this.y = 0;
        this.velocidad = 200;
        this.hitbox = new Rectangle(x, y, ANCHO, ALTO);
    }

    public void actualizar(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= velocidad * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += velocidad * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += velocidad * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= velocidad * delta;
        }

        x = Math.max(0, Math.min(pantallaAncho - ANCHO, x));
        y = Math.max(0, Math.min(pantallaAlto - ALTO, y));

        hitbox.setPosition(x, y);
    }

    public void renderizar(SpriteBatch batch) {
        batch.draw(textura, x, y, ANCHO, ALTO);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void dispose() {
        textura.dispose();
    }
}

