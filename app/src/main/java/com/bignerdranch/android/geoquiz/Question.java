package com.bignerdranch.android.geoquiz;

/**
 * Created by brian on 1/12/16.
 */


public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(int textResId, boolean answerTrue){

        mTextResId  = textResId;
        mAnswerTrue = answerTrue;
    }
}
