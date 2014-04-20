import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class InfluenceMapLocksmith {
	private Pacman pm;
	private float lastPacmanX, lastPacmanY;
	
	InfluenceMapLocksmith(Pacman pm){
		this.pm = pm;
		lastPacmanX = lastPacmanY = -1.0f;
	}	

	public float[][] getInfluenceMap(){
		return InfluenceMap.getInfluenceMap();
	}
	
	public void propagateInfluence(){
		//get pacman position
		float pmX, pmY;
		pmX = pm.getX();
		pmY = pm.getY();
		
		if (pmX == lastPacmanX & pmY == lastPacmanY){
			return;
		}
		
		lastPacmanX = pmX;
		lastPacmanY = pmY;
		
		InfluenceMap.getInfluenceMap()[(int)pm.getY()/20][(int)pm.getX()/20] = Pacman.INFLUENCE_VALUE;
		
		//System.out.println("Writing new influence map");
		float[][] influenceCopy = new float[31][28];
		for(int i = 0; i < 31; i++){
			for(int j = 0; j < 28; j++){
				influenceCopy[i][j] = InfluenceMap.getInfluenceMap()[i][j];
			}
		}
	
		InfluenceMap.propagateInfluence(influenceCopy);
		InfluenceMap.setInfluenceMap(influenceCopy);
	}

}
