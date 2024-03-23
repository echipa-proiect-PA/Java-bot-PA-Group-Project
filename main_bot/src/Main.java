package src;

import java.util.Random;
import java.util.Scanner;

public class Main {

    final static int seedNR = 42;

    public static void main(String[] args) {

        Map map = Map.getInstance();

        map.startGame();

    }

}
