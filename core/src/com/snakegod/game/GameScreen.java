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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class GameScreen implements Screen {
    SpriteBatch batch;
    BitmapFont bmf;
    OrthographicCamera camera;
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
        speed = 1;
        score = 0;
        batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("AvantGarde Normal.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 44;
        bmf = generator.generateFont(parameter);
        bmf.setColor(Color.WHITE);
        bmf.getData().setScale(1,1);
        count = 0;

        snakeOne = new Snake(Snakegod.direction.RIGHT, Snakegod.direction.RIGHT, new LinkedList<>()
                , new ArrayList<>(Arrays.asList(Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S)));

        snakeTwo = new Snake(Snakegod.direction.LEFT, Snakegod.direction.LEFT, new LinkedList<>()
                , new ArrayList<>(Arrays.asList(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K)));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 64, 64);
        shape = new ShapeRenderer();
        snake = new Rectangle();
        snake.width = 1;
        snake.height = 1;

        snakeOne.snakeList.add(new Vector2(7,10));
        snakeOne.snakeList.add(new Vector2(8,10));
        snakeOne.snakeList.add(new Vector2(9,10));
        snakeOne.snakeList.add(new Vector2(10,10));
        snakeOne.snakeList.add(new Vector2(11,10));
        snakeOne.snakeList.add(new Vector2(12,10));
        snakeOne.snakeList.add(new Vector2(13,10));

        snakeTwo.snakeList.add(new Vector2(56,11));
        snakeTwo.snakeList.add(new Vector2(55,11));
        snakeTwo.snakeList.add(new Vector2(54,11));
        snakeTwo.snakeList.add(new Vector2(53,11));
        snakeTwo.snakeList.add(new Vector2(52,11));
        snakeTwo.snakeList.add(new Vector2(51,11));
        snakeTwo.snakeList.add(new Vector2(50,11));

        juicer = new Rectangle();
        juicer.x = 20;
        juicer.y = 20;
        juicer.width = 1;
        juicer.height = 1;
    }

    @Override
    public void render (float delta) {
        InputHandler.getInput(snakeOne);
        InputHandler.getInput(snakeTwo);

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

        batch.begin();
        bmf.draw(batch, "SCORE = "+score.toString(),0,1000);
        batch.end();

        count += Gdx.graphics.getDeltaTime();
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
    public void dispose () {
        shape.dispose();
        batch.dispose();
    }

    public void hitCheck(LinkedList<Vector2> snake) {
        if (snake.getLast().x == juicer.x && snake.getLast().y == juicer.y) {
            snake.addFirst(new Vector2(snake.getFirst().x, snake.getFirst().y));
            juicer.x = (int) (Math.random() * (63-1) + 1);
            juicer.y = (int) (Math.random() * (63-1) + 1);
            score++;
        }
    }

    public void deathCheck(Vector2 head, LinkedList<Vector2> check1, LinkedList<Vector2> check2) {
        if (check1.subList(0,check1.size()-1).contains(head) || check2.contains(head)) {
            game.menuScreen.testWait = 0;
            game.setMenuScreen();
        }
    }
}
