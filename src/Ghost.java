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
	private boolean gameRunning = true;
	
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
	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean y) {
		gameRunning = y;
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
		if(getState().equals("eaten")){
			move2();
			return;
		}
		else{
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
		}
		updateCenter();
	}
	
	public void move2(){
		System.out.println("move2");
		float curX, curY;
		curX = getX();
		curY = getY();
		String s = eyeRoll(this);
		if (s.equals("") && !isAsleep()){
			return;
		}
		else if (s.equals("left") && !isAsleep()){
			curX -= GameBoard.tileLength/4;
			setX(curX);
		}
		else if (s.equals("right") && !isAsleep()){
			curX += GameBoard.tileLength/4;
			setX(curX);
		}
		else if (s.equals("up") && !isAsleep()){
			curY -= GameBoard.tileLength/4;
			setY(curY);
		}
		else if (s.equals("down") && !isAsleep()){
			curY += GameBoard.tileLength/4;
			setY(curY);
		}
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
	
	public String eyeRoll(Ghost ghost){
		int x = (int)ghost.getX();
		int y = (int)ghost.getY();
		
		String ret = "left";
		if(x < 247){
			ret = "right";
		}
		else if(x > 307 ){
			ret = "left";
		}
		else if(y > 286){
			ret = "up";
		}
		else if(y < 286){
			ret = "down";
		}
		if (x < 27 || x > 547 || y== 286){
			ghost.setX(260);
			ghost.setY(290);
			ghost.setStateChasing();
			ghost.doReset.set(true);
			return "";
		}
		else if (y < 27 || y > 586){
			ghost.setX(260);
			ghost.setY(290);
			ghost.doReset.set(true);
			return "";
		}
		return ret;
	}
		
	
/*	public String eyeRoll(Ghost ghost){
		int x = (int)ghost.getX();
		int y = (int)ghost.getY();

		if(x <= 127 && (y == 26 || y == 106 || y == 166 || y == 406 || y == 526)){
			return ("right");
		}
		else if(x >= 427 && (y == 26 || y == 106 || y == 166 || y == 406 || y == 526)){
			return ("left");
		}
		else if(x > 127 && x < 247){
			return ("left");
		}
		else if( x > 307 && x < 427){
			return ("right");
		}
		else if( y < 106 && (x == 127 || x == 427)){
			return ("up");
		}
		else if( y < 106 && ( x == 247 || x == 307)){
			return ("down");
		}
		else if((y >= 106 && y < 166) && (x == 187 || x == 307)){
			return ("down");
		}
		else if( y == 166 && (x >= 187 && x < 247)){
			return ("right");
		}
		else if( y == 166 && (x > 307 && x <= 367)){
			return ("left");
		}
		else if((x == 307 || x == 247) && (y < 226 && y >= 166)){
			return ("down");
		}
		else if( y == 106 && x <= 127){
			return ("right");
		}
		else if(y == 106 && !(x == 187 || x == 367)){
			return ("left");
		}
		else if( (x == 127 || x == 427) && y <=527){
			return ("up");
		}
		else if(x < 427 && y == 466){
			return ("left");
		}
		else if(y == 586 && x != 27 && x != 527){
			return ("left");
		}
		else if((x == 27 || x == 527) && (y <= 586 && y > 526)){
			return ("up");
		}
		else if( (y <= 406 && y >466) && (x == 466 || x == 406)){
			return ("down");
		}
		else if( y == 406 && (x <= 247 && x > 127)){
			return ("left");
		}
		else if( y  == 406 && (x >= 307 && x < 427)){
			return ("right");
		}
		else if(x == 527 && ( y <= 466 && y > 406)){
			return ("up");
		}
		else if(x == 27 &&  ( y <= 466 && y > 406)){
			return ("up");
		}
		else if((x == 487 || x == 67) && (y < 526 && y > 466)){
			return ("up");
		}
		else if( (x < 67 && x > 27) && y == 466){
			return ("left");
		}
		else if( (x < 527 && x > 487) && y == 466){
			return ("right");
		}
		else if( (x == 307 || x == 247) && (y <586 && y >=526)){
			return ("down");
		}
		else if( y == 526 && (x < 247 && x > 187)){
			return ("left");
		}
		else if( y == 526 && (x > 307 && x < 367)){
			return ("right");
		}
		else if( (x == 367 || x == 187) && (y <=526 && y > 466)){
			return ("up");
		}
		else if( y == 286 && (x > 367 && x < 427)){
			return ("right");
		}
		else if( y == 286 && (x < 187 && x > 127)){
			return ("left");
		}
		else if(y == 346 && (x < 367 && x > 187)){
			return ("left");
		}
		else if(x == 187 || x == 367){
			return ("up");
		}
		else if(y == 226 && (x <=367 && x > 287)){
			return ("left");
		}
		else if(y == 226 && (x >= 187 && x < 287)){
			return ("right");
		}
		else return ("down");
	}
	
*/}
