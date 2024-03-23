package src;


import src.Enums.CharacterType;

public class Piece {
    private int strength;
    private int line;
    private int column;
    private CharacterType characterType;


    /**
     * copy constructor
     *
     * @param strength          valoarea din casuta
     * @param line              indexul liniei din matrice
     * @param column            indexul coloanei din matrice
     * @param characterType     tipul de jucator care ocupa casuta respectiva
     */
    public Piece(final int strength, final int line, final int column, final Enums.CharacterType characterType) {
        this.strength = strength;
        this.line = line;
        this.column = column;
        this.characterType = characterType;
    }


    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }


    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(final CharacterType characterType) {
        this.characterType = characterType;
    }



    public boolean isMyBot() {
        return (this.characterType.equals(CharacterType.MY_BOT));
    }

    public boolean isEnemy() {
        return (this.characterType.equals(CharacterType.ENEMY));
    }

    public boolean isNoOne() {
        return (this.characterType.equals(CharacterType.NO_ONE));
    }
}
