
public class GhostManager {
	Ghost[] ghosts;
	
	public GhostManager(){
		ghosts = new Ghost[4];
		
	}
	
	public void initializeGhosts(SpriteManager sm){
		float x, y;
		x = 240;
		y = 300;
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
	
	public Ghost[] getGhosts(){
		return ghosts;
	}
	
	public Ghost getRedGhost(){
		return ghosts[0];
	}
	
	public Ghost getPinkGhost(){
		return ghosts[1];
	}
	
	public Ghost getBlueGhost(){
		return ghosts[2];
	}
	
	public Ghost getYellowGhost(){
		return ghosts[3];
	}
	
	public void renderGhosts(){
		for (Ghost ghost : ghosts){
			ghost.drawCurrentAnimation();
		}
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
					Thread.sleep(red.putToSleep(1));
					red.wake();
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
					Thread.sleep(pink.putToSleep(12));
					pink.wake();
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
	
	

	
}
