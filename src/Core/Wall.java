package Core;

public class Wall extends GameObject
{
	private Line wall;
	private Bitmap texture;
	private Vector2f minTexCoord;
	private Vector2f maxTexCoord;
	
	public Wall(Vector2f position, float rot, Line wall) 
	{
		this(position, rot, wall, new Bitmap(32, 32), new Vector2f(0, 0), new Vector2f(1, 1));
	}
	
	public Wall(Vector2f position, float rot, Line wall, Bitmap texure) 
	{
		this(position, rot, wall, texure, new Vector2f(0, 0), new Vector2f(1, 1));
	}
	
	public Wall(Vector2f position, float rot, Line wall, Bitmap texture, Vector2f minTexCoord, Vector2f maxTexCoord) 
	{
		super(position, rot);
		this.wall = wall;
		this.texture = texture;
		this.minTexCoord = minTexCoord;
		this.maxTexCoord = maxTexCoord;
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
	
	public Bitmap getTexture()
	{
		return texture;
	}

	public Vector2f getMaxTexCoord() {
		return maxTexCoord;
	}

	public void setMaxTexCoord(Vector2f maxTexCoord) {
		this.maxTexCoord = maxTexCoord;
	}

	public Vector2f getMinTexCoord() {
		return minTexCoord;
	}

	public void setMinTexCoord(Vector2f minTexCoord) {
		this.minTexCoord = minTexCoord;
	}

	public Line getWall() {
		return wall.translate(getPosition());
	}

	public void setWall(Line wall) {
		this.wall = wall;
	}
}
