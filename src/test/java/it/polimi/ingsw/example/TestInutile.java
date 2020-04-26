package it.polimi.ingsw.example;

import com.google.gson.Gson;
import it.polimi.ingsw.example.server.Cane;
import it.polimi.ingsw.example.server.Persona;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class TestInutile {

    Persona santa;

    @Before
    public void setUp(){
        santa = new Persona("Santa", "Claus", 1000, new Cane("Rodrigo"));
    }


    @Test
    public void confront(){
        Gson gson = new Gson();
        String stringSanta = gson.toJson(santa);
        Persona deepSanta = gson.fromJson(stringSanta, Persona.class);
        assertNotEquals(santa, deepSanta);
        assertNotEquals(santa.getCane(), deepSanta.getCane());
    }
}
