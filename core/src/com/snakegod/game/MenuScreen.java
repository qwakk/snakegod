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
    private Snakegod game;
    private BitmapFont bmf;
    private Stage stage;
    private TextButtonStyle playButtonStyle;
    private TextButtonStyle exitButtonStyle;
    private TextButton playButton;
    private TextButton exitButton;
    private OrthographicCamera camera;
    private FitViewport viewport;

    public MenuScreen(Snakegod game) {
        this.game = game;
    }

    @Override
    public void show() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Snakegod.fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 44;
        bmf = generator.generateFont(parameter);
        bmf.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Snakegod.gameWidth, Snakegod.gameHeight);
        viewport = new FitViewport(Snakegod.gameWidth, Snakegod.gameHeight, camera);

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
    public void pause() { /*NYI*/ }

    @Override
    public void resume() { /*NYI*/ }

    @Override
    public void hide() { /*NYI*/ }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
