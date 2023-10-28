package com.mygdx.game.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Engine;

public class Board implements Screen {
    private int x, y, screenHeight = Gdx.graphics.getHeight(),
            screenWidth = Gdx.graphics.getWidth();
    private Engine tetris;
    final public static int BOARD_WIDTH = 10, BOARD_HEIGHT = 20, SPACE_SIZE = 20;
    private Rectangle[][] grid;

    public Board(Engine tetris) {
        this.tetris = tetris;
        grid = new Rectangle[BOARD_WIDTH][BOARD_HEIGHT];
        grid();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        tetris.shapeRenderer.setAutoShapeType(true);
        tetris.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        tetris.shapeRenderer.setColor(new Color(0, 255, 26, 0));
        for(int i = 0; i < grid.length; i ++){
            for(int u = 0; u < grid[i].length; u ++){
                tetris.shapeRenderer.rect(grid[i][u].x,grid[i][u].y, grid[i][u].width, grid[i][u].height);
            }
        }
        tetris.shapeRenderer.end();
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    public void grid() {
        int positionX = getLeftOfBoard(), positionY = getBottomOfBoard();
        for (int i = 0; i < grid.length; i++) {
            for (int u = 0; u < grid[i].length; u++) {
                grid[i][u] = new Rectangle(positionX,positionY,SPACE_SIZE, SPACE_SIZE);
                positionY += SPACE_SIZE;
            }
            positionY = getBottomOfBoard();
            positionX += SPACE_SIZE;
        }
    }

    private int getCenterHorizontally() {
        return screenWidth / 2;
    }

    private int getCenterVertically() {
        return screenHeight / 2;
    }

    private int getLeftOfBoard() {
        return getCenterHorizontally() - (BOARD_WIDTH * SPACE_SIZE)/2;
    }

    private int getBottomOfBoard() {
        return getCenterVertically() - (BOARD_HEIGHT * SPACE_SIZE)/2;
    }

}