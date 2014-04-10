import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;


public class Ghost implements Entity{
	private Position position;
	private Image[] sprites;
	private Animation leftAnimation;
	private Animation rightAnimation;
	private Animation upAnimation;
	private Animation downAnimation;
	private Animation currentAnimation;
	private Animation orbAnimation;
	private Animation eatenAnimation;
	private int currentFrame;
	private String direction;
	private String type;
	private String state;
	
	public Ghost(Image[] sprites, String type, float x, float y, String state){
		position = new Position(x, y);
		this.sprites = sprites;
		this.state = state;
		
		//create animations
		
		Image[] upSprites = new Image[2];
		upSprites[0] = sprites[0];
		upSprites[1] = sprites[1];
		
		Image[] downSprites = new Image[2];
		downSprites[0] = sprites[2];
		downSprites[1] = sprites[3];
		
		Image[] leftSprites = new Image[2];
		leftSprites[0] = sprites[4];
		leftSprites[1] = sprites[5];
		
		Image[] rightSprites = new Image[2];
		rightSprites[0] = sprites[6];
		rightSprites[1] = sprites[7];
		
		Image[] orbSprites = new Image[2];
		orbSprites[0] = sprites[8];
		orbSprites[1]= sprites[9];
		
		Image[] eatenSprites = new Image[2];
		eatenSprites[0] = sprites[10];
		eatenSprites[1] = sprites[11];
		
		leftAnimation = new Animation(leftSprites, SpriteManager.animationFrames);
		rightAnimation = new Animation(rightSprites, SpriteManager.animationFrames);
		upAnimation = new Animation(upSprites, SpriteManager.animationFrames);
		downAnimation = new Animation(downSprites, SpriteManager.animationFrames);
		orbAnimation = new Animation(orbSprites, SpriteManager.animationFrames);
		eatenAnimation = new Animation(eatenSprites, SpriteManager.animationFrames);
		
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
		updateCurrentAnimation();
	}
	
	public void setDirectionUp(){
		direction = "up";
		updateCurrentAnimation();
	}
	
	public void setDirectionLeft(){
		direction = "left";
		updateCurrentAnimation();
	}
	
	public void setDirectionRight(){
		direction = "right";
		updateCurrentAnimation();
	}
	
	public String getDirection(){
		return direction;
	}
	
	public void updateCurrentAnimation(){
		if (direction.equals("left") && (state.equals("chasing") || state.equals("running"))){
			//frame matching
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = leftAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("right") && (state.equals("chasing") || state.equals("running"))){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = rightAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("up") && (state.equals("chasing") || state.equals("running"))){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = upAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("down") && (state.equals("chasing") || state.equals("running"))){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = downAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (state.equals("orb")){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = orbAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (state.equals("eaten")){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame();
			currentAnimation = eatenAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
	}
	
	public String getType(){
		return type;
	}
	
	public void setStateChasing(){
		state = "chasing";
		updateCurrentAnimation();
	}
	
	public void setStateOrb(){
		state = "orb";
		updateCurrentAnimation();
	}
	
	public void setStateEaten(){
		state = "eaten";
		updateCurrentAnimation();
	}
	
	public void setStateRunning(){
		state = "running";
		updateCurrentAnimation();
	}
	
	public void setStateFlashing(){
		
	}
	
}
