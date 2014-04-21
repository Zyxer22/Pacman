import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class GameBoard {
	public static final int width = 560;
	public static final int length = 620;
	public static final int tileLength = 20;
	public static final Vector2Float size = new Vector2Float(280, 310);
	public static final Vector2Float center = new Vector2Float(280, 310);
	public static final AABB absoluteBoundary = new AABB(center, size);
	private int score = 0;
	private static int lives = 3;
	List<Entity> entities;
	static TiledMap tm;
	InfluenceMapLocksmith influenceMapLocksmith;
	static Random rand = new Random();

	public static final String tileMapPath = "/BoardTileMap.tmx";
	public static final String spritePath = "/background.png";

	private Image bg;

	public GameBoard(InfluenceMapLocksmith iml) {
		try {
			bg = new Image(spritePath);
			tm = new TiledMap(tileMapPath);
			System.out.println("Tile height = " + tm.getTileHeight()
					+ ", tile width  = " + tm.getTileWidth());
			System.out.println("Map height = " + tm.getHeight()
					+ ", map width = " + tm.getWidth());
			System.out.println("Objects in map = " + tm.getObjectCount(0));
			for (int i = 0; i < tm.getObjectCount(0); i++) {
				System.out.print("Object" + i + ": ");
				System.out.print(tm.getObjectX(0, i) + ", "
						+ tm.getObjectY(0, i));
				System.out.println(", Height: " + tm.getObjectHeight(0, i)
						+ ", Width: " + tm.getObjectWidth(0, i));
			}

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entities = new ArrayList<Entity>();
		this.influenceMapLocksmith = iml;
	}

	public Image getBG() {
		return bg;
	}

	public void renderTileMap(float x, float y) {
		tm.render((int) x, (int) y);
	}

	public void renderTileMap() {
		tm.render(0, 0);
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public int getScore() {
		return score;
	}

	public void incrementScore(int inc) {
		this.score += inc;
	}

	public static int getLives() {
		return lives;
	}

	public static void removeLife() {
		lives--;
	}

	public static void addLife() {
		lives++;
	}

	public void updateEntityPosition() {
		Pacman pacman = (Pacman) entities.get(0);

		float oldX = pacman.getX();
		float oldY = pacman.getY();

		incrementScore(pacman.checkForGhost(entities));
		incrementScore(pacman.checkForOrb(entities));

		if (!(pacman.getNextState().equals(pacman.getDirection()))) {
			pacman.move(pacman.getNextState());
			if (!isBlocked((Pacman) entities.get(0))) {
				pacman.setDirection(pacman.getNextState());
			}

			pacman.setPosition(oldX, oldY);
		}
		for (Entity entity : entities) {
			oldX = entity.getX();
			oldY = entity.getY();

			if (entity.getClass().getName().equals("Pacman")) {
				entity.move();
				if (isBlocked(entity)) {
					entity.setPosition(oldX, oldY);
				}
			} else if (entity.getClass().getName().equals("Ghost")) {
				Ghost ghost = (Ghost) entity;
				if (ghost.getPathReady().get() == true) {
					ghost.move();
					if (isBlocked(entity)) {
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

	public int getTileId(Entity entity) {
		return tm.getTileId((int) (entity.getX() / 10),
				(int) (entity.getY() / 10), 0);
	}
	
	public static String getRandomJunctionDirection(float x, float y, int numCorridors, String curDirection){
		String[] directions = new String[numCorridors];
		
		
		int index = 0;
		if (!isBlocked(x+5, y) && !directionIsOpposite(curDirection, "right")){
			directions[index] = "right";
			index++;
		}
		
		if (!isBlocked(x-5, y) && !directionIsOpposite(curDirection, "left")){
			directions[index] = "left";
			index++;
		}
		
		if (!isBlocked(x, y+5) && !directionIsOpposite(curDirection, "down")){
			if ((y < 240 || y >= 320) || (x < 200 || x >= 340)){
				directions[index] = "down";
				index++;
			}
		}
		
		if (!isBlocked(x, y-5) && !directionIsOpposite(curDirection, "up")){
			directions[index] = "up";
			index++;
		}
		
		if (index == 0){
			return curDirection;
		}
		
		int randomNum = rand.nextInt(index);
		String direction = directions[randomNum];
		return direction;
		
	}
	
	
	public static int getJunctionCount(float x, float y){
		//provide neutral position, unmoved
		
		int corridorCount = 0;
		
		if (!isBlocked(x+5, y)){
			corridorCount++;
		}
		
		if (!isBlocked(x-5, y)){
			corridorCount++;
		}
		
		if (!isBlocked(x, y+5)){
			if ((y < 240 || y >= 320) && (x < 200 || x >= 340)){
				corridorCount++;
			}
		}
		
		if (!isBlocked(x, y-5)){
			corridorCount++;
		}
		
		return corridorCount;
	}
	
	public static boolean isBlocked(float x, float y) {
		for (int i = 0; i < tm.getObjectCount(0) - 1; i++) {

			float entX = x;
			float entY = y;
			int objX = tm.getObjectX(0, i);
			int objY = tm.getObjectY(0, i);
			int objWidth = tm.getObjectWidth(0, i);
			int objHeight = tm.getObjectHeight(0, i);
		
		// offset to allow some passage
					int tileOffset = 5;

					if ((entX >= objX - tileLength + tileOffset)
							&& (entX <= (objX + objWidth))) {
						if ((entY >= objY - tileLength + tileOffset)
								&& (entY <= (objY + objHeight))) {
							
							return true;
						}

					} else if ((entY >= objY - tileLength + tileOffset)
							&& (entY <= (objY + objHeight))) {
						if ((entX >= objX - tileLength + tileOffset)
								&& (entX <= (objX + objWidth))) {
							
							return true;
						}
					}
			}
		
		return false;
	}

	public static boolean isBlocked(Entity entity) {
		// don't check for gate (ghosts can go through)
		// do pacman check after
		if(entity.getClass().getName().equals("Ghost"))
			if(((Ghost)entity).getState().equals("eaten")){
				return false;
			}
		for (int i = 0; i < tm.getObjectCount(0) - 1; i++) {

			float entX = entity.getX();
			float entY = entity.getY();
			int objX = tm.getObjectX(0, i);
			int objY = tm.getObjectY(0, i);
			int objWidth = tm.getObjectWidth(0, i);
			int objHeight = tm.getObjectHeight(0, i);

			// offset to allow some passage
			int tileOffset = 5;

			if ((entX >= objX - tileLength + tileOffset)
					&& (entX <= (objX + objWidth))) {
				if ((entY >= objY - tileLength + tileOffset)
						&& (entY <= (objY + objHeight))) {
					if (entity.getClass().getName().equals("Ghost")) {
						Ghost ghost = (Ghost) entity;
						ghost.setIsBlocked(true);
					}
					return true;
				}

			} else if ((entY >= objY - tileLength + tileOffset)
					&& (entY <= (objY + objHeight))) {
				if ((entX >= objX - tileLength + tileOffset)
						&& (entX <= (objX + objWidth))) {
					if (entity.getClass().getName().equals("Ghost")) {
						Ghost ghost = (Ghost) entity;
						ghost.setIsBlocked(true);
					}
					return true;
				}
			}
		}

		if (entity.getClass().getName().equals("Pacman")) {
			return pacmanGateCheck((Pacman) entity);
		}

		if (entity.getClass().getName().equals("Ghost")) {
			Ghost ghost = (Ghost) entity;
			ghost.setIsBlocked(false);
		}
		return false;
	}

	public static boolean pacmanGateCheck(Pacman pacman) {
		int gateX = tm.getObjectX(0, tm.getObjectCount(0) - 1);
		int gateY = tm.getObjectY(0, tm.getObjectCount(0) - 1);
		int gateHeight = tm.getObjectHeight(0, tm.getObjectCount(0) - 1);
		int gateWidth = tm.getObjectWidth(0, tm.getObjectCount(0) - 1);

		int tileOffset = 0;
		int entX = (int) pacman.getX();
		int entY = (int) pacman.getY();

		if ((entX >= gateX - tileLength + tileOffset)
				&& (entX <= (gateX + gateWidth))) {
			if ((entY >= gateY - tileLength + tileOffset)
					&& (entY <= (gateY + gateHeight))) {
				// System.out.println("detected collision");
				return true;
			}

		} else if ((entY >= gateY - tileLength + tileOffset)
				&& (entY <= (gateY + gateHeight))) {
			if ((entX >= gateX - tileLength + tileOffset)
					&& (entX <= (gateX + gateWidth))) {
				// System.out.println("detected collision");
				return true;
			}
		}

		return false;
	}
	
	public static boolean directionIsOpposite(String direction1, String direction2){
		
		if (direction1.equals("up") && direction2.equals("down")){
			return true;
		}
		
		if (direction1.equals("left") && direction2.equals("right")){
			return true;
		}
		
		if (direction1.equals("down") && direction2.equals("up")){
			return true;
		}
		
		if (direction1.equals("right") && direction2.equals("left")){
			return true;
		}
		
		return false;
	}

}
