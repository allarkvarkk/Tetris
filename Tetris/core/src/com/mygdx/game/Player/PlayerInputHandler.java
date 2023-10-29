package com.mygdx.game.Player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.Board.Board;
import com.mygdx.game.Board.Piece.*;

public class PlayerInputHandler extends InputAdapter {
    Board board;
    Piece piece;
    public PlayerInputHandler(String name, int score, int level) {

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                piece.move(-1, 0); // Move left
                break;
            case Input.Keys.RIGHT:
                piece.move(1, 0); // Move right
                break;
            case Input.Keys.DOWN:
                piece.move(0, -1); // Move down
                break;
        }
        return true;
    }
}

