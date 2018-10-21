package org.fransis.game.words.words;

public class Game {
    private int maxTry;
    private int nTry;
    private StringBuilder word;
    private StringBuilder charPressed;
    private GameCallback callback = null;
    private Level current;

    public Game(Level level){
        maxTry = 5;
        nTry = 0;
        this.current = level;
        charPressed = new StringBuilder();
        word = new StringBuilder();
        for (int i= 0; i< current.getSecretWord().length(); i++){
            word.append("_");
        }
    }

    public String getWord(){
        return word.toString();
    }

    public String getAlphabet(){
        return current.getAlphabet();
    }

    public int getnTry(){
        return maxTry - nTry;
    }

    public void newTry(String letter){
        if(charPressed.indexOf(letter) < 0){
            charPressed.append(letter);
            if(nTry < maxTry){
                if(word.indexOf(letter) < 0){
                    boolean found = false;
                    int index = current.getSecretWord().indexOf(letter);
                    while( index >= 0 ) {
                        found = true;
                        word.replace(index, index + 1, letter);
                        index = current.getSecretWord().indexOf(letter, index + 1);
                    }
                    if(found){
                        if(callback != null)
                            callback.character(word.toString());
                        else
                            throw new RuntimeException("Callback is null");
                        if(word.indexOf("_") < 0){
                            callback.wellDone();
                        }
                    }else{
                        nTry++;
                        if(callback != null)
                            callback.fail(maxTry - nTry);
                        else
                            throw new RuntimeException("Callback is null");

                    }
                }
            }
            if(nTry == maxTry){
                if(callback != null)
                    callback.gameOver();
                else
                    throw new RuntimeException("Callback is null");
            }
        }
    }

    public void setCallback(GameCallback callback) {
        if(callback != null)
            this.callback = callback;
        else
            throw new RuntimeException("Callback is null");

    }
}

