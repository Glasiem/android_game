package com.example.game.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.game.GameLoop;
import com.example.game.R;

import java.text.DecimalFormat;

public class Performance {

    private GameLoop gameLoop;
    private Context context;

    public Performance(GameLoop gameLoop, Context context) {
        this.gameLoop = gameLoop;
        this.context = context;
    }

    public void draw(Canvas canvas) {
        drawUPS(canvas);
        //drawFPS(canvas);
    }

    public void drawUPS(Canvas canvas){
        String averageUPS = new DecimalFormat("#0.0").format(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + averageUPS, 100, 60, paint);
    }

    public void drawFPS(Canvas canvas){
        String averageFPS = new DecimalFormat("#0.0").format(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + averageFPS, 100, 160, paint);
    }
}
