package Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*Allows client to setup based on configuration sent from server*/
public class MainClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Client started!");

        //Allows client to connect to specific server
        String serverAddress = JOptionPane.showInputDialog("Enter IP Address of a server:");
        Socket s = new Socket(serverAddress, 9090);

        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String welcome = input.readLine();
        String configuration = input.readLine();

        System.out.println(welcome);
        System.out.println(configuration);
        PrintWriter output = new PrintWriter(s.getOutputStream(), true);

        //Creates a board for the user
        new Home(input, output, configuration);
    }
}
