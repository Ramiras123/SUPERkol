package com.example.ramiras.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestView extends SurfaceView implements SurfaceHolder.Callback {
    public DrawThread drawThread;

    public TestView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener((t, e) -> {
            this.performClick();
            drawThread.lock.lock();
            drawThread.coords.removeIf(p -> e.getX() > p.first && e.getY() < p.second && e.getX() < p.first + 500 && e.getY() > p.second - 45);
            Log.e("g",(int)e.getX() + " "+(int)e.getY());
            drawThread.lock.unlock();
            return true;
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class DrawThread extends Thread {
        public Lock lock = new ReentrantLock();

        public List<Pair<Integer, Integer>> coords = new ArrayList<>();
        private boolean running = false;
        private SurfaceHolder surfaceHolder;
        private Random random = new Random();

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            Canvas canvas;
            Paint p = new Paint();
            p.setTextSize(64);
            Paint p1 = new Paint();
            //p1.setColor(Color.WHITE);
            int i = 0;
            while (running) {

                canvas = null;
                try {
                    lock.lock();
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                   canvas.drawColor(Color.BLUE);
                    if (i == 25) {
                        i = 0;
                        if (coords.size() < 10) {
                                coords.add(new Pair<>(50 + random.nextInt(500), 50 + random.nextInt(500)));

                        }
                    }
                    if (coords.size() < 10) {
                        for (Pair<Integer, Integer> pair : coords) {
                          //  canvas.drawRect(pair.first,pair.second-45,pair.first+500,pair.second,p1);
                            canvas.drawText("Kolyan pidaras", pair.first, pair.second, p);
                           // canvas.drawRect(pair.first,pair.second-45,pair.first+500,pair.second,p1);
                           // canvas.drawCircle(pair.first, pair.second, 5.0f, p1);
                        }
                    } else {
                        canvas.drawColor(0x00AAAAAA);
                        //  coords.clear();
                        canvas.drawText("Ты пидор", 500 ,500,p);
                    }

                    i++;
                } finally {
                    lock.unlock();
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
