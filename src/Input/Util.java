package Input;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;

public class Util 
{
	public static final Random random = new Random();
	
	public static FloatBuffer createFloatBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}
	
	public static IntBuffer createIntBuffer(int size)
	{
		return BufferUtils.createIntBuffer(size);
	}
	
	public static ByteBuffer createByteBuffer(int size)
	{
		return BufferUtils.createByteBuffer(size);
	}
	
	public static IntBuffer createFlippedBuffer(int... values)
	{
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		
		return buffer;
	}
	
//	public static FloatBuffer createFlippedBuffer(Matrix4f value)
//	{
//		FloatBuffer buffer = createFloatBuffer(4 * 4);
//		
//		for(int i = 0; i < 4; i++)
//			for(int j = 0; j < 4; j++)
//				buffer.put(value.get(i, j));
//		
//		buffer.flip();
//		
//		return buffer;
//	}
	
	public static String[] removeEmptyStrings(String[] data)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		for(int i = 0; i < data.length; i++)
			if(!data[i].equals(""))
				result.add(data[i]);
		
		String[] res = new String[result.size()];
		result.toArray(res);
		
		return res;
	}
	
	public static int[] toIntArray(Integer[] data)
	{
		int[] result = new int[data.length];
		
		for(int i = 0; i < data.length; i++)
			result[i] = data[i].intValue();
		
		return result;
	}
	
	public static double clamp(double val, double min, double max) {
		if (val < min) {
			val = min;
		} else if (val > max) {
			val = max;
		}
		return val;
	}
	
	public static float exponent(float base, float exp)
	{
		float result = 1;
		
		for(int i = 0; i < exp; i++)
		{
			result *= base;
		}
		
		return result;
	}
	
	public static byte[] toByteArray(short[] src)
	{
		byte[] dest = new byte[src.length];
		
		for(int i = 0; i < src.length; i++)
		{
			dest[i] = (byte)src[i];
		}
		
		return dest;
	}
	
	public static ByteBuffer convertAudioBytes(byte[] audio_bytes, boolean two_bytes_data, ByteOrder order) 
	{
		ByteBuffer dest = ByteBuffer.allocateDirect(audio_bytes.length);
		dest.order(ByteOrder.nativeOrder());
		ByteBuffer src = ByteBuffer.wrap(audio_bytes);
		src.order(order);
		if (two_bytes_data) {
			ShortBuffer dest_short = dest.asShortBuffer();
			ShortBuffer src_short = src.asShortBuffer();
			while (src_short.hasRemaining())
				dest_short.put(src_short.get());
		} else {
			while (src.hasRemaining())
				dest.put(src.get());
		}
		dest.rewind();
		return dest;
	}
	
	public static float random(float min, float max)
	{
		return (float)(Math.random() * (max - min)) + min;
	}
	
	public static int random(int min, int max)
	{
		return (random.nextInt(max - min)) + min;
	}
	
	public static double now()
	{
		return System.nanoTime() / 1000000000d;
	}
	
	public static float lerp(float src, float dest, float lerp)
	{
		return (src * (1.0f - lerp)) + (dest * lerp);
	}
}