package com.mygdx.game.Board.Piece;

public class TPiece extends Piece {
    public TPiece() {
        super(PieceCreator.getShape(PieceType.T), 0, 0, 0, PieceType.T, PieceCreator.getColorForPieceType(PieceType.T));
    }
}
