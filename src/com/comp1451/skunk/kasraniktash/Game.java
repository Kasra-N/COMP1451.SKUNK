package com.comp1451.skunk.kasraniktash;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class models the game
 * @author Kasra Niktash
 * @version 1.0
 */
class Game {
    private static Random random = new Random();
    private static Scanner key = new Scanner(System.in);
    private Player p;
    private Dice dice;

    private static int subScoreAI = 0;
    private static int subScorePlayer = 0;
    private static int totalScoreAI = 0;
    private static int totalScorePlayer = 0;

    private boolean standingAI = true;
    private boolean standingPlayer = true;

    private static int round = 1;

    private final static int MIN_DIE = 1;
    private final static int SKUNK = 5;

    private static boolean over = false;

    Game() {
        System.out.println("What is your name?");
        p = new Player(key.next());
        dice = new Dice();
    }

    /**
     * method to roll the dice
     */
    private void rollTheDice() throws InterruptedException {
        if (standingAI || standingPlayer) {
            for (int i = 0; i < 5; i++) {
                dice.roll();
            }
            System.out.println("The dice got rolled: " + dice.getDieOne() + " and " + dice.getDieTwo());
            if (standingAI && standingPlayer) {
                if (dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE) {
                    System.out.println("Everyone is standing. Rolling the dice again...");
                    TimeUnit.MILLISECONDS.sleep(400);
                    rollTheDice();
                }
            }
        }
    }

    /**
     * method to update the scores for player and AI
     */
    private void updateScores() {
        if (standingAI && standingPlayer) {
            subScoreAI += (dice.getDieOne() + dice.getDieTwo());
            subScorePlayer += (dice.getDieOne() + dice.getDieTwo());

            System.out.println(p.getName().toUpperCase() + "'s score for this round is: " + subScorePlayer);
            System.out.println("Computer's score for this round is: " + subScoreAI);
        }
        if (standingAI && !standingPlayer) {
            if (dice.getDieOne() == MIN_DIE && dice.getDieTwo() == MIN_DIE) {
                subScoreAI = 0;
                totalScoreAI = 0;
                dice.setDice(0, 0);
                exitRound();

                System.out.println("Computer lost the game");
                System.out.println("Computers total score is " + totalScoreAI);

                System.out.println(p.getName().toUpperCase() + " won the game");
                System.out.println(p.getName().toUpperCase() + "'s total score is " + totalScorePlayer);

                standingAI = false;
                standingPlayer = false;
                round = SKUNK;
            }
            if (dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE) {
                subScoreAI = 0;
                dice.setDice(0, 0);
                exitRound();
                System.out.println("Computer lost this round");
                System.out.println(p.getName().toUpperCase() + " won this round");
                standingAI = false;
                standingPlayer = false;
            }
            if (!(dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE)) {
                subScoreAI += (dice.getDieOne() + dice.getDieTwo());
            }

            System.out.println(p.getName().toUpperCase() + "'s score for this round is: " + subScorePlayer);
            System.out.println("Computer's score for this round is: " + subScoreAI);
        }
        if (!standingAI && standingPlayer) {
            if (dice.getDieOne() == MIN_DIE && dice.getDieTwo() == MIN_DIE) {
                subScorePlayer = 0;
                totalScorePlayer = 0;
                dice.setDice(0, 0);
                exitRound();

                System.out.println(p.getName().toUpperCase() + "lost the game");
                System.out.println(p.getName().toUpperCase() + "'s total score is " + totalScorePlayer);

                System.out.println("Computer won the game");
                System.out.println("Computer's total score is " + totalScoreAI);

                standingAI = false;
                standingPlayer = false;
                round = SKUNK;
            }
            if (dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE) {
                subScorePlayer = 0;
                dice.setDice(0, 0);
                exitRound();

                System.out.println(p.getName().toUpperCase() + " lost this round");
                System.out.println("Computer won this round");

                standingAI = false;
                standingPlayer = false;
            }
            if (!(dice.getDieOne() == MIN_DIE || dice.getDieTwo() == MIN_DIE)) {
                subScorePlayer += (dice.getDieOne() + dice.getDieTwo());
            }
            System.out.println(p.getName().toUpperCase() + "'s score for this round is: " + subScorePlayer);
            System.out.println("Computer's score for this round is: " + subScoreAI);
        }
    }

    /**
     * method to keep track of rounds and end the round accordingly
     */
    private void checkEndRound() {
        if (!standingAI && !standingPlayer) {
            totalScorePlayer += subScorePlayer;
            totalScoreAI += subScoreAI;

            System.out.println("End of round: " + round);
            System.out.println(p.getName().toUpperCase() + "'s total score is: " +
                    totalScorePlayer);
            System.out.println("Computer's total score is: " +
                    totalScoreAI + "\n");

            standingAI = true;
            standingPlayer = true;

            subScorePlayer = 0;
            subScoreAI = 0;

            round++;
        }
    }

    /**
     * method to exit the round accordingly
     */
    private void exitRound() {
        standingAI = false;
        standingPlayer = false;
    }

    /**
     * method to keep track of the game and end the game accordingly with a final message
     */
    private void checkEndGame() {
        if (round > SKUNK) {
            finalMessage();

            standingAI = true;
            standingPlayer = true;
            totalScorePlayer = 0;
            totalScoreAI = 0;
            over = true;
            round = 1;
        }
    }

    /**
     * method to generate final message and declare the winner
     */
    private void finalMessage() {
        if (totalScoreAI > totalScorePlayer) {
            System.out.println(" ");
            System.out.println("COMPUTER WINS!");
            return;
        }
        if (totalScoreAI < totalScorePlayer) {
            System.out.println(" ");
            System.out.println(p.getName().toUpperCase() + " WINS!");
            return;
        }
        System.out.println(" ");
        System.out.println(p.getName().toUpperCase() + " IS ALMOST AS SMART AS THE COMPUTER");
    }

    /**
     * method to create pseudo-random AI decision
     */
    private void aIDecision() {
        if (subScoreAI != 0 && standingAI) {
            standingAI = random.nextBoolean();
            if (standingAI && standingPlayer) {
                System.out.println("Computer chooses to keep playing. Choice is yours...");
            }
            if (!standingAI && standingPlayer) {
                System.out.println("Computer chooses to stop. Choice is yours...");
            }
            if (standingAI && !standingPlayer) {
                System.out.println("Computer chooses to continue. The dice are rolled again.");
            }
            if (!standingAI && !standingPlayer) {
                System.out.println("Computer chooses to stop.");
            }
        }
    }

    /**
     * method to allow player to sit or keep playing accordingly
     */
    private void playerDecision() {
        System.out.println("Enter \"sit\" to sit for this round");
        if (standingPlayer) {
            if (key.next().equalsIgnoreCase("sit")) {
                standingPlayer = false;
                System.out.println(p.getName().toUpperCase() + " chose to stop.");
            }
        }
    }

    /**
     * method to create skunk logo
     */
    static void skunkLogo() {
        System.out.println(
                "\n\n\n\n" +
                "      $$$$$$\\  $$\\   $$\\ $$\\   $$\\ $$\\   $$\\ $$\\   $$\\\n" +
                "     $$  __$$\\ $$ | $$  |$$ |  $$ |$$$\\  $$ |$$ | $$  |\n" +
                "     $$ /  \\__|$$ |$$  / $$ |  $$ |$$$$\\ $$ |$$ |$$  /\n" +
                "     \\$$$$$$\\  $$$$$  /  $$ |  $$ |$$ $$\\$$ |$$$$$  /\n" +
                "      \\____$$\\ $$  $$<   $$ |  $$ |$$ \\$$$$ |$$  $$<\n" +
                "     $$\\   $$ |$$ |\\$$\\  $$ |  $$ |$$ |\\$$$ |$$ |\\$$\\\n" +
                "     \\$$$$$$  |$$ | \\$$\\ \\$$$$$$  |$$ | \\$$ |$$ | \\$$\\\n" +
                "      \\______/ \\__|  \\__| \\______/ \\__|  \\__|\\__|  \\__|" +
                "\n" +
                "\n" + "\n");
    }

    /**
     * method to display the rules of skunk game
     */
    static void rules() {
        System.out.println("========================================================\n" +
                "                     ╦═╗╦ ╦╦  ╔═╗╔═╗\n" +
                "                     ╠╦╝║ ║║  ║╣ ╚═╗\n" +
                "                     ╩╚═╚═╝╩═╝╚═╝╚═╝\n" +
                "========================================================\n\n" +
                "Each letter of \"skunk\" represents a different round of the game; play begins with the \"S\"(1)\n" +
                "column and continues through the \"K\"(5) column.\n" +
                "The object of \"skunk\" is to accumulate the greatest possible point total over the five rounds.\n" +
                "The rules for play are the same for each of the five rounds.\n" +
                "To accumulate points in a given round, a pair of dice is rolled.\n" +
                "A player gets the total of the dice and records it in his or her column, unless a \"one\" comes\n" +
                "up.\n" +
                "If a \"one\" comes up, play is over for that round and all the player's points in that column are\n" +
                "wiped out.\n" +
                "If \"double ones\" come up, all points accumulated in prior columns are wiped out as well.\n" +
                "If a \"one\" doesn't occur, the player may choose either to try for more points on the next roll\n" +
                "or to stop and keep what he or she has accumulated.\n" +
                "Note: If a \"one\" or \"double ones\" occur on the very first roll of a round while everyone is" +
                "\nstanding, then the dice will get re-rolled. \n" + "HOW TO PLAY:\n" +
                "Game will start by asking your name, you can enter any name and press enter.\n" +
                "by typing sit, you can sit through any round, or by entering any letter you will continue to roll the dice\n");
    }


    /**
     * method that calls the private methods that comprise the entire game in a loop
     */
    void play() throws InterruptedException {
        while (!over) {
            System.out.println("Enter any \"letter\" to start rolling the dice");

            aIDecision();

            playerDecision();

            rollTheDice();

            updateScores();

            checkEndRound();

            checkEndGame();
        }
    }
}