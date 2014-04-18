//Axis-Aligned Bounding Box
public class AABB {
	protected Vector2Float center, size;
	
	public AABB(){}
	public AABB(Vector2Float center, Vector2Float size){
		this.center = center;
		this.size = size.divide(2);
	}
	public AABB(Entity entity){
		this.center.set(entity.getX(),entity.getY());
		this.size.set(entity.getSize().divide(2));
	}

	 public static boolean collides(AABB a, AABB b)
	   {
	      if(Math.abs(a.center.x - b.center.x) < a.size.x + b.size.x)
	      {
	         if(Math.abs(a.center.y - b.center.y) < a.size.y + b.size.y)
	         {
	            return true;
	         }
	      }
	      
	      return false;
	   }
	   
	   public static boolean inside(AABB a, Vector2Float b)
	   {
	      if(Math.abs(a.center.x - b.x) < a.size.x)
	      {
	         if(Math.abs(a.center.y - b.y) < a.size.y)
	         {
	            return true;
	         }
	      }
	      return false;
	   }
	   
	   @Override
	   public String toString(){
		   return "Center:\t" + center + "\nSize:\t" + size;
	   }
	}
