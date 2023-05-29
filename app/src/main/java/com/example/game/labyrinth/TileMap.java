package com.example.game.labyrinth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.game.R;

public class TileMap {
    private MapLayout mapLayout;
    private Tile[][] tileMap;
    private Bitmap mapBitmap;
    private Context context;
    private Player player;

    public TileMap(Context context, Player player) {
        this.context = context;
        this.player = player;
        this.mapLayout = new MapLayout();
        initTileMap();
    }

    private void initTileMap() {
        int[][] layout = mapLayout.getLayout();
        tileMap = new Tile[mapLayout.NUMBER_OF_ROWS][mapLayout.NUMBER_OF_COLUMNS];
        for (int row = 0; row < mapLayout.NUMBER_OF_ROWS; row++) {
            for (int column = 0; column < mapLayout.NUMBER_OF_COLUMNS; column++) {
                tileMap[row][column] = new Tile(context, getRectByIndex(row,column), layout[row][column]);
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
                MapLayout.TILE_SIZE*MapLayout.NUMBER_OF_COLUMNS,
                MapLayout.TILE_SIZE*MapLayout.NUMBER_OF_ROWS,
                config
        );

        Canvas mapCanvas = new Canvas(mapBitmap);
        for (int row = 0; row < mapLayout.NUMBER_OF_ROWS; row++) {
            for (int column = 0; column < mapLayout.NUMBER_OF_COLUMNS; column++) {
                tileMap[row][column].draw(mapCanvas);
            }
        }
    }

    public void changeTile(int row, int column, int newTileId){
        tileMap[row][column] = new Tile(context, getRectByIndex(row,column), newTileId);
        updateBitmap();
    }

    public void resetBitmap(){
        initTileMap();
        updateBitmap();
    }

    private void updateBitmap(){
        Canvas mapCanvas = new Canvas(mapBitmap);
        for (int row = 0; row < mapLayout.NUMBER_OF_ROWS; row++) {
            for (int column = 0; column < mapLayout.NUMBER_OF_COLUMNS; column++) {
                tileMap[row][column].draw(mapCanvas);
            }
        }
    }

    private Rect getRectByIndex(int row, int column) {
        return new Rect(
                column*MapLayout.TILE_SIZE,
                row*MapLayout.TILE_SIZE,
                (column+1)*MapLayout.TILE_SIZE,
                (row+1)*MapLayout.TILE_SIZE
        );
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.black);
        paint.setColor(color);
        canvas.drawBitmap(mapBitmap, gameDisplay.getGameRect(), gameDisplay.DISPLAY_RECT,paint);
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }
}
