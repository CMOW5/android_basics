package com.example.cristian.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private static final int mQuestionNumber=5;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String[] KEY_CHEAT = {"cheat1","cheat2","cheat3","cheat4","cheat5"};
    //private static final String[] KEY1_CHEAT = new String[5];

    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
        new Question(R.string.question_oceans,true),
        new Question(R.string.question_mideast,false),
        new Question(R.string.question_africa,false),
        new Question(R.string.question_americas,true),
        new Question(R.string.question_asia,true),
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        // Set a listerner to inform you when  the mTrueButton has been pressed
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = mQuestionBank[mCurrentIndex].isCheat();
                updateQuestion();
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(mCurrentIndex == 0)
                    mCurrentIndex = mQuestionBank.length-1;
                else
                    mCurrentIndex = mCurrentIndex-1;

                mIsCheater = mQuestionBank[mCurrentIndex].isCheat();
                updateQuestion();
            }

        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void  onClick(View v){
                //start Cheatactivity
                //one way (simplest way)
                /*  Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                *   startActivity(i);
                * */
                //another way (recommended)
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                //startActivity(i);
                startActivityForResult(i,REQUEST_CODE_CHEAT);

            }
        });

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            //mQuestionBank[mCurrentIndex].setCheat(savedInstanceState.getBoolean(KEY_CHEAT));
            //mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT);
            for(int i = 0; i<mQuestionBank.length;i++){
                mQuestionBank[i].setCheat(savedInstanceState.getBoolean(KEY_CHEAT[i]));
            }
        }
        updateQuestion();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState(..) called");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        //outState.putBoolean(KEY_CHEAT,mIsCheater);
        //outState.putBoolean(KEY_CHEAT,mQuestionBank[mCurrentIndex].isCheat());
        for(int i = 0; i<mQuestionBank.length;i++){
            outState.putBoolean(KEY_CHEAT[i],mQuestionBank[i].isCheat());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheat(mIsCheater);
        }

    }

    public void updateQuestion(){
        //Log.d(TAG,"updating question text for question #" + mCurrentIndex, new Exception() );
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPresedTrue) {

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        mIsCheater = mQuestionBank[mCurrentIndex].isCheat();

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPresedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
}
