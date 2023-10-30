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

    private ArrayList<Rectangle> pieceShape;
    private ArrayList<Rectangle> stationary;
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
    }
    public Piece(int[][] shape) {
        this(shape, 0, PieceType.Z, Color.BLACK);
    }

    public Piece(Piece piece) {
        this(piece.getCurrentShape(), piece.getCurrentRotation(), piece.getPieceType(), piece.getColor());
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
        pieceShape = BoardManager.getBoard().getMovingPiece();
        ArrayList<Rectangle> pieceShape = BoardManager.getBoard().getMovingPiece();
        boolean canMoveRight = true, canMoveLeft = true;
        deltaX *= Engine.SPACE_SIZE;
        deltaY *= Engine.SPACE_SIZE;
        moveToEmptyRows();
        if (!isTouchingBottomBoundary(pieceShape) && (BoardManager.getBoard().getPreviousPieces().isEmpty()
                || !shouldPieceStop(BoardManager.getBoard().getPreviousPieces(), pieceShape))){
            for (int i = 0; i < pieceShape.size(); i++) {
                canMoveLeft = canMoveRight = true;
                if(isCollidingOnLeft(BoardManager.getBoard().getPreviousPieces(), pieceShape)){
                    canMoveLeft = false;
                    canMoveRight = true;
                }
                if(isCollidingOnRight(BoardManager.getBoard().getPreviousPieces(), pieceShape)){
                    canMoveLeft = true;
                    canMoveRight = false;
                }
                pieceShape.get(i).y += deltaY;
            }
        } else {
            BoardManager.getBoard().addPreviousColors(BoardManager.getBoard().getCurrentColor());
            BoardManager.getBoard().addPreviousPieces(pieceShape);
            BoardManager.getBoard().createMovementPiece();
        }
        for(int i = 0; i < pieceShape.size(); i ++){
            if(canMoveLeft && canMoveRight) {
                pieceShape.get(i).x += deltaX;
            }
            if(canMoveLeft && !canMoveRight && deltaX < 0){
                pieceShape.get(i).x += deltaX;
            }
            if(!canMoveLeft && canMoveRight && deltaX > 0){
                pieceShape.get(i).x += deltaX;
            }

        }
    }


    public boolean isCollidingOnLeft(ArrayList<Rectangle> previous, ArrayList<Rectangle> current) {
        for(Rectangle prev : previous){
            for(Rectangle curr : current) {
                if(curr.x == prev.x + Engine.SPACE_SIZE && curr.y == prev.y){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCollidingOnRight(ArrayList<Rectangle> previous, ArrayList<Rectangle> current) {
        for(Rectangle prev : previous){
            for(Rectangle curr : current) {
                if(curr.x + Engine.SPACE_SIZE == prev.x && curr.y == prev.y){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean shouldPieceStop(ArrayList<Rectangle> previous, ArrayList<Rectangle> current) {
        for (Rectangle prev : previous) {
            for (Rectangle curr : current) {
                if (curr.y == prev.y + Engine.SPACE_SIZE && curr.x == prev.x) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean touchingBottom(ArrayList<Rectangle> previous, ArrayList<Rectangle> current){
        for (Rectangle prev : previous) {
            for (Rectangle curr : current) {
                if (curr.y == prev.y && curr.x == prev.x) {
                    return true;
                }
            }
        }
        return false;
    }
    public int checkSideCollision(ArrayList<Rectangle> previous, ArrayList<Rectangle> current){
        for (Rectangle prev : previous) {
            for (Rectangle curr : current) {
                if(curr.y == prev.y && curr.x + Engine.SPACE_SIZE == prev.x){
                    return 1;
                }
                if(curr.y == prev.y && curr.x == prev.x + Engine.SPACE_SIZE){
                    return 2;
                }
            }
        }
        return 0;
    }

    public void updatePieceShape() {
        BoardManager.getBoard().getMovingPiece().clear();
        int currentX = Board.defaultX;
        int currentY = Board.defaultY;

        for (int i = 0; i < shape.length; i++) {
            for (int u = 0; u < shape[i].length; u++) {
                if (shape[i][u] == 1) {
                    BoardManager.getBoard().getMovingPiece().add(new Rectangle(currentX, currentY, Engine.SPACE_SIZE, Engine.SPACE_SIZE));
                }
                currentX += Engine.SPACE_SIZE;
            }
            currentY -= Engine.SPACE_SIZE;
            currentX = Board.defaultX;
        }
    }
    public void rotateClockwise() {
        int[][] currentShape = getCurrentShape();
        int[][] newShape = new int[currentShape.length][currentShape[0].length];

        float originalX = getX();
        float originalY = getY();

        for (int i = 0; i < currentShape.length; i++) {
            for (int j = 0; j < currentShape[0].length; j++) {
                newShape[j][currentShape.length - 1 - i] = currentShape[i][j];
            }
        }

        shape = newShape;
        updatePieceShape();
        setPosition(originalX, originalY);
    }

    public void rotateCounterClockwise() {

        int[][] originalShape = getCurrentShape();
        int[][] newShape = new int[originalShape[0].length][originalShape.length];

        float originalX = getX();
        float originalY = getY();

        for (int i = 0; i < originalShape.length; i++) {
            for (int j = 0; j < originalShape[0].length; j++) {
                newShape[originalShape[0].length - 1 - j][i] = originalShape[i][j];
            }
        }

        shape = newShape;
        updatePieceShape();
        setPosition(originalX, originalY);
    }

    public boolean isRotationValid() {
        // Make a copy of the piece
        Piece rotatedPiece = this.copy();

        // Rotate the copy
        rotatedPiece.rotateClockwise();

        // Get the current positions of the piece shapes
        ArrayList<Rectangle> previousPieces = BoardManager.getBoard().getPreviousPieces();
        ArrayList<Rectangle> currentPiece = BoardManager.getBoard().getMovingPiece();
        // Check if any of the shapes in the rotated piece will intersect with placed pieces
        for (Rectangle currentShape : currentPiece) {
            for (Rectangle previousPiece : previousPieces) {
                if (currentShape.overlaps(previousPiece)) {
                    return false; // Rotation is not valid
                }
            }
        }
        return true; // Rotation is valid
    }

    public float getX() {
        // Implement this method to get the current X position of the piece
        // You can calculate this based on the position of the pieceShape
        // Return the X coordinate of the leftmost piece shape
        float minX = Float.MAX_VALUE;
        for (Rectangle rect : BoardManager.getBoard().getMovingPiece()) {
            if (rect.x < minX) {
                minX = rect.x;
            }
        }
        return minX;
    }

    public float getY() {
        // Implement this method to get the current Y position of the piece
        // You can calculate this based on the position of the pieceShape
        // Return the Y coordinate of the lowest piece shape
        float minY = Float.MAX_VALUE;
        for (Rectangle rect : BoardManager.getBoard().getMovingPiece()) {
            if (rect.y < minY) {
                minY = rect.y;
            }
        }
        return minY;
    }

    public void setPosition(float x, float y) {
        // Implement this method to set the position of the piece
        // Update the positions of all piece shapes based on the new (x, y) coordinates
        float currentX = getX();
        float currentY = getY();
        float deltaX = x - currentX;
        float deltaY = y - currentY;

        for (Rectangle rect : BoardManager.getBoard().getMovingPiece()) {
            rect.x += deltaX;
            rect.y += deltaY;
        }
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

    public int howManyEmptyBottomRows(){
        int rows = 0;
        int tempY = 0;
        if(!stationary.isEmpty()) {
            tempY = Integer.MAX_VALUE;
            for (int i = 0; i < stationary.size(); i++) {
                if ((int) stationary.get(i).y < tempY) {
                    tempY = (int) stationary.get(i).y;
                }
            }

            System.out.println("temp " + tempY);
            rows = (tempY - BoardManager.getBoard().getBottomOfBoard()) / Engine.SPACE_SIZE;
        }
        System.out.println("rows left " + rows);
        return rows;
    }
    public void moveToEmptyRows(){
        stationary = BoardManager.getBoard().getPreviousPieces();
        int rowsToMove = howManyEmptyBottomRows();
        if(rowsToMove > 0 && !stationary.isEmpty()) {
            for (int i = 0; i < stationary.size(); i++) {
                stationary.get(i).y -= Engine.SPACE_SIZE * rowsToMove;
            }
        }
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
