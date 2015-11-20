package zhou.allen.swiper;

/**
 * -layout
 * TODO: -Make better image buttons
 * TODO: -Fix image view and theme
 * TODO: -Add main activity stuff
 *
 * -mechanics
 * TODO: -Swiping, Taps, Gestures, Motions
 *
 * -Easter egg
 * -Add themes
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }
}