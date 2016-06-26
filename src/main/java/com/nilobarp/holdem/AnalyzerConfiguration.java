package com.nilobarp.holdem;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class AnalyzerConfiguration extends Configuration {
    private int numberOfCards;

    @JsonProperty
    public int getNumberOfCards () {
        return this.numberOfCards;
    }

    @JsonProperty
    public void setNumberOfCards (int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
}
