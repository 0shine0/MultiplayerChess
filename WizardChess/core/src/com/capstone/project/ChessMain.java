package com.capstone.project;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.capstone.project.states.GameStateManager;
import com.capstone.project.states.PlayChooseState;

public class ChessMain extends ApplicationAdapter {
	public static int WIDTH = 500;
	public static int HEIGHT = 800;
	public static final String TITLE = "Wizard Chess";
	
	private GameStateManager gsm;
	
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		
		Assets.load();
		
		if(Gdx.app.getType() == ApplicationType.Desktop || Gdx.app.getType() == ApplicationType.WebGL){
		    WIDTH = 800;
		    HEIGHT = 500;
		}
		
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		
		gsm.push(new PlayChooseState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
