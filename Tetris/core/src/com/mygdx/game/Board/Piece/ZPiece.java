package com.mygdx.game.Board.Piece;

public class ZPiece extends Piece {
    public ZPiece() {
        super(PieceCreator.getShape(PieceType.Z), 0, PieceType.Z, PieceCreator.getColorForPieceType(PieceType.Z));
    }
}
