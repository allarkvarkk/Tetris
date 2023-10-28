package com.mygdx.game.Board.Piece;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Board.Board;
import com.mygdx.game.Engine;

public class PieceCreator extends Board {
    public PieceCreator(Engine tetris) {
        super(tetris);
    }

    public static Piece createPiece(PieceType type) {
        switch(type) {
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
                return new int[][] {
                        {0, 0, 0, 0},
                        {1, 1, 1, 1},
                        {0, 0, 0, 0},
                        {0, 0, 0, 0}
                };
            case J:
                return new int[][] {
                        {1, 0, 0},
                        {1, 1, 1},
                        {0, 0, 0},
                };
            case L:
                return new int[][] {
                        {0, 0, 0},
                        {1, 1, 1},
                        {1, 0, 0},
                };
            case O:
                return new int[][] {
                        {1, 1},
                        {1, 1}
                };
            case S:
                return new int[][] {
                        {0, 1, 1},
                        {1, 1, 0},
                        {0, 0, 0}
                };
            case T:
                return new int[][] {
                        {0, 1, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                };
            case Z:
                return new int[][] {
                        {1, 1, 0},
                        {0, 1, 1},
                        {0, 0, 0}
                };
            default:
                throw new IllegalArgumentException("Invalid TetrominoType");
        }
    }


    public static Piece createRandomPiece() {
        PieceType randomType = PieceType.values()[(int) (Math.random() * PieceType.values().length)];
        int[][] shape = PieceCreator.getShape(randomType);
        return new Piece(shape, 0, 0, 0, randomType, PieceCreator.getColorForPieceType(randomType));
    }
}
