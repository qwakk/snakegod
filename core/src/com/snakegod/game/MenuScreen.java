package com.snakegod.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

    Snakegod game;
    BitmapFont bmf;
    Stage stage;
    TextButtonStyle textButtonStyle;
    TextButton playButton;
    TextButton exitButton;
    Color exitColor, playColor;

    ShapeRenderer shape;

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

        playColor = Color.SKY;
        exitColor = Color.RED;

        stage = new Stage();
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = bmf;

        shape = new ShapeRenderer();

        playButton = new TextButton("Play", textButtonStyle);
        playButton.setPosition(400,550);
        playButton.setWidth(200);

        exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.setPosition(400,450);
        exitButton.setWidth(200);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(new GameScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(playButton);
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);



    }

    @Override
    public void render(float delta) {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(playColor);
        shape.rect(400,550, 200, 50);
        shape.setColor(exitColor);
        shape.rect(400,450, 200, 50);
        shape.end();
        stage.draw();
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
        stage.dispose();
    }
}
