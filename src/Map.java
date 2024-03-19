package src;

public class Map {

    private static Map instance = null;

    private Map() {

    }

    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    private int height;      // numarul de linii
    private int width;      // numarul de coloane


    private Piece[][] board;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMaxTurns() {
        return (int) (10 * Math.sqrt(width * height));
    }
}
