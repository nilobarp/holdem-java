package com.nilobarp.holdem;

import java.util.*;

public class Dealer {

    // dealer
    // -> has the getDeck of cards
    // --> can shuffle the getDeck
    // -> deals 3 or 5 community cards
    // -> deals 2 cards per player

    private ArrayList<String> deckOfCards = new ArrayList<String>(
            Arrays.asList(
            "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "HT", "HJ", "HQ", "HK", "HA",
            "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DT", "DJ", "DQ", "DK", "DA",
            "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "ST", "SJ", "SQ", "SK", "SA",
            "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CT", "CJ", "CQ", "CK", "CA")
    );

    private int deckIndex = 0;

    public Dealer () {
        shuffle();
    }

    /**
     * Shuffles the getDeck of cards
     */
    public void shuffle () {
        Collections.shuffle(deckOfCards);
    }

    public ArrayList<String> getDeck() {
        return deckOfCards;
    }

    /**
     * Deals specified number of cards from the getDeck
     * @param numberOfCards Number of cards to deal
     * @return List of cards
     * @throws Exception
     */
    public List<String> deal (int numberOfCards) throws Exception {
        if (deckOfCards.size() < numberOfCards) {
            throw new Exception("No more cards in getDeck");
        }

        List<String> dealtCards = deckOfCards.subList(deckIndex, deckIndex + numberOfCards);

        /**
         * Increment deckIndex to point to next available card in the getDeck
         */
        deckIndex += numberOfCards;

        dealtCards = deckOfCards.subList(deckIndex, deckIndex + numberOfCards);

        return dealtCards;
    }
}
