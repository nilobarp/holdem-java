package com.nilobarp.holdem;

import java.util.*;

public class Analyzer {
    private Table table;


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

    private Player reduce (List<String> communityCards, final Player player) {
        List<String> cards = new ArrayList<String>();
        cards.addAll(communityCards);
        cards.addAll(player.getCards());
        String suits = cards.toString().replaceAll("[^CDHS]", "");
        player.setSuits(suits);

        List<Integer> faceValue = new ArrayList<Integer>();
        String[] cardValue = this.toValue(cards);
        for (int i = 0; i < this.toValue(cards).length; i++) {
            faceValue.add(faceValue(cardValue[i]));
        }
        player.setCardValues(faceValue);

        HashMap<String, Integer> hash = new HashMap<String, Integer>();

        for(int i = 0; i < cards.size(); i++) {
            String key = Integer.toString(faceValue.get(i));
            hash.put(key, (hash.get(key) != null && hash.get(key) >= 1) ? hash.get(key) + 1 : 1);
        }

        player.setHash(hash);

        player.getCardValues().sort(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                HashMap<String, Integer> hash = player.getHash();
                String aKey = Integer.toString(a);
                String bKey = Integer.toString(b);

                return (hash.get(aKey) < hash.get(bKey)) ? 1
                        : (hash.get(aKey) > hash.get(bKey)) ? -1
                        : (b - a);
            }
        });

        faceValue = player.getCardValues();
        Hand hand = new Hand();
        hand.setValue(
                faceValue.get(0)<<24 |
                        faceValue.get(1)<<20 |
                        faceValue.get(2)<<16 |
                        faceValue.get(3)<<12 |
                        faceValue.get(4)<<8 |
                        faceValue.get(5)<<4 |
                        faceValue.get(6)
        );

        hand.rank(player.getSuits(), player.getCardValues(), player.getHash());

        player.setHand(hand);

        return player;
    }

    private String[] toValue (List<String> cards) {
        return cards.toString().replaceAll("[CDHS ]", "")
                .replace("[", "")
                .replace("]", "")
                .split(",");
    }

    private int faceValue (String face) {
        return face.equals("T") ? 10
                : face.equals("J") ? 11
                : face.equals("Q") ? 12
                : face.equals("K") ? 13
                : face.equals("A") ? 14
                : Integer.parseInt(face);
    }

}
