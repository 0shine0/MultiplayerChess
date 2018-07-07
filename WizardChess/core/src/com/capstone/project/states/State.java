package com.capstone.project.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam;
    public Vector3 mouse;
    public boolean touching;
    public static boolean backPressed;
    
    protected GameStateManager gsm;
    
    public State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }
    
    public abstract void handleInput();
    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch batch);
}
