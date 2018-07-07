package com.capstone.project.objects;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.capstone.project.Assets;
import com.capstone.project.ChessMain;
import com.github.nkzawa.socketio.client.Socket;

public class ChessBoard {
    public static int BOARD_WIDTH = 400;
    public static int BOARD_HEIGHT = 400;
    public static int BOARD_X = (ChessMain.WIDTH - BOARD_WIDTH)/2;
    public static int BOARD_Y = (ChessMain.HEIGHT - BOARD_HEIGHT)/2;
    
    public boolean moved;
    public boolean oppositeMoved;
    public Vector2 oppInitialPos;
    public Vector2 oppFinalPos;
    
    
    private boolean multiplayer;
    private Socket socket;
    
    List<Pawn> bPawn;
    King bKing;
    Queen bQueen;
    List<Bishop> bBishop;
    List<Rook> bRook;
    List<Knight> bKnight;
    
    Vector2 mouseChessCoord;
    Vector2 selectPos;
    Vector2 destPos;
    TextureRegion prevSqImg;
    boolean once;
    boolean selected;
    Piece selectedPiece;
    
    List<Piece> piecesPos;
    
    Vector3 mousePos;
    boolean touching;
    static boolean backPressed;
    
    List<Pawn> wPawn;
    King wKing;
    Queen wQueen;
    List<Bishop> wBishop;
    List<Rook> wRook;
    List<Knight> wKnight;
    
    boolean black;
    int boxWidth;
    int boxHeight;
    
    List<TextureRegion> squares;
    
    public ChessBoard(boolean black, boolean multiplayer){
        this.black = black;
        
        boxWidth = BOARD_WIDTH/8;
        boxHeight = BOARD_HEIGHT/8;
        
        this.multiplayer = multiplayer;
        
        mousePos = new Vector3();
        mouseChessCoord = new Vector2(10, 10);
        once = false;
        selected = false;
        selectPos = new Vector2(-1, -1);
        destPos = new Vector2(-1, -1);
        selectedPiece = null;
        prevSqImg = null;
        
        moved = black;
        oppositeMoved = !black;
        oppInitialPos = new Vector2(-1, -1);
        oppFinalPos = new Vector2(-1, -1);
        
        squares = new ArrayList<TextureRegion>();
        piecesPos = new ArrayList<Piece>();
        
        bPawn = new ArrayList<Pawn>();
        bKing = new King();
        bQueen = new Queen();
        bBishop = new ArrayList<Bishop>();
        bRook = new ArrayList<Rook>();
        bKnight = new ArrayList<Knight>();
        
        wPawn = new ArrayList<Pawn>();
        wKing = new King();
        wQueen = new Queen();
        wBishop = new ArrayList<Bishop>();
        wRook = new ArrayList<Rook>();
        wKnight = new ArrayList<Knight>();
        
        for(int i = 0; i < 64; i++){
            piecesPos.add(null);
        }
        
        for(int i = 0; i < 8; i++){
            bPawn.add(new Pawn());
            wPawn.add(new Pawn());
        }
        
        for(int i = 0; i < 2; i++){
            bBishop.add(new Bishop());
            bRook.add(new Rook());
            bKnight.add(new Knight());
            
            wBishop.add(new Bishop());
            wRook.add(new Rook());
            wKnight.add(new Knight());
        }
        
        int j = 0;
        for(int i = 0; i < 32; i++){
            if(j % 2 == 0){
                squares.add(new TextureRegion(Assets.sqBrownDark));
                squares.add(new TextureRegion(Assets.sqBrownLight));
            }
            else{
                squares.add(new TextureRegion(Assets.sqBrownLight));
                squares.add(new TextureRegion(Assets.sqBrownDark));
            }
            

            if(i % 4 == 3){
                j++;
            }
        }
        
        //black on player side
        int iter = 0;
        for(Pawn pawn : bPawn){
            pawn.setColor(true);
            pawn.setWidth(boxWidth);
            pawn.setHeight(boxHeight);
            pawn.setImage(new TextureRegion(Assets.bPawn));
            pawn.setPosition(iter++, 1);
            pawn.setBoard(this);
            piecesPos.set((int) (pawn.pos.x + 8*pawn.pos.y), pawn);
        }
        
        bKing.setColor(true);
        bKing.setWidth(boxWidth);
        bKing.setHeight(boxHeight);
        bKing.setImage(new TextureRegion(Assets.bKing));
        bKing.setPosition(3, 0);
        bKing.setBoard(this);
        piecesPos.set((int) (bKing.pos.x + 8*bKing.pos.y), bKing);
        
        bQueen.setColor(true);
        bQueen.setWidth(boxWidth);
        bQueen.setHeight(boxHeight);
        bQueen.setImage(new TextureRegion(Assets.bQueen));
        bQueen.setPosition(4, 0);
        bQueen.setBoard(this);
        piecesPos.set((int) (bQueen.pos.x + 8*bQueen.pos.y), bQueen);
        
        iter = 2;
        for(Bishop bishop : bBishop){
            bishop.setColor(true);
            bishop.setWidth(boxWidth);
            bishop.setHeight(boxHeight);
            bishop.setImage(new TextureRegion(Assets.bBishop));
            bishop.setPosition(iter, 0);
            bishop.setBoard(this);
            iter += 3;
            piecesPos.set((int) (bishop.pos.x + 8*bishop.pos.y), bishop);
        }
        
        iter = 0;
        for(Rook rook : bRook){
            rook.setColor(true);
            rook.setWidth(boxWidth);
            rook.setHeight(boxHeight);
            rook.setImage(new TextureRegion(Assets.bRook));
            rook.setPosition(iter, 0);
            rook.setBoard(this);
            iter += 7;
            piecesPos.set((int) (rook.pos.x + 8*rook.pos.y), rook);
        }
        
        iter = 1;
        for(Knight knight : bKnight){
            knight.setColor(true);
            knight.setWidth(boxWidth);
            knight.setHeight(boxHeight);
            knight.setImage(new TextureRegion(Assets.bKnight));
            knight.setPosition(iter, 0);
            knight.setBoard(this);
            iter += 5;
            piecesPos.set((int) (knight.pos.x + 8*knight.pos.y), knight);
        }
        
        //white on opposite side
        iter = 0;
        for(Pawn pawn : wPawn){
            pawn.setColor(false);
            pawn.setWidth(boxWidth);
            pawn.setHeight(boxHeight);
            pawn.setImage(new TextureRegion(Assets.wPawn));
            pawn.setPosition(iter++, 6);
            pawn.setBoard(this);
            piecesPos.set((int) (pawn.pos.x + 8*pawn.pos.y), pawn);
        }
        
        wKing.setColor(false);
        wKing.setWidth(boxWidth);
        wKing.setHeight(boxHeight);
        wKing.setImage(new TextureRegion(Assets.wKing));
        wKing.setPosition(3, 7);
        wKing.setBoard(this);
        piecesPos.set((int) (wKing.pos.x + 8*wKing.pos.y), wKing);
        
        wQueen.setColor(false);
        wQueen.setWidth(boxWidth);
        wQueen.setHeight(boxHeight);
        wQueen.setImage(new TextureRegion(Assets.wQueen));
        wQueen.setPosition(4, 7);
        wQueen.setBoard(this);
        piecesPos.set((int) (wQueen.pos.x + 8*wQueen.pos.y), wQueen);
        
        iter = 2;
        for(Bishop bishop : wBishop){
            bishop.setColor(false);
            bishop.setWidth(boxWidth);
            bishop.setHeight(boxHeight);
            bishop.setImage(new TextureRegion(Assets.wBishop));
            bishop.setPosition(iter, 7);
            bishop.setBoard(this);
            iter += 3;
            piecesPos.set((int) (bishop.pos.x + 8*bishop.pos.y), bishop);
        }
        
        iter = 0;
        for(Rook rook : wRook){
            rook.setColor(false);
            rook.setWidth(boxWidth);
            rook.setHeight(boxHeight);
            rook.setImage(new TextureRegion(Assets.wRook));
            rook.setPosition(iter, 7);
            rook.setBoard(this);
            iter += 7;
            piecesPos.set((int) (rook.pos.x + 8*rook.pos.y), rook);
        }
        
        iter = 1;
        for(Knight knight : wKnight){
            knight.setColor(false);
            knight.setWidth(boxWidth);
            knight.setHeight(boxHeight);
            knight.setImage(new TextureRegion(Assets.wKnight));
            knight.setPosition(iter, 7);
            knight.setBoard(this);
            iter += 5;
            piecesPos.set((int) (knight.pos.x + 8*knight.pos.y), knight);
        }
    }
    
    public void update(float deltaTime){
        if(touching){
            if(mousePos.x >= BOARD_X && mousePos.y >= BOARD_Y && mousePos.x < (BOARD_X + BOARD_WIDTH) 
                    && mousePos.y < (BOARD_Y + BOARD_HEIGHT)){
                mousePos.x -= BOARD_X;
                mousePos.y -= BOARD_Y;
                
                if(mouseChessCoord.x < 0 && mouseChessCoord.y < 0){
                    mouseChessCoord.x = (int)(mousePos.x/boxWidth);
                    mouseChessCoord.y = (int)(mousePos.y/boxHeight);
                    
                    if(!selected){
                        selectedPiece = piecesPos.get((int)(mouseChessCoord.x + (mouseChessCoord.y*8)));
                        prevSqImg = squares.get((int) (mouseChessCoord.x + (mouseChessCoord.y*8)));
                        squares.set((int)(mouseChessCoord.x + (mouseChessCoord.y*8)), Assets.sqGreyLight);
                        selectPos.set(mouseChessCoord.x, mouseChessCoord.y);
                        once = false;
                        if(selectedPiece != null){
                            selected = true;
                        }
                    }
                    else{
                        destPos.set(mouseChessCoord.x, mouseChessCoord.y);
                        if(selectedPiece.validateMove(destPos)){
                            piecesPos.set((int)(destPos.x + (8*destPos.y)), selectedPiece);
                            piecesPos.set((int)(selectPos.x + (8*selectPos.y)), null);
                            selectedPiece.move(destPos);
                            moved = true;
                            oppositeMoved = false;
                            if(multiplayer){
                                sendDataToServer(selectPos, destPos);
                            }
                        }
                        
                        squares.set((int)(selectPos.x + (8*selectPos.y)), prevSqImg);
                        selected = false;
                        selectedPiece = null;
                        selectPos.set(-1, -1);
                        destPos.set(-1, -1);
                        prevSqImg = null;
                        once = false;
                    }
                }
            }
        }
        else{
            if(!once){
                mouseChessCoord.set(-1, -1);
                once = true;
                if(selectedPiece == null && prevSqImg != null){
                    squares.set((int)(selectPos.x + (8*selectPos.y)), prevSqImg);
                    selectPos.set(-1, -1);
                    prevSqImg = null;
                }
                else if(prevSqImg != null && ((selectedPiece.black && !black) || (!selectedPiece.black && black))){
                    squares.set((int)(selectPos.x + (8*selectPos.y)), prevSqImg);
                    selectPos.set(-1, -1);
                    prevSqImg = null;
                    selectedPiece = null;
                    selected = false;
                }
                else if(moved && prevSqImg != null){
                    squares.set((int)(selectPos.x + (8*selectPos.y)), prevSqImg);
                    selectPos.set(-1, -1);
                    prevSqImg = null;
                    selectedPiece = null;
                    selected = false;
                }
            }
        }
        
        if(moved && oppositeMoved){
            Piece oppPiece = piecesPos.get((int)(oppInitialPos.x + (8*oppInitialPos.y)));
            piecesPos.set((int)(oppFinalPos.x + (8*oppFinalPos.y)), oppPiece);
            piecesPos.set((int)(oppInitialPos.x + (8*oppInitialPos.y)), null);
            oppPiece.move(oppFinalPos);
            
            moved = false;
        }
    }
    
    public void render(SpriteBatch batch){
      //board
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){                
                batch.draw(squares.get(i + j*8), BOARD_X + i*boxWidth, BOARD_Y + j*boxHeight, boxWidth, boxHeight);
            }
        }
        for(Piece piece : piecesPos){
            if(piece != null){
                piece.render(batch);
            }
        }
    }
    
    public void setMouseAttribs(Vector3 mousePos, boolean touching, boolean backPressed){
        this.mousePos.set(mousePos);
        this.touching = touching;
        this.backPressed = backPressed;
    }
    
    public void sendDataToServer(Vector2 initialPos, Vector2 finalPos){
        JSONObject data = new JSONObject();
        try{
            data.put("initialPosX", initialPos.x);
            data.put("initialPosY", initialPos.y);
            data.put("finalPosX", finalPos.x);
            data.put("finalPosY", finalPos.y);
            socket.emit("moved", data);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
    
    public void setSocket(Socket socket){
        this.socket = socket;
    }

}
