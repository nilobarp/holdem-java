package com.nilobarp.holdem;

import java.util.List;

public class Table {
    private String cards;
    private List<Player> players;

    public String getCards () {
        return cards;
    }

    public void setCards (String cards) {
        //chomp spaces if any
        this.cards = cards.replaceAll("\\s", "");
    }

    public List<Player> getPlayers () {
        return players;
    }

    public void setPlayers (List<Player> players) {
        this.players = players;
    }
}
