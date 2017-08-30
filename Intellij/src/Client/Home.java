package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/*Provides the home menu of the game in order to configure the user name, have a help menu, exit, and the start of the
* game with the appropriate configurations*/
class Home extends JFrame
{
    /*Initiation of board size and title of frame*/
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private JFrame mainFrame = new JFrame("Mancala Board");

    /*Initiation of the button and labels for the main board*/
    JButton start = new JButton("Start Game");
    JButton helpMenu = new JButton("Help Menu");
    JButton exitGame = new JButton("Exit Game");

    JLabel userPlayerLabel = new JLabel("Player Name: ");
    JTextField userPlayer = new JTextField(10);
    JLabel title = new JLabel("MANCALA");

    /*Initiation for the configuration properties*/
    static BufferedReader input;
    static PrintWriter output;
    String config;
    static public String name;
    public int numHoles;
    public int numSeeds;
    public long timeLimit;
    public char isFirstPlayer;
    public char gameMode;
    public ArrayList<Integer> seedConfig = new ArrayList<Integer>();

    /*Adds the implementation of the home menu*/
    public Home(BufferedReader in, PrintWriter out, String configuration)
    {
        input = in;
        output = out;
        config = configuration;

        /*Parses through the configuration string in order to configure the board appropriately*/
        String configPass[] = config.split(" ");
        numHoles = Integer.parseInt(configPass[1]);
        numSeeds = Integer.parseInt(configPass[2]);
        timeLimit = Long.parseLong(configPass[3]);
        isFirstPlayer = configPass[4].charAt(0);
        gameMode = configPass[5].charAt(0);

        //Parses through the configuration for the random distribution for the board
        for(int x = 6; x < configPass.length; x++)
        {
            seedConfig.add(Integer.parseInt(configPass[x]));
        }

        /*Provides the main layout for board*/
        title.setFont(new Font("Sans Serif", Font.PLAIN, 75));
        title.setForeground(Color.magenta);
        mainFrame.setSize(1200, 700);
        mainFrame.getContentPane().setBackground(Color.orange);
        mainFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        /*Adds the labels to the board*/
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setVisible(true);
        mainFrame.add(title,gbc);
        mainFrame.add(userPlayerLabel,gbc);
        mainFrame.add(userPlayer,gbc);

        /*Adds the button necessary to the Home board*/
        mainFrame.add(start,gbc);
        mainFrame.add(helpMenu,gbc);
        mainFrame.add(exitGame,gbc);

        /*Adds the functionality to the board*/
        start.addActionListener(new startGame());
        helpMenu.addActionListener(new helpMenuAction());
        exitGame.addActionListener(new exitGameAction());
    }

    /*Adds the implementation of the Start Game button*/
    class startGame implements  ActionListener
    {
        public void actionPerformed(ActionEvent e){
            name = userPlayer.getText();
            User.COLUMNS = numHoles;
            User.board = new int[User.ROWS][User.COLUMNS];
            if(gameMode == 'S'){
                User.populateBoard(numSeeds, false);
            }
            else if(gameMode == 'R') {
                for(int c = 0; c < numHoles; c++)
                {
                    User.board[0][c] = seedConfig.get(seedConfig.size()-1-c);
                    User.board[1][c] = seedConfig.get(c);
                }
            }
            output.println("READY"); //Sends READY to server

            try {
                String startGame = input.readLine();
                System.out.println(startGame);

                //Once both clients are connected begin the game
                if(startGame.equals("BEGIN")){
                    if(isFirstPlayer == 'F')
                    {
                        Game g = new Game(true, true);
                    }
                    else
                    {
                        Game g = new Game (false, true);
                    }
                    mainFrame.setVisible(false);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*Adds the implementation of the Help Menu*/
    class helpMenuAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e){
            new HelpMenu();
        }
    }

    /*Ends game if client does not wish to run*/
    class exitGameAction implements ActionListener
    {
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

}

/*Adds the aspect of the HelpMenu to the GUI with a string that describes the instructions*/
class HelpMenu extends JFrame
{
    private JFrame mainHelpFrame = new JFrame("Game Help");

    public HelpMenu(){
        mainHelpFrame.setSize(500,500);
        mainHelpFrame.setVisible(true);
        JTextArea instructions = new JTextArea(content,20,20);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        mainHelpFrame.add(new JScrollPane(instructions));
    }

    static String content = "Instructions for Game:\n" +
            "\n" +
            "How to Play\n" +
            "The Mancala board is made up of two rows of six holes, or pits, each. If you don't have a Mancala board handy, substitute an empty egg carton.\n" +
            "Four pieces—marbles or stones—are placed in each of the 12 holes. The color of the pieces is irrelevant.\n" +
            "Each player has a store to the right side of the Mancala board. (Cereal bowls work well for this purpose if you're using an egg carton.)\n" +
            "The game begins with one player picking up all of the pieces in any one of the holes on his side.\n" +
            "Moving counter-clockwise, the player deposits one of the stones in each hole until the stones run out.\n" +
            "If you run into your own store, deposit one piece in it. If you run into your opponent's store, skip it.\n" +
            "If the last piece you drop is in your own store, you get a free turn.\n" +
            "If the last piece you drop is in an empty hole on your side, you capture that piece and any pieces in the hole directly opposite.\n" +
            "Always place all captured pieces in your store.\n" +
            "The game ends when all six spaces on one side of the Mancala board are empty.\n" +
            "The player who still has pieces on his side of the board when the game ends captures all of those pieces.\n" +
            "Count all the pieces in each store. The winner is the player with the most pieces." ;
}
