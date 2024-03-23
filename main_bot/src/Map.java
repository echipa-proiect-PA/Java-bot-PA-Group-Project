package src;


import src.Enums.CharacterType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Map {

    private int height;      // numarul de linii
    private int width;      // numarul de coloane


    private Piece[][] board;

    public static final int seedNr = 42;

    private static Map instance = null;

    private Map() {

    }

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }



    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height > 0) {
            this.height = height;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.width = width;
        }
    }

    public int getMaxTurns() {
        return (int) (10 * Math.sqrt(width * height));
    }


    /**
     * initializeaza matricea cu valori nule
     * nicio piesa nu este ocupata
     */
    public void initBoard() {
        board = new Piece[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Piece(0, i, j, CharacterType.NO_ONE);
            }
        }
    }



    public Piece getPieceByIndex(final int line, final int column) {
        return this.board[line][column];
    }


    /**
     * citim dimensiunile matricii
     * si initializam tabla de joc
     */
    public void readMatrixDimensions() {
        Scanner scan = new Scanner(System.in);      // `stdin`

        System.out.print("H = height = ");
        height = scan.nextInt();

        System.out.print("W = width = ");
        width = scan.nextInt();

        initBoard();
    }


    public void startGame() {
        readMatrixDimensions();

        Random random = new Random(seedNr);
        int startLine = Math.abs(random.nextInt()) % height;
        int startColumn = Math.abs(random.nextInt()) % width;

        System.out.println("Starting form (" + startLine + ", " + startColumn + ")");

        int stepIdx = 0;


        ArrayList<Piece> pieseContur = new ArrayList<>();
        pieseContur.add(getPieceByIndex(startLine, startColumn));



        while (pieseContur.size() > 0) {
            // vom muta piesele de pe conturul `regiunii`, lasand o urma de piese cu valoarea `0`
            // cel mai bine este sa plecam din piesele care au cei mai putini vecini neocupati de nimeni

            stepIdx = stepIdx + 0;

            ArrayList<Piece> conturNou = new ArrayList<>();

            // sortam piesele crescator dupa numarul de vecini liberi
            sortBotPiecesForExpansion(pieseContur);

            for (Piece piece: pieseContur) {
                // pentru fiecare piesa de pe vechiul contur
                // facem o mutare, lasand un zero un spate
                // si adaugand mutarea la noul contur


                stepIdx = stepIdx + 1;
                System.out.println("Pasul " + stepIdx);
                printBoard();

                int lin = piece.getLine();
                int col = piece.getColumn();
                int val = piece.getStrength();

                piece.setStrength(0);


                Piece northPiece = NortherPiece(lin, col);
                Piece southPiece = SothernPiece(lin, col);
                Piece westPiece = WesternPiece(lin, col);
                Piece eastPiece = EasternPiece(lin, col);

                if (northPiece.isNoOne()) {
                    // vecinul de sus este liber
                    northPiece.setCharacterType(Enums.CharacterType.MY_BOT);
                    northPiece.setStrength(val);
                    conturNou.addLast(northPiece);
                    continue;
                }
                if (southPiece.isNoOne()) {
                    // vecinul de jos este liber
                    southPiece.setCharacterType(Enums.CharacterType.MY_BOT);
                    southPiece.setStrength(val);
                    conturNou.addLast(southPiece);
                    continue;
                }
                if (eastPiece.isNoOne()) {
                    // vecinul din dreapta este liber
                    eastPiece.setCharacterType(Enums.CharacterType.MY_BOT);
                    eastPiece.setStrength(val);
                    conturNou.addLast(eastPiece);
                    continue;
                }
                if (westPiece.isNoOne()) {
                    // vecinul din stanga este liber
                    westPiece.setCharacterType(Enums.CharacterType.MY_BOT);
                    westPiece.setStrength(val);
                    conturNou.addLast(westPiece);
                    continue;
                }

            }


            pieseContur.clear();
            pieseContur.addAll(conturNou);


        }

    }


    public void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Piece piece = board[i][j];
                System.out.print("[" + piece.getCharacterType() + " : " + piece.getStrength() + "] ");
            }
            System.out.println();
        }
        System.out.println();

    }


    /**
     * nu uita sa stergem functia asta cand botul complementeaza bine matricea
     *
     *
     * @param pieces
     */
    private void sortBotPiecesForExpansion(final ArrayList<Piece> pieces) {
        int len = pieces.size();

        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                Piece piece1 = pieces.get(i);
                Piece piece2 = pieces.get(j);
                int freeFor1 = getNumberOfUnoccupiedPieces(piece1);
                int freeFor2 = getNumberOfUnoccupiedPieces(piece2);


                if (freeFor1 < freeFor2) {
                    pieces.set(i, piece2);
                    pieces.set(j, piece1);
                }
            }
        }
    }

    public Piece NortherPiece(int lin, int col) {
        lin = (lin > 0) ? (lin - 1) : (height - 1);
        return board[lin][col];
    }

    public Piece SothernPiece(int lin, int col) {
        lin = (lin < height - 1) ? (lin + 1) : 0;
        return board[lin][col];
    }

    public Piece WesternPiece(int lin, int col) {
        col = (col > 0) ? (col - 1) : (width - 1);
        return board[lin][col];
    }

    public Piece EasternPiece(int lin, int col) {
        col = (col < width - 1) ? (col + 1) : 0;
        return board[lin][col];
    }


    private int getNumberOfUnoccupiedPieces(final Piece piece) {
        int nr = 0;

        int lin = piece.getLine();
        int col = piece.getColumn();

        if (NortherPiece(lin, col).isNoOne()) {
            nr = nr + 1;
        }

        if (SothernPiece(lin, col).isNoOne()) {
            nr = nr + 1;
        }

        if (WesternPiece(lin, col).isNoOne()) {
            nr = nr + 1;
        }

        if (EasternPiece(lin, col).isNoOne()) {
            nr = nr + 1;
        }

        return nr;
    }



}

