package com.nilobarp.holdem;

import java.util.*;

public class Analyzer {
    private Table table;

    String[] rankNames = new String[]{
            "High Card", "One Pair",
            "Two Pair", "Three of a Kind",
            "Straight", "Flush",
            "Full House", "Four of a Kind",
            "Straight Flush", "Royal Flush"
    };


    public Analyzer (Table table) {
        this.table = table;
    }

    /**
     * Runs analysis on the Table to determine hand ranks
     * @return Analyzed hands
     */
    public Table run () {

        List<Player> reduced = new ArrayList<Player>();
        for(int i = 0; i < this.table.getPlayers().size(); i++) {
            reduced.add(reduce(this.table.getCards(), this.table.getPlayers().get(i)));
        }

        this.table.setPlayers(reduced);

        Collections.sort(reduced);

        return this.table;
    }

    /**
     * Reduces the given hand to an analysable state
     * @param communityCards
     * @param player
     * @return Player
     */
    private Player reduce (String communityCards, final Player player) {
        String[] cardsInHand;
        //combine community and hold cards
        String allCards = communityCards.concat(player.getCards());

        //split into groups of 2 chars (suit + value)
        cardsInHand = allCards.split("(?<=\\G.{2})");

        //Represent the cards numerically
        Integer[] numCards = new Integer[cardsInHand.length];
        for(int i = 0; i < cardsInHand.length; i++) {
            String thisCard = cardsInHand[i];
            //get weight of the card suit
            int suit = suitWeight(thisCard.replaceAll("[^CDHS]", ""));
            //get numeric value of the card face
            int faceValue = faceValue(thisCard.replaceAll("[CDHS]", ""));

            numCards[i] = suit + faceValue;
        }

        final HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        //arrange the cards according to number of appearance
        for(int i = 0; i < numCards.length; i++) {
            int value = extractValue(numCards[i]);

            hash.put(value, (hash.get(value) != null && hash.get(value) >= 1) ? hash.get(value) + 1 : 1);
        }

        //Sort array of cards based on the hash
        Arrays.sort(numCards, new Comparator<Integer>() {
            public int compare (Integer a, Integer b) {
                int aKey = extractValue(a);
                int bKey = extractValue(b);
                return hash.get(aKey) < hash.get(bKey) ? 1
                        : hash.get(aKey) > hash.get(bKey) ? -1
                        : bKey - aKey;
            }
        });

        int rank = rankHand(numCards, hash);

        Hand hand = new Hand();
        hand.setRank(rank);
        hand.setName(rankNames[rank]);
        hand.setScore(pokerScore(numCards));

        player.setHand(hand);

        return player;
    }

    /**
     * Finds rank of the hand
     * @param cards
     * @param hash
     * @return rank order
     */
    private int rankHand (Integer[] cards, HashMap<Integer, Integer> hash) {
        int rank = 0;
        if(isRoyalFlush(cards))
            rank = 9;
        else if(isStraightFlush(cards))
            rank = 8;
        else if(isFourOfaKind(hash))
            rank = 7;
        else if(isFullHouse(hash))
            rank = 6;
        else if(isFlush(cards))
            rank = 5;
        else if(isStraight(cards))
            rank = 4;
        else if(isThreeOfaKind(hash))
            rank = 3;
        else if(isTwoPair(hash))
            rank = 2;
        else if(isOnePair(hash))
            rank = 1;

        return rank;
    }

    /**
     * Determine if the given cards constitutes a flush
     * @param cards
     * @return boolean
     */
    private boolean isFlush(Integer[] cards) {
        int[] suits = new int[cards.length];
        for(int i = 0; i < cards.length; i++)
            suits[i] = extractSuit(cards[i]);

        //sort the array
        Arrays.sort(suits);

        //check for 5 consecutive numbers
        int seqCount = 1;
        for(int i = 0; i < suits.length - 1; i++) {
            if(suits[i] == suits[i+1]) {
                seqCount++;
            } else {
                seqCount = 1;
            }

            if(seqCount == 5) break;
        }

        return seqCount == 5;
    }

    /**
     * Determine if the given cards constitutes a straight
     * @param cards
     * @return boolean
     */
    private boolean isStraight (Integer[] cards) {
        int[] values = new int[cards.length];
        for(int i = 0; i < cards.length; i++)
            values[i] = extractValue(cards[i]);

        Arrays.sort(values);

        //check for 5 consecutive numbers
        int seqCount = 1;
        for(int i = 0; i < values.length - 1; i++) {
            if(values[i] == values[i+1] - 1) {
                seqCount++;
            } else {
                seqCount = seqCount < 5 ? 1 : seqCount;
            }
        }

        return seqCount >= 5;
    }

    /**
     * Determine if the given cards constitutes a straight flush
     * @param cards
     * @return boolean
     */
    private boolean isStraightFlush (Integer[] cards) {
        return isStraight(cards) && isFlush(cards);
    }

    /**
     * Determine if the given cards constitutes a royal flush
     * @param cards
     * @return boolean
     */
    private boolean isRoyalFlush (Integer[] cards) {
        if(isFlush(cards)) {
            boolean royalSeq = false;
            List<Integer> tmp = Arrays.asList(cards);
            if(tmp.indexOf(114) > -1) {
                royalSeq =
                        tmp.indexOf(113) > -1 &&
                        tmp.indexOf(112) > -1 &&
                                tmp.indexOf(111) > -1 &&
                                tmp.indexOf(110) > -1;
            }
            if(!royalSeq && tmp.indexOf(214) > -1) {
                royalSeq =
                        tmp.indexOf(213) > -1 &&
                                tmp.indexOf(212) > -1 &&
                                tmp.indexOf(211) > -1 &&
                                tmp.indexOf(210) > -1;
            }
            if(!royalSeq && tmp.indexOf(314) > -1) {
                royalSeq =
                        tmp.indexOf(313) > -1 &&
                                tmp.indexOf(312) > -1 &&
                                tmp.indexOf(311) > -1 &&
                                tmp.indexOf(310) > -1;
            }
            if(!royalSeq && tmp.indexOf(414) > -1) {
                royalSeq =
                        tmp.indexOf(413) > -1 &&
                                tmp.indexOf(412) > -1 &&
                                tmp.indexOf(411) > -1 &&
                                tmp.indexOf(410) > -1;
            }

            return royalSeq;
        }

        return false;
    }

    /**
     * Determine if the given cards constitutes a one pair
     * @param hash
     * @return boolean
     */
    private boolean isOnePair (HashMap<Integer, Integer> hash) {
        int pairCount = 0;
        for(Integer value : hash.values()) {
            if(value == 2) {
                pairCount++;
            }
        }

        return pairCount == 1;
    }

    /**
     * Determine if the given cards constitutes a two pair
     * @param hash
     * @return boolean
     */
    private boolean isTwoPair (HashMap<Integer, Integer> hash) {
        int pairCount = 0;
        for(Integer value : hash.values()) {
            if(value == 2) {
                pairCount++;
            }
        }

        return pairCount >= 2;
    }

    /**
     * Determine if the given cards constitutes a three of a kind
     * @param hash
     * @return boolean
     */
    private boolean isThreeOfaKind (HashMap<Integer, Integer> hash) {
        int threes = 0;
        for(Integer value : hash.values()) {
            if(value == 3) {
                threes++;
            }
        }

        return threes == 1;
    }

    /**
     * Determine if the given cards constitutes a four of a kind
     * @param hash
     * @return boolean
     */
    private boolean isFourOfaKind (HashMap<Integer, Integer> hash) {
        int fours = 0;
        for(Integer value : hash.values()) {
            if(value == 4) {
                fours++;
            }
        }

        return fours == 1;
    }

    /**
     * Determine if the given cards constitutes a full house
     * @param hash
     * @return boolean
     */
    private boolean isFullHouse (HashMap<Integer, Integer> hash) {
        int threes = 0;
        for(Integer value : hash.values()) {
            if(value == 3) {
                threes++;
            }
        }

        if(threes == 2)
            return true;
        else
            return threes == 1 && (this.isOnePair(hash) || this.isTwoPair(hash));
    }

    /**
     * Calculate relevant score of the hand
     * @param cards
     * @return hand score
     */
    private int pokerScore (Integer[] cards) {
        int score = 0;
        int shiftBy = 4 * (cards.length - 1);
        for(int i = 0; i < cards.length; i++) {
            score = score | extractValue(cards[i])<<shiftBy;
            shiftBy -= 4;
        }
        return score;
    }

    /**
     * Extract the suit's weight value
     * @param card
     * @return suit weight
     */
    private int extractSuit (int card) {
        if(card < 200)
            return 100;
        else if (card < 300)
            return 200;
        else if (card < 400)
            return 300;
        else
            return 400;
    }

    /**
     * Extract card's face value
     * @param card
     * @return card face value
     */
    private int extractValue (int card) {
        return card%(extractSuit(card));
    }

    /**
     * Returns weight of a suit
     * @param suit
     * @return suit weight
     */
    private int suitWeight (String suit) {
        return suit.equals("C") ? 100
                : suit.equals("D") ? 200
                : suit.equals("H") ? 300
                : suit.equals("S") ? 400
                : 0;
    }

    /**
     * Returns numerical value of a card's face
     * @param face
     * @return face value
     */
    private int faceValue (String face) {
        return face.equals("T") ? 10
                : face.equals("J") ? 11
                : face.equals("Q") ? 12
                : face.equals("K") ? 13
                : face.equals("A") ? 14
                : Integer.parseInt(face);
    }

}
