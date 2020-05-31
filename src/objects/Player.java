package objects;

import gui.NewGame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class Player {

    private List<Card> cards = new ArrayList<>();
    private List<JButton> buttons = new ArrayList<>();

    public Player() {

    }

    public List<Card> getCards() {
        return cards;
    }
    public List<JButton> getButtons() {
        return buttons;
    }

    public int getPoints() {
        int points = 0;
        for (Card c : cards) {
            points += c.getPoints();
        }
        return points;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public int removeCard(Card c) {
        int x = cards.indexOf(c);
        cards.remove(c);
        return x;
    }

    public void addButton(JButton b) {
        buttons.add(b);
    }

    public void removeButton(JButton b) {
        buttons.remove(b);
    }

    public int handSize() {
        return cards.size();
    }
}

