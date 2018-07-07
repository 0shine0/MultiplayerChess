package com.capstone.project.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Pawn extends Piece{
    protected Pawn(){
        super();
    }

    protected Pawn(Vector2 position, int width, int height, TextureRegion image, boolean black) {
        super(position, width, height, image, black);
    }

    @Override
    public boolean validateMove(Vector2 destPos) {
        if(black){
            if(destPos.y - pos.y == 1){
                destPiece = board.piecesPos.get((int)(destPos.x + (8*destPos.y)));
                if(pos.x == destPos.x){
                    if(destPiece == null){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else if(Math.abs(destPos.x - pos.x) == 1){
                    if(destPiece == null || destPiece.black){
                        return false;
                    }
                    else{
                        return true;
                    }
                }
            }
            if(destPos.y - pos.y == 2){
                if(pos.y != 1){
                    return false;
                }
                destPiece = board.piecesPos.get((int)(destPos.x + (8*destPos.y)));
                if(pos.x == destPos.x){
                    if(board.piecesPos.get((int)(pos.x + (8*(pos.y + 1)))) != null){
                        return false;
                    }
                    else if(destPiece == null){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        else{
            if(pos.y - destPos.y == 1){
                Piece destPiece = board.piecesPos.get((int)(destPos.x + (8*destPos.y)));
                if(pos.x == destPos.x){
                    if(destPiece == null){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else if(Math.abs(destPos.x - pos.x) == 1){
                    if(destPiece == null || !destPiece.black){
                        return false;
                    }
                    else{
                        return true;
                    }
                }
            }
            if(pos.y - destPos.y == 2){
                if(pos.y != 6){
                    return false;
                }
                destPiece = board.piecesPos.get((int)(destPos.x + (8*destPos.y)));
                if(pos.x == destPos.x){
                    if(board.piecesPos.get((int)(pos.x + (8*(pos.y - 1)))) != null){
                        return false;
                    }
                    else if(destPiece == null){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        
        return false;
    }

}
