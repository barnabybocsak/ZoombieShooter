package com.example.zombieshooter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.zombieshooter.GameView.screenRatioX;
import static com.example.zombieshooter.GameView.screenRatioY;

public class Move {
    private int shoot = 0;
    private boolean moveUp = false;
    private int x, y, width, height, shootCounter = 1;
    private Bitmap move, shot1, shot2, shot3, shot4, dead;
    private GameView gameView;

    public Move(GameView gameView, int screeny, Resources res) {
        this.gameView = gameView;

        move = BitmapFactory.decodeResource(res, R.drawable.player);

        width = move.getWidth();
        height = move.getHeight();

        width /= 1.5;
        height /= 1.5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        move = Bitmap.createScaledBitmap(move, width, height, false);

        shot1 = BitmapFactory.decodeResource(res, R.drawable.shot1);
        shot2 = BitmapFactory.decodeResource(res, R.drawable.shot2);
        shot3 = BitmapFactory.decodeResource(res, R.drawable.shot3);
        shot4 = BitmapFactory.decodeResource(res, R.drawable.shot4);

        shot1 = Bitmap.createScaledBitmap(shot1, width, height, false);
        shot2 = Bitmap.createScaledBitmap(shot2, width, height, false);
        shot3 = Bitmap.createScaledBitmap(shot3, width, height, false);
        shot4 = Bitmap.createScaledBitmap(shot4, width, height, false);

        dead = BitmapFactory.decodeResource(res, R.drawable.dead);
        dead = Bitmap.createScaledBitmap(dead, width, height, false);

        y = screeny / 2;
        x = (int) (100 * screenRatioX);
    }

    public Bitmap getMove() {
        if (shoot != 0) {
            if (shootCounter == 1) {
                shootCounter++;
                return shot1;
            }
            if (shootCounter == 2) {
                shootCounter++;
                return shot2;
            }
            if (shootCounter == 3) {
                shootCounter++;
                return shot3;
            }
            shootCounter = 1;
            shoot--;
            gameView.newBullet();
            return shot4;

        }
        return move;
    }

    public Rect getHitBox() {
        return new Rect(x, y, x + width, y + height);
    }

    public Bitmap getDead() {
        return dead;
    }

    public int getMovex() {
        return x;
    }

    public int getMovey() {
        return y;
    }

    public void setMovey(int y) {
        this.y = y;
    }

    public Boolean getMoveUp() {
        return moveUp;
    }

    public void setMoveUp(Boolean b) {
        moveUp = b;
    }

    public int getMoveHeight() {
        return height;
    }

    public int getMoveWidth() {
        return width;
    }

    public int getShoot() {
        return shoot;
    }

    public void setShoot(int i) {
        shoot = i;
    }
}
