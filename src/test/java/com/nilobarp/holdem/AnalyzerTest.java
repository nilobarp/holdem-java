package com.nilobarp.holdem;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class AnalyzerTest {

    Table table;
    public AnalyzerTest () {

    }

    @Before
    public void prepare () {
        table = new Table();
        table.setCards(Arrays.asList("HA", "HK", "HQ", "HJ", "ST"));

        Player player = new Player();
        player.setId(1);
        player.setCards(Arrays.asList("H5", "S7"));

        table.setPlayers(Arrays.asList(player));
    }

    @Test
    public void preparesHandsForAnalysis () throws Exception {
        Analyzer analyzer = new Analyzer(table);

        Table t = analyzer.run();

        Player p = t.getPlayers().get(0);

        assertEquals("has a valid suit", "DDSSSSS", p.getSuits());
        assertEquals("is a flush", "Flush", p.getHand().getName());

    }
}
