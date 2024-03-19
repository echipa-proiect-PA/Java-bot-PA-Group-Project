package src;

import java.util.Random;
import java.util.Scanner;

public class Main {

    final static int seedNR = 42;

    public static void main(String[] args) {

        Map map = Map.getInstance();

        Scanner scan = new Scanner(System.in);      // `stdin`

        System.out.print("H = height = ");
        map.setHeight(scan.nextInt());

        System.out.print("W = width = ");
        map.setWidth(scan.nextInt());

        Random random = new Random(seedNR);
        int start_line = Math.abs(random.nextInt()) % map.getHeight();
        int start_column = Math.abs(random.nextInt()) % map.getWidth();

        System.out.println("Starting form (" + start_line + ", " + start_column + ")");
    }

}
