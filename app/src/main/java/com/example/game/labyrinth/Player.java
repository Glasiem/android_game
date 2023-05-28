package com.example.game.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.game.GameLoop;
import com.example.game.R;

public class Player {
    private static final double PIXELS_PER_SECOND = 400;
    private static final double MAX_SPEED = PIXELS_PER_SECOND/ GameLoop.MAX_UPS;
    private double playerX;
    private double playerY;
    private double velocityX = 0;
    private double velocityY = 0;
    private double playerRadius;
    private Joystick joystick;
    private Context context;

    public Player(double playerX, double playerY, double playerRadius, Joystick joystick, Context context) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerRadius = playerRadius;
        this.joystick = joystick;
        this.context = context;
    }


    public void update(){
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        playerX += velocityX;
        playerY += velocityY;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.teal_700);
        paint.setColor(color);
        canvas.drawCircle((float) gameDisplay.gameToDisplayX(playerX),
                (float) gameDisplay.gameToDisplayY(playerY),
                (float) playerRadius, paint);
    }

    public double getX() {
        return playerX;
    }

    public double getY() {
        return playerY;
    }
}
