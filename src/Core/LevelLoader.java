package Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader 
{
	private static String[] RemoveEmptyStrings(String[] data) {
		List<String> result = new ArrayList<String>();

		for (int i = 0; i < data.length; i++)
			if (!data[i].equals(""))
				result.add(data[i]);

		String[] res = new String[result.size()];
		result.toArray(res);

		return res;
	}
	
	public static ArrayList<GameObject> loadLevel(String fileName)
	{
		ArrayList<GameObject> res = new ArrayList<GameObject>();
		ArrayList<Vector2f> endPoints = new ArrayList<Vector2f>();
		BufferedReader reader = null;
		
		try
		{
			reader = new BufferedReader(new FileReader(fileName));
			String line;
			
			while((line = reader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = RemoveEmptyStrings(tokens);
				
				if (tokens[0].equals("#")) 
				{
					continue;
				}
				else if (tokens[0].equals("v")) 
				{
					endPoints.add(new Vector2f(Float.valueOf(tokens[1]), Float.valueOf(tokens[3])));
				}
			}
			
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		for(int i = 0; i < endPoints.size(); i++)
		{
			int latterIndex = i + 1;
			
			if(latterIndex == endPoints.size())
			{
				latterIndex = 0;
			}
			
			Line wallLine = new Line(endPoints.get(i), endPoints.get(latterIndex));
			
			res.add(new Wall(new Vector2f(0, 0), 0, wallLine, new Bitmap("./res/bricks.jpg")));
		}
		
		return res;
	}
}
