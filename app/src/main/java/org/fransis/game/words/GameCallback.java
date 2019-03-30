package org.fransis.game.words;

public interface GameCallback {
    void gameOver();
    void wellDone();
    void character(String word);
    void fail(int ntry);
    void score(int score);
}