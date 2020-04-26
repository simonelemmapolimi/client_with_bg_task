package it.polimi.ingsw.example.server;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


public class ClientHandler implements Runnable
{
  private Socket client;


  ClientHandler(Socket client)
  {
    this.client = client;
  }


  @Override
  public void run()
  {
    try {
      handleClientConnection();
    } catch (IOException e) {
      System.out.println("client " + client.getInetAddress() + " connection dropped");
    }
  }


  private void handleClientConnection() throws IOException
  {
    System.out.println("Connected to " + client.getInetAddress());

    ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
    ObjectInputStream input = new ObjectInputStream(client.getInputStream());

    try {
      while (true) {
        /* read a String from the stream and write an uppercase string in response */
        Object next = input.readObject();
        String str = (String)next;
        Persona daMandare = null;
        Gson gson = new Gson();
        if(str.equals("Santa"))
         daMandare = new Persona("Santa", "Claus", 1000, new Cane("Alfredo"));
        else if(str.equals("Roberto"))
          daMandare = new Persona("Roberto", "Carlos", 43, new Cane("Rodrigo"));
        String toSend = gson.toJson(daMandare);
        output.writeObject(toSend);
      }
    } catch (ClassNotFoundException | ClassCastException e) {
      System.out.println("invalid stream from client");
    }

    client.close();
  }
}
