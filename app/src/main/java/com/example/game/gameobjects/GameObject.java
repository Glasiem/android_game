package com.example.game.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.game.R;
import com.example.game.gamepanel.Inventory;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;

import java.util.ArrayList;

public class GameObject {
    private Context context;
    private Sprite sprite;

    public enum ObjectType{
        ID_HAND,
        ID_CUTTERS,
        ID_CUTTERS_INV,
        ID_KEY,
        ID_KEY_INV,
        ID_BATTERY,
        ID_BATTERY_INV,
        ID_CHEST_CLOSED,
        ID_CHEST_OPEN,
        ID_CHEST_BACK,
        ID_METAL_GRID,
        ID_LABYRINTH_BOX,
        ID_LABYRINTH_BOX_BACK,
        ID_LABYRINTH_BOX_FINISHED,
        ID_LABYRINTH_BOX_FINISHED_BACK,
        ID_ARCADE_MACHINE,
        ID_ARCADE_MACHINE_WORKING,
        ID_ARCADE_MACHINE_BACK,
        ID_ARCADE_MACHINE_FINISHED,
        ID_ARCADE_MACHINE_FINISHED_BACK,
        ID_DOOR,
        ID_START_TO_LEFT,
        ID_START_TO_DOOR,
        ID_LEFT_TO_START,
        ID_LEFT_TO_BACK,
        ID_BACK_TO_LEFT,
        ID_BACK_TO_DOOR,
        ID_DOOR_TO_START,
        ID_DOOR_TO_BACK,
        ID_KEY_PART_ONE,
        ID_KEY_PART_ONE_INV,
        ID_KEY_PART_TWO,
        ID_KEY_PART_TWO_INV,
        ID_FINAL_KEY_INV,
        ID_ITEM_FRAME
    }
    private int objectID;
    private int sceneID;
    private int posX;
    private int posY;
    private int height;
    private int width;

    public GameObject(Context context, SpriteSheet spriteSheet, int objectID, int sceneID, int posX, int posY, int height, int width) {
        this.context = context;
        this.objectID = objectID;
        this.sceneID = sceneID;
        this.posX = posX;
        this.posY = posY;
        this.height = height;
        this.width = width;
        setSprite(spriteSheet);
    }

    public void setSprite(SpriteSheet spriteSheet) {
        sprite = spriteSheet.getSpriteByID(SpriteSheet.SpriteType.OBJ_SPRITE.ordinal(), objectID, height, width);
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
        sprite.draw(canvas,posX,posY);
    }

    public boolean isPressed(float x, float y) {
        return posX < x && x < posX + width && posY < y && y < posY + height;
    }
}
