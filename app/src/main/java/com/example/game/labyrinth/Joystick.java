package com.example.game.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.game.R;

public class Joystick {
    private int outerCircleCenterX;
    private int outerCircleCenterY;
    private int outerCircleRadius;

    private int innerCircleCenterX;
    private int innerCircleCenterY;
    private int innerCircleRadius;

    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double actuatorX;
    private double actuatorY;
    private Context context;

    public Joystick(int CircleCenterX, int CircleCenterY, int outerCircleRadius, int innerCircleRadius, Context context) {
        this.outerCircleCenterX = CircleCenterX;
        this.outerCircleCenterY = CircleCenterY;
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleCenterX = CircleCenterX;
        this.innerCircleCenterY = CircleCenterY;
        this.innerCircleRadius = innerCircleRadius;
        this.context = context;
    }

    public void setActuators(double touchX, double touchY){
        double deltaX = touchX - outerCircleCenterX;
        double deltaY = touchY - outerCircleCenterY;
        double deltaDistance = Math.sqrt(deltaX*deltaX+deltaY*deltaY);

        if (deltaDistance<outerCircleRadius){
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        }
        else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public boolean isPressed(double touchX, double touchY){
        joystickCenterToTouchDistance = Math.sqrt(Math.pow(touchX-outerCircleCenterX,2)+Math.pow(touchY-outerCircleCenterY,2));
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }

    public void update(){
        innerCircleCenterX = (int)(outerCircleCenterX + actuatorX*outerCircleRadius);
        innerCircleCenterY = (int)(outerCircleCenterY + actuatorY*outerCircleRadius);
    }
    
    public void draw(Canvas canvas){
        Paint outerPaint = new Paint();
        int outerColor = ContextCompat.getColor(context, R.color.black);
        outerPaint.setColor(outerColor);
        canvas.drawCircle(outerCircleCenterX,outerCircleCenterY,outerCircleRadius, outerPaint);
        Paint innerPaint = new Paint();
        int innerColor = ContextCompat.getColor(context, R.color.purple_200);
        innerPaint.setColor(innerColor);
        canvas.drawCircle(innerCircleCenterX,innerCircleCenterY,innerCircleRadius, innerPaint);
    }
}
