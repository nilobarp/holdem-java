package com.nilobarp.holdem;

import java.util.HashMap;

public class Hand {
    /**
     * Hands are first sorted by rank, if two ranks are same a tie occurs.
     * In case of tie, we calculate poker score of the hands and use this
     * score to find the winning hand.
     *
     * Cards are stored in an int array along with their suit information.
     * Each suit is weighed by a numeric value as below:
     * +-------------+
     * Club     | 100
     * Diamond  | 200
     * Hearts   | 300
     * Spade    | 400
     *
     * The value of each suit is added to the cards face value and stored in
     * the int[]. This distinguishes each card in the deck and can be easily
     * analyzed for hand type and score calculations.
     *
     * See Analyzer->redude() method for reduction process
     */

    //Name of the hand rank Flush, Straight etc
    private String name;
    //Numerical representation of the rank order
    private int rank;
    //Poker score of the hand, used in case of a tie
    private int score;

    public String getName () {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getRank () {
        return this.rank;
    }

    public void setRank (int rank) {
        this.rank = rank;
    }

    public void setScore (int score) {
        this.score = score;
    }

    public int getScore () {
        return this.score;
    }
}
