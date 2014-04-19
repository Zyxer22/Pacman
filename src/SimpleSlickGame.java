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
	Orb largeOrb1, largeOrb2,largeOrb3,largeOrb4;
	Orb smallOrb1,smallOrb2,smallOrb3,smallOrb4,smallOrb5,smallOrb6,smallOrb7,smallOrb8,smallOrb9,smallOrb10,smallOrb11,smallOrb12,
	smallOrb13,smallOrb14,smallOrb15,smallOrb16,smallOrb17,smallOrb18,smallOrb19,smallOrb20,smallOrb21,smallOrb22,
	smallOrb23,smallOrb24,smallOrb25,smallOrb26,smallOrb27,smallOrb28,smallOrb29,smallOrb30,smallOrb31,smallOrb32,
	smallOrb33,smallOrb34,smallOrb35,smallOrb36,smallOrb37,smallOrb38,smallOrb39,smallOrb40,smallOrb41,smallOrb42,
	smallOrb43,smallOrb44,smallOrb45,smallOrb46,smallOrb47,smallOrb48,smallOrb49,smallOrb50,smallOrb51,smallOrb52,
	smallOrb53,smallOrb54,smallOrb55,smallOrb56,smallOrb57,smallOrb58,smallOrb59,smallOrb60,smallOrb61,smallOrb62,
	smallOrb63,smallOrb64,smallOrb65,smallOrb66,smallOrb67,smallOrb68,smallOrb69,smallOrb70,smallOrb71,smallOrb72,
	smallOrb73,smallOrb74,smallOrb75,smallOrb76,smallOrb77,smallOrb78,smallOrb79,smallOrb80,smallOrb81,smallOrb82,
	smallOrb83,smallOrb84,smallOrb85,smallOrb86,smallOrb87,smallOrb88,smallOrb89,smallOrb90,smallOrb91,smallOrb92,
	smallOrb93,smallOrb94,smallOrb95,smallOrb96,smallOrb97,smallOrb98,smallOrb99,smallOrb100,smallOrb101,smallOrb102,
	smallOrb103,smallOrb104,smallOrb105,smallOrb106,smallOrb107,smallOrb108,smallOrb109,smallOrb110,smallOrb111,smallOrb112,
	smallOrb113,smallOrb114,smallOrb115,smallOrb116,smallOrb117,smallOrb118,smallOrb119,smallOrb120,smallOrb121,smallOrb122,
	smallOrb123,smallOrb124,smallOrb125,smallOrb126,smallOrb127,smallOrb128,smallOrb129,smallOrb130,smallOrb131,smallOrb132,
	smallOrb133,smallOrb134,smallOrb135,smallOrb136,smallOrb137,smallOrb138,smallOrb139,smallOrb140,smallOrb141,smallOrb142,
	smallOrb143,smallOrb144,smallOrb145,smallOrb146,smallOrb147,smallOrb148,smallOrb149,smallOrb150,smallOrb151,smallOrb152,
	smallOrb153,smallOrb154,smallOrb155,smallOrb156,smallOrb157,smallOrb158,smallOrb159,smallOrb160,smallOrb161,smallOrb162,
	smallOrb163,smallOrb164,smallOrb165,smallOrb166,smallOrb167,smallOrb168,smallOrb169,smallOrb170,smallOrb171,smallOrb172,
	smallOrb173,smallOrb174,smallOrb175,smallOrb176,smallOrb177,smallOrb178,smallOrb179,smallOrb180,smallOrb181,smallOrb182,
	smallOrb183,smallOrb184,smallOrb185,smallOrb186,smallOrb187,smallOrb188,smallOrb189,smallOrb190,smallOrb191,smallOrb192,
	smallOrb193,smallOrb194,smallOrb195,smallOrb196,smallOrb197,smallOrb198,smallOrb199,smallOrb200,smallOrb201,smallOrb202,
	smallOrb203,smallOrb204,smallOrb205,smallOrb206,smallOrb207,smallOrb208,smallOrb209,smallOrb210,smallOrb211,smallOrb212,
	smallOrb213,smallOrb214,smallOrb215,smallOrb216,smallOrb217,smallOrb218,smallOrb219,smallOrb220,smallOrb221,smallOrb222,
	smallOrb223,smallOrb224,smallOrb225,smallOrb226,smallOrb227,smallOrb228,smallOrb229,smallOrb230,smallOrb231,smallOrb232,
	smallOrb233,smallOrb234,smallOrb235,smallOrb236,smallOrb237,smallOrb238,smallOrb239,smallOrb240,smallOrb241,smallOrb242;
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
		
		pkl = new PlayerKeyListener(pm, gm);
		gc.getInput().addKeyListener(pkl);
		
		//entities must be added in this order to maintain collision detection later
		//this is a result of them being placed in specific places in the array
		//which allows us to increase performance by only checking relevant spaces for collision
		gb.addEntity(pm);
		gb.addEntity(GhostManager.getBlueGhost());
		gb.addEntity(GhostManager.getRedGhost());
		gb.addEntity(GhostManager.getYellowGhost());
		gb.addEntity(GhostManager.getPinkGhost());
		
		
		//gb.addEntity(new Orb(sm.getOrbSprites(),200,232f,"small"));
/*		largeOrb = new Orb(sm.getOrbSprites(), 200, 230, "large");
		smallOrb = new Orb(sm.getOrbSprites(), 220, 232f, "small");
		otherOrb = new Orb(sm.getOrbSprites(),200,250, "small");
		gb.addEntity(otherOrb);

		
		gb.addEntity(largeOrb);
		gb.addEntity(smallOrb);*/
		
		populateOrbs();
		
		
		gm.getBlueLogic().start();
		gm.getRedLogic().start();
		gm.getYellowLogic().start();
		gm.getPinkLogic().start();		
		
		Font font = new Font("Serif", Font.BOLD, 15);
		ufont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
		ufont.addAsciiGlyphs();
		ufont.addGlyphs(400, 600);
		ufont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		ufont.loadGlyphs();
	}
	
	private boolean recoverFromDeath(){
		if(pm.isDead() && GameBoard.getLives() > 0){
			pm.setStateMoving();
			pm.setX(272);
			pm.setY(465);
			pm.updateCenter();
			pm.setDirectionLeft();
			GhostManager.resetGhostPositions();
			InfluenceMap.resetInfluence();
			return true;
		}
		return false;
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
			pm.updateCurrentAnimation();
			
			if (timeBetweenMove > 60 && !(pm.isDead()) ){
				gm.decrementOrbTimer();
				if(gm.getOrbTimer() <= 0)
					GhostManager.setGhostsChasing();
				else if(gm.getOrbTimer() < 90)
					GhostManager.setGhostsFlashing();
				gb.updateEntityPosition();
				timeBetweenMove = 0;
				InfluenceMap.propagateInfluence();
			}
			else if(timeBetweenMove > 1380)
				recoverFromDeath();
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
			animateOrbs();
			
			//g.setColor(new Color(50, 50, 50, 180));
			//g.fillRect(0, 0, 640, 480);
			g.setColor(Color.white);
			g.setFont(ufont);
			g.drawString(Integer.toString(gb.getTileId(pm)), pm.getX(), pm.getY() + 10);
			g.drawString("Score: " + gb.getScore(), 465, 200);
			g.drawString("Lives: ", 465, 221);
			for(int i = 0; i < GameBoard.getLives(); i++)
				sm.getPacmanSprites()[4].draw(505+12*i,222);
			g.drawString("Lamp", 260, 280);
			for(int i = 0; i < 560; i+=20){
				for(int j = 0; j<620;j+=20){
					g.drawString((""+(int)InfluenceMap.getInfluenceMap()[i/20][j/20]),10+j,5+i);
				}
			}			
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
	
	
	
	
	public void populateOrbs(){
		
		largeOrb1 =new Orb(sm.getOrbSprites(),28,70,"large");
		largeOrb2 =new Orb(sm.getOrbSprites(),528,70,"large");
		largeOrb3 =new Orb(sm.getOrbSprites(),30,470,"large");
		largeOrb4 =new Orb(sm.getOrbSprites(),530,470,"large");
		
		smallOrb1 =new Orb(sm.getOrbSprites(),30,30,"small");
		smallOrb2 =new Orb(sm.getOrbSprites(),50,30,"small");
		smallOrb3 =new Orb(sm.getOrbSprites(),70,30,"small");
		smallOrb4 =new Orb(sm.getOrbSprites(),90,30,"small");
		smallOrb5 =new Orb(sm.getOrbSprites(),110,30,"small");
		smallOrb6 =new Orb(sm.getOrbSprites(),130,30,"small");
		smallOrb7 =new Orb(sm.getOrbSprites(),150,30,"small");
		smallOrb8 =new Orb(sm.getOrbSprites(),170,30,"small");
		smallOrb9 =new Orb(sm.getOrbSprites(),190,30,"small");
		smallOrb10 =new Orb(sm.getOrbSprites(),210,30,"small");
		smallOrb11 =new Orb(sm.getOrbSprites(),230,30,"small");
		smallOrb12 =new Orb(sm.getOrbSprites(),250,30,"small");
		smallOrb13 =new Orb(sm.getOrbSprites(),310,30,"small");
		smallOrb14 =new Orb(sm.getOrbSprites(),330,30,"small");
		smallOrb15 =new Orb(sm.getOrbSprites(),350,30,"small");
		smallOrb16 =new Orb(sm.getOrbSprites(),370,30,"small");
		smallOrb17 =new Orb(sm.getOrbSprites(),390,30,"small");
		smallOrb18 =new Orb(sm.getOrbSprites(),410,30,"small");
		smallOrb19 =new Orb(sm.getOrbSprites(),430,30,"small");
		smallOrb20 =new Orb(sm.getOrbSprites(),450,30,"small");
		smallOrb21 =new Orb(sm.getOrbSprites(),470,30,"small");
		smallOrb22 =new Orb(sm.getOrbSprites(),490,30,"small");
		smallOrb23 =new Orb(sm.getOrbSprites(),510,30,"small");
		smallOrb24 =new Orb(sm.getOrbSprites(),530,30,"small");
		smallOrb25 =new Orb(sm.getOrbSprites(),30,50,"small");
		smallOrb26 =new Orb(sm.getOrbSprites(),130,50,"small");
		smallOrb27 =new Orb(sm.getOrbSprites(),250,50,"small");
		smallOrb28 =new Orb(sm.getOrbSprites(),310,50,"small");
		smallOrb29 =new Orb(sm.getOrbSprites(),430,50,"small");
		smallOrb30 =new Orb(sm.getOrbSprites(),530,50,"small");
		smallOrb31 =new Orb(sm.getOrbSprites(),130,70,"small");
		smallOrb32 =new Orb(sm.getOrbSprites(),250,70,"small");
		smallOrb33 =new Orb(sm.getOrbSprites(),310,70,"small");
		smallOrb34 =new Orb(sm.getOrbSprites(),430,70,"small");
		smallOrb35 =new Orb(sm.getOrbSprites(),30,90,"small");
		smallOrb36 =new Orb(sm.getOrbSprites(),130,90,"small");
		smallOrb37 =new Orb(sm.getOrbSprites(),250,90,"small");
		smallOrb38 =new Orb(sm.getOrbSprites(),310,90,"small");
		smallOrb39 =new Orb(sm.getOrbSprites(),430,90,"small");
		smallOrb40 =new Orb(sm.getOrbSprites(),530,90,"small");
		smallOrb41 =new Orb(sm.getOrbSprites(),30,110,"small");
		smallOrb42 =new Orb(sm.getOrbSprites(),50,110,"small");
		smallOrb43 =new Orb(sm.getOrbSprites(),70,110,"small");
		smallOrb44 =new Orb(sm.getOrbSprites(),90,110,"small");
		smallOrb45 =new Orb(sm.getOrbSprites(),110,110,"small");
		smallOrb46 =new Orb(sm.getOrbSprites(),130,110,"small");
		smallOrb47 =new Orb(sm.getOrbSprites(),150,110,"small");
		smallOrb48 =new Orb(sm.getOrbSprites(),170,110,"small");
		smallOrb49 =new Orb(sm.getOrbSprites(),190,110,"small");
		smallOrb50 =new Orb(sm.getOrbSprites(),210,110,"small");
		smallOrb51 =new Orb(sm.getOrbSprites(),230,110,"small");
		smallOrb52 =new Orb(sm.getOrbSprites(),250,110,"small");
		smallOrb53 =new Orb(sm.getOrbSprites(),270,110,"small");
		smallOrb54 =new Orb(sm.getOrbSprites(),290,110,"small");
		smallOrb55 =new Orb(sm.getOrbSprites(),310,110,"small");
		smallOrb56 =new Orb(sm.getOrbSprites(),330,110,"small");
		smallOrb57 =new Orb(sm.getOrbSprites(),350,110,"small");
		smallOrb58 =new Orb(sm.getOrbSprites(),370,110,"small");
		smallOrb59 =new Orb(sm.getOrbSprites(),390,110,"small");
		smallOrb60 =new Orb(sm.getOrbSprites(),410,110,"small");
		smallOrb61 =new Orb(sm.getOrbSprites(),430,110,"small");
		smallOrb62 =new Orb(sm.getOrbSprites(),450,110,"small");
		smallOrb63 =new Orb(sm.getOrbSprites(),470,110,"small");
		smallOrb64 =new Orb(sm.getOrbSprites(),490,110,"small");
		smallOrb65 =new Orb(sm.getOrbSprites(),510,110,"small");
		smallOrb66 =new Orb(sm.getOrbSprites(),530,110,"small");
		smallOrb67 =new Orb(sm.getOrbSprites(),30,130,"small");
		smallOrb68 =new Orb(sm.getOrbSprites(),130,130,"small");
		smallOrb69 =new Orb(sm.getOrbSprites(),190,130,"small");
		smallOrb70 =new Orb(sm.getOrbSprites(),370,130,"small");
		smallOrb71 =new Orb(sm.getOrbSprites(),430,130,"small");
		smallOrb72 =new Orb(sm.getOrbSprites(),530,130,"small");
		smallOrb73 =new Orb(sm.getOrbSprites(),30,150,"small");
		smallOrb74 =new Orb(sm.getOrbSprites(),130,150,"small");
		smallOrb75 =new Orb(sm.getOrbSprites(),190,150,"small");
		smallOrb76 =new Orb(sm.getOrbSprites(),370,150,"small");
		smallOrb77 =new Orb(sm.getOrbSprites(),430,150,"small");
		smallOrb78 =new Orb(sm.getOrbSprites(),530,150,"small");
		smallOrb79 =new Orb(sm.getOrbSprites(),30,170,"small");
		smallOrb80 =new Orb(sm.getOrbSprites(),50,170,"small");
		smallOrb81 =new Orb(sm.getOrbSprites(),70,170,"small");
		smallOrb82 =new Orb(sm.getOrbSprites(),90,170,"small");
		smallOrb83 =new Orb(sm.getOrbSprites(),110,170,"small");
		smallOrb84 =new Orb(sm.getOrbSprites(),130,170,"small");
		smallOrb85 =new Orb(sm.getOrbSprites(),190,170,"small");
		smallOrb86 =new Orb(sm.getOrbSprites(),210,170,"small");
		smallOrb87 =new Orb(sm.getOrbSprites(),230,170,"small");
		smallOrb88 =new Orb(sm.getOrbSprites(),250,170,"small");
		smallOrb89 =new Orb(sm.getOrbSprites(),310,170,"small");
		smallOrb90 =new Orb(sm.getOrbSprites(),330,170,"small");
		smallOrb91 =new Orb(sm.getOrbSprites(),350,170,"small");
		smallOrb92 =new Orb(sm.getOrbSprites(),370,170,"small");
		smallOrb93 =new Orb(sm.getOrbSprites(),430,170,"small");
		smallOrb94 =new Orb(sm.getOrbSprites(),450,170,"small");
		smallOrb95 =new Orb(sm.getOrbSprites(),470,170,"small");
		smallOrb96 =new Orb(sm.getOrbSprites(),490,170,"small");
		smallOrb97 =new Orb(sm.getOrbSprites(),510,170,"small");
		smallOrb98 =new Orb(sm.getOrbSprites(),530,170,"small");
		smallOrb99 =new Orb(sm.getOrbSprites(),130,190,"small");
		smallOrb100 =new Orb(sm.getOrbSprites(),430,190,"small");
		smallOrb101 =new Orb(sm.getOrbSprites(),130,210,"small");
		smallOrb102 =new Orb(sm.getOrbSprites(),430,210,"small");
		smallOrb103 =new Orb(sm.getOrbSprites(),130,230,"small");
		smallOrb104 =new Orb(sm.getOrbSprites(),430,230,"small");
		smallOrb105 =new Orb(sm.getOrbSprites(),130,250,"small");
		smallOrb106 =new Orb(sm.getOrbSprites(),430,250,"small");
		smallOrb107 =new Orb(sm.getOrbSprites(),130,270,"small");
		smallOrb108 =new Orb(sm.getOrbSprites(),430,270,"small");
		smallOrb109 =new Orb(sm.getOrbSprites(),130,290,"small");
		smallOrb110 =new Orb(sm.getOrbSprites(),430,290,"small");
		smallOrb111 =new Orb(sm.getOrbSprites(),130,310,"small");
		smallOrb112 =new Orb(sm.getOrbSprites(),430,310,"small");
		smallOrb113 =new Orb(sm.getOrbSprites(),130,330,"small");
		smallOrb114 =new Orb(sm.getOrbSprites(),430,330,"small");
		smallOrb115 =new Orb(sm.getOrbSprites(),130,350,"small");
		smallOrb116 =new Orb(sm.getOrbSprites(),430,350,"small");
		smallOrb117 =new Orb(sm.getOrbSprites(),130,370,"small");
		smallOrb118 =new Orb(sm.getOrbSprites(),430,370,"small");
		smallOrb119 =new Orb(sm.getOrbSprites(),130,390,"small");
		smallOrb120 =new Orb(sm.getOrbSprites(),430,390,"small");
		smallOrb121 =new Orb(sm.getOrbSprites(),30,410,"small");
		smallOrb122 =new Orb(sm.getOrbSprites(),50,410,"small");
		smallOrb123 =new Orb(sm.getOrbSprites(),70,410,"small");
		smallOrb124 =new Orb(sm.getOrbSprites(),90,410,"small");
		smallOrb125 =new Orb(sm.getOrbSprites(),110,410,"small");
		smallOrb126 =new Orb(sm.getOrbSprites(),130,410,"small");
		smallOrb127 =new Orb(sm.getOrbSprites(),150,410,"small");
		smallOrb128 =new Orb(sm.getOrbSprites(),170,410,"small");
		smallOrb129 =new Orb(sm.getOrbSprites(),190,410,"small");
		smallOrb130 =new Orb(sm.getOrbSprites(),210,410,"small");
		smallOrb131 =new Orb(sm.getOrbSprites(),230,410,"small");
		smallOrb132 =new Orb(sm.getOrbSprites(),250,410,"small");
		smallOrb133 =new Orb(sm.getOrbSprites(),310,410,"small");
		smallOrb134 =new Orb(sm.getOrbSprites(),330,410,"small");
		smallOrb135 =new Orb(sm.getOrbSprites(),350,410,"small");
		smallOrb136 =new Orb(sm.getOrbSprites(),370,410,"small");
		smallOrb137 =new Orb(sm.getOrbSprites(),390,410,"small");
		smallOrb138 =new Orb(sm.getOrbSprites(),410,410,"small");
		smallOrb139 =new Orb(sm.getOrbSprites(),430,410,"small");
		smallOrb140 =new Orb(sm.getOrbSprites(),450,410,"small");
		smallOrb141 =new Orb(sm.getOrbSprites(),470,410,"small");
		smallOrb142 =new Orb(sm.getOrbSprites(),490,410,"small");
		smallOrb143 =new Orb(sm.getOrbSprites(),510,410,"small");
		smallOrb144 =new Orb(sm.getOrbSprites(),530,410,"small");
		smallOrb145 =new Orb(sm.getOrbSprites(),30,430,"small");
		smallOrb146 =new Orb(sm.getOrbSprites(),130,430,"small");
		smallOrb147 =new Orb(sm.getOrbSprites(),250,430,"small");
		smallOrb148 =new Orb(sm.getOrbSprites(),310,430,"small");
		smallOrb149 =new Orb(sm.getOrbSprites(),430,430,"small");
		smallOrb150 =new Orb(sm.getOrbSprites(),530,430,"small");
		smallOrb151 =new Orb(sm.getOrbSprites(),30,450,"small");
		smallOrb152 =new Orb(sm.getOrbSprites(),130,450,"small");
		smallOrb153 =new Orb(sm.getOrbSprites(),250,450,"small");
		smallOrb154 =new Orb(sm.getOrbSprites(),310,450,"small");
		smallOrb155 =new Orb(sm.getOrbSprites(),430,450,"small");
		smallOrb156 =new Orb(sm.getOrbSprites(),530,450,"small");
		smallOrb157 =new Orb(sm.getOrbSprites(),50,470,"small");
		smallOrb158 =new Orb(sm.getOrbSprites(),70,470,"small");
		smallOrb159 =new Orb(sm.getOrbSprites(),130,470,"small");
		smallOrb160 =new Orb(sm.getOrbSprites(),150,470,"small");
		smallOrb161 =new Orb(sm.getOrbSprites(),170,470,"small");
		smallOrb162 =new Orb(sm.getOrbSprites(),190,470,"small");
		smallOrb163 =new Orb(sm.getOrbSprites(),210,470,"small");
		smallOrb164 =new Orb(sm.getOrbSprites(),230,470,"small");
		smallOrb165 =new Orb(sm.getOrbSprites(),250,470,"small");
		smallOrb166 =new Orb(sm.getOrbSprites(),270,470,"small");
		smallOrb167 =new Orb(sm.getOrbSprites(),290,470,"small");
		smallOrb168 =new Orb(sm.getOrbSprites(),310,470,"small");
		smallOrb169 =new Orb(sm.getOrbSprites(),330,470,"small");
		smallOrb170 =new Orb(sm.getOrbSprites(),350,470,"small");
		smallOrb171 =new Orb(sm.getOrbSprites(),370,470,"small");
		smallOrb172 =new Orb(sm.getOrbSprites(),390,470,"small");
		smallOrb173 =new Orb(sm.getOrbSprites(),410,470,"small");
		smallOrb174 =new Orb(sm.getOrbSprites(),430,470,"small");
		smallOrb175 =new Orb(sm.getOrbSprites(),490,470,"small");
		smallOrb176 =new Orb(sm.getOrbSprites(),510,470,"small");
		smallOrb177 =new Orb(sm.getOrbSprites(),70,490,"small");
		smallOrb178 =new Orb(sm.getOrbSprites(),130,490,"small");
		smallOrb179 =new Orb(sm.getOrbSprites(),190,490,"small");
		smallOrb180 =new Orb(sm.getOrbSprites(),370,490,"small");
		smallOrb181 =new Orb(sm.getOrbSprites(),430,490,"small");
		smallOrb182 =new Orb(sm.getOrbSprites(),490,490,"small");
		smallOrb183 =new Orb(sm.getOrbSprites(),70,510,"small");
		smallOrb184 =new Orb(sm.getOrbSprites(),130,510,"small");
		smallOrb185 =new Orb(sm.getOrbSprites(),190,510,"small");
		smallOrb186 =new Orb(sm.getOrbSprites(),370,510,"small");
		smallOrb187 =new Orb(sm.getOrbSprites(),430,510,"small");
		smallOrb188 =new Orb(sm.getOrbSprites(),490,510,"small");
		smallOrb189 =new Orb(sm.getOrbSprites(),30,530,"small");
		smallOrb190 =new Orb(sm.getOrbSprites(),50,530,"small");
		smallOrb191 =new Orb(sm.getOrbSprites(),70,530,"small");
		smallOrb192 =new Orb(sm.getOrbSprites(),90,530,"small");
		smallOrb193 =new Orb(sm.getOrbSprites(),110,530,"small");
		smallOrb194 =new Orb(sm.getOrbSprites(),130,530,"small");
		smallOrb195 =new Orb(sm.getOrbSprites(),190,530,"small");
		smallOrb196 =new Orb(sm.getOrbSprites(),210,530,"small");
		smallOrb197 =new Orb(sm.getOrbSprites(),230,530,"small");
		smallOrb198 =new Orb(sm.getOrbSprites(),250,530,"small");
		smallOrb199 =new Orb(sm.getOrbSprites(),310,530,"small");
		smallOrb200 =new Orb(sm.getOrbSprites(),330,530,"small");
		smallOrb201 =new Orb(sm.getOrbSprites(),350,530,"small");
		smallOrb202 =new Orb(sm.getOrbSprites(),370,530,"small");
		smallOrb203 =new Orb(sm.getOrbSprites(),430,530,"small");
		smallOrb204 =new Orb(sm.getOrbSprites(),450,530,"small");
		smallOrb205 =new Orb(sm.getOrbSprites(),470,530,"small");
		smallOrb206 =new Orb(sm.getOrbSprites(),490,530,"small");
		smallOrb207 =new Orb(sm.getOrbSprites(),510,530,"small");
		smallOrb208 =new Orb(sm.getOrbSprites(),530,530,"small");
		smallOrb209 =new Orb(sm.getOrbSprites(),30,550,"small");
		smallOrb210 =new Orb(sm.getOrbSprites(),250,550,"small");
		smallOrb211 =new Orb(sm.getOrbSprites(),310,550,"small");
		smallOrb212 =new Orb(sm.getOrbSprites(),530,550,"small");
		smallOrb213 =new Orb(sm.getOrbSprites(),30,570,"small");
		smallOrb214 =new Orb(sm.getOrbSprites(),250,570,"small");
		smallOrb215 =new Orb(sm.getOrbSprites(),310,570,"small");
		smallOrb216 =new Orb(sm.getOrbSprites(),530,570,"small");
		smallOrb217 =new Orb(sm.getOrbSprites(),30,590,"small");
		smallOrb218 =new Orb(sm.getOrbSprites(),50,590,"small");
		smallOrb219 =new Orb(sm.getOrbSprites(),70,590,"small");
		smallOrb220 =new Orb(sm.getOrbSprites(),90,590,"small");
		smallOrb221 =new Orb(sm.getOrbSprites(),110,590,"small");
		smallOrb222 =new Orb(sm.getOrbSprites(),130,590,"small");
		smallOrb223 =new Orb(sm.getOrbSprites(),150,590,"small");
		smallOrb224 =new Orb(sm.getOrbSprites(),170,590,"small");
		smallOrb225 =new Orb(sm.getOrbSprites(),190,590,"small");
		smallOrb226 =new Orb(sm.getOrbSprites(),210,590,"small");
		smallOrb227 =new Orb(sm.getOrbSprites(),230,590,"small");
		smallOrb228 =new Orb(sm.getOrbSprites(),250,590,"small");
		smallOrb229 =new Orb(sm.getOrbSprites(),270,590,"small");
		smallOrb230 =new Orb(sm.getOrbSprites(),290,590,"small");
		smallOrb231 =new Orb(sm.getOrbSprites(),310,590,"small");
		smallOrb232 =new Orb(sm.getOrbSprites(),330,590,"small");
		smallOrb233 =new Orb(sm.getOrbSprites(),350,590,"small");
		smallOrb234 =new Orb(sm.getOrbSprites(),370,590,"small");
		smallOrb235 =new Orb(sm.getOrbSprites(),390,590,"small");
		smallOrb236 =new Orb(sm.getOrbSprites(),410,590,"small");
		smallOrb237 =new Orb(sm.getOrbSprites(),430,590,"small");
		smallOrb238 =new Orb(sm.getOrbSprites(),450,590,"small");
		smallOrb239 =new Orb(sm.getOrbSprites(),470,590,"small");
		smallOrb240 =new Orb(sm.getOrbSprites(),490,590,"small");
		smallOrb241 =new Orb(sm.getOrbSprites(),510,590,"small");
		smallOrb242 =new Orb(sm.getOrbSprites(),530,590,"small");
		
		
		gb.addEntity(largeOrb1);
		gb.addEntity(largeOrb2);
		gb.addEntity(largeOrb3);
		gb.addEntity(largeOrb4);
		gb.addEntity(smallOrb1);
		gb.addEntity(smallOrb2);
		gb.addEntity(smallOrb3);
		gb.addEntity(smallOrb4);
		gb.addEntity(smallOrb5);
		gb.addEntity(smallOrb6);
		gb.addEntity(smallOrb7);
		gb.addEntity(smallOrb8);
		gb.addEntity(smallOrb9);
		gb.addEntity(smallOrb10);
		gb.addEntity(smallOrb11);
		gb.addEntity(smallOrb12);
		gb.addEntity(smallOrb13);
		gb.addEntity(smallOrb14);
		gb.addEntity(smallOrb15);
		gb.addEntity(smallOrb16);
		gb.addEntity(smallOrb17);
		gb.addEntity(smallOrb18);
		gb.addEntity(smallOrb19);
		gb.addEntity(smallOrb20);
		gb.addEntity(smallOrb21);
		gb.addEntity(smallOrb22);
		gb.addEntity(smallOrb23);
		gb.addEntity(smallOrb24);
		gb.addEntity(smallOrb25);
		gb.addEntity(smallOrb26);
		gb.addEntity(smallOrb27);
		gb.addEntity(smallOrb28);
		gb.addEntity(smallOrb29);
		gb.addEntity(smallOrb30);
		gb.addEntity(smallOrb31);
		gb.addEntity(smallOrb32);
		gb.addEntity(smallOrb33);
		gb.addEntity(smallOrb34);
		gb.addEntity(smallOrb35);
		gb.addEntity(smallOrb36);
		gb.addEntity(smallOrb37);
		gb.addEntity(smallOrb38);
		gb.addEntity(smallOrb39);
		gb.addEntity(smallOrb40);
		gb.addEntity(smallOrb41);
		gb.addEntity(smallOrb42);
		gb.addEntity(smallOrb43);
		gb.addEntity(smallOrb44);
		gb.addEntity(smallOrb45);
		gb.addEntity(smallOrb46);
		gb.addEntity(smallOrb47);
		gb.addEntity(smallOrb48);
		gb.addEntity(smallOrb49);
		gb.addEntity(smallOrb50);
		gb.addEntity(smallOrb51);
		gb.addEntity(smallOrb52);
		gb.addEntity(smallOrb53);
		gb.addEntity(smallOrb54);
		gb.addEntity(smallOrb55);
		gb.addEntity(smallOrb56);
		gb.addEntity(smallOrb57);
		gb.addEntity(smallOrb58);
		gb.addEntity(smallOrb59);
		gb.addEntity(smallOrb60);
		gb.addEntity(smallOrb61);
		gb.addEntity(smallOrb62);
		gb.addEntity(smallOrb63);
		gb.addEntity(smallOrb64);
		gb.addEntity(smallOrb65);
		gb.addEntity(smallOrb66);
		gb.addEntity(smallOrb67);
		gb.addEntity(smallOrb68);
		gb.addEntity(smallOrb69);
		gb.addEntity(smallOrb70);
		gb.addEntity(smallOrb71);
		gb.addEntity(smallOrb72);
		gb.addEntity(smallOrb73);
		gb.addEntity(smallOrb74);
		gb.addEntity(smallOrb75);
		gb.addEntity(smallOrb76);
		gb.addEntity(smallOrb77);
		gb.addEntity(smallOrb78);
		gb.addEntity(smallOrb79);
		gb.addEntity(smallOrb80);
		gb.addEntity(smallOrb81);
		gb.addEntity(smallOrb82);
		gb.addEntity(smallOrb83);
		gb.addEntity(smallOrb84);
		gb.addEntity(smallOrb85);
		gb.addEntity(smallOrb86);
		gb.addEntity(smallOrb87);
		gb.addEntity(smallOrb88);
		gb.addEntity(smallOrb89);
		gb.addEntity(smallOrb90);
		gb.addEntity(smallOrb91);
		gb.addEntity(smallOrb92);
		gb.addEntity(smallOrb93);
		gb.addEntity(smallOrb94);
		gb.addEntity(smallOrb95);
		gb.addEntity(smallOrb96);
		gb.addEntity(smallOrb97);
		gb.addEntity(smallOrb98);
		gb.addEntity(smallOrb99);
		gb.addEntity(smallOrb100);
		gb.addEntity(smallOrb101);
		gb.addEntity(smallOrb102);
		gb.addEntity(smallOrb103);
		gb.addEntity(smallOrb104);
		gb.addEntity(smallOrb105);
		gb.addEntity(smallOrb106);
		gb.addEntity(smallOrb107);
		gb.addEntity(smallOrb108);
		gb.addEntity(smallOrb109);
		gb.addEntity(smallOrb110);
		gb.addEntity(smallOrb111);
		gb.addEntity(smallOrb112);
		gb.addEntity(smallOrb113);
		gb.addEntity(smallOrb114);
		gb.addEntity(smallOrb115);
		gb.addEntity(smallOrb116);
		gb.addEntity(smallOrb117);
		gb.addEntity(smallOrb118);
		gb.addEntity(smallOrb119);
		gb.addEntity(smallOrb120);
		gb.addEntity(smallOrb121);
		gb.addEntity(smallOrb122);
		gb.addEntity(smallOrb123);
		gb.addEntity(smallOrb124);
		gb.addEntity(smallOrb125);
		gb.addEntity(smallOrb126);
		gb.addEntity(smallOrb127);
		gb.addEntity(smallOrb128);
		gb.addEntity(smallOrb129);
		gb.addEntity(smallOrb130);
		gb.addEntity(smallOrb131);
		gb.addEntity(smallOrb132);
		gb.addEntity(smallOrb133);
		gb.addEntity(smallOrb134);
		gb.addEntity(smallOrb135);
		gb.addEntity(smallOrb136);
		gb.addEntity(smallOrb137);
		gb.addEntity(smallOrb138);
		gb.addEntity(smallOrb139);
		gb.addEntity(smallOrb140);
		gb.addEntity(smallOrb141);
		gb.addEntity(smallOrb142);
		gb.addEntity(smallOrb143);
		gb.addEntity(smallOrb144);
		gb.addEntity(smallOrb145);
		gb.addEntity(smallOrb146);
		gb.addEntity(smallOrb147);
		gb.addEntity(smallOrb148);
		gb.addEntity(smallOrb149);
		gb.addEntity(smallOrb150);
		gb.addEntity(smallOrb151);
		gb.addEntity(smallOrb152);
		gb.addEntity(smallOrb153);
		gb.addEntity(smallOrb154);
		gb.addEntity(smallOrb155);
		gb.addEntity(smallOrb156);
		gb.addEntity(smallOrb157);
		gb.addEntity(smallOrb158);
		gb.addEntity(smallOrb159);
		gb.addEntity(smallOrb160);
		gb.addEntity(smallOrb161);
		gb.addEntity(smallOrb162);
		gb.addEntity(smallOrb163);
		gb.addEntity(smallOrb164);
		gb.addEntity(smallOrb165);
		gb.addEntity(smallOrb166);
		gb.addEntity(smallOrb167);
		gb.addEntity(smallOrb168);
		gb.addEntity(smallOrb169);
		gb.addEntity(smallOrb170);
		gb.addEntity(smallOrb171);
		gb.addEntity(smallOrb172);
		gb.addEntity(smallOrb173);
		gb.addEntity(smallOrb174);
		gb.addEntity(smallOrb175);
		gb.addEntity(smallOrb176);
		gb.addEntity(smallOrb177);
		gb.addEntity(smallOrb178);
		gb.addEntity(smallOrb179);
		gb.addEntity(smallOrb180);
		gb.addEntity(smallOrb181);
		gb.addEntity(smallOrb182);
		gb.addEntity(smallOrb183);
		gb.addEntity(smallOrb184);
		gb.addEntity(smallOrb185);
		gb.addEntity(smallOrb186);
		gb.addEntity(smallOrb187);
		gb.addEntity(smallOrb188);
		gb.addEntity(smallOrb189);
		gb.addEntity(smallOrb190);
		gb.addEntity(smallOrb191);
		gb.addEntity(smallOrb192);
		gb.addEntity(smallOrb193);
		gb.addEntity(smallOrb194);
		gb.addEntity(smallOrb195);
		gb.addEntity(smallOrb196);
		gb.addEntity(smallOrb197);
		gb.addEntity(smallOrb198);
		gb.addEntity(smallOrb199);
		gb.addEntity(smallOrb200);
		gb.addEntity(smallOrb201);
		gb.addEntity(smallOrb202);
		gb.addEntity(smallOrb203);
		gb.addEntity(smallOrb204);
		gb.addEntity(smallOrb205);
		gb.addEntity(smallOrb206);
		gb.addEntity(smallOrb207);
		gb.addEntity(smallOrb208);
		gb.addEntity(smallOrb209);
		gb.addEntity(smallOrb210);
		gb.addEntity(smallOrb211);
		gb.addEntity(smallOrb212);
		gb.addEntity(smallOrb213);
		gb.addEntity(smallOrb214);
		gb.addEntity(smallOrb215);
		gb.addEntity(smallOrb216);
		gb.addEntity(smallOrb217);
		gb.addEntity(smallOrb218);
		gb.addEntity(smallOrb219);
		gb.addEntity(smallOrb220);
		gb.addEntity(smallOrb221);
		gb.addEntity(smallOrb222);
		gb.addEntity(smallOrb223);
		gb.addEntity(smallOrb224);
		gb.addEntity(smallOrb225);
		gb.addEntity(smallOrb226);
		gb.addEntity(smallOrb227);
		gb.addEntity(smallOrb228);
		gb.addEntity(smallOrb229);
		gb.addEntity(smallOrb230);
		gb.addEntity(smallOrb231);
		gb.addEntity(smallOrb232);
		gb.addEntity(smallOrb233);
		gb.addEntity(smallOrb234);
		gb.addEntity(smallOrb235);
		gb.addEntity(smallOrb236);
		gb.addEntity(smallOrb237);
		gb.addEntity(smallOrb238);
		gb.addEntity(smallOrb239);
		gb.addEntity(smallOrb240);
		gb.addEntity(smallOrb241);
		gb.addEntity(smallOrb242);
		}
	public void animateOrbs(){
		
		largeOrb1.drawCurrentAnimation();
		largeOrb2.drawCurrentAnimation();
		largeOrb3.drawCurrentAnimation();
		largeOrb4.drawCurrentAnimation();
		
		smallOrb1.drawCurrentAnimation();
		smallOrb2.drawCurrentAnimation();
		smallOrb3.drawCurrentAnimation();
		smallOrb4.drawCurrentAnimation();
		smallOrb5.drawCurrentAnimation();
		smallOrb6.drawCurrentAnimation();
		smallOrb7.drawCurrentAnimation();
		smallOrb8.drawCurrentAnimation();
		smallOrb9.drawCurrentAnimation();
		smallOrb10.drawCurrentAnimation();
		smallOrb11.drawCurrentAnimation();
		smallOrb12.drawCurrentAnimation();
		smallOrb13.drawCurrentAnimation();
		smallOrb14.drawCurrentAnimation();
		smallOrb15.drawCurrentAnimation();
		smallOrb16.drawCurrentAnimation();
		smallOrb17.drawCurrentAnimation();
		smallOrb18.drawCurrentAnimation();
		smallOrb19.drawCurrentAnimation();
		smallOrb20.drawCurrentAnimation();
		smallOrb21.drawCurrentAnimation();
		smallOrb22.drawCurrentAnimation();
		smallOrb23.drawCurrentAnimation();
		smallOrb24.drawCurrentAnimation();
		smallOrb25.drawCurrentAnimation();
		smallOrb26.drawCurrentAnimation();
		smallOrb27.drawCurrentAnimation();
		smallOrb28.drawCurrentAnimation();
		smallOrb29.drawCurrentAnimation();
		smallOrb30.drawCurrentAnimation();
		smallOrb31.drawCurrentAnimation();
		smallOrb32.drawCurrentAnimation();
		smallOrb33.drawCurrentAnimation();
		smallOrb34.drawCurrentAnimation();
		smallOrb35.drawCurrentAnimation();
		smallOrb36.drawCurrentAnimation();
		smallOrb37.drawCurrentAnimation();
		smallOrb38.drawCurrentAnimation();
		smallOrb39.drawCurrentAnimation();
		smallOrb40.drawCurrentAnimation();
		smallOrb41.drawCurrentAnimation();
		smallOrb42.drawCurrentAnimation();
		smallOrb43.drawCurrentAnimation();
		smallOrb44.drawCurrentAnimation();
		smallOrb45.drawCurrentAnimation();
		smallOrb46.drawCurrentAnimation();
		smallOrb47.drawCurrentAnimation();
		smallOrb48.drawCurrentAnimation();
		smallOrb49.drawCurrentAnimation();
		smallOrb50.drawCurrentAnimation();
		smallOrb51.drawCurrentAnimation();
		smallOrb52.drawCurrentAnimation();
		smallOrb53.drawCurrentAnimation();
		smallOrb54.drawCurrentAnimation();
		smallOrb55.drawCurrentAnimation();
		smallOrb56.drawCurrentAnimation();
		smallOrb57.drawCurrentAnimation();
		smallOrb58.drawCurrentAnimation();
		smallOrb59.drawCurrentAnimation();
		smallOrb60.drawCurrentAnimation();
		smallOrb61.drawCurrentAnimation();
		smallOrb62.drawCurrentAnimation();
		smallOrb63.drawCurrentAnimation();
		smallOrb64.drawCurrentAnimation();
		smallOrb65.drawCurrentAnimation();
		smallOrb66.drawCurrentAnimation();
		smallOrb67.drawCurrentAnimation();
		smallOrb68.drawCurrentAnimation();
		smallOrb69.drawCurrentAnimation();
		smallOrb70.drawCurrentAnimation();
		smallOrb71.drawCurrentAnimation();
		smallOrb72.drawCurrentAnimation();
		smallOrb73.drawCurrentAnimation();
		smallOrb74.drawCurrentAnimation();
		smallOrb75.drawCurrentAnimation();
		smallOrb76.drawCurrentAnimation();
		smallOrb77.drawCurrentAnimation();
		smallOrb78.drawCurrentAnimation();
		smallOrb79.drawCurrentAnimation();
		smallOrb80.drawCurrentAnimation();
		smallOrb81.drawCurrentAnimation();
		smallOrb82.drawCurrentAnimation();
		smallOrb83.drawCurrentAnimation();
		smallOrb84.drawCurrentAnimation();
		smallOrb85.drawCurrentAnimation();
		smallOrb86.drawCurrentAnimation();
		smallOrb87.drawCurrentAnimation();
		smallOrb88.drawCurrentAnimation();
		smallOrb89.drawCurrentAnimation();
		smallOrb90.drawCurrentAnimation();
		smallOrb91.drawCurrentAnimation();
		smallOrb92.drawCurrentAnimation();
		smallOrb93.drawCurrentAnimation();
		smallOrb94.drawCurrentAnimation();
		smallOrb95.drawCurrentAnimation();
		smallOrb96.drawCurrentAnimation();
		smallOrb97.drawCurrentAnimation();
		smallOrb98.drawCurrentAnimation();
		smallOrb99.drawCurrentAnimation();
		smallOrb100.drawCurrentAnimation();
		smallOrb101.drawCurrentAnimation();
		smallOrb102.drawCurrentAnimation();
		smallOrb103.drawCurrentAnimation();
		smallOrb104.drawCurrentAnimation();
		smallOrb105.drawCurrentAnimation();
		smallOrb106.drawCurrentAnimation();
		smallOrb107.drawCurrentAnimation();
		smallOrb108.drawCurrentAnimation();
		smallOrb109.drawCurrentAnimation();
		smallOrb110.drawCurrentAnimation();
		smallOrb111.drawCurrentAnimation();
		smallOrb112.drawCurrentAnimation();
		smallOrb113.drawCurrentAnimation();
		smallOrb114.drawCurrentAnimation();
		smallOrb115.drawCurrentAnimation();
		smallOrb116.drawCurrentAnimation();
		smallOrb117.drawCurrentAnimation();
		smallOrb118.drawCurrentAnimation();
		smallOrb119.drawCurrentAnimation();
		smallOrb120.drawCurrentAnimation();
		smallOrb121.drawCurrentAnimation();
		smallOrb122.drawCurrentAnimation();
		smallOrb123.drawCurrentAnimation();
		smallOrb124.drawCurrentAnimation();
		smallOrb125.drawCurrentAnimation();
		smallOrb126.drawCurrentAnimation();
		smallOrb127.drawCurrentAnimation();
		smallOrb128.drawCurrentAnimation();
		smallOrb129.drawCurrentAnimation();
		smallOrb130.drawCurrentAnimation();
		smallOrb131.drawCurrentAnimation();
		smallOrb132.drawCurrentAnimation();
		smallOrb133.drawCurrentAnimation();
		smallOrb134.drawCurrentAnimation();
		smallOrb135.drawCurrentAnimation();
		smallOrb136.drawCurrentAnimation();
		smallOrb137.drawCurrentAnimation();
		smallOrb138.drawCurrentAnimation();
		smallOrb139.drawCurrentAnimation();
		smallOrb140.drawCurrentAnimation();
		smallOrb141.drawCurrentAnimation();
		smallOrb142.drawCurrentAnimation();
		smallOrb143.drawCurrentAnimation();
		smallOrb144.drawCurrentAnimation();
		smallOrb145.drawCurrentAnimation();
		smallOrb146.drawCurrentAnimation();
		smallOrb147.drawCurrentAnimation();
		smallOrb148.drawCurrentAnimation();
		smallOrb149.drawCurrentAnimation();
		smallOrb150.drawCurrentAnimation();
		smallOrb151.drawCurrentAnimation();
		smallOrb152.drawCurrentAnimation();
		smallOrb153.drawCurrentAnimation();
		smallOrb154.drawCurrentAnimation();
		smallOrb155.drawCurrentAnimation();
		smallOrb156.drawCurrentAnimation();
		smallOrb157.drawCurrentAnimation();
		smallOrb158.drawCurrentAnimation();
		smallOrb159.drawCurrentAnimation();
		smallOrb160.drawCurrentAnimation();
		smallOrb161.drawCurrentAnimation();
		smallOrb162.drawCurrentAnimation();
		smallOrb163.drawCurrentAnimation();
		smallOrb164.drawCurrentAnimation();
		smallOrb165.drawCurrentAnimation();
		smallOrb166.drawCurrentAnimation();
		smallOrb167.drawCurrentAnimation();
		smallOrb168.drawCurrentAnimation();
		smallOrb169.drawCurrentAnimation();
		smallOrb170.drawCurrentAnimation();
		smallOrb171.drawCurrentAnimation();
		smallOrb172.drawCurrentAnimation();
		smallOrb173.drawCurrentAnimation();
		smallOrb174.drawCurrentAnimation();
		smallOrb175.drawCurrentAnimation();
		smallOrb176.drawCurrentAnimation();
		smallOrb177.drawCurrentAnimation();
		smallOrb178.drawCurrentAnimation();
		smallOrb179.drawCurrentAnimation();
		smallOrb180.drawCurrentAnimation();
		smallOrb181.drawCurrentAnimation();
		smallOrb182.drawCurrentAnimation();
		smallOrb183.drawCurrentAnimation();
		smallOrb184.drawCurrentAnimation();
		smallOrb185.drawCurrentAnimation();
		smallOrb186.drawCurrentAnimation();
		smallOrb187.drawCurrentAnimation();
		smallOrb188.drawCurrentAnimation();
		smallOrb189.drawCurrentAnimation();
		smallOrb190.drawCurrentAnimation();
		smallOrb191.drawCurrentAnimation();
		smallOrb192.drawCurrentAnimation();
		smallOrb193.drawCurrentAnimation();
		smallOrb194.drawCurrentAnimation();
		smallOrb195.drawCurrentAnimation();
		smallOrb196.drawCurrentAnimation();
		smallOrb197.drawCurrentAnimation();
		smallOrb198.drawCurrentAnimation();
		smallOrb199.drawCurrentAnimation();
		smallOrb200.drawCurrentAnimation();
		smallOrb201.drawCurrentAnimation();
		smallOrb202.drawCurrentAnimation();
		smallOrb203.drawCurrentAnimation();
		smallOrb204.drawCurrentAnimation();
		smallOrb205.drawCurrentAnimation();
		smallOrb206.drawCurrentAnimation();
		smallOrb207.drawCurrentAnimation();
		smallOrb208.drawCurrentAnimation();
		smallOrb209.drawCurrentAnimation();
		smallOrb210.drawCurrentAnimation();
		smallOrb211.drawCurrentAnimation();
		smallOrb212.drawCurrentAnimation();
		smallOrb213.drawCurrentAnimation();
		smallOrb214.drawCurrentAnimation();
		smallOrb215.drawCurrentAnimation();
		smallOrb216.drawCurrentAnimation();
		smallOrb217.drawCurrentAnimation();
		smallOrb218.drawCurrentAnimation();
		smallOrb219.drawCurrentAnimation();
		smallOrb220.drawCurrentAnimation();
		smallOrb221.drawCurrentAnimation();
		smallOrb222.drawCurrentAnimation();
		smallOrb223.drawCurrentAnimation();
		smallOrb224.drawCurrentAnimation();
		smallOrb225.drawCurrentAnimation();
		smallOrb226.drawCurrentAnimation();
		smallOrb227.drawCurrentAnimation();
		smallOrb228.drawCurrentAnimation();
		smallOrb229.drawCurrentAnimation();
		smallOrb230.drawCurrentAnimation();
		smallOrb231.drawCurrentAnimation();
		smallOrb232.drawCurrentAnimation();
		smallOrb233.drawCurrentAnimation();
		smallOrb234.drawCurrentAnimation();
		smallOrb235.drawCurrentAnimation();
		smallOrb236.drawCurrentAnimation();
		smallOrb237.drawCurrentAnimation();
		smallOrb238.drawCurrentAnimation();
		smallOrb239.drawCurrentAnimation();
		smallOrb240.drawCurrentAnimation();
		smallOrb241.drawCurrentAnimation();
		smallOrb242.drawCurrentAnimation();
	}
}