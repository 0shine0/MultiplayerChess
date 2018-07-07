package com.capstone.project.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Rook extends Piece{
    Vector2 subVector;
    
    protected Rook(){
        super();
    }

    protected Rook(Vector2 position, int width, int height, TextureRegion image, boolean black) {
        super(position, width, height, image, black);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validateMove(Vector2 destPos) {
        subVector = new Vector2(destPos);
        subVector.sub(pos);
        
        if(destPos.x == pos.x){
            destPiece = board.piecesPos.get((int)(destPos.x + (8*destPos.y)));
            
            for(int i = 1; i < (Math.abs(subVector.y)); i++){
                if(subVector.y > 0){
                    if(board.piecesPos.get((int)(pos.x + (8*(pos.y + i)))) != null){
                        return false;
                    }
                }
                else if(subVector.y < 0){
                    if(board.piecesPos.get((int)(pos.x + (8*(pos.y - i)))) != null){
                        return false;
                    }
                }
            }
            
            if(destPiece == null){
                return true;
            }
            else{
                if((destPiece.black && !black) || (!destPiece.black && black)){
                    return true;
                }
            }
        }
        else if(destPos.y == pos.y){
            destPiece = board.piecesPos.get((int)(destPos.x + (8*destPos.y)));
            
            for(int i = 1; i < (Math.abs(subVector.x)); i++){
                if(subVector.x > 0){
                    if(board.piecesPos.get((int)((pos.x + i) + (8*pos.y))) != null){
                        return false;
                    }
                }
                else if(subVector.x < 0){
                    if(board.piecesPos.get((int)((pos.x - i) + (8*pos.y))) != null){
                        return false;
                    }
                }
            }
            
            if(destPiece == null){
                return true;
            }
            else{
                if((destPiece.black && !black) || (!destPiece.black && black)){
                    return true;
                }
            }
        }
        
        return false;
    }
}
