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
		Image[] sprites = new Image[21];
		sprites[0] = spriteSheet.getSprite(0, 0);
		sprites[1] = spriteSheet.getSprite(1, 0);
		sprites[2] = spriteSheet.getSprite(2, 0);
		sprites[3] = spriteSheet.getSprite(0, 1);
		sprites[4] = spriteSheet.getSprite(1, 1);
		sprites[5] = spriteSheet.getSprite(0, 2);
		sprites[6] = spriteSheet.getSprite(1, 2);
		sprites[7] = spriteSheet.getSprite(0, 3);
		sprites[8] = spriteSheet.getSprite(1, 3);
		sprites[9] = spriteSheet.getSprite(0, 12);
		sprites[10] = spriteSheet.getSprite(1, 12);
		sprites[11] = spriteSheet.getSprite(2, 12);
		sprites[12] = spriteSheet.getSprite(3, 12);
		sprites[13] = spriteSheet.getSprite(4, 12);
		sprites[14] = spriteSheet.getSprite(5, 12);
		sprites[15] = spriteSheet.getSprite(6, 12);
		sprites[16] = spriteSheet.getSprite(7, 12);
		sprites[17] = spriteSheet.getSprite(8, 12);
		sprites[18] = spriteSheet.getSprite(9, 12);
		sprites[19] = spriteSheet.getSprite(10, 12);
		sprites[20] = spriteSheet.getSprite(11, 12);
		return sprites;
	}
	
	public Image[] getOrbSprites(){
		Image[] sprites = new Image[4];
		sprites[0] = spriteSheet.getSprite(0, 9);
		sprites[1] = sprites[0].getSubImage(0, 0, GameBoard.tileLength/2, GameBoard.tileLength/2);
		sprites[2] = sprites[1].getScaledCopy(0.6f);
		sprites[3] = spriteSheet.getSprite(3, 0);
		return sprites;
	}
	
	public Image[] getYellowGhostSprites(){
		Image[] sprites = new Image[16];
		sprites[0] = spriteSheet.getSprite(0, 7);
		sprites[1] = spriteSheet.getSprite(1, 7);
		sprites[2] = spriteSheet.getSprite(2, 7);
		sprites[3] = spriteSheet.getSprite(3, 7);
		sprites[4] = spriteSheet.getSprite(4, 7);
		sprites[5] = spriteSheet.getSprite(5, 7);
		sprites[6] = spriteSheet.getSprite(6, 7);
		sprites[7] = spriteSheet.getSprite(7, 7);
		sprites[8] = spriteSheet.getSprite(0, 8);
		sprites[9] = spriteSheet.getSprite(1, 8);
		sprites[10] = spriteSheet.getSprite(2, 8);
		sprites[11] = spriteSheet.getSprite(3, 8);
		sprites[12] = spriteSheet.getSprite(0, 10);
		sprites[13] = spriteSheet.getSprite(1, 10);
		sprites[14] = spriteSheet.getSprite(2, 10);
		sprites[15] = spriteSheet.getSprite(3, 10);
		return sprites;
	}
	
	public Image[] getBlueGhostSprites(){
		Image[] sprites = new Image[16];
		sprites[0] = spriteSheet.getSprite(0, 6);
		sprites[1] = spriteSheet.getSprite(1, 6);
		sprites[2] = spriteSheet.getSprite(2, 6);
		sprites[3] = spriteSheet.getSprite(3, 6);
		sprites[4] = spriteSheet.getSprite(4, 6);
		sprites[5] = spriteSheet.getSprite(5, 6);
		sprites[6] = spriteSheet.getSprite(6, 6);
		sprites[7] = spriteSheet.getSprite(7, 6);
		sprites[8] = spriteSheet.getSprite(0, 8);
		sprites[9] = spriteSheet.getSprite(1, 8);
		sprites[10] = spriteSheet.getSprite(2, 8);
		sprites[11] = spriteSheet.getSprite(3, 8);
		sprites[12] = spriteSheet.getSprite(0, 10);
		sprites[13] = spriteSheet.getSprite(1, 10);
		sprites[14] = spriteSheet.getSprite(2, 10);
		sprites[15] = spriteSheet.getSprite(3, 10);
		return sprites;
	}
	
	public Image[] getPinkGhostSprites(){
		Image[] sprites = new Image[16];
		sprites[0] = spriteSheet.getSprite(0, 5);
		sprites[1] = spriteSheet.getSprite(1, 5);
		sprites[2] = spriteSheet.getSprite(2, 5);
		sprites[3] = spriteSheet.getSprite(3, 5);
		sprites[4] = spriteSheet.getSprite(4, 5);
		sprites[5] = spriteSheet.getSprite(5, 5);
		sprites[6] = spriteSheet.getSprite(6, 5);
		sprites[7] = spriteSheet.getSprite(7, 5);
		sprites[8] = spriteSheet.getSprite(0, 8);
		sprites[9] = spriteSheet.getSprite(1, 8);
		sprites[10] = spriteSheet.getSprite(2, 8);
		sprites[11] = spriteSheet.getSprite(3, 8);
		sprites[12] = spriteSheet.getSprite(0, 10);
		sprites[13] = spriteSheet.getSprite(1, 10);
		sprites[14] = spriteSheet.getSprite(2, 10);
		sprites[15] = spriteSheet.getSprite(3, 10);
		return sprites;
	}
	
	public Image[] getRedGhostSprites(){
		Image[] sprites = new Image[16];
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
		sprites[12] = spriteSheet.getSprite(0, 10);
		sprites[13] = spriteSheet.getSprite(1, 10);
		sprites[14] = spriteSheet.getSprite(2, 10);
		sprites[15] = spriteSheet.getSprite(3, 10);
		return sprites;
	}
	
}
