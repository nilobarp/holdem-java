package com.nilobarp.holdem;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/analyze")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Game {
    @POST
    @Timed
    public Table analyze (Table table) {
        Analyzer analyzer = new Analyzer(table);

        return analyzer.run();
    }
}
