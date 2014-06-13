/*	Claire Wang
	6 April 2014
	Final.java
	This program will let the user program and play their own basic games, while teaching them how to use the 
		KeyListeners and Timers needed to program such games. It is my final project for this JAVA course.
*/
import java.awt.*;							// import all used Java libraries.
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.*;
import java.util.Scanner;

public class Final {
	JFrame frame;							// main frame containing all components.
	MenuPanel panel;
	
	public static void main(String[] args) {
		Final fin = new Final();
		fin.run();
	}
	public void run() {
		frame = new JFrame("Keys and Timers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 663);
		frame.setResizable(false);
		panel = new MenuPanel();
		setfocus();
		
		ImageIcon icon = new ImageIcon("icon.png");
		frame.setIconImage(icon.getImage());
		
		frame.getContentPane().add(panel);
		
		frame.setVisible(true);	
	}
	
	public void setfocus() {			// directs focus to the gameboard panel, so it can take user input with keylisteners.
		frame.setFocusable(false);		//		directs focus away from all other components so gameboard doesn't lost focus, which would render keylisteners ineffective. 
		
		panel.setFocusable(true);
		panel.playpanel.setFocusable(true);
		panel.playpanel.game.setFocusable(true);
		panel.playpanel.game.holder.setFocusable(true);
		panel.playpanel.game.holder.grabFocus();
		
		panel.playpanel.game.pp.setFocusable(false);
		panel.playpanel.game.buttons.setFocusable(false);
		
		for (int i = 0; i < 6; i++) {
			panel.playpanel.game.monsterbuttons[i].setFocusable(false);
			panel.playpanel.game.itembuttons[i].setFocusable(false);
		}
		
		panel.playpanel.game.reset.setFocusable(false);
		panel.playpanel.game.popout.setFocusable(false);
		panel.playpanel.game.customize.setFocusable(false);
		panel.playpanel.game.save.setFocusable(false);
		panel.playpanel.game.options.setFocusable(false);
		panel.playpanel.game.options.speed.setFocusable(false);
		panel.playpanel.game.options.collectable.setFocusable(false);
		panel.playpanel.game.options.back.setFocusable(false);
		panel.playpanel.game.options.spritepane.setFocusable(false);
		panel.playpanel.game.options.keys.setFocusable(false);
		panel.playpanel.game.options.message.setFocusable(false);
		panel.playpanel.scrollcode.setFocusable(false);
		panel.playpanel.code.setFocusable(false);
		
		panel.chalpanel.start.setFocusable(false);
		
		panel.holder.setFocusable(false);
		panel.screenshots.setFocusable(false);
		panel.instructionpan.setFocusable(false);
		panel.scrollins.setFocusable(false);
		panel.ins.setFocusable(false);
		
		panel.loadpanel.load.setFocusable(false);
	}
}

class MenuPanel extends JPanel implements MouseListener {	// game's main panel containing all menu, 
															//	game, and instruction panels. uses cardlayout to navigate between subpanels.
	MyButton start, load, challenge, instructions, quit;		// buttons leading to game and instruction subpanels.
	AnimationPanel apanel;
	CardLayout screens;		// layout to switch between the game modes and instructions
	JPanel menu;				// menu subpanel containing buttons that lead to other subpanels.
	PlayPanel playpanel;		// subpanel for game mode 1.
	LoadPanel loadpanel;		// subpanel for game mode 2.
	ChallengePanel chalpanel;	// subpanel for game mode 3.
	JTextArea ins;				// text area containing instructions for the game.
	JScrollPane scrollins;		// scrollable pane containing instructions and screenshots
	JPanel instructionpan;		// subpanel for instructions.
	JPanel holder;				// panel holding instructions and corresponding screenshots.
	JPanel screenshots;			// panel holding screenshots to accompany instructions.
	ExitPanel exit1, exit2, exit3, exit4;
	Scanner read;				// scanner reading in instructions text file.
	
	public MenuPanel() {
		try {
			read = new Scanner(new File("instructions.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Could not open instructions.");
		}
	
		screens = new CardLayout();
		setLayout(screens);
		
		exit1 = new ExitPanel();
		exit2 = new ExitPanel();
		exit3 = new ExitPanel();
		exit4 = new ExitPanel();
		menu = new JPanel();
		menu.setLayout(null);
		menu.setBackground(Color.black);
		
		loadpanel = new LoadPanel();
		playpanel = new PlayPanel();
		chalpanel = new ChallengePanel();
		instructionpan = new JPanel();
		
		instructionpan.setLayout(new BorderLayout());
		
		loadpanel.add(exit1, BorderLayout.SOUTH);
		playpanel.add(exit2, BorderLayout.SOUTH);
		chalpanel.add(exit3, BorderLayout.SOUTH);
		instructionpan.add(exit4, BorderLayout.SOUTH);
		
		start = new MyButton("NEW GAME", 24);
		start.setName("start");
		load = new MyButton("LOAD GAME", 24);
		load.setName("load");
		challenge = new MyButton("CHALLENGE", 24);
		challenge.setName("challenge");
		instructions = new MyButton("HOW TO PLAY", 24);
		instructions.setName("instructions");
		quit = new MyButton("QUIT", 24);
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		
		load.addMouseListener(this);
		start.addMouseListener(this);
		challenge.addMouseListener(this);
		instructions.addMouseListener(this);
		
		screenshots = new PicPanel();
		screenshots.setBackground(Color.black);
		holder = new JPanel();		
		holder.setLayout(new GridLayout(1, 2));
		
		String instext = "";				// String interpretation of instructions
		while (read.hasNext()) {
			instext = instext + "\n" + read.nextLine();
		}
		
		ins = new JTextArea(instext);
		ins.setFont(new Font("Nimbus Mono L", Font.PLAIN, 22));
		ins.setBackground(Color.black);
		ins.setForeground(Color.green);
		ins.setEditable(false);
		
		holder.add(ins);
		holder.add(screenshots);
		scrollins = new JScrollPane(holder);
		scrollins.getVerticalScrollBar().setUnitIncrement(10);
		instructionpan.add(scrollins);
		
		apanel = new AnimationPanel();
		
		apanel.setBounds(20, 30, 800, 200);
		start.setBounds(180, 320, 300, 80);
		load.setBounds(510, 320, 300, 80);
		challenge.setBounds(180, 420, 300, 80);
		instructions.setBounds(510, 420, 300, 80);
		quit.setBounds(360, 520, 300, 80);
		
		menu.add(apanel);
		menu.add(start);
		menu.add(load);
		menu.add(challenge);
		menu.add(instructions);
		menu.add(quit);
		
		add(menu, "panel 1");
		add(loadpanel, "load");
		add(playpanel, "start");
		add(chalpanel, "challenge");
		add(instructionpan, "instructions");
	}
	
	public void mousePressed(MouseEvent e) {				// navigate to button's corresponding
		screens.show(this, e.getComponent().getName());		//	panel when clicked.
		
		chalpanel.cards.show(chalpanel.holder, "start");	// go back to challenge panel's main panel.
		playpanel.game.cards.show(playpanel.game, "board");	// go back to game panel's main panel.
		playpanel.game.setdefault();
		playpanel.game.setboard();
		
		apanel.timer.stop();
		apanel.counter = 0;
		apanel.text.setText("");
	}
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }

	class ExitPanel extends JPanel {		// small panel to be included in
											//	each subpanel's south area to give the options to quit the game or return to the menu.
		JButton back, exit;		// buttons that return to menu or exit the game.
		JPanel blank;				// blank subpanel to add in the middle.
		
		public ExitPanel() {
			setLayout(new GridLayout(1, 3));
			
			blank = new JPanel();
			blank.setBackground(new Color(63, 63, 63));
			back = new JButton("MENU");
			exit = new JButton("EXIT GAME");
			
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					screens.show(back.getParent().getParent().getParent(), "panel 1");		// let cardlayout display menu panel.
					chalpanel.timer.stop();					// stop and reset all timers and time information for Challenge mode.
					chalpanel.countdown.stop();
					chalpanel.counttime.setText("3");
					chalpanel.time = 80;
					chalpanel.countdowntime = 3;
					playpanel.game.setboard();			// reset the game board for New Game mode.
			
					apanel.timer.start();
				}
			});
			exit.addActionListener(new ActionListener() {		
				public void actionPerformed(ActionEvent e) {
					if (playpanel.game.saved == false || chalpanel.ingame == true) {		// only show popup if the user has unsaved custom game data or is in the middle of a challenge mode game.
						int result = JOptionPane.showConfirmDialog(null, "Quit the game? All unsaved data will be lost.", "Exit game", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {				// show popup confirming whether the user actually wants to quit or not; if yes, quits the game.
							System.exit(1);			// close and exit everything, including scanners.
							read.close();
							playpanel.read.close();
						}
					}
					else if (playpanel.game.saved && chalpanel.ingame == false) {		// if all custom 
						System.exit(1);								// game data has been saved and the user is not currently playing a challenge mode game, 
						read.close();								// directly quit the game without a popup.
						playpanel.read.close();
					}
				}
			});
			
			add(back);
			add(blank);
			add(exit);
		}
	}
	
	class AnimationPanel extends JPanel {				// animation to be shown at the menu screen.
		String message;			// text to be shown.
		Timer timer;
		Mover mover;
		int counter;				// index of message's current position.
		JLabel text;			// label showing the message
		
		public AnimationPanel() {
			setLayout(null);
			setOpaque(false);
			message = "public class KeysAndTimers {";
			mover = new Mover();
			timer = new Timer(300, mover);
			timer.start();
			counter = 0;
			text = new JLabel("");
			text.setFont(new Font("Nimbus Mono L", Font.PLAIN, 32));
			text.setForeground(Color.green);
			add(text);
			text.setBounds(0, 0, 800, 200);
		}
		class Mover implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				text.setText(message.substring(0, counter));		// display the next letter of the message every 0.3 seconds.
				counter++;
				if (counter > message.length()) timer.stop();
			}
		}
	}
	
	class PicPanel extends JPanel {					// panel of screenshots accompanying instructions
		Image[] screenshots;			// array of screenshot pictures.
		
		public PicPanel() {
			//setBackground(new Color(
			screenshots = new Image[6];
			try {
				for (int i = 0; i < 6; i++) {
					screenshots[i] = ImageIO.read(new File("ss" + (i + 1) + ".png"));
				}
			} catch (IOException e) {
				System.out.println("Could not load screenshots.");
			}
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(screenshots[0], 10, 40, 332, 405, this);		// paint all images in the array.
			g.drawImage(screenshots[1], 10, 460, 332, 405, this);
			g.drawImage(screenshots[2], 10, 880, 295, 352, this);
			g.drawImage(screenshots[3], 10, 1280, 332, 405, this);
			g.drawImage(screenshots[4], 10, 1880, 377, 280, this);
			g.drawImage(screenshots[5], 10, 2280, 347, 271, this);
		}
	}
	
	class LoadPanel extends JPanel {		// panel for game mode 2: loading a saved custom game.
		MyButton load;					// button pressed to accept current selected game name.
		String filename;				// text taken from user's input text.
		JPanel holder;					// middle subpanel containing all the page's content.
		Scanner savereader, gamecounter;	// read the text file containing information about user's saved games.
		int gamecount;						// number of games the user has saved.
		JLabel[] names = new JLabel[100];	// array of labels listing names of all saved games.
	
		public LoadPanel() {
			holder = new JPanel();
			holder.setBackground(Color.black);
			holder.setLayout(null);
			setLayout(new BorderLayout());
			
			gamecount = 0;
			try {
				gamecounter = new Scanner(new File("savedgames.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("Could not open savedgames.txt");
			}
			while (gamecounter.hasNext()) {
				String nameline = gamecounter.nextLine();
				if (nameline.contains("//")) {				// read and store names of saved games, count how many games there are total.
					gamecount++;
					names[gamecount - 1] = new JLabel(nameline.substring(2, nameline.length()));
				}
			}
			JPanel savedgames = new JPanel();							// panel showing names of all saved games.
			if (gamecount <= 20) savedgames.setLayout(new GridLayout(20, 1));		// allocate physical space for all the names in the panel.
			else if (gamecount > 20) savedgames.setLayout(new GridLayout(50, 1));
			savedgames.setBackground(Color.lightGray);
			for (int i = 0; i < gamecount; i++) {
				savedgames.add(names[i]);					// add all game name labels to the panel.
				names[i].setOpaque(true);
				names[i].setBackground(Color.lightGray);
				names[i].setFont(new Font("Nimbus Mono L", Font.PLAIN, 22));
				final int k = i;
				names[i].addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent e) {
						names[k].setBackground(new Color(165, 204, 249));		// highlight the game name when the user clicks on it.
						for (int j = 0; j < gamecount; j++) {		// de-highlight all other names.
							if (j != k) names[j].setBackground(Color.lightGray);
						}
						filename = names[k].getText();
					}
					public void mouseEntered(MouseEvent e) { }
					public void mouseExited(MouseEvent e) { }
					public void mouseClicked(MouseEvent e) { }
					public void mouseReleased(MouseEvent e) { }
				});
			}
			JScrollPane jsp = new JScrollPane(savedgames);
			jsp.getVerticalScrollBar().setUnitIncrement(10);
			jsp.setBounds(70, 10, 300, 470);
			
			JLabel info = new JLabel("<html>Select the name of your saved game in the window at the left.<html>");
			info.setBounds(500, 80, 350, 250);
			info.setFont(new Font("Nimbus Mono L", Font.PLAIN, 26));
			info.setForeground(Color.green);
			
			load = new MyButton("Load saved program", 22);
			load.setBounds(550, 450, 200, 80);
			
			load.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						savereader = new Scanner(new File("savedgames.txt"));
					} catch (FileNotFoundException f) {
						System.out.println("Could not open savedgames.txt");
					}
					while (savereader.hasNext()) {				// retrieves all saved information about user's saved game he/she has selected to load.
						if (savereader.nextLine().contains("//" + filename)) {		// find the selected game's name in the savedgames file, then retrieve variable values of said game from the following lines.
							String line = savereader.nextLine();
							int i = line.indexOf(':');
							playpanel.game.monsternum = (int)(line.charAt(i + 2) - '0');
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.itemnum = (int)(line.charAt(i + 2) - '0');
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.monsterspeed = Integer.parseInt(line.substring(i + 2, i + 6).trim());
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.up = line.charAt(i + 2);
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.left = line.charAt(i + 2);
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.down = line.charAt(i + 2);
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.right = line.charAt(i + 2);
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.usersprite = Integer.parseInt(line.substring(i + 2, i + 4).trim());
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.monstersprite = Integer.parseInt(line.substring(i + 2, i + 4).trim());
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.itemsprite = Integer.parseInt(line.substring(i + 2, i + 4).trim());
							line = savereader.nextLine();
							if (line.contains("true")) playpanel.game.iscollectable = true;
							else if (line.contains("false")) playpanel.game.iscollectable = false;
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.winstring = line.substring(i + 2, line.length());
							line = savereader.nextLine();
							i = line.indexOf(':');
							playpanel.game.losestring = line.substring(i + 2, line.length());
							screens.show(getParent(), "start");
							playpanel.game.gamename = filename;
							playpanel.game.saved = true;
							playpanel.game.setboard();
						}
					}
				}
			});
			holder.add(load);
			holder.add(jsp);
			holder.add(info);
			add(holder);		
		}	
	}

}

class PlayPanel extends JPanel {			// panel for game mode 1: New Game mode.
	JPanel holder;				// panel holding game and code.
	GamePanel game;					// panel containing actual game at the right.
	JScrollPane scrollcode;			// scrollbar panel containing sample code.
	JTextArea code;					// text box holding sample code text.
	Scanner read;				// scanner reading in text file of sample code.
	String codetext;			// string value of sample code.
	JPanel pan;					// overlay panel containing sample code text box.
	FillerPanel p;			// transparent panel containing all information labels.
	InfoLabel key, timer, start, action, performed, released, typed, pressed, getkey, isrunning, awt, javax;
	
	public PlayPanel() {
		try {
			read = new Scanner(new File("samplecode.txt"));
		} catch (FileNotFoundException err) {
			System.out.println("Could not load sample code.");
		}
	
		setLayout(new BorderLayout());
		setBackground(Color.gray);
		holder = new JPanel();
		game = new GamePanel();
		
		holder.setLayout(new GridLayout(1, 2));
		
		codetext = "";
		while (read.hasNext()) {
			codetext += "\n" + read.nextLine();
		}
		codetext += "\n}";
		
		code = new JTextArea(codetext);
		code.setEditable(false);
		code.setFont(new Font("Nimbus Mono L", Font.PLAIN, 18));
		code.setForeground(Color.green);
		code.setBackground(Color.black);
		
		pan = new JPanel();
		pan.setBackground(Color.black);
		pan.setLayout(new OverlayLayout(pan));
		code.setMaximumSize(new Dimension(500, 663));
		p = new FillerPanel();
		p.add(code);
		pan.add(p);
		awt = new InfoLabel("imports the whole package that deals with events fired by AWT components", 250, 0, 200, 200);
		javax = new InfoLabel("imports the whole package that deals with events fired by swing components", 300, 50, 150, 200);
		key = new InfoLabel("implements a KeyListener interface in class SampleCode", 200, 110, 300, 200);
		timer = new InfoLabel("initializes the Timer object \"timer\" with the taskperformer \"mover\"; it has a delay of " + game.monsterspeed + " milliseconds", 200, 320, 300, 200);
		start = new InfoLabel("starts the timer", 230, 400, 300, 200);
		action = new InfoLabel("implements an ActionListener interface in class Mover", 230, 440, 250, 200);
		performed = new InfoLabel("a required method of ActionListener; when used with the timer, this method is called every delay time", 230, 530, 300, 200);
		pressed = new InfoLabel("a required method of KeyListener, called when any keyboard key is pressed", 150, 940, 400, 200);
		getkey = new InfoLabel("a KeyEvent method that returns the char value of an ASCII character key the user pressed", 250, 980, 200, 200);
		isrunning = new InfoLabel("a Timer class method that returns a boolean of whether or not the timer is running", 250, 1020, 200, 200);
		released = new InfoLabel("a required method of KeyListener, called when a keyboard key is released", 230, 1330, 200, 200);
		typed = new InfoLabel("a required method of KeyListener, called when an ASCII character key is pressed", 250, 1435, 200, 200);
		
		code.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {			// when the user hovers over a certain part of code, the information label is shown.
				pan.remove(p);
				int x = e.getX();
				int y = e.getY();
				if (x <= 260 && y >= 50 && y <= 70) pan.add(awt);
				if (x >= 260 || y <= 50 || y >= 70) pan.remove(awt);
				
				if (x <= 295 && y >= 115 && y <= 135) pan.add(javax);
				if (x >= 295 || y <= 115 || y >= 135) pan.remove(javax);
				
				if (x >= 175 && x <= 435 && y >= 160 && y <= 180) pan.add(key);
				if (x <= 175 || x >= 435 || y <= 160 || y >= 180) pan.remove(key);
				
				if (x >= 40 && x <= 370 && y >= 370 && y <= 390) pan.add(timer);
				if (x <= 40 || x >= 370 || y <= 370 || y >= 390) pan.remove(timer);
				
				if (x >= 40 && x <= 195 && y >= 485 && y <= 505) pan.add(start);
				if (x <= 40 || x >= 195 || y <= 485 || y >= 505) pan.remove(start);
				
				if (x >= 150 && x <= 435 && y >= 555 && y <= 575) pan.add(action);
				if (x <= 150 || x >= 435 || y <= 555 || y >= 575) pan.remove(action);
				
				if (x >= 40 && x <= 530 && y >= 580 && y <= 600) pan.add(performed);
				if (x <= 40 || x >= 530 || y <= 580 || y >= 600) pan.remove(performed);
				
				if (x >= 20 && x <= 420 && y >= 990 && y <= 1010) pan.add(pressed);
				if (x <= 20 || x >= 420 || y <= 990 || y >= 1010) pan.remove(pressed);
				
				if (x >= 135 && x <= 305 && y >= 1015 && y <= 1035) pan.add(getkey);
				if (x <= 135 || x >= 305 || y <= 1015 || y >= 1035) pan.remove(getkey);
				
				if (x >= 40 && x <= 305 && y >= 1060 && y <= 1080) pan.add(isrunning);
				if (x <= 40 || x >= 305 || y <= 1060 || y >= 1080) pan.remove(isrunning);
				
				if (x >= 20 && x <= 440 && y >= 1450 && y <= 1470) pan.add(released);
				if (x <= 20 || x >= 440 || y <= 1450 || y >= 1470) pan.remove(released);
				
				if (x >= 20 && x <= 405 && y >= 1475 && y <= 1495) pan.add(typed);
				if (x <= 20 || x >= 405 || y <= 1475 || y >= 1495) pan.remove(typed);
				
				pan.add(p);
				pan.revalidate();
				pan.repaint();
			}
			public void mouseDragged(MouseEvent e) {}
		});
		scrollcode = new JScrollPane(pan);
		scrollcode.getVerticalScrollBar().setUnitIncrement(16);
		
		game.setboard();
		
		holder.add(game);
		holder.add(scrollcode);
		
		add(holder);
	}
	
	class InfoLabel extends FillerPanel {					// panels to add to sample code screen that display information about a certain line of code.
		public InfoLabel(String str, int x, int y, int w, int h) {
			JLabel label = new JLabel("<html>" + str + "<html");
			label.setForeground(Color.white);
			label.setFont(new Font(null, Font.BOLD, 14));
			label.setBounds(x, y, w, h);
			add(label);
			
			setMaximumSize(new Dimension(1500, 5000));
			setLayout(null);
		}
	}

	class GamePanel extends JPanel implements MouseListener {
		DesignPanel options;
		FillerPanel buttons, pp;			// panels that hold button groups or set of buttons, respectively.
		BoardPanel holder;
		
		JFrame window;					// popup window for when user plays his/her game.
		JButton customize, save, reset, popout;		// buttons that:
				// lead to customization panel.
				// send a popup prompting for a name to save the game under.
				// set new positions for the user, items, and monsters (re-set the game board).
				// bring up the popup window for playing the game.
		
		ButtonGroup nummonsters, numitems;			// groups of radio buttons for the desired number of monsters and items, respectively.
		JRadioButton[] monsterbuttons, itembuttons;		// arrays of radio buttons for the desired number of monsters and items.
		
		int monsterspeed;				// timer speed controlling how fast the monsters move (in milliseconds).
		int monsternum, itemnum;		// number of monsters and items, respectively.
		int itemcount;				// number of items the user has collected in the current game.
		boolean iscollectable;			// whether or not the stationary items are collectable or not; if not, the items act as obstacles and the user tries to get to a door.
		int[][] monsters, items;		// arrays holding x- and y-coordinates of monsters and items.
		int userx, usery;				// x- and y-coordinates of user's position on the game board.
		int usersprite, monstersprite, itemsprite, doorsprite;		// sprite number to paint as the user, monsters, items, and door respectively.
		boolean win, lose;			// whether the user has won or lost the game; initially both false, as the game has not ended.
		char up, left, down, right;		// ASCII characters to move the user up, left, down, and right.
		String winstring, losestring;		// text to be displayed when the user wins, loses.
		boolean saved;				// whether or not the user has saved his/her game customizations.
		String gamename;				// name the user has saved his/her game under.
		JLabel name;				// label to add underneath the game board showing the game's name.
		
		Image[] sprites;				// array of game sprite images.
		Image dawei, anarca;			// easter eggs.
		CardLayout cards;				// layout to flip between the game board panel and customization panel.
		
		PrintWriter pw;				// writer that prints the user's saved game customizations to the saved games text file

		public GamePanel() {			
			cards = new CardLayout();
			
			setLayout(cards);
			setBackground(Color.gray);
			
			setdefault();
			
			sprites = new Image[36];
			try {
				for (int i = 0; i < 36; i++) {
					sprites[i] = ImageIO.read(new File("sprite" + (i + 1) + ".png"));
					//dawei = ImageIO.read(new File("dawei"));
					//anarca = ImageIO.read(new File("anarca"));
				}
			} catch(IOException e) {
				System.err.println("Cannot load image");
			}
			holder = new BoardPanel();
			
			options = new DesignPanel();
			options.setOpaque(false);
			buttons = new FillerPanel();			
			
			pp = new FillerPanel();
			
			monsters = new int[6][2];
			items = new int[6][2];
			
			reset = new JButton("Reset");
			popout = new JButton("Play");
			pp.add(reset);
			pp.add(popout);
			
			name = new JLabel(gamename);
			name.setFont(new Font("Nimbus Mono L", Font.BOLD, 22));
			
			monsterbuttons = new JRadioButton[6];
			JLabel monsterN = new JLabel("Number of monsters	:");
			buttons.add(monsterN);
			nummonsters = new ButtonGroup();
			
			for (int i = 0; i < 6; i++) {
				final int j = i + 1;
				monsterbuttons[i] = new JRadioButton("" + j);
				if (j == monsternum) monsterbuttons[i].setSelected(true);	// set the selected radio button as the pre-set value for the number of monsters.
				monsterbuttons[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						monsternum = j;				// if a new radio button is selected, set the number of monsters to that button's corresponding numbers.
						saved = false;		// the user has made a change, so there is now unsaved material.
						setboard();
					}
				});
				nummonsters.add(monsterbuttons[i]);
				monsterbuttons[i].setOpaque(false);
				buttons.add(monsterbuttons[i]);
			}
			
			itembuttons = new JRadioButton[6];
			JLabel itemN = new JLabel("Number of stationary pieces	:");
			buttons.add(itemN);
			numitems = new ButtonGroup();
			
			for (int i = 0; i < 6; i++) {
				final int j = i + 1;
				itembuttons[i] = new JRadioButton("" + (char)(j + '0'));
				if (j == itemnum) itembuttons[i].setSelected(true);
				itembuttons[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						itemnum = j;
						saved = false;
						setboard();
					}
				});
				numitems.add(itembuttons[i]);
				itembuttons[i].setOpaque(false);
				buttons.add(itembuttons[i]);
			}
			
			FillerPanel f3 = new FillerPanel();		// add a bunch of panels so the buttons are sort of nicely aligned.
			f3.setLayout(new GridLayout(3, 1));
			
			FillerPanel sf3 = new FillerPanel();
			
			sf3.add(name);
			f3.add(new FillerPanel());
			f3.add(new FillerPanel());
			f3.add(sf3);
			
			holder.add(pp);
			holder.add(new FillerPanel());
			holder.add(new FillerPanel());
			holder.add(f3);
			holder.add(buttons);
			
			customize = new JButton("Options");
			customize.addMouseListener(this);
			buttons.add(customize);
			
			save = new JButton("Save game");
			save.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						pw = new PrintWriter(new BufferedWriter(new FileWriter("savedgames.txt", true)));
					} catch(IOException i) {
						System.out.println("Could not open savedgames.txt");
					}
					
					Image scaled = sprites[usersprite].getScaledInstance(60, 60, Image.SCALE_SMOOTH);
					ImageIcon icon = new ImageIcon(scaled);
					
					FillerPanel popup = new FillerPanel();
					JTextField gn = new JTextField("game name");
					gn.setPreferredSize(new Dimension(130, 20));
					popup.add(new JLabel("Save your game as... "));
					popup.add(gn);
					popup.setPreferredSize(new Dimension(200, 80));
					
					JOptionPane jop = new JOptionPane();
					
					int result = jop.showConfirmDialog(null, popup, "Save game", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
					if (result == JOptionPane.OK_OPTION) {		// save user's game's customization information under the name they gave it by printing the information to the saved games text file.
						saved = true;				// the game has been saved.
						gamename = gn.getText();
						if (gamename == null) gamename = "untitled";
						pw.println("//" + gamename);
						pw.println("monsternum: " + monsternum);
						pw.println("itemnum: " + itemnum);
						pw.println("monsterspeed: " + monsterspeed + " ");
						pw.println("up: " + up);
						pw.println("left: " + left);
						pw.println("down: " + down);
						pw.println("right: " + right);
						pw.println("usersprite: " + usersprite + " ");
						pw.println("monstersprite: " + monstersprite + " ");
						pw.println("itemsprite: " + itemsprite + " ");
						if (iscollectable) pw.println("iscollectable: t.setrue");
						else if (iscollectable == false) pw.println("iscollectable: false");
						pw.println("winstring: " + winstring);
						pw.println("losestring: " + losestring);
						pw.println();
						
						pw.close();
					}
				}
			});
			buttons.add(save);
			
			add(holder, "board");
			add(options, "customize");
			
			
			reset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setboard();
				}
			});
			popout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {		// "pop" the game window out when the user wishes to play his/her game.
					PopoutBoard popout = new PopoutBoard();
					popout.addKeyListener(popout);			// set the variables' values according to the user's customizations.
					popout.timer.setDelay(monsterspeed);
					win = false;
					lose = false;
					window = new JFrame(gamename);
					window.getContentPane().add(popout);
					window.setSize(500, 633);
					window.setLocation(50, 50);
					window.setResizable(false);
					window.setVisible(true);
					window.setFocusable(false);
					popout.setFocusable(true);
		
					popout.grabFocus();
				}
			});
			
		}
		
		class BoardPanel extends JPanel {		// panel showing the actual game's "game board"; not playable.
			public BoardPanel() {
				setLayout(new GridLayout(5, 1));
				setBackground(Color.gray);
			}
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(Color.white);
				g.fillRect(40, 40, 400, 400);		// paint white "gameboard" 
				
				g.setColor(Color.lightGray);		// paint board's gray gridlines
				int j = 40;
				for (int i = 1; i <= 11; i++) {
					g.fillRect(j, 40, 3, 400);
					g.fillRect(40, j, 403, 3);
					j += 40;
				}
				
				// if the items are not collectable, paint the door at the bottom right corner.
				if (iscollectable == false) g.drawImage(sprites[doorsprite], 404, 404, 35, 35, this);
				
				int x = userx * 40 + 44;			// paint user
				int y = usery * 40 + 44;
				//g.drawImage(dawei, x, y, 35, 35, this);
				//g.drawImage(anarca, x, y, 35, 35, this);
				g.drawImage(sprites[usersprite], x, y, 35, 35, this);
				
				for (int i = 0; i < itemnum; i++) {		// paint stationary pieces
					x = items[i][0]; 
					y = items[i][1];
					x = x * 40 + 44;
					y = y * 40 + 44;
					g.drawImage(sprites[itemsprite], x, y, 35, 35, this);
				}
				
				for (int i = 0; i < monsternum; i++) {		// paint monsters
					x = monsters[i][0]; 
					y = monsters[i][1];
					x = x * 40 + 44;
					y = y * 40 + 44;
					g.drawImage(sprites[monstersprite], x, y, 35, 35, this);
				}
			}
		}
		
		class PopoutBoard extends JPanel implements KeyListener {		// a playable game board panel that is able to pop out in a separate window.		
			Timer timer;
			Mover mover;
			
			public PopoutBoard() {
				setLayout(new GridLayout(5, 1));
				setBackground(Color.gray);
				
				mover = new Mover();
				timer = new Timer(monsterspeed, mover);
				timer.start();
			}
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(Color.white);
				g.fillRect(40, 40, 400, 400);
				
				g.setColor(Color.lightGray);
				int j = 40;
				for (int i = 1; i <= 11; i++) {
					g.fillRect(j, 40, 3, 400);
					g.fillRect(40, j, 403, 3);
					j += 40;
				}
				
				if (iscollectable == false) g.drawImage(sprites[doorsprite], 404, 404, 35, 35, this);
				
				int x = userx * 40 + 44;
				int y = usery * 40 + 44;
				//g.drawImage(dawei, x, y, 35, 35, this);
				//g.drawImage(anarca, x, y, 35, 35, this);
				g.drawImage(sprites[usersprite], x, y, 35, 35, this);
				
				for (int i = 0; i < itemnum; i++) {
					x = items[i][0]; 
					y = items[i][1];
					x = x * 40 + 44;
					y = y * 40 + 44;
					g.drawImage(sprites[itemsprite], x, y, 35, 35, this);
				}
				
				for (int i = 0; i < monsternum; i++) {
					x = monsters[i][0]; 
					y = monsters[i][1];
					x = x * 40 + 44;
					y = y * 40 + 44;
					g.drawImage(sprites[monstersprite], x, y, 35, 35, this);
				}
			
				g.setFont(new Font("Helvetica", Font.BOLD, 50));
				if (lose) {			// when the user loses, paint the appropriate message to be displayed in red and stop the timer.
					g.setColor(Color.red);
					g.drawString(losestring, 120, 250);
					timer.stop();
				}
				if (win) {
					g.setColor(Color.green);
					g.drawString(winstring, 120, 250);
					timer.stop();
				}
			}
			public void keyPressed(KeyEvent e) {	// controls user's movement on the gameboard.
				char c = e.getKeyChar();
				int prevx = userx;
				int prevy = usery;
				if (win == false && lose == false && timer.isRunning()) {			// only allow user to move if the game is running
					if (c == up) usery--;			// move up
					if (c == left) userx--;			// move left
					if (c == down) usery++;			// move down
					if (c == right) userx++;		// move right
				}
				
				if (c == ' ' && timer.isRunning()) timer.stop();		// when space is pressed, pause or play the game.
				else if (c == ' ' && timer.isRunning() == false) {
					timer.start();
					if (win == true || lose == true) setboard();	// if the game is over, also reset the game board.
				}
				
				for (int i = 0; i < itemnum; i++) {
					if (userx == items[i][0] && usery == items[i][1]) {
						if (iscollectable) {								// increase item counter and "move" item 
							items[i][0] = -10;									//	"off the board" so the user "collects"
							items[i][1] = -10;									//	 it if the user moves onto it.
							itemcount++;
						}
						else if (iscollectable == false) {		// don't let the player move (move the user back to his/her previous location) if he/she tries to run onto an obstacle.
							userx = prevx;
							usery = prevy;
						}
					}
					if (itemcount == itemnum) win = true;			// if all items are collected, the game is won.
				}
				
				if (iscollectable == false) {		// if the items are not collectable, the user wins if he/she reaches the door.
					if (userx == 9 && usery == 9) win = true;
				}
				
				for (int i = 0; i < monsternum; i++) {
					if (userx == monsters[i][0] && usery == monsters[i][1]) lose = true;	// if user touches a monster, 
				}																			//	the game is lost.
			
				if (userx < 0) userx = 9;			// when the user moves to the far right or left of the board, "loop" him/her to the opposite side.
				else if (userx > 9) userx = 0;
					
				if (usery < 0) usery = 9;			// "loop" user to bottom or top.
				else if (usery > 9) usery = 0;
			
				repaint();
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
			
			private class Mover implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < monsternum; i++) {			// go through monsters array to change every monster's position
						int x = monsters[i][0];
						int y = monsters[i][1];
						switch ((int)(Math.random() * 4) + 1) {		// randomly move in a direction.
							case 1: x++; break;		// right
							case 2: y++; break;		// down
							case 3: x--; break;		// left
							case 4: y--; break;		// up
						}
						
						if (x > 9) x = 0;		// if monster moves all the way to the right, wrap it back to far left.
						else if (x < 0) x = 9;		// wrap far left to right.
					
						if (y > 9) y = 0;		// wrap far bottom to top.
						else if (y < 0) y = 9;		// wrap far top to bottom.
						
						for (int j = i; j >= 0; j--) {
							for (int k = 0; k < itemnum; k++) {		// if the monster collides into another monster or a stationary item, move them back and generate another random new location.
								while ((x == monsters[j][0] && y == monsters[j][1]) || (x == items[k][0] && y == items[k][1])) {
									x = monsters[i][0];
									y = monsters[i][1];
									switch ((int)(Math.random() * 4) + 1) {
										case 1: x++; break;
										case 2: y++; break;
										case 3: x--; break;
										case 4: y--; break;
									}
									if (x > 9) x = 0;
									else if (x < 0) x = 9;
									if (y > 9) y = 0;
									else if (y < 0) y = 9;
								}
							}
						}
						
						monsters[i][0] = x;
						monsters[i][1] = y;
						
						if (userx == monsters[i][0] && usery == monsters[i][1]) lose = true;	// if a monster touches user, 
					}																			//	the game is lost.
						
					repaint();
				}
			}
		}
		
		public void setdefault() {								// sets default values for variables of class GamePanel.
			monsterspeed = 750;
			monsternum = 3;
			itemnum = 3;
			itemcount = 0;
			iscollectable = true;
			usersprite = 0;
			monstersprite = 4;
			itemsprite = 7;
			doorsprite = 26;
			up = 'w';
			left = 'a';
			down = 's';
			right = 'd';
			winstring = "YOU WIN!";
			losestring = "YOU LOSE!";
			gamename = "";
			saved = true;
		}
		
		public void setboard() {		// sets the game board based on customized values.
			int x, y;
			boolean prevsaved = saved;
			
			for (int i = 0; i < monsternum; i++) {				// sets random starting coordinates for monsters.
				x = (int)(Math.random() * 10);
				y = (int)(Math.random() * 10);
				for (int j = i; j >= 0; j--) {
					while (x == monsters[j][0] && y == monsters[j][1]) {	// make sure monsters are not placed on top of each other.
						x = (int)(Math.random() * 10);
						y = (int)(Math.random() * 10);
					}
				}
				if (iscollectable == false) {		// if items are not collectable, make sure the monsters are not placed on top of the door.
					while ((x == 9 && y == 9) || (x == 0 && y == 0)) {
						x = (int)(Math.random() * 10);
						y = (int)(Math.random() * 10);
					}
				}
				monsters[i][0] = x;
				monsters[i][1] = y;
			}
			
			for (int i = 0; i < itemnum; i++) {				// sets random starting coordinates for items.
				x = (int)(Math.random() * 10);
				y = (int)(Math.random() * 10);
				for (int j = i; j >= 0; j--) {
					while (x == items[j][0] && y == items[j][1]) {	// make sure items are not placed on top of each other.
						x = (int)(Math.random() * 10);
						y = (int)(Math.random() * 10);
					}
				}
				for (int j = 0; j < monsternum; j++) {
					while (x == monsters[j][0] && y == monsters[j][1]) {	// make sure items are not placed on top of monsters.
						x = (int)(Math.random() * 10);
						y = (int)(Math.random() * 10);
					}
				}
				if (iscollectable == false) {
					while ((x == 9 && y == 9) || (x == 0 && y == 0)) {
						x = (int)(Math.random() * 10);
						y = (int)(Math.random() * 10);
					}
				}
				items[i][0] = x;
				items[i][1] = y;
			}
			
			userx = (int)(Math.random() * 10);			// set random starting coordinates for user
			usery = (int)(Math.random() * 10);
			for (int j = 0; j < monsternum; j++) {			// make sure user is not placed on top of a monster of stationary item.
				while (userx == monsters[j][0] && usery == monsters[j][1]) {
					userx = (int)(Math.random() * 10);
					usery = (int)(Math.random() * 10);
				}
			}
			for (int j = 0; j < itemnum; j++) {
				while (userx == items[j][0] && usery == items[j][1]) {
					userx = (int)(Math.random() * 10);
					usery = (int)(Math.random() * 10);
				}
			}
			if (iscollectable == false) {		// if the items are not collectable, the user starts in the upper left corner.
				userx = 0;
				usery = 0;
			}
			win = false;
			lose = false;
			itemcount = 0;
			
			monsterbuttons[monsternum - 1].setSelected(true);		// set the selected radio buttons and checkboxes to the customizations the user set.
			itembuttons[itemnum - 1].setSelected(true);
			options.speed.setValue(monsterspeed);
			
			if (iscollectable) options.collectable.setSelected(true);
			if (iscollectable == false) options.collectable.setSelected(false);
			
			saved = prevsaved;
			name.setText(gamename);
			if (saved == false) name.setForeground(Color.red);
			if (saved == true) name.setForeground(Color.black);
			
			updatetext();
			repaint();
		}
		
		public void updatetext() {								// updates the sample code text according to the user's customizations
			int indexT = codetext.indexOf("Timer(");			//	by creating a new string with variable values of the customizations where they belong in the text.
			int indexT2 = codetext.indexOf(", mover");
			int indexM = codetext.indexOf("monsternum =");
			int indexI = codetext.indexOf("itemnum =");
			final int indexU = codetext.indexOf("case 'w'");
			final int indexL = codetext.indexOf("case 'a'");
			final int indexD = codetext.indexOf("case 's'");
			final int indexR = codetext.indexOf("case 'd'");
			
			String sub1 = codetext.substring(0, indexT + 6);
			String sub2 = codetext.substring(indexT2, indexM + 13);
			String sub3 = codetext.substring(indexM + 14, indexI + 10);
			String sub4 = codetext.substring(indexI + 11, indexU + 6);
			String sub5 = codetext.substring(indexU + 7, indexL + 6);
			String sub6 = codetext.substring(indexL + 7, indexD + 6);
			String sub7 = codetext.substring(indexD + 7, indexR + 6);
			String sub8 = codetext.substring(indexR + 7, codetext.length() - 1);
			
			String newtext = sub1 + monsterspeed + sub2 + monsternum + sub3 + itemnum + sub4 + up + sub5 + left + sub6 + down + sub7 + right + sub8;
			
			code.setText(newtext);
			code.setCaretPosition(0);
		}
		
		public void mousePressed(MouseEvent e) {
			cards.next(this);
		}
		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mouseClicked(MouseEvent e) { }
		public void mouseReleased(MouseEvent e) { }
		
		class DesignPanel extends JPanel {
			JSlider speed;				// slider for user to select the speed of the game's monsters.
			JButton back;				// button to return to the panel containing gameboard (previous panel in cardlayout).
			SpritePane spritepane;
			JCheckBox collectable;		// checkbox for whether or not stationary items in the game 
										//	are to be collected or not; if not, the player instead reaches a "door" to win.
			JButton keys, message;			// buttons to bring up popups prompting for new keyboard control keys and win/lose display messages, respectively.
			
			JLabel userch, monsterch, itemch;	// "tabs" or areas of text for user to click on to determine if he/she wishes to change the sprite of the user, monster, or item.
			
			public DesignPanel() {
				setLayout(null);
				
				JLabel monsterS = new JLabel("Monster speed (milliseconds):");
				back = new JButton("Back");
				
				JLabel faster = new JLabel("faster");
				JLabel slower = new JLabel("slower");
				
				speed = new JSlider(250, 1250, 750);
				speed.setMajorTickSpacing(250);
				speed.setPaintLabels(true);
				speed.setPaintTicks(true);
				speed.setSnapToTicks(true);
				speed.setOpaque(false);
				speed.addChangeListener(new ChangeListener() {	// when user moves the slider, set the new speed for monsters accordingly.
					public void stateChanged(ChangeEvent e) {
						monsterspeed = speed.getValue();
						updatetext();
						saved = false;
					}
				});				
				
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cards.next(back.getParent().getParent());		// go back to game board screen.
					}
				});
				
				add(speed);
				speed.setBounds(200, 470, 250, 50);
				add(monsterS);
				monsterS.setBounds(10, 455, 300, 50);
				add(back);
				back.setBounds(15, 550, 100, 50);
				add(faster);
				faster.setBounds(170, 510, 100, 50);
				add(slower);
				slower.setBounds(430, 510, 100, 50);
				
				userch = new JLabel("User sprite");
				monsterch = new JLabel("Monster sprite");
				itemch = new JLabel("Item sprite");
				userch.setOpaque(true);
				monsterch.setOpaque(true);
				itemch.setOpaque(true);
				userch.setBounds(20, 10, 120, 20);
				userch.setBackground(Color.white);
				monsterch.setBackground(Color.gray);
				itemch.setBackground(Color.gray);
				monsterch.setBounds(160, 10, 120, 20);
				itemch.setBounds(320, 10, 120, 20);
				
				userch.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent e) {
						userch.setBackground(Color.white);			// when the tab is clicked, set its background white and
						monsterch.setBackground(Color.gray);		//	set all other tabs' backgrounds gray.
						itemch.setBackground(Color.gray);
						spritepane.setsprite(1);				// let the panel know which variable to change.
						spritepane.repaint();
					}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
				});
				monsterch.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent e) {
						userch.setBackground(Color.gray);
						monsterch.setBackground(Color.white);
						itemch.setBackground(Color.gray);
						spritepane.setsprite(2);
						spritepane.repaint();
					}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
				});
				itemch.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent e) {
						userch.setBackground(Color.gray);
						monsterch.setBackground(Color.gray);
						itemch.setBackground(Color.white);
						spritepane.setsprite(3);
						spritepane.repaint();
					}
					public void mouseEntered(MouseEvent e) {} 
					public void mouseExited(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
				});
				
				add(userch);
				add(monsterch);
				add(itemch);
				
				spritepane = new SpritePane();
				spritepane.setBounds(20, 30, 420, 190);
				add(spritepane);
				spritepane.addMouseListener(spritepane);
				
				collectable = new JCheckBox("Stationary items are collectable (can be run over and picked up)", true);
				add(collectable);
				
				collectable.setOpaque(false);
				collectable.setBounds(20, 240, 400, 50);
				collectable.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {		// when the checkbox is selected, the stationary items are collectable.
						if (collectable.isSelected() == true) iscollectable = true;
						if (collectable.isSelected() == false) iscollectable = false;
						saved = false;
						setboard();						
					}
				});
				
				keys = new JButton("Set new key controls");
				keys.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Dimension jtfsize = new Dimension(17, 20);
						JTextField upfield = new JTextField("" + up);
						JTextField leftfield = new JTextField("" + left);
						JTextField downfield = new JTextField("" + down);
						JTextField rightfield = new JTextField("" + right);
						upfield.setPreferredSize(jtfsize);
						leftfield.setPreferredSize(jtfsize);
						downfield.setPreferredSize(jtfsize);
						rightfield.setPreferredSize(jtfsize);
						
						JPanel popup = new JPanel();		// throws a popup prompting for keyboard values to move the user in the game.
						JLabel info = new JLabel("<html>Please enter new keyboard control characters to move the user (ASCII characters only).<html>");
						info.setPreferredSize(new Dimension(230, 60));
						
						popup.add(info);
						popup.add(new JLabel("up: "));
						popup.add(upfield);
						popup.add(new JLabel("left: "));
						popup.add(leftfield);
						popup.add(new JLabel("down: "));
						popup.add(downfield);
						popup.add(new JLabel("right: "));
						popup.add(rightfield);
						popup.setPreferredSize(new Dimension(250, 100));
						
						int result = JOptionPane.showConfirmDialog(null, popup, "New keyboard controls", JOptionPane.OK_CANCEL_OPTION);
						
						if (result == JOptionPane.OK_OPTION) {		// if the user presses ok, set the new variable values according to his/her input.
							up = upfield.getText().charAt(0);
							left = leftfield.getText().charAt(0);
							down = downfield.getText().charAt(0);
							right = rightfield.getText().charAt(0);
							updatetext();
							saved = false;		// the user has made changes, so there is unsaved comments.
						}
					}
				});
				keys.setBounds(70, 300, 250, 50);
				add(keys);
				
				message = new JButton("Set new win/lose display messages");
				message.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JLabel info = new JLabel("<html>Please enter the messages displayed when the game is lost or won.<html>");
						info.setPreferredSize(new Dimension(230, 30));
						JTextField winmessage = new JTextField(winstring);
						winmessage.setPreferredSize(new Dimension(100, 20));
						JTextField losemessage = new JTextField(losestring);
						losemessage.setPreferredSize(new Dimension(100, 20));
						
						JPanel popup = new JPanel();	// throws a popup prompting for new text to display when the player loses or wins.
						popup.add(info);
						popup.add(new JLabel("game is won: "));
						popup.add(winmessage);
						popup.add(new JLabel("game is lost: "));
						popup.add(losemessage);
						popup.setPreferredSize(new Dimension(250, 90));
						
						int result = JOptionPane.showConfirmDialog(null, popup, "New won/lost messages", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {		// if the user presses ok, set the new string values according to his/her input.
							winstring = winmessage.getText();
							losestring = losemessage.getText();
							saved = false;		// there is now unsaved content.
						}
					}
				});
				message.setBounds(70, 380, 250, 50);
				add(message);
				
			}
			
			class SpritePane extends JPanel implements MouseListener {		// panel in the customization screen that displays the possible game sprites used and lets the user change the user, monster, or item sprites.
				int n;					// indicates which piece the user is changing the sprite of:
					// 1 for changing the player sprite,
					// 2 for monsters,
					// 3 for items.
				
				public SpritePane() {
					n = 1;
				}
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					setBackground(Color.white);
					g.setColor(Color.green);
					int x = 10;
					int y = 10;
					for (int i = 0; i < 36; i++) {
						if (i % 9 == 0 && i != 0) {
							x = 10;
							y += 45;
						}
						g.drawImage(sprites[i], x, y, 35, 35, this);	// first draws all sprites available.
						switch (n) {		// draw a green rectangle around the sprite currently used for that piece.
							case 1: 
								if (i == usersprite) g.drawRect(x - 5, y - 5, 45, 45);
								break;
							case 2:
								if (i == monstersprite) g.drawRect(x - 5, y - 5, 45, 45);
								break;
							case 3:
								if (i == itemsprite) g.drawRect(x - 5, y - 5, 45, 45);
								break;
						}
						x += 45;
					}
				}
				public void setsprite(int i) {		// set a new value for sprite piece indicator.
					n = i;
				}
				public void mousePressed(MouseEvent e) {
					int x = e.getX();
					int y = e.getY();
					
					int a = 10;
					int b = 10;
					for (int i = 0; i < 36; i++) {
						if (i % 9 == 0 && i != 0) {
							a = 10;
							b += 45;
						}
						if (x >= a - 5 && x <= a + 40 && y >= b - 5 && y <= b + 40) {	// set the sprite as whichever image the user clicked on.
							switch (n) {
								case 1: usersprite = i; break;
								case 2: monstersprite = i; break;
								case 3: itemsprite = i; break;
							}
						}
						a += 45;
					}
					saved = false;
					repaint();
				}
				public void mouseEntered(MouseEvent e) { }
				public void mouseExited(MouseEvent e) { }
				public void mouseClicked(MouseEvent e) { }
				public void mouseReleased(MouseEvent e) { }
			}
			
			public void mousePressed(MouseEvent e) {
				cards.next(e.getComponent().getParent().getParent());		// go back to game board screen.
			}
			public void mouseEntered(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
			public void mouseClicked(MouseEvent e) { }
			public void mouseReleased(MouseEvent e) { }
		}
		
	}
	
}

class ChallengePanel extends JPanel {
	JPanel holder;				// panel with CardLayout containing the set of question panels.
	QPanel[] questions;			// array of QPanel objects, each QPanel being an individual question.
	boolean[] asked;			// array accompanying QPanel array, stating true if the corresponding question has already been shown to the user.
	ScorePanel scorepan;
	CardLayout cards;
	
	Timer countdown;		// timer for initial countdown before the user starts the game
	Countdown cd;			// action event generator for countdown
	Timer timer;			// timer for countdown showing how much time the user has left
	Mover mover;			// action event generator for timer
	
	MyButton start, toscores;		// buttons to start the game or view high scores, respectively.
	JLabel counttime;			// label showing the intial countdown time.
	int countdowntime;				// initial countdown screen right before the user starts the game; implemented so the user has time to get ready to play.
	int time;						// amount of time the player has left in the game; originally start with 2 minutes on the clock.
	int tt;							// number of seconds the player took to correctly answer a question.
	int score;							// player's score in the current game.
	int[] highscores;					// array of player's top 10 scores.
	boolean ingame, played;
	
	public ChallengePanel() {
		setLayout(new BorderLayout());
		questions = new QPanel[13];
		asked = new boolean[13];
		
		countdowntime = 3;
		time = 80;
		tt = 0;
		
		cd = new Countdown();
		countdown = new Timer(1000, cd);
		mover = new Mover();
		timer = new Timer(1000, mover);
		
		holder = new JPanel();
		cards = new CardLayout();
		holder.setLayout(cards);
		
		JPanel getready = new JPanel();
		getready.setBackground(Color.black);
		JLabel ready = new JLabel("Ready?");
		ready.setForeground(Color.green);
		ready.setFont(new Font("Nimbus Mono L", Font.PLAIN, 180));
		start = new MyButton("START", 28);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				countdown.start();
			}
		});
		counttime = new JLabel("3");
		counttime.setForeground(Color.green);
		counttime.setFont(new Font("Nimbus Mono L", Font.PLAIN, 150));
		getready.setLayout(null);
		getready.add(ready);
		ready.setBounds(10, 50, 800, 200);
		getready.add(start);
		start.setBounds(400, 300, 300, 100);
		getready.add(counttime);
		counttime.setBounds(450, 430, 100, 150);
		
		toscores = new MyButton("HIGH SCORES", 28);
		toscores.setBounds(800, 400, 150, 100);
		toscores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.last(holder);
				played = false;
			}
		});
		getready.add(toscores);
		
		highscores = new int[10];
		
		scorepan = new ScorePanel();
		
		holder.add(getready, "start");
		
		for (int i = 0; i < 13; i++) {
			questions[i] = new QPanel(i + 1);			// add all question panels to the set.
			holder.add(questions[i], "question " + (i + 1));
		}
		
		holder.add(scorepan, "scores");
		
		add(holder);
	}
	
	class ScorePanel extends JPanel {			// panel showing high scores of Challenge mode.
		Scanner read;					// reads the highscores text file for the user's high scores.
		PrintWriter write;				// writes the new high scores to the text file.
		JButton back;				// button pressed to go back to the initial challenge mode panel screen.
		
		boolean updated;						// whether or not a new high score has been added to the high score list.
		
		public ScorePanel() {
			setLayout(null);
			setBackground(Color.black);
			
			try {
				read = new Scanner(new File("highscores.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("Could not load high scores.");
			}
			
			for (int i = 0; i < 10; i++) {			// retrieve high scores from external file highscores.txt and fill the highscores array with those values.
				highscores[i] = Integer.parseInt(read.nextLine());
			}
			read.close();
			
			back = new MyButton("BACK", 24);
			back.setBounds(800, 500, 100, 50);
			back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cards.first(holder);		// go back to the initial Challenge Mode "start" screen.
					updated = false;			// reset variables.
					countdowntime = 3;
					counttime.setText("3");
				}
			});
			add(back);
		}
		
		public void updatescores() {				// method to check if a new high score should be added to the list (and add it if necessary).
			try {
				write = new PrintWriter(new File("highscores.txt"));
			} catch (IOException e) {
				System.out.println("Could not load high scores.");
			}
			
			updated = false;
			int temp = 0;										// temporary variable created to move values down the highscore list when a new high score "pushes" other scores down.
			for (int i = 0; i < 10; i++) {
				int temp2 = highscores[i];
				if (updated == true) highscores[i] = temp;
				temp = temp2;
				if (score > highscores[i] && updated == false) {		// if the user's score is higher than a score currently on the high score list; do not add the new highscore more than once.
					highscores[i] = score;						// replace the old score with the user's new score.
					updated = true;									// high scores list has been updated. 
				}
				write.println("" + highscores[i]);			// print the updated scores to highscores.txt
			}
			write.close();
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.green);
			g.setFont(new Font("Nimbus Mono L", Font.PLAIN, 30));
			g.drawString("HIGH SCORES", 400, 40);
			int y = 100;
			for (int i = 0; i < 10; i++) {
				g.drawString("" + highscores[i], 60, y);		// paint the list of high scores.
				y += 45;
			}
			g.setFont(new Font("Nimbus Mono L", Font.PLAIN, 34));
			if (updated == true) {
				g.drawString("New high score!", 600, 180);		// if the user earned a score that made it onto the high score list, paint "new high score".		
			}
			if (played == true) {			// if the user played the game, paint the score the he/she earned.
				if (score == 1) g.drawString("You scored " + score + " point", 600, 220);
				if (score != 1) g.drawString("You scored " + score + " points", 600, 220);
			}
		}
	}
	
	class QPanel extends JPanel implements ActionListener {			// class of panels showing questions in challenge mode.
		JTextField answer;				// text box for user to input their answer in
		String ans;				// string taken from answer text box
		int n;						// a question number assigned to each question
		Font def;				// my custom default font
		int random;					// random number created to display a random question
		
		public QPanel(int i) {
			setLayout(null);
			setBackground(Color.black);
			def = new Font("Nimbus Mono L", Font.PLAIN, 24);
			n = i;
			
			answer = new JTextField("");
			answer.setFont(def);
			answer.setForeground(Color.green);
			answer.setBackground(Color.black);
			answer.addActionListener(this);
			
			random = (int)(Math.random() * 6) + 1;
			
			switch (n) {				// different answer text box dimensions and sizes for each question
				case 1: answer.setBounds(240, 109, 340, 30); break;
				case 2: answer.setBounds(20, 210, 540, 30); break;
				case 3: answer.setBounds(20, 100, 220, 30); break;
				case 4: answer.setBounds(190, 109, 380, 30); break;
				case 5: answer.setBounds(20, 109, 630, 30); break;
				case 6: answer.setBounds(20, 169, 500, 30); break;
				case 7: answer.setBounds(20, 169, 500, 30); break;
				case 8: answer.setBounds(20, 169, 500, 30); break;
				case 9: answer.setBounds(20, 100, 220, 30); break;
				case 10: answer.setBounds(60, 179, 360, 30); break;
				case 11: answer.setBounds(80, 109, 260, 30); break;
				case 12: answer.setBounds(20, 150, 400, 30); break;
				case 13: answer.setBounds(20, 150, 400, 30); break;
			}
			
			add(answer);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.white);
			g.setFont(def);
			
			switch (n) {				// each question number has a different question and correct answer pertaining to Java KeyListeners and Timers.
				case 1: 
					g.drawString("Implement a KeyListener in class", 20, 40);
					g.drawString("Challenge.", 20, 70);
					g.drawString("class Challenge                          {", 20, 130);
					ans = "implements KeyListener";
					break;
				case 2:
					g.drawString("Declare and initialize a timer", 20, 40);
					g.drawString("with taskperformer \"mover\"", 20, 70);
					g.drawString("that creates an event every", 20, 100);
					if (random > 1) g.drawString(random + " seconds.", 20, 130);
					if (random == 1) g.drawString(random + " second.", 20, 130);
					g.drawString("Mover mover = new Mover();", 20, 190);
					ans = "Timer timer = new Timer(" + random + "000, mover);";
					break;
				case 3:
					g.drawString("Start a Timer called \"timer\".", 20, 40);
					ans = "timer.start();";
					break;
				case 4: 
					g.drawString("Implement an ActionListener in", 20, 40);
					g.drawString("class Mover.", 20, 70);
					g.drawString("class Mover                              {", 20, 130);
					ans = "implements ActionListener";
					break;
				case 5: 
					g.drawString("Make the required ActionListener", 20, 40);
					g.drawString("method.", 20, 70);
					g.drawString("{", 660, 130);
					ans = "public void actionPerformed(ActionEvent e)";
					break;
				case 6: 
					g.drawString("Fill in the missing required", 20, 40);
					g.drawString("KeyListener methods.", 20, 70);
					g.drawString("public void keyTyped(KeyEvent e) {}", 20, 130);
					g.drawString("public void keyPressed(KeyEvent e) {}", 20, 160);
					g.drawString("{}", 540, 190);
					ans = "public void keyReleased(KeyEvent e)";
					break;
				case 7: 
					g.drawString("Fill in the missing required", 20, 40);
					g.drawString("KeyListener methods.", 20, 70);
					g.drawString("public void keyReleased(KeyEvent e) {}", 20, 130);
					g.drawString("public void keyPressed(KeyEvent e) {}", 20, 160);
					g.drawString("{}", 540, 190);
					ans = "public void keyTyped(KeyEvent e)";
					break;
				case 8: 
					g.drawString("Fill in the missing required", 20, 40);
					g.drawString("KeyListener methods.", 20, 70);
					g.drawString("public void keyTyped(KeyEvent e) {}", 20, 130);
					g.drawString("public void keyReleased(KeyEvent e) {}", 20, 160);
					g.drawString("{}", 540, 190);
					ans = "public void keyPressed(KeyEvent e)";
					break;
				case 9: 
					g.drawString("Stop a Timer called \"timer\".", 20, 40);
					ans = "timer.stop();";
					break;
				case 10: 
					g.drawString("Set variable c as the user's", 20, 40);
					g.drawString("keyboard input.", 20, 70);
					g.drawString("public void keyPressed(KeyEvent e) {", 20, 130);
					g.drawString("char c;", 60, 160);
					g.drawString("}", 20, 310);
					ans = "c = e.getKeyChar();";
					break;
				case 11:
					g.drawString("Check that Timer timer is still", 20, 40);
					g.drawString("running.", 20, 70);
					g.drawString("if (                   ) {", 20, 130);
					g.drawString("}", 20, 250);
					ans = "timer.isRunning()";
					break;
				case 12:
					g.drawString("Import the whole Java library package", 20, 40); 
					g.drawString("that provides classes for dealing with", 20, 70);
					g.drawString("events fired by AWT components.", 20, 100);
					ans = "import java.awt.event.*;";
					break;
				case 13:
					g.drawString("Import the whole Java library package", 20, 40);
					g.drawString("that provides classes for dealing with", 20, 70);
					g.drawString("events fired by Swing components.", 20, 100);
					ans = "import javax.swing.event.*;";
					break;
			}
			g.drawString("" + time, 900, 40);				// paint the user's current score and time he/she has left.
			g.setFont(new Font("Nimbus Mono L", Font.PLAIN, 30));
			g.drawString("SCORE: " + score, 800, 500);
		}
		
		public void actionPerformed(ActionEvent e) {
			String useranswer = answer.getText().trim();
			boolean alldone = true;			// whether or not all the questions have been asked.
			
			if (useranswer.equals(ans) && timer.isRunning()) {
				int a = (int)(Math.random() * 13) + 1;
				for (int x = 0; x < 13; x++) {
					if (asked[x] == false) alldone = false;
				}
				if (alldone == true) {
					for (int x = 0; x < 13; x++) {
						asked[x] = false;
					}
				}
				while (asked[a - 1] == true) {
					a = (int)(Math.random() * 13) + 1;
				}
				answer.setText("");
				cards.show(holder, "question " + a);
				asked[a - 1] = true;
				
				if (tt <= 5) score += 3;
				if (tt > 5 && tt <= 15) score += 2;
				if (tt > 15) score++;
				
				tt = 0;
			}
			else if (useranswer.equals(ans) == false) {
				time -= 5;
				repaint();
			}
		}
	}
	
	class Mover implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			time--;
			tt++;
			if (time < 1) {
				timer.stop();
				ingame = false;
				time = 80;
				for (int x = 0; x < 13; x++) {
					questions[x].answer.setText("");
				}
				cards.show(holder, "scores");
				
				scorepan.updatescores();
				scorepan.repaint();
			}
			repaint();
		}
	}
	
	class Countdown implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			countdowntime--;
			if (countdowntime < 1) {
				countdown.stop();
				int a = (int)(Math.random() * 13) + 1;
				cards.show(holder, "question " + a);
				asked[a - 1] = true;
				timer.start();
				score = 0;
			}
			ingame = true;
			played = true;
			counttime.setText("" + countdowntime);
		}
	}

}

class MyButton extends JButton {				// a simple custom JButton class that uses the font Nimbus Mono L in green and has a black background - thematic for this game. 
	public MyButton(String str, int size) {		// text and font size can be adjusted.
		setText("<html>" + str + "<html>");
		setFont(new Font("Nimbus Mono L", Font.PLAIN, size));
		setForeground(Color.green);
		setBackground(Color.black);
	}
}

class FillerPanel extends JPanel {		// my own simple JPanel class for "filler" panels. 
	public FillerPanel() {				//	created so to not constantly call the setOpaque method.
		setOpaque(false);
	}
}