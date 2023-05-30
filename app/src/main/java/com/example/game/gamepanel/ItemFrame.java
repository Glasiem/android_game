package com.example.game.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.game.R;
import com.example.game.gameobjects.GameObject;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;

public class ItemFrame {
    private Context context;
    private int posX;
    private int posY;
    private int height;
    private int width;
    private Sprite sprite;

    public ItemFrame(Context context, int posX, int posY, int height, int width, SpriteSheet spriteSheet) {
        this.context = context;
        this.posX = posX-8;
        this.posY = posY-8;
        this.height = height+16;
        this.width = width+16;
        setSprite(spriteSheet);
    }

    public void setSprite(SpriteSheet spriteSheet) {
        sprite = spriteSheet.getSpriteByID(SpriteSheet.SpriteType.OBJ_SPRITE.ordinal(),
                GameObject.ObjectType.ID_ITEM_FRAME.ordinal(), height, width);
    }

    public void posUpdate(int invPos){
        posX = invPos-10;
    }

    public void draw(Canvas canvas){
        sprite.draw(canvas,posX,posY);
    }
}
