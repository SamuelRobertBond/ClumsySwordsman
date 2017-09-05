package utils;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuManager {

	private Stage stage;
	private Table table;
	private Skin skin;
	private BitmapFont font;
	
	private Sound beep;
	
	private LinkedList<TextButton> buttons;
	
	private boolean fillCell;
	private boolean checkPosition;
	private boolean released;
	
	private float cellWidth;
	private float cellHeight;
	private float padding;
	
	private int fontSize = 64;
	
	private Timer timer;
	private int position = 0;
	
	public MenuManager(Viewport view) {
		
		stage = new Stage(view);
		Gdx.input.setInputProcessor(stage);
		skin = createBasicSkin();
		
		buttons = new LinkedList<TextButton>();
		
		fillCell = true;
		
		cellWidth = 5;
		cellHeight = 5;
		
		padding = 5;
		
		table = new Table();
		
		table.setFillParent(true);
		stage.addActor(table);
		
		timer = new Timer();
		checkPosition = true;
		beep = Gdx.audio.newSound(Constants.BEEP);
		
		released = true;
		
	}
	
	private void handleControllerInput(){
		
		
		if(buttons.size() > 0){
					
			for(int i = 0; i < Controllers.getControllers().size; ++i){
				
				Controller controller = Controllers.getControllers().get(i);
				
				//Check if a button 
				if(controller.getButton(0) && released){
					released = false;
					if(buttons.get(position).getListeners().size > 0){
						buttons.get(position).toggle();
					}
				}else if(!controller.getButton(0)){
					released = true;
				}
				
				if(checkPosition){
					
					float axis = controller.getAxis(1);
					
					if(Math.abs(axis) > .5f){
						
						beep.play();
						checkPosition = false;
						
						if(axis > .5f){
							--position;
						}else{
							++position;
						}
						
						timer.scheduleTask(new Task(){
							
							@Override
							public void run() {
								checkPosition = true;
							}
						}, .35f);
						
						if(position > (buttons.size() - 1)){
							position = 0;
						}else if(position < 0){
							position = buttons.size() - 1;
						}
						
						for(TextButton b : buttons){
							b.setColor(Color.GRAY);
						}
						
						buttons.get(position).setColor(Color.DARK_GRAY);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	public void render(float delta){
		handleControllerInput();
		stage.act();
		stage.draw();
	}
	
	public void setFillCell(boolean enable){
		fillCell = enable;
	}
	
	public void setFontSize(int size){
		fontSize = size;
		skin = createBasicSkin();
	}
	
	public void setCellSize(float width, float height){
		cellWidth = width;
		cellHeight = height;
	}
	
	public void setCellPadding(float padding){
		this.padding = padding;
	}
	
	public void dispose(){
		skin.dispose();
		stage.dispose();
		font.dispose();
		beep.dispose();
	}
	
	public TextButton addTextButton(String text){
		
		TextButton button = new TextButton(text, skin);
		
		if(fillCell){
			table.add(button).pad(padding).getActor();
		}else{
			table.add(button).width(cellWidth).height(cellHeight).pad(padding).getActor();
		}
		
		if(buttons.size() == 0){
			button.setColor(Color.DARK_GRAY);
		}
		
		buttons.add(button);
		
		return button;
	}
	
	public Label addLabel(String text){
		Label label = new Label(text, skin);
		table.add(label).pad(padding);
		return label;
	}
	
	public void row(){
		table.row();
	}
	
	private Skin createBasicSkin(){
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Constants.FONT_FILE);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 64;
		font = generator.generateFont(parameter);
		generator.dispose();
		
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

	
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
		
	}
	
}

