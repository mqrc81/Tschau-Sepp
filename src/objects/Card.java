package objects;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class Card {
    private final int number; //11: Jack | 12: Queen | 13: King | 14: Ace
    private final int symbol; //1: Spades | 2: Hearts | 3: Clubs | 4: Diamonds

    public Card(int number, int symbol) {
        this.number = number;
        this.symbol = symbol;
    }

    public int getNumber() {
        return number;
    }

    public int getSymbol() {
        return symbol;
    }

    public String getName() {
        return number + "_" + symbol;
    }

    public int getPoints() {
        if (number < 11) {
            return number;
        } else if (number == 11) {
            return 20;
        } else if (number == 14) {
            return 11;
        } else {
            return number - 9;
        }
    }

}
