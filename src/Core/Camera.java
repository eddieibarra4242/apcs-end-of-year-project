package Core;

import java.util.ArrayList;

import Input.IInput;

public class Camera 
{
	public Vector2f position;
	public float rot;
	public float fov;
	private boolean mouseLocked;
	private float sensitivity;
	
	public Camera(Vector2f position, float rot, float fov, float mouseSensitivity)
	{
		this.position = position;
		this.rot = rot;
		this.fov = fov;
		this.sensitivity = mouseSensitivity;
	}
	
	public void update(float delta, IInput input, ArrayList<GameObject> objs)
	{
		Vector2f moveDir = Vector2f.zeroVector;
		
		if(input.getKey(IInput.KEY_W))
		{
			moveDir = moveDir.add(getForward());
		}
		
		if(input.getKey(IInput.KEY_S))
		{
			moveDir = moveDir.sub(getForward());
		}
		
		if(input.getKey(IInput.KEY_A))
		{
			moveDir = moveDir.sub(getRight());
		}
		
		if(input.getKey(IInput.KEY_D))
		{
			moveDir = moveDir.add(getRight());
		}
		
		if(input.getMouse(IInput.MOUSE_BUTTON_MIDDLE))
		{
			input.setCursor(true);
			mouseLocked = false;
		}
		if(input.getMouse(IInput.MOUSE_BUTTON_LEFT))
		{
			input.setCursorPosition(Vector2f.zeroVector);
			input.setCursor(false);
			mouseLocked = true;
		}

		if(mouseLocked)
		{
			Vector2f deltaPos = input.getMouse2D().mul(14);
			
			boolean rotY = deltaPos.getX() != 0;
			
			if(rotY)
				rotate((float)(deltaPos.getX() * sensitivity * delta));

			if(rotY)
				input.setCursorPosition(Vector2f.zeroVector);
		}
		
		moveDir = moveDir.normalized();
		
		for(int i = 0; i < objs.size(); i++)
		{
			PhysicsUtil.interSphereline(position, 0.3f, ((Wall)objs.get(i)).getWall(), moveDir);
		}
		
		move(moveDir, delta);
	}
	
	public Vector2f getForward()
	{
		return new Matrix3f().initRotation(rot).mul(new Vector2f(1, 0), 0);
	}
	
	public Vector2f getRight()
	{
		return new Matrix3f().initRotation(rot + (float)Math.PI/2.0f).mul(new Vector2f(1, 0), 0);
	}
	
	public void move(Vector2f direction, float amt)
	{
		position = position.add(direction.mul(amt));
	}
	
	public void rotate(float angle)
	{
		rot += angle;
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
