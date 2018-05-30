package Core;

public class Camera 
{
	public Vector2f position;
	public float rot;
	public float fov;
	
	public Camera(Vector2f position, float rot, float fov)
	{
		this.position = position;
		this.rot = rot;
		this.fov = fov;
	}

	public Vector2f getForward()
	{
		return new Matrix3f().initRotation(rot).mul(new Vector2f(1, 0), 0);
	}
	
	public void rotate(float angle)
	{
		rot -= angle;
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
	
	public float getFov()
	{
		return fov;
	}
}
