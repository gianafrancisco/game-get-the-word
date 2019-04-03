package org.fransis.game.words;

public class Game {
    private int mMaxTry;
    private int mNTry;
    private StringBuilder mWord;
    private StringBuilder mCharPressed;
    private GameCallback mCallback = null;
    private Level mCurrent;


    public Game(Level level){
        this.reset(level);
    }

    public void reset(Level level){
        mMaxTry = 5;
        mNTry = 0;
        this.mCurrent = level;
        mCharPressed = new StringBuilder();
        mWord = new StringBuilder();
        for (int i = 0; i< mCurrent.getSecretWord().length(); i++){
            mWord.append("_");
        }
    }

    public String getWord(){
        return mWord.toString();
    }

    public String getAlphabet(){
        return mCurrent.getAlphabet();
    }

    public int getNTry(){
        return mMaxTry - mNTry;
    }

    public void newTry(String letter){
        if(mCharPressed.indexOf(letter) < 0){
            mCharPressed.append(letter);
            if(mNTry < mMaxTry){
                if(mWord.indexOf(letter) < 0){
                    boolean found = false;
                    int index = mCurrent.getSecretWord().indexOf(letter);
                    while( index >= 0 ) {
                        found = true;
                        mWord.replace(index, index + 1, letter);
                        index = mCurrent.getSecretWord().indexOf(letter, index + 1);
                    }
                    if(found){
                        if(mCallback != null) {
                            mCallback.character(mWord.toString());
                            if (mWord.indexOf("_") < 0) {
                                mCallback.wellDone();
                                switch (mNTry) {
                                    case 0:
                                        mCallback.score(GameScore.MAX);
                                        break;
                                    case 1:
                                        mCallback.score(GameScore.S4);
                                        break;
                                    case 2:
                                        mCallback.score(GameScore.S3);
                                        break;
                                    case 3:
                                        mCallback.score(GameScore.S2);
                                        break;
                                    case 4:
                                        mCallback.score(GameScore.S1);
                                        break;
                                }
                            }
                        }
                        else
                            throw new RuntimeException("Callback is null");

                    }else{
                        mNTry++;
                        if(mCallback != null)
                            mCallback.fail(mMaxTry - mNTry);
                        else
                            throw new RuntimeException("Callback is null");

                    }
                }
            }
            if(mNTry == mMaxTry){
                if(mCallback != null)
                    mCallback.gameOver();
                else
                    throw new RuntimeException("Callback is null");
            }
        }
    }

    public void setCallback(GameCallback callback) {
        if(callback != null)
            this.mCallback = callback;
        else
            throw new RuntimeException("Callback is null");

    }
}

