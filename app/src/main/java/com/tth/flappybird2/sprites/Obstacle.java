package com.tth.flappybird2.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.tth.flappybird2.R;

import java.util.ArrayList;
import java.util.Random;

public class Obstacle implements Sprite {
    private int height, width, separation, xPosition, speed;
    private int screenHeight, screenWidth;
    private int headHeight, headExtraWidth;
    private int obstacleMinPosition;
    private Bitmap image, coin;
    private ObstacleCallback obstacleCallback;
    private int heightCoin, widthCoin, coinY;
    private boolean collision = false;
    private GameDificulty gameDificulty;

    public Obstacle(Resources resources, int screenHeight, int screenWidth, ObstacleCallback obstacleCallback, GameDificulty gameDificulty) {
        image = BitmapFactory.decodeResource(resources, R.drawable.pipes);
        coin = BitmapFactory.decodeResource(resources, R.drawable.coin);
        this.gameDificulty = gameDificulty;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.obstacleCallback = obstacleCallback;
        if (gameDificulty == GameDificulty.EASY) {
            speed = (int) resources.getDimension(R.dimen.obstacle_speed_esay);
        }
        if (gameDificulty == GameDificulty.MEDIUM) {
            speed = (int) resources.getDimension(R.dimen.obstacle_speed_medium);
        }
        if (gameDificulty == GameDificulty.HARD) {
            speed = (int) resources.getDimension(R.dimen.obstacle_speed_hard);
        }
        width = (int) resources.getDimension(R.dimen.obstacle_width);
        separation = (int) resources.getDimension(R.dimen.obstacle_separation);
        headHeight = (int) resources.getDimension(R.dimen.head_height);
        headExtraWidth = (int) resources.getDimension(R.dimen.head_extra_width);
        obstacleMinPosition = (int) resources.getDimension(R.dimen.obstacle_min_position);
        xPosition = screenWidth;
        heightCoin = (int) resources.getDimension(R.dimen.coin_height);
        widthCoin = (int) resources.getDimension(R.dimen.coin_width);
        coin = Bitmap.createScaledBitmap(coin, widthCoin, heightCoin, false);
        Random random = new Random(System.currentTimeMillis());
        height = random.nextInt(screenHeight - 2 * obstacleMinPosition - separation) + obstacleMinPosition;
        if (!collision)
            coinY = random.nextInt(separation - heightCoin) + screenHeight - height - separation - headHeight;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bottomHead = new Rect(xPosition, screenHeight - height - headHeight, xPosition + width + 2 * headExtraWidth, screenHeight - height);
        Rect bottomPipe = new Rect(xPosition + headExtraWidth, screenHeight - height, xPosition + width + headExtraWidth, screenHeight);
        Rect topPipe = new Rect(xPosition + headExtraWidth, 0, xPosition + headExtraWidth + width, screenHeight - height - separation - 2 * headHeight);
        Rect topHead = new Rect(xPosition, screenHeight - height - separation - 2 * headHeight, xPosition + width + 2 * headExtraWidth, screenHeight - height - separation - headHeight);
        if (!collision) {
            canvas.drawBitmap(coin, xPosition + 2 * headExtraWidth, coinY, null);
        }
        Paint paint = new Paint();
        canvas.drawBitmap(image, null, bottomPipe, paint);
        canvas.drawBitmap(image, null, bottomHead, paint);
        canvas.drawBitmap(image, null, topPipe, paint);
        canvas.drawBitmap(image, null, topHead, paint);
    }

    @Override
    public void update() {
        xPosition -= speed;
        if (xPosition <= 0 - width - 2 * headExtraWidth) {
            obstacleCallback.ObstacleOffScreen(this);
        } else {
            ArrayList<Rect> positions = new ArrayList<>();
            Rect bottomPosition = new Rect(xPosition, screenHeight - height - headHeight, xPosition + width + 2 * headExtraWidth, screenHeight);
            Rect topPosition = new Rect(xPosition, 0, xPosition + width + 2 * headExtraWidth, screenHeight - height - separation - headHeight);
            positions.add(bottomPosition);
            positions.add(topPosition);
            if (!collision) {
                Rect coinPosition = new Rect(xPosition + 2 * headExtraWidth, coinY, xPosition + 2 * headExtraWidth + widthCoin, coinY + heightCoin);
                positions.add(coinPosition);
            }
            obstacleCallback.updatePosition(this, positions);
        }
    }

    public void setCollisionCoin(boolean collision) {
        this.collision = collision;
    }
}
