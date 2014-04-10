
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
