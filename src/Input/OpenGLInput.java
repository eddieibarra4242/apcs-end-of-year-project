/** 
 * Copyright (c) 2015, Benny Bobaganoosh. All rights reserved.
 * License terms are in the included LICENSE.txt file.
 */
package Input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import Core.Vector2f;

/**
 * An implementation of IInput compatible with {@link engine.rendering.opengl.OpenGLDisplay}.
 * 
 * @author Benny Bobaganoosh (thebennybox@gmail.com)
 */
public class OpenGLInput implements IInput {
	private long inputSource;
	private DoubleBuffer mouseX;
	private DoubleBuffer mouseY;
	private FloatBuffer[] joystickAxes;
	private ByteBuffer[] joystickButtons;
	private Vector2f mouseDelta2D;
	private boolean hasBeenUpdated;
	
	private double halfWidth;
	private double halfHeight;
	
	private static boolean[] lastKeys = new boolean[IInput.KEY_LAST];
	private static boolean[] lastMouse = new boolean[IInput.MOUSE_BUTTON_LAST];

	public OpenGLInput(long inputSource, int displayWidth, int displayHeight) {
		this.inputSource = inputSource;
		this.mouseX = BufferUtils.createDoubleBuffer(1);
		this.mouseY = BufferUtils.createDoubleBuffer(1);
		this.joystickAxes = new FloatBuffer[IInput.JOYSTICK_LAST];
		this.joystickButtons = new ByteBuffer[joystickAxes.length];
		this.mouseDelta2D = new Vector2f(0, 0);
		this.hasBeenUpdated = false;
		
		halfWidth = displayWidth / 2.0;
		halfHeight = displayHeight / 2.0;
	}

	@Override
	public boolean getMouse(int button) {
		return glfwGetMouseButton(inputSource, button) == GLFW_PRESS ? true
				: false;
	}

	private void initJoystick(int i) {
		if (joystickAxes[i] == null) {
			updateJoystick(i);
		}
	}

	private void updateJoystick(int i) {
		FloatBuffer newAxes = glfwGetJoystickAxes(i);
		ByteBuffer newButtons = glfwGetJoystickButtons(i);

		if (newAxes != null) {
			joystickAxes[i] = newAxes;
		} else if (joystickAxes[i] == null) {
			joystickAxes[i] = createDefaultJoystickAxes();
		}

		if (newButtons != null) {
			joystickButtons[i] = newButtons;
		} else if (joystickButtons[i] == null) {
			joystickButtons[i] = createDefaultJoystickButtons();
		}
	}

	private ByteBuffer createDefaultJoystickButtons() {
		return BufferUtils.createByteBuffer(0);
	}

	private FloatBuffer createDefaultJoystickAxes() {
		return BufferUtils.createFloatBuffer(0);
	}

	private void updateJoysticks() {
		for (int i = 0; i < joystickAxes.length; i++) {
			if (joystickAxes[i] == null) {
				continue;
			}

			updateJoystick(i);
		}
	}

	private void updateMouse() {
		Vector2f mousePosBefore = getMouse2D();
		glfwGetCursorPos(inputSource, mouseX, mouseY);
		
		if (hasBeenUpdated) {
			mouseDelta2D = getMouse2D().sub(mousePosBefore);
		}
		hasBeenUpdated = true;
	}

	@Override
	public void update() {
		updateMouse();
		updateJoysticks();
		
		for(int i = 0; i < IInput.KEY_LAST; i++)
			lastKeys[i] = getKey(i);
		
		for(int i = 0; i < IInput.MOUSE_BUTTON_LAST; i++)
			lastMouse[i] = getMouse(i);
	}

	@Override
	public boolean getKey(int code) {
		return glfwGetKey(inputSource, code) == GLFW_PRESS ? true : false;
	}

	@Override
	public Vector2f getMouse2D() {
		return new Vector2f((float)(mouseX.get(0) / halfWidth - 1), (float)(mouseY.get(0) / halfHeight - 1));
	}

	@Override
	public Vector2f getMouseDelta2D() {
		return mouseDelta2D;
	}

	@Override
	public String getJoystickName(int joystick) {
		return glfwGetJoystickName(joystick);
	}
	
	@Override
	public int getNumJoystickAxes(int joystick) {
		initJoystick(joystick);
		return joystickAxes[joystick].capacity();
	}

	@Override
	public double getJoystickAxis(int joystick, int axis) {
		int numAxes = getNumJoystickAxes(joystick);
		if (axis < 0 || axis >= numAxes) {
			return 0.0;
		}
		return (double) joystickAxes[joystick].get(axis);
	}

	@Override
	public int getNumJoystickButtons(int joystick) {
		initJoystick(joystick);
		return joystickButtons[joystick].capacity();
	}

	@Override
	public boolean getJoystickButton(int joystick, int button) {
		int numButtons = getNumJoystickButtons(joystick);
		if (button < 0 || button >= numButtons) {
			return false;
		}
		return joystickButtons[joystick].get(button) == 1 ? true : false;
	}

	@Override
	public void setCursor(boolean enable) 
	{
		glfwSetInputMode(inputSource, GLFW_CURSOR, enable ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_HIDDEN);
	}

	@Override
	public void setCursorPosition(Vector2f pos) 
	{
		glfwSetCursorPos(inputSource, pos.getX() * halfWidth + halfWidth, pos.getY() * halfHeight + halfHeight);
	}

	@Override
	public boolean getKeyDown(int code) {
		return getKey(code) && !lastKeys[code];
	}

	@Override
	public boolean getKeyUp(int code) {
		return !getKey(code) && lastKeys[code];
	}

	@Override
	public boolean getMouseDown(int button) {
		return getMouse(button) && !lastMouse[button];
	}

	@Override
	public boolean getMouseUp(int button) {
		return !getMouse(button) && lastMouse[button];
	}
}
