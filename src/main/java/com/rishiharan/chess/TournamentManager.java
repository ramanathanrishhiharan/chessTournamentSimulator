package com.rishiharan.chess;

import java.util.ArrayList;
import java.util.List;

public class TournamentManager {

    List<Player> players = new ArrayList<>();
    int totalRounds = 10;

    RoundEngine roundEngine = new RoundEngine();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startTournament() {

        if (players.size() < 2) {
            System.out.println("Need at least 2 players");
            return;
        }

        System.out.println("Tournament Started with " + players.size() + " players");

        for (int round = 1; round <= totalRounds; round++) {

            System.out.println("\n====================");
            System.out.println("ROUND " + round);
            System.out.println("====================");

            List<Match> matches = roundEngine.executeRound(players, round);

            printRoundResults(matches);
            printStandings();
        }

        printWinner();
    }


    private void printRoundResults(List<Match> matches) {

        for (Match m : matches) {

            String result;

            if (m.result.equals("A_WINS")) {
                result = m.A.name + " beat " + m.B.name;
            } else {
                result = m.B.name + " beat " + m.A.name;
            }

            System.out.println(result);
        }
    }


    private void printStandings() {

        System.out.println("\n--- STANDINGS ---");

        players.stream()
                .sorted((p1, p2) -> {
                    if (p1.points != p2.points) {
                        return Double.compare(p2.points, p1.points);
                    }
                    return Integer.compare(p2.elo, p1.elo);
                })
                .forEach(p ->
                        System.out.println(
                                p.name + " | Points: " + p.points + " | ELO: " + p.elo
                        )
                );
    }


    private void printWinner() {

        Player winner = players.stream()
                .sorted((p1, p2) -> {
                    if (p1.points != p2.points) {
                        return Double.compare(p2.points, p1.points);
                    }
                    return Integer.compare(p2.elo, p1.elo);
                })
                .findFirst()
                .orElse(null);

        System.out.println("\n🏆 TOURNAMENT WINNER: " + winner.name);
    }
}