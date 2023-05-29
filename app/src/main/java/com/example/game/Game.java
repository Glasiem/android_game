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
import com.example.game.graphics.SpriteSheet;
import com.example.game.labyrinth.GameDisplay;
import com.example.game.labyrinth.Joystick;
import com.example.game.labyrinth.Player;
import com.example.game.labyrinth.TileMap;

import java.util.ArrayList;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Performance performance;
    private final GameScene gameScene;
    private final Inventory inventory;
    private GameLoop gameLoop;
    private ArrayList<GameObject> gameObjects = new ArrayList();
    private GameObject handObject;
    private ItemFrame itemFrame;
    private Joystick joystick;
    private Player player;
    private GameDisplay gameDisplay;
    private TileMap tileMap;
    private SpriteSheet spriteSheet;

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        spriteSheet = new SpriteSheet(context);
        performance = new Performance(gameLoop, context);
        gameScene = new GameScene(context, spriteSheet, 1);
        initGameObjects(spriteSheet);
        gameScene.initSceneObjects(gameObjects);
        inventory = new Inventory();
        inventory.initInventoryObjects(gameObjects);
        handObject = gameObjects.get(0);
        itemFrame = new ItemFrame(context, handObject.getPosX(),handObject.getPosY(),handObject.getHeight(),handObject.getWidth());
        joystick = new Joystick(300,500,200,70,context);
        player = new Player(1100,450,30,joystick,context,spriteSheet);
        gameDisplay = new GameDisplay(600,200,1600,700,player);
        tileMap = new TileMap(context,player, spriteSheet);

        setFocusable(true);
    }

    private void initGameObjects(SpriteSheet spriteSheet) {
        gameObjects.add(new GameObject(getContext(), spriteSheet,GameObject.ObjectType.ID_HAND.ordinal(),
                GameScene.SceneType.ID_INVENTORY.ordinal(),
                80, 950, Inventory.ITEM_SIZE, Inventory.ITEM_SIZE));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_CUTTERS.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                900, 200, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_CHEST_CLOSED.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                400, 700, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_START_TO_LEFT.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                20, 500, 100, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_START_TO_DOOR.ordinal(),
                GameScene.SceneType.ID_START.ordinal(),
                1700, 500, 100, 200));


        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_METAL_GRID.ordinal(),
                GameScene.SceneType.ID_LEFT.ordinal(),
                900, 200, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_KEY.ordinal(),
                GameScene.SceneType.ID_LEFT.ordinal(),
                400, 700, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_LEFT_TO_BACK.ordinal(),
                GameScene.SceneType.ID_LEFT.ordinal(),
                20, 500, 100, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_LEFT_TO_START.ordinal(),
                GameScene.SceneType.ID_LEFT.ordinal(),
                1700, 500, 100, 200));


        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_ARCADE_MACHINE.ordinal(),
                GameScene.SceneType.ID_BACK.ordinal(),
                400, 700, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_BACK_TO_DOOR.ordinal(),
                GameScene.SceneType.ID_BACK.ordinal(),
                20, 500, 100, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_BACK_TO_LEFT.ordinal(),
                GameScene.SceneType.ID_BACK.ordinal(),
                1700, 500, 100, 200));


        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_DOOR.ordinal(),
                GameScene.SceneType.ID_DOOR.ordinal(),
                900, 200, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_DOOR_TO_START.ordinal(),
                GameScene.SceneType.ID_DOOR.ordinal(),
                20, 500, 100, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_DOOR_TO_BACK.ordinal(),
                GameScene.SceneType.ID_DOOR.ordinal(),
                1700, 500, 100, 200));


        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_BATTERY.ordinal(),
                GameScene.SceneType.ID_CHEST.ordinal(),
                900, 200, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_CHEST_BACK.ordinal(),
                GameScene.SceneType.ID_CHEST.ordinal(),
                20, 20, 100, 200));


        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_KEY_PART_TWO.ordinal(),
                GameScene.SceneType.ID_ARCADE_FINISHED.ordinal(),
                900, 200, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_ARCADE_MACHINE_BACK.ordinal(),
                GameScene.SceneType.ID_ARCADE.ordinal(),
                20, 20, 100, 200));


        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_KEY_PART_ONE.ordinal(),
                GameScene.SceneType.ID_LABYRINTH_FINISHED.ordinal(),
                900, 200, 200, 200));
        gameObjects.add(new GameObject(getContext(),spriteSheet, GameObject.ObjectType.ID_LABYRINTH_BOX_BACK.ordinal(),
                GameScene.SceneType.ID_LABYRINTH.ordinal(),
                20, 20, 100, 200));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                if (gameScene.getSceneID()== GameScene.SceneType.ID_LABYRINTH.ordinal() && joystick.isPressed(event.getX(),event.getY())){
                    joystick.setPressed(true);
                }
                else {
                    for (GameObject object : inventory.inventoryObjects){
                        if (object.isPressed(event.getX(), event.getY())) {
                            switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                case ID_HAND:
                                case ID_CUTTERS_INV:
                                case ID_BATTERY_INV:
                                case ID_FINAL_KEY_INV:
                                case ID_KEY_INV:
                                    changeHandObject(object);
                                    break;
                                case ID_KEY_PART_ONE_INV:
                                    objectsCombine(object, GameObject.ObjectType.ID_KEY_PART_TWO_INV.ordinal(),
                                            GameObject.ObjectType.ID_FINAL_KEY_INV.ordinal());
                                    break;
                                case ID_KEY_PART_TWO_INV:
                                    objectsCombine(object, GameObject.ObjectType.ID_KEY_PART_ONE_INV.ordinal(),
                                            GameObject.ObjectType.ID_FINAL_KEY_INV.ordinal());
                                    break;
                            }
                        }
                    }
                    for (GameObject object : gameScene.sceneObjects) {
                        if (object.isPressed(event.getX(), event.getY())) {
                            switch (GameScene.SceneType.values()[object.getSceneID()]) {
                                case ID_START:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_CUTTERS:
                                            objectInteract(object, GameObject.ObjectType.ID_HAND.ordinal(),
                                                    GameObject.ObjectType.ID_CUTTERS_INV.ordinal(),
                                                    GameScene.SceneType.ID_INVENTORY.ordinal());
                                            break;
                                        case ID_CHEST_CLOSED:
                                            objectInteract(object, GameObject.ObjectType.ID_KEY_INV.ordinal(),
                                                    GameObject.ObjectType.ID_CHEST_OPEN.ordinal(),
                                                    GameScene.SceneType.ID_START.ordinal());
                                            objectDelete();
                                            break;
                                        case ID_CHEST_OPEN:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_CHEST.ordinal(), spriteSheet);
                                            break;
                                        case ID_START_TO_LEFT:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_LEFT.ordinal(), spriteSheet);
                                            break;
                                        case ID_START_TO_DOOR:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_DOOR.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                                case ID_LEFT:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_KEY:
                                            objectInteract(object, GameObject.ObjectType.ID_HAND.ordinal(),
                                                    GameObject.ObjectType.ID_KEY_INV.ordinal(),
                                                    GameScene.SceneType.ID_INVENTORY.ordinal());
                                            break;
                                        case ID_METAL_GRID:
                                            objectInteract(object, GameObject.ObjectType.ID_CUTTERS_INV.ordinal(),
                                                    GameObject.ObjectType.ID_LABYRINTH_BOX.ordinal(),
                                                    GameScene.SceneType.ID_LEFT.ordinal());
                                            objectDelete();
                                            break;
                                        case ID_LABYRINTH_BOX:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_LABYRINTH.ordinal(), spriteSheet);
                                            break;
                                        case ID_LABYRINTH_BOX_FINISHED:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_LABYRINTH_FINISHED.ordinal(), spriteSheet);
                                            break;
                                        case ID_LEFT_TO_START:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_START.ordinal(), spriteSheet);
                                            break;
                                        case ID_LEFT_TO_BACK:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_BACK.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                                case ID_BACK:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_ARCADE_MACHINE:
                                            objectInteract(object, GameObject.ObjectType.ID_BATTERY_INV.ordinal(),
                                                    GameObject.ObjectType.ID_ARCADE_MACHINE_WORKING.ordinal(),
                                                    GameScene.SceneType.ID_BACK.ordinal());
                                            objectDelete();
                                            break;
                                        case ID_ARCADE_MACHINE_WORKING:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_ARCADE.ordinal(), spriteSheet);
                                            break;
                                        case ID_ARCADE_MACHINE_FINISHED:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_ARCADE_FINISHED.ordinal(), spriteSheet);
                                            break;
                                        case ID_BACK_TO_LEFT:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_LEFT.ordinal(), spriteSheet);
                                            break;
                                        case ID_BACK_TO_DOOR:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_DOOR.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                                case ID_DOOR:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_DOOR:

                                            break;
                                        case ID_DOOR_TO_START:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_START.ordinal(), spriteSheet);
                                            break;
                                        case ID_DOOR_TO_BACK:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_BACK.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                                case ID_CHEST:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_BATTERY:
                                            objectInteract(object, GameObject.ObjectType.ID_HAND.ordinal(),
                                                    GameObject.ObjectType.ID_BATTERY_INV.ordinal(),
                                                    GameScene.SceneType.ID_INVENTORY.ordinal());
                                            break;
                                        case ID_CHEST_BACK:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_START.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                                case ID_LABYRINTH:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_LABYRINTH_BOX_BACK:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_LEFT.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                                case ID_LABYRINTH_FINISHED:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_KEY_PART_ONE:
                                            objectInteract(object, GameObject.ObjectType.ID_HAND.ordinal(),
                                                    GameObject.ObjectType.ID_KEY_PART_ONE_INV.ordinal(),
                                                    GameScene.SceneType.ID_INVENTORY.ordinal());
                                            break;
                                        case ID_LABYRINTH_BOX_FINISHED_BACK:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_LEFT.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                                case ID_ARCADE:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_ARCADE_MACHINE_BACK:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_BACK.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                                case ID_ARCADE_FINISHED:
                                    switch (GameObject.ObjectType.values()[object.getObjectID()]) {
                                        case ID_KEY_PART_TWO:
                                            objectInteract(object, GameObject.ObjectType.ID_HAND.ordinal(),
                                                    GameObject.ObjectType.ID_KEY_PART_TWO_INV.ordinal(),
                                                    GameScene.SceneType.ID_INVENTORY.ordinal());
                                            break;
                                        case ID_ARCADE_MACHINE_BACK:
                                            gameScene.sceneChange(gameObjects, GameScene.SceneType.ID_BACK.ordinal(), spriteSheet);
                                            break;
                                    }
                                    break;
                            }
                            break;
                        }
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick.isPressed()){
                    joystick.setActuators(event.getX(),event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (joystick.isPressed()){
                    joystick.setPressed(false);
                    joystick.resetActuator();
                }
                return true;
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
        if (handObject.getObjectID()==neededHandID || neededHandID== GameObject.ObjectType.ID_HAND.ordinal()) {
            object.setObjectID(changedItemID);
            object.setSprite(spriteSheet);
            object.setSceneID(changedItemSceneID);

            if (changedItemSceneID == GameScene.SceneType.ID_INVENTORY.ordinal()) {
                object.toInventory(inventory.inventoryObjects.size());
                inventory.add(object);
            }
            if (changedItemSceneID != gameScene.getSceneID()) {
                gameScene.remove(object);
            }

            return true;
        }
        return false;
    }

    private void objectChange(int objectID, int changedItemID, int changedItemSceneID){
        for (GameObject object: gameObjects) {
            if (object.getObjectID()==objectID){
                object.setObjectID(changedItemID);
                object.setSprite(spriteSheet);
                object.setSceneID(changedItemSceneID);
                break;
            }
        }
    }

    private void objectsCombine(GameObject object, int neededHandID, int changedItemID) {
        if (handObject.getObjectID()==neededHandID){

            handObject.setObjectID(changedItemID);
            handObject.setSprite(spriteSheet);

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
        if (gameScene.getSceneID()== GameScene.SceneType.ID_LABYRINTH.ordinal()){
            tileMap.draw(canvas,gameDisplay);
            player.draw(canvas,gameDisplay);
            joystick.draw(canvas);
        }
        performance.draw(canvas);
    }

    public void update() {
        if (gameScene.getSceneID()== GameScene.SceneType.ID_LABYRINTH.ordinal()) {
            joystick.update();
            player.update(tileMap, gameDisplay);
            gameDisplay.update();
            if(player.isLabyrinthCompleted()){
                objectChange(GameObject.ObjectType.ID_LABYRINTH_BOX.ordinal(),
                        GameObject.ObjectType.ID_LABYRINTH_BOX_FINISHED.ordinal(),
                        GameScene.SceneType.ID_LEFT.ordinal());
                objectChange(GameObject.ObjectType.ID_LABYRINTH_BOX_BACK.ordinal(),
                        GameObject.ObjectType.ID_LABYRINTH_BOX_FINISHED_BACK.ordinal(),
                        GameScene.SceneType.ID_LABYRINTH_FINISHED.ordinal());
                gameScene.sceneChange(gameObjects,GameScene.SceneType.ID_LABYRINTH_FINISHED.ordinal(), spriteSheet);
            }
        }
    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
