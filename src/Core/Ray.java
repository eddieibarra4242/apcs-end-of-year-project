package Core;

public class Ray 
{
	public Vector2f origin;
	public Vector2f direction;
	
	public Ray(Vector2f origin, Vector2f direction)
	{
		this.origin = origin;
		this.direction = direction;
	}
	
	public Vector2f getPoint(float distance)
	{
		return origin.add(direction.mul(distance));
	}

	public Vector2f getOrigin() {
		return origin;
	}

	public void setOrigin(Vector2f origin) {
		this.origin = origin;
	}

	public Vector2f getDirection() {
		return direction;
	}

	public void setDirection(Vector2f direction) {
		this.direction = direction;
	}
}
