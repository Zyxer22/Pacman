import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class GameBoard {
	public static final int width = 560;
	public static final int length = 620;
	public static final int tileLength = 20;
	List<Entity> entities;
	
	public static final String spritePath = "/background.png";
	
	private Image bg;
	
	public GameBoard(){
		try {
			bg = new Image(spritePath);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entities = new ArrayList<Entity>();
	}
	
	public Image getBG(){
		return bg;
	}
	
	public void addEntity(Entity entity){
		entities.add(entity);
	}
	
	public void updateEntityPosition(){
		String direction;
		for (Entity entity : entities){
			Pacman pm = (Pacman)entity;
			Position curPosition = pm.getPosition();
			float curX, curY;
			curX = curPosition.getX();
			curY = curPosition.getY();
			if (pm.getDirection().equals("left")){
				curX -= GameBoard.tileLength/4;
				pm.setX(curX);
			}
			else if (pm.getDirection().equals("right")){
				curX += GameBoard.tileLength/4;
				pm.setX(curX);
			}
			else if (pm.getDirection().equals("up")){
				curY -= GameBoard.tileLength/4;
				pm.setY(curY);
			}
			else if (pm.getDirection().equals("down")){
				curY += GameBoard.tileLength/4;
				pm.setY(curY);
			}
		}
	}
}
