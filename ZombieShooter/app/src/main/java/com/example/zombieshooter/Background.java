package com.example.zombieshooter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    private int x = 0, y = 0;
    private Bitmap background;

    public Background(int screenx, int screeny, Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenx, screeny, false);
    }

    public Bitmap getBackground() {
        return background;
    }

    public int getBackgroundx() {
        return x;
    }

    public int getBackgroundy() {
        return y;
    }
}
