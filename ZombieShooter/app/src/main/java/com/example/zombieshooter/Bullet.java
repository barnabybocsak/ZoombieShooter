package com.example.zombieshooter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.zombieshooter.GameView.screenRatioX;
import static com.example.zombieshooter.GameView.screenRatioY;

public class Bullet {
    private int x, y, width, height;
    private Bitmap bullet;

    public Bullet(Resources res) {
        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 1.5;
        height /= 1.5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, height, width, false);
    }

    public Rect getHitBox() {
        return new Rect(x, y, x + width, y + height);
    }

    public int getBulletx() {
        return x;
    }

    public void setBulletx(int x) {
        this.x = x;
    }

    public int getBullety() {
        return y;
    }

    public void setBullety(int y) {
        this.y = y;
    }

    public Bitmap getBullet() {
        return bullet;
    }
}
