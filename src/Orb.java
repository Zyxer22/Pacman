import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;


public class Orb implements Entity{
	private Position position;
	private Image[] sprites;
	private String direction;
	Animation largeOrbAnimation;
	Animation smallOrbAnimation;
	Animation currentAnimation;
	Animation blackAnimation;
	private boolean isLargeOrb; //true is large, false is small
	private boolean isVisible;
	private boolean wasStateChange;
	private final Vector2Float size;
	private Vector2Float center;
	private AABB box;
	
	Orb(Image[] sprites, float x, float y, String type){
		direction = "null";
		this.sprites = sprites;
		
		this.size = new Vector2Float(10, 10);
		this.center = new Vector2Float(x,y);
		this.box = new AABB(this.center,this.size);
		
		Image[] largeOrbSprites = new Image[1];
		largeOrbSprites[0] = sprites[1];
		
		Image[] smallOrbSprites = new Image[1];
		smallOrbSprites[0] = sprites[2];
		
		Image[] blackSprites = new Image[1];
		blackSprites[0] = sprites[3];
		
		largeOrbAnimation = new Animation(largeOrbSprites, SpriteManager.animationFrames);
		smallOrbAnimation = new Animation(smallOrbSprites, SpriteManager.animationFrames);
		blackAnimation = new Animation(blackSprites, SpriteManager.animationFrames);
		
		if (type.equals("large")){
			isLargeOrb = true;
		}
		else{
			isLargeOrb = false;
		}
		
		position = new Position(x, y);
		isVisible = true;
		if (isLargeOrb){
			currentAnimation = largeOrbAnimation;
		}
		else{
			currentAnimation = smallOrbAnimation;
		}
		wasStateChange = false;
		startCurrentAnimation();
	}
	
	public void setX(float x){
		position.setX(x);
	}
	
	public void setY(float y){
		position.setY(y);
	}
	
	public void setPosition(float x, float y){
		position.setX(x);
		position.setY(y);
	}
	
	public float getX(){
		return position.getX();
	}
	
	public float getY(){
		return position.getY();
	}
	
	public Position getPosition(){
		return position;
	}
	
	public Image[] getSprite(){
		return sprites;
	}

	public String getDirection() {
		return direction;
	}
	
	public boolean getIsLargeOrb(){
		return isLargeOrb;
	}
	
	public boolean getIsVisible(){
		return isVisible;
	}
	
	public void setVisible(){
		isVisible = true;
		wasStateChange = true;
		updateCurrentAnimation();
	}
	
	public void setNotVisible(){
		isVisible = false;
		wasStateChange = true;
		updateCurrentAnimation();
	}
	
	public void drawCurrentAnimation(){
		currentAnimation.draw(position.getX(), position.getY());
	}
	
	private void startCurrentAnimation(){
		currentAnimation.start();
	}
	
	private void stopCurrentAnimation(){
		currentAnimation.stop();
	}
	
	private void updateCurrentAnimation(){
		if (!wasStateChange){
			return;
		}
		
		if (!isVisible){
			currentAnimation.stop();
			currentAnimation = blackAnimation;
			currentAnimation.start();
		}
		else{
			currentAnimation.stop();
			if (isLargeOrb){
				currentAnimation = largeOrbAnimation;
			}
			else{
				currentAnimation = smallOrbAnimation;
			}
			currentAnimation.start();
		}
		
		wasStateChange = false;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2Float getSize() {
		return this.size;
	}
	@Override
	public void updateCenter(){
		this.center.set(this.getX(),this.getY());
	}

	@Override
	public AABB getBox() {
		return this.box;
	}
}
