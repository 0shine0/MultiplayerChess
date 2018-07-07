package com.capstone.project.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Piece {
    public Vector2 pos;
    protected int width;
    protected int height;
    protected boolean highlighted;
    protected TextureRegion img;
    protected boolean black;
    protected ChessBoard board;
    protected Piece destPiece;
    
    protected Piece(){
        pos = new Vector2();
        deselect();
        width = 0;
        height = 0;
        black = false;
    }
    
    protected Piece(Vector2 position, int width, int height, TextureRegion image, boolean black){
        pos = position;
        deselect();
        img = image;
        this.width = width;
        this.height = height;
        this.black = black;
    }
    
    abstract public boolean validateMove(Vector2 destPos);
    
    public void select(){
        highlighted = true;
    }
    
    public void deselect(){
        highlighted = false;
    }
    
    public void move(Vector2 destPos){
        pos.set(destPos);
    }
    
    public void render(SpriteBatch batch){
        if(highlighted){
            batch.draw(img, board.BOARD_X + width*pos.x, board.BOARD_Y + height*pos.y, width, height);
        }
        else{
            batch.draw(img, board.BOARD_X + width*pos.x, board.BOARD_Y + height*pos.y, width, height);
        }
    }
    
    public void setPosition(int x, int y){
        pos.set(x, y);
    }
    
    public void setColor(boolean black){
        this.black = black;
    }
    
    public void setImage(TextureRegion img){
        this.img = img;
    }
    
    public void setWidth(int width){
        this.width = width;
    }
    
    public void setHeight(int height){
        this.height = height;
    }
    
    public void setBoard(ChessBoard board){
        this.board = board;
    }
}
