package com.alvin.myapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ThingsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton rightButton;
    private ImageButton leftButton;
    private ImageView quizButton;
    private ImageView mainPicture;
    private TextView mainName;
    private ImageButton audioButton;
    private RelativeLayout relativeLayout;
    private MediaPlayer mediaPlayer;
    Thing currentThing;
    Category currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();//return the intent that started this activity
        int position = intent.getIntExtra("position", 0);
        currentCategory = CategoriesActivity.categories.get(position);
        currentCategory.goToFirstThing();
        setTheme(currentCategory.theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainName = (TextView) findViewById(R.id.thingName);
        mainPicture = (ImageView) findViewById(R.id.thingImage);
        rightButton = (ImageButton) findViewById(R.id.buttonRightThing);
        leftButton = (ImageButton) findViewById(R.id.buttonLeftThing);
        audioButton = (ImageButton) findViewById(R.id.buttonAudioThing);
        relativeLayout = (RelativeLayout) findViewById(R.id.thingLayout);
        quizButton = (ImageView) findViewById(R.id.buttonQuiz);

        rightButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        audioButton.setOnClickListener(this);
        mainPicture.setOnClickListener(this);
        quizButton.setOnClickListener(this);

        currentThing = currentCategory.currentThing();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLeftThing:
                currentThing = currentCategory.prevThing();
                updateResources();
                break;
            case R.id.buttonRightThing:
                currentThing = currentCategory.nextThing();
                updateResources();
                break;
            case R.id.buttonAudioThing:
                playSound(currentThing.getSound());
                break;
            case R.id.thingImage:
                if (currentThing.hasNoise()) {
                    playSound(currentThing.getNoise());
                }
                break;
            case R.id.buttonQuiz:
                Intent previousIntent = getIntent();
                int position = previousIntent.getIntExtra("position", 0);
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                break;
        }
    }

    protected void updateResources() {
        if (currentThing.hasNoise()) {
            playSound(currentThing.getNoise());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer player) {
                    player.reset();
                    playSound(currentThing.getSound());
                }
            });
        } else
            playSound(currentThing.getSound());

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();

        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        int accentColor = typedValue.data;

        setButtonColor(leftButton, accentColor);
        setButtonColor(rightButton, accentColor);
        setButtonColor(audioButton, accentColor);

        theme.resolveAttribute(R.attr.colorPrimaryLight, typedValue, true);
        int primaryLightColor = typedValue.data;

        mainPicture.setBackgroundColor(primaryLightColor);
        relativeLayout.setBackgroundColor(primaryLightColor);
        mainName.setBackgroundColor(primaryLightColor);
        setTitle(currentCategory.title);

        mainPicture.setVisibility(View.INVISIBLE);
        mainPicture.setImageResource(currentThing.getImage());
        mainPicture.setVisibility(View.VISIBLE);
        quizButton.setImageResource(currentCategory.quizImage);
        mainName.setText(currentThing.getText());

        rightButton.setVisibility(currentCategory.hasNextThing() ? View.VISIBLE : View.INVISIBLE);
        leftButton.setVisibility(currentCategory.hasPrevThing() ? View.VISIBLE : View.INVISIBLE);
    }

    private void setButtonColor(ImageButton button, int color) {
        GradientDrawable bgShape = (GradientDrawable) button.getBackground();
        bgShape.setColor(color);
    }


    private void playSound(int sound) {
        // if the player is in the middle of playing another sound/noise
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            stopAndResetPlayer();
        }
        mediaPlayer = MediaPlayer.create(this, sound);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                player.reset();
            }
        });
    }


    private void stopAndResetPlayer() {
        mediaPlayer.stop();
        mediaPlayer.reset();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
