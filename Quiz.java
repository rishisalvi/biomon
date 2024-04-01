// Rishi Salvi
// 5/10/23
// Quiz.java
// This class makes the quiz that the user has to do to continue on with the 
// game and demonstrate their understanding of the content. Most of this file
// is taken from Mr. Deruiter's GameModuleFiles.java with some changes to make
// it more suited to my game. 

// graphics imports
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.CardLayout; 
import java.awt.BorderLayout;
import java.awt.FlowLayout;
// JComponent imports
import javax.swing.JFrame;	
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JLabel; 
import java.awt.Dimension;
// listener imports
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// File I/O imports
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

// NOTE: taken from Mr. DeRuiter's GameModuleFiles.java, with slight modifications
// (no high scores panel, only 1 button, etc.) in order to make it better for my
// game's purposes
class Quiz // makes the JFrame
{
	private int questionNum; // what challenge the user is on 
							// (what question should be displayed)
	private JFrame quizFrame; // the actual JFrame made by the class
	private Information info; // instance of Information class
	private GamePanel gp; // instance of GamePanel (for requesting focus)
	public Quiz (int questionNumIn, Information infoIn, GamePanel gpIn)
	{
		info = infoIn; 
		questionNum = questionNumIn; 
		gp = gpIn; 
	} 
    public static void main(String[] args) // main doesn't really do anything
    {
    }

	// called by GamePlay class - displays only when the user presses SPACE
	// and it is time for the quiz to show up; able to be closed without closing 
	// the entire program
	public JFrame getFrame()
	{
		quizFrame = new JFrame("Quiz"); 
        quizFrame.setLayout(new BorderLayout()); 
        QuizHolder qp = new QuizHolder(questionNum, quizFrame, info, gp);
		quizFrame.add(qp);
        quizFrame.setSize(1000, 600); 
        quizFrame.setLocation(0, 0); 
		quizFrame.setVisible(true);
		// https://chortle.ccsu.edu/java5/notes/chap56/ch56_9.html
        quizFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		return quizFrame; 
	}
}

// card layout w/ 3 cards - quiz, congratulations, or explanation; starts off
// at quiz; if user gets question right first try, it changes to the 
// congratulations panel; if they take multiple attempts, it changes to the
// explanation panel
class QuizHolder extends JPanel
{
	private JFrame quizFrame; // JFrame that the quiz is made of
	private int questionNum; // what challenge/question the user is on
	private Information info; // instance of Information class
	private GamePanel gp; // instance of GamePanel (to request focus)
    public QuizHolder(int questionNumIn, JFrame quizFrameIn, Information infoIn, GamePanel gpIn)
	{
		questionNum = questionNumIn; 
		quizFrame = quizFrameIn; 
		info = infoIn; 
		gp = gpIn; 
		GameData gd = new GameData(questionNum);
		
        CardLayout cards = new CardLayout();
        setLayout(cards);
		
        QuizPanel qp = new QuizPanel(info, cards, this, gd);
		add(qp, "Quiz");
		CongratsPanel cp = new CongratsPanel(info, cards, this, gp, quizFrame);
		add(cp, "Win");
		ExplainPanel ep = new ExplainPanel(info, gp, quizFrame, gd);
		add(ep, "Lose");
    }
}

// panel that displays the question and answers 
class QuizPanel extends JPanel
{
	private GameData data; // instance of GameData class that has all the getter/setter methods
	private Information info; // instance of Information class
	private CardLayout cards; // instance of CardLayout
	private QuizHolder qh; // what has the CardLayout applied to it

	public QuizPanel(Information infoIn, CardLayout cardsIn, QuizHolder qhIn, GameData dataIn)
	{
		info = infoIn; 
		cards = cardsIn; 
		qh = qhIn; 
		data = dataIn; 
		data.grabQuestionFromFile();
		
		setBackground(Color.BLACK);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout());

		QuestionsPanel qPanel = new QuestionsPanel(data, info, cards, qh);
		add(qPanel, BorderLayout.CENTER);
	}
}

// displays the questions & answers, has buttons for the user to select their 
// answer, shows if answer is right or not
class QuestionsPanel extends JPanel implements ActionListener
{
	private GameData data; // instance of GameData class that has the getter-setter methods
	private ButtonGroup group; // ensures that only one JRadioButton can be pressed
	private JTextArea questionArea; // displays the question
	private JRadioButton[] answer; // array of 4 radio buttons that are the potential answers
	private JButton submit; // JButton for user to submit their answer
	private Information info; // instance of Information class
	private CardLayout cards; // instance of CardLayout
	private QuizHolder qh; // what has the CardLayout applied to it
	
	public QuestionsPanel(GameData d, Information infoIn, CardLayout cardsIn, QuizHolder qhIn)
	{
		data = d; 
		info = infoIn; 
		cards = cardsIn; 
		qh = qhIn; 

		setBackground(Color.BLACK);
		setLayout(new BorderLayout(10, 10));
		Font myFont = new Font("Tahoma", Font.BOLD, 22);
		
		answer = new JRadioButton[4];
		
		// add question
		JPanel question = new JPanel();
		question.setBackground(Color.LIGHT_GRAY);
		question.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		question.setLayout(new BorderLayout());
		add(question, BorderLayout.NORTH);
		questionArea = new JTextArea(data.getQuestion(), 3, 30);
		questionArea.setFont(myFont);
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setOpaque(false);
		questionArea.setEditable(false);
		question.add(questionArea, BorderLayout.CENTER);
		
		// add answers
		JPanel answers = new JPanel();
		answers.setBackground(Color.GRAY);
		answers.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		answers.setLayout(new GridLayout(2, 2, 20, 20));
		add(answers, BorderLayout.CENTER);
		group = new ButtonGroup();
		
		// make each of the JRadioButtons
		for(int i = 0; i < answer.length; i++)
		{
			answer[i] = new JRadioButton("" + (char)(65 + i) + ". " + data.getAnswer(i));
			group.add(answer[i]);
			answer[i].setOpaque(true); 
			answer[i].setBackground(new Color(230, 230, 230)); 
			answer[i].setFont(myFont); 
			answer[i].addActionListener(this); 
			answers.add(answer[i]); 
		}
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
		add(buttonPanel, BorderLayout.SOUTH);
		
		// make the JButton for user to submit their answer
		submit = new JButton("SUBMIT");
		submit.setFont(myFont);
		submit.addActionListener(this);
		submit.setEnabled(false);
		buttonPanel.add(submit);
	}
	
	// if user clicks any of the JRadioButtons or submit button
	public void actionPerformed(ActionEvent evt) 
	{
		String command = evt.getActionCommand();
		
		if(group.getSelection() != null) // if a JRadioButton was selected
		{
			submit.setEnabled(true);
		}
		
		if(command.equals("SUBMIT")) // if the submit button was pressed
		{ 
			for(int i = 0; i < answer.length; i++)
			{
				if(answer[i].isSelected())
				{
					if(i != data.getCorrectAnswer())
					{
						data.setWrong(false);
						answer[i].setBackground(Color.RED); // answer incorrect 
															// (no need to mark correct answer)
					}
					else
					{
                        group.clearSelection();
			            for(int j = 0; j < answer.length; j++)
			            {
				            answer[j].setEnabled(false); // resets
			            }
			            submit.setEnabled(false);
						info.setAnswered(true);
						if (!data.getWrong())
							cards.show(qh, "Win");
						else
							cards.show(qh, "Lose");
					}
				}
			}
		}
	}
}

// user gets question right the first time; allows user to see explanations
// for the problems or close the quiz
class CongratsPanel extends JPanel
{
	private Information info; // instance of Information class
	private CardLayout cards; // instance of CardLayout
	private QuizHolder qh; // what has the CardLayout applied to it
	private JButton seeExplain; // in order to change card to see explainations
	private JButton close; // to close the quiz
	private JFrame quizFrame; // JFrame that actually makes up the quiz
	private GamePanel gp; // instance of GamePanel (in order to request focus)
	public CongratsPanel(Information infoIn, CardLayout cardsIn, QuizHolder qhIn, GamePanel gpIn, JFrame quizFrameIn)
	{
		info = infoIn; 
		cards = cardsIn; 
		qh = qhIn; 
		gp = gpIn; 
		quizFrame = quizFrameIn; 

		setBackground(new Color(59, 143, 127));
		setLayout(new FlowLayout(FlowLayout.CENTER, 500, 75));

		Font titleFont = new Font("Monospaced", Font.BOLD, 40); 
		Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20); 
		JLabel message1 = new JLabel("Congratulations!"); 
		message1.setFont(titleFont); 
		add(message1);
		JLabel message2 = new JLabel("You answered challenge #" + info.getChallenge() + " correctly!");	
		message2.setFont(buttonFont); 
		add(message2);
		JLabel message3 = new JLabel("Now go and catch the biomon and save the BioMon world!");	
		message3.setFont(buttonFont); 
		add(message3);

		BListener bl = new BListener(); 
		seeExplain = new JButton("Explainations");
		seeExplain.setFont(buttonFont);
		seeExplain.setPreferredSize(new Dimension(200, 50));
		seeExplain.addActionListener(bl);
		add(seeExplain);
		close = new JButton("Quit");
		close.setFont(buttonFont);
		close.addActionListener(bl);
		close.setPreferredSize(new Dimension(200, 50));
		add(close);
	}

	// changes the card if user wants to see explanations; otherwise, 
	// closes the quiz panel
	class BListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if (evt.getSource() == seeExplain)
				cards.show(qh, "Lose");
			else
			{
				gp.requestFocusInWindow(); 
				quizFrame.setVisible(false); // quiz is gone
				gp.repaint();
			}
		}
	}
}

// if user doesn't get question right immediately; displays explanations for
// why the biomon was or wasn't the correct choice; allows them to leave the
// quiz
class ExplainPanel extends JPanel
{
	private Information info; // instance of Information class
	private JButton close; // to close the quiz
	private JFrame quizFrame; // JFrame that actually makes up the quiz
	private GamePanel gp; // instance of GamePanel (in order to request focus)
	private GameData data; // instance of class with all getter/setter methods
	public ExplainPanel(Information infoIn, GamePanel gpIn, JFrame quizFrameIn, GameData dataIn)
	{
		info = infoIn; 
		gp = gpIn; 
		quizFrame = quizFrameIn; 
		data = dataIn;

		setBackground(new Color(200, 70, 60));
		setLayout(new FlowLayout(FlowLayout.CENTER, 500, 35));

		Font titleFont = new Font("Monospaced", Font.BOLD, 40); 
		Font buttonFont = new Font("TimesRoman", Font.PLAIN, 20); 
		JLabel message1 = new JLabel("You'll get it next time!"); 
		if (!data.getWrong()) // if user is changing from congrats
			message1.setText("Congratulations!");
		message1.setFont(titleFont); 
		add(message1);
		JLabel message2 = new JLabel("You eventually answered challenge #" + info.getChallenge() + " correctly!");	
		if (!data.getWrong()) // if user is changing from congrats
			message2.setText("You answered challenge #" + info.getChallenge() + " correctly!");	
		message2.setFont(buttonFont); 
		add(message2);
		JLabel message3 = new JLabel("Please look to see why your original selected biomon was wrong.");	
		if (!data.getWrong()) // if user is changing from congrats
			message3.setText("You can clarify your answers.");	
		message3.setFont(buttonFont); 
		add(message3);

		int counter = 0; 
		int lineNum = (info.getChallenge() - 1) * 5; 
		String[] explainations = new String[4];
		JLabel[] explainLabels = new JLabel[4];
		FileReader fr = new FileReader();
		Scanner explainReader = fr.readFile("explainations.txt");
		while (explainReader.hasNext() && counter != lineNum)
		{
			explainReader.nextLine();
			counter++; 
		}
		for (int j = 0; j < 4; j++) // four labels (1 for each potential answer)
		{
			explainations[j] = explainReader.nextLine(); 
			if (explainations[j].substring(0, 2).equals("->"))
			{
				explainations[j] = "<CORRECT ANSWER> " + explainations[j].substring(2);
				explainLabels[j] = new JLabel(explainations[j]);
				explainLabels[j].setForeground(new Color(20, 115, 16));
			}
			else // in order to make correct on green (without label being null)
				explainLabels[j] = new JLabel(explainations[j]);
			if (explainations[j].length() > 90)
				explainLabels[j].setFont(new Font("TimesRoman", Font.PLAIN, 20 - (explainations[j].length() - 90)/11));
				// this is to make sure the text fits on the panel's screen 
			else 
				explainLabels[j].setFont(new Font("TimesRoman", Font.PLAIN, 20));
			add(explainLabels[j]);
		}

		BListener bl = new BListener(); 
		close = new JButton("Quit");
		close.setFont(buttonFont);
		close.addActionListener(bl);
		close.setPreferredSize(new Dimension(200, 50));
		add(close);
	}

	// closes the quiz
	class BListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			gp.requestFocusInWindow(); 
			quizFrame.setVisible(false); // quiz is gone
			gp.repaint();
		}
	}
}

// class that contains all the getter-setter methods, responsible for getting the 
// question/answers, resetting the quiz panel
class GameData
{
	private String question; // what the question is 
	private String [] answerSet; // array that holds the potential answers
	private int correctAnswer; // what position in the array the right answer is 
	private int questionNum; // what question is being displayed
	private boolean ifWrong; // if user ever clicked the wrong answer
	
	public GameData(int questionNumIn)
	{
		questionNum = questionNumIn; 
		resetAll();
	}
	
	// resets all the answer choices/questions
	public void resetAll()
	{
		answerSet = new String[4];
		question = "";
		for(int i = 0; i < answerSet.length; i++)
		{
			answerSet[i] = "";
		}
		correctAnswer = -1;
		ifWrong = false; 
	}

	// gets the questions and answers from file - copied exactly from DeRuiter
	public void grabQuestionFromFile()
	{
		Scanner inFile = null; 
		String fileName = "quiz.txt";
		File inputFile = new File(fileName); 
		try
		{
			inFile = new Scanner(inputFile);
		}
		catch (FileNotFoundException e)
		{
			System.err.printf("ERROR: Cannot open %s\n", fileName); 
			System.err.println(e);
			System.exit(1);
		}
		int questionNumber = questionNum - 1;
		int counter = 0; 	
		while(inFile.hasNext() && counter < 6*questionNumber) // getting to correct question
		{
			String line = inFile.nextLine();
			counter++; 
		}
		question = inFile.nextLine();
		counter = 0; 
		while(inFile.hasNext() && counter < 4) // loading all of the answers
		{
			answerSet[counter] = inFile.nextLine();
			counter++; 
		}
		correctAnswer = inFile.nextInt(); 
		inFile.close();
	}
	
	public String getQuestion()
	{
		return "" + questionNum + ". " + question;
	}
	
	public String getAnswer(int index)
	{
		return answerSet[index];
	}
	
	public int getCorrectAnswer()
    {
        return correctAnswer;    
    }
		
	public void setWrong(boolean ifWrongIn)
	{
		ifWrong = ifWrongIn; 
	}
	public boolean getWrong()
	{
		return ifWrong;
	}
}
