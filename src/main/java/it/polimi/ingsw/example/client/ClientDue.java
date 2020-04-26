package it.polimi.ingsw.example.client;

import com.google.gson.Gson;
import it.polimi.ingsw.example.server.Persona;
import it.polimi.ingsw.example.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class ClientDue implements Runnable, ServerObserver
{
    /* auxiliary variable used for implementing the consumer-producer pattern*/
    private String response = null;
    private String request = null;
    private boolean readytosend = false;
    private boolean notReceived = false;
    private UserInterfaceDue ui;
    //passiamo come riferimento la UI per poterla usare poi per chiamare il suo metodo
    public ClientDue(UserInterfaceDue ui){
        this.ui = ui;
    }

    @Override
    public void run()
    {
        /* SIMONE: NOTA ci sono molti commenti del prof, non li levo eheh
        * */


        /*
         * WARNING: this method executes IN THE CONTEXT OF THE MAIN THREAD
         */
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


        while(!"".equals(request)){
            //esegue questo thread all'infinito finché non ha qualcosa da mandare, poi passa oltre
            //la wait mi pare fosse necessaria per non impallare tutto
            //tra l'altro questo while è circa l'unica cosa che ho toccato qua dentro, per il resto è
            //tutto farina del sacco del prof
            while(readytosend==false){
                try{
                    wait(1000);
                } catch (Exception e){}
            }

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
                System.out.println("e questa era quella del thread");
                /* we have the response, print it */
        /*Persona santaClaus = new Gson().fromJson(response, Persona.class);
        System.out.println(response);
        santaClaus.getCane().setNome("Paolo");
        System.out.println(santaClaus.getCane().getNome());*/

            }






            /* a parte che sopra puoi vedere dei tentativi con gson
                qui sotto c'è la chiamata al metodo dell'ui che poi muore prima di poter
                chiamare nuovamente sendThis
                * */


            ui.receivedMessage(response);

            //si reinizializza tutto
            request = null;
            readytosend = false;
        }
        //qui sono io che cerco di capire se il thread crepi o no, ma pare di no
        System.out.println("capiamoci, ma muore?");
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
        notReceived = false;
        notifyAll();
    }

    public void sendThis(String gsonObject){
        //synchronized (this) {
            request = gsonObject;
            readytosend = true;
            //notifyAll();
        //}
    }
/*
    public String getMessage(){
        while(response==null && notReceived)
        {
            try{
                wait(1000);
            } catch (Exception e){}
        }
        notReceived = false;
        return response;
    }*/
}
