
public interface Entity {
	
	public void setX(float x);
	
	public void setY(float y);
	
	public void setPosition(float x, float y);
	
	public float getX();
	
	public float getY();
	
	public Position getPosition();
	
	public String getDirection();
	
	public void move();
	
	public Vector2Float getSize();
	
	public void updateCenter();
	
	public AABB getBox();

}
