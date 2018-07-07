package com.capstone.project.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Knight extends Piece{

    protected Knight() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected Knight(Vector2 position, int width, int height, TextureRegion image, boolean black) {
        super(position, width, height, image, black);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean validateMove(Vector2 destPos) {
        if(((Math.abs(destPos.x - pos.x) == 1) && (Math.abs(destPos.y - pos.y) == 2)) || 
                ((Math.abs(destPos.x - pos.x) == 2) && (Math.abs(destPos.y - pos.y) == 1))){
            destPiece = board.piecesPos.get((int)(destPos.x + (8*destPos.y)));
            if(destPiece == null){
                return true;
            }
            else if((destPiece.black && !black) || (!destPiece.black && black)){
                return true;
            }
        }
        
        return false;
    }

}
