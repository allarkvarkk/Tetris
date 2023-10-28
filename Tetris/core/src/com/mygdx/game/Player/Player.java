package com.mygdx.game.Player;

public class Player {
    private String name;
    private int score;
    private int level;
    private ScoreManager scoreManager;

    public Player(String name, int score, int level) {
        this.name = name;
        this.score = score;
        this.level = level;
        this.scoreManager = new ScoreManager();
    }

    public Player(String name) {
        this(name, 0, 1);
    }

    public Player(int score, int level) {
        this(null, score, level);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public void updateScore(int linesCleared) {
        scoreManager.updateScore(linesCleared);
    }

    public int getLevel() {
        return level;
    }

    public void increaseLevel(int num) {
        level += num;
    }

    public void nextLevel() {
        level++;
    }
}
