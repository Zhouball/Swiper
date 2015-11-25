package zhou.allen.swiper;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Intent i = getIntent();
        int score = i.getIntExtra("score", 0);
        int highscore = i.getIntExtra("highscore", 0);
        TextView scoreTV = (TextView) findViewById(R.id.score);
        TextView highscoreTV = (TextView) findViewById(R.id.highScore);
        scoreTV.setText(Integer.toString(score));
        highscoreTV.setText(Integer.toString(highscore));

        RelativeLayout view = (RelativeLayout) findViewById(R.id.gameoverRL);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

/*
        GestureDetector mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            private String DEBUG_TAG = "Motion Event";
            @Override
            public void onLongPress(MotionEvent event) {
                Log.d(DEBUG_TAG, " onLongPress: " + event.toString());
            }

            @Override
            public boolean onDown(MotionEvent event) {
                Log.d(DEBUG_TAG," onDown: " + event.toString());
                return true;
            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
                Log.d(DEBUG_TAG, " onFling: " + velocityX + ", " + velocityY);
                velocityX = Math.abs(velocityX);
                velocityY = Math.abs(velocityY);
                //LR swipe
                if(velocityX > velocityY) {
                    if(event1.getX() < event2.getX()) {
                        Log.d(DEBUG_TAG, "Right Swipe");
                    }
                }
                return true; //When does this return false?
                //return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent event) {
                Log.d(DEBUG_TAG, " onShowPress: " + event.toString());
            }

            @Override
            public boolean onSingleTapUp(MotionEvent event) {
                Log.d(DEBUG_TAG, " onSingleTapUp: " + event.toString());
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent event) {
                Log.d(DEBUG_TAG, " onDoubleTap: " + event.toString());
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent event) {
                Log.d(DEBUG_TAG, " onDoubleTapEvent: " + event.toString());
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                Log.d(DEBUG_TAG, " onSingleTapConfirmed: " + event.toString());
                return true;
            }
        });
*/
    }

}
