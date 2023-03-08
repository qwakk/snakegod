package com.snakegod.game;

import com.badlogic.gdx.Gdx;

public class InputHandler {
    
    public static void getInput(Snake s) {
        if(Gdx.input.isKeyJustPressed(s.inputList.get(0)) && s.d != Snakegod.direction.RIGHT && s.ld != Snakegod.direction.RIGHT) {
            s.d = Snakegod.direction.LEFT;
        }
        if(Gdx.input.isKeyJustPressed(s.inputList.get(1)) && s.d != Snakegod.direction.LEFT && s.ld != Snakegod.direction.LEFT) {
            s.d = Snakegod.direction.RIGHT;
        }
        if(Gdx.input.isKeyJustPressed(s.inputList.get(2)) && s.d != Snakegod.direction.DOWN && s.ld != Snakegod.direction.DOWN) {
            s.d = Snakegod.direction.UP;
        }
        if(Gdx.input.isKeyJustPressed(s.inputList.get(3)) && s.d != Snakegod.direction.UP && s.ld != Snakegod.direction.UP) {
            s.d = Snakegod.direction.DOWN;
        }
    }
}
