package com.example.codenames;

public class lobby {

    private String name;
    private int numPlayers;
    private int id;

    public lobby(String name, int numPlayers, int id) {
        this.name = name;
        this.numPlayers = numPlayers;
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getId() { return id; }

}
