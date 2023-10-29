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

    public Piece(int[][] shape, int currentRotation) {
        this(shape, currentRotation, PieceType.Z, Color.BLACK);
        amountFallen = 0;
    }
    public Piece(int[][] shape) {
        this(shape, 0, PieceType.Z, Color.BLACK);
        amountFallen = 0;
    }

    public Piece(Piece piece) {
        this(piece.getCurrentShape(), piece.getCurrentRotation(), piece.getPieceType(), piece.getColor());
        amountFallen = 0;
    }

    public Piece(int[][] shape, int currentRotation, PieceType pieceType, Color color) {
        this.shape = shape;
        this.currentRotation = currentRotation;
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

    public void move(int deltaX, int deltaY) {
        ArrayList<Rectangle> pieceShape = BoardManager.getBoard().getMovingPiece();
        deltaX *= Engine.SPACE_SIZE;
        deltaY *= Engine.SPACE_SIZE;
        if (!isTouchingBottomBoundary(pieceShape) && (BoardManager.getBoard().getPreviousPieces().isEmpty()
                || !shouldPieceStop(BoardManager.getBoard().getPreviousPieces(), pieceShape))){
            for (int i = 0; i < pieceShape.size(); i++) {
                pieceShape.get(i).x += deltaX;
                pieceShape.get(i).y += deltaY;

            }

        } else {
            BoardManager.getBoard().addPreviousPieces(pieceShape);
            BoardManager.getBoard().createMovementPiece();
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
        ArrayList<Rectangle> pieceShape = BoardManager.getBoard().getMovingPiece();

        // Reset the piece to its initial state
        this.currentRotation = 0;
        for(int i = 0; i < pieceShape.size(); i++) {
            pieceShape.get(i).x = Board.defaultX;
            pieceShape.get(i).y = Board.defaultY;
        }
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
        ArrayList<Rectangle> pieceShape = BoardManager.getBoard().getMovingPiece();
        for(int i = 0; i < pieceShape.size(); i++) {
            pieceShape.get(i).y = Board.defaultY;
        }
        // Position the piece at the top of the game board
    }

    public void resetPosition() {
        moveToTop();
//        x = Engine.BOARD_WIDTH / 2 - getWidth() / 2;
        ArrayList<Rectangle> pieceShape = BoardManager.getBoard().getMovingPiece();
        for(int i = 0; i < pieceShape.size(); i++) {
            pieceShape.get(i).x = Board.defaultX;
        }
        // Reset the position of the piece to the top
    }
    public int moveDownOverTime() {
        amountFallen += Engine.SPACE_SIZE;
        return amountFallen;
    }

    // add boolean methods for touching each boundary
    public boolean isTouchingBottomBoundary(ArrayList<Rectangle> rect) {
        return lowestYPosition(rect) == BoardManager.getBoard().getBottomOfBoard();
    }

    public boolean shouldPieceStop(ArrayList<Rectangle> previous, ArrayList<Rectangle> current) {
        for(int i = 0; i < previous.size(); i ++){
            for(int u =0; u < current.size(); u ++){
                if(current.get(u).y == previous.get(i).y + Engine.SPACE_SIZE && current.get(u).x >= previous.get(i).x && current.get(u).x <= previous.get(i).x + Engine.SPACE_SIZE){
                    return true;
                }
            }
        }
        return false;
    }

    public int lowestYPosition(ArrayList<Rectangle> rect) {
        int temp = Integer.MAX_VALUE;
        for (Rectangle i : rect) {
            if ((int) i.y < temp) {
                temp = (int) i.y;
            }
        }
        return temp;
    }
    public PieceType getPieceType() {
        return pieceType;
    }

    public Piece getCurrentPiece() {
        return this;
    }
    public Color getColor() {
        return color;
    }

}
