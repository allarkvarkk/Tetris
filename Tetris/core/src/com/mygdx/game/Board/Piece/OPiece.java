package com.mygdx.game.Board.Piece;

public class OPiece extends Piece {
    public OPiece() {
        super(PieceCreator.getShape(PieceType.O), 0, 0, 0, PieceType.O, PieceCreator.getColorForPieceType(PieceType.O));
    }
}
