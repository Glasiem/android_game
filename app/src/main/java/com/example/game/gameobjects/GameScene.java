package com.example.game.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.game.R;

public class GameScene {
    private Context context;

    public enum SceneType{
        ID_INVENTORY,
        ID_START
    }
    private int sceneID;

    public GameScene(Context context, int sceneID) {
        this.context = context;
        this.sceneID = sceneID;
    }

    public int getSceneID() {
        return sceneID;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        canvas.drawRect(new Rect(0,0, 1920, 1080), paint);
    }
}
