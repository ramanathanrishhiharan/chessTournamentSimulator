package com.rishiharan.chess;

import com.rishiharan.chess.TournamentManager;

public class Main {

    public static void main(String[] args) {

        TournamentManager tm = new TournamentManager();

        tm.addPlayer(new Player("Alice", 1800));
        tm.addPlayer(new Player("Bob", 1700));
        tm.addPlayer(new Player("Charlie", 1600));
        tm.addPlayer(new Player("David", 1500));
        tm.addPlayer(new Player("Emma", 1750));
        tm.addPlayer(new Player("Frank", 1650));
        tm.addPlayer(new Player("George", 1550));
        tm.addPlayer(new Player("Henry", 1450));

        tm.startTournament();
    }
}