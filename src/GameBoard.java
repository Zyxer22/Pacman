import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;


public class GameBoard {
	public static final int width = 560;
	public static final int length = 620;
	public static final int tileLength = 20;
	public static final Vector2Float size = new Vector2Float(280,310);
	public static final Vector2Float center = new Vector2Float(280,310);
	public static final AABB absoluteBoundary = new AABB(center,size);
	private int score = 0;
	private static int lives = 3;
	List<Entity> entities;
	static TiledMap tm;
	InfluenceMapLocksmith influenceMapLocksmith;
	
	public static final String tileMapPath = "/BoardTileMap.tmx";
	public static final String spritePath = "/background.png";
	
	private Image bg;
	
	public GameBoard(InfluenceMapLocksmith iml){
		try {
			bg = new Image(spritePath);
			tm = new TiledMap(tileMapPath);
			System.out.println("Tile height = " + tm.getTileHeight() + ", tile width  = " + tm.getTileWidth());
			System.out.println("Map height = " + tm.getHeight() + ", map width = " + tm.getWidth());
			System.out.println("Objects in map = " + tm.getObjectCount(0));
			for (int i = 0; i < tm.getObjectCount(0); i++){
				System.out.print("Object" + i + ": ");
				System.out.print(tm.getObjectX(0, i) + ", " + tm.getObjectY(0, i));
				System.out.println(", Height: " + tm.getObjectHeight(0, i) + ", Width: " + tm.getObjectWidth(0, i));
			}
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entities = new ArrayList<Entity>();
		this.influenceMapLocksmith = iml;
	}
	
	public Image getBG(){
		return bg;
	}
	
	public void renderTileMap(float x, float y){
		tm.render((int)x, (int)y);
	}
	
	public void renderTileMap(){
		tm.render(0, 0);
	}
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	public int getScore(){
		return score;
	}
	public void incrementScore(int inc){
		this.score+=inc;
	}
	public static int getLives(){
		return lives;
	}
	public static void removeLife(){
		lives--;
	}
	public static void addLife(){
		lives++;
	}
	public void updateEntityPosition(){
		Pacman pacman = (Pacman) entities.get(0);
		

		float oldX = pacman.getX();
		float oldY = pacman.getY();
		
		incrementScore(pacman.checkForGhost(entities));
		incrementScore(pacman.checkForOrb(entities));
		
		if(!(pacman.getNextState().equals(pacman.getDirection()))){
			pacman.move(pacman.getNextState());
			if(!isBlocked((Pacman) entities.get(0))){
				pacman.setDirection(pacman.getNextState());
			}
		
			pacman.setPosition(oldX, oldY);
		}
		for (Entity entity : entities){
			oldX = entity.getX();
			oldY = entity.getY();
			
			if (entity.getClass().getName().equals("Pacman")){
				entity.move();
				if (isBlocked(entity)){
					entity.setPosition(oldX, oldY);
				}		
			}
			else if (entity.getClass().getName().equals("Ghost")){
				Ghost ghost = (Ghost)entity;
				if (ghost.getPathReady().get() == true ){
					if(ghost.getState().equals("eaten")){
						eyeRoll(ghost);
						if(ghost.atHome()){
							ghost.setStateChasing();
							ghost.doReset.set(true);
							//put ghost to sleep?
						}
					}
					System.out.println(ghost.getType()+"\tX: "+ghost.getX()+"\tY: "+ghost.getY());
					ghost.move();
					System.out.println(ghost.getType()+"New X: "+ghost.getX()+"\tNew Y: "+ghost.getY());
					if (isBlocked(entity)){
						entity.setPosition(oldX, oldY);
					}	
						ghost.getPathReady().set(false);
						ghost.lock();
						ghost.signal();
						ghost.unlock();
				}
				
			}
			
		}
	}
	
	public int getTileId(Entity entity){
		return tm.getTileId((int)(entity.getX()/10), (int)(entity.getY()/10), 0);
	}
	
	public static boolean isBlocked(Entity entity){
		//don't check for gate (ghosts can go through)
		//do pacman check after
		for (int i = 0; i < tm.getObjectCount(0) - 1; i++){
			
			float entX = entity.getX();
			float entY = entity.getY();
			int objX = tm.getObjectX(0, i);
			int objY = tm.getObjectY(0, i);
			int objWidth = tm.getObjectWidth(0, i);
			int objHeight = tm.getObjectHeight(0, i);
			
			//offset to allow some passage
			int tileOffset = 5;
			
			
			if ((entX >= objX - tileLength + tileOffset) && (entX <= (objX + objWidth))){
				if ((entY >= objY - tileLength + tileOffset) && (entY <= (objY + objHeight))){
					if (entity.getClass().getName().equals("Ghost")){
						Ghost ghost = (Ghost)entity;
						ghost.setIsBlocked(true);
					}
					return true;
				}
				
			}
			else if ((entY >= objY - tileLength + tileOffset) && (entY <= (objY + objHeight))){
				if ((entX >= objX - tileLength + tileOffset) && (entX <= (objX + objWidth))){
					if (entity.getClass().getName().equals("Ghost")){
						Ghost ghost = (Ghost)entity;
						ghost.setIsBlocked(true);
					}
					return true;
				}
			}
		}
		
		if (entity.getClass().getName().equals("Pacman")){
			return pacmanGateCheck((Pacman)entity);
		}
		
		if (entity.getClass().getName().equals("Ghost")){
			Ghost ghost = (Ghost)entity;
			ghost.setIsBlocked(false);
		}
		return false;
	}
	
	public static boolean pacmanGateCheck(Pacman pacman){
		int gateX = tm.getObjectX(0, tm.getObjectCount(0) - 1);
		int gateY = tm.getObjectY(0, tm.getObjectCount(0) - 1);
		int gateHeight = tm.getObjectHeight(0, tm.getObjectCount(0) - 1);
		int gateWidth = tm.getObjectWidth(0, tm.getObjectCount(0) - 1);
		
		int tileOffset = 0;
		int entX = (int)pacman.getX();
		int entY = (int)pacman.getY();
		
		if ((entX >= gateX - tileLength + tileOffset) && (entX <= (gateX + gateWidth))){
			if ((entY >= gateY - tileLength + tileOffset) && (entY <= (gateY + gateHeight))){
				//System.out.println("detected collision");
				return true;
			}
			
		}
		else if ((entY >= gateY - tileLength + tileOffset) && (entY <= (gateY + gateHeight))){
			if ((entX >= gateX - tileLength + tileOffset) && (entX <= (gateX + gateWidth))){
				//System.out.println("detected collision");
				return true;
			}
		}
		
		return false;
	}
	public void eyeRoll(Ghost ghost){
		int x = (int)ghost.getX();
		int y = (int)ghost.getY();

		if(x <= 127 && (y == 26 || y == 106 || y == 166 || y == 406 || y == 526)){
			ghost.setDirection("right");
		}
		else if(x >= 427 && (y == 26 || y == 106 || y == 166 || y == 406 || y == 526)){
			ghost.setDirection("left");
		}
		else if(x > 127 && x < 247){
			ghost.setDirection("left");
		}
		else if( x > 307 && x < 427){
			ghost.setDirection("right");
		}
		else if( y < 106 && (x == 127 || x == 427)){
			ghost.setDirection("up");
		}
		else if( y < 106 && ( x == 247 || x == 307)){
			ghost.setDirection("down");
		}
		else if((y >= 106 && y < 166) && (x == 187 || x == 307)){
			ghost.setDirection("down");
		}
		else if( y == 166 && (x >= 187 && x < 247)){
			ghost.setDirection("right");
		}
		else if( y == 166 && (x > 307 && x <= 367)){
			ghost.setDirection("left");
		}
		else if((x == 307 || x == 247) && (y < 226 && y >= 166)){
			ghost.setDirection("down");
		}
		else if( y == 106 && x <= 127){
			ghost.setDirection("right");
		}
		else if(y == 106 && !(x == 187 || x == 367)){
			ghost.setDirection("left");
		}
		else if( (x == 127 || x == 427) && y <=527){
			ghost.setDirection("up");
		}
		else if(x < 427 && y == 466){
			ghost.setDirection("left");
		}
		else if(y == 586 && x != 27 && x != 527){
			ghost.setDirection("left");
		}
		else if((x == 27 || x == 527) && (y <= 586 && y > 526)){
			ghost.setDirection("up");
		}
		else if( (y <= 406 && y >466) && (x == 466 || x == 406)){
			ghost.setDirection("down");
		}
		else if( y == 406 && (x <= 247 && x > 127)){
			ghost.setDirection("left");
		}
		else if( y  == 406 && (x >= 307 && x < 427)){
			ghost.setDirection("right");
		}
		else if(x == 527 && ( y <= 466 && y > 406)){
			ghost.setDirection("up");
		}
		else if(x == 27 &&  ( y <= 466 && y > 406)){
			ghost.setDirection("up");
		}
		else if((x == 487 || x == 67) && (y < 526 && y > 466)){
			ghost.setDirection("up");
		}
		else if( (x < 67 && x > 27) && y == 466){
			ghost.setDirection("left");
		}
		else if( (x < 527 && x > 487) && y == 466){
			ghost.setDirection("right");
		}
		else if( (x == 307 || x == 247) && (y <586 && y >=526)){
			ghost.setDirection("down");
		}
		else if( y == 526 && (x < 247 && x > 187)){
			ghost.setDirection("left");
		}
		else if( y == 526 && (x > 307 && x < 367)){
			ghost.setDirection("right");
		}
		else if( (x == 367 || x == 187) && (y <=526 && y > 466)){
			ghost.setDirection("up");
		}
		else if( y == 286 && (x > 367 && x < 427)){
			ghost.setDirection("right");
		}
		else if( y == 286 && (x < 187 && x > 127)){
			ghost.setDirection("left");
		}
		else if(y == 346 && (x < 367 && x > 187)){
			ghost.setDirection("left");
		}
		else if(x == 187 || x == 367){
			ghost.setDirection("up");
		}
		else if(y == 226 && (x <=367 && x > 287)){
			ghost.setDirection("left");
		}
		else if(y == 226 && (x >= 187 && x < 287)){
			ghost.setDirection("right");
		}
		else ghost.setDirection("down");
	}
}
