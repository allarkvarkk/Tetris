package com.mygdx.game.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Board.Piece.Piece;
import com.mygdx.game.Board.Piece.PieceCreator;
import com.mygdx.game.Board.Piece.PieceType;
import com.mygdx.game.Engine;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import java.util.ArrayList;

public class Board implements Screen {
    private int x, y;
    final public static int SCREEN_HEIGHT = Gdx.graphics.getHeight(), SCREEN_WIDTH = Gdx.graphics.getWidth();

    private Engine tetris;
    private Rectangle[][] grid;
    private PieceCreator pieceCreator;
    private Piece piece;

    private ArrayList<Rectangle> pieceShape;
    private boolean isMovingPieceDown = false;

    public Board(Engine tetris) {
        this.tetris = tetris;
        grid = new Rectangle[Engine.BOARD_WIDTH][Engine.BOARD_HEIGHT];
        pieceCreator = new PieceCreator(tetris, getCenterHorizontally(), getBottomOfBoard());
        createBoard();
        piece = pieceCreator.createRandomPiece();
        pieceShape = pieceCreator.createCurrentPieceShape();
    }

    @Override
    public void show() {
        tetris.shapeRenderer = new ShapeRenderer(); // Initialize the shape renderer
        Timer.schedule(new Task() {
            @Override
            public void run() {
                isMovingPieceDown = true;
            }
        }, 1, 1); // Move down every 2 second
    }


    @Override
    public void render(float delta) {

        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        tetris.shapeRenderer.setAutoShapeType(true);
        tetris.shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Use Filled for rendering
        tetris.shapeRenderer.setColor(Color.GREEN);

        // Draw the grid
        for (int i = 0; i < grid.length; i++) {
            for (int u = 0; u < grid[i].length; u++) {
                tetris.shapeRenderer.rect(grid[i][u].x, grid[i][u].y, grid[i][u].width, grid[i][u].height);
            }
        }
        if (isMovingPieceDown) {
            piece.move(0,-1);
            isMovingPieceDown = false;
        }


        tetris.shapeRenderer.setColor(piece.getColor());
        for (int i = 0; i < pieceShape.size(); i++) {
            tetris.shapeRenderer.rect(pieceShape.get(i).x, pieceShape.get(i).y, pieceShape.get(i).width, pieceShape.get(i).height);
            // this is where piece is drawn. Move down here
        }
        tetris.shapeRenderer.end();
    }

//    public static int getLowestYPos() {
//        return lowestYPos;
//    }

    public int getLowestYValue(ArrayList<Rectangle> rect) {
        int temp = Integer.MAX_VALUE;
        for (Rectangle i : rect) {
            if ((int) i.getY() < temp) {
                temp = (int) i.y;
            }
        }
        return temp;
    }

    @Override
    public void resize(int width, int height) {
        // Update your camera and viewport here if needed
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
        tetris.shapeRenderer.dispose(); // Dispose of the shape renderer
    }

    public void createBoard() {
        int positionX = getLeftOfBoard(), positionY = getBottomOfBoard();
        for (int i = 0; i < grid.length; i++) {
            for (int u = 0; u < grid[i].length; u++) {
                grid[i][u] = new Rectangle(positionX, positionY, Engine.SPACE_SIZE, Engine.SPACE_SIZE);
                positionY += Engine.SPACE_SIZE;
            }
            positionY = getBottomOfBoard();
            positionX += Engine.SPACE_SIZE;
        }
    }


    public int getCenterHorizontally() {
        return SCREEN_WIDTH / 2;
    }

    private int getCenterVertically() {
        return SCREEN_HEIGHT / 2;
    }

    private int getLeftOfBoard() {
        return getCenterHorizontally() - (Engine.BOARD_WIDTH * Engine.SPACE_SIZE) / 2;
    }

    public int getBottomOfBoard() {
        return getCenterVertically() - (Engine.BOARD_HEIGHT * Engine.SPACE_SIZE) / 2;
    } //(screenHeight / 2) - (Engine.BOARD_HEIGHT * ENGINE>SPACE_SIZE) / 2;

    public ArrayList<Rectangle> getMovingPiece(){
        return pieceShape;
    }
}
