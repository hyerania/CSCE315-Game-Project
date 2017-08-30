package Client;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import static java.lang.System.*;

/*Game Board for all user players that are not AI's*/
public class User {
    /*Initialization of all features and aspects of board*/
    public static final int ROWS = 2;
    public static int COLUMNS;
    public static int [][]board;

    public static int otherPlayerEndHouse;
    public static int userEndHouse;

    public static boolean otherPlayersTurn = false;
    public static boolean goAgain = false;
    public static boolean firstPlayer = false;
    public static boolean firstMove;
    public static String move = "";

    /*Provides the appropriate board distribution based on if the game is random or not and the number of seeds provided*/
    static void populateBoard(int numSeeds, boolean random)
    {
        //Create this type of board if the configuration was not random
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

        //If the board is random then it will create a number generator and distribute them appropriatly across the board
        else
        {
            Random ran = new Random();
            int sumOfAllSeeds = numSeeds * COLUMNS;

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

class Game extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    /*Initiation of the buttons on board*/
    JButton userHouse1;
    JButton userHouse2;
    JButton userHouse3;
    JButton userHouse4;
    JButton userHouse5;
    JButton userHouse6;
    JButton userHouse7;
    JButton userHouse8;
    JButton userHouse9;

    /*Initiation of the labels on the board to display the moves*/
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
    JLabel otherPlayerEndHouseLabel;
    JLabel otherPlayerEndHouseNum;

    JButton updateBoard;
    JLabel empty2;
    JLabel empty3;
    JLabel empty4;


    /*Provide the initiation of all the houses, labels, and features of the board game to be created*/
    public Game(boolean firstPlayer, boolean flag) {
        if (flag) {
            User.otherPlayersTurn = !firstPlayer;  //other players turn if you are not the first player.
            User.firstMove = !firstPlayer;      //for pie rule(if they are the first player then this should be false.
            // Cause they should never have the option.
        }

        User.firstPlayer = firstPlayer;

        if (firstPlayer) {
            setTitle("First Player");
        } else {
            setTitle("Second Player");
        }

        /*Initiate user buttons along with labels*/
        userHouse1 = new JButton("House 1");
        userHouse2 = new JButton("House 2");
        userHouse3 = new JButton("House 3");
        userHouse4 = new JButton("House 4");
        userHouse5 = new JButton("House 5");
        userHouse6 = new JButton("House 6");
        userHouse7 = new JButton("House 7");
        userHouse8 = new JButton("House 8");
        userHouse9 = new JButton("House 9");

        userSeedNumber1 = new JLabel("" + User.board[1][0], 0);
        userSeedNumber1.setVerticalTextPosition(JLabel.CENTER);
        userSeedNumber1.setHorizontalTextPosition(JLabel.CENTER);
        userSeedNumber2 = new JLabel("" + User.board[1][1], 0);
        userSeedNumber2.setVerticalTextPosition(JLabel.CENTER);
        userSeedNumber2.setHorizontalTextPosition(JLabel.CENTER);
        userSeedNumber3 = new JLabel("" + User.board[1][2], 0);
        userSeedNumber3.setVerticalTextPosition(JLabel.CENTER);
        userSeedNumber3.setHorizontalTextPosition(JLabel.CENTER);
        userSeedNumber4 = new JLabel("" + User.board[1][3], 0);
        userSeedNumber4.setVerticalTextPosition(JLabel.CENTER);
        userSeedNumber4.setHorizontalTextPosition(JLabel.CENTER);

        if (User.COLUMNS > 4) {
            userSeedNumber5 = new JLabel("" + User.board[1][4], 0);
            userSeedNumber5.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber5.setHorizontalTextPosition(JLabel.CENTER);
        }
        if (User.COLUMNS > 5) {
            userSeedNumber6 = new JLabel("" + User.board[1][5], 0);
            userSeedNumber6.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber6.setHorizontalTextPosition(JLabel.CENTER);
        }
        if (User.COLUMNS > 6) {
            userSeedNumber7 = new JLabel("" + User.board[1][6], 0);
            userSeedNumber7.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber7.setHorizontalTextPosition(JLabel.CENTER);
        }
        if (User.COLUMNS > 7) {
            userSeedNumber8 = new JLabel("" + User.board[1][7], 0);
            userSeedNumber8.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber8.setHorizontalTextPosition(JLabel.CENTER);
        }
        if (User.COLUMNS > 8) {
            userSeedNumber9 = new JLabel("" + User.board[1][8], 0);
            userSeedNumber9.setVerticalTextPosition(JLabel.CENTER);
            userSeedNumber9.setHorizontalTextPosition(JLabel.CENTER);
        }

        /*Initiate computer houses along with labels*/
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

        computerSeedNumber1 = new JLabel("" + User.board[0][0], 0);
        computerSeedNumber1.setVerticalTextPosition(JLabel.CENTER);
        computerSeedNumber1.setHorizontalTextPosition(JLabel.CENTER);
        computerSeedNumber2 = new JLabel("" + User.board[0][1], 0);
        computerSeedNumber2.setVerticalTextPosition(JLabel.CENTER);
        computerSeedNumber2.setHorizontalTextPosition(JLabel.CENTER);
        computerSeedNumber3 = new JLabel("" + User.board[0][2], 0);
        computerSeedNumber3.setVerticalTextPosition(JLabel.CENTER);
        computerSeedNumber3.setHorizontalTextPosition(JLabel.CENTER);
        computerSeedNumber4 = new JLabel("" + User.board[0][3], 0);
        computerSeedNumber4.setVerticalTextPosition(JLabel.CENTER);
        computerSeedNumber4.setHorizontalTextPosition(JLabel.CENTER);
        if (User.COLUMNS > 4) {
            computerSeedNumber5 = new JLabel("" + User.board[0][4], 0);
            computerSeedNumber5.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber5.setHorizontalTextPosition(JLabel.CENTER);
        }
        if (User.COLUMNS > 5) {
            computerSeedNumber6 = new JLabel("" + User.board[0][5], 0);
            computerSeedNumber6.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber6.setHorizontalTextPosition(JLabel.CENTER);
        }
        if (User.COLUMNS > 6) {
            computerSeedNumber7 = new JLabel("" + User.board[0][6], 0);
            computerSeedNumber7.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber7.setHorizontalTextPosition(JLabel.CENTER);
        }
        if (User.COLUMNS > 7) {
            computerSeedNumber8 = new JLabel("" + User.board[0][7], 0);
            computerSeedNumber8.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber8.setHorizontalTextPosition(JLabel.CENTER);
        }
        if (User.COLUMNS > 8) {
            computerSeedNumber9 = new JLabel("" + User.board[0][8], 0);
            computerSeedNumber9.setVerticalTextPosition(JLabel.CENTER);
            computerSeedNumber9.setHorizontalTextPosition(JLabel.CENTER);
        }

        String name;
        if(Home.name.equals(""))
        {
            name = "User End House";
        }
        else
        {
            name=Home.name+"'s End House";
        }

        userEndHouseLabel = new JLabel(name, 0);
        userEndHouseLabel.setVerticalTextPosition(JLabel.CENTER);
        userEndHouseLabel.setHorizontalTextPosition(JLabel.CENTER);
        userEndHouseNum = new JLabel("" + User.userEndHouse, 0);
        userEndHouseNum.setVerticalTextPosition(JLabel.CENTER);
        userEndHouseNum.setHorizontalTextPosition(JLabel.CENTER);

        otherPlayerEndHouseLabel = new JLabel("Opponent End House", 0);
        otherPlayerEndHouseLabel.setVerticalTextPosition(JLabel.CENTER);
        otherPlayerEndHouseLabel.setHorizontalTextPosition(JLabel.CENTER);
        otherPlayerEndHouseNum = new JLabel("" + User.otherPlayerEndHouse, 0);
        otherPlayerEndHouseNum.setVerticalTextPosition(JLabel.CENTER);
        otherPlayerEndHouseNum.setHorizontalTextPosition(JLabel.CENTER);

        updateBoard = new JButton("Update Board");
        empty2 = new JLabel(" ");
        empty2.setVerticalTextPosition(JLabel.CENTER);
        empty2.setHorizontalTextPosition(JLabel.CENTER);
        empty3 = new JLabel(" ");
        empty3.setVerticalTextPosition(JLabel.CENTER);
        empty3.setHorizontalTextPosition(JLabel.CENTER);
        empty4 = new JLabel(" ");
        empty4.setVerticalTextPosition(JLabel.CENTER);
        empty4.setHorizontalTextPosition(JLabel.CENTER);


        add(updateBoard);
        updateBoard.addActionListener(new UpdateBoardButton());
        add(computerHouse1);
        add(computerHouse2);
        add(computerHouse3);
        add(computerHouse4);
        if (User.COLUMNS > 4) {
            add(computerHouse5);
        }
        if (User.COLUMNS > 5) {
            add(computerHouse6);
        }
        if (User.COLUMNS > 6) {
            add(computerHouse7);
        }
        if (User.COLUMNS > 7) {
            add(computerHouse8);
        }
        if (User.COLUMNS > 8) {
            add(computerHouse9);
        }

        add(empty2);

        add(otherPlayerEndHouseLabel);
        add(computerSeedNumber1);
        add(computerSeedNumber2);
        add(computerSeedNumber3);
        add(computerSeedNumber4);
        if (User.COLUMNS > 4) {
            add(computerSeedNumber5);
        }
        if (User.COLUMNS > 5) {
            add(computerSeedNumber6);
        }
        if (User.COLUMNS > 6) {
            add(computerSeedNumber7);
        }
        if (User.COLUMNS > 7) {
            add(computerSeedNumber8);
        }
        if (User.COLUMNS > 8) {
            add(computerSeedNumber9);
        }
        add(userEndHouseLabel);

        add(otherPlayerEndHouseNum);
        add(userSeedNumber1);
        add(userSeedNumber2);
        add(userSeedNumber3);
        add(userSeedNumber4);
        if (User.COLUMNS > 4) {
            add(userSeedNumber5);
        }
        if (User.COLUMNS > 5) {
            add(userSeedNumber6);
        }
        if (User.COLUMNS > 6) {
            add(userSeedNumber7);
        }
        if (User.COLUMNS > 7) {
            add(userSeedNumber8);
        }
        if (User.COLUMNS > 8) {
            add(userSeedNumber9);
        }
        add(userEndHouseNum);

        add(empty3);
        add(userHouse1);
        add(userHouse2);
        add(userHouse3);
        add(userHouse4);
        if (User.COLUMNS > 4) {
            add(userHouse5);
        }
        if (User.COLUMNS > 5) {
            add(userHouse6);
        }
        if (User.COLUMNS > 6) {
            add(userHouse7);
        }
        if (User.COLUMNS > 7) {
            add(userHouse8);
        }
        if (User.COLUMNS > 8) {
            add(userHouse9);
        }
        add(empty4);

        userHouse1.addActionListener(new UserHouse1Action());
        userHouse2.addActionListener(new UserHouse2Action());
        userHouse3.addActionListener(new UserHouse3Action());
        userHouse4.addActionListener(new UserHouse4Action());
        if (User.COLUMNS > 4) {
            userHouse5.addActionListener(new UserHouse5Action());
        }
        if (User.COLUMNS > 5) {
            userHouse6.addActionListener(new UserHouse6Action());
        }
        if (User.COLUMNS > 6) {
            userHouse7.addActionListener(new UserHouse7Action());
        }
        if (User.COLUMNS > 7) {
            userHouse8.addActionListener(new UserHouse8Action());
        }
        if (User.COLUMNS > 8) {
            userHouse9.addActionListener(new UserHouse9Action());
        }

        /*Allwo for layout of these button and houses on specific pane*/
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(4, User.COLUMNS + 2));
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /*Determine if the game is over based on the seeds and returns boolean of true if it is over or else false*/
    boolean isGameOver() {
        int countComputer = 0;
        int countUser = 0;
        for (int x = 0; x < User.COLUMNS; x++) {
            countComputer += User.board[0][x];
            countUser += User.board[1][x];
        }
        return ((countComputer == 0) || (countUser == 0));
    }

    /*Provides the movement of seeds based on the rules of the game and move made by the user by placing them app
    * appropriately on the board*/
    void movingSeeds(int seeds, int r, int c) {
        for (int x = 0; x < seeds; x++) {
            if (c >= User.COLUMNS || c < 0) {
                //this makes sure that seeds are not added to the end zone of the other player.
                if (r == 1) {
                    r--;
                    c = User.COLUMNS - 1;
                    if (!User.otherPlayersTurn) //if it is users turn then add to their end zone.
                    {
                        User.userEndHouse++;
                        if (x + 1 >= seeds) //if that is the last seed then go again
                        {
                            User.goAgain = true;
                        }
                    }
                    continue;
                } else {
                    r++;
                    c = 0;
                    if (User.otherPlayersTurn) //if it is computers turn then add to its end zone
                    {
                        User.otherPlayerEndHouse++; //will need to do the go again thing for the computer
                        if (x + 1 >= seeds) {
                            User.goAgain = true;
                        }

                    }

                    continue;
                }
            }
            //if this is the last seed and the place where it is landing is empty
            //Then take all of the seeds in the matching house only if the empty house is on your side.
            if ((x + 1 >= seeds) && (User.board[r][c] == 0)) {
                if (r == 1 && !(User.otherPlayersTurn)) {
                    User.userEndHouse += User.board[r - 1][c];
                    User.board[r - 1][c] = 0;
                    User.userEndHouse++;
                } else if (r == 0 && User.otherPlayersTurn) {
                    User.otherPlayerEndHouse += User.board[r + 1][c];
                    User.board[r + 1][c] = 0;
                    User.otherPlayerEndHouse++;
                }

            }
            else
            {
                User.board[r][c]++;
            }

            if (r == 1) {
                c++;
            } else {
                c--;
            }
        }

        if (isGameOver()) {
            User.goAgain = false;
        }

    }

    /*Configures the appropriate board if the player chooses to do pie rule*/
    void pieRule() {

        //Switches the end houses
        int temp = User.userEndHouse;
        User.userEndHouse = User.otherPlayerEndHouse;
        User.otherPlayerEndHouse = temp;

        //Receives the original board
        int[][] origBoard = new int[User.ROWS][User.COLUMNS];
        for (int r = 0; r < User.ROWS; r++) {

            for (int c = 0; c < User.COLUMNS; c++) {
                origBoard[r][c] = User.board[r][c];
            }
        }

        //Transposes the board for the proper display
        for (int r = 0; r < User.ROWS; r++) {
            for (int c = 0; c < User.COLUMNS; c++) {
                User.board[r][c] = origBoard[User.ROWS - 1 - r][User.COLUMNS - 1 - c];
            }
        }

    }

    /*Implementing the results for the otherPlayers by using pie rule or displaying the result */
    void otherPlayerTurn() {
        try {

            while (!Home.input.ready()) {
            }
            String response = Home.input.readLine();
            String allMoves[] = response.split(" ");
            int moveNum = 0;
            if (response.equals("OK"))
            {
                out.println(response);
                otherPlayerTurn();
            }
            out.println(response);

            do {
                String otherPlayerResponse = allMoves[moveNum];

                if (otherPlayerResponse.equals("P")) {
                    pieRule();
                    User.goAgain = false;
                    User.otherPlayersTurn = false;
                    break;
                }
                if (otherPlayerResponse.equals("WINNER") || otherPlayerResponse.equals("LOSER") || otherPlayerResponse.equals("TIE")) {
                    winnerByDefault(otherPlayerResponse);
                    break;
                }
                if (otherPlayerResponse.equals("ILLEGAL") || otherPlayerResponse.equals("TIME")) {
                    winnerByDefault();
                    break;
                }

                int otherPlayerMove = Integer.parseInt(otherPlayerResponse);
                User.goAgain = false;
                otherPlayerMove--;  //makes it based on starting from zero


                int amount = User.board[0][otherPlayerMove];
                User.board[0][otherPlayerMove] = 0;
                movingSeeds(amount, 0, otherPlayerMove - 1);
                moveNum ++;

                if(Home.input.ready())
                {
                    otherPlayerTurn();
                }

            } while (User.goAgain || Home.input.ready());       //movingSeeds will set goAgain to the correct value.

            User.otherPlayersTurn = false;     //So that the buttons will be clickable again

        } catch (Exception e) {
        }
        Home.output.println("OK"); //sends to the server to let it know that its turn is starting

    }

    /*Display the winner of the game*/
    void winnerByDefault() {
        try {
            String response = Home.input.readLine();
            out.println(response);
        } catch (Exception e) {
        }

        exit(0);
    }

    /*Exit the game once the winner has been decided*/
    void winnerByDefault(String response) {
        exit(0);
    }

    /*Update button allows to see the board updated once the player makes a move*/
    class UpdateBoardButton implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (User.otherPlayersTurn) {
                otherPlayerTurn();

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);

                if (User.firstMove) {
                    int reply = JOptionPane.showConfirmDialog(null, "Do you want to do the pie rule?", "Pie Rule", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        pieRule();
                        Home.output.println("P");
                        out.println("P");
                        User.otherPlayersTurn = true;
                        User.firstMove = false;

                        setVisible(false);
                        Game g2 = new Game(User.firstPlayer, false);
                        return;
                    }
                    User.firstMove = false;
                }
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse1Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            //Does not allow for the button to be clicked on when it is the computer turn and when the house is empty.
            if (!User.otherPlayersTurn) {
                int numSeeds = User.board[1][0];
                User.board[1][0] = 0;
                int r = 1;
                int c = 1;
                movingSeeds(numSeeds, r, c);

                if (!User.goAgain) {
                    User.move = User.move + 1;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 1 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse2Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!User.otherPlayersTurn) {

                int numSeeds = User.board[1][1];
                User.board[1][1] = 0;
                int r = 1;
                int c = 2;
                movingSeeds(numSeeds, r, c);


                if (!User.goAgain) {
                    User.move = User.move + 2;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 2 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse3Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!User.otherPlayersTurn) {

                int numSeeds = User.board[1][2];
                User.board[1][2] = 0;
                int r = 1;
                int c = 3;
                movingSeeds(numSeeds, r, c);


                if (!User.goAgain) {
                    User.move = User.move + 3;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 3 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse4Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!User.otherPlayersTurn) {

                int numSeeds = User.board[1][3];
                User.board[1][3] = 0;
                int r = 1;
                int c = 4;
                movingSeeds(numSeeds, r, c);


                if (!User.goAgain) {
                    User.move = User.move + 4;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 4 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse5Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!User.otherPlayersTurn) {

                int numSeeds = User.board[1][4];
                User.board[1][4] = 0;
                int r = 1;
                int c = 5;
                movingSeeds(numSeeds, r, c);


                if (!User.goAgain) {
                    User.move = User.move + 5;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 5 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse6Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!User.otherPlayersTurn) {

                int numSeeds = User.board[1][5];
                User.board[1][5] = 0;
                int r = 1;
                int c = 6;
                movingSeeds(numSeeds, r, c);


                if (!User.goAgain) {
                    User.move = User.move + 6;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 6 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse7Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!User.otherPlayersTurn) {

                int numSeeds = User.board[1][6];
                User.board[1][6] = 0;
                int r = 1;
                int c = 7;
                movingSeeds(numSeeds, r, c);


                if (!User.goAgain) {
                    User.move = User.move + 7;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 7 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse8Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!User.otherPlayersTurn) {

                int numSeeds = User.board[1][7];
                User.board[1][7] = 0;
                int r = 1;
                int c = 8;
                movingSeeds(numSeeds, r, c);


                if (!User.goAgain) {
                    User.move = User.move + 8;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 8 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }

    /*Button to pick this specific house from where the seed needs to be moved from*/
    class UserHouse9Action implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (!User.otherPlayersTurn) {

                int numSeeds = User.board[1][8];
                User.board[1][8] = 0;
                int r = 1;
                int c = 9;
                movingSeeds(numSeeds, r, c);


                if (!User.goAgain) {
                    User.move = User.move + 9;
                    out.println(User.move);
                    Home.output.println(User.move);
                    User.move = "";
                    User.otherPlayersTurn = true;
                }
                else
                {
                    User.move = User.move + 9 + " ";
                }
                User.goAgain = false;

                setVisible(false);
                Game g = new Game(User.firstPlayer, false);
            }

        }
    }
}