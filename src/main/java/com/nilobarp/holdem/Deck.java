package com.nilobarp.holdem;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getDeck")
@Produces(MediaType.APPLICATION_JSON)
public class Deck {
    private final int numberOfCards;

    Logger log = LoggerFactory.getLogger(Deck.class);

    public Deck(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @GET()
    @Timed
    public ArrayList<String> deck () {
        Dealer dealer = new Dealer();
        return dealer.getDeck();
    }
}
