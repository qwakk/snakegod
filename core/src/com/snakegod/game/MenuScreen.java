package com.snakegod.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {

    Snakegod game;
    BitmapFont bmf;
    Stage stage;
    TextButtonStyle playButtonStyle;
    TextButtonStyle exitButtonStyle;
    TextButton playButton;
    TextButton exitButton;

    OrthographicCamera camera;
    FitViewport viewport;

    public MenuScreen(Snakegod game) {
        this.game = game;
    }

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AvantGarde Normal.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 38;
        bmf = generator.generateFont(parameter);
        bmf.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        viewport = new FitViewport(1000,1000,camera);

        stage = new Stage();
        stage.setViewport(viewport);

        playButtonStyle = new TextButtonStyle();
        playButtonStyle.font = bmf;
        playButtonStyle.fontColor = Color.SKY;
        exitButtonStyle = new TextButtonStyle();
        exitButtonStyle.font = bmf;
        exitButtonStyle.fontColor = Color.RED;

        playButton = new TextButton("Play", playButtonStyle);
        playButton.setPosition(400,550);
        playButton.setWidth(200);

        exitButton = new TextButton("Exit", exitButtonStyle);
        exitButton.setPosition(400,450);
        exitButton.setWidth(200);
        exitButton.setColor(Color.RED);
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
        viewport.update(width, height);
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
