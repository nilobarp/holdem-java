package com.nilobarp.holdem;

import javax.validation.constraints.NotNull;

public class Player implements Comparable<Player> {
    @NotNull
    private int id;

    /**
     * Collection of cards the player has
     */
    private String cards;

    private Hand hand;

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getCards () {
        return cards;
    }

    public void setCards (String cards) {
        this.cards = cards.replaceAll("\\s", "");
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
            return hand.getScore() > this.getHand().getScore() ? 1
                    : hand.getScore() < this.getHand().getScore() ? -1
                    : hand.getScore() - this.getHand().getScore();
        }
    }
}
