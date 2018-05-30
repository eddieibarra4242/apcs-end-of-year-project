package Core;
import java.util.ArrayList;

public class Main 
{
	private static String titleFormat = "Game | %f fps | %f ms per frame";
	
	public static void main(String[] args)
	{
		Window window = new Window(800, 600, titleFormat);
		
		RenderUtil.init(new Bitmap(800, 600));
		
		RenderUtil.clear(155);
		
		
		Camera c = new Camera(new Vector2f(0, 0), 0, (float)Math.PI/2.0f);
		
		RenderEngine renderEngine = new RenderEngine(c);
		
		Wall w = new Wall(new Vector2f(1, 0), 0, new Line(new Vector2f(0, -1), new Vector2f(0, 1)));
		
		ArrayList<GameObject> walls = new ArrayList<GameObject>();
		
		walls.add(w);
		
		double lastTime = System.nanoTime() / 1000000000d;
		
		double frametime = 0.0;
		
		while(!window.shouldClose())
		{
			double startTime = System.nanoTime() / 1000000000d;
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			frametime += passedTime;
			
			if(frametime >= 1.0/30.0)
			{
				window.setTitle(String.format(titleFormat, 1.0/passedTime, 1000*passedTime));
				frametime = 0.0;
			}
			
			window.update();
			
			c.rotate(1.0f/60.0f);
			
			RenderUtil.clear(0);
			
			renderEngine.render(walls);
			
			RenderUtil.draw();
			window.present();
		}
		
		window.destroyWindow();
	}
}
