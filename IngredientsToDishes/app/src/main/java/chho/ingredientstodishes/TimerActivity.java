package chho.ingredientstodishes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Gary on 2/1/2016.
 */
public class TimerActivity extends Activity {
    private ImageButton fav;
    private ImageButton ingred;
    private ImageButton recipe;
    private Button start;
    private Button pause;
    private Button reset;
    private CountDownTimer countDownTimer;
    private TextView clock;

    private Button setTime;
    private EditText hour;
    private EditText min;
    private EditText sec;
    private long totalTimeCountInMilliseconds;
    private long current;
    private boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.timer_activity);

        fav = (ImageButton)findViewById(R.id.FavoriteIcon);
        ingred = (ImageButton)findViewById(R.id.IngredientsIcon);
        recipe = (ImageButton)findViewById(R.id.RecipeIcon);
        start = (Button) findViewById(R.id.timerStart);
        pause = (Button) findViewById(R.id.timerPause);
        reset = (Button) findViewById(R.id.timerReset);
        clock = (TextView) findViewById(R.id.clock);
        hour = (EditText) findViewById(R.id.hour);
        min = (EditText) findViewById(R.id.minute);
        sec = (EditText) findViewById(R.id.second);
        setTime = (Button) findViewById(R.id.settimebutton);


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });
        ingred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, IngredientsActivity.class);
                startActivity(intent);
            }
        });
        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimerActivity.this, SelectingRecipeActivity.class);
                startActivity(intent);
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countDownTimer != null)
                    countDownTimer.cancel();
                pause.setText("Pause");
                isPaused = false;
                current = 0;
                setTimer();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countDownTimer != null)
                    countDownTimer.cancel();
                pause.setText("Pause");
                isPaused = false;
                current = 0;
                setTimer();
                startTimer();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isPaused) {
                    isPaused = true;
                    pause.setText("Resume");
                    countDownTimer.cancel();
                }
                else {
                    isPaused = false;
                    pause.setText("Pause");
                    countDownTimer = new CountDownTimer(current, 500) {
                        // 500 means, onTick function will be called at every 500
                        // milliseconds

                        @Override
                        public void onTick(long leftTimeInMilliseconds) {
                            long seconds = leftTimeInMilliseconds / 1000;
                            current = leftTimeInMilliseconds;
                            long s = seconds % 60;
                            long m = (seconds / 60) % 60;
                            long h = (seconds / (60 * 60)) % 24;
                            clock.setText(String.format("%02d:%02d:%02d", h, m, s));
                        }

                        @Override
                        public void onFinish() {

                        }
                    }.start();

                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                pause.setText("Pause");
                isPaused = false;
                current = 0;
                totalTimeCountInMilliseconds = 0;
                clock.setText(String.format("%02d:%02d:%02d", 0, 0, 0));
            }
        });

    }

    private void setTimer() {
        int time = 0;
        totalTimeCountInMilliseconds = 0;

        if(!hour.getText().toString().equals("")){
            time = Integer.parseInt(hour.getText().toString());
            totalTimeCountInMilliseconds = 60 * 60 * time * 1000;
        }
        if(!min.getText().toString().equals("")){
            time = Integer.parseInt(min.getText().toString());
            totalTimeCountInMilliseconds += 60 * time * 1000;
        }

        if(!sec.getText().toString().equals("")){
            time = Integer.parseInt(sec.getText().toString());
            totalTimeCountInMilliseconds += time * 1000;
        }
        current = totalTimeCountInMilliseconds;

        long seconds = totalTimeCountInMilliseconds / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        clock.setText(String.format("%02d:%02d:%02d", h, m, s));
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

                current = leftTimeInMilliseconds;

                long s = seconds % 60;
                long m = (seconds / 60) % 60;
                long h = (seconds / (60 * 60)) % 24;
                clock.setText(String.format("%02d:%02d:%02d", h, m, s));

            }

            @Override
            public void onFinish() {
            }

        }.start();

    }
}
