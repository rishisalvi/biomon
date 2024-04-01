// Rishi Salvi
// 4/25/23
// BioMon.java
// The game will help 9th grade biology student understand the importance of 
// certain adaptations and ecological niches are in an organism. By playing the 
// game, they will be able to understand how characteristics benefit organisms
// and why they evolved to have them. 

/// graphics imports
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
/// image/file IO imports
import java.util.Scanner; 
import java.io.FileNotFoundException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import java.io.FileWriter; 
/// layout imports
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
/// JComponents imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Insets; 
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
/// listener imports
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener; 
import java.awt.event.MouseEvent; 
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent; 
/// misc. imports
import javax.swing.Timer; 

// makes the panel that holds everything, including all of the card layouts
public class BioMon
{
    public BioMon()
    {
    }
    public static void main(String [] args)
    {
        BioMon bm = new BioMon();
        bm.run();
    }
    public void run()
    {
        JFrame frame = new JFrame("BioMon");
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocation(0,0);
        frame.setResizable(true);
        BioMonHolder bmh = new BioMonHolder();
        frame.getContentPane().add(bmh);
        frame.setVisible(true);
    }
}

// holder for the first card layout that opens when user runs the program
// contains the Start, Login, Instructions, Settings, and GameHolder panels
class BioMonHolder extends JPanel
{
    public BioMonHolder()
    {
        setBackground(Color.CYAN);
        CardLayout cards = new CardLayout();
        setLayout(cards);
        Information in = new Information(); 
        StartPanel sp = new StartPanel(this, cards, in); 
        add(sp, "Start"); 
        LoginPanel lp = new LoginPanel(this, cards, in); 
        add(lp, "Login");
        InstructionPanel ip = new InstructionPanel(this, cards); 
        add(ip, "Instructions");  
        SettingPanel sep = new SettingPanel(this, cards, in); 
        add(sep, "Settings");  
        GamePanelHolder gph = new GamePanelHolder(this, cards, in); 
        add(gph, "Game"); 
    }
}

// panel that first opens when user runs the program; contains multiple
// buttons that they can click to change panels; the game is locked until
// the user logs in; they can close the program by clicking quit at the 
// bottom; has a null layout
class StartPanel extends JPanel
{
	private BioMonHolder bmh; // instance of CardLayout container
	private CardLayout cards; // instance of CardLayout
	private Information info; // instance of Information class (used to 
							  // check username/logged in)
	private JButton startButton; // JButton that changes to GamePanelHolder
	private JButton instructButton; // JButton that changes to Instructions
	private JButton loginButton; // JButton that changes to Login
	private JButton quit; // JButton that closes the program
	private Image firstChar; // Image for the first character (if selected)
	private Image secondChar; // Image for second character (if selected)
	private Image defaultChar; // Image for default character (nothing selected)
	private JLabel usernameLabel; // JLabel that has user's username
	private JLabel levelLabel; // JLabel that has user's level
	private JLabel warningLabel; // tells user to login
	private Image[] misc; // holds the decorations
	
	public StartPanel(BioMonHolder bmhIn, CardLayout cardsIn, Information infoIn)
	{
		setLayout(null); 
		setBackground(new Color(173, 216, 230)); 
		bmh = bmhIn; 
		cards = cardsIn; 
		info = infoIn; 
		
		Font titleFont = new Font("Dialog", Font.BOLD + Font.ITALIC, 40); 
		Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20); 
		
		JLabel title = new JLabel("BioMon"); 
		title.setFont(titleFont); 
		title.setBounds(330, 60, 200, 50); 
		title.setForeground(new Color(18, 46, 29)); 
		add(title); 
		
		// all of the buttons
		ButtonHandler bh = new ButtonHandler(); 
		loginButton = new JButton("LOGIN"); 
		loginButton.setFont(buttonFont); 
		loginButton.setBounds(180, 150, 225, 75);
		loginButton.addActionListener(bh); 
		add(loginButton); 
		startButton = new JButton("START"); 
		startButton.setFont(buttonFont); 
		startButton.setBounds(180, 275, 225, 75);
		startButton.addActionListener(bh); 
		add(startButton); 
		instructButton = new JButton("INSTRUCTIONS"); 
		instructButton.setFont(buttonFont); 
		instructButton.setBounds(180, 400, 225, 75);
		instructButton.addActionListener(bh); 
		add(instructButton); 
		quit = new JButton("QUIT"); 
		quit.setFont(buttonFont); 
		quit.setBounds(180, 525, 225, 75);
		quit.addActionListener(bh); 
		add(quit); 
		
		ImageReader ir = new ImageReader(); 
		firstChar = ir.getMyImage("sprites/char1/1-1.png"); 
		secondChar = ir.getMyImage("sprites/char2/2-1.png"); 
		defaultChar = ir.getMyImage("sprites/misc/M-2.png");
		usernameLabel = new JLabel("Name: "); 
		usernameLabel.setFont(buttonFont); 
		usernameLabel.setBounds(530, 235, 230, 75);
		add(usernameLabel); 
		levelLabel = new JLabel("Challenge: "); 	
		levelLabel.setFont(buttonFont); 
		levelLabel.setBounds(530, 500, 230, 75);
		add(levelLabel); 
		warningLabel = new JLabel("<html> <left> Please LOGIN <br>" +
			"to continue! </left><html>");
		warningLabel.setFont(new Font("TimesRoman", Font.ITALIC, 20)); 
		warningLabel.setForeground(Color.RED); 
		warningLabel.setBounds(540, 185, 230, 75);
		add(warningLabel); 
		misc = ir.getMisc(); 
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		if (info.getChar() == 1)
			g.drawImage(firstChar, 510, 300, 170, 200, this); 
		else if (info.getChar() == 2)
			g.drawImage(secondChar, 510, 300, 170, 200, this);
		else
			g.drawImage(defaultChar, 510, 300, 170, 200, this);
		if (info.getLogged())
		{
			warningLabel.setVisible(false); 
			usernameLabel.setText("Name: " + info.getName()); 
			int challenge = info.getChallenge();
			if (challenge > 10)
				challenge = 10; 
			levelLabel.setText("Challenge: " + challenge); 
		}
		for (int i = -1; i < 12; i++)
		{
			g.drawImage(misc[5], i * (int)(Math.random()*5 + 70), 0, 100, 100, this);
			if (i % 3 == 0)
			{
				if (i % 2 == 0)
					g.drawImage(misc[3], i * (int)(Math.random()*5 + 70), 0, 95, 95, this);
				else	
					g.drawImage(misc[4], i * (int)(Math.random()*5 + 70), 0, 95, 95, this);
			}
		}
		for (int j = -1; j < 12; j++)
		{
			g.drawImage(misc[6], j * (int)(Math.random()*5 + 70), 
				getHeight() - 100, 100, 100, this);
		}
	}
	// handler class for all of the buttons; changes the card layout
	// depending on what the user has pressed and if they have met the
	// prerequirements (getLogged() == true)
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if (evt.getSource() == startButton && info.getLogged())
				cards.show(bmh, "Game"); 
			else if(evt.getSource() == instructButton)
				cards.show(bmh, "Instructions"); 
			else if (evt.getSource() == loginButton)
				cards.show(bmh, "Login"); 
			else if (evt.getSource() == quit)
				System.exit(0); // closes program
		}
	}
}

// panel that holds the instructions; user can change their setting for 
// what section the instructions are on; back button to go back to start; 
// BorderLayout (w/ North/Center)
class InstructionPanel extends JPanel
{
	private BioMonHolder bmh; // instance of CardLayout container
	private CardLayout cards; // instance of CardLayout
	private JButton back; // JButton that changes to Start 
	private JMenuBar sections; // JMenuBar that changes instruction sections
	private JTextArea instructions; // JTextArea that shows the instructions 
									// for a section
	private JLabel sectionName; // JLabel that changes to show the current section
	private Scanner insReader; // Scanner to read instructions.txt file
	private InstructionChanger ic; // changes what shows on Text Area
	private Image[] misc; // displays map instead of textbox
	private boolean mapSelected; // if map has been selected 
	private JScrollPane scroller; // allows user to move text area
	
	public InstructionPanel(BioMonHolder bmhIn, CardLayout cardsIn)
	{
		setLayout(new BorderLayout()); 
		setBackground(new Color(144, 238, 144)); 
		bmh = bmhIn; 
		cards = cardsIn; 
		
		ic = new InstructionChanger();
		
		ComponentPanel compPanel = new ComponentPanel(); 
		add(compPanel, BorderLayout.NORTH); 

		TextAreaPanel taPanel = new TextAreaPanel();
		add(taPanel, BorderLayout.CENTER); 
		
		ic.changeInstructions();

		ImageReader ir = new ImageReader(); 
		misc = ir.getMisc();
	}

	// JPanel that holds the JButton and JMenuBar
	class ComponentPanel extends JPanel
	{
		public ComponentPanel()
		{
			setBackground(new Color(144, 238, 144)); 
			setLayout(new FlowLayout(FlowLayout.CENTER, 300, 20)); 
			setPreferredSize(new Dimension(getWidth(), 110));
			Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20);
			back = new JButton("Back"); 
			ButtonHandler bh = new ButtonHandler(); 
			back.addActionListener(bh); 
			back.setFont(buttonFont); 
			add(back); 
			
			makeMenuBar();
		}
		
		// makes the menu bar for the instruction settings 
		public void makeMenuBar()
		{
			Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20);
			sections = new JMenuBar(); 
			JMenu sectionsList = new JMenu("Sections");
			sectionsList.setFont(buttonFont); 
			
			JMenuItem general = new JMenuItem("General"); 
			JMenuItem gameplay = new JMenuItem("Gameplay"); 
			JMenuItem challenges = new JMenuItem("Challenges"); 
			JMenuItem quiz = new JMenuItem("Quiz");
			JMenuItem map = new JMenuItem("Map");

			general.addActionListener(ic); 
			gameplay.addActionListener(ic); 
			challenges.addActionListener(ic); 
			quiz.addActionListener(ic); 
			map.addActionListener(ic);
			
			sectionsList.add(general); 
			sectionsList.add(gameplay);
			sectionsList.add(challenges);
			sectionsList.add(quiz);
			sectionsList.add(map);
			
			sections.add(sectionsList); 
			add(sections); 
		}

		// draws the decorations near the button/menu bar
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(misc[4], 150, 45, 90, 90, this);
			g.drawImage(misc[3], 550, 45, 90, 90, this);
		}
	}
	
	//JPanel that holds the JTextArea
	class TextAreaPanel extends JPanel
	{
		public TextAreaPanel()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 400, 20));
			setBackground(new Color(144, 238, 144)); 
			
			Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20);
			Font titleFont = new Font("Monospaced", Font.BOLD, 40);  
			
			sectionName = new JLabel("name of section"); 
			sectionName.setFont(titleFont); 
			add(sectionName); 
			
			instructions = new JTextArea("instructions!");
			instructions.setFont(buttonFont);
			instructions.setLineWrap(true);
			instructions.setWrapStyleWord(true);
			instructions.setOpaque(false);
			instructions.setEditable(false);
			instructions.setMargin(new Insets(10,10,10,10));
			scroller = new JScrollPane(instructions);
			scroller.setPreferredSize(new Dimension(500, 400)); 
			add(scroller);
		}

		// draws the map if user has clicked the Map button
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if(mapSelected)
				g.drawImage(misc[7], 50, 100, 675, 375, this);
		}
	}
	
	// handler for the back button at the top-left (goes to start page)
	class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			cards.first(bmh); 
		}
	}
	
	// changes instructions depending on what section the user clicks
	class InstructionChanger implements ActionListener
	{
		private String whatSection; // what section in Menu Bar was clicked
		
		public InstructionChanger()
		{
			whatSection = "General"; 
		}

		public void actionPerformed(ActionEvent evt)
		{
			whatSection = evt.getActionCommand(); 
			changeInstructions();
		}
		
		// change the instructions (text area value)
		public void changeInstructions()
		{
			sectionName.setText(whatSection);
			if (whatSection.equals("Map")) // displays the map + gets rid of text area
			{
				mapSelected = true; 
				scroller.setVisible(false);
				repaint(); 
			}
			else // changes text area value depending on what was pressed
			{
				mapSelected = false; 
				scroller.setVisible(true);
				FileReader fr = new FileReader(); 
				insReader = fr.readFile("instructions.txt");  
				String currentLine = "?"; 
				String sectionInstructions = "";
				while (!whatSection.equals(insReader.nextLine()))
				{}
				while (!currentLine.equals("") && insReader.hasNext())
				{
					currentLine = insReader.nextLine();
					if (currentLine.length() > 2 && currentLine.substring(0, 2).equals("->"))
					{
				
						sectionInstructions += "\n\n"; // 2 new lines
						currentLine = currentLine.substring(2);	
					}
					sectionInstructions += currentLine; 
				}
				instructions.setText(sectionInstructions);
				repaint(); // gets rid of map
			}
		}
	}
}

// panel that makes the user login in order to create a save or access 
// a previous save; has a JTextField where user enters their name; Images
// that represent the characters that the user can be; has 2 JButtons - 
// one to go to start and one to go to settings; BorderLayout(w/ North/Center)
class LoginPanel extends JPanel
{
	private BioMonHolder bmh; // instance of CardLayout container
	private CardLayout cards; // instance of CardLayout
	private JTextField username; // JTextField where user enters their name
	private JButton back; // JButton to go back to Start
	private JButton settings;  // JButton to go to Settings
	private Information info; // instance of Information class (used to 
							  // check username/logged in)
	private Image char1Image; // Image of the first sprite/option
	private Image char2Image;  // Image of the second sprite/option
	private JRadioButton char1Button; // button for first sprite/option
	private JRadioButton char2Button; // button for second sprite/option
	private int char1Modifier; // changes image of first sprite
	private int char2Modifier; // changes image of second sprite
	private Image[] misc; // displays map instead of textbox
	
	public LoginPanel(BioMonHolder bmhIn, CardLayout cardsIn, Information infoIn)
	{
		setLayout(new BorderLayout());
		setBackground(new Color(238, 144, 231)); 
		bmh = bmhIn; 
		cards = cardsIn; 
		info = infoIn; 
		
		ButtonPanel bp = new ButtonPanel(); 
		add(bp, BorderLayout.NORTH); 
		
		TextPanel tp = new TextPanel();
		add(tp, BorderLayout.CENTER); 

		ImageReader ir = new ImageReader(); 
		misc = ir.getMisc();
	}
	
	// holds both of the buttons
	class ButtonPanel extends JPanel
	{
		public ButtonPanel()
		{
			setBackground(new Color(238, 144, 231)); 
			setLayout(new FlowLayout(FlowLayout.CENTER, 300, 20)); 
			setPreferredSize(new Dimension(getWidth(), 110));
			Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20); 
			back = new JButton("Back"); 
			BListener bl = new BListener(); 
			back.addActionListener(bl); 
			back.setFont(buttonFont); 
			add(back); 
			settings = new JButton("Settings"); 
			settings.setFont(buttonFont); 
			settings.addActionListener(bl); 
			add(settings); 
		}

		// draw decorations near the buttons
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(misc[3], 150, 45, 90, 90, this);
			g.drawImage(misc[4], 550, 45, 90, 90, this);
		}
	}
	
	// has place for user to enter their name as well as two JLabels that
	// serve as instructions
	class TextPanel extends JPanel
	{
		public TextPanel()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 315, 50)); 
			setBackground(new Color(238, 144, 231)); 
			Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20); 
			Font titleFont = new Font("Monospaced", Font.BOLD, 40);
			 
			JLabel enter = new JLabel("Enter Username:"); 
			enter.setFont(titleFont); 
			add(enter); 
		
			username = new JTextField(); 
			username.setFont(buttonFont); 
			username.setPreferredSize(new Dimension(400, 30));
			TFListener tf = new TFListener(); 
			username.addActionListener(tf);
			add(username); 
		
			JLabel characterInfo = new JLabel("Select your character:"); 
			characterInfo.setFont(titleFont); 
			add(characterInfo); 
			
			ButtonGroup bg = new ButtonGroup();
			CharSelecter cs = new CharSelecter(); 
			char1Button = new JRadioButton(""); 
			bg.add(char1Button); 
			char1Button.addActionListener(cs);
			char1Button.setBackground(new Color(238, 144, 231)); 
			add(char1Button);
			char2Button = new JRadioButton(""); 
			bg.add(char2Button); 
			char2Button.addActionListener(cs); 
			char2Button.setBackground(new Color(238, 144, 231)); 
			add(char2Button); 
			
			// stack overflow: https://stackoverflow.com/questions/68594456/vscode-img-folder-location
			ImageReader ir = new ImageReader(); 
			char1Image = ir.getMyImage("sprites/char1/1-1.png"); 
			char2Image = ir.getMyImage("sprites/char2/2-1.png"); 
		}
		
		public void paintComponent(Graphics g) // draws the characters
		{
			super.paintComponent(g); 
			g.drawImage(char1Image, 175 - char1Modifier, 365 - char1Modifier, 
				170 + char1Modifier, 200 + (20/17)*char1Modifier, this); 
			g.drawImage(char2Image, 475 - char2Modifier, 365 - char2Modifier, 
				170 + char2Modifier, 200 + (20/17)*char2Modifier, this); 
		}
	}
	
	// listener for the textfield - sends info to Information class; also reads
	// users.txt to see if user has played the game before in order to save their
	// progress and allow them to continue
	class TFListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			boolean foundUser = false; 
            int levelIndex = 0; 
            int level = 0; 
            int settingIndex = 0; 
			String userInfo = username.getText(); 
			info.setName(userInfo); 
			info.setLogged(true); 
			username.setEditable(false); 

			FileReader fr = new FileReader();
			Scanner checkUser = fr.readFile("users.txt"); 
			PrintWriter writeUser = fr.printFile("users.txt");
			String ifUser = "";
			while (checkUser.hasNext())
			{
				ifUser = checkUser.nextLine(); // reads whole line
				if (userInfo.length() < ifUser.length() && 
					(userInfo + " ").equals(ifUser.substring(0, userInfo.length() + 1)))
				{
					foundUser = true; // if user has played the game
                    levelIndex = ifUser.lastIndexOf(" ");
                    level = Integer.parseInt(ifUser.substring(levelIndex + 1)); 
						// up to what level the user has completed
                    info.setChallenge(level); 
                    ifUser = ifUser.substring(0, levelIndex); 
                    settingIndex = ifUser.lastIndexOf(" "); // if hints are on
                    if (ifUser.substring(settingIndex + 1).equals("ON"))
						info.setHints("ON");
					else
						info.setHints("OFF");
				}
			}
			if (!foundUser) // if user has not played the game
			{
				writeUser.println(userInfo + " ON 1");
			}
			writeUser.close();
		}
	}
	
	// listener for buttons - changes the panel depending on the button
	class BListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if (info.getLogged() && evt.getSource() == settings)
				cards.show(bmh, "Settings"); 
			if (info.getLogged() && evt.getSource() == back)
				cards.show(bmh, "Start"); 
		}
	}
	
	// listener for JRadioButtons - set the selected character + increases character size
	class CharSelecter implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if (evt.getSource() == char1Button)
			{
				info.setChar(1);
				char1Modifier = 10; 
				char2Modifier = 0;  
			}
			else
			{
				info.setChar(2); 
				char2Modifier = 10;
				char1Modifier = 0; 
			}
			repaint(); 
		}
	}
}

// gives user option to change settings; contains 1 JButton to go back 
// to login panel; FlowLayout (CENTER)
class SettingPanel extends JPanel
{
	private BioMonHolder bmh; // instance of CardLayout container
	private Information info; // instance of Information
	private CardLayout cards; // instance of CardLayout
	private JButton back; // JButton that goes back to Login
	private JCheckBox hints; // JCheckBox if user wants hints
	private JCheckBox toggleTimed; // JCheckBox if time limit is enabled
	private JSlider timeLimit; // JSlider for timed mode
		
	public SettingPanel(BioMonHolder bmhIn, CardLayout cardsIn, Information infoIn)
	{
		setLayout(new BorderLayout()); 
		setBackground(new Color(237, 80, 80)); 
		bmh = bmhIn; 
		cards = cardsIn; 
		info = infoIn; 
		Font buttonFont = new Font("TimesRoman", Font.PLAIN, 25); 
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(237, 80, 80));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 300, 20)); 
		buttonPanel.setPreferredSize(new Dimension(getWidth(), 110));
		back = new JButton("Back"); 
		BListener bl = new BListener(); 
		back.addActionListener(bl); 
		back.setFont(buttonFont); 
		buttonPanel.add(back); 
		add(buttonPanel, BorderLayout.NORTH); 
		
		// hint checkbox
		JPanel componentPanel = new JPanel(); 
		componentPanel.setBackground(new Color(237, 80, 80)); 
		componentPanel.setLayout(new GridLayout(6, 1)); 
		JPanel hintsPanel = new JPanel();
		hintsPanel.setBackground(new Color(237, 80, 80)); 
		hintsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		hints = new JCheckBox("Toggle hints"); 
		hints.setFont(buttonFont); 
		CBListener cbl = new CBListener(); 
		if (info.getHints() == "ON") // otherwise off
			hints.setSelected(true);
		hints.addActionListener(cbl); 
		hints.setBackground(new Color(237, 80, 80)); 
		hintsPanel.add(hints);
		componentPanel.add(hintsPanel);
		
		// if timed mode checkbox
		JPanel timePanel = new JPanel();
		timePanel.setBackground(new Color(237, 80, 80)); 
		timePanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		toggleTimed = new JCheckBox("Enable timed mode (seconds)"); 
		toggleTimed.setFont(buttonFont); 
		toggleTimed.addActionListener(cbl); 
		toggleTimed.setBackground(new Color(237, 80, 80)); 
		timePanel.add(toggleTimed);
		componentPanel.add(timePanel);
		
		// timer checkbox
		timeLimit = new JSlider(15, 60, 30);
		timeLimit.setMajorTickSpacing(1);	
		timeLimit.setPaintTicks(true);
		timeLimit.setLabelTable(timeLimit.createStandardLabels(5)); 
		timeLimit.setPaintLabels(true);
		timeLimit.setBackground(new Color(237, 80, 80)); 
		timeLimit.setOrientation(JSlider.HORIZONTAL);
		SliderListener sl = new SliderListener();
		timeLimit.addChangeListener(sl);
		componentPanel.add(timeLimit);
		
		// warning label panels
		JPanel warning1Panel = new JPanel();
		warning1Panel.setBackground(new Color(237, 80, 80)); 
		warning1Panel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		JLabel warning1 = new JLabel("WARNING:");
		warning1.setForeground(Color.RED);
		warning1.setFont(new Font("TimesRoman", Font.ITALIC + Font.BOLD, 20));
		warning1Panel.add(warning1);
		componentPanel.add(warning1Panel);
		JPanel warning2Panel = new JPanel();
		warning2Panel.setBackground(new Color(237, 80, 80)); 
		warning2Panel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		JLabel warning2 = new JLabel("Clicking this button will wipe all of your progress!"); 
		warning2.setForeground(Color.BLACK);
		warning2.setFont(new Font("Monospaced", Font.BOLD, 25));
		warning2Panel.add(warning2);
		componentPanel.add(warning2Panel);

		// reset radio button panel
		JPanel resetPanel = new JPanel();
		resetPanel.setBackground(new Color(237, 80, 80)); 
		resetPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		JRadioButton reset = new JRadioButton("Reset progress"); 
		RBListener rbl = new RBListener(); 
		reset.addActionListener(rbl);
		reset.setFont(buttonFont);
		reset.setBackground(new Color(237, 80, 80)); 
		resetPanel.add(reset);
		componentPanel.add(resetPanel); 
		add(componentPanel, BorderLayout.CENTER); 
	}

	// listener class for button - changes to login page
	class BListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			cards.show(bmh, "Login"); 
		}
	}
	
	// listener class for radio button - wipes progress
	class RBListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			FileReader fr = new FileReader();
			PrintWriter writeUser = fr.printFile("users.txt");
			writeUser.println(info.getName() + " " + info.getHints() + " " + 1);
			info.setChallenge(1); 
			writeUser.close();
		}
	}
	// listener class for checkbox - if user wants any hints
	class CBListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if (hints.isSelected()) // if hint is selected now
			{
				info.setHints("ON"); 
				FileReader fr = new FileReader(); 
				PrintWriter pw = fr.printFile("users.txt");
				pw.println(info.getName() + " " + "ON" + " " + info.getChallenge());
				pw.close();
			}
			else // if hints are no longer selected
			{
				info.setHints("OFF");
				FileReader fr = new FileReader(); 
				PrintWriter pw = fr.printFile("users.txt");
				pw.println(info.getName() + " OFF " + info.getChallenge());
				pw.close();
			}
			if (toggleTimed.isSelected()) // if timed mode is on
			{
				info.setTimed(true);
			}
			else
			{
				info.setTimed(false); 
			}
		}
	}
	
	// listener class for slider - for timed mode limit
	class SliderListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt)
		{
			int value = timeLimit.getValue(); 
			info.setLimit(value);
		}
	}
}

// panel that is actually the game; the top is essentially the return 
// button and a JLabel that changes depending on where the user is; the
// center is a CardLayout with 9 cards that change when the user moves; 
// the south is a TextArea that provides information on what is going on 
// in the game; BorderLayout (w/ North, Center, South)
class GamePanelHolder extends JPanel
{
	private BioMonHolder bmh; // instance of CardLayout container
	private Information info; // instance of Information
	private CardLayout cards; // instance of CardLayout
	private JButton back; // JButton that goes back to start
	private JButton insButton; // JButton that goes to the Instruction panel
	private JLabel location; // JLabel that changes depending on where the user is
	private JTextArea scene; // JTextArea that holds the dialogue/challenges
	
	public GamePanelHolder(BioMonHolder bmhIn, CardLayout cardsIn, Information infoIn)
	{
		setLayout(new BorderLayout()); 
		setBackground(Color.BLACK);
		bmh = bmhIn; 
		cards = cardsIn; 
		info = infoIn; 
		
		HeadingPanel hp = new HeadingPanel(); 
		add(hp, BorderLayout.NORTH); 
		ScenePanel sp = new ScenePanel(); 
		add(sp, BorderLayout.SOUTH); 
		GamePanel gp = new GamePanel(location, info, this); 
		add(gp, BorderLayout.CENTER); 
	}
	
	public JTextArea getTextArea() // used to get scene in GamePanel class
	{
		return scene; 
	}

	// GridLayout (2x1); top is JButton that allows user to go back; bottom
	// is JLabel with location info
	class HeadingPanel extends JPanel
	{
		public HeadingPanel()
		{
			setLayout(new GridLayout(2, 1));
			setBackground(Color.BLACK);
			Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20);  
			
			JPanel buttons = new JPanel();
			buttons.setBackground(Color.BLACK);
			buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 285, 3)); 
			back = new JButton("Back"); 
			BListener bl = new BListener(); 
			back.addActionListener(bl);
			back.setFont(buttonFont);
			insButton = new JButton("Instructions");
			insButton.addActionListener(bl);
			insButton.setFont(buttonFont);
			buttons.add(back);
			buttons.add(insButton);
			add(buttons); 
			
			JPanel locationPanel = new JPanel();
			locationPanel.setBackground(Color.BLACK);
			locationPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
			location = new JLabel("You are currently in: ???"); 
			location.setForeground(new Color(255, 0, 0));
			location.setFont(buttonFont); 
			locationPanel.add(location); 
			add(locationPanel); 
		}
		
		// listener class for back button - goes to start panel
		class BListener implements ActionListener
		{
			public void actionPerformed(ActionEvent evt)
			{
				if (evt.getSource() == back)
					cards.show(bmh, "Start"); 
				else 
					cards.show(bmh, "Instructions"); 
			}
		}
	}
		
	// contains the JTextArea that explains what's going on in the game - 
	// hints, challenges, etc. 
	class ScenePanel extends JPanel
	{
		public ScenePanel()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(Color.BLACK); 
			Font buttonFont = new Font("TimesRoman", Font.PLAIN, 16); 
			scene = new JTextArea("Welcome to BioMon, " + info.getName() + "!");
			scene.setFont(buttonFont); 
			scene.setMargin(new Insets(5, 5, 5, 5)); 
			scene.setLineWrap(true); 
			scene.setWrapStyleWord(true);
			scene.setEditable(false); 
			JScrollPane sceneScroller = new JScrollPane(scene);
			sceneScroller.setPreferredSize(new Dimension(785, 125)); 
			add(sceneScroller);
		}
	}
}

// the actual game; center of GamePanelHolder; essentially is a 3x3 grid
// that has different backgrounds based on where the user is; allows the user
// to move their sprite across the panel and changes the background if they
// leave the panel 
class GamePanel extends JPanel implements KeyListener, MouseListener
{ 
	/// general
	private Information info; // instance of Information class
	/// paintComponent()
	private ImageReader ir; // loads the images at the start of program
	private Image[] sprites; // array holding all of the animation parts (12 parts - all directions)
	private Timer move; // timer for the animation to work
	private int xPosChange; // distance from center (x)
	private int yPosChange; // distance from center (y)
	private int pos; // which position in animation sequence it is (different for each direction)
	private int startPos; // which direction it is (ex. N, S, E, W)
	private char keyPress; // value of key being held down
	private int whatPanel; // what panel in a 3x3 grid the player is in
	private JLabel location; // the heading at the top of GameHolder
	private boolean underground; // if user is underground in cave area
	private boolean showBiomon; // whether to show biomon or not
	private boolean captureBiomon; // if biomon is captured or not
	private boolean depositBiomon; // if biomon is deposited into the checkpoint
	private int bioxPos; // randomly generated x-position for biomon
	private int bioyPos; // randomly generated y-position for biomon
	private int checkxPos; // randomly generated x-position for checkpoint
	private int checkyPos; // randomly generated y-position for checkpoint
	private int frame; // what frame the ripple animation is at
	/// GamePanelHolder
	private GamePanelHolder gph; // used to get the Text Area at the bottom of panel
	private JTextArea scene; // JTextArea that displays challenges/dialogue
	/// GamePanel
	private GamePanel gp; // instance of this class (getting called in gamePlay)
	/// GamePlay
	private GamePlay gPlay; // changes the dialogue, comtrols what is currently happening
	/// Ripple Timer
	private Timer ripChange; // Timer that changes animation frame
	private Ripples rip; // instance of class that controls the ripples
	private boolean firstRipple; // ripple when program starts
	/// Listeners
	private int bioClicks; // how many clicks the user has on the biomon
	private int checkClicks; // how many clicks the user has on the checkpoint
	private boolean allowChange; // if user can change text area content
	private boolean hint1; // if user has clicked the first hint
	private boolean gameWon; // if user has won the game
	/// timed mode
	private double timeSpent; // how long the user has spent on the level (timed mode)
	private boolean levelFailed; // if user has failed the level (timed mode)
	private Timer howMuchTime; // controls the timer at the bottom (timed mode)
	private boolean timerStarted; // if timer has been started or not (timed mode)

	public GamePanel(JLabel locationIn, Information infoIn, GamePanelHolder gphIn)
	{
		info = infoIn; 
		location = locationIn; 
		gph = gphIn;
		gp = this; 
		underground = false; 
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBackground(new Color(37, 200, 37));	
		
		addKeyListener(this);
		addMouseListener(this); 
		ir = new ImageReader(); 

		// setting starting values
		startPos = 1; 
		pos = 0; 
		whatPanel = 5;  
		frame = 0; 
		bioClicks = 0; 
		checkClicks = 0; 
		timeSpent = 0.0; 
		firstRipple = false; 
		showBiomon = false; 
		captureBiomon = false; 
		allowChange = true; 
		hint1 = false; 
		gameWon = false; 
		
		// timer
		Movement mover = new Movement(this);
		move = new Timer(60, mover);
		move.setInitialDelay(25); 	
		rip = new Ripples(); 
		ripChange = new Timer(200, rip); 

		// get text area
		scene = gph.getTextArea();
		gPlay = new GamePlay();

		// timed mode
		TimedMode tm = new TimedMode(); 
		howMuchTime = new Timer(10, tm);
	}
	
	// draws the background image based on which panel the player is in; 
	// changes to draw the character where on the panel they are and how
	// they are moving; changes the location TextArea at the top
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Challenges ch = new Challenges(info); // for checkpoint
		Image[] backgrounds = ir.getBackgrounds();
		Image[] biomon = ir.getBiomon(); 
		Image[] animation = ir.getAnimations(); 
		Image[] misc = ir.getMisc(); 
		int xPos = getWidth()/2; // find center (starting point at game)
		int yPos = getHeight()/2; 

		if (!firstRipple) // to randomly generate biomon & checkpoint locations at game start
		{
			firstRipple = true; 
			getRandomPos();
		}
		if (whatPanel == 5) // starting (center) panel
		{
			g.drawImage(backgrounds[0], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: meadows"); 
		}
		else if (whatPanel == 8)
		{
			g.drawImage(backgrounds[1], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: swamps"); 
		}
		else if (whatPanel == 6)
		{
			g.drawImage(backgrounds[2], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: deserts"); 
		}
		else if (whatPanel == 2)
		{
			g.drawImage(backgrounds[3], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: mountains"); 
			
		}
		else if (whatPanel == 1)
		{
			g.drawImage(backgrounds[4], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: volcanoes"); 
		}
		else if (whatPanel == 3)
		{
			g.drawImage(backgrounds[5], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: tundras"); 
		}
		else if (whatPanel == 4)
		{
			g.drawImage(backgrounds[6], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: rainforests"); 
		}
		else if (whatPanel == 9)
		{
			g.drawImage(backgrounds[7], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: coral reefs"); 
		}
		else if (whatPanel == 7) // cave biome (has two things)
		{
			if (underground)
				g.drawImage(backgrounds[9], 0, 0, getWidth(), getHeight(), this); 
			else 
				g.drawImage(backgrounds[8], 0, 0, getWidth(), getHeight(), this); 
			location.setText("You are currently: caves"); 
		}
		
		if (depositBiomon && info.getAnswered() && ch.getCheckpointLocation() == whatPanel)
		{ // draws biomon on trophy
			g.drawImage(biomon[info.getChallenge() - 1], checkxPos + 6, checkyPos - 18, 50, 50, this);
		}
		if (captureBiomon && info.getAnswered() && (whatPanel != 7 || (whatPanel == 7 && underground)) && 
			ch.getCheckpointLocation() == whatPanel && distance(checkxPos, checkyPos) > 70)
		{
			// if biomon is captured, player is in appropriate panel to 
			// deposit biomon, and distance from player to checkpoint is far
			g.drawImage(misc[0], checkxPos, checkyPos, 60, 60, this);
		}
		else if ((captureBiomon || depositBiomon) && info.getAnswered() && (whatPanel != 7 || (whatPanel == 7 && underground)) &&
			ch.getCheckpointLocation() == whatPanel)
		{
			// if biomon is captured, player is in appropriate panel to 
			// deposit biomon, and distance from player to checkpoint is close
			g.drawImage(misc[1], checkxPos, checkyPos, 60, 60, this);
		}

		if (!captureBiomon && showBiomon && distance(bioxPos, bioyPos) > 70 && info.getAnswered())
		{
			// if biomon isn't captured, player is far
			// ripple animation plays to display hidden biomon
			ripChange.start(); 
			g.drawImage(animation[frame], bioxPos, bioyPos, this); 
		}
		else if (!captureBiomon && showBiomon && info.getAnswered())
		{
			// biomon isn't captured, player is close
			// ripple animation ends to display biomon
			ripChange.stop(); 
			// -1 because it starts at 1
			g.drawImage(biomon[info.getChallenge() - 1], bioxPos, bioyPos, 90, 90, this); 
		}

		sprites = ir.getImages(info.getChar()); 
		g.drawImage(sprites[startPos + pos - 1], xPos + xPosChange, yPos + yPosChange, this); 
		if (captureBiomon && showBiomon && info.getAnswered())
		{
			// if biomon is captured (to display it on the person)
			g.drawImage(biomon[info.getChallenge() - 1], xPos + xPosChange + 25, 
				yPos + yPosChange + 5, 40, 40, this);
		}

		if (gameWon)
		{
			for (int i = 0; i < 10; i++) // draws all of the sprites
			{
				int size = (int)(Math.random()*80 + 30);
				g.drawImage(biomon[i], (int)(Math.random()*(getWidth() - 75)) + 50, 
					(int)(Math.random()*(getWidth() - 75)) + 50, size, size, this); 
			}
		}

		if(info.ifTimed()) // timed mode only
		{
			if (levelFailed) // timed expired (level failed)
			{
				g.setColor(Color.RED);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.BLACK);
				g.setFont(new Font("Monospaced", Font.BOLD, 30));
				g.drawString("Level failed. Click SPACE to restart.", 50, getHeight()/2 - 10);
			}
			else // increments text at the corner of screen
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 440, 90, 50);
				g.setColor(Color.WHITE);
				String strTimeSpent = timeSpent + "           "; // spaces to stop out of bounds exception
				if (timeSpent > 10)
					strTimeSpent = strTimeSpent.substring(0, 5);
				else
					strTimeSpent = strTimeSpent.substring(0, 4);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 23));
				g.drawString(strTimeSpent, 15, 465);
			}
		}
		requestFocusInWindow(); // for KeyListener to work
		gPlay.first(); // intro dialogue
	}
	
	// gets the distance between character position and checkpoint/biomon position
	public int distance(int xPos, int yPos)
	{
		int distance = 0; 
		int xDiff = getWidth()/2 + xPosChange - xPos - 25;
		int yDiff = getWidth()/2 + yPosChange - yPos - 160;
		distance = (int)Math.sqrt(xDiff * xDiff + yDiff * yDiff); 
		return distance; 
	}
	
	// randomly generates position for biomon and checkpoint everytime the user
	// enters a new panel
	private void getRandomPos()
	{
		bioxPos = (int)(Math.random()*(getWidth() - 75)) + 50; // have some buffer
		bioyPos = (int)(Math.random()*(getHeight() - 75)) + 50; 
		checkxPos = (int)(Math.random()*(getWidth() - 75)) + 50;
		checkyPos = (int)(Math.random()*(getHeight() - 75)) + 50; 
	}

	// responds when using presses a key; when user presses WASD, it starts
	// the timer to change the animation
	public void keyPressed(KeyEvent evt)
	{
		String hintText = "";
		int keyCode = evt.getKeyCode(); // for arrow keys
		keyPress = evt.getKeyChar();
		if (keyCode == KeyEvent.VK_UP)
			keyPress = 'w';
		else if (keyCode == KeyEvent.VK_DOWN)
			keyPress = 's'; 
		else if (keyCode == KeyEvent.VK_RIGHT)
			keyPress = 'd'; 
		else if (keyCode == KeyEvent.VK_LEFT)
			keyPress = 'a'; 
		if (info.getAnswered())
		{
			if (keyPress == 'w' || keyPress == 's' || keyPress == 'a' || keyPress == 'd')
			{
				move.start();
			}
			else if ((int)keyPress == 32) // if user presses SPACE
			{
				if (levelFailed) // resets the game
				{
					levelFailed = false; 
					howMuchTime.stop(); 
					timeSpent = 0; 
					timerStarted = false;
					gPlay.reset(0); 
					whatPanel = 5; 
					depositBiomon = false; 
					captureBiomon = false; 
					showBiomon = false; 
					bioClicks = 0; 
					checkClicks = 0; 
					xPosChange = 0; 
					yPosChange = 0; 
					allowChange = true; 
					gPlay.next(); 
					requestFocusInWindow();
					repaint();
				}
				else if (allowChange)
				{
					if (depositBiomon)
					{
						info.setChallenge(info.getChallenge() + 1);
						if (!info.ifTimed()) // timed mode does not write to file
						{
							FileReader fr = new FileReader();
							PrintWriter writeUser = fr.printFile("users.txt");
							writeUser.println(info.getName() + " " + info.getHints() + " " + info.getChallenge());
							writeUser.close();
						}
						depositBiomon = false; 
						timeSpent = 0; 
						getRandomPos(); // new place for next level
					}
					if (info.ifTimed() && !timerStarted)
					{
						timerStarted = true; 
						howMuchTime.start();
					}
					gPlay.next();
				}
				else if (captureBiomon && info.getHints() == "ON")
				{
					hintText = "\n\nPress 'h' to get a hint on where to drop off your BioMon.";
					scene.append(hintText);
				}
				else if (showBiomon  && info.getHints() == "ON")
				{
					hintText = "\n\nNavigate your character to the ripple and click" + 
					" the biomon 3 times.";
					scene.append(hintText);
				}
			}
			else if (keyPress == 'h' && info.getHints() == "ON")
			{
				FileReader fr = new FileReader(); 
				Scanner hintReader = fr.readFile("hints.txt"); 
				int numLines = (info.getChallenge() - 1) * 3; 
				if (hint1) // first hint
				{
					numLines++; 
					hint1 = false; 
				}
				else 
					hint1 = true; 
				for (int i = 0; i < numLines; i++)
				{
					hintReader.nextLine(); 
				}
				String hintLine = "\n\n" + hintReader.nextLine();
				scene.append(hintLine); 
			}
		}
	}
	public void keyTyped(KeyEvent evt){}
	
	// stops the timer when the user lets go of the key; resets animation
	// to default (no movement, looking forward)
	public void keyReleased(KeyEvent evt)
	{
		move.stop(); 
		startPos = 1; 
		pos = 0;
		repaint(); 
	}

	public void mousePressed(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseReleased(MouseEvent evt){}
	
	// user clicks their mouse to deposit/pick up biomon
	public void mouseClicked(MouseEvent evt)
	{
		Challenges ch = new Challenges(info);
		int clickX = evt.getX();
		int clickY = evt.getY();
		if (bioxPos <= clickX && clickX <= bioxPos + 90 
			&& bioyPos <= clickY && clickY <= bioyPos + 90)
		{
			bioClicks++; // if click is on biomon
		}
		if (bioClicks > 0 && bioClicks % 3 == 0 && showBiomon && 
			distance(bioxPos, bioyPos) < 70)
		{
			captureBiomon = true; 
			repaint();
		}

		if (checkxPos <= clickX && clickX <= checkxPos + 90 
			&& checkyPos <= clickY && clickY <= checkyPos + 90)
		{
			checkClicks++; // if click in on checkpoint
		}
		if (checkClicks > 0 && checkClicks % 3 == 0 && captureBiomon && showBiomon && info.getAnswered() &&
			ch.getCheckpointLocation() == whatPanel && distance(checkxPos, checkyPos) < 70) // reset
		{ // resets to move onto next challenge
			depositBiomon = true; 
			captureBiomon = false; 
			showBiomon = false; 
			allowChange = true; 
			bioClicks = 0; 
			checkClicks = 0; 
			timerStarted = false; 
			howMuchTime.stop();
			String text = "CONGRATULATIONS! \nYou have finished Challenge #" + 
			(info.getChallenge()) + "!\n\nPress SPACE to go to the next level.";
			scene.setText(text);
			repaint();
		}
	}
	public void mouseExited(MouseEvent evt){}

	// changes the position of the sprite depending on what the user is 
	// holding down; changes what area the user is in
	class Movement implements ActionListener
	{
		private GamePanel gp; // instance of GamePanel (for dimensions)
		public Movement(GamePanel gpIn)
		{
			gp = gpIn; 
		}
		
		// called by move; changes location of sprite (x-value, y-value) 
		// depending on what is being pressed; changes position in animation
		// - loop (either 2 frames or 4 frames depending on direction);
		public void actionPerformed(ActionEvent evt)
		{
		   if (keyPress == 'w') // UP
			{
				yPosChange -= 4; 
				pos = (pos + 1) % 4; 
				startPos = 9; 
				if (yPosChange < gp.getHeight()/2 *-1 + 5)
				{
					if (whatPanel > 3 && !underground)
					{
						whatPanel -= 3; 
						yPosChange = gp.getHeight()/2 - 5; 
						if (!depositBiomon)
							getRandomPos(); 
					}
					else
						yPosChange = gp.getHeight()/2 *-1 + 5;  
				}
			}
			else if (keyPress == 's') // DOWN
			{
				yPosChange += 4; 
				pos = (pos + 1)% 4; 
				startPos = 1; 
				if (yPosChange > gp.getHeight()/2 - 60)
				{
					if (whatPanel <= 6)
					{
						whatPanel += 3; 
						yPosChange = gp.getHeight()/2 * -1 + 5; 
						if (!depositBiomon)
							getRandomPos(); 
					}
					else
						yPosChange = gp.getHeight()/2 - 60; 
				}
			}
			else if (keyPress == 'a') // LEFT
			{
				xPosChange -= 4; 
				pos = (pos + 1) % 2;
				startPos = 5; 
				if (xPosChange < gp.getWidth()/2 *-1 + 5)
				{
					if (whatPanel != 1 && whatPanel != 4 && whatPanel != 7 && !underground)
					{
						whatPanel --; 
						xPosChange = gp.getWidth()/2 - 5; 
						if (!depositBiomon)
							getRandomPos(); 
					}
					else
						xPosChange = gp.getWidth()/2 *-1 + 5;
				}
			}
			else if (keyPress == 'd') // RIGHT
			{
				xPosChange += 4; 
				pos = (pos + 1) % 2; 
				startPos = 7; 
				if (xPosChange > gp.getWidth()/2 - 45)
				{
					if (whatPanel != 3 && whatPanel != 6 && whatPanel != 9)
					{
						whatPanel++; 
						xPosChange = gp.getWidth()/2 * -1 + 5; 
						if (!depositBiomon)
							getRandomPos(); 
					}
					else 
						xPosChange = gp.getWidth()/2 - 45;
				}
			}
			if (whatPanel == 7) // if user is in cave area
			{
				if (underground && xPosChange <= gp.getWidth()/2 *-1 + 10 && 
					yPosChange < 60 && yPosChange > -60)
				{
					xPosChange = -150; 
					yPosChange = -15; 
					underground = false; // make them aboveground
				}
				else if (!underground && xPosChange < -25 && xPosChange > -75 &&
					yPosChange < 5 && yPosChange > -45)
				{
					xPosChange = 0; 
					yPosChange = 0; 
					underground = true; // make them below ground
				}
			}
			repaint();
		}
	}
	
	// class that changes the dialog and is responsible for showing the quiz and
	// the game's progression; calls Quiz.java to make the quiz
	class GamePlay
	{
		private boolean alrFirst; // if orginal method has been printed or not
		private Scanner diaReader; //reads lines from challenges.txt
		private int challengeCounter; // which challenge the user is on
		private Quiz qu; // quiz panel
		private int counter; // FV so that is doesn't reset everytime next() is called
		public GamePlay()
		{
			alrFirst = false; 
			challengeCounter = 1; 
			FileReader fr = new FileReader();
			diaReader = fr.readFile("challenges.txt"); 
			counter = 0; 
		}
		
		public void first() // when the panel first shows up
		{
			if (!alrFirst)
			{
				alrFirst = true; 
				scene.setText("Welcome to BioMon, " + info.getName() + "!"); 
			}
		}
		
		public void next() // every other time the panel shows
		{
			challengeCounter = info.getChallenge();
			if (challengeCounter > 10 && counter % 3 == 0) // user has completed all of the challenges
			{
				scene.setText("CONGRATS ON COMPLETING THE GAME!"); 
				gameWon = true; 
				scene.setForeground(Color.YELLOW);
				scene.setFont(new Font("TimesRoman", Font.BOLD, 25));
			}
			else
			{
				if (counter % 3 == 2) // if quiz should be displayed
				{
					qu = new Quiz(challengeCounter, info, gp);
					// Stack Overflow: https://stackoverflow.com/questions/37664394/how-to-call-setvisible-from-another-class
					qu.getFrame().setVisible(true);
					showBiomon = true; 
					info.setAnswered(false);
					counter++; 
					allowChange = false; 
					repaint(); // start animation 
				}
				else
				{
					showBiomon = false; 
					changeDialogue();
					counter++; 
				}
			requestFocusInWindow();
			}
		}

		// change what is in the text area at the bottom; consists of a Scanner reading all of
		// the challenge prompt, setting it as the text in the text area, and a PrintWriter
		// setting that challenge into users.txt
		public void changeDialogue()
		{
			String currentLine = "?"; 
			String challenge = "";
			String strChallengeCounter = challengeCounter + ""; 
			while (diaReader.hasNext() && currentLine.indexOf(strChallengeCounter) == -1 && counter % 3 != 1)
			{	
				currentLine = diaReader.nextLine();
			}
			while(!currentLine.equals("") && diaReader.hasNext())
			{
				if (currentLine == "?")
					currentLine = ""; // used to get rid of ? at second prompt
				if (currentLine.indexOf("-") == 0)
					challenge += "\n"; // new line	
				challenge += currentLine; 
				currentLine = diaReader.nextLine();
			}
			scene.setText(challenge); 
		}

		// if user fails the level (resets dialogue)
		public void reset(int value)
		{
			info.setChallenge(1);
			challengeCounter = 1; 
			FileReader fr = new FileReader(); 
			diaReader = fr.readFile("challenges.txt");
			counter = 0;
		}
	}
	
	// draws the ripple animation
	class Ripples implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			frame = (frame + 1) % 4; 
			repaint();
		}
	}

	// increments the timer on the side in timed mode
	class TimedMode implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			timeSpent += 0.01; 
			if (timeSpent > info.getLimit())
				levelFailed = true; 
			repaint(); 
		}
	}
}

// returns what panel the checkpoint should be in depending on the challenge
class Challenges
{
	private Information info; // instance of Information class
	public Challenges(Information infoIn)
	{
		info = infoIn; 
	}
	public int getCheckpointLocation()
	{
		int challengeNum = info.getChallenge(); 
		if (challengeNum == 1)
			return 1; 
		else if (challengeNum == 2)
			return 8;
		else if (challengeNum == 3)
			return 9;
		else if (challengeNum == 4)
			return 5; 
		else if (challengeNum == 5)
			return 4;
		else if (challengeNum == 6)
			return 2;
		else if (challengeNum == 7)
			return 3;
		else if (challengeNum == 8)
			return 5; 
		else if (challengeNum == 9)
			return 7;
		else
			return 6;
	}
}

// reads the images (sprites, backgrounds) when the game is first running
// to prevent loading when panel is opened
class ImageReader 
{
	private Image[] char1Sprites; // all (12) of the sprites for 1st character
	private Image[] char2Sprites; // all (12) of the sprites for 2nd character
	private Image[] backgrounds; // all of the background images
	private Image[] animation; // has animation to represent biomon
	private Image[] biomon; // holds the biomon images
	private Image[] misc; // holds the misc. image (checkpoints)
	public ImageReader()
	{
		char1Sprites = new Image[12]; 
		char2Sprites = new Image[12];
		backgrounds = new Image[10];
		animation = new Image[5];
		biomon = new Image[10]; 
		misc = new Image[8]; 
		
		String imageName = "";
		for (int i = 1; i <= 12; i++) // load into char1 array
		{
			imageName = "sprites/char1/1-" + i + ".png"; 
			char1Sprites[i - 1] = getMyImage(imageName); 
		}
		for (int j = 1; j <= 12; j++) // load into char2 array
		{
			imageName = "sprites/char2/2-" + j + ".png"; 
			char2Sprites[j - 1] = getMyImage(imageName); 
		}
		for (int k = 0; k <= 9; k++) // load into background array
		{
			imageName = "sprites/backgrounds/B_" + k + ".png";
			backgrounds[k] = getMyImage(imageName); 
		}
		for (int l = 0; l <= 4; l++)
		{
			imageName = "sprites/animation/A-" + l + ".png";
			animation[l] = getMyImage(imageName); 
		}
		for (int m = 1; m <= 10; m++)
		{
			imageName = "sprites/biomon/C-" + m + ".png";
			biomon[m - 1] = getMyImage(imageName); 
		}
		for (int n = 0; n < 8; n++)
		{
			imageName = "sprites/misc/M-" + n + ".png";
			misc[n] = getMyImage(imageName); 
		}
	}
	
	public Image getMyImage(String pictName) // default image-IO
	{
		Image picture = null;
		try
		{
			picture = ImageIO.read(new File(pictName));
		}    
		catch (IOException e)
		{
			System.err.println("\n\n" + pictName + " can't be found.\n\n");
			e.printStackTrace();
		}
		return picture;
	}
	
	// returns images depending on what character has been selected (in Information)
	public Image[] getImages(int spriteNum)
	{
		if (spriteNum == 1)
			return char1Sprites; 
		else
			return char2Sprites; 
	}
	
	// returns all of the background images
	public Image[] getBackgrounds()
	{
		return backgrounds; 
	}
	
	// returns all of the biomon images
	public Image[] getBiomon()
	{
		return biomon; 
	}
	// returns all of the ripple animation images
	public Image[] getAnimations()
	{
		return animation; 
	}
	
	// return all of the misc. image
	public Image[] getMisc()
	{
		return misc; 
	}
}

// this is the class that makes all of the Scanners/PrintWriters for this file;
// it uses the normal File I/O we learned in class; all PrintWriters are made to 
// append the file
class FileReader 
{
	private Scanner input; // basic Scanner to be returned
	private PrintWriter output; // basic PrintWriter to be returned
	public FileReader()
	{
		input = null; 
		output = null;
	}
	
	// making Scanner based on input file
	public Scanner readFile(String inFileName)
	{
		File inFile = new File(inFileName);
		try
		{
			input = new Scanner(inFile); 
		}
		catch(FileNotFoundException e)
		{
			System.err.printf("\n\nERROR: Cannot find/open file %s.%n%n", inFileName);
			System.exit(1);
		}
		return input; 
	}

	// making PrintWriter based on export file
	public PrintWriter printFile(String outFileName)
	{
		File outFile = new File(outFileName);
		try
		{
			output = new PrintWriter(new FileWriter(outFileName, true));
		}
		catch(IOException e)
		{
			System.err.print("\n\nERROR: Cannot find/open file " + outFileName + ".\n\n");
			System.exit(2);
		}
		return output; 
	}
}

// contains getter-setter methods that can be used to check what the user
// has done
class Information
{
	private boolean logged; // checks if user has entered their username
	private String name; // stores username
	private int charSelected; // selected sprite
	private String ifHint; // if hints show up
	private int level; // what challenge the user is on
	private boolean quizAnswered; // if user has answered quiz (if character can move)
	private boolean ifTimed; // if user is playing timed mode
	private int limit; // seconds limit for timed mode
	public Information()
	{
		name = ""; 
		ifHint = "ON"; // on by default (needs to be toggled off by user)
        level = 1; // 1 by default
        limit = 30; // 30 seconds 
		quizAnswered = true; 
	}
	public void setLogged(boolean loggedIn)
	{
		logged = loggedIn; 
	}
	public boolean getLogged()
	{
		return logged; 
	}
	public void setName(String nameIn)
	{
		name = nameIn; 
	}
	public String getName()
	{
		return name; 
	}
	public void setChar(int charIn)
	{
		charSelected = charIn; 
	}
	public int getChar()
	{
		return charSelected; 
	}
	public void setHints(String selection)
	{
		ifHint = selection; 
	}
	public String getHints()
	{
		return ifHint; 
	}
	public void setChallenge(int levelIn)
	{
		level = levelIn; 
	}
	public int getChallenge()
	{
		return level; 
	}
	public void setAnswered(boolean quizAnsweredIn)
	{
		quizAnswered = quizAnsweredIn;
	}
	public boolean getAnswered()
	{
		return quizAnswered;
	}
	public void setTimed(boolean ifTimedIn)
	{
		ifTimed = ifTimedIn; 
		if (ifTimed)
			setChallenge(1); // timed mode starts new challenge
	}
	public boolean ifTimed()
	{
		return ifTimed;
	}
	public void setLimit(int limitIn)
	{
		limit = limitIn; 
	}
	public int getLimit()
	{
		return limit; 
	}
}
