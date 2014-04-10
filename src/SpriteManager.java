import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class SpriteManager {
	private SpriteSheet spriteSheet;
	public static final String spritePath = "/sprites2.png";
	public static final int animationFrames = 175;
	
	public SpriteManager(){
		try {
			spriteSheet = new SpriteSheet(spritePath, GameBoard.tileLength, GameBoard.tileLength, Color.black);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Image[] getPacmanSprites(){
		Image[] sprites = new Image[9];
		sprites[0] = spriteSheet.getSprite(0, 0);
		sprites[1] = spriteSheet.getSprite(1, 0);
		sprites[2] = spriteSheet.getSprite(2, 0);
		sprites[3] = spriteSheet.getSprite(0, 1);
		sprites[4] = spriteSheet.getSprite(1, 1);
		sprites[5] = spriteSheet.getSprite(0, 2);
		sprites[6] = spriteSheet.getSprite(1, 2);
		sprites[7] = spriteSheet.getSprite(0, 3);
		sprites[8] = spriteSheet.getSprite(1, 3);
		return sprites;
	}
	
	public Image[] getRedGhostSprites(){
		Image[] sprites = new Image[12];
		sprites[0] = spriteSheet.getSprite(0, 4);
		sprites[1] = spriteSheet.getSprite(1, 4);
		sprites[2] = spriteSheet.getSprite(2, 4);
		sprites[3] = spriteSheet.getSprite(3, 4);
		sprites[4] = spriteSheet.getSprite(4, 4);
		sprites[5] = spriteSheet.getSprite(5, 4);
		sprites[6] = spriteSheet.getSprite(6, 4);
		sprites[7] = spriteSheet.getSprite(7, 4);
		sprites[8] = spriteSheet.getSprite(0, 8);
		sprites[9] = spriteSheet.getSprite(1, 8);
		sprites[10] = spriteSheet.getSprite(2, 8);
		sprites[11] = spriteSheet.getSprite(3, 8);
		return sprites;
	}
	
}
