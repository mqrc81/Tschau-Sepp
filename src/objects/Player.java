package objects;

import javax.swing.*;
import java.util.*;
import java.util.List;

/**
 * an instance of player, which has a set of cards and corresponding buttons
 *
 * @author Marc Schmidt
 * @since 2020-05-14
 * @version 1.3
 */
public class Player {

    private List<Card> cards = new ArrayList<>();
    private List<JButton> buttons = new ArrayList<>();

    /**
     * getter method
     *
     * @return cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * getter method
     *
     * @return buttons
     */
    public List<JButton> getButtons() {
        return buttons;
    }

    /**
     * getter method
     * @return points
     */
    public int getPoints() {
        int points = 0;
        for (Card c : cards) {
            points += c.getPoints();
        }
        return points;
    }

    /**
     * add card to player's hand
     *
     * @param c card to be added
     */
    public void addCard(Card c) {
        cards.add(c);
    }

    /**
     * remove card from player's hand and returns card's index, in order to remove corresponding button
     *
     * @param c card to be removed
     * @return index of card in array
     */
    public int removeCard(Card c) {
        int x = cards.indexOf(c);
        cards.remove(c);
        return x;
    }

    /**
     * add button corresponding to a card
     *
     * @param b button to be added
     */
    public void addButton(JButton b) {
        buttons.add(b);
    }

    /**
     * remove button corresponding to a card
     *
     * @param b button to be removed
     */
    public void removeButton(JButton b) {
        buttons.remove(b);
    }

    /**
     * getter method
     *
     * @return amount of cards in player's hand
     */
    public int handSize() {
        return cards.size();
    }
}

