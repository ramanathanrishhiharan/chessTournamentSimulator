package com.rishiharan.chess;

import com.rishiharan.chess.TournamentManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TournamentManager tm = new TournamentManager();
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the number of players are participated in the tournament : ");
        int n = input.nextInt();
        input.nextLine();

        if (n<2){
            System.out.println("tournament need least 2 players");
            return;
        }

        for(int i=0; i<n; i++) {
            System.out.println("\nPlayer " + (i + 1) + ":");

            System.out.print("Name: ");
            String name = input.nextLine();

            System.out.print("ELo rating :");
            int elo = input.nextInt();
            input.nextLine();
            tm.addPlayer(new Player(name, elo));
        }




        tm.startTournament();
        input.close();
    }
}