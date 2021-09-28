package com.example.jetpackpack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    private ArrayList<Obstacles> arrObstacles;
    private int sumObstacles, distance;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPakpak();
        initObstacles();
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    private void initObstacles() {
        sumObstacles = 6;
        distance = 300*Constants.SCREEN_HEIGHT/1000;
        arrObstacles = new ArrayList<>();
        for(int i = 0; i<sumObstacles;i++){
            if (i < sumObstacles / 2) {
                this.arrObstacles.add(new Obstacles(Constants.SCREEN_WIDTH+i*((Constants.SCREEN_WIDTH+200* Constants.SCREEN_WIDTH/1920)/(sumObstacles/2)),
                        0,200* Constants.SCREEN_WIDTH/1920,Constants.SCREEN_HEIGHT/2));
                this.arrObstacles.get(this.arrObstacles.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe2));
                this.arrObstacles.get(this.arrObstacles.size()-1).randomY();
            }else {
                this.arrObstacles.add(new Obstacles(this.arrObstacles.get(i-sumObstacles/2).getX(),this.arrObstacles.get(i-sumObstacles/2).getY()
                +this.arrObstacles.get(i-sumObstacles/2).getHeight() + this.distance, 200* Constants.SCREEN_WIDTH/1920,Constants.SCREEN_HEIGHT/2));
                this.arrObstacles.get(this.arrObstacles.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.pipe1));
            }
        }
    }

    private void initPakpak() {
        pakPak = new PakPak();
        pakPak.setWidth(100*Constants.SCREEN_WIDTH/1920);  //Réso de l'écran
        pakPak.setHeight(100*Constants.SCREEN_HEIGHT/1000);
        pakPak.setX(100*Constants.SCREEN_WIDTH/1920);
        pakPak.setY(Constants.SCREEN_HEIGHT/2-pakPak.getHeight()/2);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(),R.drawable.bird2));
        pakPak.setArrBms(arrBms);
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        pakPak.draw(canvas);
        for (int i = 0; i <sumObstacles; i++){
            if (this.arrObstacles.get(i).getX() < -arrObstacles.get(i).getWidth()) {
                    this.arrObstacles.get(i).setX(Constants.SCREEN_WIDTH);
                if (i < sumObstacles / 2) {
                    arrObstacles.get(i).randomY();
                }else {
                    arrObstacles.get(i).setY(this.arrObstacles.get(i-sumObstacles/2).getY()
                            +this.arrObstacles.get(i-sumObstacles/2).getHeight() + this.distance);
                }
                this.arrObstacles.get(i).draw(canvas);
            }
        }
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

