package com.example.game.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.game.R;
import com.example.game.gamepanel.Inventory;

import java.util.ArrayList;

public class GameObject {
    private Context context;

    public enum ObjectType{
        ID_HAND,
        ID_KNIFE,
        ID_KNIFE_INV,
        ID_KEY,
        ID_KEY_INV,
        ID_ROPE,
        ID_ROPE_INV,
        ID_KEY_ON_A_ROPE,
        ID_TO_LEFT,
        ID_TO_LEFT_UPD,
        ID_TO_RIGHT
    }
    private int objectID;
    private int sceneID;
    private int posX;
    private int posY;
    private int height;
    private int width;

    public GameObject(Context context, int objectID, int sceneID, int posX, int posY, int height, int width) {
        this.context = context;
        this.objectID = objectID;
        this.sceneID = sceneID;
        this.posX = posX;
        this.posY = posY;
        this.height = height;
        this.width = width;
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public int getSceneID() {
        return sceneID;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void toInventory(int invPos){
        sceneID = GameScene.SceneType.ID_INVENTORY.ordinal();
        width = Inventory.ITEM_SIZE;
        height = Inventory.ITEM_SIZE;
        posX = 80 + 80*invPos;
        posY = 950;
    }

    public void posUpdate(int invPos){
        posX = 80 + 80*invPos;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.teal_700);
        paint.setColor(color);
        canvas.drawRect(new Rect(posX,posY,posX+width,posY+height), paint);
    }

    public boolean isPressed(float x, float y) {
        return posX < x && x < posX + width && posY < y && y < posY + height;
    }
}
