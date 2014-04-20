import java.util.concurrent.atomic.AtomicBoolean;


public class GhostManager {
	static Ghost[] ghosts;
	static int orbTimer = 80000;
	private static boolean timerOn = false;
	private InfluenceMapLocksmith influenceMapLocksmith;
	
	public GhostManager(InfluenceMapLocksmith influenceMapLocksmith){
		ghosts = new Ghost[4];
		this.influenceMapLocksmith = influenceMapLocksmith;
		
	}
	
	public void initializeGhosts(SpriteManager sm){
		ghosts[0] = new Ghost(sm.getRedGhostSprites(), "red", 260, 310, "chasing");
		ghosts[1] = new Ghost(sm.getPinkGhostSprites(), "pink", 260, 310, "chasing");
		ghosts[2] = new Ghost(sm.getBlueGhostSprites(), "blue", 260, 310, "chasing");
		ghosts[3] = new Ghost(sm.getYellowGhostSprites(), "yellow", 260, 310, "chasing");
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
		setGhostsChasing();
		getRedGhost().setX(240);
		getRedGhost().setY(300);
		getRedGhost().updateCenter();
		getPinkGhost().setX(260);
		getPinkGhost().setY(300);
		getPinkGhost().updateCenter();
		getBlueGhost().setX(280);
		getBlueGhost().setY(300);
		getBlueGhost().updateCenter();
		getYellowGhost().setX(300);
		getYellowGhost().setY(300);
		getYellowGhost().updateCenter();
		getRedGhost().doReset.set(true);
		getBlueGhost().doReset.set(true);
		getYellowGhost().doReset.set(true);
		getPinkGhost().doReset.set(true);
		
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
						if (red.getX() == 260 && red.getY() == 230){
							break;
						}
						red.lock();
						red.setDirectionUp();
						red.getPathReady().set(true);
						red.await();
					}
					while(true){
						choosePath(red);
						//synchronized(red.getCondition()){
						
								red.lock();
								//System.out.println("Red awaiting...");
								red.getPathReady().set(true);
								red.await();
								//System.out.println("Red awake...");
								
						//	}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        System.out.println("Red incoming");
	    }  
	};
	Thread pinkLogic = new Thread() {
		public void run() {
	        Ghost pink = getPinkGhost();
	        if(pink.isAsleep())
				try {
					Thread.sleep(pink.putToSleep(0));
					pink.wake();
					
					while(true){
						
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
					Thread.sleep(blue.putToSleep(0));
					blue.wake();
					while(true){
						if (blue.getX() == 260 && blue.getY() == 230){
							break;
						}
						blue.lock();
						blue.setDirectionUp();
						blue.getPathReady().set(true);
						blue.await();
					}
					while(true){
						choosePath(blue);
						//synchronized(blue.getCondition()){
							
								blue.lock();
								//System.out.println("Blue awaiting...");
								blue.getPathReady().set(true);
								blue.await();
								//System.out.println("Blue awake...");
							
						
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        System.out.println("The blue bomber will blow you away!");
	    }  
	};
	Thread yellowLogic = new Thread() {
	    public void run() {
	        Ghost yellow = getYellowGhost();
	        if(yellow.isAsleep())
				try {
					Thread.sleep(yellow.putToSleep(0));
					yellow.wake();
					while(true){
						if (yellow.getX() == 260 && yellow.getY() == 230){
							break;
						}
						yellow.lock();
						yellow.setDirectionUp();
						yellow.getPathReady().set(true);
						yellow.await();
					}
					while(true){
						choosePath(yellow);
						//synchronized(yellow.getCondition()){

								yellow.lock();
								//System.out.println("Yellow awaiting...");
								yellow.getPathReady().set(true);
								//System.out.println("condition hash: " + yellow.getCondition().hashCode());
								yellow.await();
								//System.out.println("Yellow awake...");
							
						//}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        System.out.println("Yellow here?");
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
		
		
		float left = influenceMapLocksmith.getInfluenceMap()[x][y - 1];
		float right = influenceMapLocksmith.getInfluenceMap()[x][y + 1];
		float up = influenceMapLocksmith.getInfluenceMap()[x - 1][y];
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
