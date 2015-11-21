package zhou.allen.swiper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.wearable.view.DismissOverlayView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class GameActivity extends Activity {

    private String DEBUG_TAG = "Motion";

    TextView scoreText;
    Integer score = 0, currEvent = -1, nextEvent = -1;
    ImageView currIcon, nextIcon;
    WatchViewStub stub;
    final int numEvents = 5;

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        currEvent = (int) (Math.random()*numEvents);
        nextEvent = (int) (Math.random()*numEvents);

        stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                scoreText = (TextView) stub.findViewById(R.id.scoreText);
                currIcon = showCurrIcon(currEvent, stub);
                nextIcon = showNextIcon(nextEvent, stub);

                // Obtain the DismissOverlayView element
                mDismissOverlay = (DismissOverlayView) stub.findViewById(R.id.dismiss_overlay);
                mDismissOverlay.setIntroText("Long Press to close");
                mDismissOverlay.showIntroIfNecessary();
            }
        });


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
                //LR swipe
                if(velocityX > velocityY) {
                    if(event1.getX() > event2.getX()) {
                        Log.d(DEBUG_TAG, "Left Swipe");
                        if(currEvent == 3) {
                            update();
                        }
                    } else {
                        Log.d(DEBUG_TAG, "Right Swipe");
                        if(currEvent == 1) {
                            update();
                        }
                    }
                }
                //UD swipe
                else if(velocityY > velocityX) {
                    if(event1.getY() > event2.getY()) {
                        Log.d(DEBUG_TAG, "Up Swipe");
                        if(currEvent == 0) {
                            update();
                        }
                    } else {
                        Log.d(DEBUG_TAG, "Down Swipe");
                        if(currEvent == 2) {
                            update();
                        }
                    }
                }
                return true;
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
                if(currEvent == 4) {
                    update();
                }
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }

    private ImageView showCurrIcon(int curr, View v) {
        ImageView currIcon = (ImageView) v.findViewById(R.id.upFront);
        if(curr >= 0 && curr < numEvents) {
            switch (curr) {
                case 0:
                    currIcon = (ImageView) v.findViewById(R.id.upFront);
                    break;
                case 1:
                    currIcon = (ImageView) v.findViewById(R.id.rightFront);
                    break;
                case 2:
                    currIcon = (ImageView) v.findViewById(R.id.downFront);
                    break;
                case 3:
                    currIcon = (ImageView) v.findViewById(R.id.leftFront);
                    break;
                case 4:
                    currIcon = (ImageView) v.findViewById(R.id.tapFront);
                    break;
            }
        }
        currIcon.setVisibility(View.VISIBLE);
        return currIcon;
    }

    private ImageView showNextIcon(int next, View v) {
        ImageView nextIcon = (ImageView) v.findViewById(R.id.upBack);
        if(next >= 0 && next < numEvents) {
            switch (next) {
                case 0:
                    nextIcon = (ImageView) v.findViewById(R.id.upBack);
                    break;
                case 1:
                    nextIcon = (ImageView) v.findViewById(R.id.rightBack);
                    break;
                case 2:
                    nextIcon = (ImageView) v.findViewById(R.id.downBack);
                    break;
                case 3:
                    nextIcon = (ImageView) v.findViewById(R.id.leftBack);
                    break;
                case 4:
                    nextIcon = (ImageView) v.findViewById(R.id.tapBack);
                    break;
            }
        }
        nextIcon.setVisibility(View.VISIBLE);
        return nextIcon;
    }

    private void update() {
        currEvent = nextEvent;
        nextEvent = (int) (Math.random()*numEvents);
        currIcon.setVisibility(View.INVISIBLE);
        nextIcon.setVisibility(View.INVISIBLE);
        currIcon = showCurrIcon(currEvent, stub);
        nextIcon = showNextIcon(nextEvent, stub);
        score++;
        scoreText.setText(score.toString());
    }
}

