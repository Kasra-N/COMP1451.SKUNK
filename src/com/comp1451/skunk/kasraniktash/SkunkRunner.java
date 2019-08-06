package com.comp1451.skunk.kasraniktash;

import java.util.concurrent.TimeUnit;

/**
 * This class runs the skunk game
 * @author Kasra
 * @version 1.0
 */

public class SkunkRunner {

    public static void main(String[] args) throws InterruptedException {

        Game.skunkLogo();
        TimeUnit.MILLISECONDS.sleep(1000);
        Game.rules();

        TimeUnit.MILLISECONDS.sleep(3000);
        Game g = new Game();
        g.play();
    }
}