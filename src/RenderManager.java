import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;


public class RenderManager {
	
	public static void drawResource(Image[] sprites, Graphics g, float x, float y){
		int xTileOffset = GameBoard.tileLength;
		int yTileOffset = 0;
		int spriteNum = 1;
		for (Image sprite : sprites){
			if (spriteNum > sprites.length/2){
				xTileOffset = 0;
				yTileOffset = GameBoard.tileLength;
			}
			g.drawImage(sprite, x - xTileOffset, y - yTileOffset);
			spriteNum++;
		}
	}
	
}
