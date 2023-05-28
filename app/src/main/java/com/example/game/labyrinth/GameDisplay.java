package com.example.game.labyrinth;

import android.graphics.Rect;

public class GameDisplay {
    private final static int ROW_START = 2;
    private final static int COLUMN_START = 2;
    public final Rect DISPLAY_RECT;
    private int displayWidth;
    private int displayHeight;
    private Player centerObject;
    private int displayCenterX;
    private int displayCenterY;
    private double offsetX;
    private double offsetY;
    private double mapStartToPlayerStartOffsetX;
    private double mapStartToPlayerStartOffsetY;

    public GameDisplay(int left, int top, int right, int bottom, Player centerObject) {
        this.displayWidth = right-left;
        this.displayHeight = bottom-top;
        DISPLAY_RECT = new Rect(left, top, right, bottom);
        this.centerObject = centerObject;

        displayCenterX = (left+right)/2;
        displayCenterY = (top+bottom)/2;

        mapStartToPlayerStartOffsetX = centerObject.getX()-(ROW_START - 0.5)*MapLayout.TILE_SIZE;
        mapStartToPlayerStartOffsetY = centerObject.getY()-(COLUMN_START - 0.5)*MapLayout.TILE_SIZE;
    }

    public void update(){
        offsetX = displayCenterX - centerObject.getX();
        offsetY = displayCenterY - centerObject.getY();
    }

    public double gameToDisplayX(double x){
        return x + offsetX;
    }

    public double gameToDisplayY(double y){
        return y + offsetY;
    }

    public Rect getGameRect(){
        return new Rect(
                (int)(centerObject.getX()-displayWidth/2-mapStartToPlayerStartOffsetX),
                (int)(centerObject.getY()-displayHeight/2-mapStartToPlayerStartOffsetY),
                (int)(centerObject.getX()+displayWidth/2-mapStartToPlayerStartOffsetX),
                (int)(centerObject.getY()+displayHeight/2-mapStartToPlayerStartOffsetY)
        );
    }
}
