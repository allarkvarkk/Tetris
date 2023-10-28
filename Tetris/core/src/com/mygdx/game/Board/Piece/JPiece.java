package com.mygdx.game.Board.Piece;

public class JPiece extends Piece {
    public JPiece() {
        super(PieceCreator.getShape(PieceType.J), 0, 0, 0, PieceType.J, PieceCreator.getColorForPieceType(PieceType.J));
    }
}
