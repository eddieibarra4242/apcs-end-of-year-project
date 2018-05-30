package Core;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class RenderUtil 
{
	private static Bitmap frameBuffer;
	private static int FBID;
	
	public static void init(Bitmap fb)
	{
		frameBuffer = fb;
		FBID = glGenTextures();
		loadTexture();
		
		glClearColor(0, 0, 0, 0);
		
		glEnable(GL_TEXTURE_2D);
	}
	
	public static void clear(int shade)
	{
		frameBuffer.clear(shade);
	}
	
	public static void drawPixel(int x, int y, int a, int r, int g, int b)
	{
		frameBuffer.drawPixel(x, y, a, r, g, b);
	}
	
	public static void copyPixel(Bitmap texture, int srcx, int srcy, int destx, int desty)
	{
		frameBuffer.copyPixel(texture, srcx, srcy, destx, desty);;
	}
	
	public static void draw()
	{
		if(frameBuffer.hasChanged())
		{
			loadTexture();
		}
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		glBindTexture(GL_TEXTURE_2D, FBID);
		
		glBegin(GL_QUADS);
		{
			glVertex2d(-1, -1); glTexCoord2d(1, 1);
			glVertex2d( 1, -1); glTexCoord2d(1, 0);
			glVertex2d( 1,  1); glTexCoord2d(0, 0);
			glVertex2d(-1,  1); glTexCoord2d(0, 1);
		}
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public static void loadTexture()
	{
		ByteBuffer data = BufferUtils.createByteBuffer(4 * frameBuffer.getWidth() * frameBuffer.getHeight());
		
		for(int j = 0; j < frameBuffer.getHeight(); j++)
		{
			for(int i = 0; i < frameBuffer.getWidth(); i++)
			{
				int pixel = frameBuffer.readPixel(i, j);
				
				data.put((byte)((pixel & 0x00ff0000) >> 16)); //R
				data.put((byte)((pixel & 0x0000ff00) >>  8)); //G
				data.put((byte)((pixel & 0x000000ff)      )); //B
				data.put((byte)((pixel & 0xff000000) >> 24)); //A data.put((byte)(0xff));
			}
		}
		
		data.flip();
		
		glBindTexture(GL_TEXTURE_2D, FBID);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, frameBuffer.getWidth(), frameBuffer.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public static Bitmap getFrameBuffer()
	{
		return frameBuffer;
	}
	
	public static float getAR()
	{
		return (float)frameBuffer.getWidth() / (float)frameBuffer.getHeight();
	}
}
