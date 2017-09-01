package screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MagnetGame;

import Worlds.GameWorld;
import modes.GameOptions;
import modes.LastManStanding;
import utils.Constants;

public class GameScreen implements Screen{

	private MagnetGame game;
	private GameWorld world;
	private OrthographicCamera camera;
	private FitViewport view;
	private Stage stage;
	
	public GameScreen(MagnetGame game, LinkedList<Controller> controllers) {
		
		this.game = game;
		this.camera = new OrthographicCamera(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM);
		this.view = new FitViewport(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM);
		view.apply();
		
		;
		
		//Stage information
		stage = new Stage(view);
		Table table = new Table();
		table.setFillParent(true);
		Gdx.input.setInputProcessor(stage);
		
		stage.addActor(table);
		
		camera.zoom = .8f;
		camera.position.x = 40;
		camera.position.y = 40;
		
		camera.update();
		
		world = new GameWorld(new LastManStanding(game, new GameOptions()), camera, Constants.loadMap(MathUtils.random((Constants.MAP_COUNT - 1)) + 1));
		world.addPlayers(controllers);
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        world.update(delta);
        stage.act(delta);
        stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height);
		camera.combined.set(view.getCamera().combined);
		
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
		world.dispose();
		stage.dispose();
	}

}
