package objects;

/**
 * an instance of card, consisting of number, symbol and points
 *
 * @author Marc Schmidt
 * @since 2020-05-14
 * @version 1.3
 */
public class Card {
    private final int number; //11: Jack | 12: Queen | 13: King | 14: Ace
    private final int symbol; //1: Spades | 2: Hearts | 3: Clubs | 4: Diamonds

    /**
     * constructor method
     *
     * @param number number (value) of card
     * @param symbol symbol (color) of card
     */
    public Card(int number, int symbol) {
        this.number = number;
        this.symbol = symbol;
    }

    /**
     * getter method
     *
     * @return number
     */
    public int getNumber() {
        return number;
    }

    /**
     * getter method
     *
     * @return symbol
     */
    public int getSymbol() {
        return symbol;
    }

    /**
     * getter method
     *
     * @return name
     */
    public String getName() {
        //used for card image
        return number + "_" + symbol;
    }

    /**
     * getter method
     *
     * @return points
     */
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