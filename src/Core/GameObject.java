package Core;

public abstract class GameObject 
{
	private Vector2f position;
	private float rot;
	
	public GameObject(Vector2f position, float rot)
	{
		this.position = position;
		this.rot = rot;
	}
	
	public abstract float intersect(Ray r);
	
	public void move(Vector2f dir, float dist)
	{
		position = position.add(dir.mul(dist));
	}
	
	public void rotate(float rot)
	{
		this.rot += rot;
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getRot() {
		return rot;
	}

	public void setRot(float rot) {
		this.rot = rot;
	}
}
