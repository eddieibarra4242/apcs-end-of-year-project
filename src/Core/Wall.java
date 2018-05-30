package Core;

public class Wall extends GameObject
{
	private Line wall;
	
	public Wall(Vector2f position, float rot, Line wall) 
	{
		super(position, rot);
		this.wall = wall;
	}

	@Override
	public float intersect(Ray r) 
	{
		Vector2f intersectionPoint = PhysicsUtil.intersectLine(wall.translate(getPosition()), new Line(r.getOrigin(), r.getPoint(1000)));
		
		if(intersectionPoint == null)
		{
			return -1;
		}
		
		return intersectionPoint.sub(r.getOrigin()).length();
	}

}
