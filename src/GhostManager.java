
public class GhostManager {
	Ghost[] ghosts;
	
	public GhostManager(){
		ghosts = new Ghost[4];
		
	}
	
	public void initializeGhosts(SpriteManager sm){
		float x, y;
		x = 240;
		y = 300;
		ghosts[0] = new Ghost(sm.getRedGhostSprites(), "red", x, y, "chasing", 10);
		x += GameBoard.tileLength;
		ghosts[1] = new Ghost(sm.getPinkGhostSprites(), "pink", x, y, "chasing", 20);
		x += GameBoard.tileLength;
		ghosts[2] = new Ghost(sm.getBlueGhostSprites(), "blue", x, y, "chasing", 30);
		x += GameBoard.tileLength;
		ghosts[3] = new Ghost(sm.getYellowGhostSprites(), "yellow", x, y, "chasing", 40);
	}
	
/*	private void escapeLogic(Ghost ghost){
		float x = ghost.getX();
		float y = 
		if (x > )
		
	}
*/	
	Thread redLogic = new Thread() {
	    public void run() {
	        System.out.println("Hi");
	    }  
	};
	Thread pinkLogic = new Thread() {
	    public void run() {
	        System.out.println("Does it work?");
	    }  
	};
	Thread blueLogic = new Thread() {
	    public void run() {
	        System.out.println("Does it work?");
	    }  
	};
	Thread yellowLogic = new Thread() {
	    public void run() {
	        System.out.println("Does it work?");
	        if(getYellowGhost().isAsleep())
				try {
					Thread.sleep(getYellowGhost().timer()*10);
					getYellowGhost().wake();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }  
	};
	
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
}
