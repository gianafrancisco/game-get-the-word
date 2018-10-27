package org.fransis.game.words.words;

public interface GameCallback {
    void gameOver();
    void wellDone();
    void character(String word);
    void fail(int ntry);
}