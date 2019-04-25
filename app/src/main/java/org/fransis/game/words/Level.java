package org.fransis.game.words;

public class Level {
    public static final String DEFAULT_ALPHABET = "abcdefghijklmnñopqrstuvwxyz".toUpperCase();
    private String alphabet;
    private String secretWord;
    private String definition;

    public Level(String alphabet, String secretWord, String definition) {
        this.alphabet = alphabet;
        this.secretWord = secretWord;
        this.definition = definition;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public String getDefinition() {
        return definition;
    }
}
