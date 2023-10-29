package com.mygdx.game.Board.Piece;

public class SPiece extends Piece {
    public SPiece() {
        super(PieceCreator.getShape(PieceType.S), 0, PieceType.S, PieceCreator.getColorForPieceType(PieceType.S));
    }
}
