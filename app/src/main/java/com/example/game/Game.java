package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.game.gameobjects.GameObject;
import com.example.game.gameobjects.GameScene;
import com.example.game.gamepanel.Inventory;
import com.example.game.gamepanel.ItemFrame;
import com.example.game.gamepanel.Performance;

import java.util.ArrayList;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Performance performance;
    private final GameScene gameScene;
    private final Inventory inventory;
    private GameLoop gameLoop;
    private ArrayList<GameObject> gameObjects = new ArrayList();
    private GameObject handObject;
    private ItemFrame itemFrame;

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        performance = new Performance(gameLoop, context);
        gameScene = new GameScene(context, 1);
        initGameObjects();
        gameScene.initSceneObjects(gameObjects);
        inventory = new Inventory();
        inventory.initInventoryObjects(gameObjects);
        handObject = gameObjects.get(0);
        itemFrame = new ItemFrame(context, handObject.getPosX(),handObject.getPosY(),handObject.getHeight(),handObject.getWidth());

        setFocusable(true);
    }

    private void initGameObjects() {
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_HAND.ordinal(),
                GameScene.SceneType.ID_INVENTORY.ordinal(),
                80, 950, Inventory.ITEM_SIZE, Inventory.ITEM_SIZE));
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_KNIFE.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                500, 300, 200, 80));
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_KEY.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                900, 200, 40, 60));
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_ROPE.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                1400, 50, 800, 20));
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_TO_LEFT.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                20, 450, 80, 140));
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_TO_RIGHT.ordinal(),
                GameScene.SceneType.ID_LEFT.ordinal(),
                1653, 450, 80, 140));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                for (GameObject object: gameObjects) {
                    if (object.isPressed(event.getX(),event.getY())){
                        switch (GameScene.SceneType.values()[object.getSceneID()]){
                            case ID_INVENTORY:
                                switch (GameObject.ObjectType.values()[object.getObjectID()]){
                                    case ID_HAND:
                                    case ID_KNIFE_INV:
                                    case ID_KEY_ON_A_ROPE:
                                        changeHandObject(object);
                                        break;
                                    case ID_KEY_INV:
                                        objectsCombine(object,GameObject.ObjectType.ID_ROPE_INV.ordinal(),
                                                GameObject.ObjectType.ID_KEY_ON_A_ROPE.ordinal());
                                        break;
                                    case ID_ROPE_INV:
                                        objectsCombine(object,GameObject.ObjectType.ID_KEY_INV.ordinal(),
                                                GameObject.ObjectType.ID_KEY_ON_A_ROPE.ordinal());
                                        break;
                                }
                                break;
                            case ID_START:
                                switch (GameObject.ObjectType.values()[object.getObjectID()]){
                                    case  ID_KNIFE:
                                        objectInteract(object,GameObject.ObjectType.ID_HAND.ordinal(),
                                                GameObject.ObjectType.ID_KNIFE_INV.ordinal(),
                                                GameScene.SceneType.ID_INVENTORY.ordinal());
                                        break;
                                    case  ID_KEY:
                                        objectInteract(object,GameObject.ObjectType.ID_HAND.ordinal(),
                                                GameObject.ObjectType.ID_KEY_INV.ordinal(),
                                                GameScene.SceneType.ID_INVENTORY.ordinal());
                                        break;
                                    case  ID_ROPE:
                                        if(objectInteract(object,GameObject.ObjectType.ID_KNIFE_INV.ordinal(),
                                                GameObject.ObjectType.ID_ROPE_INV.ordinal(),
                                                GameScene.SceneType.ID_INVENTORY.ordinal())){
                                            objectDelete();
                                        }
                                        break;
                                    case ID_TO_LEFT:
                                        gameScene.setSceneID(GameScene.SceneType.ID_LEFT.ordinal());
                                        gameScene.initSceneObjects(gameObjects);
                                }
                                break;
                            case ID_LEFT:
                                switch (GameObject.ObjectType.values()[object.getObjectID()]){
                                    case ID_TO_RIGHT:
                                        gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_START.ordinal());
                                }
                        }

                        break;
                    }
                }

        }
        return super.onTouchEvent(event);
    }

    private void changeHandObject(GameObject object) {
        handObject = object;
        itemFrame.posUpdate(object.getPosX());
    }

    private void objectDelete() {
        inventory.remove(handObject.getObjectID());
        gameObjects.removeIf(obj -> obj.getObjectID()==handObject.getObjectID());

        inventory.sort();
        changeHandObject(inventory.inventoryObjects.get(0));
    }

    private boolean objectInteract(GameObject object, int neededHandID, int changedItemID, int changedItemSceneID) {
        if (handObject.getObjectID()==neededHandID) {

            object.toInventory(inventory.inventoryObjects.size());
            object.setObjectID(changedItemID);
            object.setSceneID(changedItemSceneID);
            inventory.add(object);

            gameScene.remove(object);

            return true;
        }
        return false;
    }

    private void objectsCombine(GameObject object, int neededHandID, int changedItemID) {
        if (handObject.getObjectID()==neededHandID){

            handObject.setObjectID(changedItemID);

            inventory.remove(object);
            gameObjects.remove(object);

            inventory.sort();
            itemFrame.posUpdate(handObject.getPosX());

        }else {

            changeHandObject(object);

        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if(gameLoop.getState().equals(Thread.State.TERMINATED)){
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gameScene.draw(canvas);
        itemFrame.draw(canvas);
        inventory.draw(canvas);
        performance.draw(canvas);
    }

    public void update() {
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
