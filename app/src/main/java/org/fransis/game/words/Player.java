package org.fransis.game.words;

public class Player {
    private long score = 0;

    public long getScore() {
        return score;
    }

    public long addScore(long delta){
        if(delta > 0) {
            this.score += delta;
        }
        return this.score;
    }

    public Player() {
        this(0);
    }

    public Player(long score) {
        this.score = score;
    }
}
