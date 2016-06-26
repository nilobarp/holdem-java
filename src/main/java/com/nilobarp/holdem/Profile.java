package com.nilobarp.holdem;

import com.codahale.metrics.annotation.Timed;
import com.github.javafaker.Faker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

/**
 * Generates a random player profile
 */
@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
public class Profile {
    @GET
    @Timed
    public HashMap<String, String> generate () {
        Faker faker = new Faker();
        HashMap<String, String> player = new HashMap<String, String>();
        player.put("name", faker.name().firstName());
        player.put("avatar", faker.internet().avatar());
        return player;
    }
}
