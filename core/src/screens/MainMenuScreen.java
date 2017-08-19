package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MagnetGame;

import utils.Constants;

public class MainMenuScreen implements Screen{

	private MagnetGame game;
	private FitViewport view;
	
	//Stage stuff
	private Stage stage;
	private Table table;
	private BitmapFont font;
	private Skin skin;
	private int menuPosition;
	private TextButton buttons[];
	private Timer timer;
	private Sound beep;
	
	boolean checkControllers;
	
	public MainMenuScreen(MagnetGame game) {
		
		this.game = game;
		this.menuPosition = 0;
		this.checkControllers = true;
		
		//Stage generation
		view = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
		stage = new Stage(view);
		table = new Table();
		table.setFillParent(true);
		
		
		//Debug
		//table.setDebug(true);
		
		Gdx.input.setInputProcessor(stage);
		
		//Font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Constants.FONT_FILE);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 64;
		font = generator.generateFont(parameter);
		generator.dispose();
		
		//Skin Generation
		skin = createBasicSkin(font);
		
		//Game Title
		Label title = new Label("Clumsy Swordsman", skin);
		
		//Buttons
		TextButton play = new TextButton("Play", skin);
		TextButton exit = new TextButton("Exit", skin);
		
		buttons = new TextButton[2];
		buttons[0] = play;
		buttons[1] = exit;
		
		
		table.add(title);
		table.row();
		table.add(play).padBottom(50).padTop(200);
		table.row();
		table.add(exit);
		
		play.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				startGame();
			}
		});
		
		stage.addActor(table);
		
		timer = new Timer();
		beep = Gdx.audio.newSound(Constants.BEEP);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        //Adjust Menu Position
        if(checkController()){
            adjustMenu();
        }

        stage.act(delta);
        stage.draw();
        
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		
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
		stage.dispose();
		font.dispose();
		beep.dispose();
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
	
	private void startGame(){
		this.dispose();
		game.setScreen(new SelectScreen(game));
	}
	
	private boolean checkController(){
		
		if(checkControllers){
			
			for(Controller controller : Controllers.getControllers()){
				
				if(controller.getButton(0)){
					
					if(menuPosition == 0){
						startGame();
					}else if(menuPosition == 1){
						Gdx.app.exit();
					}
				}
				
				
				//Checks if the button should be adujusted and changes the menu position 
				if(controller.getAxis(0) < -.5f){
					
					++menuPosition;
					beep.play();
					
					if(menuPosition > 1){
						menuPosition = 0;
					}
					
					checkControllers = false;
					timer.scheduleTask(new Task(){
						
						@Override
						public void run() {
							checkControllers = true;
						}
					}, .35f);
					
					return true;
					
				}else if(controller.getAxis(0) > .5f){
					
					--menuPosition;
					beep.play();
					
					if(menuPosition < 0){
						menuPosition = 1;
					}
					
					checkControllers = false;
					timer.scheduleTask(new Task(){
						
						@Override
						public void run() {
							checkControllers = true;
						}
					}, .35f);
					
					return true;
				}
				
			}
			
			return false;
		}
		
		return false;
		
	}

	private void adjustMenu(){
		for(int i = 0; i < buttons.length; ++i){
			if(menuPosition == i){
				buttons[i].setColor(Color.DARK_GRAY);
			}else{
				buttons[i].setColor(Color.GRAY);
			}
		}
	}
}
