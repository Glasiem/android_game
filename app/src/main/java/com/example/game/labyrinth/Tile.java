package com.example.game.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.game.R;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;

public class Tile {
    private final Rect mapLocationRect;
    private final int tileID;
    private Sprite sprite;

    public Tile(Context context, Rect mapLocationRect, int tileID, SpriteSheet spriteSheet) {
        this.mapLocationRect = mapLocationRect;
        this.tileID = tileID;
        sprite = spriteSheet.getSpriteByID(SpriteSheet.SpriteType.TILE_SPRITE.ordinal(),tileID);
    }

    public enum TileType{
        ID_PATH,
        ID_WALL,
        ID_SPIKE,
        ID_KEY,
        ID_DOOR,
        ID_EXIT,
        ID_PLAYER
    }

    public int getTileID() {
        return tileID;
    }



    public void draw(Canvas canvas){
        sprite.draw(canvas,mapLocationRect.left,mapLocationRect.top);
    }


}
