package com.comp1451.skunk.kasraniktash;

/**
 * This class models the player
 * @author Kasra Niktash
 * @version 1.0
 */
class Player{
    private static String name;

    Player(String name){
        playerSetup(name);
    }

    /**
     * method to allow player enter their name so that it can be used throughout the game
     */
    private void playerSetup(String name) {
        Player.name = name;
    }

    /**
     *
     * @return the name player has entered
     */
    String getName() {
        return name;
    }
}
