package Core;
import java.util.ArrayList;

public class RenderEngine 
{
	private Camera camera;
	
	public RenderEngine(Camera camera)
	{
		this.camera = camera;
	}
	
	public void render(ArrayList<GameObject> objects)
	{
		int width = RenderUtil.getFrameBuffer().getWidth();
		int height = RenderUtil.getFrameBuffer().getHeight();
		
		for(int x = 0; x < width; x++)
		{
			float px = (2 * ((x + 0.5f) / width) - 1) * (float)Math.tan(camera.getFov() / 2.0f) * RenderUtil.getAR();
			
			Vector2f direction = new Matrix3f().initRotation((float)Math.atan(px) + camera.getRot()).mul(new Vector2f(1, 0), 0);
			Ray ray = new Ray(camera.getPosition(), direction);
			
			float[] inters = new float[objects.size()];
			
			for(int i = 0; i < inters.length; i++)
			{
				inters[i] = objects.get(i).intersect(ray);
			}
			
			Vector2f intersectionPoint = PhysicsUtil.nearestIntersection(ray, inters);
			
			if(intersectionPoint != null)
			{	
				float dist = direction.dot(camera.getForward()) * camera.getPosition().sub(intersectionPoint).length();
				float check = 0.4f/dist;
				
				for(int y = 0; y < height; y++)
				{
					float ndcy = (y + 0.5f) * 2 / height - 1;
					
					if(ndcy < check && ndcy > -check)
					{	
						RenderUtil.drawPixel(x, y, 255, 255, 0, 0);
					}
					else
					{
						//Draw Ceiling and floor
						RenderUtil.drawPixel(x, y, 100, 100, 100, 100);
					}
				}
			}
		}
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
