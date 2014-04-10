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
	private Animation deathAnimation;
	private int currentFrame;
	private String direction;
	private String state;
	private boolean wasStateChange = false;
	
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
		
		Image[] deathSprites = new Image[12];
		deathSprites[0] = sprites[9];
		deathSprites[1] = sprites[10];
		deathSprites[2] = sprites[11];
		deathSprites[3] = sprites[12];
		deathSprites[4] = sprites[13];
		deathSprites[5] = sprites[14];
		deathSprites[6] = sprites[15];
		deathSprites[7] = sprites[16];
		deathSprites[8] = sprites[17];
		deathSprites[9] = sprites[18];
		deathSprites[10] = sprites[19];
		deathSprites[11] = sprites[20];
		
		leftAnimation = new Animation(leftSprites, SpriteManager.animationFrames);
		leftAnimation.setPingPong(true);
		rightAnimation = new Animation(rightSprites, SpriteManager.animationFrames);
		rightAnimation.setPingPong(true);
		upAnimation = new Animation(upSprites, SpriteManager.animationFrames);
		upAnimation.setPingPong(true);
		downAnimation = new Animation(downSprites, SpriteManager.animationFrames);
		downAnimation.setPingPong(true);
		deathAnimation = new Animation(deathSprites, SpriteManager.animationFrames);
		deathAnimation.setSpeed(1.5f);
		deathAnimation.setLooping(false);
		
		currentAnimation = leftAnimation;
		direction = "left";
		state = "moving";
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
		wasStateChange = true;
		direction = "down";
	}
	
	public void setDirectionUp(){
		wasStateChange = true;
		direction = "up";
	}
	
	public void setDirectionLeft(){
		wasStateChange = true;
		direction = "left";
	}
	
	public void setDirectionRight(){
		wasStateChange = true;
		direction = "right";
	}
	
	public String getDirection(){
		return direction;
	}
	
	public void updateCurrentAnimation(){
		if (!wasStateChange){
			return;
		}

		if (direction.equals("left") && state.equals("moving")){
			//frame matching
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame() % leftAnimation.getFrameCount();
			currentAnimation = leftAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("right") && state.equals("moving")){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame() % rightAnimation.getFrameCount();
			currentAnimation = rightAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("up") && state.equals("moving")){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame() % upAnimation.getFrameCount();
			currentAnimation = upAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (direction.equals("down") && state.equals("moving")){
			currentAnimation.stop();
			currentFrame = currentAnimation.getFrame() % downAnimation.getFrameCount();
			currentAnimation = downAnimation;
			currentAnimation.setCurrentFrame(currentFrame);
			currentAnimation.start();
		}
		else if (state.equals("dead")){
			currentAnimation.stop();
			currentAnimation = deathAnimation;
			currentAnimation.restart();
		}
		
		wasStateChange = false;
	}
	
	public void setStateMoving(){
		state = "moving";
		wasStateChange = true;
		updateCurrentAnimation();
	}
	
	public void setStateDead(){
		state = "dead";
		wasStateChange = true;
		updateCurrentAnimation();
	}
	
	public boolean isDead(){
		if (state.equals("dead")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void move(){
		float curX, curY;
		curX = getX();
		curY = getY();
		if (getDirection().equals("left") && !isDead()){
			curX -= GameBoard.tileLength/4;
			setX(curX);
		}
		else if (getDirection().equals("right") && !isDead()){
			curX += GameBoard.tileLength/4;
			setX(curX);
		}
		else if (getDirection().equals("up") && !isDead()){
			curY -= GameBoard.tileLength/4;
			setY(curY);
		}
		else if (getDirection().equals("down") && !isDead()){
			curY += GameBoard.tileLength/4;
			setY(curY);
		}
	}
}
