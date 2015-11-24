package zhou.allen.swiper;

import android.app.Activity;
import android.util.Pair;

import java.util.Random;

/**
 * Created by Owner on 11/24/2015.
 */
public class Game {
    Activity gameActivity;
    Gamemode mode;
    int score = 0;
    GameGesture current, next;

    public Game(Activity gA, Gamemode m) {
        gameActivity = gA;
        mode = m;
    }

    public Pair<GameGesture, GameGesture> start() {
        //TODO start timers
        current = getNextGesture();
        next = getNextGesture();
        return new Pair<GameGesture, GameGesture>(current, next);
    }

    public Pair<GameGesture, GameGesture> correct() {
        //TODO reset timers or whatever
        score++;
        return nextGesture();
    }

    public Pair<GameGesture, GameGesture> incorrect() {
        //TODO handle incorrect swipe here
        return thisGesture();
    }

    public int stop() {
        //TODO handle gameovers
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
