package com.snakegod.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Snake {
    Snakegod.direction d;
    Snakegod.direction ld;
    Array<Vector2> snakeArray;

    public Snake(Snakegod.direction d, Snakegod.direction ld, Array<Vector2> snakeArray) {
        this.d = d;
        this.ld = ld;
        this.snakeArray = snakeArray;
    }

    public Vector2 getHead() {
        return snakeArray.get(snakeArray.size-1);
    }

}
