package com.tth.flappybird2.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.tth.flappybird2.R;

public class Background implements Sprite {
    private int screenHeight;
    Bitmap top, bottom;
    private int topHeight, bottomHeight;

    public Background(Resources resources, int screenHight) {
        this.screenHeight = screenHight;
        topHeight = (int) resources.getDimension(R.dimen.bkg_top_height);
        bottomHeight = (int) resources.getDimension(R.dimen.bkg_bottom_height);
        Bitmap bkgtop = BitmapFactory.decodeResource(resources, R.drawable.sky);
        Bitmap bkgbottom = BitmapFactory.decodeResource(resources, R.drawable.ground);
        top = Bitmap.createScaledBitmap(bkgtop, bkgtop.getWidth(), topHeight, false);
        bottom = Bitmap.createScaledBitmap(bkgbottom, bkgbottom.getWidth(), bottomHeight, false);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(top, 0, 0, null);
        canvas.drawBitmap(top, top.getWidth(), 0, null);
        canvas.drawBitmap(bottom, 0, screenHeight-bottom.getHeight(), null);
        canvas.drawBitmap(bottom, bottom.getWidth(), screenHeight-bottom.getHeight(), null);
    }

    @Override
    public void update() {

    }
}
