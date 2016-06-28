package com.nilobarp.holdem;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HandTest {
    Hand hand;
    String suits;
    List<Integer> cardValues;
    HashMap<String, Integer> hash;

    public HandTest () {
        /*this.hand = new Hand();
        this.suits = "CDDSSSS";
        this.cardValues = Arrays.asList(5, 5, 14, 12, 11, 10, 2);

        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 2);
        hash.put("10", 1);
        hash.put("11", 1);
        hash.put("12", 1);
        hash.put("14", 1);

        hand.setValue(90098594);

        hand.rank(suits, cardValues, hash);*/
    }

    /*@Test
    public void serializesToJSON () throws Exception {
        assertEquals("Serializes to JSON", asJson(hand), jsonFixture("fixtures/hand.json"));
    }

    @Test
    public void deserializesFromJSON () throws Exception {
        Hand h = fromJson(jsonFixture("fixtures/hand.json"), Hand.class);
        assertEquals("has same rank name", h.getName(), this.hand.getName());
        assertEquals("has same rank order", h.getRank(), this.hand.getRank());
        assertEquals("has same bit value", h.getValue(), this.hand.getValue());
    }*/

    /*
    @Test
    public void detectsFlush () throws Exception {
        //card suits are sorted in ascending order
        hand.rank("CDSSSSS", cardValues, this.hash);
        assertEquals("is a flush", "Flush", this.hand.getName());

        hand.rank("CDHSSSS", cardValues, this.hash);
        assertNotEquals("is not a flush", "Flush", this.hand.getName());
    }

    @Test
    public void detectsStraight () throws Exception {
        this.cardValues = Arrays.asList(5, 5, 14, 13, 12, 11, 10);
        hand.rank(this.suits, this.cardValues, this.hash);
        assertEquals("is a straight", "Straight", this.hand.getName());

        this.cardValues = Arrays.asList(5, 5, 14, 13, 12, 11, 7);
        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is not a straight", "Straight", this.hand.getName());
    }

    @Test
    public void detectsStraightFlush () throws Exception {
        this.cardValues = Arrays.asList(5, 5, 10, 9, 8, 7, 6);
        this.suits = "CDSSSSS";
        hand.rank(this.suits, this.cardValues, this.hash);
        assertEquals("is a straight flush", "Straight Flush", this.hand.getName());

        this.cardValues = Arrays.asList(5, 5, 10, 9, 8, 7, 2);
        this.suits = "CDSSSSS";
        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is not a straight flush", "Straight Flush", this.hand.getName());

        this.cardValues = Arrays.asList(5, 5, 10, 9, 8, 7, 6);
        this.suits = "CDHSSSS";
        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is a not straight flush", "Straight Flush", this.hand.getName());
    }

    @Test
    public void detectsRoyalFlush () throws Exception {
        this.cardValues = Arrays.asList(5, 5, 14, 13, 12, 11, 10);
        this.suits = "CDSSSSS";
        hand.rank(this.suits, this.cardValues, this.hash);
        assertEquals("is a straight flush", "Royal Flush", this.hand.getName());

        this.cardValues = Arrays.asList(5, 5, 5, 5, 13, 12, 11);
        this.suits = "CDHHSSS";
        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is a straight flush", "Royal Flush", this.hand.getName());
    }

    @Test
    public void detectsOnePair () throws Exception {
        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 2);
        hash.put("10", 1);
        hash.put("11", 1);
        hash.put("12", 1);
        hash.put("14", 1);

        hand.rank(this.suits, this.cardValues, this.hash);
        assertEquals("is a one pair", "One Pair", this.hand.getName());

        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 1);
        hash.put("7", 1);
        hash.put("10", 1);
        hash.put("11", 1);
        hash.put("12", 1);
        hash.put("14", 1);
        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is a pair", "One Pair", this.hand.getName());
    }

    @Test
    public void detectsTwoPair () throws Exception {
        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 2);
        hash.put("10", 1);
        hash.put("11", 1);
        hash.put("12", 2);

        hand.rank(this.suits, this.cardValues, this.hash);
        assertEquals("is two pair", "Two Pair", this.hand.getName());

        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 2);
        hash.put("10", 1);
        hash.put("11", 1);
        hash.put("12", 1);
        hash.put("14", 1);
        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is two pair", "Two Pair", this.hand.getName());
    }

    @Test
    public void detectsThreeOfaKind () throws Exception {
        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 3);
        hash.put("10", 1);
        hash.put("11", 1);
        hash.put("12", 1);

        hand.rank(this.suits, this.cardValues, this.hash);
        assertEquals("is 3 of a kind", "Three of a Kind", this.hand.getName());

        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 2);
        hash.put("10", 1);
        hash.put("11", 1);
        hash.put("12", 2);

        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is not 3 of a kind", "Three of a Kind", this.hand.getName());
    }

    @Test
    public void detectsFullHouse () throws Exception{
        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 3);
        hash.put("10", 1);
        hash.put("11", 2);

        hand.rank(this.suits, this.cardValues, this.hash);
        assertEquals("is full house", "Full House", this.hand.getName());

        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 3);
        hash.put("10", 1);
        hash.put("11", 1);
        hash.put("12", 1);

        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is not full house", "Full House", this.hand.getName());
    }

    @Test
    public void detectsFourOfaKind () throws Exception {
        this.hash = new HashMap<String, Integer>();
        hash.put("2", 1);
        hash.put("5", 4);
        hash.put("11", 2);

        hand.rank(this.suits, this.cardValues, this.hash);
        assertEquals("is four of a kind", "Four of a Kind", this.hand.getName());

        this.hash = new HashMap<String, Integer>();
        hash.put("2", 2);
        hash.put("5", 3);
        hash.put("11", 2);

        hand.rank(this.suits, this.cardValues, this.hash);
        assertNotEquals("is four of a kind", "Four of a Kind", this.hand.getName());
    }*/
}
