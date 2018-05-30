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
			
			Vector2f intersectionPoint = null;
			Wall hitWall = null;
			
			float shortest = 1001;
			
			for(int i = 0; i < objects.size(); i++)
			{
				float dist = objects.get(i).intersect(ray);
				
				if(dist < shortest && dist > 0)
				{
					intersectionPoint = ray.getPoint(dist);
					hitWall = (Wall) objects.get(i);
				}
			}
			
			if(intersectionPoint != null)
			{	
				float dist = direction.dot(camera.getForward()) * camera.getPosition().sub(intersectionPoint).length();
				float check = 0.4f/dist;
				
				for(int y = 0; y < height; y++)
				{
					float ndcy = (y + 0.5f) * 2 / height - 1;
					
					if(ndcy < check && ndcy > -check)
					{	
						int texWidth = hitWall.getTexture().getWidth();
						int texHeight = hitWall.getTexture().getHeight();
						
						float yRange = hitWall.getMaxTexCoord().getY() - hitWall.getMinTexCoord().getY();
						float xRange = hitWall.getMaxTexCoord().getX() - hitWall.getMinTexCoord().getX();
						
						float texy = ((ndcy / (2.0f * check)) + 0.5f) * yRange + hitWall.getMinTexCoord().getY();
						float texx = (intersectionPoint.sub(hitWall.getWall().getL0()).length() / hitWall.getWall().length()) * xRange + hitWall.getMinTexCoord().getX();
						
						RenderUtil.copyPixel(hitWall.getTexture(), (int)(texx * texWidth), (int)(texy * texHeight), x, y);
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
