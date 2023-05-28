package com.example.game.gamepanel;

import android.graphics.Canvas;

import com.example.game.gameobjects.GameObject;

import java.util.ArrayList;

public class Inventory {
    public ArrayList<GameObject> inventoryObjects = new ArrayList();

    public void remove(int ordinal) {
        inventoryObjects.removeIf(obj -> obj.getObjectID()==ordinal);
    }

    public void remove(GameObject object) {
        inventoryObjects.remove(object);
    }

    public void initInventoryObjects(ArrayList<GameObject> gameObjects) {
        for (GameObject object: gameObjects) {
            if (object.getObjectID() == 0) inventoryObjects.add(object);
        }
    }


    public void add(GameObject object) {
        inventoryObjects.add(object);
    }

    public void draw(Canvas canvas) {
        for (GameObject object: inventoryObjects) {
            object.draw(canvas);
        }
    }

    public void sort(){
        int k = 0;
        for (GameObject object: inventoryObjects) {
            object.posUpdate(k);
            k++;
        }
    }
}
