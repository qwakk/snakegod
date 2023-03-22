package com.snakegod.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    Snakegod.direction d;
    Snakegod.direction ld;
    LinkedList<Vector2> snakeList;
    ArrayList<Integer> inputList;

    public Snake(Snakegod.direction d, LinkedList<Vector2> snakeList, ArrayList<Integer> inputList) {
        this.d = d;
        this.ld = d;
        this.snakeList = snakeList;
        this.inputList = inputList;
    }

    public Vector2 getHead() {
        return snakeList.getLast();
    }

    public Vector2 getLastTail() { return snakeList.getFirst(); }

    public List<Vector2> getTail() { return snakeList.subList(0,length()-1); }

    public int length() { return snakeList.size(); }

    public void add(Vector2 v) {
        snakeList.add(v);
    }
    public void addFirst(Vector2 v) {
        snakeList.addFirst(v);
    }

    public void move(int speed) {
        Vector2 moving = snakeList.removeFirst();
        moving.x = getHead().x;
        moving.y = getHead().y;

        if(d == Snakegod.direction.LEFT) {
            moving.x -= speed;
            if (moving.x < 0) {moving.x = 1008;}
        }
        if(d == Snakegod.direction.RIGHT) {
            moving.x += speed;
            if (moving.x > 1008) {moving.x = 0;}
        }
        if(d == Snakegod.direction.UP) {
            moving.y += speed;
            if (moving.y > 1008) {moving.y = 0;}
        }
        if(d == Snakegod.direction.DOWN) {
            moving.y -= speed;
            if (moving.y < 0) {moving.y = 1008;}
        }
        snakeList.add(moving);
    }

}
