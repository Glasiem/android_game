package com.example.game.labyrinth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.game.GameLoop;
import com.example.game.R;

public class Player {
    private static final double PIXELS_PER_SECOND = 400;
    private static double PLAYER_START_X;
    private static double PLAYER_START_Y;
    private static final double MAX_SPEED = PIXELS_PER_SECOND/ GameLoop.MAX_UPS;
    private double playerX;
    private double playerY;
    private double velocityX = 0;
    private double velocityY = 0;
    private double playerRadius;
    private boolean hasKey = false;
    private boolean isLabyrinthCompleted = false;
    private Joystick joystick;
    private Context context;

    public Player(double playerX, double playerY, double playerRadius, Joystick joystick, Context context) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerRadius = playerRadius;
        this.joystick = joystick;
        this.context = context;
        PLAYER_START_X = playerX;
        PLAYER_START_Y = playerY;
    }


    public void update(TileMap tileMap, GameDisplay gameDisplay){
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        double playerNextX = playerX + velocityX;
        double playerNextY = playerY + velocityY;

        NextTile(tileMap, gameDisplay, playerNextX, playerNextY);
    }

    private void NextTile(TileMap tileMap, GameDisplay gameDisplay, double playerNextX, double playerNextY) {
        switch (Tile.TileType.values()
                [tileMap.getTileMap()
                [(int) ((playerNextY - gameDisplay.mapStartToPlayerStartOffsetY + Math.signum(velocityY)*playerRadius) /MapLayout.TILE_SIZE)]
                [(int) ((playerNextX - gameDisplay.mapStartToPlayerStartOffsetX + Math.signum(velocityX)*playerRadius) /MapLayout.TILE_SIZE)]
                .getTileID()]){
            case ID_PATH:
                double tempX = playerX;
                double tempY = playerY;
                if (tileMap.getTileMap()
                                [(int) ((playerY - gameDisplay.mapStartToPlayerStartOffsetY - Math.signum(velocityY)*playerRadius) /MapLayout.TILE_SIZE)]
                                [(int) ((playerNextX - gameDisplay.mapStartToPlayerStartOffsetX + Math.signum(velocityX)*playerRadius) /MapLayout.TILE_SIZE)]
                                .getTileID() == 0)
                    tempX = playerNextX;
                if (tileMap.getTileMap()
                                [(int) ((playerNextY - gameDisplay.mapStartToPlayerStartOffsetY + Math.signum(velocityY)*playerRadius) /MapLayout.TILE_SIZE)]
                                [(int) ((playerX - gameDisplay.mapStartToPlayerStartOffsetX - Math.signum(velocityX)*playerRadius) /MapLayout.TILE_SIZE)]
                                .getTileID() == 0)
                    tempY = playerNextY;
                playerX = tempX;
                playerY = tempY;
                break;
            case ID_DOOR:
                if (hasKey == true){
                    tileMap.changeTile(
                            (int) ((playerNextY - gameDisplay.mapStartToPlayerStartOffsetY + Math.signum(velocityY)*playerRadius) /MapLayout.TILE_SIZE),
                            (int) ((playerNextX - gameDisplay.mapStartToPlayerStartOffsetX + Math.signum(velocityX)*playerRadius) /MapLayout.TILE_SIZE),
                            Tile.TileType.ID_PATH.ordinal());
                    break;
                }
            case ID_WALL:
                if(playerNextX != playerX && playerNextY != playerY){
                    NextTile(tileMap, gameDisplay, playerNextX,playerY);
                }
                else if (playerNextX != playerX){
                    NextTile(tileMap, gameDisplay, playerX, playerY + velocityY);
                } else if (playerNextY != playerY && velocityX == 0) {
                    NextTile(tileMap, gameDisplay, playerX + velocityX, playerY);
                }
                break;
            case ID_SPIKE:
                playerX = PLAYER_START_X;
                playerY = PLAYER_START_Y;
                tileMap.resetBitmap();
                hasKey = false;
                break;
            case ID_KEY:
                hasKey = true;
                tileMap.changeTile(
                        (int) ((playerNextY - gameDisplay.mapStartToPlayerStartOffsetY + Math.signum(velocityY)*playerRadius) /MapLayout.TILE_SIZE),
                        (int) ((playerNextX - gameDisplay.mapStartToPlayerStartOffsetX + Math.signum(velocityX)*playerRadius) /MapLayout.TILE_SIZE),
                        Tile.TileType.ID_PATH.ordinal());
                break;
            case ID_EXIT:
                isLabyrinthCompleted = true;
        }
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay){
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.teal_700);
        paint.setColor(color);
        canvas.drawCircle((float) gameDisplay.gameToDisplayX(playerX),
                (float) gameDisplay.gameToDisplayY(playerY),
                (float) playerRadius, paint);
    }

    public double getX() {
        return playerX;
    }

    public double getY() {
        return playerY;
    }

    public boolean isLabyrinthCompleted() {
        return isLabyrinthCompleted;
    }
}
