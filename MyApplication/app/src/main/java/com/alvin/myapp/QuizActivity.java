package com.alvin.myapp;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    Category currentCategory;
    ArrayList<Thing> things;
    private ImageView mainPicture;
    private RelativeLayout relativeLayout;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private Thing thingAnswer;
    private TextView questionTextView;
    private TextView scoreTextView;
    private MediaPlayer mediaPlayer;

    private int score = 0;
    private int questionNumber = 1;


    private boolean stopUserInteractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        currentCategory = CategoriesActivity.categories.get(position);
        setTheme(currentCategory.theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button

        things = currentCategory.getListOfThings();
        relativeLayout = (RelativeLayout) findViewById(R.id.quizLayout);
        mainPicture = (ImageView) findViewById(R.id.quizImage);
        answer1 = (RadioButton) findViewById(R.id.answer1);
        answer2 = (RadioButton) findViewById(R.id.answer2);
        answer3 = (RadioButton) findViewById(R.id.answer3);
        scoreTextView = (TextView) findViewById(R.id.scoreCounter);
        questionTextView = (TextView) findViewById(R.id.questionCounter);

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);

        updateResources();
    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();

        releaseMediaPlayer();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (stopUserInteractions) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    protected void updateResources() {


        if (questionNumber == 1) {
            scoreTextView.setText("Score: " + 0);
        } else if (questionNumber > 10) {
            this.finish();
            Highscores.open(this);
            if (Highscores.setHighscore(currentCategory.columnName, score))
                Toast.makeText(this, "New Highscore!", Toast.LENGTH_LONG).show();
            Highscores.close();
            return;
        }
        questionTextView.setText("Question: " + questionNumber);
        questionNumber++;
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();

        theme.resolveAttribute(R.attr.colorPrimaryLight, typedValue, true);
        int primaryLightColor = typedValue.data;
        mainPicture.setBackgroundColor(primaryLightColor);
        relativeLayout.setBackgroundColor(primaryLightColor);

        Random r = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < 3) {
            set.add(r.nextInt(things.size()));
        }

        Integer[] answers = set.toArray(new Integer[set.size()]);

        ArrayList<Integer> indexes = new ArrayList<>(Arrays.asList(0, 1, 2));

        int index = indexes.get(r.nextInt(indexes.size()));
        thingAnswer = things.get(answers[index]);

        mainPicture.setVisibility(View.INVISIBLE);
        mainPicture.setImageResource(thingAnswer.getImage());
        mainPicture.setVisibility(View.VISIBLE);

        setRandomAnswer(answer1, indexes, answers);
        setRandomAnswer(answer2, indexes, answers);
        setRandomAnswer(answer3, indexes, answers);
    }

    private void setRandomAnswer(RadioButton button, ArrayList<Integer> indexes, Integer[] answers) {

        Random r = new Random();

        int index = indexes.get(r.nextInt(indexes.size()));

        indexes.remove(Integer.valueOf(index));

        button.setText(things.get(answers[index]).getText());
    }


    @Override
    public void onClick(final View v) {
        if (v instanceof RadioButton) {
            if (((RadioButton) v).getText() == thingAnswer.getText()) {
                score++;
                scoreTextView.setText("Score: " + score);
                playSound(true);
            } else {
                playSound(false);
            }
        }

        stopUserInteractions = true;
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateResources();
                if (v instanceof RadioButton)
                    ((RadioButton) v).setChecked(false);
                stopUserInteractions = false; // enable UI again after the next question is displayed
            }
        }, 2000);

    }


    private void playSound(boolean isCorrect) {
        mediaPlayer = MediaPlayer.create(this,
                isCorrect ? randomCorrectSound() : randomWrongSound());
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                player.reset();
            }
        });
    }


    private int randomCorrectSound() {
        List<Integer> correctSounds = new ArrayList<>();
        correctSounds.add(R.raw.correct1_good_job);
        correctSounds.add(R.raw.correct2_well_done);
        correctSounds.add(R.raw.correct3_perfect);
        correctSounds.add(R.raw.correct4_amazing);
        correctSounds.add(R.raw.correct5_great);
        Random rand = new Random();

        return correctSounds.get(rand.nextInt(correctSounds.size()));
    }

    private int randomWrongSound() {
        List<Integer> wrongSounds = new ArrayList<>();
        wrongSounds.add(R.raw.wrong1_oh_no);
        wrongSounds.add(R.raw.wrong2_try_again);
        wrongSounds.add(R.raw.wrong3_wrong);
        wrongSounds.add(R.raw.wrong4_you_need_some_practice);
        Random rand = new Random();

        return wrongSounds.get(rand.nextInt(wrongSounds.size()));
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
