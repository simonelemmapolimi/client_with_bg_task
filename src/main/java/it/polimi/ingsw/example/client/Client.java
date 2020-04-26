package it.polimi.ingsw.example.client;

import com.google.gson.Gson;
import it.polimi.ingsw.example.server.Persona;
import it.polimi.ingsw.example.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client implements Runnable, ServerObserver
{
  /* auxiliary variable used for implementing the consumer-producer pattern*/
  private String response = null;
  private String request = null;
  private CliGameManager cli;
/*
  public static void main( String[] args )
  {
    /* Instantiate a new Client which will also receive events from
     * the server by implementing the ServerObserver interface */
  /*  Client client = new Client();
    client.run();
  }*/


  @Override
  public void run()
  {
    /*
     * WARNING: this method executes IN THE CONTEXT OF THE MAIN THREAD
     */
    cli=new CliGameManager();
    String ip = "127.0.0.1";

    /* open a connection to the server */
    Socket server;
    try {
      server = new Socket(ip, Server.SOCKET_PORT);
    } catch (IOException e) {
      System.out.println("server unreachable");
      return;
    }
    System.out.println("Connected");

    /* Create the adapter that will allow communication with the server
     * in background, and start running its thread */
    ServerAdapter serverAdapter = new ServerAdapter(server);
    serverAdapter.addObserver(this);
    Thread serverAdapterThread = new Thread(serverAdapter);
    serverAdapterThread.start();


    request = cli.scrivoUnaCosa();

    while(!"".equals(request)){


      System.out.println("ma ci arriva mai fin ui");
    synchronized (this) {
      /* reset the variable that contains the next string to be consumed
       * from the server */
      response = null;
      serverAdapter.requestConversion(request);

      /* While we wait for the server to respond, we can do whatever we want.
       * In this case we print a count-up of the number of seconds since we
       * requested the conversion to the server. */
      int seconds = 0;
      while (response == null) {
        try{
          wait(1000);
        } catch (InterruptedException e){}
      }
      System.out.println(response);
      /* we have the response, print it */
        /*Persona santaClaus = new Gson().fromJson(response, Persona.class);
        System.out.println(response);
        santaClaus.getCane().setNome("Paolo");
        System.out.println(santaClaus.getCane().getNome());*/
    }
    request = cli.scrivoUnaCosa();
  }

    serverAdapter.stop();
  }


  @Override
  public synchronized void didReceiveConvertedString(String oldStr, String newStr)
  {
    /*
     * WARNING: this method executes IN THE CONTEXT OF `serverAdapterThread`
     * because it is called from inside the `run` method of ServerAdapter!
     */

    /* Save the string and notify the main thread */
    response = newStr;
    notifyAll();
  }

  public synchronized void sendThis(String gsonObject){
    request = gsonObject;
    notifyAll();
  }
}
