package it.polimi.ingsw.example.client;

import java.util.Scanner;

public class CliGameManager {
    Scanner scanner;
    public CliGameManager(){
        scanner = new Scanner(System.in);
    }
    public String scrivoUnaCosa(){
        System.out.println("a chiamrlo arriva");
        return scanner.nextLine();
    }
}
