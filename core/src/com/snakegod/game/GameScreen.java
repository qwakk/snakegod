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
    private SpriteBatch batch;
    private BitmapFont bmf;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private Vector2 scorePoint;
    private ShapeRenderer shape;
    private Snake snakeOne;
    private Snake snakeTwo;
    private double count;
    private int score;
    private final int unit = 16;
    private Snakegod game;

    public GameScreen(Snakegod game) {
        this.game = game;
    }

    @Override
    public void show() {
        score = 0;

        snakeOne = new Snake(
                Snakegod.direction.RIGHT, new LinkedList<>(),
                new ArrayList<>(Arrays.asList(Input.Keys.A, Input.Keys.D, Input.Keys.W, Input.Keys.S)));

        snakeTwo = new Snake(
                Snakegod.direction.LEFT, new LinkedList<>(),
                new ArrayList<>(Arrays.asList(Input.Keys.J, Input.Keys.L, Input.Keys.I, Input.Keys.K)));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Snakegod.fontPath));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 44;
        bmf = generator.generateFont(parameter);
        bmf.setColor(Color.WHITE);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Snakegod.gameWidth, Snakegod.gameHeight);
        viewport = new FitViewport(Snakegod.gameWidth,Snakegod.gameHeight, camera);

        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        snakeStart(snakeOne,snakeTwo, 5);

        scorePoint = createRandomPoint();

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

            hitCheck(snakeOne);
            hitCheck(snakeTwo);
            deathCheck(snakeOne, snakeTwo);
            deathCheck(snakeTwo, snakeOne);
            count = 0;
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
        shape.rect(scorePoint.x, scorePoint.y, unit, unit);
        shape.end();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        bmf.draw(batch, "SCORE = "+score,10,1014);
        batch.end();

        count += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() { /*NYI*/ }
    @Override
    public void resume() { /*NYI*/ }
    @Override
    public void hide() { /*NYI*/ }

    @Override
    public void dispose () {
        shape.dispose();
        batch.dispose();
    }

    public void hitCheck(Snake snake) {
        if (snake.getHead().equals(scorePoint)) {
            snake.addFirst(new Vector2(snake.getLastTail()));
            scorePoint = createRandomPoint();
            score++;
        }
    }

    public void deathCheck(Snake one, Snake two) {
        if (    one.getTail().contains(one.getHead())
                || two.getSnake().contains(one.getHead())) {
            game.setMenuScreen();
        }
    }

    public void snakeStart(Snake one, Snake two, int startLength) {
        int startY = Snakegod.gameHeight/2;
        int startOne = 0;
        int startTwo = Snakegod.gameWidth-unit;
        for (int i = 0; i < startLength; i++) {
            one.add(new Vector2(startOne,startY));
            two.add(new Vector2(startTwo,startY));
            startOne+=unit;
            startTwo-=unit;
        }
    }

    public Vector2 createRandomPoint() {
        return new Vector2(
                (int) (Math.random() * (15-1) + 1)*64,
                (int) (Math.random() * (15-1) + 1)*64);
    }
}
