package com.example.codenames;

public class lobby {

    private String name;
    private int numPlayers;

    public lobby(String name, int numPlayers) {
        this.name = name;
        this.numPlayers = numPlayers;
    }

    public String getName () {
        return name;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

}
