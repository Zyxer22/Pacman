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
import org.newdawn.slick.geom.Rectangle;

public class SimpleSlickGame extends BasicGame
{
	
	SpriteManager sm;
	GameBoard gb;
	Pacman pm;
	Input playerInput;
	PlayerKeyListener pkl;
	GhostManager gm;
	Orb largeOrb, smallOrb;
	int timeBetweenMove = 0;
	UnicodeFont ufont;
	Font font;
	
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		sm = new SpriteManager();
		gb = new GameBoard();
		pm = new Pacman(sm.getPacmanSprites());
		gm = new GhostManager();
		gm.initializeGhosts(sm);
		
		largeOrb = new Orb(sm.getOrbSprites(), 200, 230, "large");
		smallOrb = new Orb(sm.getOrbSprites(), 220, 232f, "small");
		pkl = new PlayerKeyListener(pm, gm);
		gc.getInput().addKeyListener(pkl);
		gb.addEntity(pm);
		gb.addEntity(largeOrb);
		gb.addEntity(smallOrb);
		gb.addEntity(gm.getBlueGhost());
		gb.addEntity(gm.getRedGhost());
		gb.addEntity(gm.getYellowGhost());
		gb.addEntity(gm.getPinkGhost());
		gm.getYellowLogic().start();
		
		
		Font font = new Font("Serif", Font.BOLD, 15);
		ufont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
		ufont.addAsciiGlyphs();
		ufont.addGlyphs(400, 600);
		ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		ufont.loadGlyphs();
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		//get player input
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
				gc.exit();
		else if (gc.getInput().isKeyPressed(Input.KEY_P))
		    gc.setPaused(!gc.isPaused());
		else if(!gc.isPaused()){
			timeBetweenMove += i;
			//playerInput = gc.getInput();
			pm.updateCurrentAnimation();
			
			if (timeBetweenMove >= 75){
				gb.updateEntityPosition();
				timeBetweenMove = 0;
			}
		}
		
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		
		
		
		
		//gb.renderBackground();
		  
		    //g.drawImage(gb.getBG(), 0, 0);
			gb.renderTileMap();
			pm.drawCurrentAnimation();
			gm.renderGhosts();
			largeOrb.drawCurrentAnimation();
			smallOrb.drawCurrentAnimation();
			
			//g.setColor(new Color(50, 50, 50, 180));
			//g.fillRect(0, 0, 640, 480);
			g.setColor(Color.white);
			g.setFont(ufont);
			g.drawString(Integer.toString(gb.getTileId(pm)), pm.getX(), pm.getY() + 10);
			
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