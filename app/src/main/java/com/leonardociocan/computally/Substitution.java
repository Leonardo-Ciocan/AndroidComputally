package com.leonardociocan.computally;

public class Substitution {
    String from;
    String to;

    public Substitution(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {

        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
