package com.rishiharan.chess;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoundEngine {

    // ONLY public entry point
    public List<Match> executeRound(List<Player> players, int roundNumber) {

        // Step 1: sort players
        List<Player> sortedPlayers = sortPlayers(players);

        // Step 2: pair players
        List<Match> matches = pairPlayers(sortedPlayers, roundNumber);

        // Step 3: simulate matches
        simulateMatches(matches);


        return matches;
    }

    // internal step 1
    private List<Player> sortPlayers(List<Player> players) {
        // points DESC, then ELO DESC
        return players.stream()
                .sorted((p1,p2)->{
                    if(p1.points!=p2.points){
                        return Double.compare(p2.points,p1.points);
                    }
                    return Integer.compare(p2.elo, p1.elo);

                })
                .toList();
    }

    // internal step 2
    private List<Match> pairPlayers(List<Player> players, int roundNumber) {
        List<Match> matches = new ArrayList<>();


         for (int i=0;i< players.size();i+=2){
           Player A = players.get(i);
           Player B = players.get(i+1);

           Match match = new Match();
           match.A = A;
           match.B = B;
           match.roundNumber = roundNumber;

           matches.add(match);

         }
        return matches;
    }

    // internal step 3
    private void simulateMatches(List<Match> matches) {

        Random random = new Random();

        for (Match match : matches) {
            Player A = match.A;
            Player B = match.B;

            double probabilityA = 1.0/(1.0+Math.pow(10,(B.elo-A.elo)/400.0));
            double roll = random.nextDouble();

            if (roll<probabilityA){
                match.result="A wins";
                A.wins++;
                B.losses++;

                A.points+=1;

            }
            else {
                match.result="B wins";
                B.wins++;
                A.losses++;
                B.points+=1;
            }

        }
    }


}