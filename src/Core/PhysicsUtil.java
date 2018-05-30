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
	
	public static Vector2f interSphereline(Vector2f center, float radius, Line line, Vector2f moveDir)
	{
		Vector2f ld = line.getL1().sub(line.getL0());
		
		float f = (ld.normalized().dot(line.getL0().sub(center)));
		float h = line.getL0().sub(center).length();
		float det = f * f - h * h + radius * radius;
		
		if(det <= 0)
		{
			return new Vector2f(0, 0);
		}
		
		float d0 = -f - (float)Math.sqrt(det);
		float d1 = -f + (float)Math.sqrt(det);
		
		if((d0 < 0 && d1 < 0) ||
		   (d0 > ld.length() && d1 > ld.length()))
		{
			return new Vector2f(0, 0);
		}
		
		float dist = (float)Math.abs(d1 - d0);
		float interpenetration = radius - (float)Math.sqrt(radius * radius - dist * dist);
		
		Vector2f pl = ld.normalized().getPerpendicular(1);
		float dot0 = pl.dot(moveDir);
		
		if(dot0 > 0)
		{
			pl = ld.normalized().getPerpendicular(-1);
			dot0 = pl.dot(moveDir);
		}
		
		System.out.println(moveDir.dot(pl.mul(interpenetration)));
		
		return new Vector2f(0, 0);
	}
}
