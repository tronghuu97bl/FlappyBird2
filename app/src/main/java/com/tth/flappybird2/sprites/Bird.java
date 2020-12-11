package com.tth.flappybird2.sprites;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.tth.flappybird2.GameManagerCallback;
import com.tth.flappybird2.R;

public class Bird implements Sprite {
    private Bitmap bird_up, bird_down;
    private Bitmap birdBmpDown, birdBmpUp;
    private int birdWidth, birdHeight, birdX, birdY;
    private float gravity;
    private float currentFallingSpeed;
    private float flappyBoost;
    private boolean collision = false;
    private int screenHeight;
    private int bird_id;
    private GameManagerCallback callback;

    public Bird(Resources resources, int screenHeight, GameManagerCallback callback, GameDificulty gameDificulty, SharedPreferences pre) {
        this.screenHeight = screenHeight;
        this.callback = callback;
        bird_id = pre.getInt("Bird_id", 0);
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
        switch (bird_id) {
            case 1:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird2_down);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird2_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
            case 2:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird3_down);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird3_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
            case 3:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird4_down);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird4_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
            case 4:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird5_down);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird5_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
            case 5:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird6_down);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird6_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
            case 6:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird7_down);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird7_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
            case 7:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird8_down);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird8_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
            case 8:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird9_up);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird9_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
            default:
                birdBmpDown = BitmapFactory.decodeResource(resources, R.drawable.bird_down);
                bird_down = Bitmap.createScaledBitmap(birdBmpDown, birdWidth, birdHeight, false);
                birdBmpUp = BitmapFactory.decodeResource(resources, R.drawable.bird_up);
                bird_up = Bitmap.createScaledBitmap(birdBmpUp, birdWidth, birdHeight, false);
                break;
        }

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
