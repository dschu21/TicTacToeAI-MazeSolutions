/*=================================================================
Program name- Spring Assignment, TicTacToe
Author - Danyon Chu
Date- March 10, 2014
Programming Language, Version Number- Java v1.7
=================================================================
Problem Definition - Required to create a program which allows a user to play tic tac toe human vs. human, human vs. computer, computer vs. computer
Input - player's move selection
Output - the current game board, winner of the game
Process - for ai to check each possible way to go using recursion and use the best next move to try to win the game
=================================================================
List of Identifiers (local variables written in each individual method)
---------------
 Variables
---------------
(int) check - used as a condition to perform actions when the timer is running
(int) number - determines what game mode the user wants to use
(int) restart - determines if the user wants to restart the game
(Font) font - holds a font that is needed to write the pieces down
(Font) titleFont - holds a font that is needed to write the title down
(Timer) timer - controls the speed at which computer vs. computer will play
(byte) player - determines which player's turn it is
(byte) game[][] - holds the current game board 
(String) name1 - holds the name of the first player
(String) name2 - holds the name of the second player
(int) x - is the value of the current x position of the cursor
(int) y - is the value of the current y position of the cursor
(JLabel) names - displays both players names during the game
(String[]) options - used to hold an array of strings to display the game mode
(int) SIDELENGTH - is the set side length of a board square
(int) INITX - is the set distance from the left the program starts drawing the board at
(int) INITY - is the set distance from the top the program starts drawing the board at
(boolean) start - determines if the program needs to randomize the first ai vs. ai move
=================================================================
 */ 
package SpringAssignment;// allows access to the SpringAssignment package

import java.awt.BasicStroke; // allows access to the java.awt.BasicStroke class
import java.awt.Color; // allows access to the java.awt.Color class
import java.awt.Font; // allows access to the java.awt.Font class
import java.awt.Graphics; // allows access to the java.awt.Graphics class
import java.awt.Graphics2D; // allows access to the java.awt.Graphics2D class
import java.awt.event.ActionEvent; // allows access to the java.awt.event.ActionEvent class
import java.awt.event.ActionListener; // allows access to the java.awt.event.ActionListener class
import java.awt.event.MouseEvent; // allows access to the java.awt.event.MouseEvent class
import java.awt.event.MouseListener; // allows access to the java.awt.event.MouseListener class
import javax.swing.JLabel; // allows access to the javax.swing.JLabel class
import javax.swing.JOptionPane; // allows access to the javax.swing.JOptionPane class
import javax.swing.JPanel; // allows access to the javax.swing.JPanel class
import javax.swing.Timer; // allows access to the javax.swing.Timer class

public class TicTacToe extends JPanel implements MouseListener, ActionListener {
	int check=0;
	int number;
	int restart;
	Font font = new Font("Times New Roman", Font.PLAIN, 90 );
	Font titleFont = new Font("Times New Roman", Font.PLAIN, 60 );
	Timer timer = new Timer(1000, this);

	//game
	byte player =1;
	byte game[][] = new byte [3][3];
	String name1, name2;
	int x,y;
	JLabel names;
	String options[] = {"Human vs Human", "Human vs Computer", "Computer vs Computer"};
	final int SIDELENGTH=150;
	final int INITX=150;
	final int INITY=300;
	int randomx;
	int randomy;
	boolean start=false;


	/**MouseMaze constructor:
	 * This constructor is called automatically upon running the program. Sets up the initial states of the board
	 * 
	 * @param <int w, int h>
	 * w - constant for the width of the frame used to hold the program TicTacToe
	 * h - constant for the height of the frame used to hold the program TicTacToe
	 * 
	 */

	public TicTacToe(int w, int h) {
		addMouseListener(this);
		this.setSize(WIDTH, HEIGHT);    
		String options[] = {"Human vs Human", "Human vs Computer", "Computer vs Computer"};
		number = JOptionPane.showOptionDialog(this,"Choose Your Game Mode:", "Options", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		names = new JLabel("");
		names.setFont(titleFont);
		if (number==0){
			name1= JOptionPane.showInputDialog("Player 1. Please enter your name.");
			name2= JOptionPane.showInputDialog("Player 2. Please enter your name.");
			names.setText(name1 +  " vs. "  + name2);
		}       
		else if (number==1){
			name1= JOptionPane.showInputDialog(" Please enter your name.");
			name2= "Computer";
			names.setText(name1 + " vs. " +  "Computer");
		}
		else if (number==2){
			name1= "Computer 1";
			name2= "Computer 2";
			names.setText("Computer 1" +  " vs. "  + "Computer 2");
			timer.start();
			check=1;
		}
		else
			System.exit(0);

		add(names);
	}//end TicTacToe constructor

	/**PaintComponent method:
	 * This procedural method is called to draw all graphics on screen
	 * 
	 * Local Variables
	 * ---------------
	 * (Graphics2D) g2 - used to gain access to the Graphics2D class
	 * (int) setx - is the x position at which the next board piece should be drawn at
	 * (int) sety - is the y position at which the next board piece should be drawn at
	 * 
	 * @param <Graphics g>
	 * g - used to draw on screen
	 * 
	 * @return void
	 */

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int  setx=150,  sety=300;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(10));
		g2.setFont(font);

		for (byte i=0;i<3;i++){
			for (byte n=0;n<3;n++){
				g2.setColor(Color.lightGray);
				g2.drawRect(setx, sety, SIDELENGTH, SIDELENGTH);
				g2.setColor(Color.cyan);
				g2.fillRect(setx, sety, SIDELENGTH, SIDELENGTH);
				setx+=SIDELENGTH;                             
			}
			setx=INITX;
			sety+=SIDELENGTH;
		}

		g2.setColor(Color.black);

		for (byte i=0;i<3;i++){
			for (byte n=0;n<3;n++){
				if(game[i][n]==1)
					g2.drawString("X", INITX + n*SIDELENGTH + 50, INITY + i*SIDELENGTH + 100);
				else if(game[i][n]==-1)
					g2.drawString("O", INITX + n*SIDELENGTH + 50, INITY + i*SIDELENGTH + 100);
			}
		}
	}//end paintComponent method

	/**mouseClicked method:
	 * This procedural method determines which board board piece was clicked
	 * 
	 * @param <MouseEvent e>
	 * e - used to access MouseEvent class
	 * 
	 * @return void
	 */

	public void mouseClicked(MouseEvent e) {

		if(number!=2){
			x = (int) e.getX();
			y = (int) e.getY();
			if (x>150 && x<600 && y>300 && y<750){
				x=(x-INITX)/SIDELENGTH;
				y=(y-INITY)/SIDELENGTH;
				if (game[y][x]==0){

					if (player==1)
						game[y][x]=1;
					else
						game[y][x]=-1;  

					repaint();

					if(hasWon())
						whenWin();              
					else if(isTie())
						whenTie();
					else{
						player*=-1;
						if(number==1){
							int best=bestMove(player);
							game[best/10][best%10]=player;

							if(hasWon())
								whenWin();
							else if (isTie())
								whenTie();      
							else
								player*=-1;
						}
					}
				}
			}
		}       
	}//end MouseClicked method

	/**whenTie method:
	 * This procedural method determines what to do after a tie is reached on the board
	 * 
	 * @param <null>
	 * 
	 * @return void
	 */

	public void whenTie() {
		timer.stop();
		restart = JOptionPane.showConfirmDialog(this,"Draw! Would you like to play again?");
		if(restart==0){
			for (byte i=0;i<3;i++)
				for (byte n=0;n<3;n++)
					game[i][n]=0;

			repaint();
			player=1;
			start=false;
			if(number==2)
				timer.start();
		}                       
		else
			System.exit(0);
	}//end whenTie method

	/**whenWin method:
	 * This procedural method determines determines what to do after a win is reached on the board
	 * 
	 * @param <null>
	 * 
	 * @return void
	 */

	public void whenWin() {
		if(number==2)
			timer.stop();
		restart = JOptionPane.showConfirmDialog(this,winner() + " wins! Would you like to play again?");
		if(restart==0){
			for (byte i=0;i<3;i++)
				for (byte n=0;n<3;n++)
					game[i][n]=0;

			repaint();
			player=1;
			start=false;
			if(number==2)
				timer.start();
		}                       
		else
			System.exit(0);
	}//end endWin method

	/**hasWon method:
	 * This procedural method determines if a win has been reached on the board
	 * Local Variables
	 * (byte) tempNum - used to check who owns the first piece in a line on the board
	 * 
	 * @param <null>
	 * 
	 * @return void
	 */

	public boolean hasWon(){
		byte tempNum;

		for (byte i=0;i<game.length;i++){
			if(game[i][0]==1)
				tempNum=1;
			else if (game[i][0]==-1)
				tempNum=-1;
			else
				continue;

			if(game[i][1]==tempNum && game[i][2]==tempNum){
				return true;
			}
		}// end hasWon method

		for (byte n=0;n<game.length;n++){
			if(game[0][n]==1)
				tempNum=1;
			else if (game[0][n]==-1)
				tempNum=-1;
			else
				continue;

			if(game[1][n]==tempNum && game[2][n]==tempNum){
				return true;
			}
		}

		if(game[0][0]==1)
			tempNum=1;
		else if (game[0][0]==-1)
			tempNum=-1;
		else 
			tempNum=2;
		if(game[1][1]==tempNum && game[2][2]==tempNum){
			return true;
		}

		if(game[0][2]==1)
			tempNum=1;
		else if (game[0][2]==-1)                 
			tempNum=-1;
		else 
			tempNum=2;
		if(game[1][1]==tempNum && game[2][0]==tempNum){
			return true;
		}
		return false;
	}

	/**winner method:
	 * This functional method determines who the winner is
	 * 
	 *@param <null>
	 * 
	 * @return name1/name2-name of the winner
	 */

	public String winner(){
		if (player==1)
			return name1;
		else 
			return name2;
	}//end winner method

	/**bestMove method:
	 * This functional method determines what the best move to make for the computer is
	 * 
	 * @param <byte p>
	 * p - keeps track of who's move it currently is
	 * 
	 * @return int bestLocation-location of the best move for the computer
	 */

	public int bestMove(byte p) {

		int max=-200;
		int bestLocation = 0;

		for (byte i=0;i<3;i++){
			for (byte n=0;n<3;n++){
				int value=0;
				if (isClear(i,n)){
					move(i,n);
					value = goodness(p);
					erase(i,n);
					if (value>max){
						max=value;
						bestLocation=i*10+n;     
					}        
				}
			}       
		}

		return bestLocation;
	}// end bestMove method

	/**goodness method:
	 * This functional method determines what the value of each move for each square would be for the computer
	 * 
	 * @param <byte p>
	 * p - keeps track of who's move it currently is
	 * 
	 * @return int max -the value of the best move
	 */

	public int goodness( byte p){
		int max=200;
		if(hasWon())
			return 100;
		else{
			if(isTie())
				return 0;
			else{
				p*=-1;
				for (byte j=0;j<game.length;j++){
					for (byte k=0;k<game[j].length;k++){
						if (game[j][k]==0){
							game[j][k]= p;
							int value = -1*goodness(p)/2;
							game[j][k]=0;
							if (value<max)
								max=value;
						}  
					}
				}
			}
			return max;
		}
	}//end goodness method

	/**isTie method:
	 * This functional method determines if a tie has been reached
	 * 
	 * @param <>
	 * 
	 * @return true/false - if a tie is reached (boolean)
	 */

	public boolean isTie() {
		for (byte i=0;i<3;i++)
			for (byte n=0;n<3;n++)
				if (game[i][n]==0)
					return false;
		return true;
	}//end isTie method

	/**isClear method:
	 * This functional method determines if a location is free of any pieces
	 * 
	 * @param <>
	 * 
	 * @return true/false - if a piece is clear (boolean)
	 */

	public boolean isClear(int i, int n) {
		if (game[i][n]==0)
			return true;
		else
			return false;
	}//end isClear method

	/**erase method:
	 * This procedural method erases a piece from a location
	 * 
	 * @param <int i, int n>
	 * i - row # of piece to be erased
	 * n - col # of piece to be erased
	 * @return void
	 */


	public void erase( int i, int n) {
		game[i][n]=0;
	}//end erase method

	/**move method:
	 * This procedural method puts a piece in a location
	 * 
	 * @param <int i, int n>
	 * i - row # of piece to be placed
	 * n - col # of piece to be placed
	 * 
	 * @return void
	 */

	public void move(int i, int n) {
		game[i][n]=player;                   
	}//end move method

	/**actionPerformed method:
	 * This procedural method is called automatically when an action is performed and is
	 * 
	 * @param <ActionEvent evt>
	 * evt - object used to check when events are happening in the JFrame. Ex. clicking a Jbutton
	 * 
	 * @return void
	 */

	public void actionPerformed(ActionEvent evt) {

		if(check==1 && start){
			if(number==2){
				int best=bestMove(player);
				game[best/10][best%10]=player;
			}
			repaint();
			if(hasWon()){
				timer.stop();
				whenWin();
			}
			else if(isTie()){
				timer.stop();
				whenTie();      
			}
			else
				player*=-1;
		}

		if(!start){
			randomx=(int) Math.round(Math.random()*2);
			randomy=(int) Math.round(Math.random()*2);
			game[randomx][randomy]=1;
			start=true;
			player*=-1;
		}
	}//end actionPerformed method
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

}//end TicTacToe