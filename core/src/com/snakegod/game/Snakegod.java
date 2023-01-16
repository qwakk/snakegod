package com.snakegod.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class Snakegod extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont bmf;
	OrthographicCamera camera;
	Rectangle snake;
	Rectangle juicer;
	ShapeRenderer shape;
	Snake snakeOne;
	Snake snakeTwo;
	int count;
	Integer score;
	float speed;

	@Override
	public void create () {
		speed = 1;
		score = 0;
		batch = new SpriteBatch();
		bmf = new BitmapFont();
		bmf.setColor(Color.WHITE);
		bmf.getData().setScale(2,2);
		count = 0;
		snakeOne = new Snake(direction.RIGHT, direction.RIGHT, new Array<Vector2>());
		snakeTwo = new Snake(direction.LEFT, direction.LEFT, new Array<Vector2>());
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 64, 64);
		shape = new ShapeRenderer();
		snake = new Rectangle();
		snake.width = 1;
		snake.height = 1;

		snakeOne.snakeArray.add(new Vector2(7,10));
		snakeOne.snakeArray.add(new Vector2(8,10));
		snakeOne.snakeArray.add(new Vector2(9,10));
		snakeOne.snakeArray.add(new Vector2(10,10));
		snakeOne.snakeArray.add(new Vector2(11,10));
		snakeOne.snakeArray.add(new Vector2(12,10));
		snakeOne.snakeArray.add(new Vector2(13,10));

		snakeTwo.snakeArray.add(new Vector2(56,11));
		snakeTwo.snakeArray.add(new Vector2(55,11));
		snakeTwo.snakeArray.add(new Vector2(54,11));
		snakeTwo.snakeArray.add(new Vector2(53,11));
		snakeTwo.snakeArray.add(new Vector2(52,11));
		snakeTwo.snakeArray.add(new Vector2(51,11));
		snakeTwo.snakeArray.add(new Vector2(50,11));

		juicer = new Rectangle();
		juicer.x = 20;
		juicer.y = 20;
		juicer.width = 1;
		juicer.height = 1;
	}

	@Override
	public void render () {
		inputChecker();

		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.SKY);

		for (Vector2 current : new Array.ArrayIterator<>(snakeOne.snakeArray)) {
			shape.rect(current.x,current.y, snake.width, snake.height);
		}
		shape.setColor(Color.RED);
		for (Vector2 current : new Array.ArrayIterator<>(snakeTwo.snakeArray)) {
			shape.rect(current.x,current.y, snake.width, snake.height);
		}

		shape.setColor(Color.GREEN);
		shape.rect(juicer.x, juicer.y, juicer.width, juicer.height);
		shape.end();

		if (count == 6) {
			snakeOne.ld = snakeOne.d;
			snakeTwo.ld = snakeTwo.d;
			checks();
			count = 0;
		}

		batch.begin();
		bmf.draw(batch, "SCORE = "+score.toString(),0,1000);
		batch.end();

		count++;
	}

	@Override
	public void dispose () {
		shape.dispose();
		batch.dispose();
	}

	public void inputChecker() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.A) && snakeOne.d != direction.RIGHT && snakeOne.ld != direction.RIGHT) {
			snakeOne.d = direction.LEFT;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.D) && snakeOne.d != direction.LEFT && snakeOne.ld != direction.LEFT) {
			snakeOne.d = direction.RIGHT;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.W) && snakeOne.d != direction.DOWN && snakeOne.ld != direction.DOWN) {
			snakeOne.d = direction.UP;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.S) && snakeOne.d != direction.UP && snakeOne.ld != direction.UP) {
			snakeOne.d = direction.DOWN;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.J) && snakeTwo.d != direction.RIGHT && snakeTwo.ld != direction.RIGHT) {
			snakeTwo.d = direction.LEFT;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.L) && snakeTwo.d != direction.LEFT && snakeTwo.ld != direction.LEFT) {
			snakeTwo.d = direction.RIGHT;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.I) && snakeTwo.d != direction.DOWN && snakeTwo.ld != direction.DOWN) {
			snakeTwo.d = direction.UP;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.K) && snakeTwo.d != direction.UP && snakeTwo.ld != direction.UP) {
			snakeTwo.d = direction.DOWN;
		}
	}

	public void hitCheck(Array<Vector2> one, Array<Vector2> two) {
		if (one.get(one.size-1).x == juicer.x && one.get(one.size-1).y == juicer.y) {
			one.insert(0,new Vector2(one.get(0).x, one.get(0).y));
			juicer.x = (int) (Math.random() * (63-1) + 1);
			juicer.y = (int) (Math.random() * (63-1) + 1);
			score++;
		}
		else if (two.get(two.size-1).x == juicer.x && two.get(two.size-1).y == juicer.y) {
			two.insert(0,new Vector2(two.get(0).x, two.get(0).y));
			juicer.x = (int) (Math.random() * (63-1) + 1);
			juicer.y = (int) (Math.random() * (63-1) + 1);
			score++;
		}
	}

	public void deathCheck(Vector2 head, Array<Vector2> check1, Array<Vector2> check2) {
		for (int i = 0; i < check1.size-1; i++) {
			if (head.x == check1.get(i).x && head.y == check1.get(i).y) {
				dispose();
				create();
				break;
			}
		}

		for (int i = 0; i < check2.size; i++) {
			if (head.x == check2.get(i).x && head.y == check2.get(i).y) {
				dispose();
				create();
				break;
			}
		}
	}

	public void checks() {
		snakeMovement(snakeOne);
		snakeMovement(snakeTwo);
		hitCheck(snakeOne.snakeArray, snakeTwo.snakeArray);
		deathCheck(snakeOne.getHead(), snakeOne.snakeArray, snakeTwo.snakeArray);
		deathCheck(snakeTwo.getHead(), snakeTwo.snakeArray, snakeOne.snakeArray);
	}

	public void snakeMovement(Snake snake) {
		snake.snakeArray.removeIndex(0);
		float x = snake.snakeArray.get(snake.snakeArray.size-1).x;
		float y = snake.snakeArray.get(snake.snakeArray.size-1).y;
		Vector2 moving = new Vector2(x,y);

		if(snake.d == direction.LEFT) {
			moving.x -= speed;
			if (moving.x < 0) {moving.x = 63;}
		}
		if(snake.d == direction.RIGHT) {
			moving.x += speed;
			if (moving.x > 63) {moving.x = 0;}
		}
		if(snake.d == direction.UP) {
			moving.y += speed;
			if (moving.y > 63) {moving.y = 0;}
		}
		if(snake.d == direction.DOWN) {
			moving.y -= speed;
			if (moving.y < 0) {moving.y = 63;}
		}
		snake.snakeArray.add(moving);
	}

	enum direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
}