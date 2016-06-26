package com.nilobarp.holdem;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class HoldemApplication extends Application<AnalyzerConfiguration> {
    public static void main (String[] args) throws Exception {
        new HoldemApplication().run(args);
    }

    @Override
    public String getName () {
        return "Holdem";
    }

    @Override
    public void initialize (Bootstrap<AnalyzerConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run (AnalyzerConfiguration configuration, Environment environment) throws Exception {
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        environment.jersey().setUrlPattern("/api/*");
        final Deck deck = new Deck(configuration.getNumberOfCards());
        final Profile profile = new Profile();
        final Game game = new Game();
        environment.jersey().register(deck);
        environment.jersey().register(profile);
        environment.jersey().register(game);
    }
}
