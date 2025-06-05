package io.github.some_example_name.entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class BolaEnemiga {
    private Texture textura;
    private float x, y;
    private float velocidadX, velocidadY;
    private static final float ANCHO = 64;
    private static final float ALTO = 64;
    private float screenWidth, screenHeight;
    private Rectangle hitbox;

    public BolaEnemiga(float screenWidth, float screenHeight) {
        this.textura = new Texture("ball.png");
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.x = MathUtils.random(0, screenWidth - ANCHO);
        this.y = MathUtils.random(0, screenHeight - ALTO);

        this.velocidadX = MathUtils.randomSign() * 80;
        this.velocidadY = MathUtils.randomSign() * 80;

        this.hitbox = new Rectangle(x, y, ANCHO, ALTO);
    }

    public void actualizar(float deltaTime) {
        x += velocidadX * deltaTime;
        y += velocidadY * deltaTime;

        if (x < 0 || x + ANCHO > screenWidth) {
            velocidadX *= -1;
            x = MathUtils.clamp(x, 0, screenWidth - ANCHO);
        }

        if (y < 0 || y + ALTO > screenHeight) {
            velocidadY *= -1;
            y = MathUtils.clamp(y, 0, screenHeight - ALTO);
        }

        hitbox.setPosition(x, y);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void renderizar(SpriteBatch batch) {
        batch.draw(textura, x, y, ANCHO, ALTO);
    }

    public void dispose() {
        textura.dispose();
    }

    public void setVelocidadExtra(float factor) {
        velocidadX *= factor;
        velocidadY *= factor;
        }


}


