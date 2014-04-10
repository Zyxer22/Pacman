import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class SimpleSlickGame extends BasicGame
{
	
	SpriteManager sm;
	GameBoard gb;
	Pacman pm;
	Input playerInput;
	PlayerKeyListener pkl;
	int timeBetweenMove = 0;
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		sm = new SpriteManager();
		gb = new GameBoard();
		pm = new Pacman(sm.getPacmanSprites());
		pkl = new PlayerKeyListener(pm);
		gc.getInput().addKeyListener(pkl);
		gb.addEntity(pm);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		//get player input
		timeBetweenMove += i;
		playerInput = gc.getInput();
		pm.updateCurrentAnimation();
		
		if (timeBetweenMove >= 75){
			gb.updateEntityPosition();
			timeBetweenMove = 0;
		}
		
		
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		/*
		g.setColor(new Color(50, 50, 50, 180));
		g.fillRect(0, 0, 640, 480);
		g.setColor(Color.white);
		UnicodeFont ufont;
		Font font = new Font("Serif", Font.BOLD, 15);
		ufont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
		ufont.addAsciiGlyphs();
		ufont.addGlyphs(400, 600);
		ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		ufont.loadGlyphs();
		g.setFont(ufont);
		g.drawString("test", 32, 32);
		sm.test(200, 200);
		*/
		
		//gb.renderBackground();
		g.drawImage(gb.getBG(), 0, 0);
		pm.drawCurrentAnimation();
		
		
	}

	public static void main(String[] args)
	{
		//System.setProperty("org.lwjgl.librarypath", "C:\\Users\\Joseph\\workspace\\Pacman\\slick2d");
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame("Simple Slick Game"));
			appgc.setDisplayMode(GameBoard.width, GameBoard.length, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}