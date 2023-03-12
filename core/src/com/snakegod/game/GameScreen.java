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
import com.badlogic.gdx.math.Rectangle;
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
    Rectangle snake;
    Rectangle juicer;
    ShapeRenderer shape;
    Snake snakeOne;
    Snake snakeTwo;
    double count;
    Integer score;
    int speed;

    Snakegod game;

    public GameScreen(Snakegod game) {
        this.game = game;
    }

    @Override
    public void show() {
        speed = 16;
        score = 0;
        batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AvantGarde Normal.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size =44;
        bmf = generator.generateFont(parameter);
        bmf.setColor(Color.WHITE);

        count = 0;

        snakeOne = new Snake(Snakegod.direction.RIGHT, Snakegod.direction.RIGHT, new LinkedList<>()
                , new ArrayList<>(Arrays.asList(Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S)));

        snakeTwo = new Snake(Snakegod.direction.LEFT, Snakegod.direction.LEFT, new LinkedList<>()
                , new ArrayList<>(Arrays.asList(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K)));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 1024);
        viewport = new FitViewport(1024,1024, camera);

        shape = new ShapeRenderer();
        snake = new Rectangle();
        snake.width = 16;
        snake.height = 16;

        snakeOne.snakeList.add(new Vector2(0,512));
        snakeOne.snakeList.add(new Vector2(16,512));
        snakeOne.snakeList.add(new Vector2(32,512));
        snakeOne.snakeList.add(new Vector2(48,512));
        snakeOne.snakeList.add(new Vector2(64,512));

        snakeTwo.snakeList.add(new Vector2(1008,512));
        snakeTwo.snakeList.add(new Vector2(992,512));
        snakeTwo.snakeList.add(new Vector2(976,512));
        snakeTwo.snakeList.add(new Vector2(960,512));
        snakeTwo.snakeList.add(new Vector2(944,512));

        juicer = new Rectangle();
        juicer.x = 512;
        juicer.y = 512;
        juicer.width = 16;
        juicer.height = 16;
    }

    @Override
    public void render (float delta) {
        InputHandler.getInput(snakeOne);
        InputHandler.getInput(snakeTwo);

        if (count >= 0.1) {
            snakeOne.ld = snakeOne.d;
            snakeTwo.ld = snakeTwo.d;

            snakeOne.move(speed);
            snakeTwo.move(speed);

            hitCheck(snakeOne.snakeList);
            hitCheck(snakeTwo.snakeList);
            deathCheck(snakeOne.getHead(), snakeOne.snakeList, snakeTwo.snakeList);
            deathCheck(snakeTwo.getHead(), snakeTwo.snakeList, snakeOne.snakeList);
            count -= 0.1;
        }

        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.SKY);

        for (Vector2 current : snakeOne.snakeList) {
            shape.rect(current.x,current.y, snake.width, snake.height);
        }
        shape.setColor(Color.RED);
        for (Vector2 current : snakeTwo.snakeList) {
            shape.rect(current.x,current.y, snake.width, snake.height);
        }

        shape.setColor(Color.GREEN);
        shape.rect(juicer.x, juicer.y, juicer.width, juicer.height);
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
        if (snake.getLast().x == juicer.x && snake.getLast().y == juicer.y) {
            snake.addFirst(new Vector2(snake.getFirst().x, snake.getFirst().y));
            juicer.x = (int) (Math.random() * (15-1) + 1)*64;
            juicer.y = (int) (Math.random() * (15-1) + 1)*64;
            score++;
        }
    }

    public void deathCheck(Vector2 head, LinkedList<Vector2> check1, LinkedList<Vector2> check2) {
        if (check1.subList(0,check1.size()-1).contains(head) || check2.contains(head)) {
            game.setMenuScreen();
        }
    }
}
