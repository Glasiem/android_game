package com.example.game.arcade;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;

public class Arcade {

    private final static int ELEMENT_TYPES_NUMBER = 6;
    private final static int ROWS_NUMBER = 7;
    private final static int COLUMNS_NUMBER = 10;
    private int[][] elements;
    private SpriteSheet spriteSheet;
    private int posX;
    private int posY;
    private boolean isArcadeCompleted = false;

    public boolean onArcade(float x, float y) {
        return posX < x && x < posX + COLUMNS_NUMBER*SpriteSheet.ARCADE_SPRITE_WIDTH &&
                posY < y && y < posY + ROWS_NUMBER*SpriteSheet.ARCADE_SPRITE_HEIGHT;
    }

    public void onSwipeRight(float x, float y) {
        int column = getElementColumn(x);
        int row = getElementRow(y);
        if (column != COLUMNS_NUMBER-1 && elements[row][column]!=ElementType.ID_KEY.ordinal()){
            int temp = elements[row][column];
            elements[row][column]=elements[row][column+1];
            elements[row][column+1]=temp;
            if(!checkAndUpdate()){
                temp = elements[row][column];
                elements[row][column]=elements[row][column+1];
                elements[row][column+1]=temp;
            };
            bitmapUpdate();
        }
    }

    public void onSwipeLeft(float x, float y) {
        int column = getElementColumn(x);
        int row = getElementRow(y);
        if (column != 0 && elements[row][column]!=ElementType.ID_KEY.ordinal()){
            int temp = elements[row][column];
            elements[row][column]=elements[row][column-1];
            elements[row][column-1]=temp;
            if(!checkAndUpdate()){
                temp = elements[row][column];
                elements[row][column]=elements[row][column-1];
                elements[row][column-1]=temp;
            };
            bitmapUpdate();
        }
    }

    public void onSwipeDown(float x, float y) {
        int column = getElementColumn(x);
        int row = getElementRow(y);
        if (row != ROWS_NUMBER - 1 && elements[row][column]!=ElementType.ID_KEY.ordinal()){
            int temp = elements[row][column];
            elements[row][column]=elements[row+1][column];
            elements[row+1][column]=temp;
            if(!checkAndUpdate()){
                temp = elements[row][column];
                elements[row][column]=elements[row+1][column];
                elements[row+1][column]=temp;
            };
            bitmapUpdate();
        }
    }

    public void onSwipeUp(float x, float y) {
        int column = getElementColumn(x);
        int row = getElementRow(y);
        if (row != 0 && elements[row][column]!=ElementType.ID_KEY.ordinal()){
            int temp = elements[row][column];
            elements[row][column]=elements[row-1][column];
            elements[row-1][column]=temp;
            if(!checkAndUpdate()){
                temp = elements[row][column];
                elements[row][column]=elements[row-1][column];
                elements[row-1][column]=temp;
            };
            bitmapUpdate();
        }
    }

    private int getElementColumn(float x) {
        return (int) ((x-posX) / SpriteSheet.ARCADE_SPRITE_WIDTH);
    }

    private int getElementRow(float y) {
        return (int) ((y-posY) / SpriteSheet.ARCADE_SPRITE_HEIGHT);
    }


    enum ElementType{
        ID_DIAMOND,
        ID_SAPPHIRE,
        ID_RUBY,
        ID_AMETHYST,
        ID_EMERALD,
        ID_KEY
    }
    private Bitmap bitmap;

    public Arcade(SpriteSheet spriteSheet, int posX, int posY) {
        this.spriteSheet = spriteSheet;
        this.posX = posX;
        this.posY = posY;
        elements = new int[ROWS_NUMBER][COLUMNS_NUMBER];
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        bitmap = Bitmap.createBitmap(COLUMNS_NUMBER*SpriteSheet.ARCADE_SPRITE_WIDTH,
                ROWS_NUMBER*SpriteSheet.ARCADE_SPRITE_HEIGHT,
                config
        );
    }

    private boolean checkAndUpdate(){
        boolean check = checkRows();
        if (check == false){
            check = checkColumns();
        }else {
            checkColumns();
        }
        updateElements();
        if (check){
            checkAndUpdate();
        }
        checkIfComplete();
        return check;
    }

    private boolean checkRows(){
        boolean ans = false;
        for (int row = 0; row < ROWS_NUMBER; row++) {
            int currElementID = elements[row][0];
            if (elements[row][0] >= ELEMENT_TYPES_NUMBER) {
                currElementID -= ELEMENT_TYPES_NUMBER;
            }
            int streak = 0;
            for (int column = 0; column < COLUMNS_NUMBER; column++) {
                if (currElementID==elements[row][column]||currElementID==elements[row][column]+ELEMENT_TYPES_NUMBER){
                    streak++;
                }
                else {
                    streak = 1;
                    if (elements[row][column] < ELEMENT_TYPES_NUMBER) {
                        currElementID = elements[row][column];
                    }
                    else {
                        currElementID = elements[row][column] - ELEMENT_TYPES_NUMBER;
                    }
                }
                if (streak == 3){
                    elements[row][column] += ELEMENT_TYPES_NUMBER;
                    if (elements[row][column-1] < ELEMENT_TYPES_NUMBER) {
                        elements[row][column-1] += ELEMENT_TYPES_NUMBER;
                    }
                    if (elements[row][column-2] < ELEMENT_TYPES_NUMBER) {
                        elements[row][column-2] += ELEMENT_TYPES_NUMBER;
                    }
                    ans = true;
                }
                if (streak>4){
                    elements[row][column] += ELEMENT_TYPES_NUMBER;
                }
            }
        }
        return ans;
    }

    private boolean checkColumns(){
        boolean ans = false;
        for (int column = 0; column < COLUMNS_NUMBER; column++) {
            int currElementID = elements[0][column];
            if (elements[0][column] >= ELEMENT_TYPES_NUMBER) {
                currElementID -= ELEMENT_TYPES_NUMBER;
            }
            int streak = 0;
            for (int row = 0; row < ROWS_NUMBER; row++) {
                if (currElementID==elements[row][column]||currElementID==elements[row][column]+ELEMENT_TYPES_NUMBER){
                    streak++;
                }
                else {
                    streak = 1;
                    if (elements[row][column] < ELEMENT_TYPES_NUMBER) {
                        currElementID = elements[row][column];
                    }
                    else {
                        currElementID = elements[row][column] - ELEMENT_TYPES_NUMBER;
                    }
                }
                if (streak == 3){
                    elements[row][column] += ELEMENT_TYPES_NUMBER;
                    if (elements[row-1][column] < ELEMENT_TYPES_NUMBER) {
                        elements[row-1][column] += ELEMENT_TYPES_NUMBER;
                    }
                    if (elements[row-2][column] < ELEMENT_TYPES_NUMBER) {
                        elements[row-2][column] += ELEMENT_TYPES_NUMBER;
                    }
                    ans = true;
                }
                if (streak>4){
                    elements[row][column] += ELEMENT_TYPES_NUMBER;
                }
            }
        }
        return ans;
    }

    public boolean isArcadeCompleted() {
        return isArcadeCompleted;
    }

    private void checkIfComplete(){
        for (int i = 0; i < COLUMNS_NUMBER; i++) {
            if (elements[ROWS_NUMBER-1][i] == ElementType.ID_KEY.ordinal()){
                isArcadeCompleted = true;
                break;
            }
        }
    }

    private void updateElements(){
        for (int i = 0; i < ROWS_NUMBER; i++) {
            for (int j = 0; j < COLUMNS_NUMBER; j++) {
                if (elements[i][j]>ElementType.values().length-1){
                    for (int k = i; k > 0; k--) {
                        elements[k][j] = elements[k-1][j];
                    }
                    elements[0][j] = (int) (Math.random()*(ElementType.values().length-1));
                }
            }
        }
    }

    public void arcadeFill(){
        for (int i = 0; i < ROWS_NUMBER; i++) {
            for (int j = 0; j < COLUMNS_NUMBER; j++) {
                elements[i][j] = (int) (Math.random()*(ElementType.values().length-1));
            }
        }
        checkAndUpdate();
        elements[0][(int) (Math.random()*COLUMNS_NUMBER)] = ElementType.ID_KEY.ordinal();
        bitmapUpdate();
    }

    public void bitmapUpdate(){
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        bitmap = Bitmap.createBitmap(COLUMNS_NUMBER*SpriteSheet.ARCADE_SPRITE_WIDTH,
                ROWS_NUMBER*SpriteSheet.ARCADE_SPRITE_HEIGHT,
                config);
        Canvas mapCanvas = new Canvas(bitmap);
        for (int row = 0; row < ROWS_NUMBER; row++) {
            for (int column = 0; column < COLUMNS_NUMBER; column++) {
                drawElement(mapCanvas, row, column);
            }
        }
    }

    private void drawElement(Canvas canvas, int row, int column) {
        Sprite sprite = spriteSheet.getSpriteByID(SpriteSheet.SpriteType.ARCADE_SPRITE.ordinal(), elements[row][column]);
        sprite.drawTile(canvas,column*SpriteSheet.ARCADE_SPRITE_WIDTH,row*SpriteSheet.ARCADE_SPRITE_HEIGHT);
    }

    public void draw(Canvas canvas){
        Rect rect = new Rect(
            0, 0, COLUMNS_NUMBER*SpriteSheet.ARCADE_SPRITE_WIDTH,
                ROWS_NUMBER*SpriteSheet.ARCADE_SPRITE_HEIGHT
        );
        RectF rectF = new RectF((float) ((posX+rect.left)*spriteSheet.scaleX),
                (float) ((posY+rect.top)*spriteSheet.scaleY),
                (float) ((posX+rect.right)*spriteSheet.scaleX),
                (float) ((posY+rect.bottom)*spriteSheet.scaleY));
        canvas.drawBitmap(bitmap, rect, rectF,null);
    }

}
