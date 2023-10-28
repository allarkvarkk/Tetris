package com.mygdx.game.Board.Piece;

public class IPiece extends Piece {

    public IPiece() {
        super(PieceCreator.getShape(PieceType.I), 0, 0, 0, PieceType.I, PieceCreator.getColorForPieceType(PieceType.I));
    }

}
