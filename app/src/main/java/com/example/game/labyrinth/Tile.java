package com.example.game.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.game.R;

public class Tile {
    private final Rect mapLocationRect;
    private final int tileID;
    private Paint tilePaint;

    public Tile(Context context, Rect mapLocationRect, int tileID) {
        this.mapLocationRect = mapLocationRect;
        this.tileID = tileID;
        initPaint(context);
    }

    private void initPaint(Context context) {
        tilePaint = new Paint();
        int color;
        switch (TileType.values()[tileID]){
            case ID_PATH:
                color = ContextCompat.getColor(context, R.color.gray);
                break;
            case ID_WALL:
                color = ContextCompat.getColor(context, R.color.black);
                break;
            case ID_SPIKE:
                color = ContextCompat.getColor(context, R.color.red);
                break;
            case ID_KEY:
                color = ContextCompat.getColor(context, R.color.gold);
                break;
            case ID_DOOR:
                color = ContextCompat.getColor(context, R.color.brown);
                break;
            case ID_EXIT:
                color = ContextCompat.getColor(context, R.color.white);
                break;
            default:
                color = ContextCompat.getColor(context, R.color.teal_700);
                break;
        }
        tilePaint.setColor(color);
    }

    public enum TileType{
        ID_PATH,
        ID_WALL,
        ID_SPIKE,
        ID_KEY,
        ID_DOOR,
        ID_EXIT
    }

    public int getTileID() {
        return tileID;
    }



    public void draw(Canvas canvas){
        canvas.drawRect(mapLocationRect, tilePaint);
    }


}
