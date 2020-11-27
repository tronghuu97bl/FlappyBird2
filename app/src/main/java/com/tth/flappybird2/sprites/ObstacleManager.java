package com.tth.flappybird2.sprites;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.tth.flappybird2.GameManagerCallback;
import com.tth.flappybird2.R;

import java.util.ArrayList;
import java.util.List;

public class ObstacleManager implements Sprite, ObstacleCallback {
    private int interval;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private int screenHeight, screenWidth;
    private Resources resources;
    private int progress = 0, speed;
    private GameManagerCallback callback;
    private GameDificulty gameDificulty;

    public ObstacleManager(Resources resources, int screenHeight, int screenWidth, GameManagerCallback callback, GameDificulty gameDificulty) {
        this.resources = resources;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.callback = callback;
        this.gameDificulty = gameDificulty;
        interval = (int) resources.getDimension(R.dimen.obstacle_interval);
        if (gameDificulty == GameDificulty.EASY) {
            speed = (int) resources.getDimension(R.dimen.obstacle_speed_esay);
        }
        if (gameDificulty == GameDificulty.MEDIUM) {
            speed = (int) resources.getDimension(R.dimen.obstacle_speed_medium);
        }
        if (gameDificulty == GameDificulty.HARD) {
            speed = (int) resources.getDimension(R.dimen.obstacle_speed_hard);
        }
        obstacles.add(new Obstacle(resources, screenHeight, screenWidth, this, gameDificulty));
    }

    @Override
    public void draw(Canvas canvas) {
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
    }

    @Override
    public void update() {
        progress += speed;
        if (progress > interval) {
            progress = 0;
            obstacles.add(new Obstacle(resources, screenHeight, screenWidth, this, gameDificulty));
        }
        //System.out.println(obstacles.size());
        List<Obstacle> duplicate = new ArrayList<>();
        duplicate.addAll(obstacles);
        for (Obstacle obstacle : duplicate) {
            obstacle.update();
        }
    }

    @Override
    public void ObstacleOffScreen(Obstacle obstacle) {
        obstacles.remove(obstacle);
        callback.removeObstacle(obstacle);
    }

    @Override
    public void updatePosition(Obstacle obstacle, ArrayList<Rect> positions) {
        callback.updatePosition(obstacle, positions);
    }
}
