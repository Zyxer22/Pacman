import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;


public class Pacman implements Entity{
	private Position position;
	private Image[] sprites;
	private Animation leftAnimation;
	private Animation rightAnimation;
	private Animation upAnimation;
	private Animation downAnimation;
	private Animation currentAnimation;
	private int currentFrame;
	private String direction;
	
	public Pacman(Image[] sprites){
		position = new Position(270, 342);
		this.sprites = sprites;
		
		//create animations
		
		Image[] leftSprites = new Image[3];
		leftSprites[0] = sprites[1];
		leftSprites[1] = sprites[0];
		leftSprites[2] = sprites[2];
		
		Image[] rightSprites = new Image[3];
		rightSprites[0] = sprites[4];
		rightSprites[1] = sprites[3];
		rightSprites[2] = sprites[2];
		
		Image[] upSprites = new Image[3];
		upSprites[0] = sprites[6];
		upSprites[1] = sprites[5];
		upSprites[2] = sprites[2];
		
		Image[] downSprites = new Image[3];
		downSprites[0] = sprites[8];
		downSprites[1] = sprites[7];
		downSprites[2] = sprites[2];
		
		leftAnimation = new Animation(leftSprites, SpriteManager.animationFrames);
		leftAnimation.setPingPong(true);
		rightAnimation = new Animation(rightSprites, SpriteManager.animationFrames);
		rightAnimation.setPingPong(true);
		upAnimation = new Animation(upSprites, SpriteManager.animationFrames);
		upAnimation.setPingPong(true);
		downAnimation = new Animation(downSprites, SpriteManager.animationFrames);
		downAnimation.setPingPong(true);
		
		currentAnimation = leftAnimation;
		startCurrentAnimation();
		direction = "left";
		
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
	
	public Animation getUpAnimation(){
		return upAnimation;
	}
	
	public Animation getDownAnimation(){
		return downAnimation;
	}
	
	public Animation getLeftAnimation(){
		return leftAnimation;
	}
	
	public Animation getRightAnimation(){
		return rightAnimation;
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
	
	public void setDirectionDown(){
		direction = "down";
	}
	
	public void setDirectionUp(){
		direction = "up";
	}
	
	public void setDirectionLeft(){
		direction = "left";
	}
	
	public void setDirectionRight(){
		direction = "right";
	}
	
	public String getDirection(){
		return direction;
	}
	
	public void updateCurrentAnimation(){
		if (direction.equals("left")){
			//frame matching
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = leftAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("right")){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = rightAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("up")){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = upAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("down")){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = downAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
	}
}
