package com.rishiharan.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RoundEngine {

    public List<Match> executeRound(List<Player> players, int roundNumber) {

        // 1. SORT players based on latest state
        List<Player> sortedPlayers = sortPlayers(players);

        // 2. PAIR players from sorted list
        List<Match> matches = pairPlayers(sortedPlayers, roundNumber);

        // 3. SIMULATE matches + update ELO
        simulateMatches(matches);

        return matches;
    }

    // ---------------- SORT ----------------
    private List<Player> sortPlayers(List<Player> players) {

        return players.stream()
                .sorted((p1, p2) -> {

                    if (p1.points != p2.points) {
                        return Double.compare(p2.points, p1.points);
                    }

                    return Integer.compare(p2.elo, p1.elo);
                })
                .toList();
    }

    // ---------------- PAIRING ----------------
    private List<Match> pairPlayers(List<Player> players, int roundNumber) {

        List<Match> matches = new ArrayList<>();

        List<Player> tempPlayers = new ArrayList<>(players);

        // Handle odd player (BYE)
        if (tempPlayers.size() % 2 == 1) {
            Player bye = tempPlayers.remove(tempPlayers.size() - 1);

            bye.points += 1;
            bye.wins++;
        }

        for (int i = 0; i < tempPlayers.size(); i += 2) {

            Player A = tempPlayers.get(i);
            Player B = tempPlayers.get(i + 1);

            Match match = new Match();
            match.A = A;
            match.B = B;
            match.roundNumber = roundNumber;

            matches.add(match);
        }

        return matches;
    }

    // ---------------- SIMULATION ----------------
    private void simulateMatches(List<Match> matches) {

        Random random = new Random();

        for (Match match : matches) {

            Player A = match.A;
            Player B = match.B;

            double probA = 1.0 / (1.0 + Math.pow(10, (B.elo - A.elo) / 400.0));

            double roll = random.nextDouble();

            if (roll < probA) {

                match.result = "A_WINS";

                A.wins++;
                B.losses++;
                A.points += 1;

            } else {

                match.result = "B_WINS";

                B.wins++;
                A.losses++;
                B.points += 1;
            }

            updateElo(A, B, match.result);
        }
    }

    // ---------------- ELO UPDATE ----------------
    private void updateElo(Player A, Player B, String result) {

        int K = 32;

        double expectedA = 1.0 / (1.0 + Math.pow(10, (B.elo - A.elo) / 400.0));
        double expectedB = 1.0 - expectedA;

        double scoreA;
        double scoreB;

        if (result.equals("A_WINS")) {
            scoreA = 1.0;
            scoreB = 0.0;
        } else {
            scoreA = 0.0;
            scoreB = 1.0;
        }

        A.elo = (int) (A.elo + K * (scoreA - expectedA));
        B.elo = (int) (B.elo + K * (scoreB - expectedB));
    }
}