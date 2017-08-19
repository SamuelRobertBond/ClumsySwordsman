package screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MagnetGame;

import Entities.Player;
import Worlds.GameWorld;
import modes.GameMode;
import modes.GameOptions;
import modes.SelectMode;
import utils.Constants;

public class SelectScreen implements Screen{
	
	private MagnetGame game;
	
	//Stage stuff
	
	private Stage stage;
	private Table table;
	private Skin skin;
	private BitmapFont font;
	private Label text;
	
	//Selection Controller Stuff
	private LinkedList<Controller> controllers;
	private OrthographicCamera camera;
	private GameWorld world;
	private LinkedList<Player> players;
	private FitViewport view;
	
	//text
	private final String joinText = "Press A to Join";
	private final String startText = "Press START to Begin";
	
	private final Sound sound;
	
	
	public SelectScreen(MagnetGame game) {
		
		this.game = game;
		this.camera = new OrthographicCamera(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM);
		this.view = new FitViewport(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM);
		view.apply();
		
		camera.zoom = .8f;
		camera.position.x = 40;
		camera.position.y = 40;
		
		camera.update();
		
		world = new GameWorld(new SelectMode(game, new GameOptions()), camera, Constants.SELECTION_MAP);
		players = new LinkedList<Player>();
		controllers = new LinkedList<Controller>();
		
		sound = Gdx.audio.newSound(Constants.BEEP_JOIN);
		
		
		//Font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Constants.FONT_FILE);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 64;
		parameter.color = Color.BLACK;
		
		font = generator.generateFont(parameter);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		generator.dispose();
		
		
		//Stage
		FitViewport view = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
		stage = new Stage(view);
		table = new Table();
		table.setFillParent(true);
		Gdx.input.setInputProcessor(stage);
		
		//Skin Generation
		skin = createBasicSkin(font);
		text = new Label(joinText, skin);
		
		table.add(text);
		stage.addActor(table);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        world.update(delta);
        checkControllers();
        startGame();
        
        if(controllers.size() < 2){
        	text.setText(joinText);
        }else{
        	text.setText(startText);
        }
        
        stage.act(delta);
        stage.draw();
        
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		view.update(width, height);
		camera.combined.set(view.getCamera().combined);
        camera.update();
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
		font.dispose();
		sound.dispose();
	}
	
	
	private void startGame(){
		for(Controller controller : controllers){
			if(controller.getButton(7) && controllers.size() > 1){
				this.dispose();
				game.setScreen(new GameScreen(game, controllers));
			}
		}
	}
	
	private void checkControllers(){
		for(Controller controller : Controllers.getControllers()){
			if(controller.getButton(0) && !controllers.contains(controller) && controllers.size() < 4){
				Gdx.app.log("Select Screen", "Player Added");
				controllers.add(controller);
				Player player = world.addPlayer(controller, controllers.size() - 1);
				player.removeScore();
				sound.play();
			}
		}
		
	}

	private Skin createBasicSkin(BitmapFont font){
		Skin skin = new Skin();
		skin.add("default", font);
		
		Pixmap pixmap = new Pixmap((int)Constants.V_WIDTH/4, (int)Constants.V_HEIGHT/10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background", new Texture(pixmap));
		
		TextButton.TextButtonStyle textStyle = new TextButton.TextButtonStyle();
		textStyle.up = skin.newDrawable("background", Color.GRAY);
		textStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
		textStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
		textStyle.font = skin.getFont("default");
		skin.add("default", textStyle);
		
		Label.LabelStyle labelStyle = new LabelStyle(font, Color.BLACK);
		skin.add("default", labelStyle);
		
		return skin;
	}
	
}
