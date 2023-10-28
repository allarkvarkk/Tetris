package com.mygdx.game.Board.Piece;

public class PieceCreator {
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
}
