package com.mygdx.game.Board.Piece;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Board.Board;
import com.mygdx.game.Board.BoardManager;
import com.mygdx.game.Engine;

import java.util.ArrayList;

public class Piece {
    protected int[][] shape;
    protected int currentRotation;
    protected int x;
    protected int y;

    private ArrayList<Piece> pieces;
    protected Color color;

    private int amountFallen, newY;

    protected PieceType pieceType;
    private static final Color[] pieceColors = {
            Color.CYAN,   // I
            Color.BLUE,   // J
            Color.ORANGE, // L
            Color.YELLOW, // O
            Color.GREEN,  // S
            Color.MAGENTA, // T
            Color.RED    // Z
    };

    public Piece(int[][] shape, int currentRotation, int x, int y) {
        this(shape, currentRotation, x, y, PieceType.BLANK, Color.BLACK);
        amountFallen = 0;
    }
    public Piece(int[][] shape) {
        this(shape, 0, 0, 0, PieceType.BLANK, Color.BLACK);
        amountFallen = 0;
    }

    public Piece(Piece piece) {
        this(piece.getCurrentShape(), piece.getCurrentRotation(), piece.getX(), piece.getY(), piece.getPieceType(), piece.getColor());
        amountFallen = 0;
    }

    public Piece(int[][] shape, int currentRotation, int x, int y, PieceType pieceType, Color color) {
        this.shape = shape;
        this.currentRotation = currentRotation;
        this.x = x;
        this.y = y;
        this.pieceType = pieceType;
        this.color = color;


        amountFallen = 0;
    }

    public int[][] getCurrentShape() {
        return shape;
    }

    public int getCurrentRotation() {
        return currentRotation;
    }
    public int getWidth() {
        return shape[0].length;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int deltaX, int deltaY) {
        ArrayList<Rectangle> pieceShape = BoardManager.getBoard().getMovingPiece();
        deltaX *= Engine.SPACE_SIZE;
        deltaY *= Engine.SPACE_SIZE;
        for(int i = 0; i < pieceShape.size(); i++) {
            pieceShape.get(i).x += deltaX;
            pieceShape.get(i).y += deltaY;
        }
    }

    public void rotateClockwise() {
        int[][] currentShape = getCurrentShape();
        int[][] newShape = new int[currentShape.length][currentShape[0].length];

        for (int i = 0; i < currentShape.length; i++) {
            for (int j = 0; j < currentShape[0].length; j++) {
                newShape[j][currentShape.length - 1 - i] = currentShape[i][j];
            }
        }

        shape = newShape;
    }

    public void rotateCounterClockwise() {
        int[][] originalShape = shape;
        int[][] newShape = new int[originalShape[0].length][originalShape.length];

        for (int i = 0; i < originalShape.length; i++) {
            for (int j = 0; j < originalShape[0].length; j++) {
                newShape[originalShape[0].length - 1 - j][i] = originalShape[i][j];
            }
        }

        shape = newShape;
    }

    public void reset() {
        // Reset the piece to its initial state
        this.currentRotation = 0;
        this.x = 0;
        this.y = 0;
        // maybe reset other attributes of piece
    }


    public Piece copy() {
        // Create a copy of the piece
        return new Piece(this);
    }

    public void randomRotation() {
        int randomRotations = (int) (Math.random() * 4); // Randomly rotate 0 to 3 times
        for (int i = 0; i < randomRotations; i++) {
            rotateClockwise();
        }
    }

    public static boolean isValidPieceType(PieceType type) {
        for (PieceType validType : PieceType.values()) {
            if (validType == type) {
                return true;
            }
        }
        return false;
    }

//    public boolean isAtBottom(Board board) {
//        return board.getLowestYPos() == Engine.BOARD_HEIGHT;
//        // Check if the piece is at the bottom of the game board
//    }

    public void moveToTop() {
        y = 0;
        // Position the piece at the top of the game board
    }

    public void resetPosition() {
        moveToTop();
        x = Engine.BOARD_WIDTH / 2 - getWidth() / 2;
        // Reset the position of the piece to the top
    }
    public int moveDownOverTime() {
        amountFallen += Engine.SPACE_SIZE;
        return amountFallen;
    }



    public boolean isTouchingLeftBoundary() {
        return x == 0;
        // Check if the piece is touching the left boundary
    }

    public boolean isTouchingRightBoundary() {
        // Check if the piece is touching the right boundary
        return x + getWidth() == Engine.BOARD_WIDTH;
    }

    public boolean isTouchingBottomBoundary() {
        return y + getHeight() == Engine.BOARD_HEIGHT;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Color getColor() {
        return color;
    }

}
