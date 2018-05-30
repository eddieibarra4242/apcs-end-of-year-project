package Core;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import Input.IInput;
import Input.OpenGLInput;

public class Window 
{
	private long window;
	
	private int width;
	private int height;
	private String title;
	
	private IInput input;
	
	public Window(int width, int height, String title)
	{
		this(width, height, title, 2);
	}
	
	public Window(int width, int height, String title, int samples)
	{
		this(width, height, title, samples, false);
	}
	
	public Window(int width, int height, String title, int samples, boolean fullscreen)
	{
		if(!glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
		glfwWindowHint(GLFW_SAMPLES, samples);
		
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetMonitors().get(0) : NULL, NULL);
		
		if(window == NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		GLFWVidMode v = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (v.width() - width) / 2, (v.height() - height) / 2);
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);
		GL.createCapabilities();
		
		input = new OpenGLInput(window, width, height);
	}
	
	public boolean shouldClose()
	{
		return glfwWindowShouldClose(window);
	}
	
	public void present() 
	{
		IntBuffer widthi = BufferUtils.createIntBuffer(1);
		IntBuffer heighti = BufferUtils.createIntBuffer(1);
		
		glfwGetFramebufferSize(window, widthi, heighti);
		
		width = widthi.get(0);
		height = heighti.get(0);
		
		glViewport(0,0,width,height);
		
		glfwSwapBuffers(window);
	}

	public void update() 
	{
		glfwPollEvents();
		input.update();
	}
	
	public void setDimensions(int width, int height, boolean fullscreen)
	{
		glfwDestroyWindow(window);
		
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
		
		if(window == NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}
		
		GLFWVidMode v = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (v.width() - width) / 2, (v.height() - height) / 2);
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		GL.createCapabilities();
	}
	
	public void setTitle(String title) 
	{
		glfwSetWindowTitle(window, title);
	}
	
	public int getWidth()
	{	
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public float getAspectRatio()
	{
		return (float)getWidth()/(float)getHeight();
	}
	
	public void destroyWindow()
	{
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public long getWindow()
	{
		return window;
	}

	public IInput getInput() {
		return input;
	}

	public void setInput(IInput input) {
		this.input = input;
	}
}
