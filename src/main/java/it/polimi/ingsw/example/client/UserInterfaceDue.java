package it.polimi.ingsw.example.client;

/*
* questa classe è tipo il middleware tra più set di classi
* da una parte abbiamo il client, che gestisce le comunicazioni col server
* dall'altra abbiamo CliGameManager che gestisce le interazioni con l'utente
* [nome inconsistente con quello del progetto, in cui è CliPlayerManager a gestire gli input]
* */

public class UserInterfaceDue {
    ClientDue client;
    CliGameManager cli;
    Thread t;
    Thread t1;
    public UserInterfaceDue(){

        //si avvia il client
        client = new ClientDue(this);
        t = new Thread(client);
        t.start();
        //client.run();

        //si crea l'oggetto di lettura
        cli = new CliGameManager();

        //questo metodo chiamerà il metodo sendthis del client dopo che la cli avrà fornito un input
        client.sendThis(cli.scrivoUnaCosa());

        /*
        questo pezzo di codice è quello che non so se mettere o no, ma forse va messo sì
        quando questo while è commentato il programma si ferma alla prima iterazione
        */
        while(true) {

            //qui dovremmo mettere il riconoscimento dei messaggi?
            client.sendThis(cli.scrivoUnaCosa());
        }

        //miei esperimenti coi thread in azione
        //t1 = Thread.currentThread();
    }

    public void receivedMessage(String msg){
        System.out.println(msg);
            System.out.println(client.equals(Thread.currentThread()));

            /*questo commentato è il pezzo di codice che vorrei andasse e invece no
            ci arriva a stampare quelle cose che vedi sopra, ci arriva a chiamare scrivoUnaCosa
            però poi il client non arriva a fare una seconda chiamata, dio solo sa perché
            * */
            //client.sendThis(cli.scrivoUnaCosa());
    }
}
