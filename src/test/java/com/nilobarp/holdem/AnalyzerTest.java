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
        table.setCards(Arrays.asList("SA", "D5", "SQ", "SJ", "ST"));

        Player player = new Player();
        player.setId(1);
        player.setCards(Arrays.asList("DA", "SK"));

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
