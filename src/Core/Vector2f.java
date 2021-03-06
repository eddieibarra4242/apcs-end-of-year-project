package Core;

public class Vector2f 
{
	public static final Vector2f zeroVector = new Vector2f(0, 0);
	private float x;
	private float y;
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float length()
	{
		return (float)Math.sqrt(x * x + y * y);
	}
	
	public float dot(Vector2f r)
	{
		return x * r.getX() + y * r.getY();
	}
	
	public float cross(Vector2f r)
	{
		return (x * r.getY()) - (y * r.getX());
	}
	
	public Vector2f normalized()
	{
		float len = length();
		
		if(len == 0)
		{
			return new Vector2f(0, 0);
		}
		
		return div(len);
	}
	
	public Vector2f getPerpendicular(int mul)
	{
		return new Vector2f(-y, x).mul(mul);
	}
	
	public Vector2f add(float r)
	{
		return new Vector2f(x + r, y + r);
	}
	
	public Vector2f add(Vector2f r)
	{
		return new Vector2f(x + r.getX(), y + r.getY());
	}
	
	public Vector2f sub(float r)
	{
		return new Vector2f(x - r, y - r);
	}
	
	public Vector2f sub(Vector2f r)
	{
		return new Vector2f(x - r.getX(), y - r.getY());
	}
	
	public Vector2f mul(float r)
	{
		return new Vector2f(x * r, y * r);
	}
	
	public Vector2f mul(Vector2f r)
	{
		return new Vector2f(x * r.getX(), y * r.getY());
	}
	
	public Vector2f div(float r)
	{
		return new Vector2f(x / r, y / r);
	}
	
	public Vector2f div(Vector2f r)
	{
		return new Vector2f(x / r.getX(), y / r.getY());
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return "(" + x + " " + y + ")";
	}
}
