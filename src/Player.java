import java.util.*;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class Player {

    private List<Card> cards = new ArrayList<>();
    private int rank = 0;

    public Player() {
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getPoints() {
        int points = 0;
        for (Card c: cards) {
            points += c.getPoints();
        }
        return points;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public void removeCard(Card cRemove) {
        for (Card c: cards) {
            if (c.getName().equals(cRemove.getName())) {
                cards.remove(c);
                break;
            }
        }
    }

    public int handSize() {
        return cards.size();
    }

}