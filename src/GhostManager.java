import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


public class GhostManager {
	static Ghost[] ghosts;
	static int orbTimer = 80000;
	private static boolean timerOn = false;
	private InfluenceMapLocksmith influenceMapLocksmith;
	private String[] directions;
	private Random rand = new Random();
	
	public GhostManager(InfluenceMapLocksmith influenceMapLocksmith){
		ghosts = new Ghost[4];
		this.influenceMapLocksmith = influenceMapLocksmith;
		influenceMapLocksmith.addGhosts(ghosts);
		directions = new String[4];
		directions[0] = "up";
		directions[1] = "left";
		directions[2] = "down";
		directions[3] = "right";
		
	}
	
	public void initializeGhosts(SpriteManager sm){
		ghosts[0] = new Ghost(sm.getRedGhostSprites(), "red", 260, 290, "starting");
		ghosts[1] = new Ghost(sm.getPinkGhostSprites(), "pink", 260, 310, "starting");
		ghosts[2] = new Ghost(sm.getBlueGhostSprites(), "blue", 280, 310, "starting");
		ghosts[3] = new Ghost(sm.getYellowGhostSprites(), "yellow", 280, 290, "starting");
	}
	
/*==================================================*
 * 													*
 *              Get&Set for all ghosts              *
 *													*
 *==================================================*/	

	
	public Thread getRedLogic(){
		return redLogic;
	}
	public Thread getPinkLogic(){
		return pinkLogic;
	}
	public Thread getBlueLogic(){
		return blueLogic;
	}
	public Thread getYellowLogic(){
		return yellowLogic;
	}
	
	public static Ghost[] getGhosts(){
		return ghosts;
	}
	
	public static Ghost getRedGhost(){
		return ghosts[0];
	}
	
	public static Ghost getPinkGhost(){
		return ghosts[1];
	}
	
	public static Ghost getBlueGhost(){
		return ghosts[2];
	}
	
	public static Ghost getYellowGhost(){
		return ghosts[3];
	}
	
	public InfluenceMapLocksmith getInfluenceMapLocksmith(){
		return influenceMapLocksmith;
	}
	
	public void renderGhosts(){
		for (Ghost ghost : ghosts){
			ghost.drawCurrentAnimation();
		}
	}
	public static void setGhostsChasing(){
		for (Ghost ghost : ghosts)
			if(!ghost.getState().equals("eaten"))
				ghost.setStateChasing();
		orbTimer = 80000;
		timerOn = false;
	}
	public static void setGhostsFlashing(){
		for (Ghost ghost : ghosts)
			if(ghost.getState().equals("orb"))
				ghost.setStateFlashing();
	}
	public static void setGhostsOrb(){
		for (Ghost ghost : ghosts)
			if(!ghost.getState().equals("eaten") && !ghost.getState().equals("starting"))
				ghost.setStateOrb();
		orbTimer = 225;
		timerOn = true;
	}
	public static void resetGhostPositions(){
		getRedGhost().setStateStarting();
		getBlueGhost().setStateStarting();
		getYellowGhost().setStateStarting();
		getPinkGhost().setStateStarting();
		getRedGhost().sleep();
		getRedGhost().doReset.set(true);
		getBlueGhost().sleep();
		getBlueGhost().doReset.set(true);
		getYellowGhost().sleep();
		getYellowGhost().doReset.set(true);
		getPinkGhost().sleep();
		getPinkGhost().doReset.set(true);
		getBlueGhost().setIsBlocked(false);
		getRedGhost().setIsBlocked(false);
		getPinkGhost().setIsBlocked(false);
		getYellowGhost().setIsBlocked(false);
		
		//setGhostsChasing();
		
		getRedGhost().setX(260);
		getRedGhost().setY(290);
		
		getRedGhost().updateCenter();
		getPinkGhost().setX(260);
		getPinkGhost().setY(310);
		getPinkGhost().updateCenter();
		
		getBlueGhost().setX(280);
		getBlueGhost().setY(310);
		
		getBlueGhost().updateCenter();
		getYellowGhost().setX(280);
		getYellowGhost().setY(290);
		
		getYellowGhost().updateCenter();
		getRedGhost().wake();
		
		
	}
	public void resetOrbTimer(){
		this.orbTimer = 0;
	}
	public void decrementOrbTimer(){
		if(timerOn)
			orbTimer--;
	}
	public int getOrbTimer(){
		return orbTimer;
	}
	public void turnOnTimer(){
		this.timerOn = true;
	}
	public boolean isTimerOn(){
		return timerOn;
	}

	
/*==================================================*
 * 													*
 *              	Thread Setup	                *
 *													*
 *==================================================*/
	
	
	Thread redLogic = new Thread() {
		public void run() {
	        Ghost red = getRedGhost();
	        if(red.isAsleep())
				try {
					Thread.sleep(red.putToSleep(0));
					red.wake();
					List<String> validStrings = new ArrayList<String>();
					
					while(true){
						while (red.isAsleep()){
							Thread.sleep(red.putToSleep(0));
							
						}
						
						getPinkGhost().wake();
						
						if (red.doReset.get() == true){
							if (red.isBlocked()){
								red.setPosition(262.0f, 226.0f);
								System.out.println("position: " + red.getX() + ", " + red.getY());
								red.setIsBlocked(false);
								red.doReset.set(false);
								red.setStateChasing();
								//Thread.sleep(red.putToSleep(2));
								//red.doReset.set(false);
							}
							else{
							//if (red.getX() == 187 && red.getY() == 225){
								//break;
							//}
								red.lock();
								red.setDirectionUp();
								red.getPathReady().set(true);
								red.await();
							}
						}
						else{
							if (red.isBlocked()){
								//System.out.println("position: " + red.getX() + ", " + red.getY());
								System.out.println("LastDirection = " + red.getLastDirection());
								if (red.getLastDirection().equals("up")){
									red.setPosition(red.getX(), red.getY() - 5);
								}
								else if (red.getLastDirection().equals("down")){
									red.setPosition(red.getX(), red.getY() + 5);
								}
								else if (red.getLastDirection().equals("left")){
									red.setPosition(red.getX() - 5, red.getY());
								}
								else if (red.getLastDirection().equals("right")){
									red.setPosition(red.getX() + 5, red.getY());
									
								}
								red.setIsBlocked(false);
							}
							if (red.getState().equals("chasing") || red.getState().equals("running")){
								Position lookAheadMove = red.virtualMove();
								if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
									choosePath(red);
								}
								red.directionSwitched = false;
							}
							else if (red.getState().equals("orb") || red.getState().equals("flashing")){
								//String tempLastDirection = red.getLastDirection();
								//choosePath(red);
								//red.setLastDirection(tempLastDirection);
								
								float oldX, oldY;
								oldX = red.getX();
								oldY = red.getY();
								
								if (red.directionSwitched == false){
									if (red.getDirection().equals("up")){
										red.setLastDirection(red.getDirection());
										red.setDirectionDown();
									}
									else if (red.getDirection().equals("down")){
										red.setDirectionUp();
										red.setLastDirection(red.getDirection());
									}
									else if (red.getDirection().equals("left")){
										red.setDirectionRight();
										red.setLastDirection(red.getDirection());
									}
									else{
										red.setDirectionLeft();
										red.setLastDirection(red.getDirection());
									}
									
									red.directionSwitched = true;
								}
								
								red.move();
								if (!GameBoard.isBlocked(red)){
									
									
									//if original path is ok, maybe switch at a junction
									red.setPosition(oldX, oldY);
									
									
									String originalOKDirection = red.getDirection();
									List<String> directions = new ArrayList<String>();
									int index = 0;
									if (!red.getDirection().equals("up")){
										directions.add("up");
										index++;
									}
									if (!red.getDirection().equals("left")){
										directions.add("left");
										index++;
									}
									if (!red.getDirection().equals("down")){
										directions.add("down");
										index++;
									}
									if (!red.getDirection().equals("right")){
										directions.add("right");
										index++;
									}
									
									String newDirection;
									boolean foundGoodDirection = false;
									while (directions.size() > 0){
										int randomNum = rand.nextInt(directions.size());
										newDirection = directions.get(randomNum);
										directions.remove(randomNum);
										
										red.setDirection(newDirection);
										red.move();
										
										if (!GameBoard.isBlocked(red) && !newDirection.equals(red.getLastDirection())){
											//test other directions
											
											if ((red.getDirection().equals("down") && originalOKDirection.equals("up")) ||
													(red.getDirection().equals("up") && originalOKDirection.equals("down")) ||
															(red.getDirection().equals("left") && originalOKDirection.equals("right")) ||
															(red.getDirection().equals("down") && originalOKDirection.equals("up"))){
												
											}
											else{
												red.setPosition(oldX, oldY);
												red.setLastDirection(newDirection);
												foundGoodDirection = true;
												break;
											}
										}
										
										red.setPosition(oldX, oldY);
										
									}
									if (foundGoodDirection == false){
										red.setDirection(originalOKDirection);
									}
									
								}
								else{
									String newDirection;
									//red.directionSwitched = false;
									//randomize direction
									List<String> directions = new ArrayList<String>();
									int index = 0;
									if (!red.getDirection().equals("up")){
										directions.add("up");
										index++;
									}
									if (!red.getDirection().equals("left")){
										directions.add("left");
										index++;
									}
									if (!red.getDirection().equals("down")){
										directions.add("down");
										index++;
									}
									if (!red.getDirection().equals("right")){
										directions.add("right");
										index++;
									}
									
									int randomNum = rand.nextInt(3);
									newDirection = directions.get(randomNum);
									
									red.setDirection(newDirection);
									red.move();
									if (GameBoard.isBlocked(red)){
										red.setPosition(oldX, oldY);
										directions.remove(randomNum);
										
										randomNum = rand.nextInt(2);
										newDirection = directions.get(randomNum);
										red.setDirection(newDirection);
										red.move();
										
										if (GameBoard.isBlocked(red)){
											red.setPosition(oldX, oldY);
											directions.remove(randomNum);
											
											randomNum = rand.nextInt(1);
											newDirection = directions.get(randomNum);
											red.setDirection(newDirection);
											red.move();
											
											if (GameBoard.isBlocked(red)){
												red.setPosition(oldX, oldY);
												
											}
										}									
									}
								
									red.setPosition(oldX, oldY);
								
								}
								red.setLastDirection(red.getDirection());
								
							}
							
							//synchronized(red.getCondition()){
		
									//System.out.println("red awaiting...");
									red.lock();
									red.getPathReady().set(true);
									//System.out.println("red Condition Hashcode: " + red.getCondition().hashCode());
									red.await();
									//System.out.println("red awake...");
	
							//}
						}
					}
						
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	finally{
	        		red.unlock();
	        	}
	        System.out.println("redy will survive");
	    }  
	};
	Thread pinkLogic = new Thread() {
		public void run() {
	        Ghost pink = getPinkGhost();
	        if(pink.isAsleep())
	        	try {
					Thread.sleep(pink.putToSleep(10));
					pink.wake();
					List<String> validStrings = new ArrayList<String>();
					
					while(true){
						while (pink.isAsleep()){
							Thread.sleep(pink.putToSleep(10));
							
						}
						
						getBlueGhost().wake();
						
						if (pink.doReset.get() == true){
							if (pink.isBlocked()){
								pink.setPosition(262.0f, 226.0f);
								System.out.println("position: " + pink.getX() + ", " + pink.getY());
								pink.setIsBlocked(false);
								pink.doReset.set(false);
								pink.setStateChasing();
								//pink.doReset.set(false);
							}
							else{
							//if (pink.getX() == 187 && pink.getY() == 225){
								//break;
							//}
								pink.lock();
								pink.setDirectionUp();
								pink.getPathReady().set(true);
								pink.await();
							}
						}
						else{
							if (pink.isBlocked()){
								//System.out.println("position: " + pink.getX() + ", " + pink.getY());
								System.out.println("LastDirection = " + pink.getLastDirection());
								if (pink.getLastDirection().equals("up")){
									pink.setPosition(pink.getX(), pink.getY() - 5);
								}
								else if (pink.getLastDirection().equals("down")){
									pink.setPosition(pink.getX(), pink.getY() + 5);
								}
								else if (pink.getLastDirection().equals("left")){
									pink.setPosition(pink.getX() - 5, pink.getY());
								}
								else if (pink.getLastDirection().equals("right")){
									pink.setPosition(pink.getX() + 5, pink.getY());
									
								}
								pink.setIsBlocked(false);
							}
							if (pink.getState().equals("chasing") || pink.getState().equals("running")){
								Position lookAheadMove = pink.virtualMove();
								if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
									choosePath(pink);
								}
								pink.directionSwitched = false;
							}
							else if (pink.getState().equals("orb") || pink.getState().equals("flashing")){
								//String tempLastDirection = pink.getLastDirection();
								//choosePath(pink);
								//pink.setLastDirection(tempLastDirection);
								
								float oldX, oldY;
								oldX = pink.getX();
								oldY = pink.getY();
								
								if (pink.directionSwitched == false){
									if (pink.getDirection().equals("up")){
										pink.setLastDirection(pink.getDirection());
										pink.setDirectionDown();
									}
									else if (pink.getDirection().equals("down")){
										pink.setLastDirection(pink.getDirection());
										pink.setDirectionUp();
									}
									else if (pink.getDirection().equals("left")){
										pink.setLastDirection(pink.getDirection());
										pink.setDirectionRight();
									}
									else{
										pink.setLastDirection(pink.getDirection());
										pink.setDirectionLeft();
									}
									
									pink.directionSwitched = true;
								}
								
								pink.move();
								if (!GameBoard.isBlocked(pink)){
									
									
									//if original path is ok, maybe switch at a junction
									pink.setPosition(oldX, oldY);
									
									
									String originalOKDirection = pink.getDirection();
									List<String> directions = new ArrayList<String>();
									int index = 0;
									if (!pink.getDirection().equals("up")){
										directions.add("up");
										index++;
									}
									if (!pink.getDirection().equals("left")){
										directions.add("left");
										index++;
									}
									if (!pink.getDirection().equals("down")){
										directions.add("down");
										index++;
									}
									if (!pink.getDirection().equals("right")){
										directions.add("right");
										index++;
									}
									
									String newDirection;
									boolean foundGoodDirection = false;
									while (directions.size() > 0){
										int randomNum = rand.nextInt(directions.size());
										newDirection = directions.get(randomNum);
										directions.remove(randomNum);
										
										pink.setDirection(newDirection);
										pink.move();
										
										if (!GameBoard.isBlocked(pink) && !newDirection.equals(pink.getLastDirection())){
											//test other directions
											
											if ((pink.getDirection().equals("down") && originalOKDirection.equals("up")) ||
													(pink.getDirection().equals("up") && originalOKDirection.equals("down")) ||
															(pink.getDirection().equals("left") && originalOKDirection.equals("right")) ||
															(pink.getDirection().equals("down") && originalOKDirection.equals("up"))){
												
											}
											else{
												pink.setPosition(oldX, oldY);
												pink.setLastDirection(newDirection);
												foundGoodDirection = true;
												break;
											}
										}
										
										pink.setPosition(oldX, oldY);
										
									}
									if (foundGoodDirection == false){
										pink.setDirection(originalOKDirection);
									}
									
								}
								else{
									String newDirection;
									//pink.directionSwitched = false;
									//randomize direction
									List<String> directions = new ArrayList<String>();
									int index = 0;
									if (!pink.getDirection().equals("up")){
										directions.add("up");
										index++;
									}
									if (!pink.getDirection().equals("left")){
										directions.add("left");
										index++;
									}
									if (!pink.getDirection().equals("down")){
										directions.add("down");
										index++;
									}
									if (!pink.getDirection().equals("right")){
										directions.add("right");
										index++;
									}
									
									int randomNum = rand.nextInt(3);
									newDirection = directions.get(randomNum);
									
									pink.setDirection(newDirection);
									pink.move();
									if (GameBoard.isBlocked(pink)){
										pink.setPosition(oldX, oldY);
										directions.remove(randomNum);
										
										randomNum = rand.nextInt(2);
										newDirection = directions.get(randomNum);
										pink.setDirection(newDirection);
										pink.move();
										
										if (GameBoard.isBlocked(pink)){
											pink.setPosition(oldX, oldY);
											directions.remove(randomNum);
											
											randomNum = rand.nextInt(1);
											newDirection = directions.get(randomNum);
											pink.setDirection(newDirection);
											pink.move();
											
											if (GameBoard.isBlocked(pink)){
												pink.setPosition(oldX, oldY);
												
											}
										}									
									}
								
									pink.setPosition(oldX, oldY);
								
								}
								pink.setLastDirection(pink.getDirection());
								
							}
							
							//synchronized(pink.getCondition()){
		
									//System.out.println("pink awaiting...");
									pink.lock();
									pink.getPathReady().set(true);
									//System.out.println("pink Condition Hashcode: " + pink.getCondition().hashCode());
									pink.await();
									//System.out.println("pink awake...");
	
							//}
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	finally{
	        		pink.unlock();
	        	}
	        System.out.println("Pinky will survive");
	    }  
	};
	Thread blueLogic = new Thread() {
		public void run() {
	        Ghost blue = getBlueGhost();
	        if(blue.isAsleep())
	        	try {
					Thread.sleep(blue.putToSleep(20));
					blue.wake();
					List<String> validStrings = new ArrayList<String>();
					
					while(true){
						while (blue.isAsleep()){
							Thread.sleep(blue.putToSleep(10));
							
						}
						
						getYellowGhost().wake();
						
						if (blue.doReset.get() == true){
							if (blue.isBlocked()){
								blue.setPosition(282.0f, 226.0f);
								System.out.println("position: " + blue.getX() + ", " + blue.getY());
								blue.setIsBlocked(false);
								blue.doReset.set(false);
								blue.setStateChasing();
								//blue.doReset.set(false);
							}
							else{
							//if (blue.getX() == 187 && blue.getY() == 225){
								//break;
							//}
								blue.lock();
								blue.setDirectionUp();
								blue.getPathReady().set(true);
								blue.await();
							}
						}
						else{
							if (blue.isBlocked()){
								//System.out.println("position: " + blue.getX() + ", " + blue.getY());
								System.out.println("LastDirection = " + blue.getLastDirection());
								if (blue.getLastDirection().equals("up")){
									blue.setPosition(blue.getX(), blue.getY() - 5);
								}
								else if (blue.getLastDirection().equals("down")){
									blue.setPosition(blue.getX(), blue.getY() + 5);
								}
								else if (blue.getLastDirection().equals("left")){
									blue.setPosition(blue.getX() - 5, blue.getY());
								}
								else if (blue.getLastDirection().equals("right")){
									blue.setPosition(blue.getX() + 5, blue.getY());
									
								}
								blue.setIsBlocked(false);
							}
							if (blue.getState().equals("chasing") || blue.getState().equals("running")){
								Position lookAheadMove = blue.virtualMove();
								if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
									choosePath(blue);
								}
								blue.directionSwitched = false;
							}
							else if (blue.getState().equals("orb") || blue.getState().equals("flashing")){
								//String tempLastDirection = blue.getLastDirection();
								//choosePath(blue);
								//blue.setLastDirection(tempLastDirection);
								
								float oldX, oldY;
								oldX = blue.getX();
								oldY = blue.getY();
								
								if (blue.directionSwitched == false){
									if (blue.getDirection().equals("up")){
										blue.setLastDirection(blue.getDirection());
										blue.setDirectionDown();
									}
									else if (blue.getDirection().equals("down")){
										blue.setLastDirection(blue.getDirection());
										blue.setDirectionUp();
									}
									else if (blue.getDirection().equals("left")){
										blue.setLastDirection(blue.getDirection());
										blue.setDirectionRight();
									}
									else{
										blue.setLastDirection(blue.getDirection());
										blue.setDirectionLeft();
									}
									
									blue.directionSwitched = true;
								}
								
								blue.move();
								if (!GameBoard.isBlocked(blue)){
									
									
									//if original path is ok, maybe switch at a junction
									blue.setPosition(oldX, oldY);
									
									
									String originalOKDirection = blue.getDirection();
									List<String> directions = new ArrayList<String>();
									int index = 0;
									if (!blue.getDirection().equals("up")){
										directions.add("up");
										index++;
									}
									if (!blue.getDirection().equals("left")){
										directions.add("left");
										index++;
									}
									if (!blue.getDirection().equals("down")){
										directions.add("down");
										index++;
									}
									if (!blue.getDirection().equals("right")){
										directions.add("right");
										index++;
									}
									
									String newDirection;
									boolean foundGoodDirection = false;
									while (directions.size() > 0){
										int randomNum = rand.nextInt(directions.size());
										newDirection = directions.get(randomNum);
										directions.remove(randomNum);
										
										blue.setDirection(newDirection);
										blue.move();
										
										if (!GameBoard.isBlocked(blue) && !newDirection.equals(blue.getLastDirection())){
											//test other directions
											
											if ((blue.getDirection().equals("down") && originalOKDirection.equals("up")) ||
													(blue.getDirection().equals("up") && originalOKDirection.equals("down")) ||
															(blue.getDirection().equals("left") && originalOKDirection.equals("right")) ||
															(blue.getDirection().equals("down") && originalOKDirection.equals("up"))){
												
											}
											else{
												blue.setPosition(oldX, oldY);
												blue.setLastDirection(newDirection);
												foundGoodDirection = true;
												break;
											}
										}
										
										blue.setPosition(oldX, oldY);
										
									}
									if (foundGoodDirection == false){
										blue.setDirection(originalOKDirection);
									}
									
								}
								else{
									String newDirection;
									//blue.directionSwitched = false;
									//randomize direction
									List<String> directions = new ArrayList<String>();
									int index = 0;
									if (!blue.getDirection().equals("up")){
										directions.add("up");
										index++;
									}
									if (!blue.getDirection().equals("left")){
										directions.add("left");
										index++;
									}
									if (!blue.getDirection().equals("down")){
										directions.add("down");
										index++;
									}
									if (!blue.getDirection().equals("right")){
										directions.add("right");
										index++;
									}
									
									int randomNum = rand.nextInt(3);
									newDirection = directions.get(randomNum);
									
									blue.setDirection(newDirection);
									blue.move();
									if (GameBoard.isBlocked(blue)){
										blue.setPosition(oldX, oldY);
										directions.remove(randomNum);
										
										randomNum = rand.nextInt(2);
										newDirection = directions.get(randomNum);
										blue.setDirection(newDirection);
										blue.move();
										
										if (GameBoard.isBlocked(blue)){
											blue.setPosition(oldX, oldY);
											directions.remove(randomNum);
											
											randomNum = rand.nextInt(1);
											newDirection = directions.get(randomNum);
											blue.setDirection(newDirection);
											blue.move();
											
											if (GameBoard.isBlocked(blue)){
												blue.setPosition(oldX, oldY);
												
											}
										}									
									}
								
									blue.setPosition(oldX, oldY);
								
								}
								blue.setLastDirection(blue.getDirection());
								
							}
							
							//synchronized(blue.getCondition()){
		
									//System.out.println("blue awaiting...");
									blue.lock();
									blue.getPathReady().set(true);
									//System.out.println("blue Condition Hashcode: " + blue.getCondition().hashCode());
									blue.await();
									//System.out.println("blue awake...");
	
							//}
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	finally{
	        		blue.unlock();
	        	}
	        System.out.println("bluey will survive");
	    }  
	};
	Thread yellowLogic = new Thread() {
		public void run() {
	        Ghost yellow = getYellowGhost();
	        if(yellow.isAsleep())
	        	try {
					Thread.sleep(yellow.putToSleep(30));
					yellow.wake();
					List<String> validStrings = new ArrayList<String>();
					
					while(true){
						while (yellow.isAsleep()){
							Thread.sleep(yellow.putToSleep(10));
							
						}
						
						if (yellow.doReset.get() == true){
							if (yellow.isBlocked()){
								yellow.setPosition(282.0f, 226.0f);
								System.out.println("position: " + yellow.getX() + ", " + yellow.getY());
								yellow.setIsBlocked(false);
								yellow.doReset.set(false);
								yellow.setStateChasing();
								//yellow.doReset.set(false);
							}
							else{
							//if (yellow.getX() == 187 && yellow.getY() == 225){
								//break;
							//}
								yellow.lock();
								yellow.setDirectionUp();
								yellow.getPathReady().set(true);
								yellow.await();
							}
						}
						else{
							if (yellow.isBlocked()){
								//System.out.println("position: " + yellow.getX() + ", " + yellow.getY());
								System.out.println("LastDirection = " + yellow.getLastDirection());
								if (yellow.getLastDirection().equals("up")){
									yellow.setPosition(yellow.getX(), yellow.getY() - 5);
								}
								else if (yellow.getLastDirection().equals("down")){
									yellow.setPosition(yellow.getX(), yellow.getY() + 5);
								}
								else if (yellow.getLastDirection().equals("left")){
									yellow.setPosition(yellow.getX() - 5, yellow.getY());
								}
								else if (yellow.getLastDirection().equals("right")){
									yellow.setPosition(yellow.getX() + 5, yellow.getY());
									
								}
								yellow.setIsBlocked(false);
							}
							if (yellow.getState().equals("chasing") || yellow.getState().equals("running")){
								int numCorridors = 0;
								numCorridors = GameBoard.getJunctionCount(yellow.getX(), yellow.getY());
								if (numCorridors >= 3){
									//choose direction at random
									//String direction = GameBoard.getRandomJunctionDirection(yellow.getX(), yellow.getY(), numCorridors);
									choosePath(yellow);
									//yellow.setDirection(direction);
								}
								else{
									Position lookAheadMove = yellow.virtualMove();
									if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
										choosePath(yellow);
									}
								}
								
								//helper for orb mode
								yellow.directionSwitched = false;
							}
							else if (yellow.getState().equals("orb") || yellow.getState().equals("flashing")){
								//String tempLastDirection = yellow.getLastDirection();
								//choosePath(yellow);
								//yellow.setLastDirection(tempLastDirection);
								
								float oldX, oldY;
								oldX = yellow.getX();
								oldY = yellow.getY();
								
								if (yellow.directionSwitched == false){
									if (yellow.getDirection().equals("up")){
										yellow.setLastDirection(yellow.getDirection());
										yellow.setDirectionDown();
									}
									else if (yellow.getDirection().equals("down")){
										yellow.setLastDirection(yellow.getDirection());
										yellow.setDirectionUp();
									}
									else if (yellow.getDirection().equals("left")){
										yellow.setLastDirection(yellow.getDirection());
										yellow.setDirectionRight();
									}
									else{
										yellow.setLastDirection(yellow.getDirection());
										yellow.setDirectionLeft();
									}
									
									yellow.directionSwitched = true;
								}
								
								yellow.move();
								if (!GameBoard.isBlocked(yellow)){
									
									
									//if original path is ok, maybe switch at a junction
									yellow.setPosition(oldX, oldY);
									
									
									String originalOKDirection = yellow.getDirection();
									List<String> directions = new ArrayList<String>();
									int index = 0;
									if (!yellow.getDirection().equals("up")){
										directions.add("up");
										index++;
									}
									if (!yellow.getDirection().equals("left")){
										directions.add("left");
										index++;
									}
									if (!yellow.getDirection().equals("down")){
										directions.add("down");
										index++;
									}
									if (!yellow.getDirection().equals("right")){
										directions.add("right");
										index++;
									}
									
									String newDirection;
									boolean foundGoodDirection = false;
									while (directions.size() > 0){
										int randomNum = rand.nextInt(directions.size());
										newDirection = directions.get(randomNum);
										directions.remove(randomNum);
										
										yellow.setDirection(newDirection);
										yellow.move();
										
										if (!GameBoard.isBlocked(yellow) && !newDirection.equals(yellow.getLastDirection())){
											//test other directions
											
											if ((yellow.getDirection().equals("down") && originalOKDirection.equals("up")) ||
													(yellow.getDirection().equals("up") && originalOKDirection.equals("down")) ||
															(yellow.getDirection().equals("left") && originalOKDirection.equals("right")) ||
															(yellow.getDirection().equals("down") && originalOKDirection.equals("up"))){
												
											}
											else{
												yellow.setPosition(oldX, oldY);
												yellow.setLastDirection(newDirection);
												foundGoodDirection = true;
												break;
											}
										}
										
										yellow.setPosition(oldX, oldY);
										
									}
									if (foundGoodDirection == false){
										yellow.setDirection(originalOKDirection);
									}
									
								}
								else{
									String newDirection;
									//yellow.directionSwitched = false;
									//randomize direction
									List<String> directions = new ArrayList<String>();
									int index = 0;
									if (!yellow.getDirection().equals("up")){
										directions.add("up");
										index++;
									}
									if (!yellow.getDirection().equals("left")){
										directions.add("left");
										index++;
									}
									if (!yellow.getDirection().equals("down")){
										directions.add("down");
										index++;
									}
									if (!yellow.getDirection().equals("right")){
										directions.add("right");
										index++;
									}
									
									int randomNum = rand.nextInt(3);
									newDirection = directions.get(randomNum);
									
									yellow.setDirection(newDirection);
									yellow.move();
									if (GameBoard.isBlocked(yellow)){
										yellow.setPosition(oldX, oldY);
										directions.remove(randomNum);
										
										randomNum = rand.nextInt(2);
										newDirection = directions.get(randomNum);
										yellow.setDirection(newDirection);
										yellow.move();
										
										if (GameBoard.isBlocked(yellow)){
											yellow.setPosition(oldX, oldY);
											directions.remove(randomNum);
											
											randomNum = rand.nextInt(1);
											newDirection = directions.get(randomNum);
											yellow.setDirection(newDirection);
											yellow.move();
											
											if (GameBoard.isBlocked(yellow)){
												yellow.setPosition(oldX, oldY);
												
											}
										}									
									}
								
									yellow.setPosition(oldX, oldY);
								
								}
								yellow.setLastDirection(yellow.getDirection());
								
							}
							
							//synchronized(yellow.getCondition()){
		
									//System.out.println("yellow awaiting...");
									yellow.lock();
									yellow.getPathReady().set(true);
									//System.out.println("yellow Condition Hashcode: " + yellow.getCondition().hashCode());
									yellow.await();
									//System.out.println("yellow awake...");
	
							//}
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	finally{
	        		yellow.unlock();
	        	}
	        System.out.println("yellowy will survive");
	    }  
	};
	
	
/*==================================================*
 * 													*
 *              	Pathing Logic	                *
 *													*
 *==================================================*/
	
	public void choosePath(Ghost ghost){
		
		//get current position
		  		int y = (int) (ghost.getX()/20);
		  		int x = (int) (ghost.getY()/20);
		  		
		  		//System.out.println("Choosing path for ghost " + ghost.getType());
		  		//get the direction influences
		  		
		  		
		  		float left = influenceMapLocksmith.getInfluenceMap()[x][y -  1];
		  		float right = influenceMapLocksmith.getInfluenceMap()[x][y + 1];
		  		float up = influenceMapLocksmith.getInfluenceMap()[x -  1][y];
		  		float down = influenceMapLocksmith.getInfluenceMap()[x + 1][y];
		  		
		  		//get highest weight with bias towards up,right,left,down
		  		if( up >= right &&
		  			up >= left &&
		  			up >= down){
		  				if (!ghost.getDirection().equals("up")){
		  					ghost.setLastDirection(ghost.getDirection());
		  					System.out.println("Setting last direction " + ghost.getLastDirection());
		  				}
		  				ghost.setDirectionUp();
		  				
		  		}
		  		else if( right >= left &&
		  				 right >= up &&
		  				 right >= down){
		  				if (!ghost.getDirection().equals("right")){
		  					
		  					ghost.setLastDirection(ghost.getDirection());
		  					System.out.println("Setting last direction " + ghost.getLastDirection());
		  				}
		  					ghost.setDirectionRight();
		  		}
		  		else if( left >= right &&
		  				 left >= up &&
		  				 left >= down){
		  					//System.out.println("left = " + left + "(x, y) = (" + x + ", " + y + ")");
		  				if (!ghost.getDirection().equals("left")){
		  					ghost.setLastDirection(ghost.getDirection());
		  					System.out.println("Setting last direction " + ghost.getLastDirection());
		  				}
		  					ghost.setDirectionLeft();
		  		}
		  		else if( down >= right &&
		  				 down >= left &&
		  				 down >= up){
		  				if (!ghost.getDirection().equals("down")){
		  					ghost.setLastDirection(ghost.getDirection());
		  					System.out.println("Setting last direction " + ghost.getLastDirection());
		  				}
		  					ghost.setDirectionDown();
		  		}
		  		
		  			//ghost.getPathReady().set(true);
		  			//System.out.println(ghost.getType() + " pathReady True");
		  			
		  		
		  	}
	
		public  String chooseOrbPath(Ghost ghost, List<String> validStrings){
			return null;
		}
		
		public String getOrbDirection(Ghost ghost){
			int y = (int) (ghost.getX()/20);
	  		int x = (int) (ghost.getY()/20);
			
			float left = influenceMapLocksmith.getInfluenceMap()[x][y -  1];
	  		float right = influenceMapLocksmith.getInfluenceMap()[x][y + 1];
	  		float up = influenceMapLocksmith.getInfluenceMap()[x -  1][y];
	  		float down = influenceMapLocksmith.getInfluenceMap()[x + 1][y];
	  		
	  		//get highest weight with bias towards up,right,left,down
	  		if( up <= right &&
	  			up <= left &&
	  			up <= down){
	  				
	  				return "up";
	  				
	  		}
	  		else if( down <= right &&
	  				 down <= left &&
	  				 down <= up){
	  				
	  					return "down";
	  		}
	  		else if( right <= left &&
	  				 right <= up &&
	  				 right <= down){
	  				
	  					return "right";
	  		}
	  		else if( left <= right &&
	  				 left <= up &&
	  				 left <= down){
	  					//System.out.println("left = " + left + "(x, y) = (" + x + ", " + y + ")");
	  				
	  					return "left";
	  		}
	  		
	  		
			return null;
		}
	}
	
