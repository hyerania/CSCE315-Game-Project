/**
 * Created by Karl on 3/9/2017.
 */
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;
import java.lang.Math.*;
import static java.lang.System.*;

public class Main {
    public static final int ROWS = 2;
    public static int COLUMNS;    //actual one to use
   //public static final int COLUMNS = 7;//for testing purposes
    //public static int[][]board = new int[ROWS][COLUMNS];  //for testing purposes
    public static int [][]board;      //actual one to use
    public static int computerEndHouse;
    public static int userEndHouse;
    public static boolean computersTurn = false;
    public static boolean goAgain = false;


    public static void main(String[] args) {
       // populateBoard(2, false);      //for testing purposes

        Home h = new Home();        //Makes home screen of game
    }
    static void populateBoard(int numSeeds, boolean random)     //correctly populates board
    {
        if(!random)
        {
            for(int r = 0; r < ROWS; r++)
            {
                for(int c = 0; c < COLUMNS; c++)
                {
                    board[r][c] = numSeeds;
                }
            }
        }
        else
        {
            Random ran = new Random();
            int sumOfAllSeeds = numSeeds * COLUMNS;
            //figure out how to decide what goes in each spot
            //maybe use the first row to put into the second row.
            for(int r = 0; r < ROWS; r++)
            {
                for(int c = 0; c < COLUMNS; c++)
                {
                    if(r == 0)
                    {
                       int seedVal =  ran.nextInt(sumOfAllSeeds);
                       board[r][c] = seedVal;
                       sumOfAllSeeds -= seedVal;
                    }
                    else
                    {
                        board[r][c] = board[r-1][COLUMNS-c-1];
                    }

                }
            }
        }


    }
}

class Home extends JFrame
{
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private JFrame mainFrame = new JFrame("Mancala Board");
    private JPanel controlPanel;

    JButton userPlayers = new JButton("Two-User Player");
    JButton computerPlayer = new JButton("Single-User Player");
    JButton helpMenu = new JButton("Help Menu");
    JButton exitGame = new JButton("Exit Game");

    JLabel status1 = new JLabel("");
    JLabel status2 = new JLabel("");

    JLabel userPlayer1Label = new JLabel("Player 1 Name: ", 4);
    JLabel userPlayer2Label = new JLabel("Player 2 Name: ", 4);
    JTextField userPlayer1 = new JTextField(10);
    JTextField userPlayer2 = new JTextField(10);

    public Home(){
        mainFrame.setSize(1200, 700);
        mainFrame.setLayout(new GridLayout(0, 2));
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setVisible(true);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(userPlayers);
        mainFrame.add(computerPlayer);
        mainFrame.add(helpMenu);
        mainFrame.add(exitGame);
        mainFrame.add(controlPanel);

        controlPanel.add(userPlayer1Label);
        controlPanel.add(userPlayer1);
        controlPanel.add(userPlayer2Label);
        controlPanel.add(userPlayer2);
        userPlayers.addActionListener(new userPlayersAction());
        computerPlayer.addActionListener(new computerPlayersAction());
        helpMenu.addActionListener(new helpMenuAction());
        exitGame.addActionListener(new exitGameAction());
    }

    class userPlayersAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String player1 = userPlayer1.getText();
            String player2 = userPlayer2.getText();
            userPlayer1.setText("");
            userPlayer2.setText("");
            mainFrame.setVisible(false);
            /*
            Get the number of seeds, if it is a random distribution, and how many houses there should be.(columns)
            set Main.COLUMNS to how many houses they want. (Must be at least 4 and at most 9)
            Main.board = new int[Main.ROWS][Main.COLUMNS];
            also put this ^ before the populate.
            then populate the board
            Main.populateBoard(int numOfSeeds, boolean isRandom);
             */

            //for testing purposes~~~~~~~~~~~
            Main.COLUMNS = 5;
            Main.board = new int [Main.ROWS][Main.COLUMNS];
            Main.populateBoard(4, false);
            out.println(Main.COLUMNS);
            Game g = new Game();
        }
    }

    class computerPlayersAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String player1 = userPlayer1.getText();
            userPlayer1.setText("");
            mainFrame.setVisible(false);

            /*
            Get the number of seeds, if it is a random distribution, and how many houses there should be.(columns)
            set Main.COLUMNS to how many houses they want. (Must be at least 4 and at most 9)
            Main.board = new int[Main.ROWS][Main.COLUMNS];
            also put this ^ before the populate.
            then populate the board
            Main.populateBoard(int numOfSeeds, boolean isRandom);
             */

            //for testing purposes~~~~~~~~~~~
            Main.COLUMNS = 5;
            Main.board = new int [Main.ROWS][Main.COLUMNS];
            Main.populateBoard(4, false);       //change to true if you want to be random numbers in the houses
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            Game g = new Game();
        }
    }
    class helpMenuAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            HelpMenu help = new HelpMenu();
        }
    }
    class exitGameAction implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

}

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

    static String content = "Instructions for Game:\n";
}

class Game extends JFrame
{
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    //buttons for the game
    JButton userHouse1;
    JButton userHouse2;
    JButton userHouse3;
    JButton userHouse4;
    JButton userHouse5;
    JButton userHouse6;
    JButton userHouse7;
    JButton userHouse8;
    JButton userHouse9;

    JLabel userSeedNumber1;
    JLabel userSeedNumber2;
    JLabel userSeedNumber3;
    JLabel userSeedNumber4;
    JLabel userSeedNumber5;
    JLabel userSeedNumber6;
    JLabel userSeedNumber7;
    JLabel userSeedNumber8;
    JLabel userSeedNumber9;


    JLabel computerHouse1;
    JLabel computerHouse2;
    JLabel computerHouse3;
    JLabel computerHouse4;
    JLabel computerHouse5;
    JLabel computerHouse6;
    JLabel computerHouse7;
    JLabel computerHouse8;
    JLabel computerHouse9;

    JLabel computerSeedNumber1;
    JLabel computerSeedNumber2;
    JLabel computerSeedNumber3;
    JLabel computerSeedNumber4;
    JLabel computerSeedNumber5;
    JLabel computerSeedNumber6;
    JLabel computerSeedNumber7;
    JLabel computerSeedNumber8;
    JLabel computerSeedNumber9;

    JLabel userEndHouseLabel;
    JLabel userEndHouseNum;
    JLabel computerEndHouseLabel;
    JLabel computerEndHouseNum;

    JLabel empty1;
    JLabel empty2;
    JLabel empty3;
    JLabel empty4;


    public Game()
    {
        setTitle("Mancala Board");

        //instantiate buttons
        userHouse1 = new JButton("House 1");
        userHouse2 = new JButton("House 2");
        userHouse3 = new JButton("House 3");
        userHouse4 = new JButton("House 4");
        userHouse5 = new JButton("House 5");
        userHouse6 = new JButton("House 6");
        //need to make the button actions for these buttons.~~~~~~~~~~~~~~~~~~~~~
        userHouse7 = new JButton("House 7");
        userHouse8 = new JButton("House 8");
        userHouse9 = new JButton("House 9");

        userSeedNumber1 = new JLabel("" + Main.board[1][0], 0 );
        userSeedNumber1.setVerticalTextPosition(JLabel.CENTER);
        userSeedNumber1.setHorizontalTextPosition(JLabel.CENTER);
        userSeedNumber2 = new JLabel("" + Main.board[1][1], 0 );
        userSeedNumber2.setVerticalTextPosition(JLabel.CENTER);
        userSeedNumber2.setHorizontalTextPosition(JLabel.CENTER);
        userSeedNumber3 = new JLabel("" + Main.board[1][2], 0 );
        userSeedNumber3.setVerticalTextPosition(JLabel.CENTER);
        userSeedNumber3.setHorizontalTextPosition(JLabel.CENTER);
        userSeedNumber4 = new JLabel("" + Main.board[1][3], 0 );
        userSeedNumber4.setVerticalTextPosition(JLabel.CENTER);
        userSeedNumber4.setHorizontalTextPosition(JLabel.CENTER);
        //makes sure that the board is that large
        if(Main.COLUMNS > 4)
        {
            userSeedNumber5 = new JLabel("" + Main.board[1][4], 0 );
            userSeedNumber5.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber5.setHorizontalTextPosition(JLabel.CENTER);
        }
        if(Main.COLUMNS > 5)
        {
            userSeedNumber6 = new JLabel("" + Main.board[1][5], 0 );
            userSeedNumber6.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber6.setHorizontalTextPosition(JLabel.CENTER);
        }
        if(Main.COLUMNS > 6)
        {
            userSeedNumber7 = new JLabel("" + Main.board[1][6], 0 );
            userSeedNumber7.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber7.setHorizontalTextPosition(JLabel.CENTER);
        }
        if(Main.COLUMNS>7)
        {
            userSeedNumber8 = new JLabel("" + Main.board[1][7], 0 );
            userSeedNumber8.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber8.setHorizontalTextPosition(JLabel.CENTER);
        }
        if(Main.COLUMNS>8)
        {
            userSeedNumber9 = new JLabel("" + Main.board[1][8], 0 );
            userSeedNumber9.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber9.setHorizontalTextPosition(JLabel.CENTER);
        }


        computerHouse1 = new JLabel("House 1", 0);
        computerHouse1.setVerticalTextPosition(JLabel.CENTER);
        computerHouse1.setHorizontalTextPosition(JLabel.CENTER);
        computerHouse2 = new JLabel("House 2", 0);
        computerHouse2.setVerticalTextPosition(JLabel.CENTER);
        computerHouse2.setHorizontalTextPosition(JLabel.CENTER);
        computerHouse3 = new JLabel("House 3", 0);
        computerHouse3.setVerticalTextPosition(JLabel.CENTER);
        computerHouse3.setHorizontalTextPosition(JLabel.CENTER);
        computerHouse4 = new JLabel("House 4", 0);
        computerHouse4.setVerticalTextPosition(JLabel.CENTER);
        computerHouse4.setHorizontalTextPosition(JLabel.CENTER);
        computerHouse5 = new JLabel("House 5", 0);
        computerHouse5.setVerticalTextPosition(JLabel.CENTER);
        computerHouse5.setHorizontalTextPosition(JLabel.CENTER);
        computerHouse6 = new JLabel("House 6", 0);
        computerHouse6.setVerticalTextPosition(JLabel.CENTER);
        computerHouse6.setHorizontalTextPosition(JLabel.CENTER);
        computerHouse7 = new JLabel("House 7", 0);
        computerHouse7.setVerticalTextPosition(JLabel.CENTER);
        computerHouse7.setHorizontalTextPosition(JLabel.CENTER);
        computerHouse8 = new JLabel("House 8", 0);
        computerHouse8.setVerticalTextPosition(JLabel.CENTER);
        computerHouse8.setHorizontalTextPosition(JLabel.CENTER);
        computerHouse9 = new JLabel("House 9", 0);
        computerHouse9.setVerticalTextPosition(JLabel.CENTER);
        computerHouse9.setHorizontalTextPosition(JLabel.CENTER);

        computerSeedNumber1 = new JLabel("" + Main.board[0][0], 0 );
        computerSeedNumber1.setVerticalTextPosition(JLabel.CENTER);
        computerSeedNumber1.setHorizontalTextPosition(JLabel.CENTER);
        computerSeedNumber2 = new JLabel("" + Main.board[0][1], 0);
        computerSeedNumber2.setVerticalTextPosition(JLabel.CENTER);
        computerSeedNumber2.setHorizontalTextPosition(JLabel.CENTER);
        computerSeedNumber3 = new JLabel("" + Main.board[0][2], 0);
        computerSeedNumber3.setVerticalTextPosition(JLabel.CENTER);
        computerSeedNumber3.setHorizontalTextPosition(JLabel.CENTER);
        computerSeedNumber4 = new JLabel("" + Main.board[0][3], 0);
        computerSeedNumber4.setVerticalTextPosition(JLabel.CENTER);
        computerSeedNumber4.setHorizontalTextPosition(JLabel.CENTER);
        if(Main.COLUMNS>4)
        {
            computerSeedNumber5 = new JLabel("" + Main.board[0][4], 0);
            computerSeedNumber5.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber5.setHorizontalTextPosition(JLabel.CENTER);
        }
        if(Main.COLUMNS>5)
        {
            computerSeedNumber6 = new JLabel("" + Main.board[0][5], 0);
            computerSeedNumber6.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber6.setHorizontalTextPosition(JLabel.CENTER);
        }
        if(Main.COLUMNS>6)
        {
            computerSeedNumber7 = new JLabel("" + Main.board[0][6], 0);
            computerSeedNumber7.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber7.setHorizontalTextPosition(JLabel.CENTER);
        }
        if(Main.COLUMNS>7)
        {
            computerSeedNumber8 = new JLabel("" + Main.board[0][7], 0);
            computerSeedNumber8.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber8.setHorizontalTextPosition(JLabel.CENTER);
        }
        if(Main.COLUMNS>8)
        {
            computerSeedNumber9 = new JLabel("" + Main.board[0][8], 0);
            computerSeedNumber9.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber9.setHorizontalTextPosition(JLabel.CENTER);
        }


        userEndHouseLabel = new JLabel("User End House" , 0);
        userEndHouseLabel.setVerticalTextPosition(JLabel.CENTER);
        userEndHouseLabel.setHorizontalTextPosition(JLabel.CENTER);
        userEndHouseNum = new JLabel("" + Main.userEndHouse , 0);
        userEndHouseNum.setVerticalTextPosition(JLabel.CENTER);
        userEndHouseNum.setHorizontalTextPosition(JLabel.CENTER);

        computerEndHouseLabel = new JLabel("Computer End House" , 0);
        computerEndHouseLabel.setVerticalTextPosition(JLabel.CENTER);
        computerEndHouseLabel.setHorizontalTextPosition(JLabel.CENTER);
        computerEndHouseNum = new JLabel("" + Main.computerEndHouse , 0);
        computerEndHouseNum.setVerticalTextPosition(JLabel.CENTER);
        computerEndHouseNum.setHorizontalTextPosition(JLabel.CENTER);

        empty1 = new JLabel(" " );
        empty1.setVerticalTextPosition(JLabel.CENTER);
        empty1.setHorizontalTextPosition(JLabel.CENTER);
        empty2 = new JLabel(" " );
        empty2.setVerticalTextPosition(JLabel.CENTER);
        empty2.setHorizontalTextPosition(JLabel.CENTER);
        empty3 = new JLabel(" " );
        empty3.setVerticalTextPosition(JLabel.CENTER);
        empty3.setHorizontalTextPosition(JLabel.CENTER);
        empty4 = new JLabel(" " );
        empty4.setVerticalTextPosition(JLabel.CENTER);
        empty4.setHorizontalTextPosition(JLabel.CENTER);



        if(Main.COLUMNS>4)
        {

        }


        add (empty1);
        add (computerHouse1);
        add (computerHouse2);
        add (computerHouse3);
        add (computerHouse4);
        if(Main.COLUMNS>4)
        {
            add (computerHouse5);
        }
        if(Main.COLUMNS>5)
        {
            add (computerHouse6);
        }
        if(Main.COLUMNS>6)
        {
            add(computerHouse7);
        }
        if(Main.COLUMNS>7)
        {
            add(computerHouse8);
        }
        if(Main.COLUMNS>8)
        {
            add(computerHouse9);
        }

        add (empty2);

        add (computerEndHouseLabel);
        add (computerSeedNumber1);
        add (computerSeedNumber2);
        add (computerSeedNumber3);
        add (computerSeedNumber4);
        if(Main.COLUMNS>4)
        {
            add (computerSeedNumber5);
        }
        if(Main.COLUMNS>5)
        {
            add (computerSeedNumber6);
        }
        if(Main.COLUMNS>6)
        {
            add(computerSeedNumber7);
        }
        if(Main.COLUMNS>7)
        {
            add(computerSeedNumber8);
        }
        if(Main.COLUMNS>8)
        {
            add(computerSeedNumber9);
        }
        add (userEndHouseLabel);

        add (computerEndHouseNum);
        add (userSeedNumber1);
        add (userSeedNumber2);
        add (userSeedNumber3);
        add (userSeedNumber4);
        if(Main.COLUMNS>4)
        {
            add (userSeedNumber5);
        }
        if(Main.COLUMNS>5)
        {
            add (userSeedNumber6);
        }
        if(Main.COLUMNS>6)
        {
            add(userSeedNumber7);
        }
        if(Main.COLUMNS>7)
        {
            add(userSeedNumber8);
        }
        if(Main.COLUMNS>8)
        {
            add(userSeedNumber9);
        }
        add ( userEndHouseNum);

        add (empty3);
        add (userHouse1);
        add (userHouse2);
        add (userHouse3);
        add (userHouse4);
        if(Main.COLUMNS>4)
        {
            add (userHouse5);
        }
        if(Main.COLUMNS>5)
        {
            add (userHouse6);
        }
        if(Main.COLUMNS>6)
        {
            add(userHouse7);
        }
        if(Main.COLUMNS>7)
        {
            add(userHouse8);
        }
        if(Main.COLUMNS>8)
        {
            add(userHouse9);
        }
        add (empty4);

        userHouse1.addActionListener(new UserHouse1Action ());
        userHouse2.addActionListener(new UserHouse2Action ());
        userHouse3.addActionListener(new UserHouse3Action ());
        userHouse4.addActionListener(new UserHouse4Action ());
        if(Main.COLUMNS>4)
        {
            userHouse5.addActionListener(new UserHouse5Action ());
        }
        if(Main.COLUMNS>5)
        {
            userHouse6.addActionListener(new UserHouse6Action ());
        }
        if(Main.COLUMNS>6)
        {
            userHouse7.addActionListener(new UserHouse7Action ());
        }
        if(Main.COLUMNS>7)
        {
            userHouse8.addActionListener(new UserHouse8Action ());
        }
        if(Main.COLUMNS>8)
        {
            userHouse9.addActionListener(new UserHouse9Action ());
        }



        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, Main.COLUMNS + 2));


        setSize(WIDTH, HEIGHT);
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    boolean isGameOver()
    {
        int countComputer = 0;
        int countUser = 0;
        for(int x=0; x< Main.COLUMNS; x++)
        {
            countComputer += Main.board[0][x];
            countUser += Main.board[1][x];
        }
        return ((countComputer == 0) || (countUser == 0));
    }

    void movingSeeds(int seeds, int r, int c)
    {
        //int numSeeds = seeds;

        for(int x=0; x < seeds; x++)
        {
            if(c >= Main.COLUMNS || c < 0)
            {
                //this makes sure that seeds are not added to the end zone of the other player.
                if(r==1)
                {
                    r--;
                    c = Main.COLUMNS - 1;
                    if(!Main.computersTurn)     //if it is users turn then add to their end zone.
                    {
                        Main.userEndHouse++;
                        if(x+1 >= seeds)        //if that is the last seed then go again
                        {
                            Main.goAgain = true;
                        }
                    }
                    continue;
                }
                else
                {
                    r++;
                    c = 0;
                    if(Main.computersTurn)      //if it is computers turn then add to its end zone
                    {
                        Main.computerEndHouse++;    //will need to do the go again thing for the computer
                        if(x+1 >= seeds)
                        {
                            Main.goAgain = true;
                        }

                    }

                    continue;
                }
            }
            //if this is the last seed and the place where it is landing is empty
            //Then take all of the seeds in the matching house only if the empty house is on your side.
            if((x+1 >= seeds) && (Main.board[r][c] == 0))
            {
                if(r == 1 && !(Main.computersTurn))
                {
                    Main.userEndHouse += Main.board[r-1][c];
                    Main.board[r-1][c] = 0;
                }
                else
                if(r == 0 && Main.computersTurn)
                {
                    Main.computerEndHouse += Main.board[r+1][c];
                    Main.board[r+1][c] = 0;
                }

            }

            Main.board[r][c]++;


            if(r == 1)
            {
                c++;
            }
            else
            {
                c--;
            }
        }

        //if the game is over then it quits the program and prints out the results
        //in the future probably want to do this in GUI as well.
        if(isGameOver())
        {
            setVisible(false);
            findWinner();
            exit(0);
        }

    }

    void findWinner()
    {
        int totalUserSeeds = Main.userEndHouse;
        int totalComputerSeeds = Main.computerEndHouse;

        //adds all of the seeds remaining in the houses after the game has ended.
        for(int r = 0; r < Main.ROWS; r++)
        {
            for(int c = 0; c < Main.COLUMNS; c++)
            {
                if(r == 0)
                {
                    totalComputerSeeds += Main.board[r][c];
                }
                else
                {
                    totalUserSeeds += Main.board[r][c];
                }
            }
        }

        //Decide winner then print out
        if(totalUserSeeds > totalComputerSeeds)
        {
            out.println("Congratulations you beat the computer with a score of " + totalUserSeeds + " to " + totalComputerSeeds + ".");
        }
        else
        if(totalComputerSeeds > totalUserSeeds)
        {
            out.println("Sorry you lost to the computer with a score of " + totalComputerSeeds + " to " + totalUserSeeds + ".");
        }
        else
        {
            out.println("You have tied the computer.");
        }

    }

    void pieRule()
    {
        //switches the end houses
        int temp = Main.userEndHouse;
        Main.userEndHouse = Main.computerEndHouse;
        Main.computerEndHouse = temp;

        //gets original board
        int[][] origBoard = new int[Main.ROWS][Main.COLUMNS];
        for(int r = 0; r < Main.ROWS; r++)
        {

            for (int c = 0; c < Main.COLUMNS; c++)
            {
                origBoard[r][c] = Main.board[r][c];
            }
        }

        //transposing board
        for(int r = 0; r < Main.ROWS; r++)
        {
            for(int c = 0; c < Main.COLUMNS; c++)
            {
                Main.board[r][c] = origBoard[Main.ROWS-1-r][Main.COLUMNS-1-c];
            }
        }

        //so that the user goes again
        Main.goAgain = true;
    }

    class UserHouse1Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            //So that buttons can't be clicked when it is the computers turn and when that house is empty.
            if(!Main.computersTurn && !(Main.board[1][0] == 0))
            {



                int numSeeds = Main.board[1][0];
                Main.board[1][0] = 0;
                int r = 1;
                int c = 1;
                movingSeeds(numSeeds, r, c);

//this calls the pie rule function is the yes button is pressed.

            //    int reply = JOptionPane.showConfirmDialog(null, "Do you want to do the pie rule?", "Pie Rule", JOptionPane.YES_NO_OPTION);
             //   if (reply == JOptionPane.YES_OPTION) {
              //      pieRule();
               // }



                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }



        }
    }
    class UserHouse2Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(!Main.computersTurn && !(Main.board[1][1] == 0))
            {
                int numSeeds = Main.board[1][1];
                Main.board[1][1] = 0;
                int r = 1;
                int c = 2;
                movingSeeds(numSeeds, r, c);


                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }

        }
    }
    class UserHouse3Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(!Main.computersTurn && !(Main.board[1][2] == 0))
            {
                int numSeeds = Main.board[1][2];
                Main.board[1][2] = 0;
                int r = 1;
                int c = 3;
                movingSeeds(numSeeds, r, c);

                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }

        }
    }
    class UserHouse4Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(!Main.computersTurn && !(Main.board[1][3] == 0))
            {
                int numSeeds = Main.board[1][3];
                Main.board[1][3] = 0;
                int r = 1;
                int c = 4;
                movingSeeds(numSeeds, r, c);

                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }



        }
    }
    class UserHouse5Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(!Main.computersTurn && !(Main.board[1][4] == 0))
            {
                int numSeeds = Main.board[1][4];
                Main.board[1][4] = 0;
                int r = 1;
                int c = 5;
                movingSeeds(numSeeds, r, c);

                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }

        }
    }

    class UserHouse6Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(!Main.computersTurn && !(Main.board[1][5] == 0))
            {
                int numSeeds = Main.board[1][5];
                Main.board[1][5] = 0;
                int r = 1;
                int c = 6;
                movingSeeds(numSeeds, r, c);

                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }

        }
    }
    class UserHouse7Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(!Main.computersTurn && !(Main.board[1][6] == 0))
            {
                int numSeeds = Main.board[1][6];
                Main.board[1][6] = 0;
                int r = 1;
                int c = 7;
                movingSeeds(numSeeds, r, c);

                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }

        }
    }
    class UserHouse8Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(!Main.computersTurn && !(Main.board[1][7] == 0))
            {
                int numSeeds = Main.board[1][7];
                Main.board[1][7] = 0;
                int r = 1;
                int c = 8;
                movingSeeds(numSeeds, r, c);

                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }

        }
    }
    class UserHouse9Action implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(!Main.computersTurn && !(Main.board[1][8] == 0))
            {
                int numSeeds = Main.board[1][8];
                Main.board[1][8] = 0;
                int r = 1;
                int c = 9;
                movingSeeds(numSeeds, r, c);

                if(!Main.goAgain)
                {
                    Main.computersTurn = true;
                    computerTurn();
                }
                Main.goAgain = false;

                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                //not a very good way to reload the frame. Look at example code more.
                setVisible(false);
                Game g = new Game();
                //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            }

        }
    }

     int psuedoMovingSeeds(int seeds, int r, int c)
    {
        int extra = 0;
        int[][] psuedoBoard = new int[Main.ROWS][Main.COLUMNS];
        for(int i = 0; i < Main.ROWS; i++)
        {

            for (int j = 0; j < Main.COLUMNS; j++)
            {
                psuedoBoard[i][j] = Main.board[i][j];
            }
        }
        int col= Main.COLUMNS;
        int row=Main.ROWS;
        int userEndHouse= Main.userEndHouse;
        boolean comp= Main.computersTurn;
        boolean go=Main.goAgain;
        int compEndHouse=Main.computerEndHouse;
        for(int x=0; x < seeds; x++)
        {
            if(c >= col || c < 0)
            {
                //this makes sure that seeds are not added to the end zone of the other player.
                if(r==1)
                {
                    r--;
                    c = col - 1;
                    if(!comp)     //if it is users turn then add to their end zone.
                    {
                        userEndHouse++;
                        if(x+1 >= seeds)        //if that is the last seed then go again
                        {
                            go = true;
                        }
                    }
                    continue;
                }
                else
                {
                    r++;
                    c = 0;
                    if(comp)      //if it is computers turn then add to its end zone
                    {
                        compEndHouse++;    //will need to do the go again thing for the computer
                        if(x+1 >= seeds)
                        {
                            go = true;
                        }

                    }

                    continue;
                }
            }
            //if this is the last seed and the place where it is landing is empty
            //Then take all of the seeds in the matching house only if the empty house is on your side.
            if((x+1 >= seeds) && (psuedoBoard[r][c] == 0))
            {
                if(r == 1 && !(comp))
                {
                    userEndHouse += psuedoBoard[r-1][c];
                    psuedoBoard[r-1][c] = 0;
                }
                else
                if(r == 0 && comp)
                {
                    compEndHouse += psuedoBoard[r+1][c];
                    psuedoBoard[r+1][c] = 0;
                }

            }

            psuedoBoard[r][c]++;

            if(r == 1)
            {
                c++;
            }
            else
            {
                c--;
            }
        }
        //if negative then User is winning if postive then computer winning
        if(go == true)
        {
            extra = 1;
        }
        //out.println(compEndHouse-userEndHouse + extra);
        return compEndHouse-userEndHouse + extra;
        
    }

    
   public GameObject minimax(GameObject go,int alpha,int beta,  int depth, boolean maxTree)
    {   //returns the column that should be executed
        if(depth == 0 || go.column == 6){
            //return value;
            return go;
        }
        //check if Game Over
        if(maxTree){
            // if it is the computer does the minimax tree with all the potential moves
            ArrayList<GameObject>moves= new ArrayList<GameObject>();

            int bestValue = Integer.MIN_VALUE;  
            int bestCol=0;

            for(int i=0; i<Main.COLUMNS; i++)
            {
                if(Main.board[0][i] != 0)
                {
                    int value = psuedoMovingSeeds(Main.board[0][i],0,i-1);
                    GameObject node = new GameObject(i, value);
                    GameObject abc= minimax(node,alpha,beta, depth-1, false);
                    bestValue= Math.max(bestValue, abc.value);
                    alpha=Math.max(alpha,bestValue);
                    if(bestValue==abc.value)
                    {
                        bestCol=abc.column;
                    }
                    if(beta <= alpha)
                    {
                        break;
                    }
                }

                // GameObject node= new GameObject();
                //return moves.get(moves.size()-1);
            }
            GameObject returnObj = new GameObject(bestCol,bestValue);        
            return returnObj;
        }
        else 
        {
           // int moves[]= new int[6];
            int bestValue = Integer.MAX_VALUE;
            int bestCol=0;
            for(int i=0; i<Main.COLUMNS; i++)
            {
                if(Main.board[0][i] != 0)
                {
                    int value = psuedoMovingSeeds(Main.board[0][i],0,i-1);
                    GameObject node = new GameObject(i, value);
                    GameObject abc= minimax(node,alpha,beta,depth-1, true);
                    bestValue= Math.min(bestValue, abc.value);
                    beta=Math.min(beta,bestValue);
                    if(bestValue==abc.value)
                    {
                        bestCol=abc.column;
                    }
                    if(beta <=alpha)
                    {
                        break;
                    }
                }

                // GameObject node= new GameObject();
                //return moves.get(moves.size()-1);
            }
            GameObject returnObj = new GameObject(bestCol,bestValue);        
            return returnObj;
        }
    }
    void computerTurn()
    {
        //one problem is that this will do all the turns that the computer can do then it will show up on the GUI.
        do
        {
            Main.goAgain = false;

            GameObject go= new GameObject(0,Main.computerEndHouse, Main.userEndHouse);
            GameObject current = minimax(go,Integer.MIN_VALUE, Integer.MAX_VALUE,3, true);
           // out.println(num);
            movingSeeds(Main.board[0][current.column], 0, current.column-1);
            Main.board[0][current.column]=0;
            //Temporary testing~~~~~~~~
            //movingSeeds(Main.board[0][3], 0, 2);
            //Main.board[0][3] = 0;
            //~~~~~~~~~~~~~
        }while(Main.goAgain);       //movingSeeds will set goAgain to the correct value.

        Main.computersTurn = false;     //So that the buttons will be clickable again
    }

}
class GameObject
{
    int column = 6;
    //int[][]board = new int[ROWS][COLUMNS];    //int [row][column]
    int computerEndZone;
    int userEndZone;
    int value;
    //public static boolean computersTurn = false;
    //public static boolean goAgain = false;

    GameObject(int c,int cez, int uez)
    {
        column=c;
        computerEndZone=cez;
        userEndZone=uez;
    }
    GameObject(int c, int v)
    {
        column = c;
        value = v;
    }
}