package com.mygdx.game.Board.Piece;

public class LPiece extends Piece {
    public LPiece() {
        super(PieceCreator.getShape(PieceType.L), 0, PieceType.L, PieceCreator.getColorForPieceType(PieceType.L));
    }
}
