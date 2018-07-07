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

public class AboutState extends State{
    private TextureRegion background;
    
    private TextButton backButton;
    private TextButtonStyle buttonStyle;

    public AboutState(GameStateManager gsm) {
        super(gsm);
        // TODO Auto-generated constructor stub
        
        cam = new OrthographicCamera(ChessMain.WIDTH, ChessMain.HEIGHT);
        cam.position.set(ChessMain.WIDTH/2, ChessMain.HEIGHT/2, 0);
        
        background = Assets.background1;
        
        buttonStyle = new TextButtonStyle();
        buttonStyle.font = Assets.futureFont;
        buttonStyle.downFontColor = Color.YELLOW;
        buttonStyle.fontColor = Color.BLACK;
        buttonStyle.up = new TextureRegionDrawable(Assets.buttonUp);
        buttonStyle.down = new TextureRegionDrawable(Assets.buttonDown);
        
        backButton = new TextButton("BACK", buttonStyle);
        backButton.setPosition(ChessMain.WIDTH/2-backButton.getWidth()/2, ChessMain.HEIGHT/2-backButton.getHeight()/2 - (150.0f/800.0f)*ChessMain.HEIGHT);
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
        
        // back
        if(mouse.x>=backButton.getX() && mouse.x<=(backButton.getX()+backButton.getWidth())
                && mouse.y>=backButton.getY() && mouse.y<=(backButton.getY()+backButton.getHeight())
                && Gdx.input.justTouched()){
                    gsm.pop();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO Auto-generated method stub
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        
        batch.draw(background, 0, 0, ChessMain.WIDTH, ChessMain.HEIGHT);
        backButton.draw(batch, 1);
        
        batch.end();
    }

}
