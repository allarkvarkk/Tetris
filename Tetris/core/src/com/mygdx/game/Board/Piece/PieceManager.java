package com.mygdx.game.Board.Piece;

public class PieceManager {
    private static Piece piece;

    public static void setPiece(Piece piece) {
        PieceManager.piece = piece;
    }

    public static Piece getPiece() {
        return piece;
    }
}