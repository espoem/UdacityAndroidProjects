package com.example.android.quizapp;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CompoundButton> buttons = new ArrayList<>();
    private ArrayList<EditText> editTexts = new ArrayList<>();
    private int maxScore;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button czechAnthemButton = (Button) findViewById(R.id.czech_anthem_button);
        final MediaPlayer mpCzech = MediaPlayer.create(this, R.raw.czech_anthem_instrumental);
        czechAnthemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpCzech.isPlaying()) {
                    mpCzech.pause();
                } else {
                    mpCzech.start();
                }
            }
        });

        Button usaAnthemButton = (Button) findViewById(R.id.usa_anthem_button);
        final MediaPlayer mpUSA = MediaPlayer.create(this, R.raw.usa_anthem);
        usaAnthemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpUSA.isPlaying()) {
                    mpUSA.pause();
                } else {
                    mpUSA.start();
                }
            }
        });

        int[] idsButtons = {R.id.czech_button_1, R.id.czech_button_2, R.id.czech_button_3, R.id
                .czech_button_4, R.id.us_button_1, R.id.us_button_2, R.id.us_button_3, R.id
                .us_button_4, R.id.uk_button_1, R.id.uk_button_2, R.id.uk_button_3, R.id
                .uk_button_4};
        int[] idsEditTexts = {R.id.charles_wife_edit_text, R.id.czech_capital_edit_text};
        int[] idsAll = Arrays.copyOf(idsButtons, idsButtons.length + idsEditTexts.length);
        System.arraycopy(idsEditTexts, 0, idsAll, idsButtons.length, idsEditTexts.length);

        computeMaxScore(idsAll);

        setupButtons(idsButtons);
        setupEditTexts(idsEditTexts);
    }

    /**
     * Computes maximum score in QuizApp
     *
     * @param ids
     */
    private void computeMaxScore(int[] ids) {
        for (int id : ids) {
            TextView cb = (TextView) findViewById(id);
            if (cb.getTag() != null) {
                maxScore++;
            }
        }
    }

    /**
     * Finds ids of buttons.
     *
     * @param ids
     */
    private void setupButtons(int[] ids) {
        for (int id : ids) {
            CompoundButton cb = (CompoundButton) findViewById(id);
            buttons.add(cb);
        }
    }

    /**
     * Finds ids of editTexts.
     *
     * @param ids
     */
    private void setupEditTexts(int[] ids) {
        for (int id : ids) {
            EditText et = (EditText) findViewById(id);
            editTexts.add(et);
        }
    }

    /**
     * Submits answers.
     *
     * @param view
     */
    public void submit(View view) {
        score = 0;
//        check radio buttons and checkboxes
        for (CompoundButton button : buttons) {
            if (button.getTag() != null) {
                if (button.isChecked()) {
                    score++;
                    button.setBackgroundColor(Color.GREEN);
                } else {
                    button.setBackgroundColor(Color.RED);
                }
            }
        }

//        check edit texts
        for (EditText et : editTexts) {
            switch (et.getId()) {
                case R.id.charles_wife_edit_text:
                    checkEditTexts(et, R.id.charles_wife_answer_text_view, "diana",
                            "Princess Diana");
                    break;
                case R.id.czech_capital_edit_text:
                    checkEditTexts(et, R.id.czech_capital_answer_text_view, "prague",
                            "Prague");
                    break;
                default:
                    break;
            }
        }

//        print result
        TextView tv = (TextView) findViewById(R.id.final_score_text_view);
        String msg = "Final score: " + score + " of " + maxScore;
        tv.setText(msg);
        tv.setVisibility(View.VISIBLE);
    }

    private void checkEditTexts(EditText et, int idAnswerText, String keyWord,
                                String correctAnswer) {
        TextView tv = (TextView) findViewById(idAnswerText);
        if (et.getText().toString().toLowerCase().contains(keyWord)) {
            et.setBackgroundColor(Color.GREEN);
            if (tv.getVisibility() == View.VISIBLE) {
                tv.setVisibility(View.GONE);
            }
            score++;
        } else {
            et.setBackgroundResource(0);
            tv.setBackgroundColor(Color.RED);
            tv.setText(String.valueOf("Correct answer: " + correctAnswer));
            tv.setVisibility(View.VISIBLE);
        }
    }
}
