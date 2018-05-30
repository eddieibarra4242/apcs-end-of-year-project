package Core;

public class Line 
{
	public Vector2f l0;
	public Vector2f l1;
	
	public Line(Vector2f l0, Vector2f l1)
	{
		this.l0 = l0;
		this.l1 = l1;
	}
	
	public Line translate(Vector2f translation)
	{
		return new Line(l0.add(translation), l1.add(translation));
	}
	
	public Ray getRayFromL0()
	{
		return new Ray(l0, l1.sub(l0));
	}
	
	public Ray getRayFromL1()
	{
		return new Ray(l1, l0.sub(l1));
	}
	
	public float length() 
	{
		return l1.sub(l0).length();
	}

	public Vector2f getL0() {
		return l0;
	}

	public void setL0(Vector2f l0) {
		this.l0 = l0;
	}

	public Vector2f getL1() {
		return l1;
	}

	public void setL1(Vector2f l1) {
		this.l1 = l1;
	}
}
