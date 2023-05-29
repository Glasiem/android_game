package com.example.game.gameobjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;

import java.util.ArrayList;

public class GameScene {
    private Context context;
    private Sprite sprite;


    public void sceneChange(ArrayList<GameObject> gameObjects, int newSceneID, SpriteSheet spriteSheet) {
        setSceneID(newSceneID);
        setSprite(spriteSheet);
        initSceneObjects(gameObjects);
    }

    public enum SceneType{
        ID_INVENTORY,
        ID_START,
        ID_LEFT,
        ID_BACK,
        ID_DOOR,
        ID_CHEST,
        ID_LABYRINTH,
        ID_LABYRINTH_FINISHED,
        ID_ARCADE,
        ID_ARCADE_FINISHED
    }
    private int sceneID;

    public GameScene(Context context, SpriteSheet spriteSheet, int sceneID) {
        this.context = context;
        this.sceneID = sceneID;
        setSprite(spriteSheet);
    }

    public void setSprite(SpriteSheet spriteSheet) {
        sprite = spriteSheet.getSpriteByID(SpriteSheet.SpriteType.BG_SPRITE.ordinal(),sceneID);
    }
    public ArrayList<GameObject> sceneObjects = new ArrayList();

    public void initSceneObjects(ArrayList<GameObject> gameObjects) {
        sceneObjects.clear();
        for (GameObject object: gameObjects) {
            if(object.getSceneID() == sceneID) sceneObjects.add(object);
        }
    }

    public void add(GameObject object) {
        sceneObjects.add(object);
    }

    public void remove(GameObject object) {
        sceneObjects.remove(object);
    }

    public int getSceneID() {
        return sceneID;
    }

    public void setSceneID(int sceneID) {
        this.sceneID = sceneID;
    }

    public void draw(Canvas canvas){
        sprite.draw(canvas,0,0);
        for (GameObject object: sceneObjects) {
            object.draw(canvas);
        }
    }
}
