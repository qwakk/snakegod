package com.snakegod.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;

public class Snake {
    Snakegod.direction d;
    Snakegod.direction ld;
    LinkedList<Vector2> snakeList;

    ArrayList<Integer> inputList;

    public Snake(Snakegod.direction d, Snakegod.direction ld, LinkedList<Vector2> snakeList, ArrayList<Integer> inputList) {
        this.d = d;
        this.ld = ld;
        this.snakeList = snakeList;
        this.inputList = inputList;
    }

    public Vector2 getHead() {
        return snakeList.getLast();
    }

    public void move(int speed) {
        snakeList.removeFirst();
        float x = snakeList.getLast().x;
        float y = snakeList.getLast().y;
        Vector2 moving = new Vector2(x,y);

        if(d == Snakegod.direction.LEFT) {
            moving.x -= speed;
            if (moving.x < 0) {moving.x = 63;}
        }
        if(d == Snakegod.direction.RIGHT) {
            moving.x += speed;
            if (moving.x > 63) {moving.x = 0;}
        }
        if(d == Snakegod.direction.UP) {
            moving.y += speed;
            if (moving.y > 63) {moving.y = 0;}
        }
        if(d == Snakegod.direction.DOWN) {
            moving.y -= speed;
            if (moving.y < 0) {moving.y = 63;}
        }
        snakeList.add(moving);
    }

}
