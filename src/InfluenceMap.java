
public class InfluenceMap {
	
	
	
	private static float[][] influenceMap = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,0,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
			{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
			{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
			{0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0},
			{0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
			{0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
			{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
			{0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
	
	
	public InfluenceMap(){
		System.out.println("InfluenceMap inintialized, i = " + influenceMap.length);
	}
	
	public static float[][] getInfluenceMap(){
		return InfluenceMap.influenceMap;
	}
	
	public static void setInfluenceMap(float[][] influenceMapCopy){
		influenceMap = influenceMapCopy;
	}
	
	public static void resetInfluence(){
		for(int i = 0; i < influenceMap.length; i++){
			for(int j = 0; j < influenceMap[i].length; j++){
				if(influenceMap[i][j] > 0.)
					influenceMap[i][j] = 1.0f;
			}
		}
	}
	
	public float max(float first, float second){
		if(first > second)
			return first;
		else return second;
	}
	
	public static float lerp(float a, float b, float percent){		
		return (a + percent * (b-a));
	}
	
	public static float averageIt(int i, int j){
		float a = 0.5f*(influenceMap[i+1][j]+influenceMap[i-1][j]
					+influenceMap[i][j+1]+influenceMap[i][j-1]);
		
		/*
		if(a > 1)
			return a;
		else return 1;
		*/
		
		return a;
	}
	
	public static void averageIt(float[][] influenceMap){
		float a;
		for(int i = 1; i < 30; i++){
			for(int j = 1; j < influenceMap[i].length-2; j++){
				if (influenceMap[i][j] != 0){
					a = 0.5f*(influenceMap[i+1][j]+influenceMap[i-1][j]
					+influenceMap[i][j+1]+influenceMap[i][j-1]);
					
					influenceMap[i][j] = a;
					
					/*
					if(a > 1){
						influenceMap[i][j] = a;
					}
					else{
						influenceMap[i][j] = 1.0f;
					}
					*/
					
				}
			}
		}
		
	}
	
	public static void propagateInfluence(float[][] bufferMap){
		float up, down, left, right, tempMax;
		
			for(int i = 1; i < 30; i++){
				for(int j = 1; j < bufferMap[i].length-2; j++){
					if (bufferMap[i][j] != 0){
						/*up = influenceMap[i-1][j];
						down = influenceMap[i+1][j];
						left = influenceMap[i][j-1];
						right = influenceMap[i][j+1];
						tempMax = Math.max(up, down);
						tempMax = Math.max(tempMax, left);
						tempMax = Math.max(tempMax, right);*/
						if (i >= 12 && i <= 16 && j >= 10 && j <= 17){
							//prevents influence from affecting ghost home
							bufferMap[i][j] = 0.0f;
						}
						else{
							bufferMap[i][j] = averageIt(i,j);
						}
						//bufferMap[i][j] = lerp(tempMax, influenceMap[i][j], 0.95f);
					}
				}
			}
			
			for(int i = 1; i < 30; i++){
				for(int j = 1; j < bufferMap[i].length-2; j++){
					bufferMap[i][j] = bufferMap[i][j] * 0.8f;
				}
				}
		
		//System.out.println("Influence Map Changed");
		influenceMap = bufferMap;
		
	}
	
	/*
	public static void propagateInfluence(){
		double bufferMap[][] = new double[31][28];
		
			for(int i = 1; i < 29; i++){
				for(int j = 1; j < influenceMap[i].length-2; j++){
					if (influenceMap[i][j] != 0){
						bufferMap[i][j] = averageIt(i,j);
					}
				}
		}
		
		System.out.println("Influence Map Changed");
		influenceMap = bufferMap;
		
	}
	*/
}