package com.nilobarp.holdem;

import java.util.List;

public class Table {
    private List<String> cards;
    private List<Player> players;

    public List<String> getCards () {
        return cards;
    }

    public void setCards (List<String> cards) {
        this.cards = cards;
    }

    public List<Player> getPlayers () {
        return players;
    }

    public void setPlayers (List<Player> players) {
        this.players = players;
    }
}
