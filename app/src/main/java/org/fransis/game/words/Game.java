package org.fransis.game.words;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public enum Status {
        CORRECT,
        INCORRECT,
        UNCLICKED
    }

    public class CharStatus {
        private char mData;
        private Status mStatus;
        CharStatus(char data) {
            this.mData = data;
            this.mStatus = Status.UNCLICKED;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CharStatus that = (CharStatus) o;
            return mData == that.mData;
        }

        @Override
        public int hashCode() {
            return mData;
        }

        public char getData() {
            return mData;
        }

        public Status getStatus() {
            return mStatus;
        }

        public void setCorrect() {
            this.mStatus = Status.CORRECT;
        }

        public void setIncorrect() {
            this.mStatus = Status.INCORRECT;
        }

        @Override
        public String toString() {
            return mData +"";
        }
    }

    private int mMaxTry;
    private int mNTry;
    private StringBuilder mWord;
    private StringBuilder mCharPressed;
    private GameCallback mCallback = null;
    private Level mCurrent;
    private List<CharStatus> mCharStatusList;


    public Game(Level level){
        this.reset(level);
    }

    public void reset(Level level){
        mMaxTry = 5;
        mNTry = 0;
        mCurrent = level;
        mCharPressed = new StringBuilder();
        mWord = new StringBuilder();
        for (int i = 0; i< mCurrent.getSecretWord().length(); i++) {
            mWord.append("_");
        }
        mCharStatusList = new ArrayList<>(mCurrent.getAlphabet().length());
        String alphabet = mCurrent.getAlphabet();
        for (int i = 0; i < alphabet.length(); i++) {
            mCharStatusList.add(new CharStatus(alphabet.charAt(i)));
        }
    }

    public String getWord(){
        return mWord.toString();
    }

    public String getAlphabet(){
        return mCurrent.getAlphabet();
    }

    public List<CharStatus> getAlphabetStatus() {
        return mCharStatusList;
    }

    public int getNTry(){
        return mMaxTry - mNTry;
    }

    public void newTry(String letter){
        if(mCharPressed.indexOf(letter) < 0){
            mCharPressed.append(letter);
            int position = mCharStatusList.indexOf(new CharStatus(letter.charAt(0)));
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
                            mCharStatusList.get(position).setCorrect();
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
                        if(mCallback != null) {
                            mCharStatusList.get(position).setIncorrect();
                            mCallback.fail(mMaxTry - mNTry);
                        }
                        else {
                            throw new RuntimeException("Callback is null");
                        }
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

    public String getCharPressed() {
        return mCharPressed.toString();
    }

    public void setCallback(GameCallback callback) {
        if(callback != null)
            this.mCallback = callback;
        else
            throw new RuntimeException("Callback is null");

    }

    public String getLevelDefinition(){
        return mCurrent.getDefinition();
    }
}

