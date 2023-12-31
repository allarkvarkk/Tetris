package com.mygdx.game.Board.Piece;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Board.Board;
import com.mygdx.game.Engine;

import java.util.ArrayList;

public class PieceCreator {
    private Engine tetris;
    private ArrayList<Rectangle> pieceShape;
    private Piece piece;
    private int centerHorizontally, bottomOfBoard;
    public int counter;

    public PieceCreator(Engine tetris, int centerHorizontally, int bottomOfBoard) {
        this.tetris = tetris;
        this.centerHorizontally = centerHorizontally;
        this.bottomOfBoard = bottomOfBoard;
        this.counter = 0; // Initialize the counter to 0
        pieceShape = new ArrayList<Rectangle>();
    }


    public Piece createPiece(PieceType type) {
        switch (type) {
            case I:
                return new IPiece();
            case J:
                return new JPiece();
            case L:
                return new LPiece();
            case O:
                return new OPiece();
            case S:
                return new SPiece();
            case T:
                return new TPiece();
            case Z:
                return new ZPiece();
            default:
                throw new IllegalArgumentException("Invalid piece type");
        }
    }

    public static Color getColorForPieceType(PieceType type) {
        switch (type) {
            case I:
                return Color.CYAN;
            case J:
                return Color.BLUE;
            case L:
                return Color.ORANGE;
            case O:
                return Color.YELLOW;
            case S:
                return Color.GREEN;
            case T:
                return Color.MAGENTA;
            case Z:
                return Color.RED;
            default:
                return Color.BLACK; // Default color for unknown types
        }
    }

    public static int[][] getShape(PieceType type) {
        switch (type) {
            case I:
                return new int[][]{
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0},
                        {0, 1, 0, 0}
                };
            case J:
                return new int[][]{
                        {1, 1, 1},
                        {0, 0, 1},
                        {0, 0, 0}
                };
            case L:
                return new int[][]{
                        {1, 1, 1},
                        {1, 0, 0},
                        {0, 0, 0}
                };
            case O:
                return new int[][]{
                        {0, 1, 1},
                        {0, 1, 1},
                        {0, 0, 0}
                };
            case S:
                return new int[][]{
                        {0, 1, 1},
                        {1, 1, 0},
                        {0, 0, 0}
                };
            case T:
                return new int[][]{
                        {1, 1, 1},
                        {0, 1, 0},
                        {0, 0, 0}
                };
            case Z:
                return new int[][]{
                        {1, 1, 0},
                        {0, 1, 1},
                        {0, 0, 0}
                };
            default:
                throw new IllegalArgumentException("Invalid PieceType");
        }
    }


    public Piece createRandomPiece() {
        PieceType randomType = PieceType.values()[(int) (Math.random() * PieceType.values().length)];
        piece = createPiece(randomType); // Update the piece field
        return piece;
    }

    public ArrayList<Rectangle> createCurrentPieceShape() {
        pieceShape.clear();
        counter = 0;
        int currentX = Board.defaultX; // centerHorizontally - 40;
        int currentY = Board.defaultY;
        for (int i = 0; i < piece.getCurrentShape().length; i++) {
            for (int u = 0; u < piece.getCurrentShape()[i].length; u++) {
                if (piece.getCurrentShape()[i][u] == 1) {
                    pieceShape.add(new Rectangle(currentX, currentY, Engine.SPACE_SIZE, Engine.SPACE_SIZE));
                }
                currentX += Engine.SPACE_SIZE;
            }
            currentY -= Engine.SPACE_SIZE; // Move up to the next row
            currentX =  centerHorizontally - 40; // Reset X to the start of the row
        }
        return pieceShape;
    }

    public void clearPieceShape() {
        pieceShape.clear();
    }

    public ArrayList<Rectangle> getPieceShape() {
        return pieceShape;
    }

}
