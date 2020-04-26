package it.polimi.ingsw.example.client;

import com.google.gson.Gson;

public class UserInterface {
    Client client;
    //CliGameManager cli;
    public UserInterface(){
        client = new Client();
        client.run();
        //cli = new CliGameManager();

    }
}
