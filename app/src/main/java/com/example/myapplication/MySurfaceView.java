package com.example.myapplication;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private MyThread myThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        // Стартуем отрисовки
        myThread = new MyThread(getHolder());
        myThread.setFlag(true);
        myThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // Изменяется размер SurfaceView
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        // Завершаем поток отрисовки
        boolean retry = true;
        myThread.setFlag(false);
        while(retry) {
            try {
                myThread.join();
                retry = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
