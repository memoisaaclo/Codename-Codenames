package com.example.codenames;

/**
 * @author Dylan Booth
 */

// Class Object for some instances. Is called to create and store lobby objects and values

public class lobby {

    private String name;
    private int numPlayers;
    private String id;

    public lobby(String name, int numPlayers, String id) {
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

    public String getId() { return id; }

}
