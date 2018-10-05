package org.fransis.game.words.words;

public class Game {
    private String alphabet = "abcdefghijklmnñopqrstuvwxyz".toUpperCase();
    private String secretWord = "Chucrut".toUpperCase();;
    private int maxTry = 5;
    private int nTry = 0;
    private StringBuilder word = null;

    private GameCallback callback = null;

    public Game(){
        word = new StringBuilder();
        for (int i= 0; i< secretWord.length(); i++){
            word.append("_");
        }
    }

    public String getWord(){
        return word.toString();
    }

    public String getAlphabet(){
        return alphabet;
    }

    public int getnTry(){
        return maxTry - nTry;
    }

    public void newTry(String letter){
        if(nTry < maxTry){
            if(word.indexOf(letter) < 0){
                boolean found = false;
                int index = secretWord.indexOf(letter);
                while( index >= 0 ) {
                    found = true;
                    word.replace(index, index + 1, letter);
                    index = secretWord.indexOf(letter, index + 1);
                }
                if(found){
                    callback.letter(word.toString());

                    if(word.indexOf("_") < 0){
                        callback.wellDone();
                    }
                }else{
                    nTry++;
                    callback.fail(maxTry - nTry);
                }
            }
        }
        if(nTry == maxTry){
            callback.gameOver();
        }
    }

    public void setCallback(GameCallback callback) {
        this.callback = callback;
    }
}

