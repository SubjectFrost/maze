package maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MazeSolver {
	public MazeSolver(String filename) {
		MazeParser mp = new MazeParser(filename);
		
		List<Coord> coordList = new ArrayList<Coord>();
		
		if (mp.maze != null) {
			//make an array list of all the empty coordinates
			for (int i = 0; i < mp.maze.length; i++) {
				for (int j = 0; j < mp.maze[0].length; j++) {
					if(' ' == mp.maze[i][j]) {
						coordList.add(new Coord(j, i));
					}
				}
			}
			
			
			List<Coord> moves = Solve(coordList.get(0), coordList.get(coordList.size() - 1), coordList);
			System.out.println(writeDirections(moves));
		} else {
			System.out.println("file empty");
		}
		
	}
	
	public List<Coord> Solve(Coord st, Coord end, List<Coord> coordList) {
		List<Coord> moves = new ArrayList<Coord>(); //arraylist of the moves to the solution
		if (st.equals(end)) {
			//if the start is the end, return an empty moves arraylist
			return moves;
		}
		
		LinkedList<Coord> list = new LinkedList<Coord>(); //my queue for bfs
		//add and pop for queue behavior
		
		List<Coord> nlist = st.getNeighbours(coordList);
		Coord cur = st;
		
		while (! cur.equals(end)) {
			nlist = cur.getNeighbours(coordList);
			cur.e = 2;
			for (Coord x : nlist) {
				x.e = 1;
				x.parent = cur;
				list.add(x);
			}
			//needs empty list protection unless there is a guaranteed solution.
			cur = list.pop();
		}
		while (cur != null) {
			moves.add(cur);
			cur = cur.parent;
		}
		return moves;
	}
	
	public List<String> writeDirections(List<Coord> moves) {
		List<String> directions = new ArrayList<String>();
		List<String> finalDirections = new ArrayList<String>();
		for (int i = moves.size() - 1; i > 0; i--) {
			directions.add(direction(moves.get(i), moves.get(i - 1)));
		}
		
		if (directions.isEmpty()) {
			return directions;
		}
		
		String cur = directions.get(0);
		int counter = 1;
		for (int i = 1; i < directions.size() - 1; i++) {
			if (cur.equals(directions.get(i))) {
				counter++;
			} else {
				finalDirections.add(cur +" "+ counter);
				cur = directions.get(i);
				counter = 1;
			}
		}
		finalDirections.add(cur +" "+ counter);
		
		return finalDirections;
	}
	
	public String direction(Coord st, Coord dest) {
		if (st.x < dest.x) {
			return "Right";
		}
		if (st.x > dest.x) {
			return "Left";
		}
		if (st.y > dest.y){
			return "Up";
		}
		if (st.y < dest.y) {
			return "Down";
		}
		return "broken";
	}
}

class Coord {
	public int x;
	public int y;
	public int e; //undiscovered = 0, discovered = 1 (added to the queue), explored = 2 (popped from the queue)
	public Coord parent;
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
		this.e = 0;
	}
	
	public boolean equals(Coord x) {
		if (this.x == x.x && this.y == x.y) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<Coord> getNeighbours(List<Coord> coordList) {
		//returns an arraylist of empty, undiscovered coords (possible moves) to this coord
		List<Coord> nlist = new ArrayList<Coord>();
		for (int i = 0; i < coordList.size(); i++) {
			//add to neighbours if it is undiscovered
			if (coordList.get(i).e == 0) {
				//add to neighbours if shares the x axis and differs on y axis by 1
				if (coordList.get(i).x == this.x) {
					if (coordList.get(i).y == this.y + 1) {
						nlist.add(coordList.get(i));
					}
					if (coordList.get(i).y == this.y - 1) {
						nlist.add(coordList.get(i));
					}
				}
				if (coordList.get(i).y == this.y) {
					//add to neighbours if shares the y axis and differs on x axis by 1
					if (coordList.get(i).x == this.x + 1) {
						nlist.add(coordList.get(i));
					}
					if (coordList.get(i).x == this.x - 1) {
						nlist.add(coordList.get(i));
					}
				}
		}
		}
		
		return nlist;
	}
	
	public String toString() {
		String ret = "["+ x +","+ y +"]";
		return ret;
	}
}
