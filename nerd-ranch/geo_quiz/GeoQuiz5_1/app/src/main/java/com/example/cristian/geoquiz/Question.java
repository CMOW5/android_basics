package com.example.cristian.geoquiz;

/**
 * Created by root on 12/28/16.
 */

public class Question {

    private int mTextResId; /*mTextResId variable will hold the resource ID
                            (always an int ) of a string resource for the question */
    private boolean mAnswerTrue;
    private boolean mCheat;

    public Question (int textResId, boolean answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mCheat = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isCheat() {
        return mCheat;
    }

    public void setCheat(boolean cheat) {
        mCheat = cheat;
    }
}
