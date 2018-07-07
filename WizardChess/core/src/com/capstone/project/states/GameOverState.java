package com.capstone.project.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.capstone.project.Assets;
import com.capstone.project.ChessMain;

public class GameOverState extends State{
    private boolean won;
    private String resultString;
    
    private TextureRegion background;
    
    private TextButton resultButton;
    private TextButton mainMenuButton;
    private TextButtonStyle buttonStyle;
    private TextButtonStyle textStyle;
    
    public GameOverState(GameStateManager gsm) {
        this(gsm, true);
        // TODO Auto-generated constructor stub
    }
    public GameOverState(GameStateManager gsm, boolean result) {
        super(gsm);
        // TODO Auto-generated constructor stub
        won = result;
        
        if(won){
            resultString = new String("WON");
        }
        else{
            resultString = new String("LOST");
        }
        
        cam = new OrthographicCamera(ChessMain.WIDTH, ChessMain.HEIGHT);
        cam.position.set(ChessMain.WIDTH/2, ChessMain.HEIGHT/2, 0);
        
        background = Assets.background1;
        
        textStyle = new TextButtonStyle();
        textStyle.font = Assets.futureFont;
        textStyle.downFontColor = Color.YELLOW;
        textStyle.fontColor = Color.BLACK;
        
        buttonStyle = new TextButtonStyle(textStyle);
        buttonStyle.up = new TextureRegionDrawable(Assets.buttonUp);
        buttonStyle.down = new TextureRegionDrawable(Assets.buttonDown);
        
        resultButton = new TextButton("YOU "+resultString, textStyle);
        resultButton.setPosition(ChessMain.WIDTH/2-resultButton.getWidth()/2, ChessMain.HEIGHT/2-resultButton.getHeight()/2+(100.0f/800.0f)*ChessMain.HEIGHT);
        mainMenuButton = new TextButton("MAIN MENU", buttonStyle);
        mainMenuButton.setPosition(ChessMain.WIDTH/2-mainMenuButton.getWidth()/2, ChessMain.HEIGHT/2-mainMenuButton.getHeight()/2-(100.0f/800.0f)*ChessMain.HEIGHT);
    }

    @Override
    public void handleInput() {
        // TODO Auto-generated method stub
        if(Gdx.input.isTouched()){
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
        // TODO Auto-generated method stub
        handleInput();
        
        //main menu
        if(mouse.x>=mainMenuButton.getX() && mouse.x<=(mainMenuButton.getX()+mainMenuButton.getWidth())
        && mouse.y>=mainMenuButton.getY() && mouse.y<=(mainMenuButton.getY()+mainMenuButton.getHeight())
        && Gdx.input.justTouched()){
            gsm.set(new PlayChooseState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO Auto-generated method stub
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        
        batch.draw(background, 0, 0, ChessMain.WIDTH, ChessMain.HEIGHT);
        mainMenuButton.draw(batch, 1);
        resultButton.draw(batch, 1);
        
        batch.end();
    }

}
