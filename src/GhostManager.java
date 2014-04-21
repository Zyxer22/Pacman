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
								red.setDirectionLeft();
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
								int numCorridors = 0;
								numCorridors = GameBoard.getJunctionCount(red.getX(), red.getY());
								if (numCorridors >= 3){
									
									
									//if (GameBoard.rand.nextInt(100) <= 98){
										String direction = pathDirectionLookAhead(red);
										if (GameBoard.directionIsOpposite(direction, red.getDirection())){
											String curDirection = red.getDirection();
											direction = GameBoard.getRandomJunctionDirection(red.getX(), red.getY(), numCorridors, curDirection);
											red.setDirection(direction);
										}
										else{
											choosePath(red);
										}
										
									//}
									
									
									
									//yellow.setDirection(direction);
								}
								else{
									Position lookAheadMove = red.virtualMove();
									if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
										choosePath(red);
									}
								}
								
								//helper for orb mode
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
								//////////////////////////////////////////
								int numCorridors = 0;
								numCorridors = GameBoard.getJunctionCount(red.getX(), red.getY());
								if (numCorridors >= 3){
									
									
									//if (GameBoard.rand.nextInt(100) <= 98){
										String direction = getOrbDirection(red);
										if (GameBoard.directionIsOpposite(direction, red.getDirection())){
											String curDirection = red.getDirection();
											direction = GameBoard.getRandomJunctionDirection(red.getX(), red.getY(), numCorridors, curDirection);
											red.setDirection(direction);
										}
										else{
											red.setDirection(direction);
										}
										
									//}
									
									
									
									//yellow.setDirection(direction);
								}
								else{
									Position lookAheadMove = red.virtualMove();
									if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
										String direction = getOrbDirection(red);
										red.setDirection(direction);
									}
								}
								
								//red.directionSwitched = false;
								//////////////////////////////////////////
								
				
								
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
								pink.setDirectionRight();
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
								int numCorridors = 0;
								numCorridors = GameBoard.getJunctionCount(pink.getX(), pink.getY());
								if (numCorridors >= 3){
									if (GameBoard.rand.nextInt(100) <= 89){
										String direction = pathDirectionLookAhead(pink);
										if (GameBoard.directionIsOpposite(direction, pink.getDirection())){
											String curDirection = pink.getDirection();
											direction = GameBoard.getRandomJunctionDirection(pink.getX(), pink.getY(), numCorridors, curDirection);
											pink.setDirection(direction);
										}
										else{
											choosePath(pink);
										}
									}
									else{
									//choose direction at random
										String curDirection = pink.getDirection();
										String direction = GameBoard.getRandomJunctionDirection(pink.getX(), pink.getY(), numCorridors, curDirection);
										pink.setDirection(direction);
									
									}
								}
								else{
									Position lookAheadMove = pink.virtualMove();
									if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
										choosePath(pink);
									}
								}
								
								//helper for orb mode
								pink.directionSwitched = false;
							}
							else if (pink.getState().equals("orb") || pink.getState().equals("flashing")){
								//String tempLastDirection = red.getLastDirection();
								//choosePath(red);
								//red.setLastDirection(tempLastDirection);
								
								float oldX, oldY;
								oldX = pink.getX();
								oldY = pink.getY();
								
								if (pink.directionSwitched == false){
									if (pink.getDirection().equals("up")){
										pink.setLastDirection(pink.getDirection());
										pink.setDirectionDown();
									}
									else if (pink.getDirection().equals("down")){
										pink.setDirectionUp();
										pink.setLastDirection(pink.getDirection());
									}
									else if (pink.getDirection().equals("left")){
										pink.setDirectionRight();
										pink.setLastDirection(pink.getDirection());
									}
									else{
										pink.setDirectionLeft();
										pink.setLastDirection(pink.getDirection());
									}
									
									pink.directionSwitched = true;
								}
								//////////////////////////////////////////
								int numCorridors = 0;
								numCorridors = GameBoard.getJunctionCount(pink.getX(), pink.getY());
								if (numCorridors >= 3){
									
									
									//if (GameBoard.rand.nextInt(100) <= 98){
										String direction = getOrbDirection(pink);
										if (GameBoard.directionIsOpposite(direction, pink.getDirection())){
											String curDirection = pink.getDirection();
											direction = GameBoard.getRandomJunctionDirection(pink.getX(), pink.getY(), numCorridors, curDirection);
											pink.setDirection(direction);
										}
										else{
											pink.setDirection(direction);
										}
										
									//}
									
									
									
									//yellow.setDirection(direction);
								}
								else{
									Position lookAheadMove = pink.virtualMove();
									if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
										String direction = getOrbDirection(pink);
										pink.setDirection(direction);
									}
								}
								
								//pink.directionSwitched = false;
								//////////////////////////////////////////
								
				
								
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
								blue.setDirectionLeft();
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
								int numCorridors = 0;
								numCorridors = GameBoard.getJunctionCount(blue.getX(), blue.getY());
								if (numCorridors >= 3){
									if (GameBoard.rand.nextInt(100) <= 79){
										String direction = pathDirectionLookAhead(blue);
										if (GameBoard.directionIsOpposite(direction, blue.getDirection())){
											String curDirection = blue.getDirection();
											direction = GameBoard.getRandomJunctionDirection(blue.getX(), blue.getY(), numCorridors, curDirection);
											blue.setDirection(direction);
										}
										else{
											choosePath(blue);
										}
									}
									else{
									//choose direction at random
										String curDirection = blue.getDirection();
										String direction = GameBoard.getRandomJunctionDirection(blue.getX(), blue.getY(), numCorridors, curDirection);
										blue.setDirection(direction);
									
									}
								}
								else{
									Position lookAheadMove = blue.virtualMove();
									if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
										choosePath(blue);
									}
								}
								
								//helper for orb mode
								blue.directionSwitched = false;
							}
							else if (blue.getState().equals("orb") || blue.getState().equals("flashing")){
								//String tempLastDirection = red.getLastDirection();
								//choosePath(red);
								//red.setLastDirection(tempLastDirection);
								
								float oldX, oldY;
								oldX = blue.getX();
								oldY = blue.getY();
								
								if (blue.directionSwitched == false){
									if (blue.getDirection().equals("up")){
										blue.setLastDirection(blue.getDirection());
										blue.setDirectionDown();
									}
									else if (blue.getDirection().equals("down")){
										blue.setDirectionUp();
										blue.setLastDirection(blue.getDirection());
									}
									else if (blue.getDirection().equals("left")){
										blue.setDirectionRight();
										blue.setLastDirection(blue.getDirection());
									}
									else{
										blue.setDirectionLeft();
										blue.setLastDirection(blue.getDirection());
									}
									
									blue.directionSwitched = true;
								}
								//////////////////////////////////////////
								int numCorridors = 0;
								numCorridors = GameBoard.getJunctionCount(blue.getX(), blue.getY());
								if (numCorridors >= 3){
									
									
									//if (GameBoard.rand.nextInt(100) <= 98){
										String direction = getOrbDirection(blue);
										if (GameBoard.directionIsOpposite(direction, blue.getDirection())){
											String curDirection = blue.getDirection();
											direction = GameBoard.getRandomJunctionDirection(blue.getX(), blue.getY(), numCorridors, curDirection);
											blue.setDirection(direction);
										}
										else{
											blue.setDirection(direction);
										}
										
									//}
									
									
									
									//yellow.setDirection(direction);
								}
								else{
									Position lookAheadMove = blue.virtualMove();
									if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
										String direction = getOrbDirection(blue);
										blue.setDirection(direction);
									}
								}
								
								//blue.directionSwitched = false;
								//////////////////////////////////////////
								
				
								
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
								yellow.setDirectionRight();
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
									if (GameBoard.rand.nextInt(100) <= 29){
										choosePath(yellow);
									}
									else{
									//choose direction at random
										String curDirection = yellow.getDirection();
										String direction = GameBoard.getRandomJunctionDirection(yellow.getX(), yellow.getY(), numCorridors, curDirection);
										yellow.setDirection(direction);
									
									}
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
								//String tempLastDirection = red.getLastDirection();
								//choosePath(red);
								//red.setLastDirection(tempLastDirection);
								
								float oldX, oldY;
								oldX = yellow.getX();
								oldY = yellow.getY();
								
								if (yellow.directionSwitched == false){
									if (yellow.getDirection().equals("up")){
										yellow.setLastDirection(yellow.getDirection());
										yellow.setDirectionDown();
									}
									else if (yellow.getDirection().equals("down")){
										yellow.setDirectionUp();
										yellow.setLastDirection(yellow.getDirection());
									}
									else if (yellow.getDirection().equals("left")){
										yellow.setDirectionRight();
										yellow.setLastDirection(yellow.getDirection());
									}
									else{
										yellow.setDirectionLeft();
										yellow.setLastDirection(yellow.getDirection());
									}
									
									yellow.directionSwitched = true;
								}
								//////////////////////////////////////////
								int numCorridors = 0;
								numCorridors = GameBoard.getJunctionCount(yellow.getX(), yellow.getY());
								if (numCorridors >= 3){
									
									
									//if (GameBoard.rand.nextInt(100) <= 98){
										String direction = getOrbDirection(yellow);
										if (GameBoard.directionIsOpposite(direction, yellow.getDirection())){
											String curDirection = yellow.getDirection();
											direction = GameBoard.getRandomJunctionDirection(yellow.getX(), yellow.getY(), numCorridors, curDirection);
											yellow.setDirection(direction);
										}
										else{
											yellow.setDirection(direction);
										}
										
									//}
									
									
									
									//yellow.setDirection(direction);
								}
								else{
									Position lookAheadMove = yellow.virtualMove();
									if (GameBoard.isBlocked(lookAheadMove.getX(), lookAheadMove.getY())){
										String direction = getOrbDirection(yellow);
										yellow.setDirection(direction);
									}
								}
								
								//yellow.directionSwitched = false;
								//////////////////////////////////////////
								
				
								
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
	
public String pathDirectionLookAhead(Ghost ghost){
		
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
		  				return "up";
		  				
		  		}
		  		else if( right >= left &&
		  				 right >= up &&
		  				 right >= down){
		  				return "right";
		  		}
		  		else if( left >= right &&
		  				 left >= up &&
		  				 left >= down){
		  					//System.out.println("left = " + left + "(x, y) = (" + x + ", " + y + ")");
		  				return "left";
		  		}
		  		else if( down >= right &&
		  				 down >= left &&
		  				 down >= up){
		  				
		  					return "down";
		  		}
		  		
		  		return "up";
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
	  		
	  		//one pass, find lowest value
	  		float lowestVal = Float.MAX_VALUE;
	  		if (up < lowestVal && up > 0){
	  			lowestVal = up;
	  		}
	  		if (right < lowestVal && right > 0){
	  			lowestVal = right;
	  		}
	  		if (left < lowestVal && left > 0){
	  			lowestVal = left;
	  		}
	  		if (down < lowestVal && down > 0){
	  			lowestVal = down;
	  		}
	  		
	  		//create List of lowest vals
	  		List<String> tieValues = new ArrayList<String>();
	  		if (up == lowestVal){
	  			tieValues.add("up");
	  		}
	  		if (right == lowestVal){
	  			tieValues.add("right");
	  		}
	  		if (left == lowestVal){
	  			tieValues.add("left");
	  		}
	  		if (down == lowestVal){
	  			tieValues.add("down");
	  		}
	  		
	  		//choose one at random
	  		if (tieValues.size() == 0){
	  			return ghost.getDirection();
	  		}
	  		int randomVal = GameBoard.rand.nextInt(tieValues.size());
	  		return tieValues.get(randomVal);
	  		
		}
	}
	
