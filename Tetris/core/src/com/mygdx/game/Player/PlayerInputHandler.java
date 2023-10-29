package com.mygdx.game.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Board.Board;
import com.mygdx.game.Board.Piece.*;

public class PlayerInputHandler implements InputProcessor {
    Board board;
    Piece piece;

    public PlayerInputHandler() {

    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                PieceManager.getPiece().move(-1, 0); // Move left
                break;
            case Input.Keys.RIGHT:
                PieceManager.getPiece().move(1, 0); // Move right
                break;
            case Input.Keys.DOWN:
                PieceManager.getPiece().move(0, -1); // Move down
                break;
//            case Input.Keys.UP:
//                Piece.rotateClockwise();
//                rotate (clockwise or counterclockwise)
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

