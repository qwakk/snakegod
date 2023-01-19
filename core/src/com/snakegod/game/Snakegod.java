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
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Iterator;
import java.util.LinkedList;

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
		snakeOne = new Snake(direction.RIGHT, direction.RIGHT, new LinkedList<Vector2>());
		snakeTwo = new Snake(direction.LEFT, direction.LEFT, new LinkedList<Vector2>());
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
	public void render () {
		inputChecker();

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

	public void hitCheck(LinkedList<Vector2> one, LinkedList<Vector2> two) {
		if (one.getLast().x == juicer.x && one.getLast().y == juicer.y) {
			one.addFirst(new Vector2(one.getFirst().x, one.getFirst().y));
			juicer.x = (int) (Math.random() * (63-1) + 1);
			juicer.y = (int) (Math.random() * (63-1) + 1);
			score++;
		}
		else if (two.getLast().x == juicer.x && two.getLast().y == juicer.y) {
			two.addFirst(new Vector2(two.getFirst().x, two.getFirst().y));
			juicer.x = (int) (Math.random() * (63-1) + 1);
			juicer.y = (int) (Math.random() * (63-1) + 1);
			score++;
		}
	}

	public void deathCheck(Vector2 head, LinkedList<Vector2> check1, LinkedList<Vector2> check2) {
		Iterator<Vector2> iterator = check1.iterator();
		while (iterator.hasNext()) {
			Vector2 part = iterator.next();
			if (!iterator.hasNext()) {break;}
			if (head.x == part.x && head.y == part.y) {
				dispose();
				create();
				break;
			}
		}

		iterator = check2.iterator();
		while (iterator.hasNext()) {
			Vector2 part = iterator.next();
			if (head.x == part.x && head.y == part.y) {
				dispose();
				create();
				break;
			}
		}
	}

	public void checks() {
		snakeMovement(snakeOne);
		snakeMovement(snakeTwo);
		hitCheck(snakeOne.snakeList, snakeTwo.snakeList);
		deathCheck(snakeOne.getHead(), snakeOne.snakeList, snakeTwo.snakeList);
		deathCheck(snakeTwo.getHead(), snakeTwo.snakeList, snakeOne.snakeList);
	}

	public void snakeMovement(Snake snake) {
		snake.snakeList.removeFirst();
		float x = snake.snakeList.getLast().x;
		float y = snake.snakeList.getLast().y;
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
		snake.snakeList.add(moving);
	}

	enum direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
}