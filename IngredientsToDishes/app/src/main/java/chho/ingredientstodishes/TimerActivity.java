package chho.ingredientstodishes;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText reminder;
    private long totalTimeCountInMilliseconds;
    private long current = 0;
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
        reminder = (EditText) findViewById(R.id.texthere);


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

                if(validTime()) {
                    setTimer();
                    Toast.makeText(TimerActivity.this, "Timer started!",
                            Toast.LENGTH_SHORT).show();

                    startTimer();
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (current > 0) {
                    if (!isPaused) {
                        try {
                            countDownTimer.cancel();
                            isPaused = true;
                            pause.setText("Resume");
                            Toast.makeText(TimerActivity.this, "Timer paused!",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(pause.getContext(), "Nothing to pause!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        isPaused = false;
                        pause.setText("Pause");

                        Toast.makeText(TimerActivity.this, "Timer resumed!",
                                Toast.LENGTH_SHORT).show();

                        countDownTimer = new CountDownTimer(current, 500) {

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
                                current = 0;
                                String stuff = reminder.getText().toString();
                                sendNotification(stuff);
                            }
                        }.start();

                    }
                } else{
                    Toast.makeText(pause.getContext(), "Nothing to pause!",
                            Toast.LENGTH_SHORT).show();
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
                Toast.makeText(TimerActivity.this, "Timer reset!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validTime(){

        int count = 0;
        if(!hour.getText().toString().equals("")){
            count = Integer.parseInt(hour.getText().toString());
        }
        if(!min.getText().toString().equals("")){
            count += Integer.parseInt(min.getText().toString());
        }
        if(!sec.getText().toString().equals("")){
            count += Integer.parseInt(sec.getText().toString());
        }

        if(count > 0)
            return true;

        Toast.makeText(TimerActivity.this, "Please set time to some value!",
                Toast.LENGTH_SHORT).show();
        return false;
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
                current = 0;
                String stuff = reminder.getText().toString();
                sendNotification(stuff);
            }

        }.start();

    }

    private void sendNotification(String msg) {

        if(msg.equals("")){
            msg = "Your timer finished!";
        }

        NotificationManager mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Ingredients To Dishes!")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
        Log.i("TAG", "Notification sent successfully.");
    }
}
