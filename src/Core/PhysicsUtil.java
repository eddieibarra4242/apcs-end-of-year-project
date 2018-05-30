package Core;

public class PhysicsUtil 
{	
	public static Vector2f intersectLine(Line l1, Line l2)
	{
		Vector2f line1 = l1.getL1().sub(l1.getL0());
		Vector2f line2 = l2.getL1().sub(l2.getL0());
		
		float cross = line1.cross(line2);
		
		if(cross == 0)
			return null;
		
		Vector2f dist = l2.getL0().sub(l1.getL0());
		
		float a = dist.cross(line2) / cross;
		float b = dist.cross(line1) / cross;
		
		if(0.0f < a && a < 1.0f && 0.0f < b && b < 1.0f)
			return l1.getL0().add(line1.mul(a));
		
		return null;
	}
}
