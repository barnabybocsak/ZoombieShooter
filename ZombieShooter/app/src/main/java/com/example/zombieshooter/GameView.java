package com.example.zombieshooter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    public Thread thread;
    private boolean playing, gameOver = false;
    private int screenx, screeny, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private ArrayList<Zombie> zombies;
    private ArrayList<Zombie2> zombies2;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private ArrayList<Bullet> bullets;
    private int sound;
    private Move move;
    private GameActivity activity;
    private Background background1;

    public GameView(GameActivity activity , int screenx, int screeny) {
        super(activity);

        this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        sound = soundPool.load(activity, R.raw.shoot, 1);

        this.screenx = screenx;
        this.screeny = screeny;
        screenRatioX = 1920f / screenx;
        screenRatioY = 1080f / screeny;

        background1 = new Background(screenx, screeny, getResources());

        move = new Move(this, screeny, getResources());

        bullets = new ArrayList<>();

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        zombies = new ArrayList<>(2);
        zombies2 = new ArrayList<>(2);

        for (int i = 0; i < 3; i++) {
            Zombie zombie = new Zombie(getResources());
            zombies.add(zombie);
            Zombie2 zombie2 = new Zombie2(getResources());
            zombies2.add(zombie2);
        }

        random = new Random();
    }

    @Override
    public void run() {
        while(playing) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        if (move.getMoveUp()) {
            move.setMovey(move.getMovey() - (int) (15 * screenRatioY));
        } else {
            move.setMovey(move.getMovey() + (int) (15 * screenRatioY));
        }
        if (move.getMovey() < 0 ) {
            move.setMovey(0);
        }
        if (move.getMovey() > screeny - move.getMoveHeight()) {
            move.setMovey(screeny - move.getMoveHeight());
        }

        ArrayList<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if (bullet.getBulletx() > screenx) {
                trash.add(bullet);
            }

            bullet.setBulletx(bullet.getBulletx() + (int) (50 * screenRatioX));

            for (Zombie zombie : zombies) {
                if (Rect.intersects(zombie.getHitBox(), bullet.getHitBox())) {
                    score++;
                    zombie.setZombiex(-500);
                    bullet.setBulletx(screenx + 500);
                    zombie.setWasHit(true);
                }
            }

            for (Zombie2 zombie : zombies2) {
                if (Rect.intersects(zombie.getHitBox(), bullet.getHitBox())) {
                    score++;
                    zombie.setZombiex(-500);
                    bullet.setBulletx(screenx + 500);
                    zombie.setWasHit(true);
                }
            }

        }

        for (Bullet bullet : trash) {
            bullets.remove(bullet);
        }

        for (Zombie zombie : zombies) {
            zombie.setZombiex(zombie.getZombiex() - zombie.getZombieSpeed());

            if (score < 50 && score > 100) {
                zombie.setMinSpeed(12);
            } else if (score < 100 && score > 150) {
                zombie.setMinSpeed(15);
            } else if (score < 150 && score > 200) {
                zombie.setMinSpeed(17);
            } else if (score < 200 && score > 250){
                zombie.setMinSpeed(20);
            } else if (score < 250) {
                zombie.setMinSpeed(25);
            }

            if (zombie.getZombiex() + zombie.getZombieWidth() < 0) {
                if (!zombie.zombieWasHit()) {
                    gameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                zombie.setZombieSpeed(random.nextInt(bound));

                if (zombie.getZombieSpeed() < zombie.getMinSpeed() * screenRatioX) {
                    zombie.setZombieSpeed((int) (zombie.getMinSpeed() * screenRatioX));
                }

                zombie.setZombiex(screenx);
                zombie.setZombiey(random.nextInt(screeny - zombie.getZombieHeight()));

                zombie.setWasHit(false);
            }

            if (Rect.intersects(zombie.getHitBox(), move.getHitBox())) {
                gameOver = true;
                return;
            }

        }

        for (Zombie2 zombie : zombies2) {
            zombie.setZombiex(zombie.getZombiex() - zombie.getZombieSpeed());

            if (score < 50 && score > 100) {
                zombie.setMinSpeed(12);
            } else if (score < 100 && score > 150) {
                zombie.setMinSpeed(15);
            } else if (score < 150 && score > 200) {
                zombie.setMinSpeed(17);
            } else if (score < 200 && score > 250){
                zombie.setMinSpeed(20);
            } else if (score < 250) {
                zombie.setMinSpeed(25);
            }

            if (zombie.getZombiex() + zombie.getZombieWidth() < 0) {
                if (!zombie.zombieWasHit()) {
                    gameOver = true;
                    return;
                }

                int bound = (int) (30 * screenRatioX);
                zombie.setZombieSpeed(random.nextInt(bound));

                if (zombie.getZombieSpeed() < zombie.getMinSpeed() * screenRatioX) {
                    zombie.setZombieSpeed((int) (zombie.getMinSpeed() * screenRatioX));
                }

                zombie.setZombiex(screenx);
                zombie.setZombiey(random.nextInt(screeny - zombie.getZombieHeight()));

                zombie.setWasHit(false);
            }

            if (Rect.intersects(zombie.getHitBox(), move.getHitBox())) {
                gameOver = true;
                return;
            }

        }

    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.getBackground(), background1.getBackgroundx(), background1.getBackgroundy(), paint);

            for (Zombie zombie : zombies) {
                canvas.drawBitmap(zombie.getZombie(), zombie.getZombiex(), zombie.getZombiey(), paint);
            }

            for (Zombie2 zombie : zombies2) {
                canvas.drawBitmap(zombie.getZombie(), zombie.getZombiex(), zombie.getZombiey(), paint);
            }

            canvas.drawText(score + "", screenx / 2f, 164, paint);

            if (gameOver) {
                playing = false;
                canvas.drawBitmap(move.getDead(), move.getMovex(), move.getMovey(), paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }

            canvas.drawBitmap(move.getMove(), move.getMovex(), move.getMovey(), paint);

            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.getBullet(), bullet.getBulletx(), bullet.getBullety(), paint);
            }

            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void waitBeforeExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if (prefs.getInt("highscore", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            playing = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenx / 2 && event.getY() < screeny /2) {
                    move.setMoveUp(true);
                } else if (event.getX() < screenx / 2 && event.getY() > screeny /2) {
                    move.setMoveUp(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() > screenx / 2)
                    move.setShoot(move.getShoot()+1);
                break;
        }
        return true;
    }

    public void newBullet() {
        if (!prefs.getBoolean("mute", false)) {
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }

        Bullet bullet = new Bullet(getResources());
        bullet.setBulletx(move.getMovex() + move.getMoveWidth());
        bullet.setBullety(move.getMovey() + (int) (move.getMoveHeight() / 2.5));
        bullets.add(bullet);
    }
}
