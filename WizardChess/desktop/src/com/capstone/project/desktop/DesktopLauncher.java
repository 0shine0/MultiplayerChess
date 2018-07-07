package com.capstone.project.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TextureUnpacker;
import com.capstone.project.ChessMain;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher {
	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = false;
	private static boolean unpack = false;
	
	public static void main (String[] arg) {
		if(rebuildAtlas){
			Settings settings = new Settings();
			settings.maxWidth = 4096;
			settings.maxHeight = 4096;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "texture-raw", "texture", "allAtlas");
		}
		
		if(unpack){
			TextureUnpacker textureUnpacker = new TextureUnpacker();
			textureUnpacker.splitAtlas(new TextureAtlasData(new FileHandle("assets-raw/uiskin.atlas"),new FileHandle("assets-raw"), false), "assets-raw");
		}
		
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 900;
		config.height = 600;
		config.title = ChessMain.TITLE;
		
		new LwjglApplication(new ChessMain(), config);
	}
}
