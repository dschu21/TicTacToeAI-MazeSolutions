/*=================================================================
Program name- Spring Assignment, Generate Maze
Author - Danyon Chu
Date- March 20, 2014
Programming Language, Version Number- Java v1.7
=================================================================
Problem Definition - Required to create a program that will generate a random maze
Input - n/a
Output - a maze
Process - start with all walls and create corridors by checking to see if adjacent nodes are a wall or corridor
=================================================================
Local variables written in each individual method

=================================================================
 */ 

package SpringAssignment;// allows access to SpringAssignment package

import java.util.*;//allows access to the java.util class

public class GenerateMaze {

	/**main method:
	 * This functional method is called automatically and is used to create a randomized maze
	 * 
	 * Local Variables
	 *(char[][]) maze - used to hold the values of a randomized maze
	 *(int) r - holds the # of rows the maze will contain
	 *(int) c - holds the # of columns the maze will contain
	 *(Point) st - creates an object of point class and holds a randomized point to start the maze at
	 *(StringBuilder) s - object allows access to StringBuilder class
	 *(Point) last - holds the last location marked
	 *(Point) cu - holds the position of a random node in the frontier
	 *(Point) op - holds the position of the opposite node from a random node (cu) in the frontier
	 *(ArrayList<>) frontier - an array list to hold the possible nodes around the starting node in which you can create a new path
	 *
	 * @param <String[] args>
	 * args - required for main method
	 * 
	 * @return void
	 */

	public static char[][] main(String[]args) {
		// dimensions of generated maze
		int r=7, c=11;

		// build maze and initialize with only walls
		StringBuilder s = new StringBuilder(c); //creates a StringBuilder object with 'c' spaces
		for(int x=0;x<c;x++)
			s.append('B'); //fills the array with walls
		char[][] maze = new char[r][c];
		for(int x=0;x<r;x++) //fills the first element of each row in the array with walls that are 'c' thick
			maze[x] = s.toString().toCharArray();


		// select random point and open as start node
		Point st = new Point((int)(Math.random()*r),(int)(Math.random()*c),null);
		maze[st.r][st.c] = ' ';

		// iterate through direct neighbors of node
		ArrayList<Point> frontier = new ArrayList<Point>();

		for(int x=-1;x<=1;x++)  //this nested loop + condition allows program to check only adjacent nodes (no diagonals)
			for(int y=-1;y<=1;y++){
				if(x==0 && y==0||x!=0 && y!=0)
					continue;
				try{
					if(maze[st.r+x][st.c+y]==' ') 
						continue;
				}catch(Exception e){ // ignore ArrayIndexOutOfBounds
					continue;
				}
				// add eligible points to frontier
				frontier.add(new Point(st.r+x,st.c+y,st));

			}

		Point last=null;
		while(!frontier.isEmpty()){

			// pick current node at random
			Point cu = frontier.remove((int)(Math.random()*frontier.size()));
			Point op = cu.opposite();

			try{
				// if both node and its opposite are walls
				if(maze[cu.r][cu.c]=='B'){
					if(maze[op.r][op.c]=='B'){

						// open path between the nodes
						maze[cu.r][cu.c]=' ';
						maze[op.r][op.c]=' ';

						// store last node in order to mark it later
						last = op;

						// iterate through direct neighbors of node, same as earlier
						for(int x=-1;x<=1;x++)
							for(int y=-1;y<=1;y++){
								if(x==0&&y==0||x!=0&&y!=0)
									continue;
								try{
									if(maze[op.r+x][op.c+y]=='.') continue;
								}catch(Exception e){
									continue;
								}
								frontier.add(new Point(op.r+x,op.c+y,op));
							}
					}
				}
			}catch(Exception e){ // ignore NullPointer and ArrayIndexOutOfBounds
			}

			// if algorithm has resolved, mark end node
			if(frontier.isEmpty())
				maze[last.r][last.c]='X';
		}

		return maze;
	}//end main method

	static class Point{
		Integer r;
		Integer c;
		Point parent;

		/**Point constructor:
		 * This constructor is called automatically upon running the program. Sets up the initial states of the Point
		 * 
		 * @param <int x, int y, Point p>
		 * x -row # of point that is to be added to the frontier
		 * y - column # of point that is to be added to the frontier
		 * p - point that is to be added to the frontier
		 * 
		 */

		public Point(int x, int y, Point p){
			r=x;c=y;parent=p;
		}//end  Point constructor

		/**opposite method:
		 * This functional method is called to find a node opposite to a parent node
		 * 
		 * @param <ActionEvent evt>
		 * evt - object used to check what events are happening in the JFrame. Ex. clicking a Jbutton
		 * 
		 * @return void
		 */

		// compute opposite node given that it is in the other direction from the parent
		public Point opposite(){
			if(this.r.compareTo(parent.r)!=0)
				return new Point(this.r+this.r.compareTo(parent.r),this.c,this);
			if(this.c.compareTo(parent.c)!=0)
				return new Point(this.r,this.c+this.c.compareTo(parent.c),this);
			return null;
		}//end opposite method
	}//end Point class
}//end GenerateMaze class
