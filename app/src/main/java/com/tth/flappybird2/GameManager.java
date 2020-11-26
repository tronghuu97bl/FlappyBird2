package com.tth.flappybird2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.tth.flappybird2.sprites.Background;
import com.tth.flappybird2.sprites.Bird;
import com.tth.flappybird2.sprites.GameDificulty;
import com.tth.flappybird2.sprites.GameMessage;
import com.tth.flappybird2.sprites.GameOver;
import com.tth.flappybird2.sprites.Obstacle;
import com.tth.flappybird2.sprites.ObstacleManager;
import com.tth.flappybird2.sprites.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameManager extends SurfaceView implements SurfaceHolder.Callback, GameManagerCallback {
    private static final String APP_NAME = "FlappyBird2";
    public MainThread thread;
    private Bird bird;
    private Background background;
    private DisplayMetrics dm;
    private ObstacleManager obstacleManager;
    private Rect birdPosition;
    private HashMap<Obstacle, List<Rect>> obstacleListHashMap;
    private GameState gameState = GameState.INITIAL;
    private GameOver gameOver;
    private GameMessage gameMessage;
    private GameDificulty gameDificulty;
    private Score scoreSprite;
    private int score, difLevel;
    private MediaPlayer mpPoint, mpSwoosh, mpDie, mpHit, mpWing;
    private SharedPreferences pre;

    public GameManager(Context context, AttributeSet attributeSet) {
        super(context);
        pre = getContext().getSharedPreferences("DIF", Context.MODE_PRIVATE);
        difLevel = pre.getInt("DIF", 0);
        switch (difLevel) {
            case 0:
                gameDificulty = GameDificulty.EASY;
                break;
            case 1:
                gameDificulty = GameDificulty.MEDIUM;
                break;
            case 2:
                gameDificulty = GameDificulty.HARD;
                break;
        }
        initSound();
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        initGame();
    }

    private void initGame() {
        score = 0;
        birdPosition = new Rect();
        obstacleListHashMap = new HashMap<>();
        bird = new Bird(getResources(), dm.heightPixels, this, gameDificulty);
        background = new Background(getResources(), dm.heightPixels);
        obstacleManager = new ObstacleManager(getResources(), dm.heightPixels, dm.widthPixels, this, gameDificulty);
        gameOver = new GameOver(getResources(), dm.heightPixels, dm.widthPixels);
        gameMessage = new GameMessage(getResources(), dm.heightPixels, dm.widthPixels);
        scoreSprite = new Score(getResources(), dm.heightPixels, dm.widthPixels);
    }

    private void initSound() {
        mpDie = MediaPlayer.create(getContext(), R.raw.die);
        mpHit = MediaPlayer.create(getContext(), R.raw.hit);
        mpPoint = MediaPlayer.create(getContext(), R.raw.point);
        mpSwoosh = MediaPlayer.create(getContext(), R.raw.swoosh);
        mpWing = MediaPlayer.create(getContext(), R.raw.wing);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        switch (gameState) {
            case PLAYING:
                bird.update();
                obstacleManager.update();
                break;
            case GAME_OVER:
                bird.update();
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawRGB(200, 255, 255);
            background.draw(canvas);
            switch (gameState) {
                case PLAYING:
                    bird.draw(canvas);
                    obstacleManager.draw(canvas);
                    scoreSprite.draw(canvas);
                    callculateCollision();
                    break;
                case INITIAL:
                    bird.draw(canvas);
                    gameMessage.draw(canvas);
                    break;
                case GAME_OVER:
                    obstacleManager.draw(canvas);
                    bird.draw(canvas);
                    scoreSprite.draw(canvas);
                    gameOver.draw(canvas);
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (gameState) {
            case PLAYING:
                bird.onTouchEvent();
                mpWing.start();
                break;
            case INITIAL:
                bird.onTouchEvent();
                mpWing.start();
                gameState = GameState.PLAYING;
                mpSwoosh.start();
                break;
            case GAME_OVER:
                initGame();
                gameState = GameState.INITIAL;
                break;

        }
        //bird.onTouchEvent();
        return super.onTouchEvent(event);
    }

    @Override
    public void updatePosition(Rect birdPosition) {
        this.birdPosition = birdPosition;
    }

    @Override
    public void updatePosition(Obstacle obstacle, ArrayList<Rect> positions) {
        if (obstacleListHashMap.containsKey(obstacle)) {
            obstacleListHashMap.remove(obstacle);
        }
        obstacleListHashMap.put(obstacle, positions);
    }

    @Override
    public void removeObstacle(Obstacle obstacle) {
        obstacleListHashMap.remove(obstacle);
        score++;
        scoreSprite.updateScore(score);
        mpPoint.start();
    }

    //tinh toan va cham
    public void callculateCollision() {
        boolean collision = false;
        if (birdPosition.bottom > dm.heightPixels) {//vuot qua chieu dai man hinh
            collision = true;
        } else {
            for (Obstacle obstacle : obstacleListHashMap.keySet()) {
                boolean collisionCoin = false;
                Rect bottomRect = obstacleListHashMap.get(obstacle).get(0);
                Rect topRect = obstacleListHashMap.get(obstacle).get(1);
                Rect coinRect = obstacleListHashMap.get(obstacle).get(2);
                if (birdPosition.right > bottomRect.left && birdPosition.left < bottomRect.right && birdPosition.bottom > bottomRect.top) {
                    collision = true;
                } else {
                    if (birdPosition.right > topRect.left && birdPosition.left < topRect.right && birdPosition.top < topRect.bottom) {
                        collision = true;
                    }
                }
                if (birdPosition.right > coinRect.left && birdPosition.left < coinRect.right && birdPosition.bottom > coinRect.top && birdPosition.top < coinRect.bottom) {
                    collisionCoin = true;
                    if (collisionCoin) {
                        obstacle.setCollisionCoin(collisionCoin);
                    }
                }
            }
        }
        if (collision) {
            gameState = GameState.GAME_OVER;
            bird.collision();
            scoreSprite.collision(getContext().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE));
            mpHit.start();
            mpHit.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mpDie.start();
                }
            });
        }
    }
}
