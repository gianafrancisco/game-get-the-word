package org.fransis.game.words.words;

public class Player {
    private int score = 0;


    public int getScore() {
        return score;
    }

    public int addScore(int delta){
        if(delta > 0) {
            this.score += delta;
        }
        return this.score;
    }
}
