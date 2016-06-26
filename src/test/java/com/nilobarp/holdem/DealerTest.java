package com.nilobarp.holdem;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DealerTest {

    @Test
    public void canShuffle () throws Exception {
        Dealer dealer = new Dealer();
        ArrayList<String> deck = new ArrayList<String>(dealer.getDeck());
        dealer.shuffle();
        ArrayList<String> shuffledDeck = new ArrayList<String>(dealer.getDeck());

        assertNotEquals("deck is shuffled", deck.toArray(),shuffledDeck.toArray());
    }

    @Test
    public void canDealCards () throws Exception {
        int cardsToDeal = 5;
        Dealer dealer = new Dealer();
        List<String> cards = dealer.deal(cardsToDeal);

        assertEquals("deals exact number of cards", cardsToDeal, cards.size());
    }
}
