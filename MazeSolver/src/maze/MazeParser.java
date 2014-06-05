package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeParser {
	public char[][] maze;

	public MazeParser(String filename) {
		BufferedReader br;
		int height; //height of the maze
		int len; //length of a line
		List<String> mazeList = new ArrayList<String>();
		
		try {
			br = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = br.readLine()) != null) {
				//put line into arraylist, then into 2d array character by character
				mazeList.add(line);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (! mazeList.isEmpty()) {
			height = mazeList.size();
			len = mazeList.get(0).length();
			maze = new char[height][len];
			String temp;
			int strlen = mazeList.get(0).length();
			int curlen;
			//convert arraylist into 2d array
			for (int i = 0; i < height; i++) {
				temp = mazeList.get(i);
				curlen = temp.length();
				for (int j = 0; j < Math.min(curlen, strlen); j++) {
					maze[i][j] = temp.charAt(j);
				}
			}
		}
		
		//print out the maze (testing)
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < len; j++) {
//				System.out.print(maze[i][j]);
//			}
//			System.out.print("\n");
//		}
		
	}
	
}

