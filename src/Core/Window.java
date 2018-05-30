package Core;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glViewport;
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
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
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
