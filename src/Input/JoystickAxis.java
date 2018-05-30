/** 
 * Copyright (c) 2015, Benny Bobaganoosh. All rights reserved.
 * License terms are in the included LICENSE.txt file.
 */
package Input;

/**
 * Axis from a joystick.
 * 
 * @author Benny Bobaganoosh (thebennybox@gmail.com)
 */
public class JoystickAxis implements IAxis {
	private IInput input;
	private int joystick;
	private int[] joystickAxes;
	private float deadZone;
	
	/**
	 * Creates a new JoystickAxis
	 * 
	 * @param input
	 *            The input system
	 * @param joystick
	 *            The joystick. Should be one of the IInput.JOYSTICK values.
	 * @param joystickAxis
	 *            The axis on the joystick being checked.
	 */
	public JoystickAxis(IInput input, int joystick, int joystickAxis) {
		this(input, joystick, new int[] { joystickAxis }, 0.2f);
	}

	/**
	 * Creates a new JoystickAxis
	 * 
	 * @param input
	 *            The input system
	 * @param joystick
	 *            The joystick. Should be one of the IInput.JOYSTICK values.
	 * @param joystickAxes
	 *            The axes on the joystick being checked.
	 * @param deadZone
	 * 			  The dead zone of your joystick
	 */
	public JoystickAxis(IInput input, int joystick, int[] joystickAxes, float deadZone) {
		this.input = input;
		this.joystick = joystick;
		this.joystickAxes = joystickAxes;
		this.deadZone = deadZone;
	}

	@Override
	public double getAmount() {
		if (joystickAxes == null || joystick == -1) {
			return 0.0;
		}
		double result = 0.0;
		for (int i = 0; i < joystickAxes.length; i++) {
			result += input.getJoystickAxis(joystick, joystickAxes[i]);
		}
		
		result = Util.clamp(result, -1.0, 1.0);
		
		if(result < -deadZone || result > deadZone)
		{
			return result;
		}
		else
		{
			return 0;
		}
	}
}
