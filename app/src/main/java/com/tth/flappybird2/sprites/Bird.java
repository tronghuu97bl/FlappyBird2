package com.tth.flappybird2.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.tth.flappybird2.GameManagerCallback;
import com.tth.flappybird2.R;

public class Bird implements Sprite {
    private Bitmap bird_up;
    private Bitmap bird_down;
    private int birdWidth, birdHeight, birdX, birdY;
    private float gravity;
    private float currentFallingSpeed;
    private float flappyBoost;
    private boolean collision = false;
    private int screenHeight;
    private GameManagerCallback callback;
    private GameDificulty gameDificulty;

    public Bird(Resources resources, int screenHeight, GameManagerCallback callback, GameDificulty gameDificulty) {
        this.screenHeight = screenHeight;
        this.callback = callback;
        this.gameDificulty=gameDificulty;
        birdX = (int) resources.getDimension(R.dimen.bird_x);
        birdY = screenHeight / 2 - birdHeight / 2;
        birdHeight = (int) resources.getDimension(R.dimen.bird_height);
        birdWidth = (int) resources.getDimension(R.dimen.bird_width);
        if (gameDificulty == GameDificulty.EASY) {
            gravity = resources.getDimension(R.dimen.gravity_easy);
            flappyBoost = resources.getDimension(R.dimen.flappy_boost_easy);
        }
        if (gameDificulty == GameDificulty.MEDIUM) {
            gravity = resources.getDimension(R.dimen.gravity_medium);
            flappyBoost = resources.getDimension(R.dimen.flappy_boost_medium);
        }
        if (gameDificulty == GameDificulty.HARD) {
            gravity = resources.getDimension(R.dimen.gravity_hard);
            flappyBoost = resources.getDimension(R.dimen.flappy_boost_hard);
        }
        Bitmap birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird_down);
        // tạo một bitmap mới, chia tỉ lệ từ bitmap hiện có
        bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
        Bitmap birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird_up);
        bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
    }

    @Override
    public void draw(Canvas canvas) {
        if (currentFallingSpeed < 0) {
            canvas.drawBitmap(bird_up, birdX, birdY, null);
        } else {
            canvas.drawBitmap(bird_down, birdX, birdY, null);
        }
    }

    @Override
    public void update() {
        if (collision) {
            if (birdY + bird_down.getHeight() < screenHeight) {
                birdY += currentFallingSpeed;
                currentFallingSpeed += gravity;
            }
        } else {
            birdY += currentFallingSpeed;
            currentFallingSpeed += gravity;
            Rect birdPosition = new Rect(birdX, birdY, birdX + birdWidth, birdY + birdHeight);
            callback.updatePosition(birdPosition);
        }
    }

    public void onTouchEvent() {
        if (!collision) {
            currentFallingSpeed = flappyBoost;
        }
    }

    public void collision() {
        collision = true;
    }
}
