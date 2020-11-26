package com.tth.flappybird2.sprites;

import android.graphics.Rect;

import java.util.ArrayList;

public interface ObstacleCallback {
    void ObstacleOffScreen(Obstacle obstacle);
    void updatePosition(Obstacle obstacle, ArrayList<Rect> positions);
}
