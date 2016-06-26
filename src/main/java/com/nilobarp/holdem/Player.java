package com.nilobarp.holdem;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Player implements Comparable<Player> {
    @NotNull
    private int id;

    /**
     * Collection of cards the player has
     */
    private List<String> cards;

    /**
     * Stores the reduced hash of cards
     */
    //@Optional
    private HashMap<String, Integer> hash = new HashMap<String, Integer>();

    /**
     * Stores suits of cards without numbers
     */
    private String suits = "";

    /**
     * Collection of card values without suites
     */
    private List<Integer> cardValues;

    private Hand hand;

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public List<String> getCards () {
        return cards;
    }

    public void setCards (List<String> cards) {
        this.cards = cards;
    }

    public void setSuits (String suits) {
        //sort the string of suits
        char[] s = suits.toCharArray();
        Arrays.sort(s);
        this.suits = new String(s);
    }

    public String getSuits () {
        return this.suits;
    }

    public void setHash (HashMap<String, Integer> hash) {
        this.hash = hash;
    }

    public HashMap<String, Integer> getHash () {
        return this.hash;
    }

    public void setCardValues (List<Integer> values) {
        this.cardValues = values;
    }

    public List<Integer> getCardValues () {
        return this.cardValues;
    }

    public void setHand (Hand hand) {
        this.hand = hand;
    }

    public Hand getHand () {
        return this.hand;
    }

    public int compareTo (Player p) {
        Hand hand = p.getHand();
        int rankCompare = hand.getRank() > this.getHand().getRank() ? 1
                : hand.getRank() < this.getHand().getRank() ? -1
                : hand.getRank() - this.getHand().getRank();
        if (rankCompare != 0) {
            return rankCompare;
        } else {
            return hand.getValue() > this.getHand().getValue() ? 1
                    : hand.getValue() < this.getHand().getValue() ? -1
                    : hand.getValue() - this.getHand().getValue();
        }
    }
}
