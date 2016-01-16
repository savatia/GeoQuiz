package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class QuizActivity extends AppCompatActivity{

    private  static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private  static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;
    private  boolean mIsCheater;

    private void updateQuestion(){
        mQuestionTextView.setText(
                (mQuestionBank[mCurrentIndex]).getTextResId()
        );
    }

    private void checkAnswer(boolean userPressedTrue){
        int messageResId = 0;

        if(mIsCheater){
            messageResId = R.string.judgement_toast;
        }
        else {
            if(mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressedTrue)
                messageResId =  R.string.correct_toast;
            else
                messageResId =  R.string.incorrect_toast;
        }

        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.d(TAG, "OnCreate(Bundle) called.");

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            }
        });


        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);

        mTrueButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
               checkAnswer(true);
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               checkAnswer(false);
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }

        });

        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;

                if(mCurrentIndex < 0)
                    mCurrentIndex += mQuestionBank.length;

                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue );
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }

        });

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called.");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called.");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG , "onResume() called.");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called.");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called.");
    }



}