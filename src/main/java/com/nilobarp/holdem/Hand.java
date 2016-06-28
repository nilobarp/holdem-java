package com.nilobarp.holdem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hand {
    private String name;
    private int rank;
    private int value;
    private HashMap<Integer, String> handRanks = new HashMap();

    public Hand () {
        buildHandRanks();
    }

    public void rank (String suits, List<Integer> cardValues, HashMap<String, Integer> hash) {
        if(isRoyalFlush(suits, cardValues))
            setRank(10);
        else if(isStraightFlush(suits, cardValues))
            setRank(9);
        else if(isFourOfaKind(hash))
            setRank(8);
        else if(isFullHouse(hash))
            setRank(7);
        else if(isFlush(suits))
            setRank(6);
        else if(isStraight(cardValues))
            setRank(5);
        else if(isThreeOfaKind(hash))
            setRank(4);
        else if(isTwoPair(hash))
            setRank(3);
        else if(isOnePair(hash))
            setRank(2);
        else
            setRank(1);

    }

    public String getName () {
        return this.name;
    }

    private void setRank (int rank) {
        this.rank = rank;
        this.name = handRanks.get(rank);
    }

    public int getRank () {
        return this.rank;
    }

    public void setValue (int value) {
        this.value = value;
    }

    public int getValue () {
        return this.value;
    }


    private void buildHandRanks() {
        handRanks.put(10, "Royal Flush");
        handRanks.put(9, "Straight Flush");
        handRanks.put(8, "Four of a Kind");
        handRanks.put(7, "Full House");
        handRanks.put(6, "Flush");
        handRanks.put(5, "Straight");
        handRanks.put(4, "Three of a Kind");
        handRanks.put(3, "Two Pair");
        handRanks.put(2, "One Pair");
        handRanks.put(1, "High Card");
    }

    private boolean isFlush (String suits) {
        String sortedSuits;
        char[] s = suits.toCharArray();
        Arrays.sort(s);
        sortedSuits = new String(s);
        Matcher matcher = Pattern.compile("SSSSS|DDDDD|HHHHH|SSSSS").matcher(sortedSuits);
        return matcher.find();
    }

    private boolean isRoyalFlush(String suits, List<Integer> cardValues) {
        int startIndex = 0;
        if(isFlush(suits)) {
            for(int i = 0; i < cardValues.size(); i++) {
                if (cardValues.get(i) == 14) {
                    startIndex = i;
                    break;
                }
            }
            if (startIndex > cardValues.size() - 5)
                return false;
            else
                return cardValues.get(startIndex) == 14 &&
                    cardValues.get(++startIndex) == 13 &&
                    cardValues.get(++startIndex) == 12 &&
                    cardValues.get(++startIndex) == 11 &&
                    cardValues.get(++startIndex) == 10;
        }

        return false;
    }

    private boolean isStraight (List<Integer> cardValues) {
        boolean straight = true;
        for (int i = 0; i < cardValues.size() - 1; i++) {
            if(i > cardValues.size() - 6 && cardValues.get(i) != cardValues.get(i + 1) + 1)
                straight = false;
        }
        return straight;
    }

    private boolean isStraightFlush(String suits, List<Integer> cardValues) {
        return (this.isFlush(suits) && this.isStraight(cardValues));
    }

    private boolean isOnePair (HashMap<String, Integer> hash) {
        int pairCount = 0;
        for (int value : hash.values()) {
            if (value == 2)
                pairCount++;
        }

        return pairCount == 1;
    }


    private boolean isTwoPair (HashMap<String, Integer> hash) {
        int pairCount = 0;
        for (int value : hash.values()) {
            if (value == 2)
                pairCount++;
        }

        return pairCount == 2;
    }

    private boolean isThreeOfaKind(HashMap<String, Integer> hash) {
        int threes = 0;
        for(Integer value : hash.values()) {
            if(value == 3) {
                threes++;
            }
        }

        return threes == 1;
    }

    private boolean isFourOfaKind(HashMap<String, Integer> hash) {
        int fours = 0;
        for(Integer value : hash.values()) {
            if(value == 4) {
                fours++;
            }
        }
        return fours == 1;
    }

    private boolean isFullHouse(HashMap<String, Integer> hash) {
        int threes = 0;
        for(Integer value : hash.values()) {
            if(value == 3) {
                threes++;
            }
        }

        if (threes == 2)
            return true;
        else
            return (threes == 1 && (this.isOnePair(hash) || this.isTwoPair(hash)));
    }
}
