package com.capstone.project.states;

import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.capstone.project.Assets;
import com.capstone.project.ChessMain;
import com.capstone.project.objects.ChessBoard;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class PlayState extends State{
    private TextureRegion background;
    private ChessBoard board;
    
    private boolean gameOver;
    private boolean won;
    
    private boolean multiplayer;
    
    private String id;
    private static String serverUrl;
    private Socket socket;
    private boolean connected;
    
    private boolean black;

    public PlayState(GameStateManager gsm) {
        this(gsm, false);
    }
    
    public PlayState(GameStateManager gsm, boolean multiplayer) {
        super(gsm);
        
        cam = new OrthographicCamera(ChessMain.WIDTH, ChessMain.HEIGHT);
        cam.position.set(ChessMain.WIDTH/2, ChessMain.HEIGHT/2, 0);
        
        gameOver = false;
        won = false;
        
        this.multiplayer = multiplayer;
        connected = false;
        
        background = Assets.background1;
        
        if(!multiplayer){
            black = true;
            connected = true;
        }
        else{
            serverUrl = "localhost";
            connectSocket();
            configSocketEvents();
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isButtonPressed(Buttons.LEFT)){
            touching = true;
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);
        }
        else{
            touching = false;
        }
        
        if(Gdx.input.isKeyPressed(Keys.BACK)){
            State.backPressed = true;
            gsm.pop();
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        
        if(connected){
            board = new ChessBoard(black, multiplayer);
            if(multiplayer){
                board.setSocket(socket);
            }
            
            connected = false;
        }
        
        if(board != null){
            board.setMouseAttribs(mouse, touching, backPressed);
            
            board.update(deltaTime);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        
        batch.draw(background, 0, 0, ChessMain.WIDTH, ChessMain.HEIGHT);
        if(board != null){
            board.render(batch);
        }
        
        batch.end();
    }
    
    public void connectSocket(){
        try{
            socket = IO.socket("http://"+serverUrl+":8081");
            socket.connect();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                Gdx.app.log("Server", "connected");
            }
        }).on("socketID", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    id = data.getString("id");
                    Gdx.app.log("My ID", id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).on("startGameB", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                black = true;
                connected = true;
            };
        }).on("startGameW", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                black = false;
                connected = true;
            };
        }).on("moved", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    board.oppInitialPos.x = (float)data.getInt("initialPosX");
                    board.oppInitialPos.y = (float)data.getInt("initialPosY");
                    board.oppFinalPos.x = (float)data.getInt("finalPosX");
                    board.oppFinalPos.y = (float)data.getInt("finalPosY");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                board.oppositeMoved = true;
            }
        }).on("win", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                gameOver = true;
                won = true;
            }
        }).on("lose", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                gameOver = true;
                won = false;
            }
        });
    }

}
