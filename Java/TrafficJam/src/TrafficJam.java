
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.Timer;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
public class TrafficJam {
///****** Author : Casey Munga  - Completed Feb. 19th 2022
	// GUI variables
	JFrame board;
	JFrame welcomeScreen = new JFrame("Traffic Jame Game - Welcome - Casey Munga");
	GetChanges itemChanged = new GetChanges(); // ( Spinners Selected used to get the number of players per team
	GetButtons playerSelected = new GetButtons(); // //Reads the button selected (Game Buttons and Players)
	GetActions commndSelected = new GetActions(); // All other buttons on the board

	int first;
	int team1Players;
	int team2Players;
	String currentPlayer; // color of current player
	TeamMember moveEmpty; // keeps the empty space object
	String currentPlayerColor=null;
	Boolean gameCompleted = false;
	Boolean walk = true;
	Timer myTimer = new Timer();
	File solutionFile;
	//
	ArrayList<TeamMember> Team1 = new ArrayList<TeamMember>(); // Team 1 is the Red Spinner value
	ArrayList<TeamMember> Team2 = new ArrayList<TeamMember>(); // Team 2 is the Blue Spinner value
	ArrayList<TeamMember> GameBoard = new ArrayList<TeamMember>(); // Board - holds the two teams - players move on this
	ArrayList<JToggleButton> selectedButtons = new ArrayList<JToggleButton>(); // Make an arrayList of Toggle Buttons

	public void LoadGame() {
		// Spinner type and values
		SpinnerNumberModel spnmRed = new SpinnerNumberModel(5, 1, 10, 1); // (default value, start, end, step)
		SpinnerNumberModel spnmBlue = new SpinnerNumberModel(5, 1, 10, 1);

		// Create and format the Red Team Spinner
		JSpinner spRed = new JSpinner(spnmRed); // create spinner
		spRed.setPreferredSize(new Dimension(50, 40));

		
		// Set up the board the player will see when the game starts
		JFrame board = new JFrame("Traffic Jam Game - Welcome : Casey Munga");
		spRed.setName("spRed");
		spRed.setToolTipText(" RED Team");
		spRed.getEditor().getComponent(0).setBackground(Color.RED);
		spRed.getEditor().getComponent(0).setForeground(Color.WHITE);
		spRed.addChangeListener(itemChanged); // now add a listener for the event changes

		// Create and format Blue Spinner
		spRed.setBackground(Color.BLUE);
		JSpinner spBlue = new JSpinner(spnmBlue);
		spBlue.setName("spBlue");
		spRed.setToolTipText("BLUE Team");
		spBlue.getEditor().getComponent(0).setBackground(Color.BLUE);
		spBlue.getEditor().getComponent(0).setForeground(Color.WHITE);
		spBlue.setPreferredSize(new Dimension(50, 40));
		spBlue.addChangeListener(itemChanged); // add a listener event for changes

		// Put Images on button
		ImageIcon startIcon = new ImageIcon("image/Start-128.png");
		ImageIcon rulesIcon = new ImageIcon("image/Book-Close-128.png");
		ImageIcon restartIcon = new ImageIcon("image/Command-Reset-128.png");

		JPanel startPanel = new JPanel();
		JButton btnRules = new JButton(rulesIcon);
		btnRules.setToolTipText("Game Rules");
		btnRules.setName("btnRules");
		btnRules.setBackground(Color.DARK_GRAY);
		btnRules.addActionListener(commndSelected);

		JButton btnStart = new JButton(startIcon);
		btnStart.setToolTipText("Start Game");
		btnStart.setName("btnStart");
		btnStart.setBackground(Color.DARK_GRAY);
		btnStart.addActionListener(commndSelected);

		JButton btnRestart = new JButton(restartIcon);
		btnRestart.setToolTipText("Restart Game");
		btnRestart.setName("btnRestart");
		btnRestart.setBackground(Color.DARK_GRAY);
		btnRestart.addActionListener(commndSelected);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel directions = new JLabel();
		directions.setText("Choose Your Teams then Select Play ");
		topPanel.add(directions);

		JPanel midPanel = new JPanel();
		midPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		midPanel.add(btnStart);
		midPanel.add(btnRules);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(spRed);
		bottomPanel.add(spBlue);

		welcomeScreen.setLayout(new GridLayout(3, 1));

		welcomeScreen.add(topPanel);
		welcomeScreen.add(bottomPanel);
		welcomeScreen.add(midPanel);

		welcomeScreen.setPreferredSize(new Dimension(600, 500));
		welcomeScreen.setResizable(false);
		welcomeScreen.setVisible(true);
		welcomeScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeScreen.pack();

	}

	public void LoadGUIBoard()  {
		 board= new JFrame("Traffic Jam Game -Play Game Casey Munga");
		// Load Images to add to the buttons
		ImageIcon moveIcon = new ImageIcon("image/Man-128.png");
		ImageIcon jumpIcon = new ImageIcon("image/Hurdling-128.png");
		ImageIcon solveIcon = new ImageIcon("image/Lock-Open-128.png");
		ImageIcon printIcon = new ImageIcon("image/Touch-Screen-128.png");
		//ImageIcon startIcon = new ImageIcon("image/Start-128.png");
		ImageIcon rulesIcon = new ImageIcon("image/Book-Close-128.png");
		ImageIcon restartIcon = new ImageIcon("image/Command-Reset-128.png");

		// Create Buttons -
		JToggleButton btnWalk = new JToggleButton(moveIcon);
		btnWalk.setToolTipText("Walk");
		btnWalk.setName("btnWalk"); // sets button name so that button can be accessed later in code
		btnWalk.addActionListener(playerSelected); // add action listener to capture button clicks
		btnWalk.setBackground(Color.DARK_GRAY);

		JToggleButton btnJump = new JToggleButton(jumpIcon);
		btnJump.setToolTipText("Jump");
		btnJump.setName("btnJump");
		btnJump.setBackground(Color.DARK_GRAY);
		btnJump.addActionListener(playerSelected);

		JButton btnSolve = new JButton(solveIcon);
		btnSolve.setToolTipText("Solve Game.. I give up!");
		btnSolve.setName("btnSolve");
		btnSolve.setBackground(Color.DARK_GRAY);
		btnSolve.addActionListener(commndSelected);

		JButton btnRules = new JButton(rulesIcon);
		btnRules.setToolTipText("Game Rules");
		btnRules.setName("btnRules");
		btnRules.setBackground(Color.DARK_GRAY);
		btnRules.addActionListener(commndSelected);

		JButton btnRestart = new JButton(restartIcon);
		btnRestart.setToolTipText("Restart Game");
		btnRestart.setName("btnRestart");
		btnRestart.setBackground(Color.DARK_GRAY);
		btnRestart.addActionListener(commndSelected);

		JButton btnPrint = new JButton(printIcon);
		btnPrint.setToolTipText("Print Solution");
		btnPrint.setName("btnPrint");
		btnPrint.setBackground(Color.DARK_GRAY);
		btnPrint.addActionListener(commndSelected);

		// SET BUTTONS,SPINNERS, GAMEBOARD - Set the regions of the Panels which will be
		// shown in the frame
		JPanel rightPanel, midPanel, leftPanel, bottomPanel;
		leftPanel = new JPanel();
		leftPanel.setName("pnStart");
		leftPanel.setLayout(new FlowLayout());
		/*
		 * leftPanel.add(new JLabel("Choose Your Team")); leftPanel.add(spRed);
		 * leftPanel.add(btnStart); leftPanel.add(spBlue);
		 */
		// PLAYERS STAGE - Buttons for Teams Here
		midPanel = new JPanel();
		midPanel.setName("pnPlayers"); // Action panel of players
		midPanel.setLayout(new GridLayout(1, 11)); // create the grid

		Integer countPlayers = 0;
		// Add toggle button because I can select more than 1
		JToggleButton thisButton; // create a button object
		for (int i = 0; i < team1Players; i++) {
			thisButton = new JToggleButton(); // instantiate button
			thisButton.setName(Integer.toString(countPlayers));
			thisButton.setBackground(Color.RED);
			thisButton.setForeground(Color.WHITE);
			thisButton.setFont(new Font("Monaco", Font.BOLD, 15));
			thisButton.setText("R" + Integer.toString(i));
			thisButton.addActionListener(playerSelected);
			countPlayers++;
			midPanel.add(thisButton);

		}

		// Empty space, add button to the panel
		thisButton = new JToggleButton();
		thisButton.setName(Integer.toString(countPlayers));
		thisButton.setText("X");
		thisButton.setBackground(Color.WHITE);
		thisButton.addActionListener(playerSelected);
		midPanel.add(thisButton);

		// add player buttons programmatically
		for (int i = team2Players-1; i >= 0; i--) {
			countPlayers++;
			thisButton = new JToggleButton();
			thisButton.setName(Integer.toString(countPlayers));
			thisButton.setBackground(Color.BLUE);
			thisButton.setForeground(Color.WHITE);
			thisButton.setFont(new Font("Monaco", Font.BOLD, 15));
			thisButton.setText("B" + Integer.toString(i));
			thisButton.addActionListener(playerSelected);
			midPanel.add(thisButton);
			midPanel.setVisible(true);// force players to select the start button at the beginning of the game
		}
		board.add(midPanel, BorderLayout.CENTER);

		// Buttons on far right
		rightPanel = new JPanel();
		rightPanel.setName("pnRestartRules");
		rightPanel.setLayout(new FlowLayout());
		rightPanel.add(btnRules);
		rightPanel.add(btnRestart);
		rightPanel.setVisible(true);// force players to select the start button at the beginning of the game
		board.add(rightPanel, BorderLayout.EAST);

		// add the buttons to the screen
		bottomPanel = new JPanel();
		bottomPanel.setName("pnCommands");
		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(btnWalk);
		bottomPanel.add(btnJump);
		bottomPanel.add(btnSolve);
		bottomPanel.add(btnPrint);
		bottomPanel.setVisible(true);// force players to select the start button at the beginning of the game
		
		board.add(bottomPanel, BorderLayout.SOUTH);
		board.setPreferredSize(new Dimension(1500, 500));
		board.setResizable(false);
		board.setVisible(true);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.pack();
	}

	// ----------------------GAME CODE ----------------------------------
	

	public void Move(int from, int destination) {
		
		Component[] cont = board.getContentPane().getComponents(); // Access the root Frame
		JPanel playersBoard = (JPanel) cont[0]; // pnPlayers - get Players Board	
		JToggleButton playerFrom =(JToggleButton)playersBoard.getComponent(from); // get the player buttons
		JToggleButton playerTo = (JToggleButton)playersBoard.getComponent(destination);// to have access to the button attributes 
		JToggleButton walkSelected = (JToggleButton) ((JPanel) cont[2]).getComponent(0); // Get the walk button
		JToggleButton jumpSelected = (JToggleButton) ((JPanel) cont[2]).getComponent(1); // Get the jump button
		
		// Animation - move players on board
		playerTo.setBackground(playerFrom.getBackground());
		playerTo.setText(playerFrom.getText());
		playerTo.setForeground(playerFrom.getForeground());
		playerFrom.setBackground(Color.WHITE);
		playerFrom.setText("X");
		
		// Clean up. all the buttons
		playerFrom.setSelected(false);
		playerTo.setSelected(false);
		
		if ( walkSelected.isSelected()) { // if walk chosen
			 walkSelected.setSelected(false); //
		    
		}
		if ( jumpSelected.isSelected()) { // if jump chosen
			 jumpSelected.setSelected(false); //
		   
		}
				 
		board.revalidate(); //commit changes and refresh board
		//System.out.println(cont.getName() + "Name and selected " + cont.getBackground().getColorSpace()+ " background color : parent component " + cont.getParent().getName());
	
	}


	public boolean Jump( TeamMember currentPlayer) {

		Boolean jump = true;  int tempPos = 0;
		
		  if ((currentPlayer.Color == "RED" && currentPlayer.Pos + 2 == moveEmpty.Pos)&& (currentPlayer.Color != GameBoard.get(currentPlayer.Pos + 1).Color)
				|| (currentPlayer.Color == "BLUE" && currentPlayer.Pos - 2 == moveEmpty.Pos)&& (currentPlayer.Color != GameBoard.get(currentPlayer.Pos - 1).Color)) { // TRY Walk
			tempPos = currentPlayer.Pos; // Start swap
			currentPlayer.Pos = moveEmpty.Pos; // player moves to the Empty space
			moveEmpty.Pos = tempPos; // Swap done
			jump =false; // player used up his walk for the turn
			GameBoard.set(currentPlayer.Pos, currentPlayer); // update game board
			GameBoard.set(moveEmpty.Pos, moveEmpty);	
			System.out.println("\n"+" "+currentPlayer.Name+" Jumps to position "+currentPlayer.Pos+". A Space open now at pos "+moveEmpty.Pos);
			PrintBoard();
			}
		return jump;
	}

	public boolean Walk( TeamMember currentPlayer) {

		//Boolean walk = true; // Player can only WALK ONCE per Turn
		
		int tempPos = 0;
		 if ((currentPlayer.Color == "RED" && currentPlayer.Pos + 1 == moveEmpty.Pos)
				|| (currentPlayer.Color == "BLUE" && currentPlayer.Pos - 1 == moveEmpty.Pos)) { // TRY Walk
			tempPos = currentPlayer.Pos; // Start swap
			currentPlayer.Pos = moveEmpty.Pos; // player moves to the Empty space
			moveEmpty.Pos = tempPos; // Swap done
			walk = false; // player used up his walk for the turn
			GameBoard.set(currentPlayer.Pos, currentPlayer); // update game board
			GameBoard.set(moveEmpty.Pos, moveEmpty);
			System.out.println("\n"+" "+currentPlayer.Name+" walks to position "+currentPlayer.Pos+". A Space open now at pos "+moveEmpty.Pos);
			PrintBoard();

		}
		return walk;
	}

	public void PrintBoard() {
	
		System.out.println();
		GameBoard.forEach(member -> System.out.println("    Player: " + member.Name + "| Team " + member.Color
				+ " | Game board position " + GameBoard.indexOf(member)));
	}

	public Boolean ValidateWin(String team2Color, int team2Size) {
		// Since team 1 starts first check for team 2 at the top or beginning or left of
		// the board

		for (int i = 0; i <= team2Size - 1; i++) {
			if (GameBoard.get(i).Color != team2Color) {
				return false; // members are not contiguous
			}
		}
		if (GameBoard.get(team2Size).Color == "Empty") // all members of the team are contiguous and there is a Empty behind
													// them
			return true; // So they have successfully traversed to the opposite side of the game board

		return false;
	}

	public void SolveGame(int team1Size, String team1Color, int team2Size) {
		// Automatic solution of game cause it was too hard for the players
		int team1Turn=0;
		int team2Turn=0; 		//Turns per team used to create log
		Boolean gameFinished= false; 
		String team2Color=null;
		int teamCount=0;

		PrintStream solutionFile;
		try {
			solutionFile = new PrintStream (new File("TrafficGameSolution.txt"));
		
		PrintStream outputSolution = System.out;
		
			outputSolution = new PrintStream(solutionFile);
			System.setOut(solutionFile);
		System.out.println("Date Time : "+ LocalDate.now().toString() + "   Time : "+LocalTime.now().toString());
		System.out.println ( " Traffic Jam Solution - Log- Casey Munga");
		System.out.println ("             BOARD IS SET ");
		PrintBoard();
		
		if(team1Color =="RED")
			team2Color ="BLUE";
		else
			team2Color="RED";
		//decide which team moves first and increment turns - for log file\
		
		while (gameFinished== false) {
			walk=true;
			if(team1Color == "RED" || team1Color =="BLUE") { 
				System.out.println();
				team1Turn++;
				System.out.println(" "+team1Color +" Team Turn " +team1Turn);
				//Try WALK FIRST
				for(int i = team1Size-1; i>=0; i--) {

					Jump(Team1.get(i));
					if (walk==true) 
						walk=Walk(Team1.get(i));	
					}
				}
				System.out.println();
				team2Turn++;
				walk=true; // new turn reset walk
				if(team2Color == "RED" || team2Color =="BLUE") { // RedTeam moves firs
					System.out.println(" ");
					System.out.println(" "+team2Color +" Team Turn " +team2Turn);
			
				//Try JUMP FIRST
				for(int i = 0; i<= team2Size-1; i++) {
					Jump(Team2.get(i));
					if(walk== true)
						walk=Walk(Team2.get(i));
							
					}
				}
				gameFinished = ValidateWin(Team2.get(0).Color,team2Size); // Check to see if there is a win after the turn is completed
			}
			
		
		System.out.println();
		System.out.println("        ***** The Game is SOLVED. *********");
		PrintBoard();
		outputSolution.close();
		}	catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(board," File Access Denied","Solution File Not Created", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(board,"Your Solution Is Ready. Select the Print File Icon");
	}
	
		
	public void LoadBoard(int team1Players, int team2Players, Boolean solve) {
		// Create Teams and Fill Game Board

		// Solve =false print game board else don't print

		int count = 0;
		String teamFirstColor; // color of team to go first

		// SET UP
		if (first == 0)
			teamFirstColor = "RED";
		else
			teamFirstColor = "BLUE";

		// Load team1
		TeamMember player;

		for (int i = 0; i <= team1Players - 1; i++) {
			player = new TeamMember("R" + i, i, "RED");
			Team1.add(player);
			GameBoard.add(player);
			count++;
		}

		// Load Empty Space and keep its position
		int EmptyPos = count++;
		moveEmpty = new TeamMember("Empty", EmptyPos, "Empty");
		GameBoard.add(moveEmpty);

		for (int i = team2Players - 1; i >= 0; i--) {
			player = new TeamMember("B" + i, count++, "BLUE");
			; // so that when they are facing the blue team starts at the nex
			Team2.add(player);
			GameBoard.add(player);// Team 2 players added to the game board
		}
		if (solve)
			SolveGame(team1Players, teamFirstColor, team2Players); // parameters needed to create the cheat sheet
		
	}

	public void PlayGame( String currentColor,int from, int destination, int action) {


		TeamMember currentPlayer = new TeamMember();

		// START GAME
		
		//System.out.println("Inside Play Game");
		if (gameCompleted == false) { // game on
			switch (action) {
			case 4: // Solve Game
					Team1.removeAll(Team1);
					Team2.removeAll(Team2);
					LoadBoard(team1Players, team2Players, true);
					board.revalidate();
					break;
				}

				if (action <= 3 ) {
					currentPlayer = GameBoard.get(from);

					if (currentPlayer.Color == currentColor) {
						switch (action) {
						case 1:
							//System.out.println("Walk is "+walk);
							if (walk) { // can only walk once per turn
								walk = Walk(currentPlayer);
								//System.out.println("after walk is "+walk);
								Move(from,destination);
								gameCompleted = ValidateWin(Team2.get(0).Color,team2Players);
							} else
							JOptionPane.showMessageDialog(board, " Your Team has walked already. RESTART GAME","Player Error", JOptionPane.WARNING_MESSAGE);
							break;

						case 2:
							if (Jump(currentPlayer)) // Jump
								JOptionPane.showMessageDialog(board, "INVALID JUMP -RESTART Game", "Jump Error",  JOptionPane.WARNING_MESSAGE);
							else
								Move(from,destination);
							gameCompleted = ValidateWin(Team2.get(0).Color, team2Players);
							break;


						default:
							gameCompleted = true; // user wants out
						}
					}
				}
				
			} 
			if (gameCompleted==true) PrintWin();
	}

	public void PrintWin() {
	
			String s1="WHOOO-HOO Genius Rank - You got your team to their destination safely. ";
			JOptionPane.showMessageDialog(board,"\n"+"\n"+s1+"\n");
	}
	public void PrintRules() {

		String s0 = "-------------------------------------------- WELCOME to TRAFFIC JAM -------------------------------------------";
		String s1 = "          Your Objective : MOVE your TEAM  SAFELY to the OTHER SIDE of Town                                    ";
		String s9 = "---------------------------------------- RULES  ---------------------------------------------------------------";
		String s2 = "  1.    WALK 1 Space or JUMP 2 spaces.";
		String s3 = "  2.    GO FORWARD  OR JUMP over your OPPONENT";
		String s4 = "  3.    NO jumping over your TeamMate";
		String s5 = " If you can't solve it 'cause you're a baby, just cheat and press the UNLOCK Icon";
		String s6 = "  4.    RESTART or GIVE UP anytime.. I DARE You!.";
		String s7 = " You PRINT the solution after you PRESS the cheat button.";
		String s8 = "                                  GOOD LUCK!";
		JOptionPane.showMessageDialog(board, s0 + "\n" + s1 + "\n" + s9 + "\n" + s2 + "\n" + s3 + "\n" + s4 + "\n" + s6
				+ "\n" +"\n" + s5 + "\n" + s7 + "\n"+"\n" + s8);

	}

	public static void main(String[] args) {

		TrafficJam game = new TrafficJam();
		game.LoadGame();
	}
	
	// CLASSES TO RECEIVE INPUT FROM THE GUI AS THEY ARE ALL OBJECTS

	private class GetChanges implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSpinner s = (JSpinner) e.getSource();
			if (s.getName() == "spRed") { // Find the spinner and get its value
				team1Players = (int) s.getValue(); // cast to value to get the players for the red team
				// System.out.println("Red Team:"+ redTeamCount);
			} else
				team2Players = (int) s.getValue(); // Cast to value to get the players for the blue Team
			// System.out.println("Red Team: "+ team1Players);
			// System.out.println("BlueTeam: "+ team2Players);
		}
	}

	private class GetButtons implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JToggleButton currentButton = (JToggleButton) e.getSource();
			Boolean success = true;
			Boolean hasDestination = false;
			Integer from = -1;
			Integer destination=-1;
			Integer counter = 0;
		

			// button selected 1. get all buttons selected 2. check selected button are
			// exactly 3 (blank space, walk button, player to be moved)
			// 3. send selected button array to the procedure
			
			selectedButtons.add(currentButton);   // collect all the buttons pressed thus far
			if (currentButton.getName() == "btnWalk" || currentButton.getName() == "btnJump") {
		
				for (JToggleButton b : selectedButtons) { // run through the buttons 
					if (b.isSelected() == true) {
						//System.out.println(b.getName() + " " + b.isSelected());
						if (b.getText() == "X") {
							hasDestination = true; // player chose a blank spot
							destination = Integer.valueOf(b.getName());}
						//System.out.println(b.getName() + " " + b.getText());

						if  (b.getText() != "X" && b.getName() != "btnJump" && b.getName() != "btnWalk"){
							if( currentPlayerColor =="RED" && (b.getBackground() ==Color.BLUE)||
									( currentPlayerColor =="BLUE" && (b.getBackground() ==Color.RED)))// Team turn ends 
									walk=true; // reset walk
							if (b.getBackground()== Color.RED) 
								  currentPlayerColor ="RED";
								else currentPlayerColor = "BLUE";
							from = Integer.valueOf(b.getName());}
						counter++; // increment counter to make sure that it gets all the buttons
					}
				}
				if (counter > 3 || hasDestination == false) {
					JOptionPane.showMessageDialog(board, "Select ONE player and WALK or JUMP to a FREE destination."+"\n"+ "RESTART GAME");
					success = false;
					selectedButtons.removeAll(selectedButtons);
					}
				else {// we have a player and a blank spot
					if (success && currentButton.getName() == "btnWalk") {
						//System.out.println("Lets Walk now");
						selectedButtons.removeAll(selectedButtons); // the buttons pressed list
						PlayGame(currentPlayerColor,from,destination,1);}
					if (success && currentButton.getName() == "btnJump") {
						selectedButtons.removeAll(selectedButtons);
						PlayGame(currentPlayerColor,from,destination,2);}		
				}
		} // got all the selected buttons
	}
}
	
	private class GetActions implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			//listen for events that is wired to the buttons with GetActions
			JButton b = (JButton) e.getSource();
			//System.out.println("" + b.getName());
              
			switch (b.getName()) {
			case "btnStart": // START HERE --- Load Board - false = Play Game Do not SOLVE
				first = JOptionPane.showConfirmDialog(welcomeScreen, "Red Team goes First?", "Pick a Team",
						JOptionPane.YES_NO_OPTION);
				 
				//player does not use the spinner and just press the play button
				 if (team1Players==0) team1Players=5; //set default board
				 if (team2Players==0) team2Players=5;
				welcomeScreen.dispose();
				LoadGUIBoard();
				LoadBoard(team1Players, team2Players, false);
				break;

			case "btnSolve":
				GameBoard.removeAll(GameBoard); // reset board
				LoadBoard(team1Players, team2Players, true);
				break;

			case "btnRules":
				PrintRules();
				break;

			case "btnRestart":
				board.dispose();// get rid of the old gameboard
				LoadGUIBoard(); // load the new board
				GameBoard.removeAll(GameBoard); // reset board
				LoadBoard(team1Players, team2Players, false);
				walk=true;
				break;
				
			case "btnPrint":
				try {
					Desktop.getDesktop().open(new File("TrafficGameSolution.txt"));
				}catch (Exception g) {
					g.printStackTrace();
				}
				break;
			
			default:
				
				break;

			}

		}
	}
}
