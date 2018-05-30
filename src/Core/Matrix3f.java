package Core;

public class Matrix3f 
{
	private float[][] m;
	
	public Matrix3f()
	{
		m = new float[3][3];
	}
	
	public Matrix3f initIdentity()
	{
		m[0][0] = 1; m[0][1] = 0; m[0][2] = 0;
		m[1][0] = 0; m[1][1] = 1; m[1][2] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1;
		
		return this;
	}
	
	public Matrix3f initTranslate(float x, float y)
	{
		m[0][0] = 1; m[0][1] = 0; m[0][2] = x;
		m[1][0] = 0; m[1][1] = 1; m[1][2] = y;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1;
		
		return this;
	}
	
	public Matrix3f initRotation(float angle)
	{
		m[0][0] = (float)Math.cos(angle); m[0][1] = -(float)Math.sin(angle);m[0][2] = 0;
		m[1][0] = (float)Math.sin(angle); m[1][1] = (float)Math.cos(angle); m[1][2] = 0;
		m[2][0] = 0; 					  m[2][1] = 0; 						m[2][2] = 1;
		
		return this;
	}
	
//	public Matrix3f initRotation(float angle)
//	{
//		m[0][0] = (float)Math.cos(angle); m[0][1] = 0; m[0][2] = -(float)Math.sin(angle);
//		m[1][0] = 0; 					  m[1][1] = 1; m[1][2] = 0;
//		m[2][0] = (float)Math.sin(angle); m[2][1] = 0; m[2][2] = (float)Math.cos(angle);
//		
//		return this;
//	}
	
	public Vector2f mul(Vector2f point, float third)
	{
		float[] v = new float[2];
		
		for(int i = 0; i < 2; i++) 
		{
			v[i] = point.getX() * m[i][0] +
					point.getY() * m[i][1] +
					third * m[i][2];
		}
		
		return new Vector2f(v[0], v[1]);
	}
	
	public String toString()
	{
		StringBuilder string = new StringBuilder();
		
		for(int i = 0; i < 3; i++)
		{
			string.append("[");
			for(int j = 0; j < 3; j++)
			{
				string.append(m[i][j]).append(" ");
			}
			string.delete(string.length() - 1, string.length());
			string.append("]");
			string.append("\n");
		}
		
		return string.toString();
	}
	
	public Matrix3f mul(Matrix3f r)
	{
		Matrix3f res = new Matrix3f();
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				res.set(i, j, m[i][0] * r.get(0, j) +
							  m[i][1] * r.get(1, j) +
							  m[i][2] * r.get(2, j));// +
							  //m[i][3] * r.get(3, j));
			}
		}
		
		return res;
	}
	
	public float[][] getM()
	{
		float[][] res = new float[3][3];
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				res[i][j] = m[i][j];
		
		return res;
	}
	
	public float get(int x, int y)
	{
		return m[x][y];
	}

	public void setM(float[][] m)
	{
		this.m = m;
	}
	
	public void set(int x, int y, float value)
	{
		m[x][y] = value;
	}
}
