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

    private boolean leftPressed = false, rightPressed = false, downPressed = false;

    final private static float MOVE_DELAY = 0.2f;
    private float moveTimer = 0;

    public PlayerInputHandler() {

    }

    public void update(float delta) {
        // Handle continuous key presses with a delay
        if (leftPressed || rightPressed || downPressed) {
            moveTimer += delta;
            if (moveTimer >= MOVE_DELAY) {
                if (leftPressed) {
                    PieceManager.getPiece().move(-1,0); // Move left
                }
                if (rightPressed) {
                    PieceManager.getPiece().move(1,0); // Move right
                }
                if (downPressed) {
                    PieceManager.getPiece().move(0,-1); // Move down
                }
                moveTimer = 0;
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                leftPressed = true; // Set the leftPressed flag to true
                break;
            case Input.Keys.RIGHT:
                rightPressed = true; // Set the rightPressed flag to true
                break;
            case Input.Keys.DOWN:
                downPressed = true; // Set the downPressed flag to true
                break;
//            case Input.Keys.UP:
//                Piece.rotateClockwise();
//                rotate (clockwise or counterclockwise)
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                leftPressed = false; // Set the leftPressed flag to false
                moveTimer = 0;
                break;
            case Input.Keys.RIGHT:
                rightPressed = false; // Set the rightPressed flag to false
                moveTimer = 0;
                break;
            case Input.Keys.DOWN:
                downPressed = false; // Set the downPressed flag to false
                moveTimer = 0;
                break;
        }
        return true;
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
