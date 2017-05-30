package com.udacity.wertonguimaraes.quizapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int TOTAL_MAX_POINTS = 4;
    private static final Integer CORRECT_ANSWER_QUESTION1 = R.id.answer_amsterdam;
    private static final Integer[] CORRECT_ANSWER_QUESTION3 = {R.id.answer_cedric, R.id.answer_dobby, R.id.answer_malfoy};

    private RadioButton mCorrectAnswerQuestion1;
    private TextView mCorrectAnswerQuestion2;
    private TextView mCorrectAnswerQuestion4;

    private EditText mAnswerQuestion2;
    private EditText mAnswerQuestion4;

    private Button mCheckAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInitView();
        mInitButtonListeners();
    }

    private void mInitView() {
        mCorrectAnswerQuestion1 = (RadioButton) findViewById(CORRECT_ANSWER_QUESTION1);

        mAnswerQuestion2 = (EditText) findViewById(R.id.answer_total_oscar);
        mCorrectAnswerQuestion2 = (TextView) findViewById(R.id.correct_answer_total_oscar);
        mCorrectAnswerQuestion2.setVisibility(View.GONE);

        mAnswerQuestion4 = (EditText) findViewById(R.id.answer_aids_in_french);
        mCorrectAnswerQuestion4 = (TextView) findViewById(R.id.correct_answer_aids_in_french);
        mCorrectAnswerQuestion4.setVisibility(View.GONE);

        mCheckAnswers = (Button) findViewById(R.id.check_answers);
    }

    private void mInitButtonListeners() {
        mCheckAnswers.setOnClickListener(mCheckAnswersListener);
    }

    private View.OnClickListener mCheckAnswersListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.check_answers:
                    mCheckAnswers();
                    break;
                default:
                    break;
            }
        }
    };

    private void mCheckAnswers() {
        int[] pointsByQuestion = {mCheckQuestion1(), mCheckQuestion2(), mCheckQuestion3(),
                mCheckQuestion4()};

        mShowScoreMessage(mSumPoints(pointsByQuestion));
    }

    private int mSumPoints(int[] pointsByQuestion){
        int sum = 0;

        for (int i : pointsByQuestion)
            sum += i;

        return sum;
    }

    private int mCheckQuestion1() {
        int points = 0;
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group_question1);

        if (rg.getCheckedRadioButtonId() == CORRECT_ANSWER_QUESTION1) {
            points = 1;
        }

        mCorrectAnswerQuestion1.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        return points;
    }

    private int mCheckQuestion2() {
        int points = 0;
        String correctAnswer = mCorrectAnswerQuestion2.getText().toString();

        if (mAnswerQuestion2.getText().toString().equals(correctAnswer)) {
            points = 1;
        }

        mCorrectAnswerQuestion2.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        mCorrectAnswerQuestion2.setVisibility(View.VISIBLE);
        return points;
    }

    private int mCheckQuestion3() {
        Integer[] checkBoxIds = {R.id.answer_cedric, R.id.answer_malfoy, R.id.answer_dobby,
        R.id.answer_jerry, R.id.answer_tk};

        return mCheckAllCheckboxs(checkBoxIds, CORRECT_ANSWER_QUESTION3);
    }

    private int mCheckQuestion4() {
        int points = 0;
        String correctAnswer = mCorrectAnswerQuestion4.getText().toString();

        if (mAnswerQuestion4.getText().toString().toLowerCase().equals(correctAnswer.toLowerCase())) {
            points = 1;
        }

        mCorrectAnswerQuestion4.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        mCorrectAnswerQuestion4.setVisibility(View.VISIBLE);
        return points;
    }

    private int mCheckAllCheckboxs(Integer[] checkBoxIds, Integer[] correctAnswers){
        double totalPoint = 0;
        for (Integer id: checkBoxIds) {
            if (mCheckCheckBox(id, correctAnswers) == 1) {
                totalPoint += 0.2;
            }
        }
        return (int) totalPoint;
    }

    private int mCheckCheckBox(int checkboxId, Integer[] correctAnswers) {
        int points = 1;
        CheckBox cb = (CheckBox) findViewById(checkboxId);


        if ((cb.isChecked() && !Arrays.asList(correctAnswers).contains(checkboxId)) ||
                !cb.isChecked() && Arrays.asList(correctAnswers).contains(checkboxId)) {
            points = 0;
        }

        if (Arrays.asList(correctAnswers).contains(checkboxId)){
            cb.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        }

        return points;
    }

    private void mShowScoreMessage(int totalScore){
        String message;
        if (totalScore == TOTAL_MAX_POINTS) {
            message = getResources().getString(R.string.message_all_answer_is_right);
        } else if (totalScore == 0) {
            message = getResources().getString(R.string.message_all_answer_is_wrong);
        } else if (totalScore == 1) {
            message = getResources().getString(R.string.message_answer_one_question_right);
        } else {
            message = String.format(getResources().getString(R.string.message_answer_any_questions_right), String.valueOf(totalScore));
        }

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
