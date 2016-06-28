package com.nilobarp.holdem;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class AnalyzerTest {

    Table table;
    Player player;
    Analyzer analyzer;
    public AnalyzerTest () {

    }

    @Before
    public void prepare () {
        table = new Table();

        player = new Player();
        player.setId(1);

        table.setPlayers(Arrays.asList(player));

        analyzer = new Analyzer(table);
    }

    private void setCards (String cards) {
        String comCards = cards.substring(0, 14);
        String holdCards = cards.substring(15);

        table.setCards(comCards);
        player.setCards(holdCards);

        analyzer.run();
    }

    @Test
    public void testFlush () throws Exception {
        setCards("H3 D4 H7 HA C2 HT H5");
        assertEquals("Flush", "Flush", player.getHand().getName());

        setCards("H3 D4 C7 HA C2 HT H5");
        assertNotEquals("Flush", "Flush", player.getHand().getName());
    }

    @Test
    public void testStraight () throws Exception {
        setCards("H3 C5 D4 SA S6 D7 S3");
        assertEquals("Straight", "Straight", player.getHand().getName());

        setCards("H3 C5 D2 SA S6 D7 S3");
        assertNotEquals("Straight", "Straight", player.getHand().getName());
    }

    @Test
    public void testStraightFlush () throws Exception {
        setCards("H3 H5 H4 SA H6 H7 S3");
        assertEquals("Straight Flush", "Straight Flush", player.getHand().getName());

        setCards("H3 H5 C4 SA H6 H7 S3");
        assertNotEquals("Straight Flush", "Straight Flush", player.getHand().getName());
    }
    @Test
    public void testRoyalFlush () throws Exception {
        setCards("SA CK CT CQ CJ HA CA");
        assertEquals("is a royal flush", "Royal Flush", player.getHand().getName());

        setCards("SA DK DT DQ DJ HA DA");
        assertEquals("is a royal flush", "Royal Flush", player.getHand().getName());

        setCards("SA SK ST SQ SJ HA DA");
        assertEquals("is a royal flush", "Royal Flush", player.getHand().getName());

        setCards("SA DK HT HQ HJ H4 S7");
        assertNotEquals("is a flush", "Royal Flush", player.getHand().getName());

        setCards("SA HK HT HQ HJ H4 S7");
        assertNotEquals("is a flush", "Royal Flush", player.getHand().getName());

        setCards("SA HK HT HQ HJ HA CA");
        assertEquals("is a royal flush", "Royal Flush", player.getHand().getName());
    }

    @Test
    public void testOnePair () throws Exception {
        setCards("H3 H5 C4 SA H6 HT S3");
        assertEquals("One Pair", "One Pair", player.getHand().getName());

        setCards("H2 H5 C4 SA H6 HT S3");
        assertNotEquals("One Pair", "One Pair", player.getHand().getName());
    }

    @Test
    public void testTwoPair () throws Exception {
        setCards("H3 H5 C4 S5 H6 HT S3");
        assertEquals("Two Pair", "Two Pair", player.getHand().getName());

        setCards("H2 H5 C4 SA H6 HT S3");
        assertNotEquals("Two Pair", "Two Pair", player.getHand().getName());
    }

    @Test
    public void testThreeOfaKind () throws Exception {
        setCards("H3 H5 C3 S7 H6 HT S3");
        assertEquals("Three of a kind", "Three of a Kind", player.getHand().getName());

        setCards("H3 H5 C3 S5 H6 HT S3");
        assertNotEquals("Three of a kind", "Three of a Kind", player.getHand().getName());
    }

    @Test
    public void testFourOfaKind () throws Exception {
        setCards("H3 H5 C3 S5 D5 C5 S3");
        assertEquals("Four of a Kind", "Four of a Kind", player.getHand().getName());

        setCards("H3 H5 C3 S5 D7 C5 S3");
        assertNotEquals("Four of a Kind", "Four of a Kind", player.getHand().getName());
    }

    @Test
    public void testFullHouse () throws Exception {
        setCards("H3 H5 C3 S5 H6 HT S3");
        assertEquals("Full House", "Full House", player.getHand().getName());

        setCards("H3 H5 C3 S5 D5 HT S3");
        assertEquals("Full House", "Full House", player.getHand().getName());

        setCards("H3 H5 C3 S5 H6 HT ST");
        assertNotEquals("Full House", "Full House", player.getHand().getName());
    }

    @Test
    public void testBestHand () throws Exception {
        setCards("H3 H5 C3 S5 H6 HT S3");
        int player_1_score = player.getHand().getScore();

        setCards("H3 H5 C3 S5 H6 H7 S3");
        int player_2_score = player.getHand().getScore();

        assertEquals("Score 1 is bigger", true, player_1_score > player_2_score);
    }
}
