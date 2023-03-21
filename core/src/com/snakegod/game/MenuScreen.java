package com.snakegod.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
        parameter.size = 44;
        bmf = generator.generateFont(parameter);
        bmf.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage();
        stage.setViewport(viewport);

        playButtonStyle = new TextButtonStyle();
        playButtonStyle.font = bmf;
        playButtonStyle.fontColor = Color.SKY;
        exitButtonStyle = new TextButtonStyle();
        exitButtonStyle.font = bmf;
        exitButtonStyle.fontColor = Color.RED;

        playButton = new TextButton("Play", playButtonStyle);
        exitButton = new TextButton("Exit", exitButtonStyle);
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

        Table menuTable = new Table();
        menuTable.add(playButton).pad(20).minWidth(Gdx.graphics.getWidth()*0.2f);
        menuTable.row();
        menuTable.add(exitButton).pad(20).minWidth(Gdx.graphics.getWidth()*0.2f);
        menuTable.setFillParent(true);

        stage.addActor(menuTable);
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
