package com.snakegod.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MenuScreen implements Screen {


    Snakegod game;
    BitmapFont bmf;

    SpriteBatch batch;

    float testWait = 0;

    public MenuScreen(Snakegod game) {
        this.game = game;
    }

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AvantGarde Normal.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 44;
        bmf = generator.generateFont(parameter);
        bmf.setColor(Color.WHITE);
        bmf.getData().setScale(1,1);

        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        batch.begin();
        bmf.draw(batch, "TESTMENU",500,500);
        batch.end();

        testWait += delta;
        if (testWait > 5) {
            game.changeScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
