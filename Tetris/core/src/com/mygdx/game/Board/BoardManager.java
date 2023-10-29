package com.mygdx.game.Board;

public class BoardManager {
    private static Board board;

    public static void setBoard(Board board) {
        BoardManager.board = board;
    }

    public static Board getBoard() {
        return board;
    }
}