package com.snakegod.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class GameScreen implements Screen {
    SpriteBatch batch;
    BitmapFont bmf;
    OrthographicCamera camera;
    FitViewport viewport;
    Vector2 juicer;
    ShapeRenderer shape;
    Snake snakeOne;
    Snake snakeTwo;
    double count;
    Integer score;
    int unit;
    Snakegod game;

    public GameScreen(Snakegod game) {
        this.game = game;
    }

    @Override
    public void show() {
        unit = 16;
        score = 0;
        batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AvantGarde Normal.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 44;
        bmf = generator.generateFont(parameter);
        bmf.setColor(Color.WHITE);

        snakeOne = new Snake(
                Snakegod.direction.RIGHT, new LinkedList<>(),
                new ArrayList<>(Arrays.asList(Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S)));

        snakeTwo = new Snake(
                Snakegod.direction.LEFT, new LinkedList<>(),
                new ArrayList<>(Arrays.asList(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K)));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 1024);
        viewport = new FitViewport(1024,1024, camera);

        shape = new ShapeRenderer();

        snakeStart(snakeOne,snakeTwo, 5);

        juicer = new Vector2();
        juicer.x = (int) (Math.random() * (15-1) + 1)*64;
        juicer.y = (int) (Math.random() * (15-1) + 1)*64;

        count = 0;
    }

    @Override
    public void render (float delta) {
        InputHandler.getInput(snakeOne);
        InputHandler.getInput(snakeTwo);

        if (count >= 0.1) {
            snakeOne.ld = snakeOne.d;
            snakeTwo.ld = snakeTwo.d;

            snakeOne.move(unit);
            snakeTwo.move(unit);

            hitCheck(snakeOne.snakeList);
            hitCheck(snakeTwo.snakeList);
            deathCheck(snakeOne, snakeTwo);
            deathCheck(snakeTwo, snakeOne);
            count -= 0.1;
        }

        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        camera.update();
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.SKY);

        for (Vector2 current : snakeOne.snakeList) {
            shape.rect(current.x,current.y, unit, unit);
        }
        shape.setColor(Color.RED);
        for (Vector2 current : snakeTwo.snakeList) {
            shape.rect(current.x,current.y, unit, unit);
        }

        shape.setColor(Color.GREEN);
        shape.rect(juicer.x, juicer.y, unit, unit);
        shape.end();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        bmf.draw(batch, "SCORE = "+score.toString(),0,1024);
        batch.end();

        count += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
    public void dispose () {
        shape.dispose();
        batch.dispose();
    }

    public void hitCheck(LinkedList<Vector2> snake) {
        if (snake.getLast().equals(juicer)) {
            snake.addFirst(new Vector2(snake.getFirst()));
            juicer.x = (int) (Math.random() * (15-1) + 1)*64;
            juicer.y = (int) (Math.random() * (15-1) + 1)*64;
            score++;
        }
    }

    public void deathCheck(Snake check1, Snake check2) {
        if (check1.snakeList.subList(0,check1.snakeList.size()-1).contains(check1.getHead())
                || check2.snakeList.contains(check1.getHead())) {
            game.setMenuScreen();
        }
    }

    public void snakeStart(Snake one, Snake two, int startLength) {
        int startY = Gdx.graphics.getWidth()/2;
        int startOne = 0;
        int startTwo = Gdx.graphics.getWidth()-unit;
        for (int i = 0; i < startLength; i++) {
            one.snakeList.add(new Vector2(startOne,startY));
            two.snakeList.add(new Vector2(startTwo,startY));
            startOne+=unit;
            startTwo-=unit;
        }
    }
}
