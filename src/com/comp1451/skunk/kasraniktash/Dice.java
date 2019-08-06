package com.comp1451.skunk.kasraniktash;

/**
 * this class models dice
 * @author Kasra Niktash
 * @version 1.0
 */
class Dice {
    private int dieOne = 0;
    private int dieTwo = 0;

    Dice(){
        roll();
    }

    /**
     *
     * method to roll the dice
     */
    void roll() {
        setDice((int)(Math.random()*6) + 1, (int)(Math.random()*6) + 1);
    }

    /**
     *
     * @param dieOne setter for first die
     * @param dieTwo setter for second die
     */
    void setDice(int dieOne, int dieTwo) {
        this.dieOne = dieOne;
        this.dieTwo = dieTwo;
    }

    /**
     *
     * @return first die
     */
    synchronized int getDieOne() {
        return dieOne;
    }
    /**
     *
     * @return second die
     */
    synchronized int getDieTwo() {
        return dieTwo;
    }

}
