package it.polimi.ingsw.example.server;

public class Persona {
    private String nome;
    private String cognome;
    private int eta;
    private Cane cane;

    public Persona(String nome, String cognome, int eta, Cane cane){
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.cane = cane;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public Cane getCane() {
        return cane;
    }

    public void setCane(Cane cane) {
        this.cane = cane;
    }
}
