package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.StringJoiner;
import static java.lang.System.*;

/*Based on the configuration, creates the board accordingly, allows for the configuration of the clients,
* and allows the appropriate output of the server messages*/
public class GameServer implements Runnable{
    /*Initiation for the AI and player's turns and boards*/
    int columns, numSeeds;
    final int ROWS = 2;
    boolean player1Turn = true;
    boolean goAgain = false;
    int board[][];
    int player1EndZone;
    int player2EndZone;
    long timeLimit;
    char gameMode;

    /*Initiation of sending and receiving messages between client and server*/
    Socket socketP1;
    Socket socketP2;
    PrintWriter outputP1;
    PrintWriter outputP2;
    BufferedReader inputP1;
    BufferedReader inputP2;

    /*Creates the board for the game by calling on populateBoard based on the configuration file*/
    public GameServer(int holes, int seeds, long time, char mode)
    {
        columns = holes;
        numSeeds = seeds;
        timeLimit = time;
        gameMode = mode;
        board = new int[2][columns];

        if(gameMode == 'S'){
            populateBoard(numSeeds, false);
        }
        else if(gameMode == 'R'){
            populateBoard(numSeeds, true);
        }
    }

    /*Provides the appropriate board distribution based on if the game is random or not and the number of seeds provided*/
    void populateBoard(int numSeeds, boolean random)
    {
        //Create this type of board if the configuration was not random
        if(!random)
        {
            for(int r = 0; r < 2; r++)
            {
                for(int c = 0; c < columns; c++)
                {
                    board[r][c] = numSeeds;
                }
            }
        }
        //If the board is random then it will create a number generator and distribute them appropriatly across the board
        else
        {
            Random ran = new Random();
            int sumOfAllSeeds = numSeeds * columns;

            for(int r = 0; r < 2; r++)
            {
                for(int c = 0; c < columns; c++)
                {
                    if(r == 0)
                    {
                        int seedVal =  ran.nextInt(sumOfAllSeeds);
                        board[r][c] = seedVal;
                        sumOfAllSeeds -= seedVal;
                    }
                    else
                    {
                        board[r][c] = board[r-1][columns-c-1];
                    }
                }
            }
        }
    }

    /*Provides the movement of seeds based on the rules of the game and move made by the user by placing them app
    * appropriately on the board*/
    void movingSeeds(int seeds, int r, int c)
    {
        for(int x = 0; x < seeds; x++)
        {
            if(c >= columns || c < 0)
            {
                //Makes sure seeds added to end zone of appropriate player
                if(r == 1)
                {
                    r--;
                    c = columns - 1;
                    if(player1Turn) //If the users turn, add to the end zone
                    {
                        player1EndZone++;
                        if(x+1 >= seeds) //If last seed, goAgain
                        {
                            goAgain = true;
                        }
                    }
                    continue;
                }
                else
                {
                    r++;
                    c = 0;
                    if(!player1Turn) //If other player's turn, then add the seeds to this end zone instead
                    {
                        player2EndZone++;
                        if(x+1 >= seeds)
                        {
                            goAgain = true;
                        }
                    }
                    continue;
                }
            }

            //If last seed and it lands on an empty house, then all the seeds in the matching house should be yours
            if((x+1 >= seeds) && (board[r][c] == 0))
            {
                if(r == 1 && player1Turn)
                {
                    player1EndZone += board[r-1][c];
                    board[r-1][c] = 0;
                    player1EndZone++;
                }
                else if(r == 0 && !player1Turn)
                {
                    player2EndZone += board[r+1][c];
                    board[r+1][c] = 0;
                    player2EndZone++;
                }
            }
            else
            {
                board[r][c]++;
            }

            if(r == 1)
            {
                c++;
            }
            else
            {
                c--;
            }
        }

        //If game is over, determine and display the winner
        if(isGameOver())
        {
            findWinner();
            goAgain = false;
        }

    }

    /*Determine if the game is over based on the seeds and returns boolean of true if it is over or else false*/
    boolean isGameOver()
    {
        int countComputer = 0;
        int countUser = 0;
        for(int x=0; x < columns; x++)
        {
            countComputer += board[0][x];
            countUser += board[1][x];
        }
        return ((countComputer == 0) || (countUser == 0));
    }

    /*Display the appropriate winner of the game */
    void findWinner()
    {
        //Keeps track of points from the end zones
        int player1Result = player1EndZone;
        int player2Result = player2EndZone;

        //Adds the seeds remaining from the hoses in order to find the points for each player.
        for(int r = 0; r < ROWS; r++)
        {
            for(int c = 0; c < columns; c++)
            {
                if(r == 0)
                {
                    player2Result += board[r][c];
                }
                else
                {
                    player1Result += board[r][c];
                }
            }
        }

        /*Displays appropriate result to the console depending on the players results*/
        if(player1Result > player2Result) //Player 1 is the winner
        {
            outputP1.println("WINNER");
            outputP2.println("LOSER");
            out.println("Player one wins with a score of " + player1Result + " to " + player2Result + ".");
        }

        else if(player2Result > player1Result) //Player 2 is the winner
        {
            outputP2.println("WINNER");
            outputP1.println("LOSER");
            out.println("Player two wins with a score of " + player2Result + " to " + player1Result + ".");
        }

        else //Player 1 and Player 2 are tied
        {
            outputP1.println("TIE");
            outputP2.println("TIE");
            out.println("Player one and player two have tied.");
        }

    }

    /*Configures the appropriate board if the player chooses to do pie rule*/
    void pieRule()
    {
        //Switches the end houses
        int temp = player1EndZone;
        player1EndZone= player2EndZone;
        player2EndZone = temp;

        //Receives the original board
        int[][] origBoard = new int[2][columns];
        for(int r = 0; r < 2; r++)
        {
            for (int c = 0; c < columns; c++)
            {
                origBoard[r][c] = board[r][c];
            }
        }

        //Transposes the board for the proper display
        for(int r = 0; r < 2; r++)
        {
            for(int c = 0; c < columns; c++)
            {
                board[r][c] = origBoard[2-1-r][columns-1-c];
            }
        }
    }

    /*Compiles all the components of the configuration into one string in order to send it to each of the clients
    * with all the necessary components, including the random distribution necessary*/
    public String sendConfig(Boolean isFirstPlayer)
    {
        StringJoiner joinConfig = new StringJoiner(" ");
        joinConfig.add("INFO");
        joinConfig.add(Integer.toString(columns));
        joinConfig.add(Integer.toString(numSeeds));
        joinConfig.add(Long.toString(timeLimit));
        if(isFirstPlayer){
            joinConfig.add("F");
        }
        else{
            joinConfig.add("S");
        }
        joinConfig.add(Character.toString(gameMode));

        if(gameMode == 'R')     //gives the distribution of the seeds.
        {
            for(int c = 0; c < columns; c++)
            {
                joinConfig.add(Integer.toString(board[1][c]));
            }
        }

        return joinConfig.toString();
    }

    /*Keeps track of each player by keeping track of the socket along with if it is the first player*/
    public void addPlayer(Socket socket, Boolean isFirstPlayer)
    {
        try{
            if(isFirstPlayer){
                socketP1 = socket;
                outputP1 = new PrintWriter(socket.getOutputStream(), true);
                outputP1.println("WELCOME");
                outputP1.println(sendConfig(isFirstPlayer));
            }
            else{
                socketP2 = socket;
                outputP2 = new PrintWriter(socket.getOutputStream(), true);
                outputP2.println("WELCOME");
                outputP2.println(sendConfig(isFirstPlayer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


@Override
    /*The timer always needs to be running throughout the game along with the server waiting for the move from the client
    * in order to send the appropriate response*/
    public void run()
    {
            String readyP1 = null;
            String readyP2 = null;

            try {
                inputP1 = new BufferedReader(new InputStreamReader(socketP1.getInputStream()));
                inputP2 = new BufferedReader(new InputStreamReader(socketP2.getInputStream()));
                readyP1 = inputP1.readLine();
                readyP2 = inputP2.readLine();

                /*If both clients have connected, then the game is ready to begin from both sides and timer should be
                * set if not zero */
                if(readyP1.equals("READY") && readyP2.equals("READY")){
                    outputP1.println("BEGIN");
                    outputP2.println("BEGIN");

                    //Begins timer for the first player
                    long start = 0;
                    long end = 0;
                    if(timeLimit!=0)
                    {
                        start = System.currentTimeMillis();
                        end = start + timeLimit;
                    }


                    String player1Move = "";
                    String player2Move = "";

                    String allMoves[] = {};
                    int moveCount = 0;
                    String response = "";

                    while(true)
                    {
                        /*PLAYER'S 1 TURN*/
                        if(player1Turn)
                        {
                            if(!goAgain)
                            {
                                while (!inputP1.ready()) {}
                                response = inputP1.readLine();

                                if(response.equals("OK"))
                                {
                                    //Start Timer
                                    if(timeLimit!=0)
                                    {
                                        start = System.currentTimeMillis();
                                        end = start + timeLimit;
                                    }

                                    continue;
                                }
                                else
                                {
                                    /*Once move has been received, either timer is up or there was no time limit, display
                                    * appropriate message*/
                                    if(System.currentTimeMillis() > end && timeLimit!=0)
                                    {
                                        out.println(response);
                                        outputP1.println("OK");
                                        out.println("OK");

                                        outputP1.println("TIME");
                                        out.println("TIME");
                                        outputP1.println("LOSER");
                                        outputP2.println("WINNER");
                                        out.println("Player 2 wins because player 1 took too long.");
                                        player1Turn = false;
                                        break;
                                    }
                                    out.println(response);
                                    out.println("OK");
                                    outputP1.println("OK");
                                }
                                allMoves = response.split(" ");
                            }
                            goAgain = false;
                            response = allMoves[moveCount];

                            /*Implement Pie Rule for the second player*/
                            if(response.equals("P"))
                            {
                                pieRule();
                                outputP2.println(response);
                                player1Turn = false;
                                continue;
                            }

                            int move = Integer.parseInt(response);
                            move --;

                            //If move illegal, display the appropriate message to the clients
                            if(move < 0 || move >= columns || board[1][move] == 0 )
                            {
                                player1Move = player1Move + (columns-move+1);
                                outputP2.println(player1Move);  //sends the move
                                outputP2.println("WINNER");
                                outputP1.println("OK");

                                outputP1.println("ILLEGAL");
                                outputP1.println("LOSER");
                                out.println("Player 2 wins because player 1 played an invalid move.");
                                player1Turn = false;
                                break;
                            }

                            //Updates the seeds on the board
                            int numSeeds = board[1][move];
                            board[1][move] = 0;
                            movingSeeds(numSeeds, 1, move+1);

                            //Switches turn between players now that current player's turn is over
                            if(!goAgain)
                            {
                                player1Move = player1Move + (columns-move);
                                outputP2.println(player1Move);

                                moveCount = 0;
                                player1Move = "";
                                player1Turn = false;
                            }
                            else
                            {
                                player1Move = player1Move + (columns-move) + " ";
                                moveCount++;
                            }
                        }

                        /*PLAYER 2'S TURN*/
                        else
                        {
                            if(!goAgain)
                            {
                                while (!inputP2.ready()) {}
                                response = (inputP2.readLine());

                                if(response.equals("OK"))
                                {
                                    if(timeLimit!=0)
                                    {
                                        start = System.currentTimeMillis();
                                        end = start + timeLimit;
                                    }
                                    continue;
                                }
                                else
                                {
                                    /*Once move has been received, either timer is up or there was no time limit, display
                                    * appropriate message*/
                                    if(System.currentTimeMillis() > end && timeLimit!=0)
                                    {
                                        out.println(response);
                                        outputP2.println("OK");
                                        out.println("OK");

                                        outputP2.println("TIME");
                                        out.println("TIME");
                                        outputP2.println("LOSER");
                                        outputP1.println("WINNER");
                                        out.println("Player 1 wins because player 2 took too long.");
                                        player1Turn = true;
                                        break;
                                    }
                                    out.println(response);
                                    out.println("OK");
                                    outputP2.println("OK");
                                }
                                allMoves = response.split(" ");
                            }
                            goAgain = false;
                            response = allMoves[moveCount];

                            /*Implement Pie Rule for the second player*/
                            if(response.equals("P"))
                            {
                                pieRule();
                                outputP1.println(response);
                                player1Turn = true;
                                continue;
                            }
                            int move = Integer.parseInt(response);
                            move --;

                            //If move illegal, display the appropriate message to the clients
                            if(move < 0 || move >= columns || board[0][columns - 1 - move] == 0 )
                            {
                                player2Move = player2Move + (columns-move+1);
                                outputP1.println(player2Move);      //sends the move first
                                outputP2.println("OK");
                                outputP2.println("ILLEGAL");
                                outputP2.println("LOSER");
                                outputP1.println("WINNER");
                                out.println("Player 1 wins because player 2 played an invalid move.");
                                player1Turn = true;
                                break;
                            }

                            //Updates the seeds on the board
                            int numSeeds = board[0][columns - move -1];
                            board[0][columns - move - 1] = 0;
                            movingSeeds(numSeeds, 0, columns - 2 - move);

                            //Switches turn between players now that current player's turn is over
                            if(!goAgain)
                            {
                                player2Move = player2Move + (columns-move);
                                outputP1.println(player2Move);
                                moveCount = 0;
                                player2Move = "";
                                player1Turn = true;
                            }
                            else
                            {
                                player2Move = player2Move + (columns-move) + " ";
                                moveCount++;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

}
