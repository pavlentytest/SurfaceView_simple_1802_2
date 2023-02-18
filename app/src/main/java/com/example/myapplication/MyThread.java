package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MyThread extends Thread {

    private Paint paint;
    private SurfaceHolder holder;
    private boolean flag;

    MyThread(SurfaceHolder holder) {
        this.holder = holder;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public long getTime() {
        return System.nanoTime()/1000; // мкс
    }

    private long redrawTime = 0;
    @Override
    public void run() {
        Canvas canvas;
        // логика отрисовки
        while(flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - redrawTime;
            if(elapsedTime < 100000) {
                continue;
            }
            // блокировка Canvas для отрисовки
            canvas = holder.lockCanvas();
            drawCircle(canvas);
            // разблокируй и покажи
            holder.unlockCanvasAndPost(canvas);
            redrawTime = getTime();
        }
    }

    public void drawCircle(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        float radius = (float)(300 * Math.random());
        canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2,radius ,paint);
    }
}
