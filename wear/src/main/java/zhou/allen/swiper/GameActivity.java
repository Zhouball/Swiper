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


public class GameActivity extends Activity {

    private String DEBUG_TAG = "Motion";

    TextView scoreText;
    Integer score = 0;

    private DismissOverlayView mDismissOverlay;
    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                scoreText = (TextView) stub.findViewById(R.id.scoreText);

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
                final float xDistance = Math.abs(event1.getX() - event2.getX());
                final float yDistance = Math.abs(event1.getY() - event2.getY());
                velocityX = Math.abs(velocityX);
                velocityY = Math.abs(velocityY);
                //LR swipe
                if(velocityX > velocityY) {
                    if(event1.getX() > event2.getX())
                        Log.d(DEBUG_TAG, "Left Swipe");
                    else
                        Log.d(DEBUG_TAG, "Right Swipe");
                }
                //UD swipe
                else if(velocityY > velocityX) {
                    if(event1.getY() > event2.getY())
                        Log.d(DEBUG_TAG, "Up Swipe");
                    else
                        Log.d(DEBUG_TAG, "Down Swipe");
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
        //return mDetector.onTouchEvent(ev) || super.onTouchEvent(ev);

        mDetector.onTouchEvent(ev);
        super.onTouchEvent(ev);
        return true;
    }
}

