package com.example.zombieshooter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.zombieshooter.GameView.screenRatioX;
import static com.example.zombieshooter.GameView.screenRatioY;

public class Zombie {
    private int speed = 20;
    private boolean wasHit = true;
    private int x = 0, y, width, height;
    private Bitmap zombie;
    private int zombieMinSpeed = 10;

    public Zombie (Resources res) {
        zombie = BitmapFactory.decodeResource(res, R.drawable.zombie);

        width = zombie.getWidth();
        height = zombie.getHeight();

        width /= 1.5;
        height /= 1.5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        zombie = Bitmap.createScaledBitmap(zombie, width, height, false);

        y = -height;
    }

    public Bitmap getZombie() {
            return zombie;
    }

    public Rect getHitBox() {
        return new Rect(x, y, x + width, y + height);
    }

    public int getZombiex() {
        return x;
    }

    public void setZombiex(int x) {
        this.x = x;
    }

    public int getZombiey() {
        return y;
    }

    public void setZombiey(int y) {
        this.y = y;
    }

    public Boolean zombieWasHit() {
        return wasHit;
    }

    public void setWasHit(Boolean hit) {
        wasHit = hit;
    }

    public int getZombieSpeed() {
        return speed;
    }

    public void setZombieSpeed(int speed) {
        this.speed = speed;
    }

    public int getZombieHeight() {
        return height;
    }

    public int getZombieWidth() {
        return width;
    }

    public int getMinSpeed() {
        return zombieMinSpeed;
    }

    public void setMinSpeed(int i) {
        zombieMinSpeed = i;
    }
}

