package com.capstone.project.states;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.capstone.project.Assets;
import com.capstone.project.ChessMain;

public class PlayChooseState extends State{
    private TextureRegion background;
    
    private TextButton multiButton;
    private TextButton singleButton;
    private TextButton rulesButton;
    private TextButton aboutButton;
    private TextButton exitButton;
    private TextButtonStyle buttonStyle;
    
    private boolean multiplayer;

    public PlayChooseState(GameStateManager gsm) {
        super(gsm);
        // TODO Auto-generated constructor stub
        cam = new OrthographicCamera(ChessMain.WIDTH, ChessMain.HEIGHT);
        cam.position.set(ChessMain.WIDTH/2, ChessMain.HEIGHT/2, 0);
        
        background = Assets.background1;
        
        multiplayer = false;
        
        buttonStyle = new TextButtonStyle();
        buttonStyle.font = Assets.futureFont;
        buttonStyle.downFontColor = Color.YELLOW;
        buttonStyle.fontColor = Color.BLACK;
        buttonStyle.up = new TextureRegionDrawable(Assets.buttonUp);
        buttonStyle.down = new TextureRegionDrawable(Assets.buttonDown);
        
        multiButton = new TextButton("MULTIPLAYER", buttonStyle);
        multiButton.setPosition(ChessMain.WIDTH/2-multiButton.getWidth()/2, ChessMain.HEIGHT/2-multiButton.getHeight()/2 + (50.0f/800.0f)*ChessMain.HEIGHT);
        singleButton = new TextButton("SINGLEPLAYER", buttonStyle);
        singleButton.setPosition(ChessMain.WIDTH/2-singleButton.getWidth()/2, ChessMain.HEIGHT/2-singleButton.getHeight()/2 + (150.0f/800.0f)*ChessMain.HEIGHT);
        rulesButton = new TextButton("RULES", buttonStyle);
        rulesButton.setPosition(ChessMain.WIDTH/2-rulesButton.getWidth()/2, ChessMain.HEIGHT/2-rulesButton.getHeight()/2 - (50.0f/800.0f)*ChessMain.HEIGHT);
        aboutButton = new TextButton("ABOUT", buttonStyle);
        aboutButton.setPosition(ChessMain.WIDTH/2-aboutButton.getWidth()/2, ChessMain.HEIGHT/2-aboutButton.getHeight()/2 - (150.0f/800.0f)*ChessMain.HEIGHT);
        exitButton = new TextButton("EXIT", buttonStyle);
        exitButton.setPosition(ChessMain.WIDTH/2-exitButton.getWidth()/2, ChessMain.HEIGHT/2-exitButton.getHeight()/2 - (250.0f/800.0f)*ChessMain.HEIGHT);
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
    }

    @Override
    public void update(float deltaTime) {
        // TODO Auto-generated method stub
        handleInput();
        
        //single player
        if(mouse.x>=singleButton.getX() && mouse.x<=(singleButton.getX()+singleButton.getWidth())
        && mouse.y>=singleButton.getY() && mouse.y<=(singleButton.getY()+singleButton.getHeight())
        && Gdx.input.justTouched()){
            gsm.push(new PlayState(gsm, multiplayer));
        }
        
        //multi player
        if(mouse.x>=multiButton.getX() && mouse.x<=(multiButton.getX()+multiButton.getWidth())
        && mouse.y>=multiButton.getY() && mouse.y<=(multiButton.getY()+multiButton.getHeight())
        && Gdx.input.justTouched()){
            multiplayer = true;
            gsm.push(new PlayState(gsm, multiplayer));
        }
        
        //rules
        if(mouse.x>=rulesButton.getX() && mouse.x<=(rulesButton.getX()+rulesButton.getWidth())
                && mouse.y>=rulesButton.getY() && mouse.y<=(rulesButton.getY()+rulesButton.getHeight())
                && Gdx.input.justTouched()){
                    gsm.push(new RulesState(gsm));
        }
        
        //about
        if(mouse.x>=aboutButton.getX() && mouse.x<=(aboutButton.getX()+aboutButton.getWidth())
                && mouse.y>=aboutButton.getY() && mouse.y<=(aboutButton.getY()+aboutButton.getHeight())
                && Gdx.input.justTouched()){
                    gsm.push(new AboutState(gsm));
        }
        
        //exit
        if(mouse.x>=exitButton.getX() && mouse.x<=(exitButton.getX()+exitButton.getWidth())
                && mouse.y>=exitButton.getY() && mouse.y<=(exitButton.getY()+exitButton.getHeight())
                && Gdx.input.justTouched()){
                    Gdx.app.exit();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO Auto-generated method stub
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        
        batch.draw(background, 0, 0, ChessMain.WIDTH, ChessMain.HEIGHT);
        multiButton.draw(batch, 1);
        singleButton.draw(batch, 1);
        rulesButton.draw(batch, 1);
        aboutButton.draw(batch, 1);
        exitButton.draw(batch, 1);
        
        batch.end();
    }
}
