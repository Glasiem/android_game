package com.example.game.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Sprite {
    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int x, int y){
        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new RectF((float) (x*spriteSheet.scaleX), (float) (y*spriteSheet.scaleY),
                        (float) ((x+rect.width())*spriteSheet.scaleX), (float) ((y+rect.height())*spriteSheet.scaleY)),
                null
        );
    }

    public void drawTile(Canvas canvas, int x, int y){
        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new RectF((float) (x), (float) (y),
                        (float) ((x+rect.width())), (float) ((y+rect.height()))),
                null
        );
    }
}
