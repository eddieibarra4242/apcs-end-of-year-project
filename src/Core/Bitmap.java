package Core;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bitmap 
{
	private int width;
	private int height;
	private int[] pixels;
	private boolean hasChanged;
	
	public Bitmap(String fileName)
	{
		try 
		{
			BufferedImage image = ImageIO.read(new File(fileName));
			
			width = image.getWidth();
			height = image.getHeight();
			pixels = image.getRGB(0, 0, width, height, null, 0, width);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		hasChanged = true;
	}
	
	public Bitmap(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		
		hasChanged = true;
	}
	
	private int clamp(int max, int min, int val)
	{
		if(val > max)
		{
			return max;
		}
		
		if(val < min)
		{
			return min;
		}
		
		return val;
	}
	
	private int compressColor(int a, int r, int g, int b)
	{
		a = clamp(255, 0, a);
		r = clamp(255, 0, r);
		g = clamp(255, 0, g);
		b = clamp(255, 0, b);
		
		return (a << 24) | (r << 16) | (g << 8) | b;
	}
	
	private boolean shouldClip(int x, int y)
	{
		return x >= width || x < 0 ||
				y >= height || y < 0;
	}
	
	public void clear(int shade)
	{
		clear(shade, shade, shade, shade);
	}
	
	public void clear(int a, int r, int g, int b)
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = compressColor(a, r, g, b);
		}
		hasChanged = true;
	}
	
	public void drawPixel(int x, int y, int a, int r, int g, int b)
	{
		if(shouldClip(x, y))
		{
			return;
		}
		
		pixels[x + y * width] = compressColor(a, r, g, b);
		hasChanged = true;
	}
	
	public void copyPixel(Bitmap src, int srcx, int srcy, int destx, int desty)
	{
		if(src.shouldClip(srcx, srcy) || shouldClip(destx, desty))
		{
			return;
		}
		
		pixels[destx + desty * width] = src.readPixel(srcx, srcy);
		hasChanged = true;
	}
	
	public int readPixel(int x, int y)
	{
		if(shouldClip(x, y))
		{
			return 0;
		}
		
		return pixels[x + y * width];
	}
	
	public boolean hasChanged()
	{
		boolean res = hasChanged;
		
		hasChanged = false;
		
		return res;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
