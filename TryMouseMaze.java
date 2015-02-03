/*=================================================================
Program name- Spring Assignment, Mouse Maze
Author - Danyon Chu
Date- March 10, 2014
Programming Language, Version Number- Java v1.7
=================================================================
Problem Definition - Required to create a program which allows a user to select a point in a maze and be given the direction out of the maze
Input - user inputs a start location
Output - output the path out of the maze
Process - check each possible way to go using recursion and if the program finds a path to the exit point, it saves that path as the way out
=================================================================
List of Identifiers (local variables written in each individual method)
---------------
 Variables
---------------
(boolean) foundKey - variable used to determine what button is pressed
(JPanel) putMaze - used to put the maze into a JPanel for easier formatting
(JButton[][]) maze - an array of JButtons that allows the user to click a certain location in the maze
(char[][]) determine - used to hold the returned value of a randomized maze
(String[][]) walls - an array of strings 7x11 that holds the location of the walls and corridors in the maze
(int) col - holds the col # of the exit
(int) row - holds the row # of the exit
(GenerateMaze) generate - allows access to the GenerateMaze class by creating an object of that class
(String[]) args - required to send as argument to access main method
(JButton) restart - allows the user to find another path out of the same maze
(JButton) newGame - allows the user to solve a new maze
(JButton) exit - allows the user to close the program
=================================================================
 */ 
package SpringAssignment;// allows access to the MouseMaze package

import java.awt.Color; // allows access to the java.awt.Color class
import java.awt.Dimension; // allows access to the java.awt.Dimension class
import java.awt.GridLayout; // allows access to the java.awt.GridLayout class
import java.awt.event.ActionEvent; // allows access to the java.awt.event.ActionEvent class
import java.awt.event.ActionListener; // allows access to the java.awt.event.ActionListener class
import javax.swing.JButton; // allows access to the javax.swing.JButton class
import javax.swing.JPanel; // allows access to the javax.swing.JPanel class


public class TryMouseMaze extends JPanel implements ActionListener {

	String walls[][]= new String [7][11];
	char[][] determine = new char[7][11];
	JPanel putMaze;
	JButton maze[][]= new JButton [7][11];
	JButton restart;                //still need to comment this
	JButton exit; 
	JButton newGame;
	boolean foundKey=false;
	int col;
	int row;
	private GenerateMaze generate;         
	private String[] args;                          

	/**MouseMaze constructor:
	 * This constructor is called automatically upon running the program. Sets up the initial states of the maze
	 * 
	 * @param <int width, int height>
	 * width - constant for the width of the frame used to hold the program MouseMaze
	 * height - constant for the height of the frame used to hold the program MouseMaze
	 * 
	 */

	public TryMouseMaze(int width, int height, GenerateMaze g) {

		generate=g;
		determine=generate.main(args);
		for (byte i=0;i<walls.length;i++)                       
			for (byte n=0;n<walls[i].length;n++){
				walls[i][n]=String.valueOf(determine[i][n]);
				if(walls[i][n].equalsIgnoreCase("X")){
					row=i;
					col=n;
				}
			}


		putMaze=new JPanel();
		putMaze.setLayout(new GridLayout(7,11));
		for (byte i=0;i<walls.length;i++){
			for (byte n=0;n<walls[i].length;n++){
				maze[i][n]=new JButton();

				maze[i][n].setPreferredSize(new Dimension(90, 90));
				maze[i][n].setText(walls[i][n]);
				maze[i][n].setOpaque(true);
				maze[i][n].addActionListener(this);
				putMaze.add(maze[i][n]);
			}
		}
		restart = new JButton("Restart");
		restart.addActionListener(this);
		exit = new JButton("Exit");
		exit.addActionListener(this);
		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		add(restart);
		add(exit);
		add(newGame);
		add(putMaze);
	}

	/**actionPerformed method:
	 * This procedural method is called automatically when an action is performed and is
	 * used to find if and what JButton was pressed
	 * 
	 * @param <ActionEvent evt>
	 * evt - object used to check what events are happening in the JFrame. Ex. clicking a Jbutton
	 * 
	 * @return void
	 */

	public void actionPerformed(ActionEvent evt) {
		for (byte i=0;i<maze.length && !foundKey;i++){
			for (byte n=0;n<maze[i].length && !foundKey;n++){
				if(evt.getSource()==maze[i][n]){
					if(walls[i][n].equals(" ") || walls[i][n].equalsIgnoreCase("X")){
						foundKey=true;
						mark(i,n);
					}
				}
			}
		}
		if (evt.getSource()==restart){

			for (byte i=0;i<walls.length;i++){                    
				for (byte n=0;n<walls[i].length;n++){
					walls[i][n]=String.valueOf(determine[i][n]);
					maze[i][n].setEnabled(true);
					maze[i][n].setBackground(null);
				}
			}
			foundKey=false;
		}

		if (evt.getSource()==exit){
			System.exit(0);
		}
		if (evt.getSource()==newGame){
			determine=generate.main(args);
			for (byte i=0;i<walls.length;i++){                     
				for (byte n=0;n<walls[i].length;n++){
					maze[i][n].setEnabled(true);
					maze[i][n].setBackground(null);
					walls[i][n]=String.valueOf(determine[i][n]);
					maze[i][n].setText(walls[i][n]);
					if(walls[i][n].equalsIgnoreCase("X")){
						row=i;
						col=n;
					}
				}
			}
			foundKey=false;
		}

	}//end ActionPerformed method

	/**mark method:
	 * This procedural method calls findExit as well as initially marks the starting location and end location in the maze
	 * 
	 * @param <byte i, byte n>
	 * i - used to hold the row # of the button clicked
	 * n - used to hold the column # of the button clicked
	 * 
	 * @return void
	 */

	private void mark(byte i, byte n) {

		maze[i][n].setBackground(Color.red);
		walls[i][n]="B";
		maze[row][col].setBackground(Color.red);

		for (byte j=0;j<maze.length;j++)
			for (byte k=0;k<maze[j].length;k++)
				if(walls[j][k].equals(" ") || walls[j][k].equalsIgnoreCase("X"))
					maze[j][k].setEnabled(false);


		findExit(i,n);
	}//end ActionPerformed method

	/**findExit method:
	 * This functional method is used to check all the possible paths to find the exit using recursion
	 * 
	 * @param <byte i, byte n>
	 * i - used to hold the row # of the spot the program is currently at
	 * n - used to hold the column # of the spot the program is currently at
	 * 
	 * @return true/false depending on if the exit is found
	 */
	private boolean findExit(int i, int n) {

		if(mazeSolved(i,n))
			return true;
		else{    

			if (canMove(i-1,n)){
				drawPath(i-1,n);
				if(findExit(i-1,n))
					return true;
				erasePath(i-1,n);
			}

			if (canMove(i+1,n) ){
				drawPath(i+1,n);
				if(findExit(i+1,n))
					return true;
				erasePath(i+1,n);
			}

			if (canMove(i,n-1) ){
				drawPath(i,n-1);
				if(findExit(i,n-1))
					return true;
				erasePath(i,n-1);
			}

			if (canMove(i,n+1)){
				drawPath(i,n+1);
				if(findExit(i,n+1))
					return true;
				erasePath(i,n+1);
			}
		}

		return false;
	}//end findExit method

	/**canMove method:
	 * This functional method is used to check and return if the program can move to that location
	 * 
	 * @param <byte i, byte n>
	 * i - used to hold the row # of the spot the program is currently checking
	 * n - used to hold the column # of the spot the program is currently checking
	 * 
	 * @return true/false depending on if there is no wall in the location you are checking in
	 */

	private boolean canMove(int i, int n) {
		if(i<0 || n<0 || i>walls.length-1 || n>walls[i].length-1)
			return false;
		else
			if(walls[i][n].equals(" ") || walls[i][n].equalsIgnoreCase("X"))
				return true;
			else
				return false;

	}//end canMove method   

	/**drawPath method:
	 * This procedural method is used to mark a spot the computer has been to so that it does not backtrack
	 * 
	 * @param <byte i, byte n>
	 * i - used to hold the row # of the spot the program is currently marking
	 * n - used to hold the column # of the spot the program is currently marking
	 * 
	 * @return void
	 */

	private void drawPath(int i, int n) {
		maze[i][n].setBackground(Color.red);
		walls[i][n]="B";
		validate();
	}//end drawPath method

	/**erasePath method:
	 * This procedural method is used to erase a spot the computer has been to if it does not lead to the exit
	 * 
	 * @param <byte i, byte n>
	 * i - used to hold the row # of the spot the program is currently marking
	 * n - used to hold the column # of the spot the program is currently marking
	 * 
	 * @return void
	 */

	private void erasePath(int i, int n) {
		maze[i][n].setBackground(null);
		walls[i][n]=" ";
		validate();
	}//end erasePath method

	/**mazeSolved method:
	 * This functional method is used to determine if the computer has found the exit
	 * 
	 * @param <byte i, byte n>
	 * i - used to hold the row # of the spot the program is currently at
	 * n - used to hold the column # of the spot the program is currently at
	 * 
	 * @return true/false if the exit has been found
	 */

	private boolean mazeSolved(int i, int n) {
		if(i==row && n==col)
			return true;
		else
			return false;
	}//end mazeSolved method
}//end MouseMaze class
