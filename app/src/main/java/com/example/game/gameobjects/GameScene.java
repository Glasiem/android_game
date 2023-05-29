package com.example.game.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.game.R;

import java.util.ArrayList;

public class GameScene {
    private Context context;



    public void sceneChange(ArrayList<GameObject> gameObjects, int newSceneID) {
        setSceneID(newSceneID);
        initSceneObjects(gameObjects);
    }

    public enum SceneType{
        ID_INVENTORY,
        ID_START,
        ID_LEFT,
        ID_LEFT_UPD
    }
    private int sceneID;

    public GameScene(Context context, int sceneID) {
        this.context = context;
        this.sceneID = sceneID;
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
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.white);
        paint.setColor(color);
        canvas.drawRect(new Rect(0,0, 1920, 1080), paint);
        for (GameObject object: sceneObjects) {
            object.draw(canvas);
        }
    }
}
