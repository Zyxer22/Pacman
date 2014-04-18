public class Vector2Float{
	public float x,y;
	
	public Vector2Float(){}
	public Vector2Float(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y){
		this.x = x;
		this.y = y;
	}
	public void set(Vector2Float set){
		this.x = set.x;
		this.y = set.y;
	}
	
	@Override
	public String toString(){
		return ("("+x + "," +y+")");
	}
	
	public Vector2Float divide(float a){
		this.x/=a;
		this.y/=a;
		return this;
	}
	public Vector2Float multiply(float a){
		this.x*=a;
		this.y*=a;
		return this;
	}

}
