package com.snakegod.game;

import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

public class Snake {
    Snakegod.direction d;
    Snakegod.direction ld;
    LinkedList<Vector2> snakeList;

    public Snake(Snakegod.direction d, Snakegod.direction ld, LinkedList<Vector2> snakeList) {
        this.d = d;
        this.ld = ld;
        this.snakeList = snakeList;
    }

    public Vector2 getHead() {
        return snakeList.getLast();
    }

}
