package com.capstone.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Assets {
    private static AssetManager manager;
    public static TextureAtlas atlas;
    
    public static BitmapFont futureFont;
    
    public static TextureRegion buttonUp;
    public static TextureRegion buttonDown;
    
    public static TextureRegion background1;
    
    public static TextureRegion wPawn;
    public static TextureRegion wKing;
    public static TextureRegion wQueen;
    public static TextureRegion wBishop;
    public static TextureRegion wRook;
    public static TextureRegion wKnight;
    
    public static TextureRegion bPawn;
    public static TextureRegion bKing;
    public static TextureRegion bQueen;
    public static TextureRegion bBishop;
    public static TextureRegion bRook;
    public static TextureRegion bKnight;
    
    public static TextureRegion sqBrownDark;
    public static TextureRegion sqBrownLight;
    public static TextureRegion sqGreyDark;
    public static TextureRegion sqGreyLight;
    
    public static void load(){
        manager = new AssetManager();
        
        // load all spriteSheets
        manager.load("texture/allAtlas.atlas", TextureAtlas.class);
        
        // wait until all resources have finished loading
        manager.finishLoading();
        
        atlas = manager.get("texture/allAtlas.atlas", TextureAtlas.class);
        loadBackground();
        loadUI();
        loadFont();
        loadPieces();
        loadChessBoard();
    }
    
    private static void loadFont() {
        // TODO Auto-generated method stub
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/kenvector_future.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 16;
        futureFont = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
    }

    private static void loadUI() {
        // TODO Auto-generated method stub
        buttonUp = atlas.findRegion("ui/blue_button00");
        buttonDown = atlas.findRegion("ui/blue_button01");
    }

    private static void loadBackground() {
        // TODO Auto-generated method stub
        background1 = atlas.findRegion("background/skin");
    }
    
    private static void loadPieces() {
        wPawn = atlas.findRegion("chess/shadow/256px/wPawn");
        wKing = atlas.findRegion("chess/shadow/256px/wKing");
        wQueen = atlas.findRegion("chess/shadow/256px/wQueen");
        wBishop = atlas.findRegion("chess/shadow/256px/wBishop");
        wRook = atlas.findRegion("chess/shadow/256px/wRook");
        wKnight = atlas.findRegion("chess/shadow/256px/wKnight");
        
        bPawn = atlas.findRegion("chess/shadow/256px/bPawn");
        bKing = atlas.findRegion("chess/shadow/256px/bKing");
        bQueen = atlas.findRegion("chess/shadow/256px/bQueen");
        bBishop = atlas.findRegion("chess/shadow/256px/bBishop");
        bRook = atlas.findRegion("chess/shadow/256px/bRook");
        bKnight = atlas.findRegion("chess/shadow/256px/bKnight");
    }
    
    private static void loadChessBoard() {
        sqBrownDark = atlas.findRegion("chess/shadow/256px/sqBrownDark");
        sqBrownLight = atlas.findRegion("chess/shadow/256px/sqBrownLight");
        sqGreyDark = atlas.findRegion("chess/shadow/256px/sqGreyDark");
        sqGreyLight = atlas.findRegion("chess/shadow/256px/sqGreyLight");
    }
    
    public void dispose(){
        manager.dispose();
    }
}
