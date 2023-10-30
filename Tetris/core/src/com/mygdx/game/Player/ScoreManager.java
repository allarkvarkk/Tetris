package com.mygdx.game.Player;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Board.BoardManager;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ScoreManager {
    //
    private int score;
    private int rowYPos;
    private ArrayList<Rectangle> stationary;
    private ArrayList<Color> colors;
    private ArrayList<Integer> indicesToRemove;

    public ScoreManager() {
        score = 0;
        rowYPos = 0;
        indicesToRemove = new ArrayList<>();
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int linesCleared) {
        int[] scoreValues = {0, 100, 300, 500, 800};
        if (linesCleared >= 1 && linesCleared <= 4) {
            score += scoreValues[linesCleared];
        }
    }

    public void resetScore() {
        score = 0;
    }

    public void removeRows() {
        indicesToRemove.clear();
        stationary = BoardManager.getBoard().getPreviousPieces();
        colors = BoardManager.getBoard().getPreviousColors();
        int size = stationary.size();  // Get the initial size
        for (int i = BoardManager.getBoard().getBottomOfBoard(); i < BoardManager.getBoard().getTopOfBoard(); i += Engine.SPACE_SIZE) {
            if (isRowComplete(i)) {
                for (int u = 0; u < size; u++) {
                    if (stationary.get(u).getY() == i) {
                        indicesToRemove.add(u);
                    }
                }
            }
        }
        // Remove the elements in reverse order to avoid index issues
        for (int j = indicesToRemove.size() - 1; j >= 0; j--) {
            int indexToRemove = indicesToRemove.get(j);
            stationary.remove(indexToRemove);
            colors.remove(indexToRemove);
        }
    }


    public boolean isRowComplete(int yValue) {
        int counter = 0;
        if (stationary.size() >= 8) {
            for (int i = 0; i < stationary.size(); i++) {
                counter = 1;
                for (int u = 0; u < stationary.size(); u++) {
                    if (i != u) {
                        if (stationary.get(i).y == stationary.get(u).y && (int) stationary.get(i).y == yValue) {
                            counter++;
                        }
                    }
                }
            }

        }
        return counter >= 10;
    }
}