import java.util.concurrent.atomic.AtomicBoolean;


public class GhostManager {
	static Ghost[] ghosts;
	static int orbTimer = 80000;
	private static boolean timerOn = false;
	private InfluenceMapLocksmith influenceMapLocksmith;
	
	public GhostManager(InfluenceMapLocksmith influenceMapLocksmith){
		ghosts = new Ghost[4];
		this.influenceMapLocksmith = influenceMapLocksmith;
		influenceMapLocksmith.addGhosts(ghosts);
		
	}
	
	public void initializeGhosts(SpriteManager sm){
		ghosts[0] = new Ghost(sm.getRedGhostSprites(), "red", 260, 290, "chasing");
		ghosts[1] = new Ghost(sm.getPinkGhostSprites(), "pink", 260, 310, "chasing");
		ghosts[2] = new Ghost(sm.getBlueGhostSprites(), "blue", 280, 310, "chasing");
		ghosts[3] = new Ghost(sm.getYellowGhostSprites(), "yellow", 280, 290, "chasing");
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
			if(!ghost.getState().equals("eaten"))
				ghost.setStateFlashing();
	}
	public static void setGhostsOrb(){
		for (Ghost ghost : ghosts)
			if(!ghost.getState().equals("eaten"))
				ghost.setStateOrb();
		orbTimer = 225;
		timerOn = true;
	}
	public static void resetGhostPositions(){
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
		
		setGhostsChasing();
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
		getPinkGhost().wake();
		getBlueGhost().wake();
		getYellowGhost().wake();
		
		
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
					
					while(true){
						while (red.isAsleep()){
							Thread.sleep(red.putToSleep(0));
							red.wake();
						}
						
						if (red.doReset.get() == true){
							if (red.isBlocked()){
								red.setPosition(262.0f, 226.0f);
								System.out.println("position: " + red.getX() + ", " + red.getY());
								red.setIsBlocked(false);
								red.doReset.set(false);
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
							choosePath(red);
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
					
					while(true){
						while (pink.isAsleep()){
							Thread.sleep(pink.putToSleep(20));
							pink.wake();
						}
						
						if (pink.doReset.get() == true){
							if (pink.isBlocked()){
								pink.setPosition(262.0f, 226.0f);
								System.out.println("position: " + pink.getX() + ", " + pink.getY());
								pink.setIsBlocked(false);
								pink.doReset.set(false);
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
							choosePath(pink);
							//synchronized(pink.getCondition()){
		
									//System.out.println("Pink awaiting...");
									pink.lock();
									pink.getPathReady().set(true);
									//System.out.println("Pink Condition Hashcode: " + pink.getCondition().hashCode());
									pink.await();
									//System.out.println("Pink awake...");
	
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
					
					while(true){
						
						while (blue.isAsleep()){
							Thread.sleep(blue.putToSleep(10));
							blue.wake();
						}
						
						if (blue.doReset.get() == true){
							if (blue.isBlocked()){
								blue.setPosition(282.0f, 226.0f);
								System.out.println("position: " + blue.getX() + ", " + blue.getY());
								blue.setIsBlocked(false);
								blue.doReset.set(false);
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
							choosePath(blue);
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
					
					while(true){
						while (yellow.isAsleep()){
							Thread.sleep(yellow.putToSleep(10));
							yellow.wake();
						}
						
						if (yellow.doReset.get() == true){
							if (yellow.isBlocked()){
								yellow.setPosition(282.0f, 226.0f);
								System.out.println("position: " + yellow.getX() + ", " + yellow.getY());
								yellow.setIsBlocked(false);
								yellow.doReset.set(false);
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
							choosePath(yellow);
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
	}
	

	
