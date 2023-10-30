package com.mygdx.game.Board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Board.Piece.*;
import com.mygdx.game.*;
import com.mygdx.game.Player.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.Player.PlayerInputHandler;
import java.util.ArrayList;

public class Board implements Screen {
    private int x, y;
    public static int SCREEN_HEIGHT = Gdx.graphics.getHeight(), SCREEN_WIDTH = Gdx.graphics.getWidth();
    final public static int defaultY = (Board.SCREEN_HEIGHT / 2 - (Engine.BOARD_HEIGHT * Engine.SPACE_SIZE) / 2) + ((Engine.BOARD_HEIGHT * Engine.SPACE_SIZE) + Engine.SPACE_SIZE);
    final public static int defaultX = (Board.SCREEN_WIDTH / 2) - (2 * Engine.SPACE_SIZE);
    private Engine tetris;
    private Rectangle[][] grid;
    private PieceCreator pieceCreator;
    PlayerInputHandler playerInput;
    private ArrayList<Rectangle> previousPieces;
    //    private Piece piece;
    private Piece piece;
    private ScoreManager scoreManager;
    private ArrayList<Rectangle> pieceShape;
    private ArrayList<Color> currentColor;
    private ArrayList<Color> previousColors;
    private ArrayList<Color> colors;
    private boolean pieceHasLanded = false;
    private boolean isMovingPieceDown = false;

    public Board(Engine tetris) {
        this.tetris = tetris;
        grid = new Rectangle[Engine.BOARD_WIDTH][Engine.BOARD_HEIGHT];
        pieceCreator = new PieceCreator(tetris, getCenterHorizontally(), getBottomOfBoard());
        createBoard();
        previousPieces = new ArrayList<Rectangle>();
        colors = new ArrayList<Color>(); // Initialize the colors list
        playerInput = new PlayerInputHandler();
        scoreManager = new ScoreManager();
        currentColor = new ArrayList<Color>();
        previousColors = new ArrayList<Color>();
    }

    public void setCurrentPiece(Piece piece) {
        this.piece = piece;
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
        }, .5f, .5f); // Move down every 2 second
    }

    public void addPreviousPieces(ArrayList<Rectangle> completed) {
        previousPieces.addAll(completed);
    }

    public ArrayList<Rectangle> getPreviousPieces() {
        return previousPieces;
    }


    public void createMovementPiece() {
        if (piece == null || pieceShape.isEmpty()) {
            piece = pieceCreator.createRandomPiece();
            pieceShape = pieceCreator.createCurrentPieceShape();
            PieceManager.setPiece(piece);
        } else {
            pieceShape.clear();
            currentColor.clear();
            piece = pieceCreator.createRandomPiece();
            pieceShape = pieceCreator.createCurrentPieceShape();
            PieceManager.setPiece(piece);
        }
        for (Rectangle rectangle : pieceShape) {
            //colors.add(piece.getColor());
            currentColor.add(piece.getColor());
        }
    }


    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        if (piece == null || pieceShape.isEmpty()) {
            createMovementPiece();
        }

        tetris.shapeRenderer.setAutoShapeType(true);
        tetris.shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Use Filled for rendering
        tetris.shapeRenderer.setColor(Color.GREEN);

        // Draw the grid
        for (int i = 0; i < Engine.BOARD_WIDTH; i++) {
            for (int u = 0; u < Engine.BOARD_HEIGHT; u++) {
                if(grid[i][u] != null) {
                    tetris.shapeRenderer.rect(grid[i][u].x, grid[i][u].y, grid[i][u].width, grid[i][u].height);
                }
            }
        }
        if (isMovingPieceDown) {
            piece.move(0, -1);
            isMovingPieceDown = false;
        }
        scoreManager.removeRows();

        tetris.shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        tetris.shapeRenderer.setColor(piece.getColor());
        for (Rectangle rectangle : pieceShape) {
            tetris.shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }

        if(!previousColors.isEmpty()) {
            for (int i = 0; i < previousPieces.size(); i++) {
                tetris.shapeRenderer.setColor(previousColors.get(i));
                tetris.shapeRenderer.rect(previousPieces.get(i).x, previousPieces.get(i).y, previousPieces.get(i).width, previousPieces.get(i).height);
            }
        }

        tetris.shapeRenderer.end();

    }


    public void checkAndClearFullRows() {
        ArrayList<Rectangle> previous = BoardManager.getBoard().getPreviousPieces();

        int totalRowsCleared = 0; // Initialize a variable to keep track of the number of rows cleared

        for (int row = 0; row < Engine.BOARD_HEIGHT; row++) {
            boolean isFull = true;

            for (int col = 0; col < Engine.BOARD_WIDTH; col++) {
                if (grid[col][row] == null) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                // Clear the full row
                clearRow(row);

                // Shift down rows above the cleared row
                shiftRowsDown(row);

                totalRowsCleared++; // Increment the count of cleared rows

                // You can also handle level increment logic here if needed.
            }
        }

        // Calculate points based on the total rows cleared using ScoreManager
        if (totalRowsCleared > 0) {
            scoreManager.updateScore(totalRowsCleared);
        }
    }

    private void clearRow(int row) {
        for (int col = 0; col < Engine.BOARD_WIDTH; col++) {
            grid[col][row] = null;
        }
    }

    private void shiftRowsDown(int clearedRow) {
        for (int row = clearedRow; row < Engine.BOARD_HEIGHT - 1; row++) {
            for (int col = 0; col < Engine.BOARD_WIDTH; col++) {
                grid[col][row] = grid[col][row + 1];
            }
        }
    }

    public int getLowestYValue(ArrayList<Rectangle> rect) {
        int temp = Integer.MAX_VALUE;
        for (Rectangle i : rect) {
            if ((int) i.getY() < temp) {
                temp = (int) i.y;
            }
        }
        return temp;
    }
    public int getTopOfBoard(){
        return getCenterVertically() + (Engine.BOARD_HEIGHT * Engine.SPACE_SIZE) / 2;
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

    public void addPreviousColors(ArrayList<Color> colorsComplete){
        previousColors.addAll(colorsComplete);
    }

    public ArrayList<Color> getPreviousColors(){
        return previousColors;
    }
    public int getCenterHorizontally() {
        return SCREEN_WIDTH / 2;
    }

    private int getCenterVertically() {
        return SCREEN_HEIGHT / 2;
    }

    public int getLeftOfBoard() {
        return getCenterHorizontally() - (Engine.BOARD_WIDTH * Engine.SPACE_SIZE) / 2;
    }
    public int getRightOfBoard(){
        return getCenterHorizontally() + (Engine.BOARD_WIDTH * Engine.SPACE_SIZE) / 2;
    }

    public int getBottomOfBoard() {
        return getCenterVertically() - (Engine.BOARD_HEIGHT * Engine.SPACE_SIZE) / 2;
    } //(screenHeight / 2) - (Engine.BOARD_HEIGHT * ENGINE>SPACE_SIZE) / 2;

    public ArrayList<Rectangle> getMovingPiece() {
        return pieceShape;
    }

    public ArrayList<Color> getCurrentColor(){
        return currentColor;
    }
}