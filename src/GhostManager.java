import java.util.concurrent.atomic.AtomicBoolean;


public class GhostManager {
	static Ghost[] ghosts;
	static int orbTimer = 80000;
	private static boolean timerOn = false;
	
	public GhostManager(){
		ghosts = new Ghost[4];
		
	}
	
	public void initializeGhosts(SpriteManager sm){
		float x, y;
		x = 250;
		y = 310;
		ghosts[0] = new Ghost(sm.getRedGhostSprites(), "red", x, y, "chasing");
		x += GameBoard.tileLength;
		ghosts[1] = new Ghost(sm.getPinkGhostSprites(), "pink", x, y, "chasing");
		x += GameBoard.tileLength;
		ghosts[2] = new Ghost(sm.getBlueGhostSprites(), "blue", x, y, "chasing");
		x += GameBoard.tileLength;
		ghosts[3] = new Ghost(sm.getYellowGhostSprites(), "yellow", x, y, "chasing");
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
					Thread.sleep(red.putToSleep(11));
					red.wake();
					while(true){
						try {
							red.getCondition().wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
						choosePath(pink);
						try {
							pink.getCondition().wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        System.out.println("Pinky will survive");
	    }  
	};
	Thread blueLogic = new Thread() {
		public void run() {
	        Ghost blue = getBlueGhost();
	        if(blue.isAsleep())
				try {
					Thread.sleep(blue.putToSleep(23));
					blue.wake();
					while(true){
						choosePath(blue);
						try {
							blue.getCondition().wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
					Thread.sleep(yellow.putToSleep(37));
					yellow.wake();
					while(true){
						try {
							yellow.getCondition().wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
		
		//get the direction influences
		double left = InfluenceMap.getInfluenceMap()[x-1][y];
		double right = InfluenceMap.getInfluenceMap()[x+1][y];
		double up = InfluenceMap.getInfluenceMap()[x][y-1];
		double down = InfluenceMap.getInfluenceMap()[x][y+1];
		
		//get highest weight with bias towards up,right,left,down
		if( up >= right &&
			up >= left &&
			up >= down)
				ghost.setDirectionUp();
		else if( right >= left &&
				 right >= up &&
				 right >= down)
					ghost.setDirectionDown();
		else if( left >= right &&
				 left >= up &&
				 left >= down)
					ghost.setDirectionLeft();
		else if( down >= right &&
				 down >= left &&
				 down >= up)
					ghost.setDirectionDown();
		
			ghost.getPathReady().set(true);
		}
	

	
}
