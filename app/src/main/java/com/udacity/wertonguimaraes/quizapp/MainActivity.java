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

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

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

        mPoints = (TextView) findViewById(R.id.points);
        mPoints.setVisibility(View.GONE);

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
        double[] pointsByQuestion = {mCheckQuestion1(), mCheckQuestion2(), mCheckQuestion3()};

        String updatedPoints = getResources().getText(R.string.points) + String.valueOf(sumPoints(pointsByQuestion));
        mPoints.setText(updatedPoints);
        mPoints.setVisibility(View.VISIBLE);

    }

    private double sumPoints(double[] pointsByQuestion){
        double sum = 0;

        for (double i : pointsByQuestion)
            sum += i;

        return sum;
    }

    private double mCheckQuestion1() {
        double points = 0.0;
        RadioGroup rg = (RadioGroup) findViewById(R.id.radio_group_question1);

        if (rg.getCheckedRadioButtonId() == CORRECT_ANSWER_QUESTION1) {
            points = 1.0;
        }

        correct_answer_question1.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        return points;
    }

    private double mCheckQuestion2() {
        double points = 0.0;

        if (answer_question2.getText().toString().equals("11")) {
            points = 1.0;
        }

        correct_answer_question2.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        correct_answer_question2.setVisibility(View.VISIBLE);
        return points;
    }

    private double mCheckQuestion3() {
        Integer[] check_box_ids = {R.id.answer_cedric, R.id.answer_malfoy, R.id.answer_dobby,
        R.id.answer_jerry, R.id.answer_tk};

        double total_point = 0.0;
        for (Integer id: check_box_ids) {
            total_point += mCheckCheckBox(id);
        }

        return total_point;
    }

    private double mCheckCheckBox(int checkbox_id) {
        double points = 0.0;
        CheckBox cb = (CheckBox) findViewById(checkbox_id);


        if ((cb.isChecked() && Arrays.asList(CORRECT_ANSWER_QUESTION3).contains(checkbox_id)) ||
                !cb.isChecked() && !Arrays.asList(CORRECT_ANSWER_QUESTION3).contains(checkbox_id)) {
            points = 0.2;
        }

        if (Arrays.asList(CORRECT_ANSWER_QUESTION3).contains(checkbox_id)){
            cb.setTextColor(Color.parseColor(getString(Integer.parseInt(String.valueOf(R.color.green)))));
        }


        return points;
    }


}
