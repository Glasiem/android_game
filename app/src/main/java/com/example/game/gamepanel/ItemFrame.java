package com.example.game.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.game.R;

public class ItemFrame {
    private Context context;
    private int posX;
    private int posY;
    private int height;
    private int width;

    public ItemFrame(Context context, int posX, int posY, int height, int width) {
        this.context = context;
        this.posX = posX-8;
        this.posY = posY-8;
        this.height = height+16;
        this.width = width+16;
    }

    public void posUpdate(int invPos){
        posX = invPos-10;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.black);
        paint.setColor(color);
        canvas.drawRect(new Rect(posX,posY,posX+width,posY+height), paint);
    }
}
