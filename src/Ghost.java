import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
	private Animation upEatenAnimation;
	private Animation downEatenAnimation;
	private Animation leftEatenAnimation;
	private Animation rightEatenAnimation;
	private Animation flashingAnimation;
	private int currentFrame;
	private String direction;
	private String type;
	private String state;
	private AtomicBoolean sleeping = new AtomicBoolean(true);
	private final long restTimer = 500;
	private AABB box;
	private Vector2Float size;
	private Vector2Float center;
	public static final float GHOST_INFLUENCE_VALUE = 0.00001f;
	private AtomicBoolean pathReady = new AtomicBoolean(false);
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private String lastDirection;
	private boolean blocked = false;
	public AtomicBoolean doReset = new AtomicBoolean(true);
	public boolean directionSwitched = false;
	
	public Ghost(Image[] sprites, String type, float x, float y, String state){
		position = new Position(x, y);
		this.sprites = sprites;
		this.state = state;
		this.size = new Vector2Float(sprites[0].getWidth()/2, sprites[0].getHeight()/2);
		this.center = new Vector2Float(x/2,y/2);
		this.box = new AABB(center,size);
		this.type = type;
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
		
		Image[] flashingSprites = new Image[4];
		flashingSprites[0] = sprites[10];
		flashingSprites[1] = sprites[11];
		flashingSprites[2] = sprites[8];
		flashingSprites[3] = sprites[9];
		
		Image[] leftEatenSprites = new Image[1];
		leftEatenSprites[0] = sprites[14];
		
		Image[] upEatenSprites = new Image[1];
		upEatenSprites[0] = sprites[12];
		
		Image[] rightEatenSprites = new Image[1];
		rightEatenSprites[0] = sprites[15];
		
		Image[] downEatenSprites = new Image[1];
		downEatenSprites[0] = sprites[13];
		
		leftAnimation = new Animation(leftSprites, SpriteManager.animationFrames);
		rightAnimation = new Animation(rightSprites, SpriteManager.animationFrames);
		upAnimation = new Animation(upSprites, SpriteManager.animationFrames);
		downAnimation = new Animation(downSprites, SpriteManager.animationFrames);
		orbAnimation = new Animation(orbSprites, SpriteManager.animationFrames);
		flashingAnimation = new Animation(flashingSprites, SpriteManager.animationFrames);
		leftEatenAnimation = new Animation(leftEatenSprites, SpriteManager.animationFrames);
		upEatenAnimation = new Animation(upEatenSprites, SpriteManager.animationFrames);
		downEatenAnimation = new Animation(downEatenSprites, SpriteManager.animationFrames);
		rightEatenAnimation = new Animation(rightEatenSprites, SpriteManager.animationFrames);
		
		currentAnimation = upAnimation;
		startCurrentAnimation();
		direction = "up";
		lastDirection = direction;
		
	}
	
	public synchronized void setX(float x){
		position.setX(x);
	}
	
	public synchronized void setY(float y){
		position.setY(y);
	}
	
	public void setPosition(float x, float y){
		position.setX(x);
		position.setY(y);
	}
	
	public synchronized float getX(){
		return position.getX();
	}
	
	public synchronized float getY(){
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
	
	public void setDirection(String direction){
		this.direction = direction;
		updateCurrentAnimation();
	}
	
	public String getDirection(){
		return direction;
	}
	
	public void updateCurrentAnimation(){
		if (direction.equals("left") && (state.equals("chasing") || state.equals("running") || state.equals("starting"))){
			//frame matching
			currentAnimation.stop();
			currentAnimation = leftAnimation;
			currentAnimation.start();
		}
		else if (direction.equals("right") && (state.equals("chasing") || state.equals("running") || state.equals("starting"))){
			currentAnimation.stop();
			currentAnimation = rightAnimation;
			currentAnimation.start();
		}
		else if (direction.equals("up") && (state.equals("chasing") || state.equals("running") || state.equals("starting"))){
			currentAnimation.stop();
			currentAnimation = upAnimation;
			currentAnimation.start();
		}
		else if (direction.equals("down") && (state.equals("chasing") || state.equals("running") || state.equals("starting"))){
			currentAnimation.stop();
			currentAnimation = downAnimation;
			currentAnimation.start();
		}
		else if (state.equals("orb")){
			currentAnimation.stop();
			currentAnimation = orbAnimation;
			currentAnimation.start();
		}
		else if (direction.equals("left") && state.equals("eaten")){
			currentAnimation.stop();
			currentAnimation = leftEatenAnimation;
			currentAnimation.start();
		}
		else if (direction.equals("up") && state.equals("eaten")){
			currentAnimation.stop();
			currentAnimation = upEatenAnimation;
			currentAnimation.start();
		}
		else if (direction.equals("right") && state.equals("eaten")){
			currentAnimation.stop();
			currentAnimation = rightEatenAnimation;
			currentAnimation.start();
		}
		else if (direction.equals("down") && state.equals("eaten")){
			currentAnimation.stop();
			currentAnimation = downEatenAnimation;
			currentAnimation.start();
		}
		else if (state.equals("flashing")){
			currentAnimation.stop();
			currentAnimation = flashingAnimation;
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
		state = "flashing";
		updateCurrentAnimation();
	}
	
	public void setStateStarting(){
		state = "starting";
		updateCurrentAnimation();
	}
	
	public String getState(){
		return this.state;
	}

	public long putToSleep(double offset){
		//the offset multiplies the default sleep time
		if(offset <= 0)
			return restTimer;
		return (long) (offset * restTimer);
	}
	public void sleep(){
		sleeping.set(true);
	}
	public boolean isAsleep(){
		return sleeping.get();
	}
	public void wake(){
		sleeping.set(false);
	}

	@Override
	public synchronized void move() {
		float curX, curY;
		curX = getX();
		curY = getY();
		if (getDirection().equals("left") && !isAsleep()){
			curX -= GameBoard.tileLength/4;
			setX(curX);
		}
		else if (getDirection().equals("right") && !isAsleep()){
			curX += GameBoard.tileLength/4;
			setX(curX);
		}
		else if (getDirection().equals("up") && !isAsleep()){
			curY -= GameBoard.tileLength/4;
			setY(curY);
		}
		else if (getDirection().equals("down") && !isAsleep()){
			curY += GameBoard.tileLength/4;
			setY(curY);
		}
		updateCenter();
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
	
	public void calculatePath(){
		
	}
	
	public AtomicBoolean getPathReady(){
		return pathReady;
	}
	
	public ReentrantLock getLock(){
		return lock;
	}
	
	public Condition getCondition(){
		return condition;
	}
	
	public void await(){
		try {
			condition.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lock(){
		lock.lock();
	}
	
	public void signal(){
		condition.signal();
	}
	
	public void unlock(){
		lock.unlock();
	}
	
	public String getLastDirection(){
		return lastDirection;
	}
	
	public boolean isBlocked(){
		return blocked;
	}
	
	public void setIsBlocked(boolean value){
		blocked = value;
	}
	
	public void setLastDirection(String direction){
		lastDirection = direction;
	}
	public boolean atHome(){
		int x = (int)getX();
		int y = (int)getY();
		if((x > 187 && x < 367) && (y < 346 && y < 226))
			return true;
		else return false;
	}
}
