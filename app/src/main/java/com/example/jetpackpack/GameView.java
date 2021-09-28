package com.example.jetpackpack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private PakPak pakPak;
    private Handler handler;
    private Runnable r;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        pakPak = new PakPak();
        pakPak.setWidth(100*Constants.SCREEN_WIDTH/1920);//Réso de l'écran
        pakPak.setHeight(100*Constants.SCREEN_HEIGHT/1000);
        pakPak.setX(100*Constants.SCREEN_WIDTH/1920);
        pakPak.setY(Constants.SCREEN_HEIGHT/2-pakPak.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        ///////////////////////////////////// je me suis arrete la
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird2));
        pakPak.setArrBms(arrBms);
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }
    public void draw(Canvas canvas){
        super.draw(canvas);
        pakPak.draw(canvas);
        handler.postDelayed(r,10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_MOVE){
            pakPak.setDrop(-15);
        }
        return true;
    }
}

