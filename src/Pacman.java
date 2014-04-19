import java.util.List;

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
	private Vector2Float size;
	private Vector2Float center;
	private AABB box;
	private double speedMod = 1;
	private int killCount = 0;
	
	private static final double FAST = 1.125;
	private static final double DEFAULT = 1;
	private static final double EATING = 0.8875;
	private static final double INFLUENCE_VALUE = 3140000;
	
	public Pacman(Image[] sprites){
		position = new Position(272, 465);
		this.sprites = sprites;
		this.size = new Vector2Float(sprites[0].getWidth()/2, sprites[0].getHeight()/2);
		this.center = new Vector2Float(272,465);
		this.box = new AABB(center,size);
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
	public double getSpeedMod(){
		return speedMod;
	}
	private void setSpeedMod(double speedMod){
		this.speedMod = speedMod;
	}
	public int getKillCount(){
		return killCount;
	}
	public void setKillCount(int kills){
		this.killCount = kills;
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
		GameBoard.removeLife();
		wasStateChange = true;
		updateCurrentAnimation();
		System.out.println("Pacman died :'(");
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
		if(!isDead()){
			if (getDirection().equals("left")){
				curX -= GameBoard.tileLength/4;
				setX(curX);
			}
			else if (getDirection().equals("right")){
				curX += GameBoard.tileLength/4;
				setX(curX);
			}
			else if (getDirection().equals("up")){
				curY -= GameBoard.tileLength/4;
				setY(curY);
			}
			else if (getDirection().equals("down")){
				curY += GameBoard.tileLength/4;
				setY(curY);
			}
		}
		int y = (int) (getX()/20);
		int x = (int) (getY()/20);
		InfluenceMap.getInfluenceMap()[x][y] = Pacman.INFLUENCE_VALUE;
		updateCenter();
	}
	
	public int checkForGhost(List<Entity> entities){
		int points = 0;
		for(int i=1; i < 5; i++){
			Ghost ghost = (Ghost) entities.get(i);
			if( !this.isDead() && (AABB.collides(ghost.getBox(), getBox()) ||
						AABB.inside(getBox(), ghost.getBox().center))){
				switch(ghost.getState()){
						case "chasing"	:	this.setStateDead();
										 	break;
						case "running"	:	this.setStateDead();
						break;
						case "orb"		:	this.killCount = (this.killCount + 1)%4;
											points = (int) (Math.pow(2,killCount)*100);
											ghost.setStateEaten();
											this.setSpeedMod(EATING);
											points += checkForGhost(entities,i+1);
											break;
						case "flashing"	:	this.killCount = (this.killCount + 1)%4;
											points = (int) (Math.pow(2,killCount)*100);
											ghost.setStateEaten();
											this.setSpeedMod(EATING);
											points += checkForGhost(entities,i+1);
											break;
						case "eaten"	:	break;
						default			:	this.setStateDead();
											break;
				}
				return points;
			}
		}
		return points;
	}
	public int checkForGhost(List<Entity> entities, int start){
		int points = 0;
		for(int i=start; i < 5; i++){
			Ghost ghost = (Ghost) entities.get(i);
			if( !this.isDead() && (AABB.collides(ghost.getBox(), getBox()) ||
						AABB.inside(getBox(), ghost.getBox().center))){
				switch(ghost.getState()){
						case "chasing"	:	this.setStateDead();
										 	break;
						case "running"	:	this.setStateDead();
						break;
						case "orb"		:	this.killCount = (this.killCount + 1)%4;
											points = (int) (Math.pow(2,killCount)*100);
											ghost.setStateEaten();
											this.setSpeedMod(EATING);
											points += checkForGhost(entities,i+1);
											break;
						case "flashing"	:	this.killCount = (this.killCount + 1)%4;
											points = (int) (Math.pow(2,killCount)*100);
											ghost.setStateEaten();
											this.setSpeedMod(EATING);
											points += checkForGhost(entities,i+1);
											break;
						case "eaten"	:	break;
						default			:	this.setStateDead();
											break;
				}
				return points;
			}
		}
		return points;
	}
	public int checkForOrb(List<Entity> entities){
		int points = 0;
		for(int i=5; i < entities.size(); i++){
			Orb orb = (Orb) entities.get(i);
			//System.out.println(String.valueOf(orb.getIsLargeOrb())+orb.getIsVisible());
			if( !this.isDead() && (AABB.collides(orb.getBox(), getBox()) ||
						AABB.inside(getBox(), orb.getBox().center))){
				switch(String.valueOf(orb.getIsLargeOrb())+orb.getIsVisible()){
						case "truetrue"		:	points = 50;
												this.setSpeedMod(FAST);
												this.killCount = 0;
												GhostManager.setGhostsOrb();
												orb.setNotVisible();
												entities.remove(i);
												System.out.println("Something large eaten");
												break;
						case "falsetrue"	:	points = 10;
												this.setSpeedMod(DEFAULT);
												orb.setNotVisible();
												entities.remove(i);
												System.out.println("Something small eaten");
												break;
						case "truefalse"	:	this.setSpeedMod(DEFAULT);
												System.out.println("Something large is invisible");
												break;
						case "falsefalse"	:	this.setSpeedMod(DEFAULT);
												System.out.println("Something large is invisible");
												break;
						default				:	this.setSpeedMod(DEFAULT);
												System.out.println("Error: Default switch hit");
												break;
				}
			}
		}
		return points;
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
