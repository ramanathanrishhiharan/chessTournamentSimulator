package com.rishiharan.chess;

import java.util.HashSet;
import java.util.Set;

public class Player {

    String name;
    int elo;
    double points;
    int wins;
    int losses;
    int draws;

    Set<Player> opponentsPlayed = new HashSet<>();

    public Player(String name, int elo) {
        this.name = name;
        this.elo = elo;

        this.points = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }
}