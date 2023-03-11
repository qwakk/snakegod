package com.snakegod.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

    Snakegod game;
    BitmapFont bmf;
    float testWait = 0;
    Stage stage;
    TextButtonStyle textButtonStyle;
    TextButton playButton;
    TextButton exitButton;

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

        stage = new Stage();
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = bmf;
        textButtonStyle.overFontColor = Color.GREEN;


        playButton = new TextButton("Play", textButtonStyle);
        playButton.setPosition(450,550);

        exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.setPosition(450,450);
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
