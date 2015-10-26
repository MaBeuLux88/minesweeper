package com.zenika;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleGame game = new ConsoleGame(new Scanner(System.in), System.out);
        game.startGame();
    }
}
