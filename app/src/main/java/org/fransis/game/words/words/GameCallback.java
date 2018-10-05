package org.fransis.game.words.words;

public interface GameCallback {
    void gameOver();
    void wellDone();
    void letter(String word);
    void fail(int ntry);
}