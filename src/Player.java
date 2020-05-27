import java.util.*;

/**
 * @author: Marc Schmidt
 * @date: 2020-05-14
 * @project: M326
 */

public class Player {

    private List<Card> cards = new ArrayList<>();

    public Player() {
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    //FIXME
    public void removeCard(Card cRemove) {
        if (cards.removeIf(c -> c.getName().equals(cRemove.getName()))) {
            System.out.println("Yes babyyyyyy");
        } else {
            System.out.println("no baby nooooo");
        }
    }

    public int handSize() {
        return cards.size();
    }

}