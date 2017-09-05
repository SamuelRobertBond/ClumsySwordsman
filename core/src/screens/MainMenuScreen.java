package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MagnetGame;

import utils.Constants;
import utils.MenuManager;

public class MainMenuScreen implements Screen{

	private MagnetGame game;
	private MenuManager menu;
	private FitViewport view;
	

	public MainMenuScreen(MagnetGame game) {
		
		this.game = game;
		view = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
		
		//Menu Creation
		menu = new MenuManager(view);
		
		//Game Title
		menu.setCellPadding(20);
		menu.addLabel("Clumsy Swordsman");
		menu.setCellPadding(5);
		menu.row();
		//Buttons
		
		//Play Button
		TextButton play = menu.addTextButton("Play");
		play.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				startGame();
			}
		});
		menu.row();
		
		//FullScreen Button
		final TextButton fullscreen = menu.addTextButton("Full");
		fullscreen.addListener(new ChangeListener(){
			

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(!Gdx.graphics.isFullscreen()){
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
					fullscreen.setText("Windowed");
				}else{
					Gdx.graphics.setWindowedMode(600, 600);
					fullscreen.setText("Full");
				}
			}
			
		});
		menu.row();
		
		//Quit Button
		TextButton quit = menu.addTextButton("Quit");
		quit.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
			
		});
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        menu.render(delta);
        
	}

	@Override
	public void resize(int width, int height) {
		menu.resize(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
	}
	
	
	private void startGame(){
		this.dispose();
		game.setScreen(new SelectScreen(game));
	}
	
}
