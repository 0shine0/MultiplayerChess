package com.capstone.project.states;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
    Stack<State> states;
    
    public GameStateManager(){
        states = new Stack<State>();
    }
    
    public void push(State state){
        states.push(state);
    }
    
    public void pop(){
        states.pop();
    }
    
    public void set(State state){
        states.pop();
        states.push(state);
    }
    
    public void update(float deltaTime){
        states.peek().update(deltaTime);
    }
    
    public void render(SpriteBatch batch){
        states.peek().render(batch);
    }
}
