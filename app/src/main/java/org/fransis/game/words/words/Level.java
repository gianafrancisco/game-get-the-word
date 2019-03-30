package org.fransis.game.words.words;

public class Level {
    public static final String DEFAULT_ALPHABET = "abcdefghijklmnñopqrstuvwxyz".toUpperCase();
    private String alphabet;
    private String secretWord;

    public Level(String alphabet, String secretWord) {
        this.alphabet = alphabet;
        this.secretWord = secretWord;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public String getSecretWord() {
        return secretWord;
    }
}
