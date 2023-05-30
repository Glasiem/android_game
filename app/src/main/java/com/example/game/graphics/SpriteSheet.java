package com.example.game.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.game.R;

public class SpriteSheet {
    public final double scaleX;
    public final double scaleY;

    public enum SpriteType{
        TILE_SPRITE,
        ARCADE_SPRITE,
        BG_SPRITE,
        OBJ_SPRITE
    }
    private static final int BG_SPRITE_ROWS = 5;
    public static final int TILE_SPRITE_HEIGHT = 64;
    public static final int TILE_SPRITE_WIDTH = 64;
    public static final int ARCADE_SPRITE_HEIGHT = 64;
    public static final int ARCADE_SPRITE_WIDTH = 64;
    private static final int BG_SPRITE_HEIGHT = 1080;
    private static final int BG_SPRITE_WIDTH = 1920;
    private static final int OBJ_SPRITE_HEIGHT = 512;
    private static final int OBJ_SPRITE_WIDTH = 512;
    private Bitmap bitmap;

    public SpriteSheet(Context context, int displayWidth, int displayHeight) {
        scaleX  = (double) displayWidth/1920;
        scaleY = (double) displayHeight/1080;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, options);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public Sprite getSpriteByID(int type, int id){
        int left = 0, top = 0, right = 0, bottom = 0;
        switch (SpriteType.values()[type]) {
            case TILE_SPRITE:
                left = TILE_SPRITE_WIDTH * id;
                top = 0;
                right = TILE_SPRITE_WIDTH * (id + 1);
                bottom = TILE_SPRITE_HEIGHT;
                break;
            case ARCADE_SPRITE:
                left = ARCADE_SPRITE_WIDTH * id;
                top = TILE_SPRITE_HEIGHT ;
                right = ARCADE_SPRITE_WIDTH * (id + 1);
                bottom = TILE_SPRITE_HEIGHT + ARCADE_SPRITE_HEIGHT;
                break;
            case BG_SPRITE:
                left = (id % 2)*BG_SPRITE_WIDTH;
                right = (id % 2 + 1)*BG_SPRITE_WIDTH;
                top = TILE_SPRITE_HEIGHT + ARCADE_SPRITE_HEIGHT + BG_SPRITE_HEIGHT*(Math.floorDiv(id, 2));
                bottom = TILE_SPRITE_HEIGHT + ARCADE_SPRITE_HEIGHT + BG_SPRITE_HEIGHT*(Math.floorDiv(id, 2)+1);
                break;
        }
        return new Sprite(this,
                new Rect(left, top, right, bottom));
    }

    public Sprite getSpriteByID(int type, int id, int height, int width) {
        int top = TILE_SPRITE_HEIGHT + ARCADE_SPRITE_HEIGHT + BG_SPRITE_HEIGHT*BG_SPRITE_ROWS + OBJ_SPRITE_HEIGHT * (Math.floorDiv(id, 8));
        int left = (id % 8) * OBJ_SPRITE_WIDTH;
        int right = (id % 8) * OBJ_SPRITE_WIDTH+width;
        int bottom = TILE_SPRITE_HEIGHT + ARCADE_SPRITE_HEIGHT + BG_SPRITE_HEIGHT*BG_SPRITE_ROWS + OBJ_SPRITE_HEIGHT * (Math.floorDiv(id, 8)) + height;
        return new Sprite(this,
                new Rect(left, top, right, bottom));
    }
}
