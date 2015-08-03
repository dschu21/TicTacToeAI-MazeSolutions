/*=================================================================
Program name- Spring Assignment, Project Frame
Author - Danyon Chu
Date- March 08, 2014
Programming Language, Version Number- Java v1.7
=================================================================
Problem Definition - Required to create a frame used to hold a class and allows a user to choose what program to run
Input - user inputs a location they want to put their piece in
Output - output result of the move/game
Process - determine whether or not a location is filled and constantly switch who's turn it is to place a piece
as well check each possible way to move and whether or not it will lead to the loss or victory of a computer generated move
=================================================================
List of Identifiers (local variables written in each individual method)
---------------
 Variables
---------------
(int) WIDTH - constant for the width of the frame used to hold the program TicTacToe
(int) HEIGHT - constant for the height of the frame used to hold the program TicTacToe
(int) WIDTH1 - constant for the width of the frame used to hold the program MouseMaze
(int) HEIGHT1 - constant for the height of the frame used to hold the program MouseMaze

=================================================================
 */ 
package SpringAssignment; //allows access to the SpringAssignment package

import javax.swing.JFrame;//allows access to the javax.swing.JFrame class
import javax.swing.JOptionPane;

public class ProjectFrame {
	final public static int WIDTH = 750;
	final public static int HEIGHT = 800;
	final public static int WIDTH1 = 1250;
	final public static int HEIGHT1 = 800;

	/**main method:
	 * This procedural method is called automatically and is used to set the initial state of the JFrame and add the tictactoe class to the frame
	 * Local Variables
	 * (int) number - used to determine which button is clicked in the JOptionPane
	 * (String[]) options - used to hold Strings to display in the JOptionPane
	 * (Jframe) frame - used to hold the JPanel for each game class
	 * 
	 * @param <String[] args>
	 * 
	 * @return void
	 */

	public static void main(String [] args){
		String options[]= new String[] {"Tic Tac Toe", "Mouse Maze"};
		JFrame frame = new JFrame();
	
		int number = JOptionPane.showOptionDialog(frame,"Please choose which game you would like to play:", "Choose your selection", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if(number==0){
			frame.setSize(WIDTH,HEIGHT);
			frame.add(new TicTacToe(WIDTH,HEIGHT));
		}
		else if (number==1){
			frame.setSize(WIDTH1,HEIGHT1);
			frame.add(new MouseMaze(WIDTH1,HEIGHT1));
		}
		else
			System.exit(0);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}//end main method
}//end TicTacToeFrame class
