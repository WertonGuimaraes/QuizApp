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
    private final int TOTAL_MAX_POINTS = 3;
    private final Integer CORRECT_ANSWER_QUESTION1 = R.id.answer_amsterdam;
    private final Integer[] CORRECT_ANSWER_QUESTION3 = {R.id.answer_cedric, R.id.answer_dobby, R.id.answer_malfoy};

    private RadioButton correct_answer_question1;
    private TextView correct_answer_question2;

    private EditText answer_question2;
    private TextView mPoints;

    private Button mCheckAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInitView();
        mInitButtonListeners();
    }

    private void mInitView() {
        correct_answer_question1 = (RadioButton) findViewById(CORRECT_ANSWER_QUESTION1);

        answer_question2 = (EditText) findViewById(R.id.answer_total_oscar);
        correct_answer_question2 = (TextView) findViewById(R.id.correct_answer_total_oscar);
        correct_answer_question2.setVisibility(View.GONE);

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
        int[] pointsByQuestion = {mCheckQuestion1(), mCheckQuestion2(), mCheckQuestion3()};

        showScoreMessage(sumPoints(pointsByQuestion));
    }

    private int sumPoints(int[] pointsByQuestion){
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

        correct_answer_question1.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        return points;
    }

    private int mCheckQuestion2() {
        int points = 0;

        if (answer_question2.getText().toString().equals("11")) {
            points = 1;
        }

        correct_answer_question2.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        correct_answer_question2.setVisibility(View.VISIBLE);
        return points;
    }

    private int mCheckQuestion3() {
        Integer[] check_box_ids = {R.id.answer_cedric, R.id.answer_malfoy, R.id.answer_dobby,
        R.id.answer_jerry, R.id.answer_tk};

        return check_all_checkboxs(check_box_ids, CORRECT_ANSWER_QUESTION3);
    }

    private int check_all_checkboxs(Integer[] check_box_ids, Integer[] correct_answers){
        double total_point = 0;
        for (Integer id: check_box_ids) {
            if (mCheckCheckBox(id, correct_answers) == 1) {
                total_point += 0.2;
            }
        }
        return (int) total_point;
    }

    private int mCheckCheckBox(int checkbox_id, Integer[] correct_answers) {
        int points = 1;
        CheckBox cb = (CheckBox) findViewById(checkbox_id);


        if ((cb.isChecked() && !Arrays.asList(correct_answers).contains(checkbox_id)) ||
                !cb.isChecked() && Arrays.asList(correct_answers).contains(checkbox_id)) {
            points = 0;
        }

        if (Arrays.asList(correct_answers).contains(checkbox_id)){
            cb.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        }

        return points;
    }

    private void showScoreMessage(int total_score){
        String message;
        if (total_score == TOTAL_MAX_POINTS) {
            message = getResources().getString(R.string.message_all_answer_is_right);
        } else if (total_score == 0) {
            message = getResources().getString(R.string.message_all_answer_is_wrong);
        } else if (total_score == 1) {
            message = getResources().getString(R.string.message_answer_one_question_right);
        } else {
            message = getResources().getString(R.string.message_answer_any_questions_right);
            message = message.replace("{}", String.valueOf(total_score));
        }

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}
