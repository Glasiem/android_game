package com.example.game;

import android.content.Context;
import android.graphics.Canvas;
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
    private ArrayList<GameObject> sceneObjects = new ArrayList();
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
        initSceneObjects();
        inventory = new Inventory();
        inventory.initInventoryObjects(gameObjects);
        handObject = gameObjects.get(0);
        itemFrame = new ItemFrame(context, handObject.getPosX(),handObject.getPosY(),handObject.getHeight(),handObject.getWidth());

        setFocusable(true);
    }

    private void initSceneObjects() {
        for (GameObject object: gameObjects) {
            if(object.getSceneID() == GameScene.SceneType.ID_START.ordinal()) sceneObjects.add(object);
        }
    }

    private void initGameObjects() {
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_HAND.ordinal(),
                GameScene.SceneType.ID_INVENTORY.ordinal(),
                80, 950, 64, 64));
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_KNIFE.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                500, 300, 200, 80));
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_KEY.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                900, 200, 40, 60));
        gameObjects.add(new GameObject(getContext(), GameObject.ObjectType.ID_ROPE.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                1400, 50, 800, 20));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                for (GameObject object: gameObjects) {
                    if (object.isPressed(event.getX(),event.getY())){
                        switch (GameObject.ObjectType.values()[object.getObjectID()]){
                            case ID_HAND:
                            case ID_KNIFE_INV:
                                handObject = object;
                                itemFrame.posUpdate(object.getPosX());
                                break;
                            case ID_KEY_INV:
                                if (handObject.getObjectID()==GameObject.ObjectType.ID_ROPE_INV.ordinal()){

                                    handObject.setObjectID(GameObject.ObjectType.ID_KEY_ON_A_ROPE.ordinal());

                                    inventory.remove(object);
                                    gameObjects.remove(object);

                                    inventory.sort();

                                }else {

                                    handObject = object;
                                    itemFrame.posUpdate(object.getPosX());

                                }
                                break;
                            case ID_ROPE_INV:
                                if (handObject.getObjectID()==GameObject.ObjectType.ID_KEY_INV.ordinal()){

                                    handObject.setObjectID(GameObject.ObjectType.ID_KEY_ON_A_ROPE.ordinal());

                                    inventory.remove(object);
                                    gameObjects.remove(object);

                                    inventory.sort();

                                }else {

                                    handObject = object;
                                    itemFrame.posUpdate(object.getPosX());

                                }
                                break;
                            case  ID_KNIFE:
                                if (handObject.getObjectID()==GameObject.ObjectType.ID_HAND.ordinal()) {

                                    object.toInventory(inventory.inventoryObjects.size());
                                    object.setObjectID(GameObject.ObjectType.ID_KNIFE_INV.ordinal());
                                    object.setSceneID(GameScene.SceneType.ID_INVENTORY.ordinal());
                                    inventory.add(object);

                                    sceneObjects.remove(object);
                                }
                                break;
                            case  ID_KEY:
                                if (handObject.getObjectID()==GameObject.ObjectType.ID_HAND.ordinal()) {

                                    object.toInventory(inventory.inventoryObjects.size());
                                    object.setObjectID(GameObject.ObjectType.ID_KEY_INV.ordinal());
                                    object.setSceneID(GameScene.SceneType.ID_INVENTORY.ordinal());
                                    inventory.add(object);

                                    sceneObjects.remove(object);
                                }
                                break;
                            case  ID_ROPE:
                                if (handObject.getObjectID()==GameObject.ObjectType.ID_KNIFE_INV.ordinal()) {

                                    object.toInventory(inventory.inventoryObjects.size());
                                    object.setObjectID(GameObject.ObjectType.ID_ROPE_INV.ordinal());
                                    object.setSceneID(GameScene.SceneType.ID_INVENTORY.ordinal());
                                    inventory.add(object);

                                    sceneObjects.remove(object);

                                    inventory.remove(GameObject.ObjectType.ID_KNIFE_INV.ordinal());
                                    gameObjects.removeIf(obj -> obj.getObjectID()==GameObject.ObjectType.ID_KNIFE_INV.ordinal());

                                    handObject = inventory.inventoryObjects.get(0);
                                    inventory.sort();
                                }
                                break;
                        }
                        break;
                    }
                }

        }
        return super.onTouchEvent(event);
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
        for (GameObject object: sceneObjects) {
            object.draw(canvas);
        }
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
