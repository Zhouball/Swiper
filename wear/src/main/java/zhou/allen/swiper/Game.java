package zhou.allen.swiper;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Pair;

import java.util.Random;

/**
 * Created by Owner on 11/24/2015.
 */
public class Game {
    GameActivity gameActivity;
    Gamemode mode;
    int score = 0;
    GameGesture current, next;
    CountDownTimer currTimer;

    public Game(GameActivity gA, Gamemode m) {
        gameActivity = gA;
        mode = m;
    }

    public Pair<GameGesture, GameGesture> start() {
        if (mode == Gamemode.TIMEATTACK) {
            CountDownTimer timer = new CountDownTimer(60000, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //TODO indicate time running out in GameActivity
                }

                @Override
                public void onFinish() {
                    stop();
                }
            };
        }
        current = getNextGesture();
        next = getNextGesture();
        return new Pair<GameGesture, GameGesture>(current, next);
    }

    public Pair<GameGesture, GameGesture> correct() {
        score++;
        if (currTimer != null) {
            currTimer.cancel();
        }
        if (mode == Gamemode.SURVIVAL) {
            final long maxTime = (long)(1000.0 / Math.log((double)(score/4) + 2)) + 250;
            currTimer = new CountDownTimer(maxTime, 10) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //TODO indicate time running out in GameActivity
                    gameActivity.updateTime((int)millisUntilFinished, (int) maxTime);
                }

                @Override
                public void onFinish() {
                    stop();
                }
            };
            currTimer.start();
        }
        else if (mode == Gamemode.TIMEATTACK) {

        }
        return nextGesture();
    }

    public Pair<GameGesture, GameGesture> incorrect() {
        //TODO handle incorrect swipe here
        if(mode == Gamemode.SURVIVAL)
            return thisGesture();
        else if(mode == Gamemode.TIMEATTACK) {
            stop();
            return thisGesture();
        }
        return thisGesture();
    }

    public int stop() {
        //TODO handle gameover
        gameActivity.gameOver(score, mode);
        return score;
    }

    public Pair<GameGesture, GameGesture> input(GameGesture input) {
        if (input == current) {
            return correct();
        }
        else
            return incorrect();
    }

    public GameGesture getNextGesture() {
        GameGesture gg;
        int rn = new Random().nextInt(4); //TODO depends on gamemode
        switch (rn) {
            case 0: gg = GameGesture.UP; break;
            case 1: gg = GameGesture.DOWN; break;
            case 2: gg = GameGesture.LEFT; break;
            case 3: gg = GameGesture.RIGHT; break;
            case 4: gg = GameGesture.TAP; break;
            default: gg = null;
        }
        return gg;
    }

    public int getScore() {
        return score;
    }

    private Pair<GameGesture, GameGesture> nextGesture() {
        current = next;
        next = getNextGesture();
        return new Pair<GameGesture, GameGesture>(current, next);
    }

    private Pair<GameGesture, GameGesture> thisGesture() {
        return new Pair<GameGesture, GameGesture>(current, next);
    }
}
