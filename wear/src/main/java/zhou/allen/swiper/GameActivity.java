package zhou.allen.swiper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.wearable.view.DismissOverlayView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;


public class GameActivity extends Activity {

    private String DEBUG_TAG = "Motion";

    TextView scoreText;
    ImageView currIcon, nextIcon;
    WatchViewStub stub;
    Gamemode gamemode;

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gamemode = Gamemode.SURVIVAL; //SET Gamemode in SharedPreferences
        final Game game = new Game(GameActivity.this, gamemode);

        // Configure a gesture detector
        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

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
                GameGesture g;
                //LR swipe
                if(velocityX > velocityY) {
                    if(event1.getX() >= event2.getX()) {
                        Log.d(DEBUG_TAG, "Left Swipe");
                        g = GameGesture.LEFT;
                    } else {
                        Log.d(DEBUG_TAG, "Right Swipe");
                        g = GameGesture.RIGHT;
                    }
                }
                //UD swipe
                //else if(velocityY > velocityX) {
                else {
                    if(event1.getY() > event2.getY()) {
                        Log.d(DEBUG_TAG, "Up Swipe");
                        g = GameGesture.UP;
                    } else {
                        Log.d(DEBUG_TAG, "Down Swipe");
                        g = GameGesture.DOWN;
                    }
                }
                update(game.input(g), game.getScore());
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
                update(game.input(GameGesture.TAP), game.getScore());
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

        stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                scoreText = (TextView) stub.findViewById(R.id.scoreText);

                //Start the game
                Pair<GameGesture, GameGesture> startPair = game.start();
                update(startPair, game.getScore());

                // Obtain the DismissOverlayView element
                mDismissOverlay = (DismissOverlayView) stub.findViewById(R.id.dismiss_overlay);
                mDismissOverlay.setIntroText("Long Press to close");
                mDismissOverlay.showIntroIfNecessary();

                pb = (ProgressBar) findViewById(R.id.progress);
                if(gamemode==Gamemode.SURVIVAL) pb.setMax(1250);
                if(gamemode==Gamemode.TIMEATTACK) pb.setMax(60000);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }

    private ImageView showCurrIcon(GameGesture curr, View v) {
        switch (curr) {
            case UP:
                currIcon = (ImageView) v.findViewById(R.id.upFront);
                break;
            case RIGHT:
                currIcon = (ImageView) v.findViewById(R.id.rightFront);
                break;
            case DOWN:
                currIcon = (ImageView) v.findViewById(R.id.downFront);
                break;
            case LEFT:
                currIcon = (ImageView) v.findViewById(R.id.leftFront);
                break;
            case TAP:
                currIcon = (ImageView) v.findViewById(R.id.tapFront);
                break;
        }
        currIcon.setVisibility(View.VISIBLE);
        return currIcon;
    }

    private ImageView showNextIcon(GameGesture next, View v) {
        switch (next) {
            case UP:
                nextIcon = (ImageView) v.findViewById(R.id.upBack);
                break;
            case RIGHT:
                nextIcon = (ImageView) v.findViewById(R.id.rightBack);
                break;
            case DOWN:
                nextIcon = (ImageView) v.findViewById(R.id.downBack);
                break;
            case LEFT:
                nextIcon = (ImageView) v.findViewById(R.id.leftBack);
                break;
            case TAP:
                nextIcon = (ImageView) v.findViewById(R.id.tapBack);
                break;
        }
        nextIcon.setVisibility(View.VISIBLE);
        return nextIcon;
    }

    private Pair<ImageView, ImageView> updateIcons(Pair<GameGesture, GameGesture> p) {
        if (currIcon != null && nextIcon != null) {
            currIcon.setVisibility(View.INVISIBLE); //TODO fade out
            nextIcon.setVisibility(View.INVISIBLE);
        }
        showCurrIcon(p.first, stub);
        showNextIcon(p.second, stub);
        return new Pair<ImageView, ImageView>(currIcon, nextIcon);
    }

    public TextView updateScore(int score) {
        scoreText.setText(Integer.toString(score));
        return scoreText;
    }

    private void update(Pair<GameGesture, GameGesture> p, int score) {
        updateIcons(p);
        updateScore(score);
    }

    public void updateTime(int timeLeft, int maxTime) {
        //TODO: implement this function (make a bar that decreases on the side?...)
        pb.setMax(maxTime);
        if(timeLeft < maxTime/3) {
            pb.setProgress(0);
            pb.setSecondaryProgress(timeLeft);
        } else {
            pb.setSecondaryProgress(0);
            pb.setProgress(timeLeft);
        }
    }

    public void gameOver(int score, Gamemode mode) {
        //TODO show Game Over screen
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int highScore = prefs.getInt("highscore"+mode, 0);
        Intent i = new Intent(this, GameOverActivity.class);
        i.putExtra("score", score);
        i.putExtra("highscore", highScore);
        if(score > highScore) {
            SharedPreferences.Editor editPrefs = prefs.edit();
            editPrefs.putInt("highscore"+mode, score);
            editPrefs.commit();
        }
        startActivity(i);
        finish();
    }
}

