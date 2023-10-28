package com.mygdx.game.Player;

public class ScoreManager {
    //
    private int score;

    public ScoreManager() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int linesCleared) {
        int[] scoreValues = {0,100,300,500,800};
        if(linesCleared >= 1 && linesCleared <= 4) {
            score += scoreValues[linesCleared];
        }
    }

    public void resetScore() {
        score = 0;
    }
}
