package zhou.allen.swiper;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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
    }

}
